package com.nateemma.greencompliance;
import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import static com.nateemma.greencompliance.Utilities.round1;

// Class to implement display of Steam Boiler results
public class BoilerSolidsResultsActivity extends Activity {


	private static final String TAG = "BoilerSolidsResults";

	private static BoilerModel mBoilerModel = null;

	private Context mContext=null;

	private static TextView vOutSS1294Dosage;
	private static TextView vOutSS1294Usage;

	private static TextView vOutSS1295Dosage;
	private static TextView vOutSS1295Usage;

	private static TextView vOutSS1350Dosage;
	private static TextView vOutSS1350Usage;

	private static TextView vOutSS1095Dosage;
	private static TextView vOutSS1095Usage;

	private static TextView vOutSS1995Dosage;
	private static TextView vOutSS1995Usage;

	private static TextView vOutSS2295Dosage;
	private static TextView vOutSS2295Usage;

	private static TextView vOutSS8985Dosage;
	private static TextView vOutSS8985Usage;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {

			mContext = this;
		    getActionBar().setDisplayHomeAsUpEnabled(true);

			setContentView(R.layout.boiler_solids_results);

			// connect the views with the output fields

			vOutSS1294Dosage = (TextView)findViewById(R.id.outSS1294Dosage);
			vOutSS1294Usage  = (TextView)findViewById(R.id.outSS1294Usage);
			vOutSS1295Dosage = (TextView)findViewById(R.id.outSS1295Dosage);
			vOutSS1295Usage  = (TextView)findViewById(R.id.outSS1295Usage);
			vOutSS1350Dosage = (TextView)findViewById(R.id.outSS1350Dosage);
			vOutSS1350Usage  = (TextView)findViewById(R.id.outSS1350Usage);
			vOutSS1095Dosage = (TextView)findViewById(R.id.outSS1095Dosage);
			vOutSS1095Usage  = (TextView)findViewById(R.id.outSS1095Usage);
			vOutSS1995Dosage = (TextView)findViewById(R.id.outSS1995Dosage);
			vOutSS1995Usage  = (TextView)findViewById(R.id.outSS1995Usage);
			vOutSS2295Dosage = (TextView)findViewById(R.id.outSS2295Dosage);
			vOutSS2295Usage  = (TextView)findViewById(R.id.outSS2295Usage);
			vOutSS8985Dosage = (TextView)findViewById(R.id.outSS8985Dosage);
			vOutSS8985Usage  = (TextView)findViewById(R.id.outSS8985Usage);


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
			mBoilerModel = BoilerModel.sharedInstance();
			mBoilerModel.setContext(mContext);

			vOutSS1294Dosage.setText(round1(mBoilerModel.ss1294Dosage));
			vOutSS1294Usage.setText(round1(mBoilerModel.ss1294Usage));
			vOutSS1295Dosage.setText(round1(mBoilerModel.ss1295Dosage));
			vOutSS1295Usage.setText(round1(mBoilerModel.ss1295Usage));
			vOutSS1350Dosage.setText(round1(mBoilerModel.ss1350Dosage));
			vOutSS1350Usage.setText(round1(mBoilerModel.ss1350Usage));
			vOutSS1095Dosage.setText(round1(mBoilerModel.ss1095Dosage));
			vOutSS1095Usage.setText(round1(mBoilerModel.ss1095Usage));
			vOutSS1995Dosage.setText(round1(mBoilerModel.ss1995Dosage));
			vOutSS1995Usage.setText(round1(mBoilerModel.ss1995Usage));
			vOutSS2295Dosage.setText(round1(mBoilerModel.ss2295Dosage));
			vOutSS2295Usage.setText(round1(mBoilerModel.ss2295Usage));
			vOutSS8985Dosage.setText(round1(mBoilerModel.ss8985Dosage));
			vOutSS8985Usage.setText(round1(mBoilerModel.ss8985Usage));
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
		Uri attachUri = createAttachment(emailBody);

		if (attachUri != null){
			// start email activity
			Intent i = new Intent(Intent.ACTION_SEND);
			i.setType("message/rfc822");
			//i.setType("text/html");
			//i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
			i.putExtra(Intent.EXTRA_SUBJECT, "WTP Product Estimates for Steam Boiler (Solids)");
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
		htmlString += "<html><br>" ;
		htmlString += "<b>Boiler Water Input Parameters</b><br>" ;
		htmlString += "<TABLE border=\"1\" style=\"border-collapse:collapse; border: 1px solid black;\">" ;

		// Site Section
		htmlString += "<TR><TH colspan=\"3\" style=\"background-color:#F0F8FF\">Site</TR>" ;
		row = "<TD colspan=\"3\">" + mBoilerModel.inSite + "</TD>" ;
		htmlString += row ;

		// Boiler Duty Section
		htmlString += "<TR><TH colspan=\"3\" style=\"background-color:#F0F8FF\">Boiler Duty" ;
		htmlString += "<TR style=\"background-color:#FFE0B2\"><TH> <TH>Summer<TH>Winter</TR>" ;

		row = "<TR><TD>Steam (lb/hr)<TD align=right>" + mBoilerModel.inSumSteam + "<TD align=right>" + mBoilerModel.inWinSteam + "</TR>";
		htmlString += row ;

		row = "<TR><TD>Hours/Day<TD align=right>" + mBoilerModel.inSumHours + "<TD align=right>" + mBoilerModel.inWinHours + "</TR>" ;
		htmlString += row ;

		row = "<TR><TD>Days/Week<TD align=right>" + mBoilerModel.inSumDays + "<TD align=right>" + mBoilerModel.inWinDays + "</TR>" ;
		htmlString += row ;

		row = "<TR><TD>Weeks/Year<TD align=right>" + mBoilerModel.inSumWeeks + "<TD align=right>" + mBoilerModel.inWinWeeks + "</TR>" ;
		htmlString += row ;


		// Feed Water Section
		htmlString += "<TR><TH colspan=\"3\" style=\"background-color:#F0F8FF\">Feed Water" ;
		htmlString += "<TR style=\"background-color:#FFE0B2\"><TH>Parameter<TH>Value<TH>Units</TR>" ;

		row = "<TR><TD>" + "TDS" + "<TD align=right>" + mBoilerModel.inTDS + "<TD>" + "ppm" + "</TR>" ;
		htmlString += row ;

		row = "<TR><TD>" + "M Alk" + "<TD align=right>" + mBoilerModel.inMAlk + "<TD>" + "ppm" + "</TR>" ;
		htmlString += row ;

		row = "<TR><TD>" + "pH" + "<TD align=right>" + mBoilerModel.inPH + "<TD>" + "" + "</TR>" ;
		htmlString += row ;

		row = "<TR><TD>" + "Ca Hardness" + "<TD align=right>" + mBoilerModel.inCaHardness + "<TD>" + "ppm" + "</TR>" ;
		htmlString += row ;

		row = "<TR><TD>" + "Temperature" + "<TD align=right>" + mBoilerModel.inTemp + "<TD>" + "&deg;C" + "</TR>" ;
		htmlString += row ;


		// Boiler Duty Section
		htmlString += "<TR><TH colspan=\"3\" style=\"background-color:#F0F8FF\">Boiler Details" ;
		htmlString += "<TR style=\"background-color:#FFE0B2\"><TH>Parameter<TH>Value<TH>Units</TR>" ;

		row = "<TR><TD>" + "Max TDS" + "<TD align=right>" + mBoilerModel.inMaxTDS + "<TD>" + "ppm" ;
		htmlString += row ;
		row = "<TR><TD>" + "Min Sulphite Reserve" + "<TD align=right>" + mBoilerModel.inMinSulphite + "<TD>" + "ppm" + "</TR>" ;
		htmlString += row ;
		row = "<TR><TD>" + "Min Caustic Alkalinity" + "<TD align=right>" + mBoilerModel.inMinCausticAlk + "<TD>" + "ppm" + "</TR>" ;
		htmlString += row ;

		htmlString += "</TABLE>" ;


		// Estimated Product amounts
		htmlString += "<br><br>" ;
		htmlString += "<b>Estimated Products (Solids) Needed</b><br>" ;
		htmlString += "<TABLE border=\"1\" style=\"border-collapse:collapse; border: 1px solid black;\">" ;
		htmlString += "<TR style=\"background-color:#F0F8FF\"><TH>Product<TH>Dosage<BR>(g/m<sup>3</sup>)<TH>Usage<BR>(kg/yr)<TH>Description</TR>" ;

		htmlString += rowText(R.string.ss1294Title,   mBoilerModel.ss1294Dosage,   mBoilerModel.ss1294Usage,   R.string.ss1294Description) ;
		htmlString += rowText(R.string.ss1295Title,   mBoilerModel.ss1295Dosage,   mBoilerModel.ss1295Usage,   R.string.ss1295Description) ;
		htmlString += rowText(R.string.ss1350Title,   mBoilerModel.ss1350Dosage,   mBoilerModel.ss1350Usage,   R.string.ss1350Description) ;
		htmlString += rowText(R.string.ss1095Title,   mBoilerModel.ss1095Dosage,   mBoilerModel.ss1095Usage,   R.string.ss1095Description) ;
		htmlString += rowText(R.string.ss1995Title,   mBoilerModel.ss1995Dosage,   mBoilerModel.ss1995Usage,   R.string.ss1995Description) ;
		htmlString += rowText(R.string.ss2295Title,   mBoilerModel.ss2295Dosage,   mBoilerModel.ss2295Usage,   R.string.ss2295Description) ;
		htmlString += rowText(R.string.ss8985Title,   mBoilerModel.ss8985Dosage,   mBoilerModel.ss8985Usage,   R.string.ss8985Description) ;

		htmlString += "</TR>" ;
		htmlString += "</TABLE>" ;

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

			// lookup strings based on ID to allow changing product names and using multiple languages
			String name = Utilities.getStringFromId(mContext, nameId);
			String description = Utilities.getStringFromId(mContext, descriptionId);

			row = "<TR><TD>" + name + "<TD align=right>" + Utilities.round1(dosage)  +
					"<TD align=right>" + Utilities.round1(usage)  + "<TD>" + description + "</TR>" ;
		} catch (Exception e){
			Log.e(TAG, "rowText err: "+e.toString());
			e.printStackTrace();
			row = "<TR><TD>Error<TD><TD><TD></TR>" ;
		}
		return row;
	}

	private Uri createAttachment(String contents) {
		Uri attachUri=null;

		String ATTACH_FILE = "boilersolids";
		FileCache.init();
		String attachPath = FileCache.getFilePath(ATTACH_FILE);
		FileCache.writeTextFile(attachPath, contents);
		attachUri = Uri.fromFile(new File(attachPath));
		
		return attachUri;
	}
	
	// FILE MANAGEMENT UTILITIES
	
	
}
