package br.edu.ifba.saj.fwads.controller;

import java.time.LocalDate;
import java.util.List;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.model.Aluno;
import br.edu.ifba.saj.fwads.model.Usuario;
import br.edu.ifba.saj.fwads.negocio.AlunoService;
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

public class ListAlunoController {

    @FXML
    private TableView<Aluno> tblAluno;

    @FXML
    private TableColumn<Aluno, String> columnNome;
    @FXML
    private TableColumn<Aluno, String> columnMatricula;
    @FXML
    private TableColumn<Aluno, String> columnCPF;
    @FXML
    private TableColumn<Aluno, String> columnEmail;
    @FXML
    private TableColumn<Aluno, LocalDate> columnNascimento;

    private AlunoService alunoService = new AlunoService();
    private Usuario usuario = UsuarioService.getUsuarioLogado();

    @FXML
    public void initialize() {
        columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        columnMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        columnCPF.setCellValueFactory(new PropertyValueFactory<>("CPF"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
        atualizarTabela();
    }

    private void atualizarTabela() {
        List<Aluno> alunos = alunoService.buscarAlunos();
        tblAluno.getItems().setAll(alunos);
    }

    @FXML
    public void showCadAlunos() {
        Stage stage = new Stage();
        Scene scene = new Scene(App.loadFXML("controller/CadAluno.fxml"), 400, 300);
        stage.setScene(scene);
        stage.setTitle("Matricular Aluno");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        
        atualizarTabela();
    }

    @FXML
    void removerAluno() {
        Aluno alunoSelecionado = tblAluno.getSelectionModel().getSelectedItem();
        if (alunoSelecionado != null) {
            alunoService.deletar(alunoSelecionado, usuario); 

            atualizarTabela();
            new Alert(AlertType.INFORMATION, "Aluno " + alunoSelecionado.getNome() + " removido.").show();
        } else {
            new Alert(AlertType.WARNING, "Selecione um aluno para remover.").show();
        }
    }
}
