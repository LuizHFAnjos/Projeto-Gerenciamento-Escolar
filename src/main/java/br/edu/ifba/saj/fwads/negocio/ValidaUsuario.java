package br.edu.ifba.saj.fwads.negocio;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifba.saj.fwads.exception.LoginInvalidoException;
import br.edu.ifba.saj.fwads.model.Usuario;

public class ValidaUsuario {

    static List<Usuario> listaUsuarios = new ArrayList<>();

    static {
        listaUsuarios.add(new Usuario("admin", "admin", "admin@sistema.com"));
        listaUsuarios.add(new Usuario("henrique", "senha", "admin@sistema.com"));
    }

    public Usuario validaLogin(String login, String senha) throws LoginInvalidoException {

        for (Usuario usuario : listaUsuarios) {
            if (usuario.getLogin().equals(login) && usuario.getSenha().equals(senha)) {
                return usuario;
            }
        }

        throw new LoginInvalidoException("Não foi possível localizar o usuario " + login + ", ou a senha esta errada");

    }

    public List<Usuario> getListUsuarios() {
        return listaUsuarios;
    }

}
