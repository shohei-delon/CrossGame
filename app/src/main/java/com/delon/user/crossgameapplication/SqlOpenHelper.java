package com.delon.user.crossgameapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 2016/07/02.
 */

public class SqlOpenHelper extends SQLiteOpenHelper {

    static final String DB_NAME = "result.db";
    static final int DB_Version = 1;

    static final String TABLE_NAME = "resultTable";
    static final String RESULT_ID = "resultId";
    static final String RESULT_DATE = "resultDate";
    static final String RESULT_COUNT = "resultCount";

    public SqlOpenHelper(Context context){
        super(context,DB_NAME,null,DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("CREATE TABLE "+ TABLE_NAME +"(" +
                RESULT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                RESULT_DATE + " DATE NOT NULL, " +
                RESULT_COUNT + " INTEGER NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
        onCreate(db);
    }



}
