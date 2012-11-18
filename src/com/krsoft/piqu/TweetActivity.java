package com.krsoft.piqu;

import java.util.ArrayList;

import twitter4j.Twitter;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TweetActivity extends Activity implements ResultsListener {

	private Button tweetButton;
	private OnClickListener tweetButtonListener = new OnClickListener() {

		@SuppressWarnings("unchecked")
		public void onClick(View v) {
			EditText statusText = (EditText) findViewById(R.id.tweetEditText);
			String tweetText = statusText.getText().toString();
			SharedPreferences pref = getSharedPreferences(Constants.PREF_NAME,
					MODE_PRIVATE);
			String accessToken = pref.getString(
					Constants.PREF_KEY_ACCESS_TOKEN, null);
			String accessTokenSecret = pref.getString(
					Constants.PREF_KEY_ACCESS_TOKEN_SECRET, null);
			ArrayList<String> params = new ArrayList<String>();
			params.add(accessToken);
			params.add(accessTokenSecret);
			params.add(tweetText);
			TweetActivityTask tweetActivityTask = new TweetActivityTask();
			tweetActivityTask.setOnResultsListener(TweetActivity.this);
			tweetActivityTask.execute(params);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tweet);
		tweetButton = (Button) findViewById(R.id.submitTweetButton);
		tweetButton.setOnClickListener(tweetButtonListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_logout:
			Intent logoutActivityIntent = new Intent(this, LogoutActivity.class);
			startActivity(logoutActivityIntent);
			break;
		case R.id.menu_search:
			Intent searchActivityIntent = new Intent(this, SearchActivity.class);
			startActivity(searchActivityIntent);
			break;
		case R.id.menu_timeline:
			Intent twitterFeedActivityIntent = new Intent(this,
					TwitterFeedActivity.class);
			startActivity(twitterFeedActivityIntent);
			break;
		case R.id.menu_tweet:
			Intent tweetActivityIntent = new Intent(this, TweetActivity.class);
			startActivity(tweetActivityIntent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onResultsSucceeded() {
        Toast.makeText(this, "Tweeted", Toast.LENGTH_LONG).show();
		Intent twitterFeedActivityIntent = new Intent(this,
				TwitterFeedActivity.class);
		startActivity(twitterFeedActivityIntent);
	}

}
