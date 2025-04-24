/**
 * Sample Skeleton for 'Login.fxml' Controller Class
 */

package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.exception.LoginInvalidoException;
import br.edu.ifba.saj.fwads.model.Usuario;
import br.edu.ifba.saj.fwads.negocio.UsuarioService;
import br.edu.ifba.saj.fwads.negocio.ValidaUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML 
    private PasswordField txSenha; 

    @FXML 
    private TextField txUsuario; 
    @FXML
    void entrar(ActionEvent event) {
        ValidaUsuario validaUsuario = new ValidaUsuario();
        try {
            Usuario usuario = validaUsuario.validaLogin(txUsuario.getText(), txSenha.getText());
            UsuarioService.setUsuarioLogado(usuario);
            App.setRoot("controller/Master.fxml");
            MasterController controller =  (MasterController)App.getController();
            controller.setEmail(usuario.getEmail());
        } catch (LoginInvalidoException e) {
            new Alert(AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    @FXML
    void limparCampos(ActionEvent event) {
        txUsuario.setText("");
        txSenha.setText("");
    }

}
