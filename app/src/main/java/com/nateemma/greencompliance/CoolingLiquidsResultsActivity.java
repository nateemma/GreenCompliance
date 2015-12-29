package com.nateemma.greencompliance;
import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


// Class to implement display of Steam Cooling (Liquids) results
public class CoolingLiquidsResultsActivity extends Activity {


	private static final String TAG = "CoolingLiquidsResults";

	private static CoolingModel mCoolingModel = null;

	private Context mContext=null;

	// Product-based values
	private static TextView vOutH207Dosage;
	private static TextView vOutH207Usage;
	private static TextView vOutH2073Dosage;
	private static TextView vOutH2073Usage;
	private static TextView vOutH280Dosage;
	private static TextView vOutH280Usage;
	private static TextView vOutH2805Dosage;
	private static TextView vOutH2805Usage;
	private static TextView vOutH390Dosage;
	private static TextView vOutH390Usage;
	private static TextView vOutH3905Dosage;
	private static TextView vOutH3905Usage;
	private static TextView vOutH391Dosage;
	private static TextView vOutH391Usage;	
	private static TextView vOutH423Dosage;
	private static TextView vOutH423Usage;	
	private static TextView vOutH425Dosage;
	private static TextView vOutH425Usage;
	private static TextView vOutH4255Dosage;
	private static TextView vOutH4255Usage;	
	private static TextView vOutH535Dosage;
	private static TextView vOutH535Usage;	
	private static TextView vOutH874Dosage;
	private static TextView vOutH874Usage;

	// Biocide values

	private static TextView vOutC31Dosage;
	private static TextView vOutC31Usage;
	private static TextView vOutC32Dosage;
	private static TextView vOutC32Usage;
	private static TextView vOutC44Dosage;
	private static TextView vOutC44Usage;
	private static TextView vOutC45Dosage;
	private static TextView vOutC45Usage;
	private static TextView vOutC48Dosage;
	private static TextView vOutC48Usage;
	private static TextView vOutC51Dosage;
	private static TextView vOutC51Usage;
	private static TextView vOutC52Dosage;
	private static TextView vOutC52Usage;
	private static TextView vOutC54Dosage;
	private static TextView vOutC54Usage;
	private static TextView vOutC58Dosage;
	private static TextView vOutC58Usage;


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

			setContentView(R.layout.cooling_liquids_results);

			// connect the views with the output fields
			vOutH207Dosage = (TextView)findViewById(R.id.outH207Dosage);
			vOutH207Usage  = (TextView)findViewById(R.id.outH207Usage);
			vOutH2073Dosage = (TextView)findViewById(R.id.outH2073Dosage);
			vOutH2073Usage  = (TextView)findViewById(R.id.outH2073Usage);
			vOutH280Dosage = (TextView)findViewById(R.id.outH280Dosage);
			vOutH280Usage  = (TextView)findViewById(R.id.outH280Usage);
			vOutH2805Dosage = (TextView)findViewById(R.id.outH2805Dosage);
			vOutH2805Usage  = (TextView)findViewById(R.id.outH2805Usage);
			vOutH390Dosage = (TextView)findViewById(R.id.outH390Dosage);
			vOutH390Usage  = (TextView)findViewById(R.id.outH390Usage);
			vOutH3905Dosage = (TextView)findViewById(R.id.outH3905Dosage);
			vOutH3905Usage  = (TextView)findViewById(R.id.outH3905Usage);
			vOutH391Dosage = (TextView)findViewById(R.id.outH391Dosage);
			vOutH391Usage  = (TextView)findViewById(R.id.outH391Usage);	
			vOutH423Dosage = (TextView)findViewById(R.id.outH423Dosage);
			vOutH423Usage  = (TextView)findViewById(R.id.outH423Usage);	
			vOutH425Dosage = (TextView)findViewById(R.id.outH425Dosage);
			vOutH425Usage  = (TextView)findViewById(R.id.outH425Usage);
			vOutH4255Dosage = (TextView)findViewById(R.id.outH4255Dosage);
			vOutH4255Usage  = (TextView)findViewById(R.id.outH4255Usage);	
			vOutH535Dosage = (TextView)findViewById(R.id.outH535Dosage);
			vOutH535Usage  = (TextView)findViewById(R.id.outH535Usage);	
			vOutH874Dosage = (TextView)findViewById(R.id.outH874Dosage);
			vOutH874Usage  = (TextView)findViewById(R.id.outH874Usage);
			
