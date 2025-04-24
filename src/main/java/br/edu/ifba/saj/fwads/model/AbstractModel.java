package br.edu.ifba.saj.fwads.model;

import java.time.LocalDateTime;

public abstract class AbstractModel<T> {
    private T id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Usuario usuarioCadastro;
    private Usuario usuarioAtualizacao;
   

    public T getId() {
        return id;
    }
    public void setId(T id) {
        this.id = id;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public Usuario getUsuarioCadastro() {
        return usuarioCadastro;
    }
    public void setUsuarioCadastro(Usuario usuarioCadastro) {
        this.usuarioCadastro = usuarioCadastro;
    }
    public Usuario getUsuarioAtualizacao() {
        return usuarioAtualizacao;
    }
    public void setUsuarioAtualizacao(Usuario usuarioAtualizacao) {
        this.usuarioAtualizacao = usuarioAtualizacao;
    }
    public String getClassName() {
        return this.getClass().getSimpleName();
    }
    

    


    

    
}
