package com.example.liutao.alarm;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {
    private Button mButton;
    private SharedPreferences mySharedPreferences;
    private TextView mViewTime;
    private TextView mViewDay;

    @Override
    protected void onResume() {
        super.onResume();
        setTime();
        setDay();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mViewTime = (TextView) findViewById(R.id.show_time);
        mySharedPreferences = getSharedPreferences("alarm", Activity.MODE_PRIVATE);
        setTime();
        mButton = (Button) findViewById(R.id.arrange);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                arrange();
            }
        });
        mViewDay = (TextView) findViewById(R.id.alarm_day);
        setDay();
    }


    public void arrange() {

        Intent i = new Intent(this, Arrange.class);
        startActivity(i);


    }

    public void setTime() {
        String time = mySharedPreferences.getString("time", "00:00");
        mViewTime.setText(time);
    }

    public void setDay() {
        String day = mySharedPreferences.getString("day", "1,2,3,4,5,6,7");
        mViewDay.setText("day:" + " " + day);

    }
}
