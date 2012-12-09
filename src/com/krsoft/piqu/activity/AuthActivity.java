package com.krsoft.piqu.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.krsoft.piqu.R;
import com.krsoft.piqu.data.Constants;

public class AuthActivity extends BaseActivity {
	
	ProgressDialog progressDialog;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twitter_auth);
		progressDialog = ProgressDialog.show(AuthActivity.this, getString(R.string.loading), getString(R.string.pleaseWait));
		twitterAuth();
	}

	private void twitterAuth() {
		WebView webView = (WebView) findViewById(R.id.twitterAuth);
		webView.clearSslPreferences();
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient() {
			public void onPageFinished(WebView view, String url) {
				if (progressDialog.isShowing() && progressDialog != null) {
					progressDialog.dismiss();
				}
				if (url != null && url.startsWith(Constants.CALLBACK_URL)) {
					Uri uri = Uri.parse(url);
					if (uri.getQueryParameter(Constants.QUERY_PARAMETER_DENIED) != null) {
						setResult(RESULT_CANCELED);
						finish();
					} else {
						String oauthToken = uri
								.getQueryParameter(Constants.QUERY_PARAMETER_OAUTH_TOKEN);
						String oauthVerifier = uri
								.getQueryParameter(Constants.QUERY_PARAMETER_OAUTH_VERIFIER);
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
		webView.loadUrl(this.getIntent().getExtras()
				.getString(Constants.INTENT_EXTRA_AUTH_URL));

	}

}
