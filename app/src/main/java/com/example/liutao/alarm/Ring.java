package com.example.liutao.alarm;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class Ring extends ActionBarActivity implements View.OnClickListener {
    private FragmentManager fm;
    private static final String TAG = "RingActivity";
    private Button sureButton;
    private Button cancelButton;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public String back_name;
    public String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);
        fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {

            fragment = new ListFragment();

            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();

        }
        sureButton = (Button) findViewById(R.id.sure_button);
        sureButton.setOnClickListener(this);
        cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(this);
        sharedPreferences = getSharedPreferences("alarm", Activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ring, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, Arrange.class);
        switch (v.getId()) {
            case R.id.sure_button:
                ListFragment fragmentTemp = (ListFragment) getFragmentManager().findFragmentById(R.id.fragment_container);
                back_name = fragmentTemp.name;
                url = fragmentTemp.url;
                editor.putString("url", url);
                editor.putString("ring", back_name);
                editor.commit();
                startActivity(i);
                break;
            case R.id.cancel_button:

                startActivity(i);
                break;

        }
    }
}
