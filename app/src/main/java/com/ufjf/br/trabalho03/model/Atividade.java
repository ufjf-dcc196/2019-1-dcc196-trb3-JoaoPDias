package com.ufjf.br.trabalho03.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Atividade implements Serializable {

    private Integer idAtividade;
    private String descricao;
    private Date dataAtividade;
    private Integer horas;
    private Producao producao;
    private final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    public Producao getProducao() {
        return producao;
    }

    public Atividade setProducao(Producao producao) {
        this.producao = producao;
        return this;
    }

    public Integer getIdAtividade() {
        return idAtividade;
    }

    public Atividade setIdAtividade(Integer idAtividade) {
        this.idAtividade = idAtividade;
        return this;
    }

    public String getDescricao() {
        return descricao;
    }

    public Atividade setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public Date getDataAtividade() {
        return dataAtividade;
    }


    public String getStringDataAtividade() {
        return format.format(this.dataAtividade);
    }

    public Atividade setDataAtividade(String dataAtividade) throws ParseException {
        this.dataAtividade = format.parse(dataAtividade);
        return this;
    }

    public Integer getHoras() {
        return horas;
    }

    public Atividade setHoras(Integer horas) {
        this.horas = horas;
        return this;
    }

    public String makeDescription() {
        return String.format(Locale.getDefault(),
                "Id: %d \n" +
                        "Descrição: %s \n" +
                        "Data Atividade: %s\n" +
                        "Producao: %s\n"+
                        "Horas: %d",
                this.getIdAtividade(),
                this.getDescricao(),
                this.getStringDataAtividade(),
                this.getProducao().getTitulo(),
                this.getHoras());
    }

    public Calendar getCalendar(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.dataAtividade);
        return cal;
    }
}
