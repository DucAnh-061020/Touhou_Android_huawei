package com.touhou.game.SqlLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    Context context;
    static final String DB_NAME ="db_score.db";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql =" create table tbl_score (id INTEGER PRIMARY KEY AUTOINCREMENT Not null, " +
                    "name text, Score Integer); "; //dinh nghia cau lenh tao bang o day
        //thuc thi cau lenh
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS tbl_score" );
        //goi lai phuong thuc oncreate de tao lai csdl
        onCreate(sqLiteDatabase);
    }
}
