package br.edu.femass.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class Professor extends Leitor{ 
    private String formacao;

    public Professor(String nome, String telefone, String email, String formacao) {
        super(nome, telefone, email);
        this.formacao = formacao;
    }

    @Override
    public String toString() {
        return "Professor [nome=" + nome + "formacao=" + formacao + "]";
    }
    
    @Override
    public int getDiasDeEmprestimo(){
        return 30;
    }
}
