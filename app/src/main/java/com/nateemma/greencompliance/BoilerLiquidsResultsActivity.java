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

// Class to implement display of Steam Boiler results
public class BoilerLiquidsResultsActivity extends Activity {


	private static final String TAG = "BoilerLiquidsResults";

	private static BoilerModel mBoilerModel = null;

	private Context mContext=null;

	private static TextView vOutS5Dosage;
	private static TextView vOutS5Usage;
	private static TextView vOutS10Dosage;
	private static TextView vOutS10Usage;
	private static TextView vOutS123Dosage;
	private static TextView vOutS123Usage;
	private static TextView vOutS125Dosage;
	private static TextView vOutS125Usage;
	private static TextView vOutS26Dosage;
	private static TextView vOutS26Usage;
	private static TextView vOutS28Dosage;
	private static TextView vOutS28Usage;
	private static TextView vOutS19Dosage;
	private static TextView vOutS19Usage;
	private static TextView vOutS456Dosage;
	private static TextView vOutS456Usage;
	private static TextView vOutS124Dosage;
	private static TextView vOutS124Usage;
	private static TextView vOutS22Dosage;
	private static TextView vOutS22Usage;
	private static TextView vOutS23Dosage;
	private static TextView vOutS23Usage;
	private static TextView vOutS88Dosage;
	private static TextView vOutS88Usage;
	private static TextView vOutS95Dosage;
	private static TextView vOutS95Usage;



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

			setContentView(R.layout.boiler_liquids_results);

			// connect the views with the output fields
			vOutS5Dosage = (TextView)findViewById(R.id.outS5Dosage);
			vOutS5Usage  = (TextView)findViewById(R.id.outS5Usage);
			vOutS10Dosage = (TextView)findViewById(R.id.outS10Dosage);
			vOutS10Usage = (TextView)findViewById(R.id.outS10Usage);
			vOutS123Dosage = (TextView)findViewById(R.id.outS123Dosage);
			vOutS123Usage = (TextView)findViewById(R.id.outS123Usage);
			vOutS125Dosage = (TextView)findViewById(R.id.outS125Dosage);
			vOutS125Usage = (TextView)findViewById(R.id.outS125Usage);
			vOutS26Dosage = (TextView)findViewById(R.id.outS26Dosage);
			vOutS26Usage = (TextView)findViewById(R.id.outS26Usage);
			vOutS28Dosage = (TextView)findViewById(R.id.outS28Dosage);
			vOutS28Usage = (TextView)findViewById(R.id.outS28Usage);
			vOutS19Dosage = (TextView)findViewById(R.id.outS19Dosage);
			vOutS19Usage = (TextView)findViewById(R.id.outS19Usage);
			vOutS456Dosage = (TextView)findViewById(R.id.outS456Dosage);
			vOutS456Usage = (TextView)findViewById(R.id.outS456Usage);
			vOutS124Dosage = (TextView)findViewById(R.id.outS124Dosage);
			vOutS124Usage = (TextView)findViewById(R.id.outS124Usage);
			vOutS22Dosage = (TextView)findViewById(R.id.outS22Dosage);
			vOutS22Usage = (TextView)findViewById(R.id.outS22Usage);
			vOutS23Dosage = (TextView)findViewById(R.id.outS23Dosage);
			vOutS23Usage = (TextView)findViewById(R.id.outS23Usage);
			vOutS88Dosage = (TextView)findViewById(R.id.outS88Dosage);
			vOutS88Usage = (TextView)findViewById(R.id.outS88Usage);
			vOutS95Dosage = (TextView)findViewById(R.id.outS95Dosage);
			vOutS95Usage = (TextView)findViewById(R.id.outS95Usage);


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

