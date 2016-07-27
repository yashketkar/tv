package com.yashketkar.tv;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TVCAdapter extends ArrayAdapter<TVC> {

    int layoutResourceId;
    MainActivity mainActivity;
    ArrayList<TVC> tvc = new ArrayList<>();
    static int count;

    public TVCAdapter(MainActivity mainActivity,
                      int layoutResourceId, ArrayList<TVC> tvc) {
        super(mainActivity, R.layout.activity_main, tvc);
        this.mainActivity = mainActivity;
        this.layoutResourceId = layoutResourceId;
        this.tvc = tvc;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TVCHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = mainActivity.getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new TVCHolder();
            holder.channeltext = (TextView) row.findViewById(R.id.channeltext);
            holder.icon = (ImageView) row.findViewById(R.id.icon);
            row.setTag(holder);
        } else {
            holder = (TVCHolder) row.getTag();
        }
        TVC channel = tvc.get(position);
        holder.channeltext.setText(channel.name);
        holder.icon.setImageResource(channel.imageid);
        count++;
        Animation animation = AnimationUtils.loadAnimation(mainActivity,
                R.anim.push_up_in);
        animation.setDuration(250);
        row.startAnimation(animation);
        animation = null;
        return row;
    }

    static class TVCHolder {
        TextView channeltext;
        ImageView icon;
    }
}