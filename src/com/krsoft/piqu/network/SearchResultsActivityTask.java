package com.krsoft.piqu.network;

import java.util.ArrayList;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.os.AsyncTask;

import com.krsoft.piqu.data.Constants;
import com.krsoft.piqu.data.Tweet;
import com.krsoft.piqu.util.SearchResultsActivityResultsListener;

public class SearchResultsActivityTask extends AsyncTask<ArrayList<String>, Void, ArrayList<Tweet> > {

	SearchResultsActivityResultsListener listener;

	public void setOnResultsListener(SearchResultsActivityResultsListener listener) {
		this.listener = listener;
	}

	@Override
	protected ArrayList<Tweet> doInBackground(ArrayList<String>... params) {
		ArrayList<String> passed = params[0];
		String accessToken = passed.get(0);
		String accessTokenSecret = passed.get(1);
		String searchText = passed.get(2);
		ConfigurationBuilder confbuilder = new ConfigurationBuilder();
		Configuration conf = confbuilder
				.setOAuthConsumerKey(Constants.CONSUMER_KEY)
				.setOAuthConsumerSecret(Constants.CONSUMER_SECRET).build();
		Twitter mTwitter;
		mTwitter = new TwitterFactory(conf).getInstance();
		mTwitter.setOAuthAccessToken(new AccessToken(accessToken,
				accessTokenSecret));
		ArrayList<Tweet> searchItems = new ArrayList<Tweet>();
		try {
			Query query = new Query(searchText);
			QueryResult result = mTwitter.search(query);
			for (twitter4j.Tweet tweet : result.getTweets()) {
				Tweet resultTweet = new Tweet();
				resultTweet.author = tweet.getFromUser();
				resultTweet.content = tweet.getText();
				searchItems.add(resultTweet);
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return searchItems;
	}
	
	@Override
	protected void onPostExecute(ArrayList<Tweet> result) {
		listener.onResultsSucceeded(result);
		super.onPostExecute(result);
	}
}