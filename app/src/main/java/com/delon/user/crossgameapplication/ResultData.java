package com.delon.user.crossgameapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by User on 2016/07/02.
 */

public class ResultData {

    static private SqlOpenHelper sqlOpenHelper;
    private SQLiteDatabase db;

    public ResultData(Context context){
        sqlOpenHelper = new SqlOpenHelper(context);
        db = sqlOpenHelper.getWritableDatabase();
    }

    //全リストを取得
    public Cursor getAllList(){
        return db.query(SqlOpenHelper.TABLE_NAME,null,null,null,null,null,null);
    }

    //追加
    public void insert(String resultDate,int resultCount){
        ContentValues values = new ContentValues();
        values.put(SqlOpenHelper.RESULT_DATE,resultDate);
        values.put(SqlOpenHelper.RESULT_COUNT,resultCount);
        db.insertOrThrow(SqlOpenHelper.TABLE_NAME, null, values);
    }

    public void delete(int resultId){
        db.delete(SqlOpenHelper.TABLE_NAME, SqlOpenHelper.RESULT_ID + " = " + resultId, null);
    }

    //タッチ回数に従って並び替えするときのメソッド
    Cursor countSort(){
        String sql = "SELECT * FROM " + SqlOpenHelper.TABLE_NAME + " ORDER BY  "+ SqlOpenHelper.RESULT_COUNT + " ASC LIMIT 10 " + " ;";
        return db.rawQuery(sql,null);
    }

    //日付に従って並び替えするときのメソッド
    Cursor dateSort(){
        String sql = "SELECT * FROM " + SqlOpenHelper.TABLE_NAME + " ORDER BY " + SqlOpenHelper.RESULT_DATE + " ;";
        return db.rawQuery(sql,null);
    }

    //idに従って並び替えするときのメソッド
    Cursor idSort(){
        String sql = "SELECT * FROM " + SqlOpenHelper.TABLE_NAME + " ORDER BY " + SqlOpenHelper.RESULT_ID + " DESC LIMIT 10 " + " ;";
        return db.rawQuery(sql,null);
    }

    Cursor maxRecord(){

        String sql = "SELECT * FROM " + SqlOpenHelper.TABLE_NAME + " ORDER BY  "+ SqlOpenHelper.RESULT_COUNT + " ASC LIMIT 1 " + " ;";
        return db.rawQuery(sql,null);
    }


}
