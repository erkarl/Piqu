package com.krsoft.piqu.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.krsoft.piqu.R;
import com.krsoft.piqu.data.Constants;

public class LogoutActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences pref = getSharedPreferences(Constants.PREF_NAME,
				MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.remove(Constants.PREF_KEY_ACCESS_TOKEN);
		editor.remove(Constants.PREF_KEY_ACCESS_TOKEN_SECRET);
		editor.commit();
		Toast.makeText(this, R.string.loggedOut, Toast.LENGTH_SHORT).show();
		Intent mainActivityIntent = new Intent(this, MainActivity.class);
		startActivity(mainActivityIntent);
	}
}
