package br.edu.femass.model.Leitor;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Aluno extends Leitor{
    private String matricula;

    public Aluno(String nome, String telefone, String email, String matricula) {
        super(nome, telefone, email);
        this.matricula = matricula;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((matricula == null) ? 0 : matricula.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Aluno other = (Aluno) obj;
        if (matricula == null) {
            if (other.matricula != null)
                return false;
        } else if (!matricula.equals(other.matricula))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Aluno [Nome=" + nome + "[matricula=" + matricula + "]";
    }

    
}
