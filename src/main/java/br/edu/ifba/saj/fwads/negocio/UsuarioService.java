package br.edu.ifba.saj.fwads.negocio;

import br.edu.ifba.saj.fwads.model.Usuario;

public class UsuarioService {

    private static Usuario usuarioLogado;

    public static void setUsuarioLogado(Usuario usuario) {
        usuarioLogado = usuario;
    }

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
    




}
