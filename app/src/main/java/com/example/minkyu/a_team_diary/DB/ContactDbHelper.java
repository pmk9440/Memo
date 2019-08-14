package com.example.minkyu.a_team_diary.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "example.db";
    public static final int DATABASE_VERSION = 1;

    public static final String CREATE_TABLE = "create table "+ Contract.ContractEmpty.TABLE_NAME +
            "("+ Contract.ContractEmpty.NAME +" text);";
    public static final String DROP_TABLE = "drop table if exists "+ Contract.ContractEmpty.TABLE_NAME;

    public ContactDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);

    }

    public void addContact(String name, SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.ContractEmpty.NAME, name);

        database.insert(Contract.ContractEmpty.TABLE_NAME, null, contentValues);

    }

    public Cursor readContact(SQLiteDatabase database) {
        String[] projections = {Contract.ContractEmpty.NAME};
        Cursor cursor = database.query(Contract.ContractEmpty.TABLE_NAME, projections, null, null, null, null, null);

        return cursor;
    }

    public void alldeleteContact(SQLiteDatabase database) {
        database.delete(Contract.ContractEmpty.TABLE_NAME, null, null);
    }

    public void selectdeleteContact(String name, SQLiteDatabase database) {
        database.execSQL("Delete From "+Contract.ContractEmpty.TABLE_NAME+" Where name = '" + name + "';");
    }

}
