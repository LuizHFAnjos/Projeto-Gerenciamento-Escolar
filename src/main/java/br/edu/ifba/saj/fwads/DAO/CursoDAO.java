package br.edu.ifba.saj.fwads.DAO;


import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import br.edu.ifba.saj.fwads.model.Curso;

public class CursoDAO extends GenericDAOImpl<Curso, UUID> {
    private static CursoDAO instance;

    public CursoDAO() {
        super(UUID.class);
    }

    public static CursoDAO getInstance() { 
        if (instance == null) {
            instance = new CursoDAO();
        }
        return instance;
    }

    public List<Curso> buscarOrdenadosPorNome() {
        return buscarTodos()
                .stream()
                .sorted(Comparator.comparing(Curso::getNome))
                .collect(Collectors.toList());
    }

}
