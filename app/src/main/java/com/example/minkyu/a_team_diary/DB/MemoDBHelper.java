package com.example.minkyu.a_team_diary.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MemoDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "example4.db";
    public static final int DATABASE_VERSION = 3;

    public static final String CREATE_TABLE = "create table "+ Contract.ContractEmpty.TABLE_NAME2 +
            "("+ Contract.ContractEmpty.ID +" integer primary key autoincrement, "+
            Contract.ContractEmpty.FOLDER_NAME +" text,"+
            Contract.ContractEmpty.MEMO_TITLE +" text, " +
            Contract.ContractEmpty.MEMO_CONTENTS + " text, " +
            Contract.ContractEmpty.DATE + " text);";
    public static final String DROP_TABLE = "drop table if exists "+ Contract.ContractEmpty.TABLE_NAME2;

    public MemoDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void addContact(String folder_name, String memo_title, String memo_contents, String date, SQLiteDatabase database) {
        String sql = "insert into "+Contract.ContractEmpty.TABLE_NAME2+" values(null, '" + folder_name + "', '" + memo_title + "', '" + memo_contents + "', '" + date + "');";
        database.execSQL(sql);
    }

    public Cursor readContact(SQLiteDatabase database) {
        String sql = "select "+ Contract.ContractEmpty.ID+", "+Contract.ContractEmpty.FOLDER_NAME+", "+Contract.ContractEmpty.MEMO_TITLE+", "+Contract.ContractEmpty.MEMO_CONTENTS+ ", "+ Contract.ContractEmpty.DATE +" from "+Contract.ContractEmpty.TABLE_NAME2;
        Cursor cursor1 = database.rawQuery(sql, null);
        return cursor1;
    }

    public void AllDelete(String name, SQLiteDatabase database) {
        database.execSQL("Delete From "+Contract.ContractEmpty.TABLE_NAME2+" Where folder_name = '" + name + "';");
    }

    public void RealAllDelete(SQLiteDatabase database) {
        database.delete(Contract.ContractEmpty.TABLE_NAME2, null, null);
    }

    public void SelectDelete(String name, String memo_title, SQLiteDatabase database) {
        database.execSQL("Delete From "+Contract.ContractEmpty.TABLE_NAME2+" Where folder_name = '" + name + "'AND memo_title = '" + memo_title + "';");
    }

    public void UpdateContact(int id, String memo_title, String memo_contents, SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();

        String selection = Contract.ContractEmpty.ID+" = "+id;

        contentValues.put(Contract.ContractEmpty.MEMO_TITLE, memo_title);
        contentValues.put(Contract.ContractEmpty.MEMO_CONTENTS, memo_contents);

        database.update(Contract.ContractEmpty.TABLE_NAME2, contentValues, selection, null);
    }
}