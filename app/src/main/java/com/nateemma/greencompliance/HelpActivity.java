package com.nateemma.greencompliance;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

// Activity to display the "Help" information
public class HelpActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.help);
	}


}
