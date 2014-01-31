package com.example.extraexam1;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.example.extraexam1.DataBase.TimeDB;

public class MyActivity extends Activity implements View.OnClickListener {
    /**
     * Called when the activity is first created.
     */

    public static final int DELETE = 1;
    TimeDB db;
    Cursor cursor;
    ListView listView;
    SimpleCursorAdapter adapter;

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);



        ImageButton addorder = (ImageButton) findViewById(R.id.addorder);
        addorder.setOnClickListener(this);



        listView = (ListView) findViewById(R.id.listView);

        registerForContextMenu(listView);
        String[] from = new String[] {
                TimeDB.COLUMN_START,
                TimeDB.COLUMN_FINISH,
                TimeDB.COLUMN_CYCLE,
        };
        int[] to = new int[] {
                R.id.starttime,
                R.id.finishtime,
                R.id.cycletime
        };
        db = new TimeDB(this);
        db.open();
        cursor = db.getAllData();


        Cursor cursor = db.getAllData();
        startManagingCursor(cursor);
        adapter = new SimpleCursorAdapter(this, R.layout.adapter, cursor, from, to);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String name = ((TextView)view.findViewById(R.id.starttime)).getText().toString();
                TimeDB carsSchedule = new TimeDB(view.getContext());
                carsSchedule.open();
                Buss result = carsSchedule.selectCars(name);
                Intent currentIntent = new Intent(view.getContext(), MoreInformActivity.class);
                currentIntent.putExtra("result", result);
                startActivity(currentIntent);
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE, 0, getString(R.string.DELETE_RSS));
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == DELETE) {
            AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            db.deleteChannel((int) adapterContextMenuInfo.id);

            TimeDB entryDatabase = new TimeDB(this);
            entryDatabase.open();
            entryDatabase.deleteChannel(adapterContextMenuInfo.id);
            entryDatabase.close();
            //--------refresh listView
            String[] from = new String[] {
                    TimeDB.COLUMN_START,
                    TimeDB.COLUMN_FINISH,
                    TimeDB.COLUMN_CYCLE,
            };
            int[] to = new int[] {
                    R.id.starttime,
                    R.id.finishtime,
                    R.id.cycletime
            };
            adapter = new SimpleCursorAdapter(this, R.layout.adapter, cursor, from, to);
            listView.setAdapter(adapter);

            cursor.requery();
            return true;
        }

        return super.onContextItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addorder:
                Intent intent = new Intent(this, AddCycleActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
        db.close();
    }
}
