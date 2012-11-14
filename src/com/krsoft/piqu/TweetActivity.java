package com.krsoft.piqu;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
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

public class TweetActivity extends Activity {

	private Button tweetButton;
	private Twitter mTwitter;
	private OnClickListener tweetButtonListener = new OnClickListener() {

		public void onClick(View v) {
			try {
				SharedPreferences pref = getSharedPreferences(Constants.PREF_NAME,
						MODE_PRIVATE);
				String accessToken = pref.getString(Constants.PREF_KEY_ACCESS_TOKEN,
						null);
				String accessTokenSecret = pref.getString(
						Constants.PREF_KEY_ACCESS_TOKEN_SECRET, null);
				ConfigurationBuilder confbuilder = new ConfigurationBuilder();
				Configuration conf = confbuilder
						.setOAuthConsumerKey(Constants.CONSUMER_KEY)
						.setOAuthConsumerSecret(Constants.CONSUMER_SECRET).build();
				mTwitter = new TwitterFactory(conf).getInstance();
				mTwitter.setOAuthAccessToken(new AccessToken(accessToken,
						accessTokenSecret));
				EditText statusText = (EditText)findViewById(R.id.tweetEditText);
	            String status = statusText.getText().toString();
				mTwitter.updateStatus(status);
				Toast.makeText(TweetActivity.this, "tweeted", Toast.LENGTH_SHORT).show();
                statusText.setText(null);
    			Intent twitterFeedActivityIntent = new Intent(TweetActivity.this, TwitterFeedActivity.class);
    			startActivity(twitterFeedActivityIntent);
			} catch (TwitterException e) {
				e.printStackTrace();
			}

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
			Intent twitterFeedActivityIntent = new Intent(this, TwitterFeedActivity.class);
			startActivity(twitterFeedActivityIntent);
			break;
		case R.id.menu_tweet:
			Intent tweetActivityIntent = new Intent(this, TweetActivity.class);
			startActivity(tweetActivityIntent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
