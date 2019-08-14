package com.ufjf.br.trabalho03.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public interface IBaseDAO<T>  {

    public T Save(T obj, Context context);
    public Integer Update(T obj, Context context);
    public Integer Delete(T obj, Context context);
    public ContentValues toValues(T obj);
    public String[] toArgs (T obj);
}
