package br.edu.ifba.saj.fwads.controller;

import java.util.List;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.model.Curso;
import br.edu.ifba.saj.fwads.model.Usuario;
import br.edu.ifba.saj.fwads.negocio.CursoService;
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

public class ListCursosController {

    @FXML
    private TableColumn<Curso, String> columnDuracao;
    @FXML
    private TableColumn<Curso, String> columnNomeCurso;
    @FXML
    private TableView<Curso> tabelaCursos;

    private CursoService cursoService = new CursoService();
    private Usuario usuario = UsuarioService.getUsuarioLogado();

    @FXML
    private void initialize() {
        columnNomeCurso.setCellValueFactory(new PropertyValueFactory<>("nome"));
        columnDuracao.setCellValueFactory(new PropertyValueFactory<>("duracao"));
        atualizarTabela();
    }

    private void atualizarTabela() {
        List<Curso> cursos = cursoService.buscarCursos();
        tabelaCursos.getItems().setAll(cursos);
    }

    @FXML
    private void showNovoCurso() {
        Stage stage = new Stage();            
        Scene scene = new Scene(App.loadFXML("controller/CadCurso.fxml"));            
        stage.setScene(scene);
        stage.setTitle("Cadastrar Curso");
        stage.initModality(Modality.APPLICATION_MODAL); 
        stage.showAndWait();  

        atualizarTabela();
    }

    @FXML
    void removerCurso() {
        Curso cursoSelecionado = tabelaCursos.getSelectionModel().getSelectedItem();

        if (cursoSelecionado != null) {
            cursoService.deletar(cursoSelecionado, usuario);
            atualizarTabela();
            mostrarAlerta("Curso " + cursoSelecionado.getNome() + " removido da Escola.", AlertType.INFORMATION);
        } else {
            mostrarAlerta("Selecione um curso para remover.", AlertType.WARNING);
        }
    }

    private void mostrarAlerta(String mensagem, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setContentText(mensagem);
        alert.show();
    }
}
