package com.ufjf.br.trabalho03.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Candidato implements Serializable {

    private Integer idCandidato;
    private String nomeCandidato;
    private Date dataNascimento;
    private String perfil;
    private String telefone;
    private String email;
    private List<Producao> producaoList;
    private final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    private Integer totalHoras;

    public Candidato() {
    }

    public Candidato(String nomeCandidato, Date dataNascimento, String perfil, String telefone, String email) {
        this.nomeCandidato = nomeCandidato;
        this.dataNascimento = dataNascimento;
        this.perfil = perfil;
        this.telefone = telefone;
        this.email = email;
    }

    public Candidato(Integer idCandidato, String nomeCandidato, String dataNascimento, String perfil, String telefone, String email) throws ParseException {
        this.idCandidato = idCandidato;
        this.nomeCandidato = nomeCandidato;
        this.dataNascimento = format.parse(dataNascimento);
        this.perfil = perfil;
        this.telefone = telefone;
        this.email = email;
    }

    public Candidato(Integer idCandidato, String nomeCandidato, Date dataNascimento, String perfil, String telefone, String email, List<Producao> producaoList) {
        this.idCandidato = idCandidato;
        this.nomeCandidato = nomeCandidato;
        this.dataNascimento = dataNascimento;
        this.perfil = perfil;
        this.telefone = telefone;
        this.email = email;
        this.producaoList = producaoList;
    }

    public Integer getTotalHoras() {
        return totalHoras;
    }

    public Candidato setTotalHoras(Integer totalHoras) {
        this.totalHoras = totalHoras;
        return this;
    }

    public Integer getIdCandidato() {
        return idCandidato;
    }

    public Candidato setIdCandidato(Integer idCandidato) {
        this.idCandidato = idCandidato;
        return this;
    }

    public String getNomeCandidato() {
        return nomeCandidato;
    }

    public Candidato setNomeCandidato(String nomeCandidato) {
        this.nomeCandidato = nomeCandidato;
        return this;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public String getStringDataNascimento() {
        return format.format(this.dataNascimento);
    }

    public Candidato setDataNascimento(String dataNascimento) throws ParseException {
        this.dataNascimento = format.parse(dataNascimento);
        return this;
    }

    public String getPerfil() {
        return perfil;
    }

    public Candidato setPerfil(String perfil) {
        this.perfil = perfil;
        return this;
    }

    public String getTelefone() {
        return telefone;
    }

    public Candidato setTelefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Candidato setEmail(String email) {
        this.email = email;
        return this;
    }

    public List<Producao> getProducaoList() {
        return producaoList;
    }

    public Candidato setProducaoList(List<Producao> producaoList) {
        this.producaoList = producaoList;
        return this;
    }

    public String makeDescription() {
        return String.format(Locale.getDefault(),
                "Id: %d \n" +
                        "Título: %s \n" +
                        "Data de Nascimento: %s \n" +
                        "E-mail: %s\n" +
                        "Total de Horas de Produção: %d",
                this.getIdCandidato(),
                this.getNomeCandidato(),
                this.getStringDataNascimento(), this.getEmail(),this.getTotalHoras());
    }

    public Calendar getCalendar(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.dataNascimento);
        return cal;
    }
}
