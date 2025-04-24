package br.edu.ifba.saj.fwads.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;


public class Aluno extends AbstractModel<UUID> {

    private String nome;
    private String email;
    private String CPF;
    private String matricula;
    private Curso curso;
    private LocalDate dataNascimento;
    private ArrayList<Turma> turmas;

    
    public Aluno(String nome, String email, String cPF, String matricula, LocalDate dataNascimento) {
        this.nome = nome;
        this.email = email;
        CPF = cPF;
        this.matricula = matricula;
        this.dataNascimento = dataNascimento;
        this.turmas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getCPF() {
        return CPF;
    }
    public void setCPF(String cPF) {
        CPF = cPF;
    }
    public String getMatricula() {
        return matricula;
    }
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    public Curso getCurso(){
        return curso;
    }
    public void setCurso(Curso curso){
        this.curso = curso;
    }   
    public ArrayList<Turma> getTurmas() {
        return turmas;
    }
    public void setTurmas(ArrayList<Turma> turmas) {
        this.turmas = turmas;
    }
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Override
    public String toString() {
        return "Nome: " + this.nome + " - Matrícula "+ this.matricula ; // EU ESTOU RETORNANDO APENAS O NOME E MATRÍCULA POR QUESTÃO DE ORGANIZAÇÃO NO MOMENTO EM QUE VOU SELECIONAR NO CHECKBOX
    }                                                                   // PARA APARECER SOMENTE O NOME E MATRÍCULA E NAO TODAS AS INFORMAÇÕES

    public String getNomeCurso() {
        return curso != null ? curso.getNome() : "Sem Curso"; //É UTILIZAVEL PARA A PAGINA DIREÇÃO
    }

    public String getNomesTurmas() {  //É UTILIZAVEL PARA A PAGINA DIREÇÃO
        if (turmas == null || turmas.isEmpty()) return "Sem Turmas";
        return turmas.stream()
                     .map(Turma::getNome)
                     .reduce((a, b) -> a + ", " + b)
                     .orElse("Sem Turmas");
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((CPF == null) ? 0 : CPF.hashCode());
        result = prime * result + ((matricula == null) ? 0 : matricula.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Aluno other = (Aluno) obj;
        if (CPF == null) {
            if (other.CPF != null)
                return false;
        } else if (!CPF.equals(other.CPF))
            return false;
        if (matricula == null) {
            if (other.matricula != null)
                return false;
        } else if (!matricula.equals(other.matricula))
            return false;
        return true;
    }


}