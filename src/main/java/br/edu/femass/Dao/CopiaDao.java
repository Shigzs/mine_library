package br.edu.femass.Dao;

import java.util.List;

import br.edu.femass.model.Livro.Copia;
import br.edu.femass.model.Livro.Livro;
import jakarta.persistence.Query;

public class CopiaDao extends Dao<Copia> {
    public CopiaDao(Class<Copia> entity) {
        super(entity);
    }

    public List<Copia> buscaCopiasDisponiveisPorLivro(Livro livro){
        Query query = em.createQuery("From Copia as c where c.livro=:livro AND c.disponivel=:disponivel");
        query.setParameter("livro", livro);
        query.setParameter("disponivel", true);
        
        return query.getResultList();
    }

    public List<Copia> buscaCopiasPorLivro(Livro livro){
        Query query = em.createQuery("From Copia as c where c.livro=:livro");
        query.setParameter("livro", livro);
        
        return query.getResultList();
    }
}