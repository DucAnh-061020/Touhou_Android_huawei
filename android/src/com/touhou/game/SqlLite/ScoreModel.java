package com.touhou.game.SqlLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.touhou.game.staticData.ScoreValue;

import java.util.ArrayList;
import java.util.List;

public class ScoreModel {
    DBHelper dbHelper;
    SQLiteDatabase db;
    public ScoreModel(Context context){
        dbHelper = new DBHelper(context);
    }

    public long insScore (ScoreValue scoresValues){
        db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",scoresValues.getName());
        contentValues.put("Score",scoresValues.getScore());
        return db.insert("tbl_score",null,contentValues);
    }

    public List<ScoreValue> getAllScore(){
        db = dbHelper.getReadableDatabase();
        List<ScoreValue> res = new ArrayList<>();
        Cursor cs = db.rawQuery(" select * from tbl_score ORDER BY score DESC", null);
        if(cs.getCount()>0){
            while(cs.moveToNext()){
                //add doi tuong score vao list
                res.add(new ScoreValue
                    (
                        cs.getString(1),
                        Integer.parseInt(cs.getString(2))
                    )
                );
            }
        }
        return res;
    }
    public Cursor getCursor(){
        db = dbHelper.getReadableDatabase();
        Cursor cs = db.rawQuery(" select * from tbl_score ORDER BY score DESC", null);
        return cs;
    }
}
