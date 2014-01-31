package com.mikhov.exam;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;

public class Act2 extends ListActivity {

    public static enum TransitionType {
        SlideLeft
    }
    public static TransitionType transitionType;

    Database database;
    SimpleCursorAdapter simpleCursorAdapter;
    Cursor cursorTimes;
    TextView tv;
    Intent intent;
    long show_id, del_time_id;
    String time, del_time;
    AdapterView.AdapterContextMenuInfo adapterContextMenuInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act2);
        this.getListView().setDividerHeight(1);
        database = new Database(this);
        database.open();

        Bundle extras = getIntent().getExtras();
        time = extras.getSerializable("time").toString();

        fillData();
    }

    private void fillData() {
        cursorTimes = database.getTimesData(time);
        startManagingCursor(cursorTimes);
        String[] from = new String[] { Database.SCHEDULE_COL_SCHEDULE };
        int[] to = new int[] { R.id.schedule_item };
        simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.schedule_item, cursorTimes, from, to);
        setListAdapter(simpleCursorAdapter);
        registerForContextMenu(getListView());
        tv = (TextView) findViewById(R.id.schedule_empty);
        if (database.scheduleIsNotEmpty(time)) {
            tv.setText("");
        } else {
            tv.setText(R.string.empty);
        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
        Intent intent = new Intent(this, Act.class);
        startActivity(intent);
        overridePendingTransition(R.layout.slide_right_in, R.layout.slide_right_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }
}