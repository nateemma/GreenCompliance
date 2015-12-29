package com.nateemma.greencompliance;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

// Activity to display the "About Us" information
public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getActionBar().setDisplayHomeAsUpEnabled(true); // enable Action Bar navigation
		
		setContentView(R.layout.about);
	}


}
