package com.krsoft.piqu.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.krsoft.piqu.R;
import com.krsoft.piqu.Tweet;

public class TweetListAdaptor extends ArrayAdapter<Tweet> {
	private ArrayList<Tweet> tweets;

	public TweetListAdaptor(Context context, int textViewResourceId,
			ArrayList<Tweet> items) {
		super(context, textViewResourceId, items);
		this.tweets = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.list_items, null);
		}
		Tweet o = tweets.get(position);
		TextView tt = (TextView) v.findViewById(R.id.toptext);
		TextView bt = (TextView) v.findViewById(R.id.bottomtext);
		tt.setText(o.getContent());
		bt.setText(o.getAuthor());
		return v;
	}
}