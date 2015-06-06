package com.example.liutao.alarm;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Select extends Fragment {

    private static final String TAG = "SelectFragment";

    private Button button;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_select, container, false);
        button = (Button) v.findViewById(R.id.select_file);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment frg = new ListFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, frg).commit();
            }
        });
        Log.d(TAG, "fragmentinflate");
        return v;
    }


}
