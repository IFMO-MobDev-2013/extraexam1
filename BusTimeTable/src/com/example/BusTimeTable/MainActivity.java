package com.example.BusTimeTable;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
	/**
	 * Called when the activity is first created.
	 */
	ListView listView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_list);
		listView = (ListView) findViewById(R.id.listView);
		registerForContextMenu(listView);

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		if (v.getId() == R.id.listView) {
			menu.add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
				@Override
				public boolean onMenuItemClick(MenuItem item) {
					AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

					BusSqlOpenHelper busSqlOpenHelper = new BusSqlOpenHelper(MainActivity.this);
					SQLiteDatabase database = busSqlOpenHelper.getWritableDatabase();
					String s  = listView.getItemAtPosition(acmi.position).toString();
					String s1 = s.substring(s.lastIndexOf("=") + 1,s.lastIndexOf("-") - 1);

					database.delete(
							busSqlOpenHelper.TABLE_NAME,
							busSqlOpenHelper.START_INTERVAL + "='" + s1 + "'" ,
							null
					);
					updateListView();
					return true;
				}
			});

		}
	}


	@Override
	protected void onResume() {
		super.onResume();
		updateListView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.Add:
				startActivity(new Intent(this, AddActivity.class));
				return true;
			case R.id.action_settings:
				startActivity(new Intent(this,ScheduleActivity.class));
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public void updateListView() {

		Cursor cursor;
		ArrayList<Map<String, String>> data = new ArrayList<Map<String, String>>();
		BusSqlOpenHelper busSqlOpenHelper = new BusSqlOpenHelper(MainActivity.this);
		SQLiteDatabase database = busSqlOpenHelper.getWritableDatabase();

		cursor = database.query(busSqlOpenHelper.TABLE_NAME, null, null, null, null, null, null);
		while (cursor.moveToNext()) {
			Map<String, String> datum = new HashMap<String, String>();
			datum.put("First Line", cursor.getString(cursor.getColumnIndex(BusSqlOpenHelper.START_INTERVAL))
					+ " - "
					+ cursor.getString(cursor.getColumnIndex(BusSqlOpenHelper.END_INTERVAL)));
			int frequency = cursor.getInt(cursor.getColumnIndex(BusSqlOpenHelper.FREQUENCY));

			datum.put("Second Line","Частота : " + String.valueOf(frequency));
			data.add(datum);
		}
		cursor.close();

		SimpleAdapter adapter = new SimpleAdapter(this, data,
				android.R.layout.simple_list_item_2,
				new String[] {"First Line", "Second Line" },
				new int[] {android.R.id.text1, android.R.id.text2 });


		listView.setAdapter(adapter);
	}
}
