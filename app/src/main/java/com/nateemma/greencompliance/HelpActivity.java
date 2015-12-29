package com.nateemma.greencompliance;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

// Activity to display the "Help" information
public class HelpActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// force landscape orientation, if setup indicates. Do this before calling setContentView
		if(getResources().getBoolean(R.bool.landscape_only)){
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			Log.d("HelpActivity", "Forcing LANDSCAPE mode");
		}

		setContentView(R.layout.help);
	}


}
