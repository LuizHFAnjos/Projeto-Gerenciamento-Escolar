package br.edu.ifba.saj.fwads.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import br.edu.ifba.saj.fwads.exception.VerificarCampos;
import br.edu.ifba.saj.fwads.model.Professor;
import br.edu.ifba.saj.fwads.model.Usuario;
import br.edu.ifba.saj.fwads.negocio.ProfessorService;
import br.edu.ifba.saj.fwads.negocio.UsuarioService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class CadProfessorController {

    @FXML private TextField txCPFProfessor;
    @FXML private DatePicker dtNascimento;
    @FXML private TextField txIdentificacao;
    @FXML private TextField txNomeProfessor;
    @FXML private TextField txTempoCarreira;

    @FXML
    private void salvarProfessor() {
        try {
            Usuario usuario = UsuarioService.getUsuarioLogado();

            int identificacao = Integer.parseInt(txIdentificacao.getText().trim());
            int tempoCarreira = Integer.parseInt(txTempoCarreira.getText().trim());

            Professor novoProfessor = new Professor(
                txNomeProfessor.getText().trim(),
                identificacao,
                txCPFProfessor.getText().trim(),
                tempoCarreira,
                dtNascimento.getValue()
            );

            new ProfessorService().adicionarProfessor(novoProfessor,usuario);

            new Alert(Alert.AlertType.INFORMATION, "Professor Cadastrado com Sucesso").showAndWait();
            limparTela();
            closeWindow();

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Erro: Identificação e Tempo de Carreira devem ser números inteiros!").showAndWait();
        } catch (VerificarCampos e) {
            new Alert(Alert.AlertType.ERROR, "Erro: " + e.getMessage()).showAndWait();
        }
    }

    @FXML
    private void limparTela() {
        txNomeProfessor.clear(); 
        txIdentificacao.clear();
        txCPFProfessor.clear();
        txTempoCarreira.clear();
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) txNomeProfessor.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cpfOnKeyTyped(KeyEvent event) {
        if (txCPFProfessor.getText().length() >= 11) {
            event.consume();
            txTempoCarreira.requestFocus();
        }
    }

    @FXML //para o datapicker aceitar numeros do teclado
    private void dataNascimentoOnKeyPressed(KeyEvent event) {
        String input = dtNascimento.getEditor().getText();
        
        // Removo os caracteres que n é numero
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
