package com.andrei.storytelling.models.animations;

import org.json.JSONObject;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class MyTranslateAnimation extends TranslateAnimation  {

	private MyTranslateAnimation(int fromXType, float fromXValue, int toXType,
			float toXValue, int fromYType, float fromYValue, int toYType,
			float toYValue) {
		super(fromXType, fromXValue, toXType, toXValue, fromYType, fromYValue,
				toYType, toYValue);

	}

	public static TranslateAnimation getTranslateAnimation(JSONObject object) {
		
		int fromXType = Animation.ABSOLUTE;
		int toXType = Animation.ABSOLUTE;

		int fromYType = Animation.ABSOLUTE;
		int toYType = Animation.ABSOLUTE;

		float fromXValue = 0.0f;
		float toXValue = 0.0f;

		float fromYValue = 0.0f;
		float toYValue = 0.0f;
		
		if (object == null) {
			return null;
		}

		JSONObject coordinates = object.optJSONObject("coordinates");

		if (coordinates != null) {
			
			float optTemp = (float) coordinates.optDouble("fromXValue");
			if (optTemp != Double.NaN) {
				fromXValue = optTemp;
			}
			optTemp = (float) coordinates.optDouble("toXValue");
			if (optTemp != Double.NaN) {
				toXValue = optTemp;
			}
			optTemp = (float) coordinates.optDouble("fromYValue");
			if (optTemp != Double.NaN) {
				fromYValue = optTemp;
			}
			optTemp = (float) coordinates.optDouble("toYValue");
			if (optTemp != Double.NaN) {
				toYValue = optTemp;
			}
			fromXType = coordinates.optInt("fromXType");
			toXType = coordinates.optInt("toXType");
			fromYType = coordinates.optInt("fromYType");
			toYType = coordinates.optInt("toYType");
		}
		
		//create object
		TranslateAnimation translateAnimation = new TranslateAnimation(
				fromXType, fromXValue, toXType, toXValue, fromYType,
				fromYValue, toYType, toYValue);

		CommonAnimation.setCommonFields(translateAnimation, object);
		
		return translateAnimation;
	}
}