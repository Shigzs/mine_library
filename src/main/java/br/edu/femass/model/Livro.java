package br.edu.femass.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Data
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    private String nome;
    private Integer ano;
    private String edicao;
    @OneToOne
    private Genero genero;
    @OneToOne
    private Autor autor;
    private Boolean ativo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "livro")
    private List<Copia> copias = new ArrayList<>();

    public Livro(String nome, Integer ano, String edicao, Genero genero, Autor autor) {
        this.nome = nome;
        this.ano = ano;
        this.edicao = edicao;
        this.genero = genero;
        this.autor = autor;
        this.ativo = true;
    }

    public Boolean addCopia(Copia copia){
        Boolean gravou = this.copias.add(copia);
        return gravou;
    }

    public void removeCopia(Copia copia) throws Exception{
        if(this.copias.size()==1){
            throw new Exception("O Livro deve possuir ao menos 1 cópia");
        }
        this.copias.remove(copia); 
    }
    
    @Override
    public String toString() {
        return "Livro [nome=" + nome + ", ano=" + ano + ", edicao=" + edicao + ", autor=" + autor + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        result = prime * result + ((ano == null) ? 0 : ano.hashCode());
        result = prime * result + ((edicao == null) ? 0 : edicao.hashCode());
        result = prime * result + ((genero == null) ? 0 : genero.hashCode());
        result = prime * result + ((autor == null) ? 0 : autor.hashCode());
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
        Livro other = (Livro) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        if (ano == null) {
            if (other.ano != null)
                return false;
        } else if (!ano.equals(other.ano))
            return false;
        if (edicao == null) {
            if (other.edicao != null)
                return false;
        } else if (!edicao.equals(other.edicao))
            return false;
        if (genero == null) {
            if (other.genero != null)
                return false;
        } else if (!genero.equals(other.genero))
            return false;
        if (autor == null) {
            if (other.autor != null)
                return false;
        } else if (!autor.equals(other.autor))
            return false;
        return true;
    }
}
