package com.nateemma.greencompliance;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

// The Activity that is the first thing launched
public class SplashActivity extends Activity {

	private Context  mContext;

	private static final String TAG = "StartupActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try{
			setContentView(R.layout.splash);

			mContext = this;

			splashThread.start();	

		} catch (Exception e){
			Log.e(TAG, "Error: "+e.toString());
		}
	}


	// Thread to wait for a while, then start the real processing
	Thread splashThread = new Thread() {
		@Override
		public void run() {
			// start the background service and give it some time to initialise while the splash screen is displaying
			try {
				Log.v(TAG, "splashThread started");
				int waited = 100;
				while (waited < 5000) {
					sleep(100);
					waited += 100;
				}
			} catch (InterruptedException e) {
				// do nothing
			} finally {
				Log.v(TAG, "splashThread finished");
				try {
					launchActivity(LocalIntents.HOME);
					//finish();
				} catch (Exception e1) {
					Log.e(TAG, "Exception: "+e1.toString());
				}
			}
		}
	};
	void launchActivity(String intentName) {
		Intent intent = new Intent();
		intent.setAction(intentName);
		try {
			startActivity(intent);
		} catch (Throwable t){
			Log.e(TAG, "startActivity exception: " + t.toString());
			Toast.makeText(mContext, "Error starting " + intentName, Toast.LENGTH_SHORT).show();
		}

	}

}
