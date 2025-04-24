package br.edu.ifba.saj.fwads.negocio;

import java.time.LocalDate;
import java.util.List;

import br.edu.ifba.saj.fwads.DAO.AlunoDAO;
import br.edu.ifba.saj.fwads.exception.VerificarCampos;
import br.edu.ifba.saj.fwads.model.Aluno;
import br.edu.ifba.saj.fwads.model.Usuario;

public class AlunoService {

    private static final AlunoDAO alunoDao = AlunoDAO.getInstance();

    public void adicionarAluno(Aluno aluno, Usuario usuario) throws VerificarCampos {
        verificarCamposAlunos(aluno);
        aluno.setUsuarioCadastro(usuario);
        alunoDao.salvar(aluno, usuario);
    }

    public static void verificarCamposAlunos(Aluno aluno) throws VerificarCampos {
        if (aluno.getNome() == null || aluno.getNome().isEmpty() ||
                aluno.getEmail() == null || aluno.getEmail().isEmpty() ||
                aluno.getCPF() == null || aluno.getCPF().isEmpty() ||
                aluno.getMatricula() == null || aluno.getMatricula().isEmpty() ||
                aluno.getDataNascimento() == null) {

            throw new VerificarCampos("Todos os campos devem ser preenchidos!");
        }

        if (!ValidarCPF.validarFormatoCpf(aluno.getCPF())) {
            throw new VerificarCampos("CPF inválido. Deve conter 11 dígitos numéricos.");
        }

        aluno.setCPF(ValidarCPF.formatarCpf(aluno.getCPF()));

        if (aluno.getDataNascimento().isAfter(LocalDate.now().minusYears(3)) ||
                aluno.getDataNascimento().isBefore(LocalDate.now().minusYears(80))) {
            throw new VerificarCampos("O aluno deve ter entre 3 e 80 anos de idade.");
        }

        if (!aluno.getEmail().contains("@")) {
            throw new VerificarCampos("E-mail inválido. Por favor, adicione um e-mail correto.");
        }

        List<Aluno> alunos = alunoDao.buscarTodos();
        if (ValidadorUnico.valorJaExiste(aluno.getCPF(), alunos, Aluno::getCPF)) {
            throw new VerificarCampos("CPF já cadastrado no sistema!");
        }
        if (ValidadorUnico.valorJaExiste(aluno.getMatricula(), alunos, Aluno::getMatricula)) {
            throw new VerificarCampos("Matrícula já cadastrada no sistema!");
        }
    }

    public List<Aluno> buscarAlunos() {
        return alunoDao.buscarOrdenadosPorNome();
    }

    public void deletar(Aluno aluno, Usuario usuario) {
        alunoDao.deletar(aluno.getId(), usuario);
    }

    public void atualizarAluno(Aluno aluno, Usuario usuario) {
        aluno.setUsuarioAtualizacao(usuario);
        alunoDao.atualizar(aluno, usuario);
    }



}
