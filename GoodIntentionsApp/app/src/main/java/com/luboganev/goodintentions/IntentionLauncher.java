package com.luboganev.goodintentions;

import android.app.Activity;
import android.content.Context;

import com.luboganev.goodintentions.data.Intention;

public class IntentionLauncher {
	public static final int START_FOR_RESULT_DEFAULT_CODE = 101;
	
	public static String launchIntention(Activity activity, Intention intention) {
		final Context launchContext = intention.contextType == Intention.INTENTION_LAUNCH_CONTEXT_ACTIVITY ?
				activity : activity.getApplicationContext();
		switch (intention.type) {
		case Intention.INTENTION_TYPE_ACTIVITY:
			try {
				launchContext.startActivity(intention.buildIntent());
			} catch (Exception e) {
				return e.getMessage();
			}
			break;
		case Intention.INTENTION_TYPE_ACTIVITY_FOR_RESULT:
			try {
				if(intention.contextType != Intention.INTENTION_LAUNCH_CONTEXT_ACTIVITY) {
					return "Start activity for result requires activity context!";
				}
				activity.startActivityForResult(intention.buildIntent(), START_FOR_RESULT_DEFAULT_CODE);
			} catch (Exception e) {
				return e.getMessage();
			}
			break;
		case Intention.INTENTION_TYPE_SERVICE:
			try {
				launchContext.sendBroadcast(intention.buildIntent());
			} catch (Exception e) {
				return e.getMessage();
			}
			break;
		case Intention.INTENTION_TYPE_BROADCAST:
			try {
				launchContext.startService(intention.buildIntent());
			} catch (Exception e) {
				return e.getMessage();
			}
			break;
		default:
			break;
		}
		return null;
	}
}
