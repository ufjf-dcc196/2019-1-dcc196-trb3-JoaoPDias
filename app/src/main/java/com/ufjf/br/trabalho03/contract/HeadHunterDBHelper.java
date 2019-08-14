package com.ufjf.br.trabalho03.contract;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HeadHunterDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "GerenciadorCurriculo.db";

    public HeadHunterDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(HeadHunterContract.Categoria.SQL_CREATE_CATEGORIA);
        db.execSQL(HeadHunterContract.Candidato.SQL_CREATE_CANDIDATO);
        db.execSQL(HeadHunterContract.Producao.SQL_CREATE_PRODUCAO);
        db.execSQL(HeadHunterContract.Atividade.SQL_CREATE_ATIVIDADE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(HeadHunterContract.Categoria.SQL_DROP_CATEGORIA);
        db.execSQL(HeadHunterContract.Candidato.SQL_DROP_CANDIDATO);
        db.execSQL(HeadHunterContract.Producao.SQL_DROP_PRODUCAO);
        db.execSQL(HeadHunterContract.Atividade.SQL_DROP_ATIVIDADE);
        onCreate(db);
    }

}
