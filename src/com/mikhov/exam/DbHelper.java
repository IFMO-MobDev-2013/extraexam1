package com.mikhov.exam;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "exam";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE_CARDS = "create table times (_id integer primary key autoincrement, time text, h1 integer, m1 integer, h2 integer, m2 integer, period integer);";
    private static final String DATABASE_CREATE_QUESTIONS = "create table schedule (_id integer primary key autoincrement, time text, h1 integer, m1 integer, h2 integer, m2 integer, schedule text);";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE_CARDS);
        sqLiteDatabase.execSQL(DATABASE_CREATE_QUESTIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(DbHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS times");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS schedule");
        onCreate(sqLiteDatabase);
    }
}
