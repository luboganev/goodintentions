package com.luboganev.goodintentions;

import android.content.Context;

import com.luboganev.goodintentions.data.Intention;

public class IntentionLauncher {
	public static void launchIntention(Context applicationContext, Intention intention) {
		switch (intention.type) {
		case Intention.INTENTION_TYPE_ACTIVITY:
			applicationContext.startActivity(intention.buildIntent());
			break;
		case Intention.INTENTION_TYPE_BROADCAST:
			applicationContext.startService(intention.buildIntent());
			break;
		case Intention.INTENTION_TYPE_SERVICE:
			applicationContext.sendBroadcast(intention.buildIntent());
			break;
		case Intention.INTENTION_TYPE_NONE:
		default:
			break;
		}
	}
}
