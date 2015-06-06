package com.example.liutao.alarm;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by liutao on 2015/5/10.
 */
public class Arrange extends Activity implements View.OnTouchListener, CompoundButton.OnCheckedChangeListener, View.OnClickListener, DialogInterface.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private SharedPreferences mySharedPreferences;
    private SharedPreferences.Editor editor;
    private TextView mTime;
    private String time;
    private Rview hView;
    private Rview mView;
    private Intent ring;
    public String[] tempStringTime = new String[2];
    public int[] tempIntTime = new int[2];
    private int id;
    private static final String TAG = "ARRANGE";
    private float[] edges = new float[2];
    private String str1;
    private String str2;
    private int add = 0;
    private ToggleButton toggleButton;
    private RelativeLayout layout;
    private SoundPool soundPool;
    private int music;
    private String temp;
    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;
    private AlertDialog.Builder builder;
    private View volumeView;
    private String alarmDay;
    private String[] days;
    private CheckBox box1;
    private CheckBox box2;
    private CheckBox box3;
    private CheckBox box4;
    private CheckBox box5;
    private CheckBox box6;
    private CheckBox box7;
    private TextView ringName;
    private SeekBar seekBar;
    private int volume;
    private Button back;
    private Calendar calendar;
    private Intent intent;
    private PendingIntent pendingIntent;


    private AlarmManager alarmManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_arr);
        mySharedPreferences = getSharedPreferences("alarm", Activity.MODE_PRIVATE);
        editor = mySharedPreferences.edit();
        time = mySharedPreferences.getString("time", "00:00");
        mTime = (TextView) findViewById(R.id.time);
        mTime.setText(time);
        convertStringToTime(time);

        toggleButton = (ToggleButton) findViewById(R.id.model);
        toggleButton.setOnCheckedChangeListener(this);
        hView = (Rview) findViewById(R.id.hView);
        hView.setOnTouchListener(this);
        convertTimeToHedge();
        hView.rotate(edges[0]);
        mView = (Rview) findViewById(R.id.mView);
        mView.setOnTouchListener(this);
        convertTimeToMedge();
        mView.rotate(edges[1]);
        layout = (RelativeLayout) findViewById(R.id.relative);
        soundPool = new SoundPool(1, AudioManager.STREAM_RING, 5);
        music = soundPool.load(this, R.raw.rotate, 3);
        ring = new Intent(this, Ring.class);
        linearLayout1 = (LinearLayout) findViewById(R.id.ring);
        linearLayout1.setOnClickListener(this);
        linearLayout2 = (LinearLayout) findViewById(R.id.volume);
        linearLayout2.setOnClickListener(this);

        back = (Button) findViewById(R.id.no);
        back.setOnClickListener(this);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        intent = new Intent(Arrange.this, MyAlarmBroadCast.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);


        box1 = (CheckBox) findViewById(R.id.day_1);
        box2 = (CheckBox) findViewById(R.id.day_2);
        box3 = (CheckBox) findViewById(R.id.day_3);
        box4 = (CheckBox) findViewById(R.id.day_4);
        box5 = (CheckBox) findViewById(R.id.day_5);
        box6 = (CheckBox) findViewById(R.id.day_6);
        box7 = (CheckBox) findViewById(R.id.day_7);
        setDay();
        ringName = (TextView) findViewById(R.id.ring_name);
        ringName.setText(mySharedPreferences.getString("ring", "星期五的早安.mp3"));
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void editTime(String string) {
        editor.putString("time", string);
        editor.commit();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        id = v.getId();

        if (id == R.id.hView) {
            hView.onTouchEvent(event);

            convertHedgeToTime(hView.mdegree);
            makeShow();

        } else {
            mView.onTouchEvent(event);

            convertMedgeToTime(mView.mdegree);
            makeShow();
        }
        return true;
    }

    public void convertStringToTime(String time) {
        tempStringTime = time.split(":");
        tempIntTime[0] = Integer.parseInt(tempStringTime[0]);
        tempIntTime[1] = Integer.parseInt(tempStringTime[1]);

    }

    public void convertTimeToHedge() {
        edges[0] = (tempIntTime[0] % 12) * 30 + tempIntTime[1] / 2 - 180;
    }

    public void convertTimeToMedge() {
        edges[1] = tempIntTime[1] * 6 - 180;
    }

    public void convertHedgeToTime(float hedge) {

        tempIntTime[0] = (int) ((180 + hedge) / 30) + add;


    }

    public void convertMedgeToTime(float medge) {
        tempIntTime[1] = (int) ((180 + medge) / 6);
    }

    public void makeShow() {
        temp = time;
        str1 = tempIntTime[0] < 10 ? ("0" + tempIntTime[0]) : ("" + tempIntTime[0]);
        str2 = tempIntTime[1] < 10 ? ("0" + tempIntTime[1]) : ("" + tempIntTime[1]);
        time = str1 + ":" + str2;
        if (!temp.equals(time)) {
            soundPool.play(music, 1, 1, 3, 0, 1);
        }
        mTime.setText(time);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            add = 0;
            layout.setBackgroundResource(R.drawable.morningtrue);
        } else {
            add = 12;
            layout.setBackgroundResource(R.drawable.evening);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ring:

                startActivity(ring);
                break;
            case R.id.volume:
                volumeView = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog, null);

                builder = new AlertDialog.Builder(this);
                builder.setTitle("闹钟音量");
                builder.setView(volumeView);
                builder.setPositiveButton("确定", this);
                builder.setNegativeButton("取消", this);
                builder.create();
                builder.show();
                seekBar = (SeekBar) volumeView.findViewById(R.id.seek);
                seekBar.setProgress(mySharedPreferences.getInt("volume", 50));
                seekBar.setOnSeekBarChangeListener(this);
                break;
            case R.id.no:
                alarmDay = "";
                if (box1.isChecked() == true) {
                    alarmDay += "1-";
                }
                if (box2.isChecked() == true) {
                    alarmDay += "2-";
                }
                if (box3.isChecked() == true) {
                    alarmDay += "3-";
                }
                if (box4.isChecked() == true) {
                    alarmDay += "4-";
                }
                if (box5.isChecked() == true) {
                    alarmDay += "5-";
                }
                if (box6.isChecked() == true) {
                    alarmDay += "6-";
                }
                if (box7.isChecked() == true) {
                    alarmDay += "7-";
                }
                if (alarmDay.length() > 0)
                    alarmDay = alarmDay.substring(0, alarmDay.length() - 1);
                editor.putString("day", alarmDay);

                editor.commit();
                editTime(time);
                makeAlarm();
                startActivity(new Intent(Arrange.this, MainActivity.class));
                break;


        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                editor.putInt("volume", volume);
                editor.commit();
                ((ViewGroup) volumeView).removeView(seekBar);
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                ((ViewGroup) volumeView).removeView(seekBar);
                break;
        }
    }

    public void setDay() {
        alarmDay = mySharedPreferences.getString("day", "1-2-3-4-5-6-7");
        days = alarmDay.split("-");
        for (int i = 0; i < days.length; i++) {
            checkDay(days[i]);
        }

    }

    public void checkDay(String day) {
        switch (day) {
            case "1":
                box1.setChecked(true);
                break;
            case "2":
                box2.setChecked(true);
                break;
            case "3":
                box3.setChecked(true);
                break;
            case "4":
                box4.setChecked(true);
                break;
            case "5":
                box5.setChecked(true);
                break;
            case "6":
                box6.setChecked(true);
                break;
            case "7":
                box7.setChecked(true);
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        volume = progress;


    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void makeAlarm() {

        alarmManager.cancel(pendingIntent);
        calendar = Calendar.getInstance();
        int a = Integer.parseInt(str1);
        int b = Integer.parseInt(str2);
        calendar.set(Calendar.HOUR_OF_DAY, a);
        calendar.set(Calendar.MINUTE, b);
        calendar.set(Calendar.SECOND, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 30 * 1000, pendingIntent);

    }


}