			vOutS5Dosage.setText(Utilities.round1(mBoilerModel.s5Dosage));
			vOutS5Usage .setText(Utilities.round1(mBoilerModel.s5Usage));
			vOutS10Dosage.setText(Utilities.round1(mBoilerModel.s10Dosage));
			vOutS10Usage.setText(Utilities.round1(mBoilerModel.s10Usage));
			vOutS123Dosage.setText(Utilities.round1(mBoilerModel.s123Dosage));
			vOutS123Usage.setText(Utilities.round1(mBoilerModel.s123Usage));
			vOutS125Dosage.setText(Utilities.round1(mBoilerModel.s125Dosage));
			vOutS125Usage.setText(Utilities.round1(mBoilerModel.s125Usage));
			vOutS26Dosage.setText(Utilities.round1(mBoilerModel.s26Dosage));
			vOutS26Usage.setText(Utilities.round1(mBoilerModel.s26Usage));
			vOutS28Dosage.setText(Utilities.round1(mBoilerModel.s28Dosage));
			vOutS28Usage.setText(Utilities.round1(mBoilerModel.s28Usage));
			vOutS19Dosage.setText(Utilities.round1(mBoilerModel.s19Dosage));
			vOutS19Usage.setText(Utilities.round1(mBoilerModel.s19Usage));
			vOutS456Dosage.setText(Utilities.round1(mBoilerModel.s456Dosage));
			vOutS456Usage.setText(Utilities.round1(mBoilerModel.s456Usage));
			vOutS124Dosage.setText(Utilities.round1(mBoilerModel.s124Dosage));
			vOutS124Usage.setText(Utilities.round1(mBoilerModel.s124Usage));
			vOutS22Dosage.setText(Utilities.round1(mBoilerModel.s22Dosage));
			vOutS22Usage.setText(Utilities.round1(mBoilerModel.s22Usage));
			vOutS23Dosage.setText(Utilities.round1(mBoilerModel.s23Dosage));
			vOutS23Usage.setText(Utilities.round1(mBoilerModel.s23Usage));
			vOutS88Dosage.setText(Utilities.round1(mBoilerModel.s88Dosage));
			vOutS88Usage.setText(Utilities.round1(mBoilerModel.s88Usage));
			vOutS95Dosage.setText(Utilities.round1(mBoilerModel.s95Dosage));
			vOutS95Usage.setText(Utilities.round1(mBoilerModel.s95Usage));

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
		Uri attachUri = Utilities.createAttachment("boilerliquids", emailBody);

		if (attachUri != null){
			// start email activity
			Intent i = new Intent(Intent.ACTION_SEND);
			i.setType("message/rfc822");
			//i.setType("text/html");
			//i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
			i.putExtra(Intent.EXTRA_SUBJECT, "WTP Product Estimates for Steam Boiler (Liquids)");
			String emailIntro = "<html><br>Please find attached a summary of the WTP Product estimates<br></html>";
			i.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(emailIntro));
			i.putExtra(Intent.EXTRA_STREAM, attachUri);
			try {
				//startActivity(Intent.createChooser(i, "Send mail..."));
				startActivity(i);
			} catch (android.content.ActivityNotFoundException ex) {
				Toast.makeText(mContext, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
				Log.v(TAG, "HTML output:\n"+emailBody);
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
		htmlString += "<b>Estimated Products (Liquids) Needed</b><br>" ;
		htmlString += "<TABLE border=\"1\" style=\"border-collapse:collapse; border: 1px solid black;\">" ;
		htmlString += "<TR style=\"background-color:#F0F8FF\"><TH>Product<TH>Dosage<BR>(g/m<sup>3</sup>)<TH>Usage<BR>(kg/yr)<TH>Description</TR>" ;

		htmlString += rowText(R.string.s5Title,   mBoilerModel.s5Dosage,   mBoilerModel.s5Usage,   R.string.s5Description) ;
		htmlString += rowText(R.string.s10Title,  mBoilerModel.s10Dosage,  mBoilerModel.s10Usage,  R.string.s10Description) ;
		htmlString += rowText(R.string.s123Title, mBoilerModel.s123Dosage, mBoilerModel.s123Usage, R.string.s123Description) ;
		htmlString += rowText(R.string.s125Title, mBoilerModel.s125Dosage, mBoilerModel.s125Usage, R.string.s5Description) ;
		htmlString += rowText(R.string.s26Title,  mBoilerModel.s26Dosage,  mBoilerModel.s26Usage,  R.string.s26Description) ;
		htmlString += rowText(R.string.s28Title,  mBoilerModel.s28Dosage,  mBoilerModel.s28Usage,  R.string.s28Description) ;
		htmlString += rowText(R.string.s19Title,  mBoilerModel.s19Dosage,  mBoilerModel.s19Usage,  R.string.s19Description) ;
		htmlString += rowText(R.string.s456Title, mBoilerModel.s456Dosage, mBoilerModel.s456Usage, R.string.s456Description) ;
		htmlString += rowText(R.string.s124Title, mBoilerModel.s124Dosage, mBoilerModel.s124Usage, R.string.s124Description) ;
		htmlString += rowText(R.string.s22Title,  mBoilerModel.s22Dosage,  mBoilerModel.s22Usage,  R.string.s22Description) ;
		htmlString += rowText(R.string.s23Title,  mBoilerModel.s23Dosage,  mBoilerModel.s23Usage,  R.string.s23Description) ;
		htmlString += rowText(R.string.s88Title,  mBoilerModel.s88Dosage,  mBoilerModel.s88Usage,  R.string.s88Description) ;
		htmlString += rowText(R.string.s95Title,  mBoilerModel.s95Dosage,  mBoilerModel.s95Usage,  R.string.s95Description) ;

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


}
