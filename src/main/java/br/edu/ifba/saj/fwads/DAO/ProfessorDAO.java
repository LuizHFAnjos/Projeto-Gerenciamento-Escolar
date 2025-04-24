package br.edu.ifba.saj.fwads.DAO;


import br.edu.ifba.saj.fwads.model.Professor;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProfessorDAO extends GenericDAOImpl<Professor, UUID> {
    private static ProfessorDAO instance;
    public ProfessorDAO() {
        super(UUID.class);
    }

    public static ProfessorDAO getInstance() { 
        if (instance == null) {
            instance = new ProfessorDAO();
        }
        return instance;
    }

    
    public List<Professor> buscarOrdenadosPorNome() {
        return buscarTodos()
                .stream()
                .sorted(Comparator.comparing(Professor::getProfessorNome))
                .collect(Collectors.toList());
    }

}
