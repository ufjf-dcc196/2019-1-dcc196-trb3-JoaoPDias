package com.ufjf.br.trabalho03.contract;

import android.provider.BaseColumns;


public class HeadHunterContract {

    public HeadHunterContract(){}

    public static final class Categoria implements BaseColumns{

        public static final String TABLE_NAME = "categoria";
        public static final String COLUMN_NAME_DESCRICAO = "descricao";
        public static final String SQL_CREATE_CATEGORIA = String.format("CREATE TABLE %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT NOT NULL "+
                        ")", Categoria.TABLE_NAME, Categoria._ID, Categoria.COLUMN_NAME_DESCRICAO);
        public static final String SQL_DROP_CATEGORIA = String.format("DROP TABLE IF EXISTS %s",TABLE_NAME);
    }

    public static final class Atividade implements BaseColumns{
        public static final String TABLE_NAME = "atividade";
        public static final String COLUMN_NAME_PRODUCAO = "fk_Producao";
        public static final String COLUMN_NAME_DESCRICAO = "descricao";
        public static final String COLUMN_NAME_DATA_ATIVIDADE = "data_atividade";
        public static final String COLUMN_NAME_HORAS = "horas";
        public static final String SQL_CREATE_ATIVIDADE = String.format("CREATE TABLE %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT NOT NULL,"+
                        "%s INTEGER NOT NULL,"+
                        "%s TIMESTAMP NOT NULL,"+
                        "%s INTEGER NOT NULL" +
                        ")",
                Atividade.TABLE_NAME,
                Atividade._ID,
                Atividade.COLUMN_NAME_DESCRICAO,
                Atividade.COLUMN_NAME_PRODUCAO,
                Atividade.COLUMN_NAME_DATA_ATIVIDADE,
                Atividade.COLUMN_NAME_HORAS);
        public static final String SQL_DROP_ATIVIDADE = String.format("DROP TABLE IF EXISTS %s",TABLE_NAME);
    }

    public static final class Producao implements BaseColumns{
        public static final String TABLE_NAME = "producao";
        public static final String COLUMN_NAME_CANDIDATO = "fkCandidato";
        public static final String COLUMN_NAME_CATEGORIA = "fkCategoria";
        public static final String COLUMN_NAME_TITULO = "titulo";
        public static final String COLUMN_NAME_DESCRICAO = "descricao";
        public static final String COLUMN_NAME_DATA_INICIO= "data_inicio";
        public static final String COLUMN_NAME_DATA_FIM= "data_fim";
        public static final String COLUMN_NAME_HORAS = "horas";
        public static final String SQL_CREATE_PRODUCAO = String.format("CREATE TABLE %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s INTEGER NOT NULL,"+
                        "%s INTEGER NOT NULL,"+
                        "%s TEXT NOT NULL,"+
                        "%s TEXT NOT NULL,"+
                        "%s TIMESTAMP NOT NULL,"+
                        "%s TIMESTAMP NOT NULL,"+
                        "%s INTEGER NOT NULL" +
                        ")",
                Producao.TABLE_NAME,
                Producao._ID,
                Producao.COLUMN_NAME_CANDIDATO,
                Producao.COLUMN_NAME_CATEGORIA,
                Producao.COLUMN_NAME_TITULO,
                Producao.COLUMN_NAME_DESCRICAO,
                Producao.COLUMN_NAME_DATA_INICIO,
                Producao.COLUMN_NAME_DATA_FIM,
                Producao.COLUMN_NAME_HORAS
                );
        public static final String SQL_DROP_PRODUCAO = String.format("DROP TABLE IF EXISTS %s",TABLE_NAME);


    }

    public static final class Candidato implements BaseColumns{
        public static final String TABLE_NAME = "candidato";
        public static final String COLUMN_NAME_NOME = "nomeCandidato";
        public static final String COLUMN_NAME_NASCIMENTO = "nascimento";
        public static final String COLUMN_NAME_PERFIL = "perfil";
        public static final String COLUMN_NAME_TELEFONE = "telefone";
        public static final String COLUMN_NAME_EMAIL= "email";
        public static final String COLUMN_NAME_SOMA = "soma";
        public static final String SQL_CREATE_CANDIDATO = String.format("CREATE TABLE %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT NOT NULL,"+
                        "%s TIMESTAMP NOT NULL,"+
                        "%s TEXT NOT NULL,"+
                        "%s TEXT NOT NULL,"+
                        "%s TEXT NOT NULL"+
                        ")",
                Candidato.TABLE_NAME,
                Candidato._ID,
                Candidato.COLUMN_NAME_NOME,
                Candidato.COLUMN_NAME_NASCIMENTO,
                Candidato.COLUMN_NAME_PERFIL,
                Candidato.COLUMN_NAME_TELEFONE,
                Candidato.COLUMN_NAME_EMAIL
        );
        public static final String SQL_DROP_CANDIDATO = String.format("DROP TABLE IF EXISTS %s",TABLE_NAME);


    }
}
