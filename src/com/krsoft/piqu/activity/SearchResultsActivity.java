package com.krsoft.piqu.activity;

import java.util.ArrayList;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.krsoft.piqu.R;
import com.krsoft.piqu.adapter.TweetListAdaptor;
import com.krsoft.piqu.data.Constants;
import com.krsoft.piqu.data.Tweet;
import com.krsoft.piqu.network.SearchResultsActivityTask;
import com.krsoft.piqu.util.SearchResultsActivityResultsListener;

public class SearchResultsActivity extends BaseListActivity implements SearchResultsActivityResultsListener {

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences pref = getSharedPreferences(
				Constants.PREF_NAME, MODE_PRIVATE);
		String accessToken = pref.getString(
				Constants.PREF_KEY_ACCESS_TOKEN, null);
		String accessTokenSecret = pref.getString(
				Constants.PREF_KEY_ACCESS_TOKEN_SECRET, null);
		ArrayList<String> params = new ArrayList<String>();
		params.add(accessToken);
		params.add(accessTokenSecret);
		params.add(this.getIntent().getExtras().getString("search_query"));
		SearchResultsActivityTask tweetActivityTask = new SearchResultsActivityTask();
		tweetActivityTask.setOnResultsListener(SearchResultsActivity.this);
		tweetActivityTask.execute(params);

		
	}
	
	public void onResultsSucceeded(ArrayList<Tweet> result) {
		Log.i("Piqu", "Result OK");
		TweetListAdaptor adaptor = new TweetListAdaptor(this,
				R.layout.list_items, result);
		setListAdapter(adaptor);
	}
}
