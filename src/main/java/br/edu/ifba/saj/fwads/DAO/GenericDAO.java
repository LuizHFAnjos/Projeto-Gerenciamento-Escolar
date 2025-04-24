package br.edu.ifba.saj.fwads.DAO;

import java.util.List;

import br.edu.ifba.saj.fwads.model.AbstractModel;
import br.edu.ifba.saj.fwads.model.Usuario;

public interface GenericDAO<T extends AbstractModel<ID>, ID> {
    ID salvar(T entidade, Usuario usuario);
    void atualizar(T entidade, Usuario usuario);
    T buscarPorId(ID id);
    void deletar(ID id, Usuario usuario);
    List<T> buscarTodos();
}
