package br.edu.ifba.saj.fwads.DAO;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import br.edu.ifba.saj.fwads.model.Usuario;

public class UsuarioDAO extends GenericDAOImpl<Usuario, UUID>{


    public UsuarioDAO() {
        super(UUID.class);
    }

    
    public List<Usuario> buscarOrdenadosPorNome() {
        return buscarTodos()
                .stream()
                .sorted(Comparator.comparing(Usuario::getLogin))
                .collect(Collectors.toList());
    }

}
