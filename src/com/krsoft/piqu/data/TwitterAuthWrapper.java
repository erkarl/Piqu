package com.krsoft.piqu.data;

import twitter4j.auth.RequestToken;
import android.content.Context;

public class TwitterAuthWrapper {
	public String verifier;
	public RequestToken mRequestToken;
	public Context context;

	public String getVerifier() {
		return verifier;
	}

	public void setVerifier(String verifier) {
		this.verifier = verifier;
	}

	public RequestToken getmRequestToken() {
		return mRequestToken;
	}

	public void setmRequestToken(RequestToken mRequestToken) {
		this.mRequestToken = mRequestToken;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public TwitterAuthWrapper(String verifier, RequestToken mRequestToken,
			Context context) {
		super();
		this.verifier = verifier;
		this.mRequestToken = mRequestToken;
		this.context = context;
	}


}
