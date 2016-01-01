package com.nateemma.greencompliance;
import com.nateemma.greencompliance.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;


// Activity to display contact info, and launch appropriate viewer if user selects and item
public class ContactActivity extends Activity {

	private static final String TAG = "ContactActivity";

	private static final int NUM_EMAILS = 2;
	private static final int NUM_TELEPHONE_NUMBERS = 6;
	private static final int NUM_ADDRESSES = 5;

	private Context mContext=null;

	private static TextView vContactWebsite;
	private static TextView [] vContactTels = new TextView[NUM_TELEPHONE_NUMBERS];
	private static TextView [] vEmails = new TextView[NUM_EMAILS];
	private static TextView [] vAddresses = new TextView[NUM_ADDRESSES];




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {

			mContext = this;
		    getActionBar().setDisplayHomeAsUpEnabled(true);

			// force landscape orientation, if setup indicates. Do this before calling setContentView
			if(getResources().getBoolean(R.bool.landscape_only)){
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
				Log.d(TAG, "Forcing LANDSCAPE mode");
			}

			setContentView(R.layout.contact);

			// connect the views with the output fields

			vContactWebsite = (TextView)findViewById(R.id.contactWebsite);

			vContactTels[0] = (TextView)findViewById(R.id.contactTel);
			vContactTels[1] = (TextView)findViewById(R.id.contactTel1);
			vContactTels[2] = (TextView)findViewById(R.id.contactTel2);
			vContactTels[3] = (TextView)findViewById(R.id.contactTel3);
			vContactTels[4] = (TextView)findViewById(R.id.contactTel4);
			vContactTels[5] = (TextView)findViewById(R.id.contactTel5);

			vEmails[0] = (TextView)findViewById(R.id.emailWater);
			vEmails[1] = (TextView)findViewById(R.id.emailRemittance);

			vAddresses[0] = (TextView)findViewById(R.id.contactOffice1);
			vAddresses[1] = (TextView)findViewById(R.id.contactOffice2);
			vAddresses[2] = (TextView)findViewById(R.id.contactOffice3);
			vAddresses[3] = (TextView)findViewById(R.id.contactOffice4);
			vAddresses[4] = (TextView)findViewById(R.id.contactOffice5);
			vAddresses[5] = (TextView)findViewById(R.id.contactOffice6);


			// set up listeners
			vContactWebsite.setOnClickListener(mWebsiteListener);

			for (int i=0; i<NUM_TELEPHONE_NUMBERS; i++) {
				vContactTels[i].setOnClickListener(mCallListener);
			}


			for (int i=0; i<NUM_EMAILS; i++) {
				vEmails[i].setOnClickListener(mEmailListener);
			}


			for (int i=0; i<NUM_ADDRESSES; i++) {
				vAddresses[i].setOnClickListener(mMapListener);
			}

		} catch (Exception e){
			Log.e(TAG, "onCreate() Error: "+e.toString());
			e.printStackTrace();
		}

	} // onCreate

	// Listeners

	private static TextView tv;

	private OnClickListener mWebsiteListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			tv = (TextView) v;
			String text = tv.getText().toString();
			browseUrl(text);
		}	
	};

	private OnClickListener mCallListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			tv = (TextView) v;
			String text = tv.getText().toString();
			callNumber(text);
		}	
	};

	private OnClickListener mEmailListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			tv = (TextView) v;
			String text = tv.getText().toString();
			sendEmail(text);
		}	
	};

	private OnClickListener mMapListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			tv = (TextView) v;
			String text = tv.getText().toString();
			mapAddress(text);
		}	
	};

	// Utilities to perform the actions

	// launch browser to supplied address
	private void browseUrl(String urlStr) {
		try{
			Log.v(TAG, "browseUrl: "+urlStr);
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(urlStr));
			startActivity(i);
		} catch (Exception e){
			Log.e(TAG, "browseUrl() exception: "+e.toString());
		}
	}

	// launch dialer app to call supplied number
	private void callNumber(String telStr) {
		try{
			Log.v(TAG, "callNumber: "+telStr);
			Intent i = new Intent(Intent.ACTION_CALL);
			i.setData(Uri.parse("tel:"+telStr));
			startActivity(i);
		} catch (Exception e){
			Log.e(TAG, "callNumber() exception: "+e.toString());
		}
	}

	// launch email client with specified destination address
	private void sendEmail(String emailStr) {
		try{
			Log.v(TAG, "sendEmail: "+emailStr);
			Intent i = new Intent(Intent.ACTION_SEND);
			i.setType("message/rfc822");
			i.putExtra(Intent.EXTRA_EMAIL, new String[] {emailStr});
			startActivity(i);		
		} catch (Exception e){
			Log.e(TAG, "sendEmail() exception: "+e.toString());
		}
	}

	// launch map app for supplied address
	private void mapAddress(String addressStr) {
		try{
			String uri = "geo:0,0?q=" + addressStr.replace(" ", "+");
			
			Log.v(TAG, "mapAddress: "+uri);
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(uri));
			startActivity(i);
		} catch (Exception e){
			Log.e(TAG, "mapAddress() exception: "+e.toString());
		}
	}

}
