package com.ufjf.br.trabalho03.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufjf.br.trabalho03.contract.HeadHunterContract;
import com.ufjf.br.trabalho03.contract.HeadHunterDBHelper;
import com.ufjf.br.trabalho03.model.Categoria;

import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO implements IBaseDAO<Categoria> {

    private static final CategoriaDAO categoriaDAO = new CategoriaDAO();

    public static CategoriaDAO getInstance() {
        return categoriaDAO;
    }

    private CategoriaDAO() {
    }

    @Override
    public Categoria Save(Categoria obj, Context context) {
        HeadHunterDBHelper headHunterDBHelper = new HeadHunterDBHelper(context);
        SQLiteDatabase db = headHunterDBHelper.getWritableDatabase();
        obj.setIdCategoria((int) db.insert(HeadHunterContract.Categoria.TABLE_NAME, null, toValues(obj)));
        return obj;
    }

    @Override
    public Integer Update(Categoria obj, Context context) {
        HeadHunterDBHelper headHunterDBHelper = new HeadHunterDBHelper(context);
        SQLiteDatabase db = headHunterDBHelper.getWritableDatabase();
        return db.update(HeadHunterContract.Categoria.TABLE_NAME, toValues(obj), HeadHunterContract.Categoria._ID + "=?", toArgs(obj));
    }

    @Override
    public Integer Delete(Categoria obj, Context context) {
        HeadHunterDBHelper headHunterDBHelper = new HeadHunterDBHelper(context);
        SQLiteDatabase db = headHunterDBHelper.getWritableDatabase();
        return db.delete(HeadHunterContract.Categoria.TABLE_NAME, HeadHunterContract.Categoria._ID + "=?", toArgs(obj));
    }

    public Cursor getAll(Context context) {
        HeadHunterDBHelper headHunterDBHelper = new HeadHunterDBHelper(context);
        SQLiteDatabase db = headHunterDBHelper.getReadableDatabase();
        String[] visao = {
                HeadHunterContract.Categoria._ID,
                HeadHunterContract.Categoria.COLUMN_NAME_DESCRICAO
        };
        //String selecao = BibliotecaContract.Livro.COLUMN_NAME_ANO + " > ?";
        //String[] args = {"1950"};
        String sort = HeadHunterContract.Categoria.COLUMN_NAME_DESCRICAO + " ASC";
        return db.query(HeadHunterContract.Categoria.TABLE_NAME, visao, null, null, null, null, sort);

    }

    public ContentValues toValues(Categoria categoria) {
        ContentValues values = new ContentValues();
        values.put(HeadHunterContract.Categoria.COLUMN_NAME_DESCRICAO, categoria.getDescricao());
        return values;
    }

    public List<Categoria> getCategorias(Context context) {
        Cursor cursor = getAll(context);
        List<Categoria> categorias = new ArrayList<>();
        for (int i = 0; i < cursor.getCount(); i++) {
            categorias.add(getCategoria(i, cursor));
        }
        return categorias;
    }

    public Categoria getCategoria(int position, Cursor cursor) {
        int idxTitulo = cursor.getColumnIndexOrThrow(HeadHunterContract.Categoria.COLUMN_NAME_DESCRICAO);
        int idxId = cursor.getColumnIndexOrThrow(HeadHunterContract.Categoria._ID);
        cursor.moveToPosition(position);
        Categoria categoria;
        categoria = new Categoria(cursor.getInt(idxId), cursor.getString(idxTitulo));
        return categoria;
    }

    @Override
    public String[] toArgs(Categoria obj) {
        return new String[]{obj.getIdCategoria().toString()};
    }


}
