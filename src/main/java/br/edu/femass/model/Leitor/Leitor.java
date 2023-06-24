package br.edu.femass.model.Leitor;

import java.util.Set;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
public abstract class Leitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String nome;
    protected String telefone;
    protected String email;
    protected Boolean ativo;
    @OneToMany
    protected Set<Emprestimo> emprestimos;

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
