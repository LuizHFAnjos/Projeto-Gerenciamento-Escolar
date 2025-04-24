package br.edu.ifba.saj.fwads.controller;

import java.util.List;

import br.edu.ifba.saj.fwads.model.Aluno;
import br.edu.ifba.saj.fwads.negocio.AlunoService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ListDirecaoController {

    @FXML
    private TableColumn<Aluno, String> columnNome;

    @FXML
    private TableColumn<Aluno, String> columnMatricula;

    @FXML
    private TableColumn<Aluno, String> columnCurso;

    @FXML
    private TableColumn<Aluno, String> columnTurmas;

    @FXML
    private TableView<Aluno> tblDirecao;

    private final AlunoService alunoService = new AlunoService(); 

    @FXML
    public void initialize() {
        columnNome.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getNome()));

        columnMatricula.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getMatricula()));

        columnCurso.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getNomeCurso()));

        columnTurmas.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getNomesTurmas()));

        atualizarTabela();
    }

    public void atualizarTabela() {
        List<Aluno> alunosAtualizados = alunoService.buscarAlunos(); 
        tblDirecao.setItems(FXCollections.observableArrayList(alunosAtualizados));
        tblDirecao.refresh();
    }
}
