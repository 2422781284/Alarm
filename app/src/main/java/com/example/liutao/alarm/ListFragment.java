package com.example.liutao.alarm;

import android.app.Fragment;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class ListFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView list;
    private ListAdapter listAdapter;
    private List<Music> arrayList;
    private Player player;
    private boolean[] flags = new boolean[100];
    private int count = 0;
    private static final String TAG = "listFragment";
    public String url;
    public String name;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_layout, container, false);
        listAdapter = new ListAdapter();
        list = (ListView) v.findViewById(R.id.list);
        list.setAdapter(listAdapter);
        list.setOnItemClickListener(this);
        loadFileData();
        player = new Player();
        return v;
    }

    public void loadFileData() {
        ContentResolver resolver = getActivity().getContentResolver();
        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        arrayList = new ArrayList<Music>();
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                long mill = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));

                if (mill >= 1000 && mill <= 900000) {
                    String size = longToSize(mill);

                    Music music = new Music(name, size, url);
                    flags[count++] = true;
                    arrayList.add(music);
                }

            } while (cursor.moveToNext());
        }

        listAdapter.update(arrayList);
    }

    public String longToSize(long size) {
        int minute = (int) (size / 1000 / 60);
        int sec = (int) (size / 1000 - minute * 60);
        return minute + ":" + sec;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LinearLayout lay = (LinearLayout) view.findViewById(R.id.item_layout);
        url = arrayList.get(position).url;
        name = arrayList.get(position).name;
        player.url = url;
        if (flags[position] == true) {
            player.begin();
            player.trueBegin();
            lay.setBackgroundResource(R.drawable.music_ok);
            flags[position] = false;
        } else {
            player.endMusic();
            lay.setBackgroundResource(R.drawable.music_no);
            flags[position] = true;
        }
    }
}
