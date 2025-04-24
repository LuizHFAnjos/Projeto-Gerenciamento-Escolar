package br.edu.ifba.saj.fwads.controller;

import java.util.List;

import br.edu.ifba.saj.fwads.model.Aluno;
import br.edu.ifba.saj.fwads.model.Curso;
import br.edu.ifba.saj.fwads.model.Usuario;
import br.edu.ifba.saj.fwads.negocio.AlunoService;
import br.edu.ifba.saj.fwads.negocio.CursoService;
import br.edu.ifba.saj.fwads.negocio.UsuarioService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CadTurmaController {

    @FXML
    private ComboBox<Aluno> idAluno;

    @FXML
    private ChoiceBox<Curso> idCurso;

    @FXML
    private TableView<Aluno> idTabela;

    @FXML
    private TableColumn<Aluno, String> columnNome;

    @FXML
    private TableColumn<Aluno, String> columnCurso;

    private CursoService cursoService = new CursoService();
    private AlunoService alunoService = new AlunoService();
    private Usuario usuario = UsuarioService.getUsuarioLogado();

    private ObservableList<Aluno> observarAlunosCursos = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        columnCurso.setCellValueFactory(cellData -> {
            Aluno aluno = cellData.getValue();
            String nomeCurso = (aluno.getCurso() != null) ? aluno.getCurso().getNome() : "";
            return new SimpleStringProperty(nomeCurso);
        });

        idTabela.setItems(observarAlunosCursos);
        idCurso.getItems().addAll(cursoService.buscarCursos());

        idCurso.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
            if (novo != null) {
                mostrarAlunosCheck(novo);
                atualizarTabelaAlunosCurso(novo);
            } else {
                observarAlunosCursos.clear();
            }
        });
    }

    @FXML
    private void mostrarAlunosCheck(Curso cursoSelecionado) {
        List<Aluno> todosAlunos = alunoService.buscarAlunos();

        List<Aluno> alunosDisponiveis = todosAlunos.stream()
            .filter(aluno -> aluno.getCurso() == null || !aluno.getCurso().equals(cursoSelecionado))
            .toList();

        idAluno.setItems(FXCollections.observableArrayList(alunosDisponiveis));
        idAluno.getSelectionModel().clearSelection();
    }

    private void atualizarTabelaAlunosCurso(Curso curso) {
        if (curso != null && curso.getAlunos() != null) {
            observarAlunosCursos.setAll(curso.getAlunos());
        } else {
            observarAlunosCursos.clear();
        }
    }

    @FXML
    private void cadastrarAlunoCurso() {
        Aluno aluno = idAluno.getValue();
        Curso curso = idCurso.getValue();

        if (aluno == null || curso == null) {
            mostrarAlerta(AlertType.WARNING, "Selecione um aluno e um curso para realizar o cadastro.");
            return;
        }

        if (aluno.getCurso() != null && !aluno.getCurso().equals(curso)) {
            mostrarAlerta(AlertType.ERROR, "O aluno " + aluno.getNome() + " já está matriculado no curso " +
                    aluno.getCurso().getNome() + ". Um aluno só pode estar em um único curso.");
            return;
        }

        aluno.setCurso(curso);
        aluno.setUsuarioAtualizacao(usuario);
        alunoService.atualizarAluno(aluno, usuario); 

        if (!curso.getAlunos().contains(aluno)) {
            curso.getAlunos().add(aluno);
        }

        curso.setUsuarioAtualizacao(usuario);
        cursoService.atualizarCurso(curso, usuario); 

        if (!observarAlunosCursos.contains(aluno)) {
            observarAlunosCursos.add(aluno);
        }

        mostrarAlunosCheck(curso);
        mostrarAlerta(AlertType.INFORMATION, "Aluno " + aluno.getNome() + " adicionado ao curso " + curso.getNome());
    }

    @FXML
    private void removerAluno() {
        Aluno selecionado = idTabela.getSelectionModel().getSelectedItem();
        Curso cursoSelecionado = idCurso.getValue();

        if (selecionado != null && cursoSelecionado != null) {
            Curso curso = selecionado.getCurso();

            if (curso != null) {
                curso.getAlunos().remove(selecionado);
                curso.setUsuarioAtualizacao(usuario);
                cursoService.atualizarCurso(curso, usuario); 

                selecionado.setCurso(null);
                selecionado.setUsuarioAtualizacao(usuario);
                alunoService.atualizarAluno(selecionado, usuario); 
            }

            observarAlunosCursos.remove(selecionado);
            idAluno.getSelectionModel().clearSelection();
            mostrarAlunosCheck(cursoSelecionado);

            mostrarAlerta(AlertType.INFORMATION, "Aluno " + selecionado.getNome() + " removido do curso.");
        } else {
            mostrarAlerta(AlertType.WARNING, "Selecione um aluno para remover.");
        }
    }

    private void mostrarAlerta(AlertType tipo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setContentText(mensagem);
        alert.show();
    }
}
