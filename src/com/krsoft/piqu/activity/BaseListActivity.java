package com.krsoft.piqu.activity;

import android.content.Intent;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.krsoft.piqu.R;
import com.krsoft.piqu.data.Constants;

public class BaseListActivity extends SherlockListActivity {

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(Constants.ACTIONBAR_MAIN_GROUP, Constants.ACTIONBAR_TWEET,
				Constants.ACTIONBAR_FIRST_ITEM, R.string.tweet)
				.setIcon(R.drawable.ic_compose)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		menu.add(Constants.ACTIONBAR_MAIN_GROUP, Constants.ACTIONBAR_SEARCH,
				Constants.ACTIONBAR_SECOND_ITEM, R.string.search)
				.setIcon(R.drawable.ic_action_search)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		menu.add(Constants.ACTIONBAR_MAIN_GROUP, Constants.ACTIONBAR_AUTH,
				Constants.ACTIONBAR_THIRD_ITEM, R.string.auth)
				.setIcon(R.drawable.ic_auth)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			Intent tweetActivityIntent = new Intent(this, TweetActivity.class);
			startActivity(tweetActivityIntent);
			break;
		case 2:
			Intent searchActivityIntent = new Intent(this, SearchActivity.class);
			startActivity(searchActivityIntent);
			break;
		case 3:
			Intent logoutActivityIntent = new Intent(this, LogoutActivity.class);
			startActivity(logoutActivityIntent);
			break;
		case 4:
			Intent twitterFeedActivityIntent = new Intent(this,
					TwitterFeedActivity.class);
			startActivity(twitterFeedActivityIntent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
