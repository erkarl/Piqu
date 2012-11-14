package com.krsoft.piqu;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Auth extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twitter_auth);
		twitterAuth();
	}

	private void twitterAuth() {
		WebView webView = (WebView) findViewById(R.id.twitterAuth);
		webView.clearSslPreferences();
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		Log.i("Piqu", "WebView set.");
		webView.setWebViewClient(new WebViewClient() {
			public void onPageFinished(WebView view, String url) {
				Log.i("Piqu", "URL IS: " + url);
				if (url != null && url.startsWith("http://www.karlranna.com/")) {
					Uri uri = Uri.parse(url);
					if (uri.getQueryParameter("denied") != null) {
						setResult(RESULT_CANCELED);
						finish();
					} else {
						String oauthToken = uri
								.getQueryParameter("oauth_token");
						String oauthVerifier = uri
								.getQueryParameter("oauth_verifier");
						Log.i("Piqu", oauthToken);
						Log.i("Piqu", oauthVerifier);
						Intent intent = getIntent();
						intent.putExtra(Constants.IEXTRA_OAUTH_TOKEN,
								oauthToken);
						intent.putExtra(Constants.IEXTRA_OAUTH_VERIFIER,
								oauthVerifier);

						setResult(RESULT_OK, intent);
						finish();
					}
				}
			}
		});
        try {
        	Log.i("Piqu", this.getIntent().getExtras().getString("auth_url"));
        } catch(NullPointerException e){
        	Log.i("Piqu", "NULL LOL!");
        }
		webView.loadUrl(this.getIntent().getExtras().getString("auth_url"));
		Log.i("Piqu", "past this");

	}

}
