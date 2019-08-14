package com.ufjf.br.trabalho03.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufjf.br.trabalho03.contract.HeadHunterContract;
import com.ufjf.br.trabalho03.contract.HeadHunterDBHelper;
import com.ufjf.br.trabalho03.model.Atividade;

public class AtividadeDAO implements IBaseDAO<Atividade> {
    private static final AtividadeDAO atividadeDAO = new AtividadeDAO();

    public static AtividadeDAO getInstance() {
        return atividadeDAO;
    }

    private AtividadeDAO() {
    }

    @Override
    public Atividade Save(Atividade obj, Context context) {
        HeadHunterDBHelper headHunterDBHelper = new HeadHunterDBHelper(context);
        SQLiteDatabase db = headHunterDBHelper.getWritableDatabase();
        obj.setIdAtividade((int) db.insert(HeadHunterContract.Atividade.TABLE_NAME, null, toValues(obj)));
        return obj;
    }

    @Override
    public Integer Update(Atividade obj, Context context) {
        HeadHunterDBHelper headHunterDBHelper = new HeadHunterDBHelper(context);
        SQLiteDatabase db = headHunterDBHelper.getWritableDatabase();
        return db.update(HeadHunterContract.Atividade.TABLE_NAME, toValues(obj), HeadHunterContract.Atividade._ID+"=?",toArgs(obj));
    }

    @Override
    public Integer Delete(Atividade obj, Context context) {
        HeadHunterDBHelper headHunterDBHelper = new HeadHunterDBHelper(context);
        SQLiteDatabase db = headHunterDBHelper.getWritableDatabase();
        return db.delete(HeadHunterContract.Atividade.TABLE_NAME, HeadHunterContract.Atividade._ID+"=?",toArgs(obj));
    }

    public Integer DeleteByProducao(Integer idProducao,Context context){
        HeadHunterDBHelper headHunterDBHelper = new HeadHunterDBHelper(context);
        SQLiteDatabase db = headHunterDBHelper.getWritableDatabase();
        return db.delete(HeadHunterContract.Atividade.TABLE_NAME, HeadHunterContract.Producao._ID+"=?", new String[]{String.valueOf(idProducao)});
    }

    public Cursor getAll(Context context,Integer idProducao) {
        HeadHunterDBHelper headHunterDBHelper = new HeadHunterDBHelper(context);
        SQLiteDatabase db = headHunterDBHelper.getReadableDatabase();
        String[] visao = {
                HeadHunterContract.Atividade._ID,
                HeadHunterContract.Atividade.COLUMN_NAME_DESCRICAO,
                HeadHunterContract.Atividade.COLUMN_NAME_DATA_ATIVIDADE,
                HeadHunterContract.Atividade.COLUMN_NAME_HORAS,
                HeadHunterContract.Atividade.COLUMN_NAME_PRODUCAO
        };
        String selecao = HeadHunterContract.Atividade.COLUMN_NAME_PRODUCAO + " = ?";
        String[] args = {String.valueOf(idProducao)};
        String sort = HeadHunterContract.Atividade.COLUMN_NAME_DESCRICAO + " ASC";
        return db.query(HeadHunterContract.Atividade.TABLE_NAME, visao, selecao, args, null, null, sort);


    }

    @Override
    public ContentValues toValues(Atividade obj)       {
        ContentValues values = new ContentValues();
        values.put(HeadHunterContract.Atividade.COLUMN_NAME_PRODUCAO, obj.getProducao().getIdProducao());
        values.put(HeadHunterContract.Atividade.COLUMN_NAME_DESCRICAO, obj.getDescricao());
        values.put(HeadHunterContract.Atividade.COLUMN_NAME_DATA_ATIVIDADE, obj.getStringDataAtividade());
        values.put(HeadHunterContract.Atividade.COLUMN_NAME_HORAS, obj.getHoras());
        return values;
    }

    @Override
    public String[] toArgs(Atividade obj) {
        return new String[]{String.valueOf(obj.getIdAtividade())};
    }
}
