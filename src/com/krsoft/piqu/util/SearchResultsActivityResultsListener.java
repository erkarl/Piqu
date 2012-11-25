package com.krsoft.piqu.util;

import java.util.ArrayList;

import com.krsoft.piqu.data.Tweet;

public interface SearchResultsActivityResultsListener {
	public void onResultsSucceeded(ArrayList<Tweet> result);
}
