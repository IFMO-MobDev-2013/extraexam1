package com.mikhov.exam;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

public class Act extends ListActivity implements View.OnClickListener {

    public static enum TransitionType {
        SlideLeft
    }
    public static TransitionType transitionType;

    AlertDialog.Builder deletes;

    private static final int DELETE_ID = Menu.FIRST + 1;

    Database database;
    SimpleCursorAdapter simpleCursorAdapter;
    Cursor cursorTimes;
    TextView tv;
    Intent intent;
    ImageButton btnAdd;
    long show_id, del_time_id;
    String time, del_time;
    AdapterView.AdapterContextMenuInfo adapterContextMenuInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.getListView().setDividerHeight(1);
        database = new Database(this);
        database.open();
        btnAdd = (ImageButton) findViewById(R.id.btn_add_time);
        btnAdd.setOnClickListener(this);

        initDialogs();

        fillData();
    }

    private void fillData() {
        cursorTimes = database.getAllTimesData();
        startManagingCursor(cursorTimes);
        String[] from = new String[] { Database.TIMES_COL_TIME };
        int[] to = new int[] { R.id.times_item };
        simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.times_item, cursorTimes, from, to);
        setListAdapter(simpleCursorAdapter);
        registerForContextMenu(getListView());
        tv = (TextView) findViewById(R.id.times_empty);
        if (database.timesIsNotEmpty()) {
            tv.setText("");
        } else {
            tv.setText(R.string.empty);
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        show_id = id;
        String toExtras = database.getTime(show_id);
        this.finish();
        intent = new Intent(this, Act2.class);
        intent.putExtra("time", toExtras);
        startActivity(intent);
        transitionType = TransitionType.SlideLeft;
        overridePendingTransition(R.layout.slide_left_in, R.layout.slide_left_out);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case DELETE_ID:
                adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                del_time_id = adapterContextMenuInfo.id;
                del_time = database.getTime(del_time_id);
                deletes.show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, R.string.delete);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_time:
                this.finish();
                intent = new Intent(this, AddTimeAct.class);
                intent.putExtra("task", "add");
                startActivity(intent);
                transitionType = TransitionType.SlideLeft;
                overridePendingTransition(R.layout.slide_left_in, R.layout.slide_left_out);
                break;
        }
    }

    public void initDialogs() {
        deletes = new AlertDialog.Builder(this);
        deletes.setTitle(getResources().getString(R.string.deleting));
        deletes.setMessage(getResources().getString(R.string.confirm_deleting));
        deletes.setPositiveButton(getResources().getString(R.string.delete), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                database.deleteTime(del_time_id);
                database.deleteScheduleWithTime(del_time);
                fillData();
            }
        });
        deletes.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
            }
        });
        deletes.setCancelable(true);
        deletes.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }
}