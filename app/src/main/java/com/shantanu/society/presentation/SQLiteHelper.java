package com.shantanu.society.presentation;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import com.shantanu.society.model.MyDataDAO;

public class SQLiteHelper extends SQLiteOpenHelper {


    public SQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }
    public void insertData(MyDataDAO myDataDAO){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Data VALUES (NULL,?, ?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, myDataDAO.getName());
        statement.bindString(2, myDataDAO.getDesc());
        statement.bindString(3, myDataDAO.getImageUrl());
        statement.executeInsert();
    }
    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }
    public int getMaxCount(){
        SQLiteDatabase database = getReadableDatabase();
        Cursor c= database.rawQuery("Select count(*) from Data", null);
        while (c.moveToNext()){
            return c.getInt(0);
        }
        return 1;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
