package com.krsoft.piqu.activity;

import java.util.ArrayList;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.krsoft.piqu.R;
import com.krsoft.piqu.adapter.TweetListAdaptor;
import com.krsoft.piqu.data.Constants;
import com.krsoft.piqu.data.Tweet;
import com.krsoft.piqu.network.TwitterFeedActivityTask;
import com.krsoft.piqu.util.TwitterFeedActivityResultsListener;

public class TwitterFeedActivity extends BaseListActivity implements
		TwitterFeedActivityResultsListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		displayTwitterFeed();
	}

	public void onResultsSucceeded(ArrayList<Tweet> result) {
		TweetListAdaptor adaptor = new TweetListAdaptor(this,
				R.layout.list_items, result);
		setListAdapter(adaptor);
	}
	
	@SuppressWarnings("unchecked")
	public void displayTwitterFeed(){
		SharedPreferences pref = getSharedPreferences(Constants.PREF_NAME,
				MODE_PRIVATE);
		String accessToken = pref.getString(Constants.PREF_KEY_ACCESS_TOKEN,
				null);
		String accessTokenSecret = pref.getString(
				Constants.PREF_KEY_ACCESS_TOKEN_SECRET, null);
		ArrayList<String> params = new ArrayList<String>();
		params.add(accessToken);
		params.add(accessTokenSecret);
		TwitterFeedActivityTask tweetActivityTask = new TwitterFeedActivityTask();
		tweetActivityTask.setOnResultsListener(TwitterFeedActivity.this);
		tweetActivityTask.execute(params);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		displayTwitterFeed();
	}
}