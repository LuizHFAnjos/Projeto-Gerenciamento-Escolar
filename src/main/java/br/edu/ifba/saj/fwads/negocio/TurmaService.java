package br.edu.ifba.saj.fwads.negocio;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import br.edu.ifba.saj.fwads.DAO.TurmaDAO;
import br.edu.ifba.saj.fwads.model.Turma;
import br.edu.ifba.saj.fwads.model.Usuario;

public class TurmaService {

    private static final List<Turma> listaTurmas = new ArrayList<>();
    private static final TurmaDAO turmaDAO = TurmaDAO.getInstance();

    public void adicionarTurma(Turma turma, Usuario usuario) {
        if (buscarPorNome(turma.getNome()) == null) {
            turma.setUsuarioCadastro(usuario);
            turmaDAO.salvar(turma, usuario);
            listaTurmas.add(turma);
        }
    }

    public List<Turma> buscarTurmas() {
        List<Turma> copiaOrdenada = new ArrayList<>(listaTurmas);
        copiaOrdenada.sort(Comparator.comparing(Turma::getNome));
        return copiaOrdenada;
    }

    public Turma buscarPorNome(String nome) {
        for (Turma turma : listaTurmas) {
            if (turma.getNome().equalsIgnoreCase(nome)) {
                return turma;
            }
        }
        return null;
    }

    public boolean removerTurma(Turma turma, Usuario usuario) {
        return listaTurmas.remove(turma);
    }

    public void limparTurmas() {
        listaTurmas.clear();
    }

    public void atualizarTurma(Turma turma, Usuario usuario) {
        Turma turmaExistente = buscarPorNome(turma.getNome());
        if (turmaExistente != null) {
            turmaExistente.setProfessores(turma.getProfessores());
            turmaExistente.setAlunos(turma.getAlunos());
            turmaExistente.setUsuarioAtualizacao(usuario);
            turmaDAO.atualizar(turmaExistente, usuario); 
        }
    }

}
