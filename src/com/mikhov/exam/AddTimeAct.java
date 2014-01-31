package com.mikhov.exam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class AddTimeAct extends Activity implements View.OnClickListener {

    public static enum TransitionType {
        SlideLeft
    }
    public static TransitionType transitionType;

    Intent intent;
    EditText addTime1, addTime2, addTime3, addTime4, addPeriod;
    Database database;
    Button save;
    String time = "", task = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_time);
        addTime1 = (EditText) findViewById(R.id.add_time1);
        addTime2 = (EditText) findViewById(R.id.add_time2);
        addTime3 = (EditText) findViewById(R.id.add_time3);
        addTime4 = (EditText) findViewById(R.id.add_time4);
        addPeriod = (EditText) findViewById(R.id.add_period);
        save = (Button)findViewById(R.id.btn_add_time_save);
        save.setOnClickListener(this);

        database = new Database(this);
        database.open();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_time_save:
                if (addTime1.getText().toString().equals("") || addTime2.getText().toString().equals("") || addTime3.getText().toString().equals("") || addTime4.getText().toString().equals("")) {
                    Toast.makeText(this, getResources().getString(R.string.fill_time), Toast.LENGTH_SHORT).show();
                } else if (addTime1.getText().toString().length() > 2 || addTime2.getText().toString().length() > 2 || addTime3.getText().toString().length() > 2 || addTime4.getText().toString().length() > 2) {
                    Toast.makeText(this, getResources().getString(R.string.incorrect_time), Toast.LENGTH_SHORT).show();
                } else if (addTime1.getText().toString().length() < 2 || addTime2.getText().toString().length() < 2 || addTime3.getText().toString().length() < 2 || addTime4.getText().toString().length() < 2) {
                    Toast.makeText(this, getResources().getString(R.string.incorrect_time), Toast.LENGTH_SHORT).show();
                } else {
                    int t1 = Integer.parseInt(addTime1.getText().toString());
                    int t2 = Integer.parseInt(addTime2.getText().toString());
                    int t3 = Integer.parseInt(addTime3.getText().toString());
                    int t4 = Integer.parseInt(addTime4.getText().toString());
                    int p = Integer.parseInt(addPeriod.getText().toString());
                    if (t1 > 23 || t2 > 59 || t3 > 23 || t4 > 59 || p > 59 || (t3 - t1 < 1 && p > t4 - t2)) {
                        Toast.makeText(this, getResources().getString(R.string.incorrect_time), Toast.LENGTH_SHORT).show();
                    } else {
                        String new_time = addTime1.getText().toString() + " : " + addTime2.getText().toString() + "  -  " + addTime3.getText().toString() + " : " + addTime4.getText().toString();
                        database.addTime(new_time, t1, t2, t3, t4, p);
                        String s1;
                        for (int i = 0; i <= 24; i++) {
                            if (i <= t3 && i >= t1) {
                                s1 = ((i + "").length() < 2) ? "0" + i : i + "";
                                if (i > t1 && i < t3) {
                                    for (int j = 0; j < 60; j += p) {
                                        String s2 = ((j + "").length() < 2) ? s1 + " : 0" + j : s1 + " : " + j;
                                        database.addSchedule(new_time, s2);
                                    }
                                } else if (i == t1 && i == t3) {
                                    for (int j = t2; j <= t4; j += p) {
                                        String s2 = ((j + "").length() < 2) ? s1 + " : 0" + j : s1 + " : " + j;
                                        database.addSchedule(new_time, s2);
                                    }
                                } else if (i == t1) {
                                    for (int j = t2; j < 60; j += p) {
                                        String s2 = ((j + "").length() < 2) ? s1 + " : 0" + j : s1 + " : " + j;
                                        database.addSchedule(new_time, s2);
                                    }
                                } else if (i == t3) {
                                    for (int j = 0; j <= t4; j += p) {
                                        String s2 = ((j + "").length() < 2) ? s1 + " : 0" + j : s1 + " : " + j;
                                        database.addSchedule(new_time, s2);
                                    }
                                }
                            }
                        }
                        this.finish();
                        intent = new Intent(this, Act.class);
                        startActivity(intent);
                        transitionType = TransitionType.SlideLeft;
                        overridePendingTransition(R.layout.slide_left_in, R.layout.slide_left_out);
                    }
                }
                break;
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