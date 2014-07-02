package com.luboganev.goodintentions;

import android.content.Context;
import android.util.TypedValue;

public class UIUtils {
	/**
	 * Returns the physical pixels that 
	 * corresponds to the input DIPs
	 * 
	 * @param context
	 * 		Use application context and not some activity's context to prevent memory leaks
	 */
	public static int getPixelsFromDips(Context context, int dips) {
		float result = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dips, context.getResources().getDisplayMetrics());
		return (int)result;
	}
}
