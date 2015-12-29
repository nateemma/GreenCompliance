package com.nateemma.greencompliance;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

// 11/2/13 - removed cost/kg and cost/annum variables (just commented out for now)

// Class that implements the 'model' for cooling calculations
public class CoolingModel {

	private static final String TAG = "CoolingModel";

	// the actual (shared) instance
	private static CoolingModel _sharedInstance;

	// Application context
	private static Context mContext = null;

	// String values provided by user
	public String inSite; // this is the key for the data

	public String inCirculation;
	public String inDeltaT;
	public String inSumpVolume;
	public String inSysVolume;
	public String inConcFactor;

	public String inTDS;
	public String inTotalHardness;
	public String inMAlk;
	public String inPH;
	public String inChlorides;

	public String inHours;
	public String inDays;
	public String inWeeks;


	// Criteria parameters
	public Double circulation;
	public Double deltaT;
	public Double sumpVolume;
	public Double sysVolume;
	public Double concFactor;


	// Makeup Water Quality params
	public Double TDS;
	public Double totalHardness;
	public Double MAlk;
	public Double pH;
	public Double chlorides;


	// Duty/Operation params

	public Double hours;
	public Double days;
	public Double weeks;


	// Calculated values

	// Criteria-based intermediate values
	public Double evaporation;
	public Double bleed;
	public Double makeup;
	public Double makeupAnnum;

	// I don't know what these are, just intermediate values
	public Double theoryQ1;
	public Double theoryQ2;
	public Double theoryQ3;
	public Double theoryQ4;
	public Double theoryQ5;

	// SOLIDS
	
	// Product-based values
	public Double hs2097Dosage;
	public Double hs2097Usage;
	//public Double hs2097CostKg;
	//public Double hs2097CostAnnum;

	public Double hs4390Dosage;
	public Double hs4390Usage;
	//public Double hs4390CostKg;
	//public Double hs4390CostAnnum;

	public Double hs3990Dosage;
	public Double hs3990Usage;
	//public Double hs3990CostKg;
	//public Double hs3990CostAnnum;

	// Biocide values

	public Double cs4400Dosage;
	public Double cs4400Usage;
	//public Double cs4400CostKg;
	//public Double cs4400CostAnnum;

	public Double cs4802Dosage;
	public Double cs4802Usage;
	//public Double cs4802CostKg;
	//public Double cs4802CostAnnum;

	public Double cs4490Dosage;
	public Double cs4490Usage;
	//public Double cs4490CostKg;
	//public Double cs4490CostAnnum;

	public Double scg1Dosage;
	public Double scg1Usage;
	//public Double scg1CostKg;
	//public Double scg1CostAnnum;

	public Double cschlorDosage;
	public Double cschlorUsage;
	//public Double cschlorCostKg;
	//public Double cschlorCostAnnum;

	public Double c42tDosage;
	public Double c42tUsage;
	//public Double c42tCostKg;
	//public Double c42tCostAnnum;

	// LIQUIDS
	
	// Inhibitors
	public Double h207Dosage;
	public Double h207Usage;
	
	public Double h2073Dosage;
	public Double h2073Usage;
	
	public Double h280Dosage;
	public Double h280Usage;
	
	public Double h2805Dosage;
	public Double h2805Usage;
	
	public Double h390Dosage;
	public Double h390Usage;
	
	public Double h3905Dosage;
	public Double h3905Usage;
	
	public Double h391Dosage;
	public Double h391Usage;
	
	public Double h423Dosage;
	public Double h423Usage;
	
	public Double h425Dosage;
	public Double h425Usage;
	
	public Double h4255Dosage;
	public Double h4255Usage;
	
	public Double h535Dosage;
	public Double h535Usage;
	
	public Double h874Dosage;
	public Double h874Usage;
	
	// Biocides - non-oxidisers
	public Double c31Dosage;
	public Double c31Usage;
	
	public Double c32Dosage;
	public Double c32Usage;
	
	public Double c44Dosage;
	public Double c44Usage;
	
	public Double c45Dosage;
	public Double c45Usage;
	
	public Double c48Dosage;
	public Double c48Usage;
	
	public Double c51Dosage;
	public Double c51Usage;
	
	public Double c52Dosage;
	public Double c52Usage;
	
	public Double c54Dosage;
	public Double c54Usage;
	
