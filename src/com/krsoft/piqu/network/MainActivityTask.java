package com.krsoft.piqu.network;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.os.AsyncTask;

import com.krsoft.piqu.data.Constants;
import com.krsoft.piqu.util.MainActivityResultsListener;

public class MainActivityTask extends AsyncTask<Void, Void, RequestToken>{
	MainActivityResultsListener listener;

	public void setOnResultsListener(MainActivityResultsListener listener) {
		this.listener = listener;
	}

	@Override
	protected RequestToken doInBackground(Void... params) {
		ConfigurationBuilder confbuilder = new ConfigurationBuilder();
		Configuration conf = confbuilder
				.setOAuthConsumerKey(Constants.CONSUMER_KEY)
				.setOAuthConsumerSecret(Constants.CONSUMER_SECRET)
				.build();
		Twitter mTwitter;
		RequestToken mRequestToken = null;
		mTwitter = new TwitterFactory(conf).getInstance();
		mTwitter.setOAuthAccessToken(null);
		try {
			mRequestToken = mTwitter
					.getOAuthRequestToken(Constants.CALLBACK_URL);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return mRequestToken;
	}
	
	@Override
	protected void onPostExecute(RequestToken mRequestToken) {
		listener.onResultsSucceeded(mRequestToken);
		super.onPostExecute(mRequestToken);
	}
}
