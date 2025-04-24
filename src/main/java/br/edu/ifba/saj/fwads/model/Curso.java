package br.edu.ifba.saj.fwads.model;

import java.util.ArrayList;
import java.util.UUID;

// EXEMPLOS DE CURSOS PODEM SER: ADS, REDES, MULTIMIDIA....

public class Curso extends AbstractModel<UUID> {
    private String nome;
    private Integer duracao; 
    private ArrayList<Aluno> alunos;
    private ArrayList<Turma> turmas;

    public Curso(String nome, Integer duracao) {
        this.nome = nome;
        this.duracao = duracao;
        this.alunos = new ArrayList<>();
        this.turmas = new ArrayList<>();     
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Integer getDuracao() {
        return duracao;
    }
    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }
    public ArrayList<Aluno> getAlunos() {
        return alunos;
    }
    public void setAlunos(ArrayList<Aluno> alunos) {
        this.alunos = alunos;
    }
    public ArrayList<Turma> getTurmas() {
        return turmas;
    }
    public void setTurmas(ArrayList<Turma> turmas) {
        this.turmas = turmas;
    }

    @Override
    public String toString() {
        return  nome; // O toString só retorna o nome por organização. Não achei necessario informa a duração do curso em todos os momentos.
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        result = prime * result + ((duracao == null) ? 0 : duracao.hashCode());
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
        Curso other = (Curso) obj;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        if (duracao == null) {
            if (other.duracao != null)
                return false;
        } else if (!duracao.equals(other.duracao))
            return false;
        return true;
    }

    
    

    


    



    

   
}