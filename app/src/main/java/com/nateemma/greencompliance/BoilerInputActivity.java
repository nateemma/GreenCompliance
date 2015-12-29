package com.nateemma.greencompliance;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

// Activity to handle input for Steam Boiler Parameter Input
public class BoilerInputActivity extends Activity {

	private static final String TAG = "BoilerInputActivity";

	private static BoilerModel mBoilerModel = null;

	private Context mContext;

	private String mEstType = ""; // estimate type (solid or liquid)

	// Input Views
	private static EditText vSite;
	private static EditText vSumSteam;
	private static EditText vSumHours;
	private static EditText vSumDays;
	private static EditText vSumWeeks;
	private static EditText vWinSteam;
	private static EditText vWinHours;
	private static EditText vWinDays;
	private static EditText vWinWeeks;
	private static EditText vTDS;
	private static EditText vMAlk;
	private static EditText vPH;
	private static EditText vCaHardness;
	private static EditText vTemp;
	private static EditText vMaxTDS;
	private static EditText vMinSulphite;
	private static EditText vMinCausticAlk;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {

			mContext = this;
			getActionBar().setDisplayHomeAsUpEnabled(true);

			setContentView(R.layout.boiler_entry);

			// connect the views with the input fields
			vSite = (EditText)findViewById(R.id.inSite);
			vSumSteam = (EditText)findViewById(R.id.inSteamSum);
			vSumHours = (EditText)findViewById(R.id.inHoursSum);
			vSumDays = (EditText)findViewById(R.id.inDaysSum);
			vSumWeeks = (EditText)findViewById(R.id.inWeeksSum);
			vWinSteam = (EditText)findViewById(R.id.inSteamWin);
			vWinHours = (EditText)findViewById(R.id.inHoursWin);
			vWinDays = (EditText)findViewById(R.id.inDaysWin);
			vWinWeeks = (EditText)findViewById(R.id.inWeeksWin);
			vTDS = (EditText)findViewById(R.id.inTDS);
			vMAlk = (EditText)findViewById(R.id.inMAlk);
			vPH = (EditText)findViewById(R.id.inPH);
			vCaHardness = (EditText)findViewById(R.id.inCaHardness);
			vTemp = (EditText)findViewById(R.id.inTemp);
			vMaxTDS = (EditText)findViewById(R.id.inMaxTDS);
			vMinSulphite = (EditText)findViewById(R.id.inMinSulphite);
			vMinCausticAlk = (EditText)findViewById(R.id.inMinCaustic);

			// load the initial data
			loadData();

			// get the estimate type
			mEstType = getIntent().getStringExtra(LocalIntents.ARG0);
			if ((mEstType==null) || (mEstType.length()==0)){
				mEstType = LocalIntents.SOLIDS;
			}

		} catch (Exception e){
			Log.e(TAG, "onCreate() Error: "+e.toString());
			e.printStackTrace();
		}

	}


	@Override
	protected void onDestroy() {
		super.onDestroy();

		// save the data for next time
		if (mBoilerModel != null){
			mBoilerModel.save();
		}

	} //onDestroy


	// MENU HANDLING

	// Register the menu actions for display on the action bar
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.input_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}


	// Handler for the menu action(s)
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_estimate:
			estimate();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	// INPUT DATA HANDLING

	// routine to notify the user that there is an incomplete field
	private void notifyInputError(){
		try{
			String description = mContext.getResources().getString(R.string.inputError);
			Toast.makeText(getApplicationContext(), description, Toast.LENGTH_SHORT).show();
		} catch (Exception e){
			Log.e(TAG, "Error displaying dialogue: "+e.toString());
		}
	}

	// APP LOGIC

	// load the initial set of data
	private void loadData(){
		try{
			mBoilerModel = BoilerModel.sharedInstance();
			mBoilerModel.setContext(mContext);
			mBoilerModel.restore();

			// update the display
			vSite.setText(mBoilerModel.inSite);
			vSumSteam.setText(mBoilerModel.inSumSteam);
			vSumHours.setText(mBoilerModel.inSumHours);
			vSumDays.setText(mBoilerModel.inSumDays);
			vSumWeeks.setText(mBoilerModel.inSumWeeks);
			vWinSteam.setText(mBoilerModel.inWinSteam);
			vWinHours.setText(mBoilerModel.inWinHours);
			vWinDays.setText(mBoilerModel.inWinDays);
			vWinWeeks.setText(mBoilerModel.inWinWeeks);
			vTDS.setText(mBoilerModel.inTDS);
			vMAlk.setText(mBoilerModel.inMAlk);
			vPH.setText(mBoilerModel.inPH);
			vCaHardness.setText(mBoilerModel.inCaHardness);
			vTemp.setText(mBoilerModel.inTemp);
			vMaxTDS.setText(mBoilerModel.inMaxTDS);
			vMinSulphite.setText(mBoilerModel.inMinSulphite);
			vMinCausticAlk.setText(mBoilerModel.inMinCausticAlk);
		} catch (Exception e){
			Log.e(TAG, "loadData() exception: "+e.toString());
		}
	}


	// routine to verify that data has been entered
	private Boolean checkInputFields(){
		Boolean result = true;

		try{
			if (vSite.getText().length()>0){
				mBoilerModel.inSite = vSite.getText().toString();
			} else {
				vSite.requestFocus();
				Log.w(TAG, "inSite not defined");
				return false;
			}

			if (vSumSteam.getText().length()>0) {
				mBoilerModel.sumSteam = Double.parseDouble(vSumSteam.getText().toString());
			}  else {
				vSumSteam.requestFocus();
				Log.w(TAG, "inSumSteam not defined");
				return false;
			}

			if (vSumHours.getText().length()>0){
				mBoilerModel.sumHours = Double.parseDouble(vSumHours.getText().toString());
			}  else {
				vSumHours.requestFocus();
				Log.w(TAG, "inSumHours not defined");
				return false;
			}

			if (vSumDays.getText().length()>0){
				mBoilerModel.sumDays = Double.parseDouble(vSumDays.getText().toString());
			}  else {
				vSumDays.requestFocus();
				Log.w(TAG, "inSumDays not defined");
				return false;
			}

			if (vSumWeeks.getText().length()>0){
				mBoilerModel.sumWeeks = Double.parseDouble(vSumWeeks.getText().toString());
			}  else {
				vSumWeeks.requestFocus();
				Log.w(TAG, "inSumWeeks not defined");
				return false;
			}

			if (vWinSteam.getText().length()>0){
				mBoilerModel.winSteam = Double.parseDouble(vWinSteam.getText().toString());
			}  else {
				vWinSteam.requestFocus();
				Log.w(TAG, "inWinSteam not defined");
				return false;
			}

			if (vWinHours.getText().length()>0){
				mBoilerModel.winHours = Double.parseDouble(vWinHours.getText().toString());
			}  else {
				vWinHours.requestFocus();
				Log.w(TAG, "inWinHours not defined");
				return false;
			}

			if (vWinDays.getText().length()>0){
				mBoilerModel.winDays = Double.parseDouble(vWinDays.getText().toString());
			}  else {
				vWinDays.requestFocus();
				Log.w(TAG, "inWinDays not defined");
				return false;
			}

			if (vWinWeeks.getText().length()>0){
				mBoilerModel.winWeeks = Double.parseDouble(vWinWeeks.getText().toString());
			}  else {
				vWinWeeks.requestFocus();
				Log.w(TAG, "inWinWeeks not defined");
				return false;
			}

			if (vTDS.getText().length()>0){
				mBoilerModel.TDS = Double.parseDouble(vTDS.getText().toString());
			}  else {
				vTDS.requestFocus();
				Log.w(TAG, "inTDS not defined");
				return false;
			}

			if (vMAlk.getText().length()>0){
				mBoilerModel.MAlk = Double.parseDouble(vMAlk.getText().toString());
			}  else {
				vMAlk.requestFocus();
				Log.w(TAG, "inMAlk not defined");
				return false;
			}

			if (vPH.getText().length()>0){
				mBoilerModel.pH = Double.parseDouble(vPH.getText().toString());
			}  else {
				vPH.requestFocus();
				Log.w(TAG, "inPH not defined");
				return false;
			}

			if (vCaHardness.getText().length()>0){
				mBoilerModel.CaHardness = Double.parseDouble(vCaHardness.getText().toString());
			}  else {
				vCaHardness.requestFocus();
				Log.w(TAG, "inCaHardness not defined");
				return false;
			}

			if (vTemp.getText().length()>0){
				mBoilerModel.temp = Double.parseDouble(vTemp.getText().toString());
			}  else {
				vTemp.requestFocus();
				Log.w(TAG, "inTemp not defined");
				return false;
			}

			if (vMaxTDS.getText().length()>0){
				mBoilerModel.maxTDS = Double.parseDouble(vMaxTDS.getText().toString());
			}  else {
				vMaxTDS.requestFocus();
				Log.w(TAG, "inMaxTDS not defined");
				return false;
			}

			if (vMinSulphite.getText().length()>0){
				mBoilerModel.minSulphite = Double.parseDouble(vMinSulphite.getText().toString());
			}  else {
				vMinSulphite.requestFocus();
				Log.w(TAG, "inMinSulphite not defined");
				return false;
			}

			if (vMinCausticAlk.getText().length()>0){
				mBoilerModel.minCausticAlk = Double.parseDouble(vMinCausticAlk.getText().toString());
			}  else {
				vMinCausticAlk.requestFocus();
				Log.w(TAG, "inMinCausticAlk not defined");
				return false;
			}

			// fields are OK, so copy input (text) values into model
			mBoilerModel.inSite = vSite.getText().toString();
			mBoilerModel.inSumSteam = vSumSteam.getText().toString();
			mBoilerModel.inSumHours = vSumHours.getText().toString();
			mBoilerModel.inSumDays = vSumDays.getText().toString();
			mBoilerModel.inSumWeeks = vSumWeeks.getText().toString();
			mBoilerModel.inWinSteam = vWinSteam.getText().toString();
			mBoilerModel.inWinHours = vWinHours.getText().toString();
			mBoilerModel.inWinDays = vWinDays.getText().toString();
			mBoilerModel.inWinWeeks = vWinWeeks.getText().toString();
			mBoilerModel.inTDS = vTDS.getText().toString();
			mBoilerModel.inMAlk = vMAlk.getText().toString();
			mBoilerModel.inPH = vPH.getText().toString();
			mBoilerModel.inCaHardness = vCaHardness.getText().toString();
			mBoilerModel.inTemp = vTemp.getText().toString();
			mBoilerModel.inMaxTDS = vMaxTDS.getText().toString();
			mBoilerModel.inMinSulphite = vMinSulphite.getText().toString();
			mBoilerModel.inMinCausticAlk = vMinCausticAlk.getText().toString();
		} catch (Exception e) {
			Log.e(TAG, "checkInputFields() exception: "+e.toString());
		}

		return result;
	}




	// process input data, calculate products and launch appropriate  display activity for results
	private void estimate(){
		Log.d(TAG, "estimate()");
		if (checkInputFields()){
			mBoilerModel.save();
			mBoilerModel.calculateAmounts();
			Log.v(TAG, "Launching Results Activity");
			Intent intent = new Intent();
			if (mEstType.equals(LocalIntents.SOLIDS)){
				intent.setAction(LocalIntents.BOILER_SOLIDS_RESULTS);
			} else if (mEstType.equals(LocalIntents.LIQUIDS)){
				intent.setAction(LocalIntents.BOILER_LIQUIDS_RESULTS);
			} else {
				Log.e(TAG, "invalid estimate type: "+mEstType);
				return;
			}
			try {
				startActivity(intent);
			} catch (Throwable t){
				Log.e(TAG, "startActivity exception: " + t.toString());
				Toast.makeText(mContext, "Error starting Results activity: "+mEstType, Toast.LENGTH_SHORT).show();
			}

		} else {
			notifyInputError();
		}
	}

}
