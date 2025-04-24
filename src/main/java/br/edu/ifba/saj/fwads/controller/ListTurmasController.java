package br.edu.ifba.saj.fwads.controller;

import java.io.IOException;
import java.util.List;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.model.Aluno;
import br.edu.ifba.saj.fwads.model.Turma;
import br.edu.ifba.saj.fwads.model.Usuario;
import br.edu.ifba.saj.fwads.negocio.TurmaService;
import br.edu.ifba.saj.fwads.negocio.UsuarioService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ListTurmasController {

    @FXML
    private TableColumn<Turma, String> columnCurso;

    @FXML
    private TableColumn<Turma, String> columnNomeTurma;

    @FXML
    private TableColumn<Turma, String> columnProfessores;

    @FXML
    private TableColumn<Turma, String> columnAlunos;

    @FXML
    private TableView<Turma> tabelaTurma;

    private final TurmaService turmaService = new TurmaService();
    private final Usuario usuario = UsuarioService.getUsuarioLogado();

    @FXML
    private void initialize() {
        columnNomeTurma.setCellValueFactory(new PropertyValueFactory<>("nome"));
        columnCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));

        columnProfessores.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getProfessoresNome()));

        columnAlunos.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getAlunosNome()));

        atualizarLista();
    }

    public void adicionarTurma(Turma turma) {
        turmaService.adicionarTurma(turma, usuario);
        atualizarLista();
    }

    public void atualizarLista() {
        List<Turma> turmasAtualizadas = turmaService.buscarTurmas();
        tabelaTurma.getItems().clear();
        tabelaTurma.setItems(FXCollections.observableArrayList(turmasAtualizadas));
        tabelaTurma.refresh();
    }



    @FXML
    void showNewTurma() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("Controller/CriarTurma.fxml"));
            Scene scene = new Scene(loader.load());

            CriarTurmaController criarTurmaController = loader.getController();
            criarTurmaController.setListTurmasController(this);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Criar Turma");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void removerTurma() {
        Turma turmaSelecionada = tabelaTurma.getSelectionModel().getSelectedItem();
        
        if (turmaSelecionada == null) {
            mostrarAlerta("Erro: Nenhuma turma selecionada.");
            return;
        }

        //Aqui eu fiz com que, se por exemplo o aluno esta na turma de poo, e eu apago/removo a turma de poo, altomaticamente o aluno é removido dela, pois ela n existe mais.
        for (Aluno aluno : turmaSelecionada.getAlunos()) {
            aluno.getTurmas().remove(turmaSelecionada); 
        }
   
        turmaService.removerTurma(turmaSelecionada, usuario); 

        atualizarLista();
        mostrarAlerta("Turma removida com sucesso.");
    }




    @FXML
    private void editarTurma() {
        Turma turmaSelecionada = tabelaTurma.getSelectionModel().getSelectedItem();

        if (turmaSelecionada != null) {
            try {
                FXMLLoader loader = new FXMLLoader(App.class.getResource("Controller/CriarTurma.fxml"));
                Scene scene = new Scene(loader.load());

                CriarTurmaController criarTurmaController = loader.getController();
                criarTurmaController.setListTurmasController(this);
                criarTurmaController.setTurmaParaEdicao(turmaSelecionada); 

                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Editar Turma");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Edição de Turma");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecione uma turma para editar.");
            alert.showAndWait();
        }
    }

    private void mostrarAlerta(String mensagem) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setContentText(mensagem);
    alert.show();
}


}