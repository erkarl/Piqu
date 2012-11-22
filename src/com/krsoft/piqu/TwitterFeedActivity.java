package com.krsoft.piqu;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class TwitterFeedActivity extends BaseListActivity {
	private Twitter mTwitter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ArrayList<Tweet> items = new ArrayList<Tweet>();
		TweetListAdaptor adaptor = new TweetListAdaptor(this,
				R.layout.list_items, items);
		setListAdapter(adaptor);
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
		try {
			List<Status> statuses = mTwitter.getHomeTimeline();
			for (Status status2 : statuses) {
				// listAdapter.add(status2.getText());
				Tweet tweet = new Tweet();
				Log.i("Piqu", "User: " + status2.getUser().getName());
				tweet.author = "@" + status2.getUser().getName();
				Log.i("Piqu", "Name: " + status2.getText());
				tweet.content = status2.getText();
				Log.i("Piqu", "Adding: " + tweet.getAuthor() + tweet.getContent());
				items.add(tweet);
			}
		} catch (TwitterException te) {
			te.printStackTrace();
		}
		
	}
}