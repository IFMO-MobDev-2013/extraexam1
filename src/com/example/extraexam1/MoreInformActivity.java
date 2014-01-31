package com.example.extraexam1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: javlon
 * Date: 31.01.14
 * Time: 15:40
 * To change this template use File | Settings | File Templates.
 */
public class MoreInformActivity extends Activity {

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moreinformation);
        Intent intent = getIntent();
        Buss buss = (Buss) intent.getSerializableExtra("result");
        TextView textView = (TextView) findViewById(R.id.textView);
        TextView textView1 = (TextView) findViewById(R.id.textView1);
        TextView textView2 = (TextView) findViewById(R.id.textView2);

        textView.setText(buss.getStartTime());
        textView1.setText(buss.getFinishTime());
        textView2.setText(buss.getTime());

    }
}
