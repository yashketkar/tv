package com.yashketkar.tv;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ImageView;

public class TVChannelCustomAdapter extends ArrayAdapter<TVC> {

	Context context;
	int layoutResourceId;
	ListActivity act;

	ArrayList<TVC> tvc = new ArrayList<TVC>();
	static int count;
	TextView channeltext;
	ImageView icon;

	public TVChannelCustomAdapter(Context context, ListActivity act,
			int layoutResourceId, ArrayList<TVC> tvc) {
		super(context, R.layout.activity_list, tvc);

		this.context = context;
		this.act = act;
		this.layoutResourceId = layoutResourceId;
		this.tvc = tvc;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		TVCHolder holder = null;

		if (row == null) {

			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new TVCHolder();
			holder.channeltext = (TextView) row.findViewById(R.id.channeltext);
			holder.icon = (ImageView) row.findViewById(R.id.icon);
			row.setTag(holder);
		} else {
			holder = (TVCHolder) row.getTag();
		}

		TVC channel = tvc.get(position);
		Typeface tf = Typeface.createFromAsset(context.getAssets(),
				"fonts/RobotoCondensed-Bold.ttf");
		holder.channeltext.setTypeface(tf);
		holder.channeltext.setText(channel.name);
		holder.icon.setImageResource(channel.imageid);

		count++;

		Animation animation = AnimationUtils.loadAnimation(context,
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