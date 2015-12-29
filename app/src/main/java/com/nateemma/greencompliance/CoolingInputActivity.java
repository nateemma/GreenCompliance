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

// Activity to handle input for Steam Cooling Parameter Input
public class CoolingInputActivity extends Activity {

	private static final String TAG = "CoolingInputActivity";

	private static CoolingModel mCoolingModel = null;

	private Context mContext;
	
	private String mEstType = ""; // estimate type (solid or liquid)

	// Input Views
	private static EditText vSite; // this is the key for the data

	private static EditText vCirculation;
	private static EditText vDeltaT;
	private static EditText vSumpVolume;
	private static EditText vSysVolume;
	private static EditText vConcFactor;

	private static EditText vTDS;
	private static EditText vTotalHardness;
	private static EditText vMAlk;
	private static EditText vPH;
	private static EditText vChlorides;

	private static EditText vHours;
	private static EditText vDays;
	private static EditText vWeeks;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {

			mContext = this;
		    getActionBar().setDisplayHomeAsUpEnabled(true);

			setContentView(R.layout.cooling_entry);

			// connect the views with the input fields
			vSite = (EditText)findViewById(R.id.inSite);
			vCirculation = (EditText)findViewById(R.id.inCirculation);
			vDeltaT = (EditText)findViewById(R.id.inDeltaT);
			vSumpVolume = (EditText)findViewById(R.id.inSumpVolume);
			vSysVolume = (EditText)findViewById(R.id.inSysVolume);
			vConcFactor = (EditText)findViewById(R.id.inConcFactor);
			vTDS = (EditText)findViewById(R.id.inTDS);
			vTotalHardness = (EditText)findViewById(R.id.inTotalHardness);
			vMAlk = (EditText)findViewById(R.id.inMAlk);
			vPH = (EditText)findViewById(R.id.inPH);
			vChlorides = (EditText)findViewById(R.id.inChlorides);
			vHours = (EditText)findViewById(R.id.inHours);
			vDays = (EditText)findViewById(R.id.inDays);
			vWeeks = (EditText)findViewById(R.id.inWeeks);

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
		if (mCoolingModel != null){
			mCoolingModel.save();
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
			mCoolingModel = CoolingModel.sharedInstance();
			mCoolingModel.setContext(mContext);
			mCoolingModel.restore();

			// update the display
			vSite.setText(mCoolingModel.inSite);
			vCirculation.setText(mCoolingModel.inCirculation);
			vDeltaT.setText(mCoolingModel.inDeltaT);
			vSumpVolume.setText(mCoolingModel.inSumpVolume);
			vSysVolume.setText(mCoolingModel.inSysVolume);
			vConcFactor.setText(mCoolingModel.inConcFactor);
			vTDS.setText(mCoolingModel.inTDS);
			vTotalHardness.setText(mCoolingModel.inTotalHardness);
			vMAlk.setText(mCoolingModel.inMAlk);
			vPH.setText(mCoolingModel.inPH);
			vChlorides.setText(mCoolingModel.inChlorides);
			vHours.setText(mCoolingModel.inHours);
			vDays.setText(mCoolingModel.inDays);
			vWeeks.setText(mCoolingModel.inWeeks);
		} catch (Exception e){
			Log.e(TAG, "loadData() exception: "+e.toString());
			e.printStackTrace();
		}
	}


