package com.krsoft.piqu.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.krsoft.piqu.R;
import com.krsoft.piqu.data.Constants;

public class SearchActivity extends BaseActivity {
	
	private OnClickListener searchButtonListener = new OnClickListener() {

		public void onClick(View v) {
			if (!(isLoggedIn())) {
				Intent mainActivity = new Intent(SearchActivity.this,
						MainActivity.class);
				startActivity(mainActivity);
			} else {
				EditText searchEditText = (EditText) findViewById(R.id.searchEditText);
				String searchText = searchEditText.getText().toString();
				if ("".equals(searchText)) {
					Toast.makeText(SearchActivity.this, R.string.emptySearch,
							Toast.LENGTH_SHORT).show();
				} else {
					Intent searchActivityResults = new Intent(
							SearchActivity.this, SearchResultsActivity.class);
					searchActivityResults.putExtra(
							Constants.INTENT_EXTRA_SEARCH_QUERY, searchText);
					startActivity(searchActivityResults);
				}
			}

		}
	};

	public boolean isLoggedIn() {
		SharedPreferences pref = getSharedPreferences(Constants.PREF_NAME,
				MODE_PRIVATE);
		String accessToken = pref.getString(Constants.PREF_KEY_ACCESS_TOKEN,
				null);
		String accessTokenSecret = pref.getString(
				Constants.PREF_KEY_ACCESS_TOKEN_SECRET, null);
		if (accessToken == null || accessTokenSecret == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		Button searchButton;
		searchButton = (Button) findViewById(R.id.submitSearchButton);
		searchButton.setOnClickListener(searchButtonListener);
	}

}
