package br.edu.ifba.saj.fwads.DAO;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import br.edu.ifba.saj.fwads.model.Turma;

public class TurmaDAO extends GenericDAOImpl<Turma, UUID> {
    private static TurmaDAO instance;

    public TurmaDAO() {
        super(UUID.class);
    }

    public static TurmaDAO getInstance() { 
        if (instance == null) {
            instance = new TurmaDAO();
        }
        return instance;
    }

    public List<Turma> buscarOrdenadasPorNome() {
        return buscarTodos()
                .stream()
                .sorted(Comparator.comparing(Turma::getNome))
                .collect(Collectors.toList());
    }

}
