package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.exception.VerificarCampos;
import br.edu.ifba.saj.fwads.model.Curso;
import br.edu.ifba.saj.fwads.model.Usuario;
import br.edu.ifba.saj.fwads.negocio.CursoService;
import br.edu.ifba.saj.fwads.negocio.UsuarioService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadCursoController {

    @FXML private TextField txCurso;
    @FXML private TextField txDuracao;

    @FXML
    private void salvarCurso() {
        try {
            Usuario usuario = UsuarioService.getUsuarioLogado();

            int duracao = Integer.parseInt(txDuracao.getText().trim());
            Curso novoCurso = new Curso(txCurso.getText(), duracao);

            new CursoService().adicionarCurso(novoCurso,usuario);

            new Alert(Alert.AlertType.INFORMATION, "Curso cadastrado!").showAndWait();
            limparTela();
            closeWindow();
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Erro: A duração do curso deve ser um número inteiro").showAndWait();
        } catch (VerificarCampos e) {
            new Alert(Alert.AlertType.ERROR, "Erro: " + e.getMessage()).showAndWait();
        }
    }

    @FXML
    private void limparTela() {
        txCurso.clear();
        txDuracao.clear();
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) txCurso.getScene().getWindow();
        stage.close();
    }
}
