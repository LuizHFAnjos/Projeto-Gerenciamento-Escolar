package br.edu.ifba.saj.fwads.DAO;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import br.edu.ifba.saj.fwads.model.AbstractModel;
import br.edu.ifba.saj.fwads.model.Usuario;

public class GenericDAOImpl<T extends AbstractModel<ID>, ID> implements GenericDAO<T, ID> {
    private Map<ID, T> bancoDeDados = new HashMap<>();
    private final Class<ID> tipoIdClass;

    public GenericDAOImpl(Class<ID> tipoIdClass) {
        this.tipoIdClass = tipoIdClass;
    }

    @Override
    public ID salvar(T entidade, Usuario usuario) {
        ID novoId = (ID) UUID.randomUUID();
        entidade.setId(novoId);
        entidade.setCreatedAt(LocalDateTime.now());
        entidade.setUsuarioCadastro(usuario); 
        bancoDeDados.put(novoId, entidade);
        return novoId;
    }

    @Override
    public void atualizar(T entidade, Usuario usuario) {
        entidade.setUpdatedAt(LocalDateTime.now()); 
        entidade.setUsuarioAtualizacao(usuario);
        bancoDeDados.put(entidade.getId(), entidade);
    }

    @Override
    public T buscarPorId(ID id) {
        return bancoDeDados.get(id);
    }

    @Override
    public void deletar(ID id, Usuario usuario) {
        T entidade = bancoDeDados.get(id);
        if (entidade != null) {
            entidade.setUsuarioAtualizacao(usuario); 
            entidade.setUpdatedAt(LocalDateTime.now()); 
            bancoDeDados.remove(id); 
        }
    }

    @Override
    public List<T> buscarTodos() {
        return bancoDeDados.values().stream().collect(Collectors.toList());
    }



    

}