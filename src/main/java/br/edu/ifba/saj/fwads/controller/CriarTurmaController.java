package br.edu.ifba.saj.fwads.controller;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifba.saj.fwads.model.Aluno;
import br.edu.ifba.saj.fwads.model.Curso;
import br.edu.ifba.saj.fwads.model.Professor;
import br.edu.ifba.saj.fwads.model.Turma;
import br.edu.ifba.saj.fwads.negocio.CursoService;
import br.edu.ifba.saj.fwads.negocio.ProfessorService;
import br.edu.ifba.saj.fwads.negocio.TurmaService;
import br.edu.ifba.saj.fwads.negocio.UsuarioService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class CriarTurmaController {

    private Turma turmaParaEdicao;

    @FXML
    private ChoiceBox<Curso> choiceBoxCursos;

    @FXML
    private ListView<Aluno> listViewAlunos;

    @FXML
    private TextField txNomeTurma;

    @FXML
    private ListView<Professor> listViewProfessores;

    private ListTurmasController listTurmasController;

    private final CursoService cursoService = new CursoService();
    private final ProfessorService professorService = new ProfessorService();
    private final TurmaService turmaService = new TurmaService();

    private final ObservableList<Aluno> alunosList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        listViewAlunos.setItems(alunosList);
        listViewAlunos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        carregarCursos();
        carregarProfessores();
    }

    public void setListTurmasController(ListTurmasController controller) {
        this.listTurmasController = controller;
        carregarCursos();
        carregarProfessores();
        alunosList.clear(); 
    }

    private void carregarCursos() {
        List<Curso> cursos = cursoService.buscarCursos();

        if (cursos.isEmpty()) {
            mostrarAlerta("Nenhum curso disponível.");
            return;
        }

        choiceBoxCursos.setItems(FXCollections.observableArrayList(cursos));
        choiceBoxCursos.setConverter(new StringConverter<>() {
            @Override
            public String toString(Curso curso) {
                return curso != null ? curso.getNome() : "";
            }

            @Override
            public Curso fromString(String string) {
                return null;
            }
        });
    }

    private void carregarProfessores() {
        List<Professor> professores = professorService.buscarProfessores();

        if (professores.isEmpty()) {
            mostrarAlerta("Nenhum professor disponível.");
            return;
        }

        listViewProfessores.setItems(FXCollections.observableArrayList(professores));
        listViewProfessores.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void pesquisarAlunos() {
        Curso cursoSelecionado = choiceBoxCursos.getValue();
        alunosList.clear();

        if (cursoSelecionado == null) {
            mostrarAlerta("Nenhum curso selecionado.");
            return;
        }

        List<Curso> cursosAtualizados = cursoService.buscarCursos();
        Curso cursoAtualizado = cursosAtualizados.stream()
            .filter(c -> c.getId().equals(cursoSelecionado.getId()))
            .findFirst()
            .orElse(null);

        if (cursoAtualizado == null) {
            mostrarAlerta("Curso não encontrado.");
            return;
        }

        List<Aluno> alunosDoCurso = cursoAtualizado.getAlunos();

        if (alunosDoCurso != null && !alunosDoCurso.isEmpty()) {
            alunosList.setAll(alunosDoCurso);
        } else {
            mostrarAlerta("O curso selecionado não possui alunos.");
        }
    }

    @FXML
    private void criarNovaTurma() {
        String nomeTurma = txNomeTurma.getText().trim();
        ObservableList<Professor> professoresSelecionados = listViewProfessores.getSelectionModel().getSelectedItems();
        ObservableList<Aluno> alunosSelecionados = listViewAlunos.getSelectionModel().getSelectedItems();

        if (nomeTurma.isEmpty() || professoresSelecionados.isEmpty() || alunosSelecionados.isEmpty()) {
            mostrarAlerta("Erro: preencha todos os campos e selecione pelo menos um professor e um aluno.");
            return;
        }

        if (turmaParaEdicao == null) {
            if (turmaService.buscarPorNome(nomeTurma) != null) {
                mostrarAlerta("Já existe uma turma com esse nome.");
                return;
            }

            Curso cursoSelecionado = alunosSelecionados.get(0).getCurso(); 
            Turma novaTurma = new Turma(nomeTurma, cursoSelecionado, new ArrayList<>(professoresSelecionados));
            novaTurma.getAlunos().addAll(alunosSelecionados);

            for (Aluno aluno : alunosSelecionados) {
                if (!aluno.getTurmas().contains(novaTurma)) {
                    aluno.getTurmas().add(novaTurma);
                }
            }

            turmaService.adicionarTurma(novaTurma, UsuarioService.getUsuarioLogado());

        } else {
            turmaParaEdicao.setProfessores(new ArrayList<>(professoresSelecionados));
            turmaParaEdicao.getAlunos().clear();
            turmaParaEdicao.getAlunos().addAll(alunosSelecionados);

            for (Aluno aluno : alunosSelecionados) {
                if (!aluno.getTurmas().contains(turmaParaEdicao)) {
                    aluno.getTurmas().add(turmaParaEdicao);
                }
            }

            turmaService.atualizarTurma(turmaParaEdicao, UsuarioService.getUsuarioLogado()); // <-- LINHA IMPORTANTE
        }

        if (listTurmasController != null) {
            listTurmasController.atualizarLista();
        }

        fecharJanela();
    }


    public void setTurmaParaEdicao(Turma turma) {
        this.turmaParaEdicao = turma;

        txNomeTurma.setText(turma.getNome());
        txNomeTurma.setDisable(true); // escolhi nao deixar mudar o nome da turma caso for editar

        choiceBoxCursos.setValue(turma.getCurso());

        listViewProfessores.getSelectionModel().clearSelection();
        for (Professor p : turma.getProfessores()) {
            listViewProfessores.getSelectionModel().select(p);
        }

        alunosList.setAll(turma.getCurso().getAlunos()); // carrega todos os alunos do curso
        listViewAlunos.getSelectionModel().clearSelection();
        for (Aluno a : turma.getAlunos()) {
            listViewAlunos.getSelectionModel().select(a);
        }
    }

    private void fecharJanela() {
        Stage stage = (Stage) txNomeTurma.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(mensagem);
        alert.show();
    }
}
