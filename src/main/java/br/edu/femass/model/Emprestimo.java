package br.edu.femass.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    private LocalDate data;
    private LocalDate dataPrevistaEntrega;
    private LocalDate dataEntrega;
    @OneToOne
    private Leitor leitor;
    @OneToOne
    private Copia copia;

    @Override
    public String toString() {
        return "Emprestimo [data=" + data + ", dataPrevistaEntrega=" + dataPrevistaEntrega + ", dataEntrega="
                + dataEntrega + ", leitor=" + leitor + ", copia=" + copia + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + ((copia == null) ? 0 : copia.hashCode());
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
        Emprestimo other = (Emprestimo) obj;
        if (data == null) {
            if (other.data != null)
                return false;
        } else if (!data.equals(other.data))
            return false;
        if (copia == null) {
            if (other.copia != null)
                return false;
        } else if (!copia.equals(other.copia))
            return false;
        if (leitor == null) {
            if (other.leitor != null)
                return false;
        } else if (!leitor.equals(other.leitor))
            return false;
        return true;
    }
    
    
}
