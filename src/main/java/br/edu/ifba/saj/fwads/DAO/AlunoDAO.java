package br.edu.ifba.saj.fwads.DAO;


import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import br.edu.ifba.saj.fwads.model.Aluno;

public class AlunoDAO extends GenericDAOImpl<Aluno, UUID>{
    private static AlunoDAO instance;

    public AlunoDAO() {
        super(UUID.class);
    }

    public static AlunoDAO getInstance() { 
        if (instance == null) {
            instance = new AlunoDAO();
        }
        return instance;
    }

    public List<Aluno> buscarOrdenadosPorNome() {
        return buscarTodos()
                .stream()
                .sorted(Comparator.comparing(Aluno::getNome))
                .collect(Collectors.toList());
    }

    

}
