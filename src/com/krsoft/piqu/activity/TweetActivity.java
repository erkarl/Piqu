package com.krsoft.piqu.activity;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.krsoft.piqu.R;
import com.krsoft.piqu.data.Constants;
import com.krsoft.piqu.network.TweetActivityTask;
import com.krsoft.piqu.util.TweetActivityResultsListener;

public class TweetActivity extends BaseActivity implements
		TweetActivityResultsListener {

	private ProgressDialog progressDialog;
	
	private OnClickListener tweetButtonListener = new OnClickListener() {

		@SuppressWarnings("unchecked")
		public void onClick(View v) {
			if (!(isLoggedIn())) {
				Intent mainActivity = new Intent(TweetActivity.this,
						MainActivity.class);
				startActivity(mainActivity);
			} else {
				EditText statusText = (EditText) findViewById(R.id.tweetEditText);
				String tweetText = statusText.getText().toString();
				if ("".equals(tweetText)) {
					Toast.makeText(TweetActivity.this, R.string.tweetFail,
							Toast.LENGTH_LONG).show();
				} else if (tweetText.length() > Constants.MAX_TWEET_LENGTH) {
					Toast.makeText(TweetActivity.this,
							R.string.tweetLengthFail, Toast.LENGTH_LONG).show();
				} else {
					SharedPreferences pref = getSharedPreferences(
							Constants.PREF_NAME, MODE_PRIVATE);
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
					progressDialog = ProgressDialog.show(TweetActivity.this, getString(R.string.loading), getString(R.string.pleaseWait));
					tweetActivityTask.execute(params);
				}

			}
		}

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tweet);
		Button tweetButton;
		tweetButton = (Button) findViewById(R.id.submitTweetButton);
		tweetButton.setOnClickListener(tweetButtonListener);
	}

	public void onResultsSucceeded() {
		progressDialog.dismiss();
		Toast.makeText(this, R.string.tweetSuccess, Toast.LENGTH_LONG).show();
		Intent twitterFeedActivityIntent = new Intent(this,
				TwitterFeedActivity.class);
		startActivity(twitterFeedActivityIntent);
	}

}
