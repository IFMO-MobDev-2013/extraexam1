package com.example.extraexam1.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.extraexam1.Buss;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: javlon
 * Date: 31.01.14
 * Time: 15:05
 * To change this template use File | Settings | File Templates.
 */
public class TimeDB {
    private static final String DB_NAME = "carsbase";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "bus_table";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_START = "start";
    public static final String COLUMN_FINISH = "finidh";
    public static final String COLUMN_CYCLE = "cycle";

    public static final String DB_CREATE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_START + " text, " +  COLUMN_FINISH + " text, " + COLUMN_CYCLE + " text" + ");";

    private final Context context;

    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public TimeDB(Context context) {
        this.context = context;
    }

    public void open() {
        mDBHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    public void close() {
        if (mDBHelper!=null) mDBHelper.close();
    }


    public Cursor getAllData() {
        return mDB.query(DB_TABLE, null, null, null, null, null, null);
    }

    public Cursor getChannelData(long channel) {
        return mDB.query(DB_TABLE, null, COLUMN_CYCLE + " = " + channel, null, null, null, COLUMN_FINISH + " DESC");
    }

    public void addChannel(String start, String finish, String cycle ) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_START, start);
        cv.put(COLUMN_FINISH, finish);
        cv.put(COLUMN_CYCLE, cycle);

        mDB.insert(DB_TABLE, null, cv);
    }

    public void deleteChannel(long id) {
        mDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
    }

    public ArrayList<Buss> getAll() {
        Cursor cursor = getAllData();
        ArrayList<Buss> result = new ArrayList<Buss>();
        while (cursor.moveToNext()) {
            Buss current = new Buss();
            current.setStartTime(cursor.getString(cursor.getColumnIndex(COLUMN_START)));
            current.setFinishTime(cursor.getString(cursor.getColumnIndex(COLUMN_FINISH)));
            current.setTime(cursor.getString(cursor.getColumnIndex(COLUMN_CYCLE)));
            result.add(current);
        }
        return result;
    }

    public Buss selectCars(String name) {
        ArrayList<Buss> carses = getAll();
        for (int i = 0; i < carses.size(); i++)
            if (carses.get(i).getBussTime().equals(name))
                return carses.get(i);
        return null;
    }

}
