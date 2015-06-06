package com.example.liutao.alarm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by liutao on 2015/5/25.
 */
public class ListAdapter extends BaseAdapter {
    private List<Music> musicList;
    private int nowPosition = -1;

    @Override
    public int getCount() {
        if (musicList == null) {
            return 0;
        }
        return musicList.size();
    }

    @Override
    public Object getItem(int position) {
        return musicList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Holder holder;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null);
            holder = new Holder((TextView) view.findViewById(R.id.music_name), (TextView) view.findViewById(R.id.music_size));
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        holder.nameText.setText(musicList.get(position).name);
        holder.sizeText.setText(musicList.get(position).size);
        if (nowPosition == position) {
            view.setBackgroundResource(R.drawable.music_ok);
        } else {
            view.setBackgroundResource(R.drawable.music_no);
        }
        return view;
    }

    private class Holder {
        public TextView nameText;
        public TextView sizeText;

        public Holder(TextView nameText, TextView sizeText) {
            this.nameText = nameText;
            this.sizeText = sizeText;
        }
    }

    public void update(List<Music> musicList) {
        this.musicList = musicList;
        notifyDataSetChanged();
    }

    public void playView(int nowPosition) {
        this.nowPosition = nowPosition;
        notifyDataSetChanged();
    }
}