	public Double c58Dosage;
	public Double c58Usage;
	
	
	// Biocides - oxidising
	// No calculations here, just text output (for now)
	

	// FRAMEWORK METHODS


	// default constructor
	private CoolingModel() { 
		// no implementation, just private to control usage, i.e. must call sharedInstance
	} 


	// prevent instantiation via an explicit clone() (or "=" operator) call
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	// gets the copy of the cooling model
	public static CoolingModel sharedInstance(){
		if (_sharedInstance == null) {
			_sharedInstance = new CoolingModel();
		}
		return _sharedInstance;
	}

	// need context to use system functions, so provide setter function
	public void setContext (Context context){
		mContext = context;
		/***
		if ((_sharedInstance.inSite==null) || (_sharedInstance.inSite.length()==0)) { // first time?
			_sharedInstance.restore(); // can't do this until Context has been set
		}
		 ***/
	}


	// check that all inputs have been defined and are valid
	public Boolean inputsValid(){
		return true; //TODO: add field checks
	}


	// SAVE/RESTORE METHODS

	// name of storage file for saved data
	private static final String PREFS_NAME = "CoolingModel";

	// save values to persistent storage
	public void save(){
		try{
			if (mContext != null){
				SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);
				SharedPreferences.Editor editor = settings.edit();

				editor.putString("inSite", _sharedInstance.inSite);
				editor.putString("inCirculation", _sharedInstance.inCirculation);
				editor.putString("inDeltaT", _sharedInstance.inDeltaT);
				editor.putString("inSumpVolume", _sharedInstance.inSumpVolume);
				editor.putString("inSysVolume", _sharedInstance.inSysVolume);
				editor.putString("inConcFactor", _sharedInstance.inConcFactor);
				editor.putString("inTDS", _sharedInstance.inTDS);
				editor.putString("inTotalHardness", _sharedInstance.inTotalHardness);
				editor.putString("inMAlk", _sharedInstance.inMAlk);
				editor.putString("inPH", _sharedInstance.inPH);
				editor.putString("inChlorides", _sharedInstance.inChlorides);
				editor.putString("inHours", _sharedInstance.inHours);
				editor.putString("inDays", _sharedInstance.inDays);
				editor.putString("inWeeks", _sharedInstance.inWeeks);

				// Commit the edits!
				editor.commit();
				Log.v(TAG, "save() site:"+_sharedInstance.inSite);
			} else {
				Log.e(TAG, "save() Context not defined. Preferences NOT saved");
			}
		} catch (Exception e){
			Log.e(TAG, "save() exception: "+e.toString());
			e.printStackTrace();
		}
	} // save()


	// restore saved values
	public void restore(){
		try{
			if (mContext != null){
				SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);

				if (settings != null){
					_sharedInstance.inSite = settings.getString("inSite", "");
					if (_sharedInstance.inSite.length()>0){
						// retrieve user-entered strings
						_sharedInstance.inSite = settings.getString("inSite", "");
						_sharedInstance.inCirculation = settings.getString("inCirculation", "0.0");
						_sharedInstance.inDeltaT = settings.getString("inDeltaT", "0.0");
						_sharedInstance.inSumpVolume = settings.getString("inSumpVolume", "0.0");
						_sharedInstance.inSysVolume = settings.getString("inSysVolume", "0.0");
						_sharedInstance.inConcFactor = settings.getString("inConcFactor", "0.0");
						_sharedInstance.inTDS = settings.getString("inTDS", "0.0");
						_sharedInstance.inTotalHardness = settings.getString("inTotalHardness", "0.0");
						_sharedInstance.inMAlk = settings.getString("inMAlk", "0.0");
						_sharedInstance.inPH = settings.getString("inPH", "0.0");
						_sharedInstance.inChlorides = settings.getString("inChlorides", "0.0");
						_sharedInstance.inHours = settings.getString("inHours", "0.0");
						_sharedInstance.inDays = settings.getString("inDays", "0.0");
						_sharedInstance.inWeeks = settings.getString("inWeeks", "0.0");


						// convert strings and update binary versions of parameters
						_sharedInstance.circulation = Double.valueOf(_sharedInstance.inCirculation);
						_sharedInstance.deltaT = Double.valueOf(_sharedInstance.inDeltaT);
						_sharedInstance.sumpVolume = Double.valueOf(_sharedInstance.inSumpVolume);
						_sharedInstance.sysVolume = Double.valueOf(_sharedInstance.inSysVolume);
						_sharedInstance.concFactor = Double.valueOf(_sharedInstance.inConcFactor);
						_sharedInstance.TDS = Double.valueOf(_sharedInstance.inTDS);
						_sharedInstance.totalHardness = Double.valueOf(_sharedInstance.inTotalHardness);
						_sharedInstance.MAlk = Double.valueOf(_sharedInstance.inMAlk);
						_sharedInstance.pH = Double.valueOf(_sharedInstance.inPH);
						_sharedInstance.chlorides = Double.valueOf(_sharedInstance.inChlorides);
						_sharedInstance.hours = Double.valueOf(_sharedInstance.inHours);
						_sharedInstance.days = Double.valueOf(_sharedInstance.inDays);
						_sharedInstance.weeks = Double.valueOf(_sharedInstance.inWeeks);

						Log.d(TAG, "Saved data restored");
					} else {
						Log.d(TAG, "restore(), no data found");
					}

				} else {
					Log.e(TAG, "restore() Settings file not found!");
				}

			} else {
				Log.e(TAG, "restore() Context not defined. Preferences NOT fully restored");
			}
		} catch (Exception e){
			Log.e(TAG, "restore() exception: "+e.toString());
			e.printStackTrace();
		}

	}//restore()


	// check whether any saved data is available
	public Boolean savedDataPresent(){
		Boolean result = false;
		SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);
		if (settings != null){
			String site = settings.getString("inSite", "");
			if ((site!=null) && site.length()>0){
				result = true;
			}
		}
		return result;
	}


	// FUNCTIONAL LOGIC

	// calculate the product amounts, based on input values
	// Note: expects inputs to have already been verified

	public void calculateAmounts(){
		Double tmp;

		try {
			if (_sharedInstance.inputsValid()){
				
				// SOLIDS
				
				// Evaporation
				tmp = _sharedInstance.circulation * _sharedInstance.deltaT * 1.8 * 0.001;// 1.8 = conversion to Farenheit
				_sharedInstance.evaporation = tmp;
				//Log.d(TAG, "CoolingWaterModel: evaporation=%.08f", _sharedInstance.evaporation);

				// Bleed
				tmp = _sharedInstance.evaporation / (_sharedInstance.concFactor - 1.0);
				_sharedInstance.bleed = tmp;
				//Log.d(TAG, "CoolingWaterModel: bleed=%.08f", _sharedInstance.bleed);

				// Makeup
				tmp = _sharedInstance.evaporation + _sharedInstance.bleed;
				_sharedInstance.makeup = tmp;
				//Log.d(TAG, "CoolingWaterModel: makeup=%.08f", _sharedInstance.makeup);

				// Makeup/Annum
				tmp = (_sharedInstance.makeup * (_sharedInstance.hours * _sharedInstance.days * _sharedInstance.weeks));

				_sharedInstance.makeupAnnum = tmp;
				//Log.d(TAG, "CoolingWaterModel: hrs:%.02f days:%.02f weeks:%.02f", _sharedInstance.hours, _sharedInstance.days, _sharedInstance.weeks);
				//Log.d(TAG, "CoolingWaterModel: makeupAnnum=%.02f", _sharedInstance.makeupAnnum);

				// Theoretical Quality values
				tmp = _sharedInstance.TDS * _sharedInstance.concFactor;
				_sharedInstance.theoryQ1 = tmp;
				//Log.d(TAG, "CoolingWaterModel: theoryQ1=%.02f", _sharedInstance.theoryQ1);

				tmp = _sharedInstance.totalHardness * _sharedInstance.concFactor;
				_sharedInstance.theoryQ2 = tmp;
				//Log.d(TAG, "CoolingWaterModel: theoryQ2=%.02f", _sharedInstance.theoryQ2);

				tmp = _sharedInstance.MAlk * _sharedInstance.concFactor;
				_sharedInstance.theoryQ3 = tmp;
				//Log.d(TAG, "CoolingWaterModel: theoryQ3=%.02f", _sharedInstance.theoryQ3);

				tmp = _sharedInstance.pH + 0.7;
				_sharedInstance.theoryQ4 = tmp;
				//Log.d(TAG, "CoolingWaterModel: theoryQ4=%.02f", _sharedInstance.theoryQ4);

				tmp = _sharedInstance.chlorides * _sharedInstance.concFactor;
				_sharedInstance.theoryQ5 = tmp;
				//Log.d(TAG, "CoolingWaterModel: theoryQ5=%.02f", _sharedInstance.theoryQ5);

				// Products parameters
				_sharedInstance.hs2097Dosage = 20.0;
				tmp = ((_sharedInstance.hs2097Dosage / 1000.0) / _sharedInstance.concFactor) * _sharedInstance.makeupAnnum;
				_sharedInstance.hs2097Usage  = tmp;
				/***
				_sharedInstance.hs2097CostKg = 7.44;
				tmp = _sharedInstance.hs2097Usage * _sharedInstance.hs2097CostKg;
				_sharedInstance.hs2097CostAnnum = tmp;
				***/

				_sharedInstance.hs4390Dosage = 20.0;
				tmp = ((_sharedInstance.hs4390Dosage / 1000.0) / _sharedInstance.concFactor) * _sharedInstance.makeupAnnum;
				_sharedInstance.hs4390Usage  = tmp;
				/***
				_sharedInstance.hs4390CostKg = 15.84;
				tmp = _sharedInstance.hs4390Usage * _sharedInstance.hs4390CostKg;
				_sharedInstance.hs4390CostAnnum = tmp;
				***/

				_sharedInstance.hs3990Dosage = 20.0;
				tmp = ((_sharedInstance.hs3990Dosage / 1000.0) / _sharedInstance.concFactor) * _sharedInstance.makeupAnnum;
				_sharedInstance.hs3990Usage  = tmp;
				/***
				_sharedInstance.hs3990CostKg = 8.4;
				tmp = _sharedInstance.hs3990Usage * _sharedInstance.hs3990CostKg;
				_sharedInstance.hs3990CostAnnum = tmp;
				***/

				// Biocides
				_sharedInstance.cs4400Dosage = 15.0;
				tmp = _sharedInstance.sysVolume * 52.0 * (_sharedInstance.cs4400Dosage / 1000.0);
				_sharedInstance.cs4400Usage  = tmp;
				/***
				_sharedInstance.cs4400CostKg = 16.55;
				tmp = _sharedInstance.cs4400Usage * _sharedInstance.cs4400CostKg;
				_sharedInstance.cs4400CostAnnum = tmp;
				***/

				_sharedInstance.cs4802Dosage = 20.0;
				tmp = _sharedInstance.sumpVolume * 52.0 * (_sharedInstance.cs4802Dosage / 1000.0);
				_sharedInstance.cs4802Usage  = tmp;
				/***
				_sharedInstance.cs4802CostKg = 12.8;
				tmp = _sharedInstance.cs4802Usage * _sharedInstance.cs4802CostKg;
				_sharedInstance.cs4802CostAnnum = tmp;
				***/

				_sharedInstance.cs4490Dosage = 20.0;
				tmp = _sharedInstance.sysVolume * 52.0 * (_sharedInstance.cs4490Dosage / 1000.0);
				_sharedInstance.cs4490Usage  = tmp;
				/***
				_sharedInstance.cs4490CostKg = 22.25;
				tmp = _sharedInstance.cs4490Usage * _sharedInstance.cs4490CostKg;
				_sharedInstance.cs4490CostAnnum = tmp;
				***/

				_sharedInstance.scg1Dosage = 5.0;
				tmp = _sharedInstance.sysVolume * 52.0 * (_sharedInstance.scg1Dosage / 1000.0);
				_sharedInstance.scg1Usage  = tmp;
				/***
				_sharedInstance.scg1CostKg = 7.3;
				tmp = _sharedInstance.scg1Usage * _sharedInstance.scg1CostKg;
				_sharedInstance.scg1CostAnnum = tmp;
				***/

				_sharedInstance.cschlorDosage = 5.0;
				tmp = _sharedInstance.sysVolume * 52.0 * (_sharedInstance.cschlorDosage / 1000.0);
				_sharedInstance.cschlorUsage  = tmp;			
				/***
				_sharedInstance.cschlorCostKg = 9.69;
				tmp = _sharedInstance.cschlorUsage * _sharedInstance.cschlorCostKg;
				_sharedInstance.cschlorCostAnnum = tmp;
				***/

				_sharedInstance.c42tDosage = 5.0;
				tmp = _sharedInstance.sysVolume * 52.0 * (_sharedInstance.c42tDosage / 1000.0);
				_sharedInstance.c42tUsage  = tmp;
				/***
				_sharedInstance.c42tCostKg = 6.9;
				tmp = _sharedInstance.c42tUsage * _sharedInstance.c42tCostKg;
				_sharedInstance.c42tCostAnnum = tmp;
				***/

				// LIQUIDS
				// Inhibitors
				tmp = _sharedInstance.makeupAnnum / (1000.0 * _sharedInstance.concFactor); // common factor
				_sharedInstance.h207Dosage = 100.0;
				_sharedInstance.h207Usage = _sharedInstance.h207Dosage * tmp;
				
				_sharedInstance.h2073Dosage = 300.0;
				_sharedInstance.h2073Usage = _sharedInstance.h2073Dosage * tmp;
				
				_sharedInstance.h280Dosage = 80.0;
				_sharedInstance.h280Usage = _sharedInstance.h280Dosage * tmp;
				
				_sharedInstance.h2805Dosage = 160.0;
				_sharedInstance.h2805Usage = _sharedInstance.h2805Dosage * tmp;
				
				_sharedInstance.h390Dosage = 50.0;
				_sharedInstance.h390Usage = _sharedInstance.h390Dosage * tmp;
				
				_sharedInstance.h3905Dosage = 100.0;
				_sharedInstance.h3905Usage = _sharedInstance.h3905Dosage * tmp;
				
				_sharedInstance.h391Dosage = 150.0;
				_sharedInstance.h391Usage = _sharedInstance.h391Dosage * tmp;
				
				_sharedInstance.h423Dosage = 100.0;
				_sharedInstance.h423Usage = _sharedInstance.h423Dosage * tmp;
				
				_sharedInstance.h425Dosage = 100.0;
				_sharedInstance.h425Usage = _sharedInstance.h425Dosage * tmp;
				
				_sharedInstance.h4255Dosage = 200.0;
				_sharedInstance.h4255Usage = _sharedInstance.h4255Dosage * tmp;
				
				_sharedInstance.h535Dosage = 100.0;
				_sharedInstance.h535Usage = _sharedInstance.h535Dosage * tmp;
				
				_sharedInstance.h874Dosage = 120.0;
				_sharedInstance.h874Usage = _sharedInstance.h874Dosage * tmp;
				
				// Biocides - non-oxidisers
				tmp = _sharedInstance.sysVolume * 52.0 / 1000.0 ; // common factor
				_sharedInstance.c31Dosage = 200.0;
				_sharedInstance.c31Usage = _sharedInstance.c31Dosage * tmp;
				
				_sharedInstance.c32Dosage = 100.0;
				_sharedInstance.c32Usage = _sharedInstance.c32Dosage * tmp;
				
				_sharedInstance.c44Dosage = 80.0;
				_sharedInstance.c44Usage = _sharedInstance.c44Dosage * tmp;
				
				_sharedInstance.c45Dosage = 125.0;
				_sharedInstance.c45Usage = _sharedInstance.c45Dosage * tmp;
				
				_sharedInstance.c48Dosage = 250.0;
				_sharedInstance.c48Usage = _sharedInstance.c48Dosage * tmp;
				
				_sharedInstance.c51Dosage = 125.0;
				_sharedInstance.c51Usage = _sharedInstance.c51Dosage * tmp;
				
				_sharedInstance.c52Dosage = 100.0;
				_sharedInstance.c52Usage = _sharedInstance.c52Dosage * tmp;
				
				_sharedInstance.c54Dosage = 100.0;
				_sharedInstance.c54Usage = _sharedInstance.c54Dosage * tmp;
				
				_sharedInstance.c58Dosage = 100.0;
				_sharedInstance.c58Usage = _sharedInstance.c58Dosage * tmp;

				
			} else {
				Log.i(TAG, "CoolingWaterModel: inputs not fully defined");
			}
		} catch (Exception e) {
			Log.e(TAG, "calculateAmounts() - exception: " + e.toString());
			e.printStackTrace();
		}

	}

}
