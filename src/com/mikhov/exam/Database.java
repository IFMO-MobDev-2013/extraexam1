package com.mikhov.exam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

public class Database {
    private static final String DATABASE_TABLE_TIMES = "times";
    public static final String TIMES_COL_ID = "_id";
    public static final String TIMES_COL_TIME = "time";
    public static final String TIMES_COL_H1 = "h1";
    public static final String TIMES_COL_M1 = "m1";
    public static final String TIMES_COL_H2 = "h2";
    public static final String TIMES_COL_M2 = "m2";
    public static final String TIMES_COL_PERIOD = "period";

    private static final String DATABASE_TABLE_SCHEDULE = "schedule";
    public static final String SCHEDULE_COL_ID = "_id";
    public static final String SCHEDULE_COL_TIME = "time";
    public static final String SCHEDULE_COL_H1 = "h1";
    public static final String SCHEDULE_COL_M1 = "m1";
    public static final String SCHEDULE_COL_H2 = "h2";
    public static final String SCHEDULE_COL_M2 = "m2";
    public static final String SCHEDULE_COL_SCHEDULE = "schedule";

    private static final String[] STR_TIMES = { TIMES_COL_ID, TIMES_COL_TIME, TIMES_COL_H1, TIMES_COL_M1, TIMES_COL_H2, TIMES_COL_M2, TIMES_COL_PERIOD };
    private static final String[] STR_SCHEDULE = {SCHEDULE_COL_ID, SCHEDULE_COL_TIME, SCHEDULE_COL_H1, SCHEDULE_COL_M1, SCHEDULE_COL_H2, SCHEDULE_COL_M2, SCHEDULE_COL_SCHEDULE };


    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;


    public Database(Context ctx) {
        this.context = ctx;
    }

    public Database open() throws SQLException {
        dbHelper = new DbHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public boolean timesIsNotEmpty() {
        Cursor cursor = getAllTimesData();
        return cursor.moveToNext();
    }
    public boolean scheduleIsNotEmpty(String time) {
        Cursor cursor = getTimesData(time);
        return cursor.moveToNext();
    }

    public void dropTimes() {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS times");
        sqLiteDatabase.execSQL("create table times (_id integer primary key autoincrement, time text, h1 integer, m1 integer, h2 integer, m2 integer period integer);");
    }
    public void dropSchedule() {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS schedule");
        sqLiteDatabase.execSQL("create table schedule (_id integer primary key autoincrement, time text, h1 integer, m1 integer, h2 integer, m2 integer schedule text);");
    }
    public void dropAll() {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS times");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS schedule");
    }

    public Cursor getAllTimesData() {
        return sqLiteDatabase.query(DATABASE_TABLE_TIMES, STR_TIMES, null, null, null, null, TIMES_COL_TIME);
    }
    public Cursor getAllScheduleData() {
        return sqLiteDatabase.query(DATABASE_TABLE_SCHEDULE, STR_SCHEDULE, null, null, null, null, null);
    }
    public Cursor getTimesData(String time) {
        return sqLiteDatabase.query(DATABASE_TABLE_SCHEDULE, STR_SCHEDULE, "time = '" + time + "'", null, null, null, SCHEDULE_COL_SCHEDULE);
    }

    public String getTime(long id) throws SQLException {
        Cursor cursor = sqLiteDatabase.query(true, DATABASE_TABLE_TIMES, STR_TIMES, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            if (cursor.getInt(cursor.getColumnIndex(TIMES_COL_ID)) == id) {
                break;
            }
        }
        return cursor.getString(cursor.getColumnIndex(TIMES_COL_TIME));
    }

    public void deleteTime(long id) {
        sqLiteDatabase.delete(DATABASE_TABLE_TIMES, TIMES_COL_ID + "=" + id, null);
    }
    public void deleteScheduleWithTime(String time) {
        sqLiteDatabase.delete(DATABASE_TABLE_SCHEDULE, SCHEDULE_COL_TIME + "='" + time + "'", null);
    }

    public void addTime(String time, int h1, int m1, int h2, int m2, int period) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TIMES_COL_TIME, time);
        contentValues.put(TIMES_COL_H1, h1);
        contentValues.put(TIMES_COL_M1, m1);
        contentValues.put(TIMES_COL_H2, h2);
        contentValues.put(TIMES_COL_M2, m2);
        contentValues.put(TIMES_COL_PERIOD, period);
        sqLiteDatabase.insert(DATABASE_TABLE_TIMES, null, contentValues);
    }
    public void addSchedule(String time, String schedule) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SCHEDULE_COL_TIME, time);
        contentValues.put(SCHEDULE_COL_SCHEDULE, schedule);
        sqLiteDatabase.insert(DATABASE_TABLE_SCHEDULE, null, contentValues);
    }
}
