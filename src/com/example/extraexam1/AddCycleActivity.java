package com.example.extraexam1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.extraexam1.DataBase.TimeDB;

/**
 * Created with IntelliJ IDEA.
 * User: javlon
 * Date: 31.01.14
 * Time: 15:29
 * To change this template use File | Settings | File Templates.
 */
public class AddCycleActivity extends Activity implements View.OnClickListener {



    TimeDB database;
    EditText editTextStart,editTextFinish, editTextCycle;
    String  spinner1, delivery;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcycle);

        Button butinfcar = (Button) findViewById(R.id.butinfcar);
        butinfcar.setOnClickListener(this);

        editTextStart = (EditText) findViewById(R.id.editTextStart);
        editTextFinish = (EditText) findViewById(R.id.editTextFinish);
        editTextCycle = (EditText) findViewById(R.id.editTextCycle);

        database = new TimeDB(this);
        database.open();


    }
    @Override
    public void onClick(View v) {

        String start = editTextStart.getText().toString();
        String finish = editTextFinish.getText().toString();
        String cycle = editTextCycle.getText().toString();
        database.addChannel(start, finish, cycle);


        Intent intent = new Intent(this, MyActivity.class);
        startActivity(intent);
    }

}
