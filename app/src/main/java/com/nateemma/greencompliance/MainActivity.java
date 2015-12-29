package com.nateemma.greencompliance;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Context  mContext;

	// Pseudo buttons (compound LinearLayout made to look like a button)
	private LinearLayout btnBoilerSolids;
	private LinearLayout btnCoolingSolids;
	private LinearLayout btnBoilerLiquids;
	private LinearLayout btnCoolingLiquids;
	
	private Button btnAbout;
	private Button btnContact;
	private Button btnHelp;

	private static final String TAG = "MainActivity";
	//private static Boolean splashDisplayed = false;
	//private static Boolean mSetupDone = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try{

			Log.v(TAG, "onCreate()");
			//if (!mSetupDone) {
				//mSetupDone = true;
				setContentView(R.layout.activity_main);

				mContext = this;

				//getActionBar().setDisplayHomeAsUpEnabled(true);

				// Set up handlers for the buttons
				btnBoilerSolids = (LinearLayout) findViewById(R.id.btnBoilerSolids);
				btnCoolingSolids = (LinearLayout) findViewById(R.id.btnCoolingSolids);
				btnBoilerLiquids = (LinearLayout) findViewById(R.id.btnBoilerLiquids);
				btnCoolingLiquids = (LinearLayout) findViewById(R.id.btnCoolingLiquids);
				btnAbout = (Button) findViewById(R.id.btnAbout);
				btnContact = (Button) findViewById(R.id.btnContact);
				btnHelp = (Button) findViewById(R.id.btnHelp);

				btnBoilerSolids.setOnClickListener (new OnClickListener(){public void onClick(View v) {launchActivity(LocalIntents.BOILER_INPUT, LocalIntents.SOLIDS);};});
				btnCoolingSolids.setOnClickListener (new OnClickListener(){public void onClick(View v) {launchActivity(LocalIntents.COOLING_INPUT, LocalIntents.SOLIDS);};});
				btnBoilerLiquids.setOnClickListener (new OnClickListener(){public void onClick(View v) {launchActivity(LocalIntents.BOILER_INPUT, LocalIntents.LIQUIDS);};});
				btnCoolingLiquids.setOnClickListener (new OnClickListener(){public void onClick(View v) {launchActivity(LocalIntents.COOLING_INPUT, LocalIntents.LIQUIDS);};});
				btnAbout.setOnClickListener (new OnClickListener(){public void onClick(View v) {launchActivity(LocalIntents.ABOUT, null);};});
				btnContact.setOnClickListener (new OnClickListener(){public void onClick(View v) {launchActivity(LocalIntents.CONTACT, null);};});
				btnHelp.setOnClickListener (new OnClickListener(){public void onClick(View v) {launchActivity(LocalIntents.HELP, null);};});
			//}
		} catch (Exception e){
			Log.e(TAG, "Error: "+e.toString());
		}
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.v(TAG, "onDestroy()");
		finish();
		//android.os.Process.killProcess(android.os.Process.myPid());
	} //onDestroy





	// Utility to launch activity given the Intent name
	void launchActivity(String intentName, String arg) {
		Intent intent = new Intent();
		intent.setAction(intentName);
		if ((arg!=null) && (arg.length()>0)){
			intent.putExtra(LocalIntents.ARG0, arg);
		}
		try {
			startActivity(intent);
		} catch (Throwable t){
			Log.e(TAG, "startActivity exception: " + t.toString());
			Toast.makeText(mContext, "Error starting " + intentName, Toast.LENGTH_SHORT).show();
		}

	}

}
