package com.andrei.storytelling.models.animations;

import org.json.JSONObject;

import android.view.animation.Animation;

/**
 * @author Andrei.Cristinescu
 * 
 */
public class CommonAnimation {
	
	public static void setCommonFields(Animation animation, JSONObject object) {

		int durationMillis;
		int startOffset;
		int zOrder;
		int repeatCount;
		int repeatMode;

		boolean fillAfter = false;
		boolean fillBefore = true;

		durationMillis = object.optInt("duration");
		startOffset = object.optInt("start_time");
		zOrder = object.optInt("z_order");
		repeatCount = object.optInt("repeat");
		repeatMode = object.optInt("repeat_mode");
		fillAfter = object.optBoolean("fill_after");
		fillBefore = object.optBoolean("fill_before");

		if (durationMillis != 0) {
			animation.setDuration(durationMillis);
		}

		if (startOffset != 0) {
			animation.setStartOffset(startOffset);
		}

		if (zOrder != 0) {
			animation.setZAdjustment(zOrder);
		}

		if (repeatCount != 0) {
			animation.setRepeatCount(repeatCount);
		}

		if (repeatMode != 0) {
			animation.setRepeatMode(repeatMode);
		}

		if (fillAfter) {
			animation.setFillAfter(fillAfter);
		}

		if (!fillBefore) {
			animation.setFillEnabled(true);
			animation.setFillBefore(fillBefore);
		}
	}
}
