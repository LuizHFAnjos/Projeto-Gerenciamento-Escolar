package br.edu.ifba.saj.fwads.model;

import java.util.ArrayList;
import java.util.UUID;

public class Turma extends AbstractModel<UUID> {
    private String nome;
    private Curso curso;
    private ArrayList<Professor> professores;
    private ArrayList<Aluno> alunos;

    public Turma(String nome, Curso curso, ArrayList<Professor> professores){
        this.nome = nome;
        this.curso = curso;
        this.professores = new ArrayList<>(professores);
        this.alunos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Curso getCurso() {
        return curso;
    }
    public void setCurso(Curso curso) {
        this.curso = curso;
    }
    public ArrayList<Professor> getProfessores(){
        return professores;
    }
    public void setProfessores(ArrayList<Professor> professores) {
        this.professores = professores;
    }
    public ArrayList<Aluno> getAlunos() {
        return alunos;
    }
    public void setAlunos(ArrayList<Aluno> alunos) {
        this.alunos = alunos;
    }


    public String getProfessoresNome() {
        StringBuilder nomesProfessores = new StringBuilder();
        for (Professor professor : professores) {
            nomesProfessores.append(professor.getProfessorNome()).append(", ");
        }
        if (nomesProfessores.length() > 0) {
            nomesProfessores.setLength(nomesProfessores.length() - 2);
        }
        return nomesProfessores.toString();
    }


    public String getAlunosNome() {
        StringBuilder nomesAlunos = new StringBuilder();     //FUNCIONA DA MESMA FORMA QUE O getProfessorNome, POIS, NA LISTA DE TURMAS
        for (Aluno aluno : alunos) {                        // EU COLOCO OS NOMES NA LISTA SEPARADOS POR VIRGULAS, PARA MOSTRAR OS ALUNOS E OS PROFESSORES.
            nomesAlunos.append(aluno.getNome()).append(", ");
        }
        if (nomesAlunos.length() > 0) {
            nomesAlunos.setLength(nomesAlunos.length() - 2);  
        }
        return nomesAlunos.toString();
    }

    
    @Override
    public String toString() {
        return "Turma [nome=" + nome + ", curso=" + curso + ", alunos=" + alunos + "]";
    }
}