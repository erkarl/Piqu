package com.krsoft.piqu.network;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.os.AsyncTask;

import com.krsoft.piqu.data.Constants;
import com.krsoft.piqu.data.Tweet;
import com.krsoft.piqu.util.TwitterFeedActivityResultsListener;

public class TwitterFeedActivityTask extends
		AsyncTask<ArrayList<String>, Void, ArrayList<Tweet>> {

	TwitterFeedActivityResultsListener listener;

	public void setOnResultsListener(TwitterFeedActivityResultsListener listener) {
		this.listener = listener;
	}

	@Override
	protected ArrayList<Tweet> doInBackground(ArrayList<String>... params) {
		ArrayList<String> passed = params[0];
		String accessToken = passed.get(0);
		String accessTokenSecret = passed.get(1);
		ConfigurationBuilder confbuilder = new ConfigurationBuilder();
		Configuration conf = confbuilder
				.setOAuthConsumerKey(Constants.CONSUMER_KEY)
				.setOAuthConsumerSecret(Constants.CONSUMER_SECRET).build();
		Twitter mTwitter;
		mTwitter = new TwitterFactory(conf).getInstance();
		mTwitter.setOAuthAccessToken(new AccessToken(accessToken,
				accessTokenSecret));
		ArrayList<Tweet> items = new ArrayList<Tweet>();
		try {
			List<twitter4j.Status> statuses = mTwitter.getHomeTimeline();
			for (twitter4j.Status status2 : statuses) {
				Tweet tweet = new Tweet();
				tweet.author = status2.getUser().getName();
				tweet.content = status2.getText();
				items.add(tweet);
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return items;
	}

	@Override
	protected void onPostExecute(ArrayList<Tweet> result) {
		listener.onResultsSucceeded(result);
		super.onPostExecute(result);
	}
}