			vOutC31Dosage = (TextView)findViewById(R.id.outC31Dosage);
			vOutC31Usage  = (TextView)findViewById(R.id.outC31Usage);
			vOutC32Dosage = (TextView)findViewById(R.id.outC32Dosage);
			vOutC32Usage  = (TextView)findViewById(R.id.outC32Usage);
			vOutC44Dosage = (TextView)findViewById(R.id.outC44Dosage);
			vOutC44Usage  = (TextView)findViewById(R.id.outC44Usage);
			vOutC45Dosage = (TextView)findViewById(R.id.outC45Dosage);
			vOutC45Usage  = (TextView)findViewById(R.id.outC45Usage);
			vOutC48Dosage = (TextView)findViewById(R.id.outC48Dosage);
			vOutC48Usage  = (TextView)findViewById(R.id.outC48Usage);
			vOutC51Dosage = (TextView)findViewById(R.id.outC51Dosage);
			vOutC51Usage  = (TextView)findViewById(R.id.outC51Usage);
			vOutC52Dosage = (TextView)findViewById(R.id.outC52Dosage);
			vOutC52Usage  = (TextView)findViewById(R.id.outC52Usage);
			vOutC54Dosage = (TextView)findViewById(R.id.outC54Dosage);
			vOutC54Usage  = (TextView)findViewById(R.id.outC54Usage);
			vOutC58Dosage = (TextView)findViewById(R.id.outC58Dosage);
			vOutC58Usage  = (TextView)findViewById(R.id.outC58Usage);

