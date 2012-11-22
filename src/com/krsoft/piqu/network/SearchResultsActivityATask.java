package com.krsoft.piqu.network;
//import twitter4j.Query;
//import twitter4j.QueryResult;
//import twitter4j.TwitterFactory;
//import twitter4j.auth.AccessToken;
//import twitter4j.conf.Configuration;
//import twitter4j.conf.ConfigurationBuilder;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.os.AsyncTask;
//import android.util.Log;
//
//import com.krsoft.piqu.Constants;
//import com.krsoft.piqu.Tweet;
//
//
//public class SearchResultsActivityATask extends AsyncTask {
//
//	@Override
//	protected Object doInBackground(Object... params) {
//		SharedPreferences pref = getSharedPreferences(
//				Constants.PREF_NAME, MODE_PRIVATE);
//		String accessToken = pref.getString(
//				Constants.PREF_KEY_ACCESS_TOKEN, null);
//		String accessTokenSecret = pref.getString(
//				Constants.PREF_KEY_ACCESS_TOKEN_SECRET, null);
//		ConfigurationBuilder confbuilder = new ConfigurationBuilder();
//		Configuration conf = confbuilder
//				.setOAuthConsumerKey(Constants.CONSUMER_KEY)
//				.setOAuthConsumerSecret(Constants.CONSUMER_SECRET)
//				.build();
//		mTwitter = new TwitterFactory(conf).getInstance();
//		mTwitter.setOAuthAccessToken(new AccessToken(accessToken,
//				accessTokenSecret));
//		Query query = new Query(searchQuery);
//		Log.i("Piqu", "Searching: " + searchQuery);
//		QueryResult result = mTwitter.search(query);
//		for (twitter4j.Tweet tweet : result.getTweets()) {
//			Tweet resultTweet = new Tweet();
//			Log.i("Piqu",
//					(tweet.getFromUser() + ":" + tweet.getText()));
//			resultTweet.author = "@" + tweet.getFromUser();
//			resultTweet.content = tweet.getText();
//			searchItems.add(resultTweet);
//			
//		}
//		return null;
//	}
//
//}
