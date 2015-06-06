package com.example.liutao.alarm;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by liutao on 2015/5/30.
 */
public class MyAlarmBroadCast extends BroadcastReceiver {

    private String days;
    private SharedPreferences sharedPreferences;
    private Calendar calendar;
    private String sub;

    @Override
    public void onReceive(Context context, Intent intent) {
        sharedPreferences = context.getSharedPreferences("alarm", Activity.MODE_PRIVATE);

        days = sharedPreferences.getString("day", "1-2-3-4-5-6-7");
        calendar = Calendar.getInstance();
        Log.d("serviceTest", "OnReceive");
        if (check(calendar.get(Calendar.DAY_OF_WEEK))) {


            Intent intent1 = new Intent(context, MainActivity2.class);

            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent1);
        }


    }


    public boolean check(int day) {

        day -= 1;
        if (day == 0) day = 7;
        sub = "" + day;
        return days.contains(sub);
    }

}
