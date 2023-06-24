package br.edu.femass.model.Leitor;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Professor extends Leitor{ 
    private String formacao;

    public Professor(String nome, String telefone, String email, String formacao) {
        super(nome, telefone, email);
        this.formacao = formacao;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((formacao == null) ? 0 : formacao.hashCode());
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
        Professor other = (Professor) obj;
        if (formacao == null) {
            if (other.formacao != null)
                return false;
        } else if (!formacao.equals(other.formacao))
            return false;
        return true;
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
