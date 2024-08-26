package com.example.model;

import javax.persistence.*;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "posts")
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String texto;
    private Date dataPublicacao;
    private Boolean compartilhado;

    @ManyToMany
    @JoinTable(name = "post_autores", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "autor_id"))
    private Set<Autor> autores = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(Date dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public Boolean getCompartilhado() {
        return compartilhado;
    }

    public void setCompartilhado(Boolean compartilhado) {
        this.compartilhado = compartilhado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id) && Objects.equals(titulo, post.titulo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo);
    }
    
}