			// load the  data
			loadData();

		} catch (Exception e){
			Log.e(TAG, "onCreate() Error: "+e.toString());
			e.printStackTrace();
		}

	} // onCreate


	// MENU HANDLING

	// Register the menu actions for display on the action bar
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.results_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}


	// Handler for the menu action(s)
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_email:
			sendEmail();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}


	// APP LOGIC

	// load the initial set of data
	private void loadData(){
		try{
			mCoolingModel = CoolingModel.sharedInstance();
			mCoolingModel.setContext(mContext);		

			vOutH207Dosage.setText(Utilities.round1(mCoolingModel.h207Dosage));
			vOutH207Usage.setText(Utilities.round1(mCoolingModel.h207Usage));
			vOutH2073Dosage.setText(Utilities.round1(mCoolingModel.h2073Dosage));
			vOutH2073Usage.setText(Utilities.round1(mCoolingModel.h2073Usage));
			vOutH280Dosage.setText(Utilities.round1(mCoolingModel.h280Dosage));
			vOutH280Usage.setText(Utilities.round1(mCoolingModel.h280Usage));
			vOutH2805Dosage.setText(Utilities.round1(mCoolingModel.h2805Dosage));
			vOutH2805Usage.setText(Utilities.round1(mCoolingModel.h2805Usage));
			vOutH390Dosage.setText(Utilities.round1(mCoolingModel.h390Dosage));
			vOutH390Usage.setText(Utilities.round1(mCoolingModel.h390Usage));
			vOutH3905Dosage.setText(Utilities.round1(mCoolingModel.h3905Dosage));
			vOutH3905Usage.setText(Utilities.round1(mCoolingModel.h3905Usage));
			vOutH391Dosage.setText(Utilities.round1(mCoolingModel.h391Dosage));
			vOutH391Usage.setText(Utilities.round1(mCoolingModel.h391Usage));	
			vOutH423Dosage.setText(Utilities.round1(mCoolingModel.h423Dosage));
			vOutH423Usage.setText(Utilities.round1(mCoolingModel.h423Usage));	
			vOutH425Dosage.setText(Utilities.round1(mCoolingModel.h425Dosage));
			vOutH425Usage.setText(Utilities.round1(mCoolingModel.h425Usage));
			vOutH4255Dosage.setText(Utilities.round1(mCoolingModel.h4255Dosage));
			vOutH4255Usage.setText(Utilities.round1(mCoolingModel.h4255Usage));	
			vOutH535Dosage.setText(Utilities.round1(mCoolingModel.h535Dosage));
			vOutH535Usage.setText(Utilities.round1(mCoolingModel.h535Usage));	
			vOutH874Dosage.setText(Utilities.round1(mCoolingModel.h874Dosage));
			vOutH874Usage.setText(Utilities.round1(mCoolingModel.h874Usage));
			
			vOutC31Dosage.setText(Utilities.round1(mCoolingModel.c31Dosage));
			vOutC31Usage.setText(Utilities.round1(mCoolingModel.c31Usage));
			vOutC32Dosage.setText(Utilities.round1(mCoolingModel.c32Dosage));
			vOutC32Usage.setText(Utilities.round1(mCoolingModel.c32Usage));
			vOutC44Dosage.setText(Utilities.round1(mCoolingModel.c44Dosage));
			vOutC44Usage.setText(Utilities.round1(mCoolingModel.c44Usage));
			vOutC45Dosage.setText(Utilities.round1(mCoolingModel.c45Dosage));
			vOutC45Usage.setText(Utilities.round1(mCoolingModel.c45Usage));
			vOutC48Dosage.setText(Utilities.round1(mCoolingModel.c48Dosage));
			vOutC48Usage.setText(Utilities.round1(mCoolingModel.c48Usage));
			vOutC51Dosage.setText(Utilities.round1(mCoolingModel.c51Dosage));
			vOutC51Usage.setText(Utilities.round1(mCoolingModel.c51Usage));
			vOutC52Dosage.setText(Utilities.round1(mCoolingModel.c52Dosage));
			vOutC52Usage.setText(Utilities.round1(mCoolingModel.c52Usage));
			vOutC54Dosage.setText(Utilities.round1(mCoolingModel.c54Dosage));
			vOutC54Usage.setText(Utilities.round1(mCoolingModel.c54Usage));
			vOutC58Dosage.setText(Utilities.round1(mCoolingModel.c58Dosage));
			vOutC58Usage.setText(Utilities.round1(mCoolingModel.c58Usage));

		} catch (Exception e){
			Log.e(TAG, "loadData() Error: "+e.toString());
			e.printStackTrace();
		}
	}


	// EMAIL FORMATION AND HANDLING

	private void sendEmail(){

		// get HTML-formatted summary of results
		String emailBody = buildHTMLsummary();

		//Log.v(TAG, "Email text: \n"+emailBody);

		/**
		 * NOTE: GMAIL cannot handle tables in HTML, so we can't just insert the text, as with iPhone
		 *       So, write the data to an HTML file and send it as an attachment instead
		 */
		// create the attachment
		Uri attachUri = Utilities.createAttachment("coolingliquids", emailBody);

		if (attachUri != null){
			// start email activity
			Intent i = new Intent(Intent.ACTION_SEND);
			i.setType("message/rfc822");
			//i.setType("text/html");
			//i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
			i.putExtra(Intent.EXTRA_SUBJECT, "WTP Product Estimates for Cooling Water (Liquids)");
			String emailIntro = "<html><br>Please find attached a summary of the WTP Product estimates<br></html>";
			i.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(emailIntro));
			i.putExtra(Intent.EXTRA_STREAM, attachUri);
			try {
				//startActivity(Intent.createChooser(i, "Send mail..."));
				startActivity(i);
			} catch (android.content.ActivityNotFoundException ex) {
				Toast.makeText(mContext, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
			} catch (Exception e){
				Log.e(TAG, "sendEmail() exception: "+e.toString());
			}
		} else {
			Log.e(TAG, "sendEmail() Error creating attachment");
		}
	}



	// routine to build HTML Summary of data
	private String buildHTMLsummary(){
		String htmlString; // string to hold the HTML Data
		String row;        // String to hold data for a row
		String disclaimer = "<p><i>Disclaimer</i>: the values shown are estimates only.<p>";

		htmlString = "<p>Below are the input parameters used for the calculations, and the resulting product estimates:</p>";

	    // Input Parameters
	    htmlString += "<br>";
	    htmlString += "<b>Cooling Water Input Parameters</b>";
	    htmlString += "<TABLE border=\"1\" style=\"border-collapse:collapse; border: 1px solid black;\">";
	    
	    // Site Section
	    htmlString += "<TR><TH colspan=\"3\" style=\"background-color:#F0F8FF\">Site</TR>";
	    row = "<TD colspan=\"3\">" + mCoolingModel.inSite + "</TD>" ;
	    htmlString += row;
	    
	    // Criteria Section
	    htmlString += "<TR><TH colspan=\"3\" style=\"background-color:#F0F8FF\">Criteria";
	    htmlString += "<TR style=\"background-color:#FFE0B2\"><TH>Parameter<TH>Value<TH>Units</TR>";
	    // rowFormat = "<TR><TD>" +  + "<TD>" +  + "<TD>" +  + "</TR>";
	    row = "<TR><TD>" +  "Circulation" + "<TD align=right>" + mCoolingModel.inCirculation + "<TD>" + "m<sup>3</sup>/hr" + "</TR>";
	    htmlString += row;
	    row = "<TR><TD>" +  "&#x2206;T" + "<TD align=right>" + mCoolingModel.inDeltaT + "<TD>" + "&deg;C" + "</TR>";
	    htmlString += row;
	    row = "<TR><TD>" +  "Sump Volume" + "<TD align=right>" + mCoolingModel.inSumpVolume + "<TD>" + "m<sup>3</sup>" + "</TR>";
	    htmlString += row;
	    row = "<TR><TD>" +  "System Volume" + "<TD align=right>" + mCoolingModel.inSysVolume + "<TD>" + "m<sup>3</sup>" + "</TR>";
	    htmlString += row;
	    row = "<TR><TD>" +  "Concentration Factor" + "<TD align=right>" + mCoolingModel.inConcFactor + "<TD>" + "" + "</TR>";
	    htmlString += row;
	    
	    // Make Up Water Quality Section
	    htmlString += "<TR><TH colspan=\"3\" style=\"background-color:#F0F8FF\">Make Up Water Quality";
	    htmlString += "<TR style=\"background-color:#FFE0B2\"><TH>Parameter<TH>Value<TH>Units</TR>";
	    //rowFormat = "<TR><TD>" +  + "<TD>" +  + "<TD>" +  + "</TR>";
	    row = "<TR><TD>" +  "TDS" + "<TD align=right>" + mCoolingModel.inTDS + "<TD>" + "ppm" + "</TR>";
	    htmlString += row;
	    row = "<TR><TD>" +  "Total Hardness" + "<TD align=right>" + mCoolingModel.inTotalHardness + "<TD>" + "ppm" + "</TR>";
	    htmlString += row;
	    row = "<TR><TD>" +  "M Alk" + "<TD align=right>" + mCoolingModel.inMAlk + "<TD>" + "ppm" + "</TR>";
	    htmlString += row;
	    row = "<TR><TD>" +  "pH" + "<TD align=right>" + mCoolingModel.inPH + "<TD>" + "" + "</TR>";
	    htmlString += row;
	    row = "<TR><TD>" +  "Chlorides" + "<TD align=right>" + mCoolingModel.inChlorides + "<TD>" + "ppm" + "</TR>";
	    htmlString += row;
	    
	    // Duty/Operation Section
	    htmlString += "<TR><TH colspan=\"3\" style=\"background-color:#F0F8FF\">Duty/Operation";
	    htmlString += "<TR style=\"background-color:#FFE0B2\"><TH>Parameter<TH>Value<TH>Units</TR>";
	    //rowFormat = "<TR><TD>" +  + "<TD>" +  + "<TD>" +  + "</TR>";
	    row = "<TR><TD>" +  "Hours/Day" + "<TD align=right>" + mCoolingModel.inHours + "<TD>" + "" + "</TR>";
	    htmlString += row;
	    row = "<TR><TD>" +  "Days/Week" + "<TD align=right>" + mCoolingModel.inDays + "<TD>" + "" + "</TR>";
	    htmlString += row;
	    row = "<TR><TD>" +  "Weeks/Year" + "<TD align=right>" + mCoolingModel.inWeeks + "<TD>" + "" + "</TR>";
	    htmlString += row;
	    
	    htmlString += "</TABLE>";
	    
	    // Estimated Product amounts
	    htmlString += "<br>";
	    htmlString += "<b>Estimated Cooling Water (Liquid) Products Needed</b>";
	    htmlString += "<TABLE border=\"1\" style=\"border-collapse:collapse; border: 1px solid black;\">";
	    
	    // Inhibitor Products
	    htmlString += "<TR><TH colspan=\"6\" style=\"background-color:#F0F8FF\">Inhibitors";
	    htmlString += "<TR style=\"background-color:#FFE0B2\">";
	    htmlString += "<TH>Product<TH>Dosage<BR>(g/m<sup>3</sup>)<TH>Usage<BR>(kg/yr)<TH>Description</TR>";
	    
	    // add products row data
	    htmlString += rowText(R.string.h207Title, mCoolingModel.h207Dosage, mCoolingModel.h207Usage, R.string.h207Description);
	    htmlString += rowText(R.string.h2073Title, mCoolingModel.h2073Dosage, mCoolingModel.h2073Usage, R.string.h2073Description);
	    htmlString += rowText(R.string.h280Title, mCoolingModel.h280Dosage, mCoolingModel.h280Usage, R.string.h280Description);
	    htmlString += rowText(R.string.h2805Title, mCoolingModel.h2805Dosage, mCoolingModel.h2805Usage, R.string.h2805Description);
	    htmlString += rowText(R.string.h390Title, mCoolingModel.h390Dosage, mCoolingModel.h390Usage, R.string.h390Description);
	    htmlString += rowText(R.string.h3905Title, mCoolingModel.h3905Dosage, mCoolingModel.h3905Usage, R.string.h3905Description);
	    htmlString += rowText(R.string.h391Title, mCoolingModel.h391Dosage, mCoolingModel.h391Usage, R.string.h391Description);
	    htmlString += rowText(R.string.h423Title, mCoolingModel.h423Dosage, mCoolingModel.h423Usage, R.string.h423Description);
	    htmlString += rowText(R.string.h425Title, mCoolingModel.h425Dosage, mCoolingModel.h425Usage, R.string.h425Description);
	    htmlString += rowText(R.string.h4255Title, mCoolingModel.h4255Dosage, mCoolingModel.h4255Usage, R.string.h4255Description);
	    htmlString += rowText(R.string.h535Title, mCoolingModel.h535Dosage, mCoolingModel.h535Usage, R.string.h535Description);
	    htmlString += rowText(R.string.h874Title, mCoolingModel.h874Dosage, mCoolingModel.h874Usage, R.string.h874Description);
	    
	    // Biocide Products
	    htmlString += "<TR><TH colspan=\"6\" style=\"background-color:#F0F8FF\">Biocides";
	    htmlString += "<TR style=\"background-color:#FFE0B2\">";
	    htmlString += "<TH>Product<TH>Dosage<BR>(g/m<sup>3</sup>)<TH>Usage<BR>(kg/yr)<TH>Description</TR>";
	    
	    // add biocides row data
	    htmlString += rowText(R.string.c31Title, mCoolingModel.c31Dosage, mCoolingModel.c31Usage, R.string.c31Description);
	    htmlString += rowText(R.string.c32Title, mCoolingModel.c32Dosage, mCoolingModel.c32Usage, R.string.c32Description);
	    htmlString += rowText(R.string.c44Title, mCoolingModel.c44Dosage, mCoolingModel.c44Usage, R.string.c44Description);
	    htmlString += rowText(R.string.c45Title, mCoolingModel.c45Dosage, mCoolingModel.c45Usage, R.string.c45Description);
	    htmlString += rowText(R.string.c48Title, mCoolingModel.c48Dosage, mCoolingModel.c48Usage, R.string.c48Description);
	    htmlString += rowText(R.string.c51Title, mCoolingModel.c51Dosage, mCoolingModel.c51Usage, R.string.c51Description);
	    htmlString += rowText(R.string.c52Title, mCoolingModel.c52Dosage, mCoolingModel.c52Usage, R.string.c52Description);
	    htmlString += rowText(R.string.c54Title, mCoolingModel.c54Dosage, mCoolingModel.c54Usage, R.string.c54Description);
	    htmlString += rowText(R.string.c58Title, mCoolingModel.c58Dosage, mCoolingModel.c58Usage, R.string.c58Description);
	    
	    //end table
		htmlString += "</TR>";
		htmlString += "</TABLE>";

		// Disclaimer
		htmlString += disclaimer ;
		htmlString += "<br>" ;
		htmlString += "</html>" ;
		return htmlString;
	}
	

	// Utility to build row from product data
	private String rowText(int nameId, Double dosage, Double usage, int descriptionId){
		String row = "";
		try{
			String name = Utilities.getStringFromId(mContext, nameId);
			String description = Utilities.getStringFromId(mContext, descriptionId);

			row = "<TR><TD>" + name + "<TD align=right>" + Utilities.round1(dosage)  + "<TD align=right>" + Utilities.round1(usage)  + "<TD>" + description + "</TR>" ;
		} catch (Exception e){
			Log.e(TAG, "rowText err: "+e.toString());
			e.printStackTrace();
			row = "<TR><TD>Error<TD><TD><TD></TR>" ;		
		}
		return row;
	}
	
}
