package br.edu.ifba.saj.fwads.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import br.edu.ifba.saj.fwads.exception.VerificarCampos;
import br.edu.ifba.saj.fwads.model.Aluno;
import br.edu.ifba.saj.fwads.model.Usuario;
import br.edu.ifba.saj.fwads.negocio.AlunoService;
import br.edu.ifba.saj.fwads.negocio.UsuarioService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class CadAlunoController {

    @FXML
    private TextField txNome;
    @FXML
    private TextField txEmail;
    @FXML
    private TextField txCPF;
    @FXML
    private TextField txMatricula;
    @FXML
    private DatePicker dtNascimento;

    @FXML
    private void salvarAluno() {
        try {
            Usuario usuario = UsuarioService.getUsuarioLogado();

            Aluno aluno = new Aluno(
                    txNome.getText(),
                    txEmail.getText(),
                    txCPF.getText(),
                    txMatricula.getText(),
                    dtNascimento.getValue());

            new AlunoService().adicionarAluno(aluno, usuario);

            new Alert(Alert.AlertType.INFORMATION, "Aluno cadastrado com sucesso!").showAndWait();
            limparTela();
            closeWindow();

        } catch (VerificarCampos e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    @FXML
    private void limparTela() {
        txNome.clear();
        txEmail.clear();
        txCPF.clear();
        txMatricula.clear();
        dtNascimento.setValue(null);
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) txNome.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cpfOnKeyTyped(KeyEvent event) {
        if (txCPF.getText().length() >= 11) {
            event.consume();
            txMatricula.requestFocus();
        }
    }

    @FXML //para o datapicker aceitar numeros do teclado
    private void dataNascimentoOnKeyPressed(KeyEvent event) {
        String input = dtNascimento.getEditor().getText();
        
        // Remover caracteres q n são numeros
        String digitsOnly = input.replaceAll("[^\\d]", "");
        
        // Aqui eu limito a 8 dígitos 
        if (digitsOnly.length() > 8) {
            digitsOnly = digitsOnly.substring(0, 8);
        }

        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < digitsOnly.length(); i++) {
            if (i == 2 || i == 4) {
                formatted.append("/");
            }
            formatted.append(digitsOnly.charAt(i));
        }

        dtNascimento.getEditor().setText(formatted.toString());

        if (digitsOnly.length() == 8) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(formatted.toString(), formatter);
                dtNascimento.setValue(date);
            } catch (DateTimeParseException e) {
                new Alert(Alert.AlertType.ERROR, "Data de nascimento inválida!").showAndWait();
                dtNascimento.getEditor().setText("");
                dtNascimento.setValue(null);
            }
        }
    }

}
