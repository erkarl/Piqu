package com.krsoft.piqu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SearchActivity extends Activity {
	
	private Button searchButton;
	
	private OnClickListener searchButtonListener = new OnClickListener() {
		
		public void onClick(View v) {
			Intent searchActivityResults = new Intent(SearchActivity.this, SearchResultsActivity.class);
			EditText searchEditText = (EditText)findViewById(R.id.searchEditText);
            String searchText = searchEditText.getText().toString();
			searchActivityResults.putExtra("search_query", searchText);
			startActivity(searchActivityResults);
			
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
		searchButton = (Button) findViewById(R.id.submitSearchButton);
		searchButton.setOnClickListener(searchButtonListener);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_logout:
			Intent logoutActivityIntent = new Intent(this, LogoutActivity.class);
			startActivity(logoutActivityIntent);
			break;
		case R.id.menu_search:
			Intent searchActivityIntent = new Intent(this, SearchActivity.class);
			startActivity(searchActivityIntent);
			break;
		case R.id.menu_timeline:
			Intent twitterFeedActivityIntent = new Intent(this, TwitterFeedActivity.class);
			startActivity(twitterFeedActivityIntent);
			break;
		case R.id.menu_tweet:
			Intent tweetActivityIntent = new Intent(this, TweetActivity.class);
			startActivity(tweetActivityIntent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	

}
