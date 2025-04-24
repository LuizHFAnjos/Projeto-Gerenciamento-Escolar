package br.edu.ifba.saj.fwads.negocio;

import java.time.LocalDate;
import java.util.List;

import br.edu.ifba.saj.fwads.DAO.ProfessorDAO;
import br.edu.ifba.saj.fwads.exception.VerificarCampos;
import br.edu.ifba.saj.fwads.model.Professor;
import br.edu.ifba.saj.fwads.model.Usuario;

public class ProfessorService {

    private static final ProfessorDAO professorDao = ProfessorDAO.getInstance();

    public void adicionarProfessor(Professor professor, Usuario usuario) throws VerificarCampos {
        vericarCamposProfessor(professor);
        professor.setUsuarioCadastro(usuario);
        professorDao.salvar(professor, usuario);
    }

    public static void vericarCamposProfessor(Professor professor) throws VerificarCampos {
        if (professor.getProfessorNome() == null || professor.getProfessorNome().isEmpty() ||
                professor.getIdentificacao() == null ||
                professor.getCPF() == null || professor.getCPF().isEmpty() ||
                professor.getTempoCarreira() == null ||
                professor.getDataNascimento() == null) {

            throw new VerificarCampos("Todos os campos devem ser preenchidos!");
        }

        if (!ValidarCPF.validarFormatoCpf(professor.getCPF())) {
            throw new VerificarCampos("CPF inválido. Deve conter 11 dígitos numéricos.");
        }

        professor.setCPF(ValidarCPF.formatarCpf(professor.getCPF()));

        if (professor.getDataNascimento().isAfter(LocalDate.now().minusYears(18)) ||
                professor.getDataNascimento().isBefore(LocalDate.now().minusYears(85))) {
            throw new VerificarCampos("O Professor adicionado deve ter entre 18 e 85 anos.");
        }

        List<Professor> professores = professorDao.buscarTodos();

        if (ValidadorUnico.valorJaExiste(professor.getCPF(), professores, Professor::getCPF)) {
            throw new VerificarCampos("CPF já cadastrado no sistema!");
        }

        if (ValidadorUnico.valorJaExiste(String.valueOf(professor.getIdentificacao()), professores,
                p -> String.valueOf(p.getIdentificacao()))) {
            throw new VerificarCampos("Indentificação já cadastrada no sistema!");
        }
    }

    public List<Professor> buscarProfessores() {
        return professorDao.buscarOrdenadosPorNome();
    }

    public void deletar(Professor professor, Usuario usuario) {
        professorDao.deletar(professor.getId(), usuario);
    }
}
