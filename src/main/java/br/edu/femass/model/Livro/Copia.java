package br.edu.femass.model.Livro;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Copia{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    private boolean disponivel = true;
    @ManyToOne
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;
    
    public Copia(Livro livro){
        this.livro = livro;
    }

    @Override
    public String toString() {
        return "Copia [id=" + id + "]";
    }
}
