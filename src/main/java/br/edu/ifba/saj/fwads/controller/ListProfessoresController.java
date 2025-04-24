package br.edu.ifba.saj.fwads.controller;

import java.time.LocalDate;
import java.util.List;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.model.Professor;
import br.edu.ifba.saj.fwads.model.Usuario;
import br.edu.ifba.saj.fwads.negocio.ProfessorService;
import br.edu.ifba.saj.fwads.negocio.UsuarioService;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ListProfessoresController {

    @FXML
    private TableColumn<Professor, String> columnCPF;
    
    @FXML
    private TableColumn<Professor, LocalDate> columnNascimento;
    
    @FXML
    private TableColumn<Professor, Integer> columnIdentificacao;
    
    @FXML
    private TableColumn<Professor, String> columnProfessores;
    
    @FXML
    private TableColumn<Professor, Integer> columnTempoCarreira;
    
    @FXML
    private TableView<Professor> tabelaProfessores;

    private final ProfessorService professorService = new ProfessorService();
    private final Usuario usuario = UsuarioService.getUsuarioLogado();

    @FXML
    private void initialize() {
        columnProfessores.setCellValueFactory(new PropertyValueFactory<>("professorNome"));
        columnNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
        columnIdentificacao.setCellValueFactory(new PropertyValueFactory<>("identificacao"));
        columnCPF.setCellValueFactory(new PropertyValueFactory<>("CPF"));     
        columnTempoCarreira.setCellValueFactory(new PropertyValueFactory<>("tempoCarreira"));

        atualizarTabela();
    }

    @FXML
    void showNovoProfessor() {
        Stage stage = new Stage();
        Scene scene = new Scene(App.loadFXML("controller/CadProfessor.fxml"), 400, 300);
        stage.setScene(scene);
        stage.setTitle("Cadastrar Professor");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        atualizarTabela();
    }

    private void atualizarTabela() {
        List<Professor> professores = professorService.buscarProfessores();
        tabelaProfessores.getItems().setAll(professores);
    }

    @FXML
    void removerProfessor() {
        Professor professorSelecionado = tabelaProfessores.getSelectionModel().getSelectedItem();

        if (professorSelecionado != null) {
            professorService.deletar(professorSelecionado, usuario);

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Professor " + professorSelecionado.getProfessorNome() + " removido.");
            alert.show();

            atualizarTabela();
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setContentText("Selecione um professor para remover.");
            alert.show();
        }
    }
}
