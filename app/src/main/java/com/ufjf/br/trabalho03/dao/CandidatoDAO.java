package com.ufjf.br.trabalho03.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.ufjf.br.trabalho03.activity.CategoriaListActivity;
import com.ufjf.br.trabalho03.contract.HeadHunterContract;
import com.ufjf.br.trabalho03.contract.HeadHunterDBHelper;
import com.ufjf.br.trabalho03.model.Candidato;
import com.ufjf.br.trabalho03.model.Categoria;

public class CandidatoDAO implements IBaseDAO<Candidato> {
    private static final CandidatoDAO candidatoDAO = new CandidatoDAO();
    private String SQLSumByCategoria = String.format("SELECT c.%s,%s,%s,%s,%s,%s,IFNULL(sum(horas),0) as soma " +
            "FROM %s c LEFT JOIN  " +
            " %s p ON c.%s = p.%s " +
            " WHERE p.%s = ? "+
            " GROUP BY c.%s " +
            " ORDER BY sum(horas) DESC",
            HeadHunterContract.Candidato._ID,
            HeadHunterContract.Candidato.COLUMN_NAME_NOME,
            HeadHunterContract.Candidato.COLUMN_NAME_EMAIL,
            HeadHunterContract.Candidato.COLUMN_NAME_NASCIMENTO,
            HeadHunterContract.Candidato.COLUMN_NAME_PERFIL,
            HeadHunterContract.Candidato.COLUMN_NAME_TELEFONE,
            HeadHunterContract.Candidato.TABLE_NAME,
            HeadHunterContract.Producao.TABLE_NAME,
            HeadHunterContract.Candidato._ID,
            HeadHunterContract.Producao.COLUMN_NAME_CANDIDATO,
            HeadHunterContract.Producao.COLUMN_NAME_CATEGORIA,
            HeadHunterContract.Candidato._ID
            );
    private String SQLSum = String.format("SELECT c.%s,%s,%s,%s,%s,%s,IFNULL(sum(horas),0) as soma " +
                    "FROM %s c LEFT JOIN " +
                    "%s p ON c.%s = p.%s " +
                    "GROUP BY c.%s " +
                    "ORDER BY sum(horas) DESC",
            HeadHunterContract.Candidato._ID,
            HeadHunterContract.Candidato.COLUMN_NAME_NOME,
            HeadHunterContract.Candidato.COLUMN_NAME_EMAIL,
            HeadHunterContract.Candidato.COLUMN_NAME_NASCIMENTO,
            HeadHunterContract.Candidato.COLUMN_NAME_PERFIL,
            HeadHunterContract.Candidato.COLUMN_NAME_TELEFONE,
            HeadHunterContract.Candidato.TABLE_NAME,
            HeadHunterContract.Producao.TABLE_NAME,
            HeadHunterContract.Candidato._ID,
            HeadHunterContract.Producao.COLUMN_NAME_CANDIDATO,
            HeadHunterContract.Candidato._ID
    );
    public static CandidatoDAO getInstance() {
        return candidatoDAO;
    }

    private CandidatoDAO() {
    }
    @Override
    public Candidato Save(Candidato obj, Context context) throws SQLException {
        HeadHunterDBHelper headHunterDBHelper = new HeadHunterDBHelper(context);
        SQLiteDatabase db = headHunterDBHelper.getWritableDatabase();
        try {
            obj.setIdCandidato((int)db.insertOrThrow(HeadHunterContract.Candidato.TABLE_NAME, null, toValues(obj)));
            return obj;
        }catch (SQLException e){
            throw e;
        }
    }

    @Override
    public Integer Update(Candidato obj, Context context) {
        HeadHunterDBHelper headHunterDBHelper = new HeadHunterDBHelper(context);
        SQLiteDatabase db = headHunterDBHelper.getWritableDatabase();
        return db.update(HeadHunterContract.Candidato.TABLE_NAME, toValues(obj), HeadHunterContract.Candidato._ID+"=?",toArgs(obj));

    }

    @Override
    public Integer Delete(Candidato obj, Context context) {

        HeadHunterDBHelper headHunterDBHelper = new HeadHunterDBHelper(context);
        SQLiteDatabase db = headHunterDBHelper.getWritableDatabase();
        ProducaoDAO.getInstance().DeleteProducaoByCandidato(context,obj.getIdCandidato());
        return db.delete(HeadHunterContract.Candidato.TABLE_NAME, HeadHunterContract.Candidato._ID+"=?",toArgs(obj));
    }



    @Override
    public ContentValues toValues(Candidato obj) {
        ContentValues values = new ContentValues();
        values.put(HeadHunterContract.Candidato.COLUMN_NAME_NOME, obj.getNomeCandidato());
        values.put(HeadHunterContract.Candidato.COLUMN_NAME_EMAIL, obj.getEmail());
        values.put(HeadHunterContract.Candidato.COLUMN_NAME_PERFIL, obj.getPerfil());
        values.put(HeadHunterContract.Candidato.COLUMN_NAME_TELEFONE, obj.getTelefone());
        values.put(HeadHunterContract.Candidato.COLUMN_NAME_NASCIMENTO, obj.getStringDataNascimento());
        return values;
    }

    @Override
    public String[] toArgs(Candidato obj) {
        return new String[]{String.valueOf(obj.getIdCandidato())};
    }

    public Cursor getCandidatosSomaHoras(Context context, Categoria categoria) {
        HeadHunterDBHelper headHunterDBHelper = new HeadHunterDBHelper(context);
        SQLiteDatabase db = headHunterDBHelper.getReadableDatabase();
        if(categoria != null) {
            return db.rawQuery(SQLSumByCategoria, new String[]{String.valueOf(categoria.getIdCategoria())});
        }
        return db.rawQuery(SQLSum, null);
    }
}
