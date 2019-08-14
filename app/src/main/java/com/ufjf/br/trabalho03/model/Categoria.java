package com.ufjf.br.trabalho03.model;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Categoria implements Serializable {
    private Integer idCategoria;
    private String descricao;
    private List<Candidato> candidatos;



    public Categoria(Integer idCategoria, String descricao, List<Candidato> candidatos) {
        this.idCategoria = idCategoria;
        this.descricao = descricao;
        this.candidatos = candidatos;
    }

    public Categoria(Integer idCategoria, String descricao) {
        this.idCategoria = idCategoria;
        this.descricao = descricao;
    }

    public Categoria() {
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public Categoria setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
        return this;
    }

    public String getDescricao() {
        return descricao;
    }

    public Categoria setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public List<Candidato> getCandidatos() {
        return candidatos;
    }

    public Categoria setCandidatos(List<Candidato> candidatos) {
        this.candidatos = candidatos;
        return this;
    }

    public String makeDescription(){
        return String.format(Locale.getDefault(),
                "Id: %d \n" +
                        "Título: %s \n" ,
                this.getIdCategoria(),
                this.getDescricao());


    }

    @Override
    public String toString() {
        return  descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Categoria categoria = (Categoria) o;

        return Objects.equals(idCategoria, categoria.idCategoria);
    }

}
