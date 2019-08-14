package com.ufjf.br.trabalho03.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.ufjf.br.trabalho03.contract.HeadHunterContract;
import com.ufjf.br.trabalho03.contract.HeadHunterDBHelper;
import com.ufjf.br.trabalho03.model.Producao;

public class ProducaoDAO implements IBaseDAO<Producao> {

    private static final ProducaoDAO producaoDAO = new ProducaoDAO();

    public static ProducaoDAO getInstance() {
        return producaoDAO;
    }

    private ProducaoDAO() {
    }

    @Override
    public Producao Save(Producao obj, Context context) {
        HeadHunterDBHelper headHunterDBHelper = new HeadHunterDBHelper(context);
        SQLiteDatabase db = headHunterDBHelper.getWritableDatabase();
        try {
            obj.setIdProducao((int) db.insertOrThrow(HeadHunterContract.Producao.TABLE_NAME, null, toValues(obj)));
            return obj;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public Integer Update(Producao obj, Context context) {
        HeadHunterDBHelper headHunterDBHelper = new HeadHunterDBHelper(context);
        SQLiteDatabase db = headHunterDBHelper.getWritableDatabase();
        return db.update(HeadHunterContract.Producao.TABLE_NAME, toValues(obj), HeadHunterContract.Producao._ID + "=?", toArgs(obj));

    }

    @Override
    public Integer Delete(Producao obj, Context context) {
        HeadHunterDBHelper headHunterDBHelper = new HeadHunterDBHelper(context);
        SQLiteDatabase db = headHunterDBHelper.getWritableDatabase();
        return db.delete(HeadHunterContract.Producao.TABLE_NAME, HeadHunterContract.Producao._ID + "=?", toArgs(obj));

    }

    public Integer DeleteProducaoByCandidato(Context context, Integer idCandidato) {
        Cursor cursor = getAll(context, idCandidato);
        if (cursor.getCount() > 0) {
            int idxId = cursor.getColumnIndexOrThrow(HeadHunterContract.Producao._ID);
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                AtividadeDAO.getInstance().DeleteByProducao(cursor.getInt(idxId),context);
            }
        }
        return  cursor.getCount();
    }

    public Cursor getAll(Context context, Integer idCandidato) {
        HeadHunterDBHelper headHunterDBHelper = new HeadHunterDBHelper(context);
        SQLiteDatabase db = headHunterDBHelper.getReadableDatabase();
        String[] visao = {
                HeadHunterContract.Producao._ID,
                HeadHunterContract.Producao.COLUMN_NAME_DESCRICAO,
                HeadHunterContract.Producao.COLUMN_NAME_DATA_INICIO,
                HeadHunterContract.Producao.COLUMN_NAME_DATA_FIM,
                HeadHunterContract.Producao.COLUMN_NAME_CANDIDATO,
                HeadHunterContract.Producao.COLUMN_NAME_TITULO,
                HeadHunterContract.Producao.COLUMN_NAME_CATEGORIA,
                HeadHunterContract.Producao.COLUMN_NAME_HORAS
        };
        String selecao = HeadHunterContract.Producao.COLUMN_NAME_CANDIDATO + " = ?";
        String[] args = {String.valueOf(idCandidato)};
        String sort = HeadHunterContract.Producao.COLUMN_NAME_TITULO + " ASC";
        return db.query(HeadHunterContract.Producao.TABLE_NAME, visao, selecao, args, null, null, sort);

    }

    @Override
    public ContentValues toValues(Producao obj) {
        ContentValues values = new ContentValues();
        values.put(HeadHunterContract.Producao.COLUMN_NAME_CANDIDATO, obj.getCandidato().getIdCandidato());
        values.put(HeadHunterContract.Producao.COLUMN_NAME_CATEGORIA, obj.getCategoria().getIdCategoria());
        values.put(HeadHunterContract.Producao.COLUMN_NAME_DESCRICAO, obj.getDescricao());
        values.put(HeadHunterContract.Producao.COLUMN_NAME_TITULO, obj.getTitulo());
        values.put(HeadHunterContract.Producao.COLUMN_NAME_DATA_INICIO, obj.getStringDataInicio());
        values.put(HeadHunterContract.Producao.COLUMN_NAME_DATA_FIM, obj.getStringDataFim());
        values.put(HeadHunterContract.Producao.COLUMN_NAME_HORAS, obj.getHoras());
        return values;
    }

    @Override
    public String[] toArgs(Producao obj) {
        return new String[]{String.valueOf(obj.getIdProducao())};
    }
}
