package com.krsoft.piqu.network;

import java.util.ArrayList;

import com.krsoft.piqu.data.Constants;
import com.krsoft.piqu.util.TweetActivityResultsListener;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.os.AsyncTask;

public class TweetActivityTask extends AsyncTask<ArrayList<String>, Void, Void> {

    TweetActivityResultsListener listener;

    public void setOnResultsListener(TweetActivityResultsListener listener) {
        this.listener = listener;
    }

	@Override
	protected Void doInBackground(ArrayList<String>... params) {
		ArrayList<String> passed = params[0];
		String accessToken = passed.get(0);
		String accessTokenSecret = passed.get(1);
		String tweetText = passed.get(2);
		ConfigurationBuilder confbuilder = new ConfigurationBuilder();
		Configuration conf = confbuilder
				.setOAuthConsumerKey(Constants.CONSUMER_KEY)
				.setOAuthConsumerSecret(Constants.CONSUMER_SECRET).build();
		Twitter mTwitter;
		mTwitter = new TwitterFactory(conf).getInstance();
		mTwitter.setOAuthAccessToken(new AccessToken(accessToken,
				accessTokenSecret));
		try {
			mTwitter.updateStatus(tweetText);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		listener.onResultsSucceeded();
		super.onPostExecute(result);
	}

}
