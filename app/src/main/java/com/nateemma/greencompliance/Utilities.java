package com.nateemma.greencompliance;

import java.io.File;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

/**
 * Collection of utility functions
 * @author pprice
 *
 */
public class Utilities {

	
	/**
	 * round Double to 1 decimal place and return in String form
	 * @param value the Double value to be rounded
	 * @return String version of rounded number
	 */
	public static String round1(Double value) {
		Double tmp = 0.0;
		if (value==null) value = 0.0;
		tmp = ((int)((value+0.05) * 10)) / 10.0;	
		//Log.v("round1: ", tmp.toString());
		return tmp.toString();
	}


	/**
	 * Create a file for attaching to email, with the specified contents
	 * @param filename the name of the file (no extension)
	 * @param contents HTML formatted contents
	 * @return the URI of the file
	 */
	public static Uri createAttachment(String filename, String contents) {
		Uri attachUri=null;

		FileCache.init();
		String attachPath = FileCache.getFilePath(filename);
		FileCache.writeTextFile(attachPath, contents);
		attachUri = Uri.fromFile(new File(attachPath));
		
		return attachUri;
	}



	public static String getStringFromId(Context context, int id) {
		String str = context.getResources().getString(id);
		if (str == null) {
			Log.w("getStringFromId()", "ID not found:"+id);
			str = "";
		}
		return str;
	}



}
