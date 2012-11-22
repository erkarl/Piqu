package com.krsoft.piqu;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends BaseActivity {

	private Twitter mTwitter;
	private RequestToken mRequestToken;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		SharedPreferences pref = getSharedPreferences(Constants.PREF_NAME,
				MODE_PRIVATE);
		String accessToken = pref.getString(Constants.PREF_KEY_ACCESS_TOKEN,
				null);
		String accessTokenSecret = pref.getString(
				Constants.PREF_KEY_ACCESS_TOKEN_SECRET, null);
		if (accessToken == null || accessTokenSecret == null) {
			Toast.makeText(MainActivity.this, "not authorize yet",
					Toast.LENGTH_SHORT).show();
			twitterAuth();
		} else {
			ConfigurationBuilder confbuilder = new ConfigurationBuilder();
			Configuration conf = confbuilder
					.setOAuthConsumerKey(Constants.CONSUMER_KEY)
					.setOAuthConsumerSecret(Constants.CONSUMER_SECRET).build();
			mTwitter = new TwitterFactory(conf).getInstance();
			mTwitter.setOAuthAccessToken(new AccessToken(accessToken,
					accessTokenSecret));
			Intent twitterFeedActivityIntent = new Intent(this,
					TwitterFeedActivity.class);
			startActivity(twitterFeedActivityIntent);

		}

	}

	public void logOut() {
		SharedPreferences pref = getSharedPreferences(Constants.PREF_NAME,
				MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.remove(Constants.PREF_KEY_ACCESS_TOKEN);
		editor.remove(Constants.PREF_KEY_ACCESS_TOKEN_SECRET);
		editor.commit();

		if (mTwitter != null) {
			mTwitter.shutdown();
		}

		Toast.makeText(MainActivity.this, "unauthorized", Toast.LENGTH_SHORT)
				.show();
	}

	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				AccessToken accessToken = null;
				try {
					String oauthVerifier = intent.getExtras().getString(
							Constants.IEXTRA_OAUTH_VERIFIER);
					accessToken = mTwitter.getOAuthAccessToken(mRequestToken,
							oauthVerifier);
					SharedPreferences pref = getSharedPreferences(
							Constants.PREF_NAME, MODE_PRIVATE);
					SharedPreferences.Editor editor = pref.edit();
					editor.putString(Constants.PREF_KEY_ACCESS_TOKEN,
							accessToken.getToken());
					editor.putString(Constants.PREF_KEY_ACCESS_TOKEN_SECRET,
							accessToken.getTokenSecret());
					editor.commit();

					Toast.makeText(this, "authorized", Toast.LENGTH_SHORT)
							.show();
					Intent twitterFeedActivityIntent = new Intent(this,
							TwitterFeedActivity.class);
					startActivity(twitterFeedActivityIntent);
				} catch (TwitterException e) {
					e.printStackTrace();
				}
			} else if (resultCode == RESULT_CANCELED) {
				Log.w("Piqu", "Twitter auth canceled.");
			}
		}
	}

	private void twitterAuth() {

		new Thread(new Runnable() {
			public void run() {
				ConfigurationBuilder confbuilder = new ConfigurationBuilder();
				Configuration conf = confbuilder
						.setOAuthConsumerKey(Constants.CONSUMER_KEY)
						.setOAuthConsumerSecret(Constants.CONSUMER_SECRET)
						.build();
				mTwitter = new TwitterFactory(conf).getInstance();
				mTwitter.setOAuthAccessToken(null);
				try {
					mRequestToken = mTwitter
							.getOAuthRequestToken(Constants.CALLBACK_URL);
					Intent intent = new Intent(MainActivity.this, Auth.class);
					intent.putExtra("auth_url",
							mRequestToken.getAuthorizationURL());
					startActivityForResult(intent, 0);
				} catch (TwitterException e) {
					e.printStackTrace();
				}

			}
		}).start();

	}
}
