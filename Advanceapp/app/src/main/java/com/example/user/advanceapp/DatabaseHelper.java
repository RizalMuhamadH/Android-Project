package com.example.user.advanceapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by User on 13/06/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper{
    SQLiteDatabase database;
    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="transaksi.db";
    public static final String TABLE_NAME1="expanses";
    public static final String TABLE_NAME2="income";
    public static final String EX_1="ID";
    public static final String EX_2="DESC_EXPANSES";
    public static final String EX_3="Amount";
    public static final String IN_1="ID";
    public static final String IN_2="DESC_INCOME";
    public static final String IN_3="Amount";
    public static final String TABLE_CREATE_EXPANSES = "CREATE TABLE " + TABLE_NAME1 + " ( " +

            EX_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

            EX_2 + " TEXT, " +

            EX_3 + " INTEGER);";
    public static final String TABLE_CREATE_INCOME = "CREATE TABLE " + TABLE_NAME2 + " ( " +

            IN_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

            IN_2 + " TEXT, " +

            IN_3 + " INTEGER);";

    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_INCOME);
        db.execSQL(TABLE_CREATE_EXPANSES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST"+TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXIST"+TABLE_NAME2);
        onCreate(db);

    }
    public boolean save_expanses(String description, int amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content_values = new ContentValues();
        content_values.put(EX_2, description);
        content_values.put(EX_3, amount);
        long result = db.insert(TABLE_NAME1,null, content_values);
        return  result !=-1;
    }
    public boolean save_income(String description, int amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content_values = new ContentValues();
        content_values.put(IN_2, description);
        content_values.put(IN_3, amount);
        long result = db.insert(TABLE_NAME2,null, content_values);
        return  result !=-1;
    }
    public Cursor list_expanses() {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor expanses = db.rawQuery("SELECT * FROM " + TABLE_NAME1, null);

        return expanses;

    }
    public Cursor list_income() {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor income = db.rawQuery("SELECT * FROM " + TABLE_NAME2, null);

        return income;

    }
    public boolean update_exp(String id,String desc, String amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EX_1,id);
        values.put(EX_2,desc);
        values.put(EX_3,amount);
        db.update(TABLE_NAME1,values,"ID = ?",new String[]{id});
        return true;
    }
    public Integer delete_exp(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME1,"ID = ?",new String[]{id});
    }
}
