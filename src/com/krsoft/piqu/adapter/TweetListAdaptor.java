package com.krsoft.piqu.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.krsoft.piqu.R;
import com.krsoft.piqu.data.Tweet;

public class TweetListAdaptor extends ArrayAdapter<Tweet> {

	private ArrayList<Tweet> tweets;

	public TweetListAdaptor(Context context, int textViewResourceId,
			ArrayList<Tweet> items) {
		super(context, textViewResourceId, items);
		this.tweets = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TweetsViewHolder viewHolder;
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.list_items, null);
			viewHolder = new TweetsViewHolder();
			viewHolder.tweetContent = (TextView) v.findViewById(R.id.toptext);
			viewHolder.tweeterName = (TextView) v.findViewById(R.id.bottomtext);
			v.setTag(viewHolder);
		} else {
			viewHolder = (TweetsViewHolder) v.getTag();
		}
		Tweet o = tweets.get(position);
		if (o != null) {
			viewHolder.tweetContent.setText(o.getContent());
			viewHolder.tweeterName.setText(o.getAuthor());
		}
		return v;
	}

	static class TweetsViewHolder {
		TextView tweeterName;
		TextView tweetContent;
	}
}