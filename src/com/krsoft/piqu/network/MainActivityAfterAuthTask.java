package com.krsoft.piqu.network;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.krsoft.piqu.R;
import com.krsoft.piqu.activity.TwitterFeedActivity;
import com.krsoft.piqu.data.Constants;
import com.krsoft.piqu.data.TwitterAuthWrapper;

public class MainActivityAfterAuthTask extends
		AsyncTask<TwitterAuthWrapper, Void, Void> {
	
	private Context context;

	@Override
	protected Void doInBackground(TwitterAuthWrapper... params) {
		TwitterAuthWrapper twitterWrapper = params[0];
		RequestToken mRequestToken = twitterWrapper.mRequestToken;
		String oauthVerifier = twitterWrapper.verifier;
		context = twitterWrapper.context;
		AccessToken accessToken = null;
		try {
			ConfigurationBuilder confbuilder = new ConfigurationBuilder();
			Configuration conf = confbuilder
					.setOAuthConsumerKey(Constants.CONSUMER_KEY)
					.setOAuthConsumerSecret(Constants.CONSUMER_SECRET).build();
			Twitter mTwitter;
			mTwitter = new TwitterFactory(conf).getInstance();
			accessToken = mTwitter.getOAuthAccessToken(mRequestToken,
					oauthVerifier);
			SharedPreferences pref = context.getSharedPreferences(
					Constants.PREF_NAME, Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = pref.edit();
			editor.putString(Constants.PREF_KEY_ACCESS_TOKEN,
					accessToken.getToken());
			editor.putString(Constants.PREF_KEY_ACCESS_TOKEN_SECRET,
					accessToken.getTokenSecret());
			editor.commit();

		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		Toast.makeText(context, R.string.loggedIn, Toast.LENGTH_SHORT)
		.show();
		Intent twitterFeedActivityIntent = new Intent(context.getApplicationContext(),
				TwitterFeedActivity.class);
		twitterFeedActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		context.startActivity(twitterFeedActivityIntent);
		super.onPostExecute(result);
	}
}
