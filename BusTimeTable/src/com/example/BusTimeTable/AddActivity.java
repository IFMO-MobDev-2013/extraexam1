package com.example.BusTimeTable;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TimePicker;

/**
 * Created by Alex on 31.01.14.
 */
public class AddActivity extends Activity implements View.OnClickListener {
	TimePicker timePicker;
	NumberPicker numberPicker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_add);
		NumberPicker np = (NumberPicker) findViewById(R.id.numberPicker);
		np.setMaxValue(100);
		np.setMinValue(0);
		((TimePicker) findViewById(R.id.timePicker)).setIs24HourView(true);
		((TimePicker) findViewById(R.id.timePicker2)).setIs24HourView(true);
	}

	@Override
	public void onClick(View v) {
		timePicker = (TimePicker) findViewById(R.id.timePicker);
		String hour =  String.valueOf(timePicker.getCurrentHour());
		String min = String.valueOf(timePicker.getCurrentMinute());
		hour = (hour.length() == 1) ? "0" + hour : hour;
		min = (min.length() == 1) ? "0" + min : min;
		String start = hour
				+ "."
				+ min
				;
		timePicker = (TimePicker) findViewById(R.id.timePicker2);

		hour =  String.valueOf(timePicker.getCurrentHour());
		min = String.valueOf(timePicker.getCurrentMinute());

		hour = (hour.length() == 1) ? "0" + hour : hour;
		min = (min.length() == 1) ? "0" + min : min;

		String end = hour
				+ "."
				+ min
				;
		start = (start.length() == 1) ? "0" + start  : start;
		start = (start.length() == 1) ? "0" + start  : start;
		numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
		int frequency = numberPicker.getValue();
		BusSqlOpenHelper busSqlOpenHelper = new BusSqlOpenHelper(AddActivity.this);
		SQLiteDatabase database = busSqlOpenHelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(BusSqlOpenHelper.START_INTERVAL, start);
		contentValues.put(BusSqlOpenHelper.END_INTERVAL, end);
		contentValues.put(BusSqlOpenHelper.FREQUENCY, frequency);
		database.insert(BusSqlOpenHelper.TABLE_NAME, null, contentValues);
		onBackPressed();
	}
}
