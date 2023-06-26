package br.edu.femass.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class Aluno extends Leitor{
    private String matricula;

    public Aluno(String nome, String telefone, String email, String matricula) {
        super(nome, telefone, email);
        this.matricula = matricula;
    }

    @Override
    public String toString() {
        return "Aluno [Nome=" + nome + "[matricula=" + matricula + "]";
    }

    
}
