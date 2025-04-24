package br.edu.ifba.saj.fwads.controller;

import java.time.LocalDateTime;
import java.util.List;

import br.edu.ifba.saj.fwads.model.AbstractModel;
import br.edu.ifba.saj.fwads.model.Aluno;
import br.edu.ifba.saj.fwads.model.Curso;
import br.edu.ifba.saj.fwads.model.Professor;
import br.edu.ifba.saj.fwads.model.Turma;
import br.edu.ifba.saj.fwads.model.Usuario;
import br.edu.ifba.saj.fwads.negocio.AuditoriaService;
import br.edu.ifba.saj.fwads.negocio.ValidaUsuario;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.StringConverter;

public class AuditoriaController {

    @FXML
    private ChoiceBox<Usuario> chbUsuarios;

    @FXML
    private TableView<AbstractModel<?>> tbvAtualizadas;

    @FXML
    private TableColumn<AbstractModel<?>, LocalDateTime> clnAtualizadaData;

    @FXML
    private TableColumn<AbstractModel<?>, String> clnAtualizadaNome;

    @FXML
    private TableView<AbstractModel<?>> tbvInseridas;

    @FXML
    private TableColumn<AbstractModel<?>, LocalDateTime> clnInseridaData;

    @FXML
    private TableColumn<AbstractModel<?>, String> clnTipoAtualizacao;

    @FXML
    private TableColumn<AbstractModel<?>, String> clnInseridaNome;

    private List<Usuario> listaUsuario;
    private final AuditoriaService auditoriaService = new AuditoriaService();

    @FXML
    void sair(ActionEvent event) {
        
    }

    void usuarioSelecionado() {
        Usuario usuarioSelecionado = chbUsuarios.getSelectionModel().getSelectedItem();
        if (usuarioSelecionado == null) return;

        new Alert(AlertType.INFORMATION, "Usuário Selecionado: " + usuarioSelecionado.getLogin()).showAndWait();

        List<AbstractModel<?>> inseridos = auditoriaService.entidadesCadastradas(usuarioSelecionado);
        List<AbstractModel<?>> atualizados = auditoriaService.entidadesAtualizadas(usuarioSelecionado);

        tbvInseridas.setItems(FXCollections.observableArrayList(inseridos));
        tbvAtualizadas.setItems(FXCollections.observableArrayList(atualizados));
    }

    @FXML
    public void initialize() {
        chbUsuarios.setConverter(new StringConverter<Usuario>() {
            @Override
            public String toString(Usuario object) {
                return (object != null) ? object.getLogin() + ":" + object.getEmail() : "";
            }

            @Override
            public Usuario fromString(String string) {
                return listaUsuario.stream()
                    .filter(u -> string.equals(u.getLogin() + ":" + u.getEmail()))
                    .findAny()
                    .orElse(null);
            }
        });

        chbUsuarios.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) usuarioSelecionado();
        });

        carregarListaUsuario();

        
        clnInseridaNome.setCellValueFactory(c -> {
            AbstractModel<?> entity = c.getValue();
            return new SimpleStringProperty(formatNome(entity));
        });
        clnInseridaData.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getCreatedAt()));

        
        clnAtualizadaNome.setCellValueFactory(c -> {
            AbstractModel<?> entity = c.getValue();
            return new SimpleStringProperty(formatNome(entity));
        });
        clnAtualizadaData.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getUpdatedAt()));

        
        clnTipoAtualizacao.setCellValueFactory(c -> {
            AbstractModel<?> entity = c.getValue();
            String tipo = switch (entity.getClassName()) {
                case "Turma" -> "Editada";
                case "Curso" -> "Aluno Adicionado";
                case "Aluno" -> "Curso Adicionado";
                default -> "Atualizado";
            };
            return new SimpleStringProperty(tipo);
        });
    }

    private void carregarListaUsuario() {
        ValidaUsuario validaUsuario = new ValidaUsuario();
        listaUsuario = validaUsuario.getListUsuarios();
        chbUsuarios.setItems(FXCollections.observableArrayList(listaUsuario));
    }

    private String formatNome(AbstractModel<?> entity) {
        String className = entity.getClassName();
        if (className.equals("Aluno")) {
            return "Aluno: " + ((Aluno) entity).getNome();
        } else if (className.equals("Professor")) {
            return "Professor: " + ((Professor) entity).getProfessorNome();
        } else if (className.equals("Curso")) {
            return "Curso: " + ((Curso) entity).getNome();
        } else if (className.equals("Turma")) {
            return "Turma: " + ((Turma) entity).getNome();
        }
        return "";
    }
}
