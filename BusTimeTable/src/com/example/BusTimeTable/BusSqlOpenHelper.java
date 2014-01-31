package com.example.BusTimeTable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alex on 31.01.14.
 */
public class BusSqlOpenHelper extends SQLiteOpenHelper {
	public static final int VERSION  = 1;
	public static final String TABLE_NAME = "bus_table";
	public static final String DATABASE_NAME = "database.db";
	public static final String START_INTERVAL = "start";
	public static final String END_INTERVAL = "end";
	public static final String FREQUENCY = "frequency";

	public BusSqlOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_NAME
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ START_INTERVAL + " TEXT, "
				+ END_INTERVAL + " TEXT, "
				+ FREQUENCY + " INTEGER"
				+ ");");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE " + TABLE_NAME);
		onCreate(db);
	}
}
