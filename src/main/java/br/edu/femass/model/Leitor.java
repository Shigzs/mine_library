package br.edu.femass.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public abstract class Leitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String nome;
    protected String telefone;
    protected String email;
    protected Boolean ativo;
    @OneToMany
    protected List<Emprestimo> emprestimos;

    public Leitor(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.ativo = true;
    }

    public int getDiasDeEmprestimo(){
        return 5;
    }
}
