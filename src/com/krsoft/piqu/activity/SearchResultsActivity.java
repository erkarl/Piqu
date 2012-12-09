package com.krsoft.piqu.activity;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.krsoft.piqu.R;
import com.krsoft.piqu.adapter.TweetListAdaptor;
import com.krsoft.piqu.data.Constants;
import com.krsoft.piqu.data.Tweet;
import com.krsoft.piqu.network.SearchResultsActivityTask;
import com.krsoft.piqu.util.SearchResultsActivityResultsListener;

public class SearchResultsActivity extends BaseListActivity implements
		SearchResultsActivityResultsListener {

	private ProgressDialog progressDialog;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences pref = getSharedPreferences(Constants.PREF_NAME,
				MODE_PRIVATE);
		String accessToken = pref.getString(Constants.PREF_KEY_ACCESS_TOKEN,
				null);
		String accessTokenSecret = pref.getString(
				Constants.PREF_KEY_ACCESS_TOKEN_SECRET, null);
		ArrayList<String> params = new ArrayList<String>();
		params.add(accessToken);
		params.add(accessTokenSecret);
		params.add(this.getIntent().getExtras()
				.getString(Constants.INTENT_EXTRA_SEARCH_QUERY));
		SearchResultsActivityTask tweetActivityTask = new SearchResultsActivityTask();
		tweetActivityTask.setOnResultsListener(SearchResultsActivity.this);
		progressDialog = ProgressDialog.show(SearchResultsActivity.this,
				getString(R.string.loading), getString(R.string.pleaseWait));
		tweetActivityTask.execute(params);

	}

	public void onResultsSucceeded(ArrayList<Tweet> result) {
		progressDialog.dismiss();
		if (result.isEmpty()) {
			Toast.makeText(SearchResultsActivity.this, R.string.noResultsFound,
					Toast.LENGTH_SHORT).show();
			Intent searchActivityIntent = new Intent(
					SearchResultsActivity.this, SearchActivity.class);
			startActivity(searchActivityIntent);
		} else {
			TweetListAdaptor adaptor = new TweetListAdaptor(this,
					R.layout.list_items, result);
			setListAdapter(adaptor);
		}
	}
}