	// routine to verify that data has been entered. Also updates binary parameters
	private Boolean checkInputFields(){
		Boolean result = true;

		try{
			// check that all fields have been entered
			if (vSite.getText().length()>0){
				mCoolingModel.inSite = vSite.getText().toString();
			} else {
				vSite.requestFocus();
				Log.w(TAG, "inSite not defined");
				return false;
			}

			if (vCirculation.getText().length()>0) {
				mCoolingModel.circulation = Double.parseDouble(vCirculation.getText().toString());
			}  else {
				vCirculation.requestFocus();
				Log.e(TAG, "CoolingInputViewController: inCirculation not defined");
				return false;
			}

			if (vDeltaT.getText().length()>0){
				mCoolingModel.deltaT = Double.parseDouble(vDeltaT.getText().toString());
			}  else {
				vDeltaT.requestFocus();
				Log.e(TAG, "CoolingInputViewController: inDeltaT not defined");
				return false;
			}

			if (vSumpVolume.getText().length()>0){
				mCoolingModel.sumpVolume = Double.parseDouble(vSumpVolume.getText().toString());
			}  else {
				vSumpVolume.requestFocus();
				Log.e(TAG, "CoolingInputViewController: inSumpVolume not defined");
				return false;
			}

			if (vSysVolume.getText().length()>0){
				mCoolingModel.sysVolume = Double.parseDouble(vSysVolume.getText().toString());
			}  else {
				vSysVolume.requestFocus();
				Log.e(TAG, "CoolingInputViewController: inSysVolume not defined");
				return false;
			}

			if (vConcFactor.getText().length()>0){
				mCoolingModel.concFactor = Double.parseDouble(vConcFactor.getText().toString());
			}  else {
				vConcFactor.requestFocus();
				Log.e(TAG, "CoolingInputViewController: inConcFactor not defined");
				return false;
			}

			if (vTDS.getText().length()>0){
				mCoolingModel.TDS = Double.parseDouble(vTDS.getText().toString());
			}  else {
				vTDS.requestFocus();
				Log.e(TAG, "CoolingInputViewController: inTDS not defined");
				return false;
			}

			if (vTotalHardness.getText().length()>0){
				mCoolingModel.totalHardness = Double.parseDouble(vTotalHardness.getText().toString());
			}  else {
				vTotalHardness.requestFocus();
				Log.e(TAG, "CoolingInputViewController: inTotalHardness not defined");
				return false;
			}

			if (vMAlk.getText().length()>0){
				mCoolingModel.MAlk = Double.parseDouble(vMAlk.getText().toString());
			}  else {
				vMAlk.requestFocus();
				Log.e(TAG, "CoolingInputViewController: inMAlk not defined");
				return false;
			}

			if (vPH.getText().length()>0){
				mCoolingModel.pH = Double.parseDouble(vPH.getText().toString());
			}  else {
				vPH.requestFocus();
				Log.e(TAG, "CoolingInputViewController: inPH not defined");
				return false;
			}

			if (vChlorides.getText().length()>0){
				mCoolingModel.chlorides = Double.parseDouble(vChlorides.getText().toString());
			}  else {
				vChlorides.requestFocus();
				Log.e(TAG, "CoolingInputViewController: inChlorides not defined");
				return false;
			}

			if (vHours.getText().length()>0){
				mCoolingModel.hours = Double.parseDouble(vHours.getText().toString());
			}  else {
				vHours.requestFocus();
				Log.e(TAG, "CoolingInputViewController: inHours not defined");
				return false;
			}

			if (vDays.getText().length()>0){
				mCoolingModel.days = Double.parseDouble(vDays.getText().toString());
			}  else {
				vDays.requestFocus();
				Log.e(TAG, "CoolingInputViewController: inDays not defined");
				return false;
			}

			if (vWeeks.getText().length()>0){
				mCoolingModel.weeks = Double.parseDouble(vWeeks.getText().toString());
			}  else {
				vWeeks.requestFocus();
				Log.e(TAG, "CoolingInputViewController: inWeeks not defined");
				return false;
			}


			// fields are OK, so copy input (text) values into model
			mCoolingModel.inSite          = vSite.getText().toString();
			mCoolingModel.inCirculation   = vCirculation.getText().toString();
			mCoolingModel.inDeltaT        = vDeltaT.getText().toString();
			mCoolingModel.inSumpVolume    = vSumpVolume.getText().toString();
			mCoolingModel.inSysVolume     = vSysVolume.getText().toString();
			mCoolingModel.inConcFactor    = vConcFactor.getText().toString();
			mCoolingModel.inTDS           = vTDS.getText().toString();
			mCoolingModel.inTotalHardness = vTotalHardness.getText().toString();
			mCoolingModel.inMAlk          = vMAlk.getText().toString();
			mCoolingModel.inPH            = vPH.getText().toString();
			mCoolingModel.inChlorides     = vChlorides.getText().toString();
			mCoolingModel.inHours         = vHours.getText().toString();
			mCoolingModel.inDays          = vDays.getText().toString();
			mCoolingModel.inWeeks         = vWeeks.getText().toString();

		} catch (Exception e) {
			Log.e(TAG, "checkInputFields() exception: "+e.toString());
			e.printStackTrace();
		}

		return result;
	}




	// process input data and calculate products
	private void estimate(){
		Log.d(TAG, "estimate()");
		if (checkInputFields()){
			mCoolingModel.save();
			mCoolingModel.calculateAmounts();
			Log.v(TAG, "Launching Results Activity");
			Intent intent = new Intent();
			if (mEstType.equals(LocalIntents.SOLIDS)){
				intent.setAction(LocalIntents.COOLING_SOLIDS_RESULTS);
			} else if (mEstType.equals(LocalIntents.LIQUIDS)){
				intent.setAction(LocalIntents.COOLING_LIQUIDS_RESULTS);
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
