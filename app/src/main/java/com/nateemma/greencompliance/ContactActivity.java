package com.nateemma.greencompliance;
import com.nateemma.greencompliance.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;


// Activity to display contact info, and launch appropriate viewer if user selects and item
public class ContactActivity extends Activity {

	private static final String TAG = "ContactActivity";


	private Context mContext=null;

	private static TextView vContactWebsite;
	private static TextView vContactTel;
	//private static TextView vContactFax;
	private static TextView vEmailSales;
	private static TextView vEmailHelp;
	private static TextView vEmailInfo;
	private static TextView vEmailDistributor;
	private static TextView vEmailLeisure;
	private static TextView vEmailMedia;
	private static TextView vContactOffice;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {

			mContext = this;
		    getActionBar().setDisplayHomeAsUpEnabled(true);

			setContentView(R.layout.contact);

			// connect the views with the output fields

			vContactWebsite = (TextView)findViewById(R.id.contactWebsite);
			vContactTel = (TextView)findViewById(R.id.contactTel);
			//vContactFax = (TextView)findViewById(R.id.contactFax);
			vEmailSales = (TextView)findViewById(R.id.emailSales);
			vEmailHelp = (TextView)findViewById(R.id.emailHelp);
			vEmailInfo = (TextView)findViewById(R.id.emailInfo);
			vEmailDistributor = (TextView)findViewById(R.id.emailDistributor);
			vEmailLeisure = (TextView)findViewById(R.id.emailLeisure);
			vEmailMedia = (TextView)findViewById(R.id.emailMedia);
			vContactOffice = (TextView)findViewById(R.id.contactOffice);

			// set up listeners
			vContactWebsite.setOnClickListener(mWebsiteListener);
			vContactTel.setOnClickListener(mCallListener);
			vEmailSales.setOnClickListener(mEmailListener);
			vEmailHelp.setOnClickListener(mEmailListener);
			vEmailInfo.setOnClickListener(mEmailListener);
			vEmailDistributor.setOnClickListener(mEmailListener);
			vEmailLeisure.setOnClickListener(mEmailListener);
			vEmailMedia.setOnClickListener(mEmailListener);
			vContactOffice.setOnClickListener(mMapListener);

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
