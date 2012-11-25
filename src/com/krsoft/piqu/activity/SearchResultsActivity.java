package com.krsoft.piqu.activity;

import java.util.ArrayList;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.krsoft.piqu.R;
import com.krsoft.piqu.adapter.TweetListAdaptor;
import com.krsoft.piqu.data.Constants;
import com.krsoft.piqu.data.Tweet;

public class SearchResultsActivity extends BaseListActivity {

	private Twitter mTwitter;
	private String searchQuery;
	private ArrayList<Tweet> searchItems = new ArrayList<Tweet>();
	private boolean isThreadFinished = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		searchQuery = this.getIntent().getExtras().getString("search_query");
		new Thread(new Runnable() {
			public void run() {
				try {
					SharedPreferences pref = getSharedPreferences(
							Constants.PREF_NAME, MODE_PRIVATE);
					String accessToken = pref.getString(
							Constants.PREF_KEY_ACCESS_TOKEN, null);
					String accessTokenSecret = pref.getString(
							Constants.PREF_KEY_ACCESS_TOKEN_SECRET, null);
					ConfigurationBuilder confbuilder = new ConfigurationBuilder();
					Configuration conf = confbuilder
							.setOAuthConsumerKey(Constants.CONSUMER_KEY)
							.setOAuthConsumerSecret(Constants.CONSUMER_SECRET)
							.build();
					mTwitter = new TwitterFactory(conf).getInstance();
					mTwitter.setOAuthAccessToken(new AccessToken(accessToken,
							accessTokenSecret));
					Query query = new Query(searchQuery);
					QueryResult result = mTwitter.search(query);
					for (twitter4j.Tweet tweet : result.getTweets()) {
						Tweet resultTweet = new Tweet();
						resultTweet.author = "@" + tweet.getFromUser();
						resultTweet.content = tweet.getText();
						searchItems.add(resultTweet);
						
					}
					isThreadFinished = true;
					
				} catch (TwitterException e) {
					e.printStackTrace();
				}
			}
		}).start();
		while(!(isThreadFinished)) {
		}
		TweetListAdaptor adaptor = new TweetListAdaptor(this,
				R.layout.list_items, searchItems);
		setListAdapter(adaptor);
		
	}
}
