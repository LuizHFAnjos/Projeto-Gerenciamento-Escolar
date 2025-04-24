package br.edu.ifba.saj.fwads.negocio;

import java.util.List;

import br.edu.ifba.saj.fwads.DAO.CursoDAO;
import br.edu.ifba.saj.fwads.exception.VerificarCampos;
import br.edu.ifba.saj.fwads.model.Curso;
import br.edu.ifba.saj.fwads.model.Usuario;

public class CursoService {

    private static final CursoDAO cursoDao = CursoDAO.getInstance();

    public void adicionarCurso(Curso curso, Usuario usuario) throws VerificarCampos {
        verificarCamposCurso(curso);
        curso.setUsuarioCadastro(usuario);
        cursoDao.salvar(curso, usuario);
    }

    public static void verificarCamposCurso(Curso curso) throws VerificarCampos {
        if (curso.getNome() == null || curso.getNome().isEmpty() ||
                curso.getDuracao() == null) {

            throw new VerificarCampos("Todos os campos devem ser preenchidos! ");
        }

        if (curso.getDuracao() < 1 || curso.getDuracao() > 10) {
            throw new VerificarCampos("O curso adicionado deve ter entre 1 a 10 anos!");
        }
        List<Curso> cursos = cursoDao.buscarTodos();
        if (ValidadorUnico.valorJaExiste(curso.getNome() + curso.getDuracao(), cursos,
                c -> c.getNome() + c.getDuracao())) {
            throw new VerificarCampos("Esse Curso já está cadastrado!");
        }
    }

    public List<Curso> buscarCursos() {
        return cursoDao.buscarOrdenadosPorNome();
    }

    public void deletar(Curso curso, Usuario usuario) {
        cursoDao.deletar(curso.getId(), usuario);
    }
    public void atualizarCurso(Curso curso, Usuario usuario) {
        curso.setUsuarioAtualizacao(usuario);
        cursoDao.atualizar(curso, usuario);
    }

}
