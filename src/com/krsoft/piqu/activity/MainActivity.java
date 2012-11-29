package com.krsoft.piqu.activity;

import twitter4j.auth.RequestToken;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.krsoft.piqu.R;
import com.krsoft.piqu.data.Constants;
import com.krsoft.piqu.data.TwitterAuthWrapper;
import com.krsoft.piqu.network.MainActivityAfterAuthTask;
import com.krsoft.piqu.network.MainActivityTask;
import com.krsoft.piqu.util.MainActivityResultsListener;

public class MainActivity extends BaseActivity implements
		MainActivityResultsListener {

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
			Toast.makeText(MainActivity.this, R.string.notLoggedIn,
					Toast.LENGTH_SHORT).show();
			twitterAuth();
		} else {

			Intent twitterFeedActivityIntent = new Intent(this,
					TwitterFeedActivity.class);
			startActivity(twitterFeedActivityIntent);

		}

	}

	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (requestCode == Constants.REQ_CODE_START_AUTH) {
			if (resultCode == RESULT_OK) {
				TwitterAuthWrapper helpParamForAsyncTask = new TwitterAuthWrapper(
						intent.getExtras().getString(
								Constants.IEXTRA_OAUTH_VERIFIER),
						mRequestToken, getApplicationContext());
				MainActivityAfterAuthTask mainActivityAfterAuthTask = new MainActivityAfterAuthTask();
				mainActivityAfterAuthTask.execute(helpParamForAsyncTask);
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(this, R.string.authCanceled, Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	private void twitterAuth() {
		MainActivityTask mainActivityTask = new MainActivityTask();
		mainActivityTask.setOnResultsListener(MainActivity.this);
		mainActivityTask.execute();
	}

	public void onResultsSucceeded(RequestToken result) {
		mRequestToken = result;
		Intent intent = new Intent(MainActivity.this, AuthActivity.class);
		intent.putExtra("auth_url", mRequestToken.getAuthorizationURL());
		startActivityForResult(intent, Constants.REQ_CODE_START_AUTH);
	}
}
