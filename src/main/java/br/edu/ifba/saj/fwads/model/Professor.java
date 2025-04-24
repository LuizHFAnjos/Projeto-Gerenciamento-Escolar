package br.edu.ifba.saj.fwads.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class Professor extends AbstractModel<UUID> {
    private String professorNome; 
    private Integer identificacao; 
    private String CPF;
    private Integer tempoCarreira; 
    private LocalDate dataNascimento;
    private ArrayList<Turma> turmas;

    public Professor(String professorNome, Integer identificacao, String cPF, Integer tempoCarreira, LocalDate dataNascimento) {
        this.professorNome = professorNome;
        this.identificacao = identificacao;
        CPF = cPF;
        this.tempoCarreira = tempoCarreira;
        this.dataNascimento = dataNascimento;
        this.turmas = new ArrayList<>();
    }

    public String getProfessorNome() {
        return professorNome;
    }
    public void setProfessor(String professor) {
        this.professorNome = professor;
    } 
    public Integer getIdentificacao() {
        return identificacao;
    }
    public void setIdentificacao(Integer identificacao) {
        this.identificacao = identificacao;
    }
    public String getCPF() {
        return CPF;
    }
    public void setCPF(String cPF) {
        this.CPF = cPF;
    }
    public Integer getTempoCarreira() {
        return tempoCarreira;
    }
    public void setTempoCarreira(Integer tempoCarreira) {
        this.tempoCarreira = tempoCarreira;
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
        return "Nome: "+ professorNome + " - Identificação "+ identificacao;  // EU ESTOU RETORNANDO APENAS O NOME E IDENTIFICAÇÃO POR QUESTÃO DE ORGANIZAÇÃO NO MOMENTO EM QUE VOU SELECIONAR NO CHECKBOX
    }                                                                     // PARA APARECER SOMENTE O NOME E IDENTIFICAÇÃO E NAO TODAS AS INFORMAÇÕES

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((identificacao == null) ? 0 : identificacao.hashCode());
        result = prime * result + ((CPF == null) ? 0 : CPF.hashCode());
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
        Professor other = (Professor) obj;
        if (identificacao == null) {
            if (other.identificacao != null)
                return false;
        } else if (!identificacao.equals(other.identificacao))
            return false;
        if (CPF == null) {
            if (other.CPF != null)
                return false;
        } else if (!CPF.equals(other.CPF))
            return false;
        return true;
    }
        
    
    

    



    


}