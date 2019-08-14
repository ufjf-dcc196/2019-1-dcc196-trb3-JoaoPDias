package com.ufjf.br.trabalho03.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Producao implements Serializable {
    private Integer idProducao;
    private String titulo;
    private String descricao;
    private Date inicio;
    private Date fim;
    private Candidato candidato;
    private Categoria categoria;
    private Integer horas;
    private final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    public Categoria getCategoria() {
        return categoria;
    }

    public Producao setCategoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public Producao setCandidato(Candidato candidato) {
        this.candidato = candidato;
        return this;
    }

    private List<Atividade> atividadeList;

    public Producao() {
    }

    public Producao(Integer idProducao, String titulo, String descricao, Date inicio, Date fim, List<Atividade> atividadeList) {
        this.idProducao = idProducao;
        this.titulo = titulo;
        this.descricao = descricao;
        this.inicio = inicio;
        this.fim = fim;
        this.atividadeList = atividadeList;
    }

    public Producao(String titulo, String descricao, Date inicio, Date fim) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.inicio = inicio;
        this.fim = fim;
    }

    public Producao(Integer idProducao, String titulo, String descricao, Date inicio, Date fim) {
        this.idProducao = idProducao;
        this.titulo = titulo;
        this.descricao = descricao;
        this.inicio = inicio;
        this.fim = fim;
    }

    public Integer getIdProducao() {
        return idProducao;
    }

    public Producao setIdProducao(Integer idProducao) {
        this.idProducao = idProducao;
        return this;
    }

    public String getTitulo() {
        return titulo;
    }

    public Producao setTitulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public String getDescricao() {
        return descricao;
    }

    public Producao setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public String getStringDataInicio() {
        return format.format(inicio);
    }

    public Producao setInicio(String inicio) throws ParseException {
        this.inicio = format.parse(inicio);
        return this;
    }

    public String getStringDataFim() {
        return format.format(fim);
    }

    public Producao setFim(String fim) throws ParseException {
        this.fim = format.parse(fim);
        return this;
    }

    public List<Atividade> getAtividadeList() {
        return atividadeList;
    }

    public Producao setAtividadeList(List<Atividade> atividadeList) {
        this.atividadeList = atividadeList;
        return this;
    }

    public Integer getHoras() {
        return horas;
    }

    public Producao setHoras(Integer horas) {
        this.horas = horas;
        return this;
    }

    public String makeDescription() {
        return String.format(Locale.getDefault(),
                "Id: %d \n" +
                        "Título: %s \n" +
                        "Descrição: %s \n" +
                        "Data de Início: %s\n" +
                        "Data de Fim: %s\n" +
                        "Categoria: %s\n" +
                        "Horas: %d",
                this.getIdProducao(),
                this.getTitulo(),
                this.getDescricao(),
                this.getStringDataInicio(),
                this.getStringDataFim(),
                this.getCategoria().getDescricao(),
                this.getHoras());
    }


    public Calendar getCalendarInicio() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.inicio);
        return cal;
    }

    public Calendar getCalendarFim() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.fim);
        return cal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producao producao = (Producao) o;
        return Objects.equals(idProducao, producao.idProducao);
    }

}
