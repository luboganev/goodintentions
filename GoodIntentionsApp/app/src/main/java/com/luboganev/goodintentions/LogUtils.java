package com.luboganev.goodintentions;

import android.util.Log;

/**
 * Useful class which logs debug or error messages 
 * under a specific TAG when in debug mode
 */
public class LogUtils {
	public static final String TAG = "GoodIntentions";
	
	/**
	 * 	This variable defines the current mode of the application. 
	 * 	When it is being released, the debug mode will be set to false.
	 */
	public static final boolean DEBUG = true;
	
	/**
	 * 	Prints a debug message in the system log
	 * 
	 * @param sender
	 * 		A string with custom name or a class
	 * @param message
	 * 		The message to be logged
	 */
	public static void d(Object sender, String message) {
		if(DEBUG) Log.d(TAG,  getSenderString(sender) + ": " + message);
	}
	
	/**
	 * 	Prints an error message in the system log
	 * 
	 * @param sender
	 * 		A string with custom name or a class
	 * @param message
	 * 		The message to be logged
	 */
	public static void e(Object sender, String message) {
		if(DEBUG) Log.e(TAG,  getSenderString(sender) + ": " + message);
	}
	
	/**
	 * Gets the name of the Class of the input object
	 * 
	 * @param sender
	 * 		The input object 
	 * @return
	 */
	private static String getSenderString(Object sender) {
		if(sender instanceof String) {
			return (String)sender;
		}
		else return sender.getClass().getSimpleName();
	}
}


