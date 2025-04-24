package br.edu.ifba.saj.fwads.negocio;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.edu.ifba.saj.fwads.DAO.AlunoDAO;
import br.edu.ifba.saj.fwads.DAO.CursoDAO;
import br.edu.ifba.saj.fwads.DAO.ProfessorDAO;
import br.edu.ifba.saj.fwads.DAO.TurmaDAO;
import br.edu.ifba.saj.fwads.model.AbstractModel;
import br.edu.ifba.saj.fwads.model.Usuario;

public class AuditoriaService {

    public List<AbstractModel<?>> entidadesCadastradas(Usuario usuarioSelecionado) {
        return entidades(usuarioSelecionado, true);
    }

    public List<AbstractModel<?>> entidadesAtualizadas(Usuario usuarioSelecionado) {
        return entidades(usuarioSelecionado, false);
    }

    private List<AbstractModel<?>> entidades(Usuario usuario, boolean cadastro) {
        return Stream.of(
                AlunoDAO.getInstance().buscarTodos(),
                ProfessorDAO.getInstance().buscarTodos(),
                CursoDAO.getInstance().buscarTodos(),
                TurmaDAO.getInstance().buscarTodos()
            )
            .flatMap(List::stream)
            .filter(e -> cadastro
                ? usuario.equals(e.getUsuarioCadastro())
                : usuario.equals(e.getUsuarioAtualizacao()))
            .collect(Collectors.toList());
    }
}
