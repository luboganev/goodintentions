package com.luboganev.goodintentions;

import android.content.Context;

import com.luboganev.goodintentions.data.Intention;

public class IntentionLauncher {
	public static String launchIntention(Context applicationContext, Intention intention) {
		switch (intention.type) {
		case Intention.INTENTION_TYPE_ACTIVITY:
			try {
				applicationContext.startActivity(intention.buildIntent());
			} catch (Exception e) {
				return e.getMessage();
			}
			break;
		case Intention.INTENTION_TYPE_SERVICE:
			try {
				applicationContext.sendBroadcast(intention.buildIntent());
			} catch (Exception e) {
				return e.getMessage();
			}
			break;
		case Intention.INTENTION_TYPE_BROADCAST:
			try {
				applicationContext.startService(intention.buildIntent());
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
