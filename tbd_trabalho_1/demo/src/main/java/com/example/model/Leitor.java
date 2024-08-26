package com.example.model;

import java.util.Objects;

import javax.persistence.*;

@Entity
@DiscriminatorValue("leitor")
public class Leitor extends Autor {
    @OneToOne(cascade = CascadeType.ALL)
    //cascadetypeall especifica que todas as operações que ocorrerem no leitor tambem ocorrerao nos enderecos
    //ex: se o leitor for removido o endereco tambem sera
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    //dizendo ao jpa para criar uma coluna endereco_id para representar o relacionamento (fk)
    private Endereco endereco;
    // getters and setters

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Leitor)) return false;
        if (!super.equals(o)) return false;
        Leitor leitor = (Leitor) o;
        return Objects.equals(endereco, leitor.endereco);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), endereco);
    }
}
