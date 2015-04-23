package com.andrei.storytelling.models.animations;

import org.json.JSONObject;

import android.view.animation.AlphaAnimation;

public class MyAlphaAnimation extends AlphaAnimation {

	private MyAlphaAnimation(float fromAlpha, float toAlpha) {
		super(fromAlpha, toAlpha);
	}

	public static  AlphaAnimation getAlphaAnimation(JSONObject object) {
		
	float fromAlpha =1 ;
	float toAlpha = 1;

		JSONObject coordinates = object.optJSONObject("coordinates");

		if (coordinates != null) {
			
			float optTemp = (float) coordinates.optDouble("fromAlpha");

			if (optTemp != Double.NaN) {
				fromAlpha = optTemp;
			}
			
			 optTemp = (float) coordinates.optDouble("toAlpha");

			if (optTemp != Double.NaN) {
				toAlpha = optTemp;
			}
		}
		
		AlphaAnimation animation = new AlphaAnimation(fromAlpha, toAlpha);
		CommonAnimation.setCommonFields(animation, object);
		return animation;
	}
}
