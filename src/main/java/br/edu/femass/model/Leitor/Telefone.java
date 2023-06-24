package br.edu.femass.model.Leitor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Telefone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    private String ddd;
    private String numero;

    public Telefone(String ddd, String numero){
        this.ddd = ddd;
        this.numero = numero;
    }
}
