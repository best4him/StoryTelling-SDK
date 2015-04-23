package com.andrei.storytelling.models.animations;

import org.json.JSONObject;

import android.view.animation.ScaleAnimation;

public class MyScaleAnimation  extends ScaleAnimation{

	private MyScaleAnimation(float fromX, float toX, float fromY, float toY) {
		super(fromX, toX, fromY, toY);
		
	}
	
	public static  ScaleAnimation getScaleAnimation(JSONObject object) {
		
		float fromX = 1;
		float toX =  1;
		float fromY = 0;
		float toY = 0;
		
		int pivotXType = 0;
		float pivotXValue = 0.0f;
		int pivotYType = 0;
		float pivotYValue = 0.0f;

		JSONObject coordinates = object.optJSONObject("coordinates");

		if (coordinates != null) {
			
			float optTemp = (float) coordinates.optDouble("fromX");

			if (optTemp != Double.NaN) {
				fromX = optTemp;
			}
			
			 optTemp = (float) coordinates.optDouble("toX");

			if (optTemp != Double.NaN) {
				toX = optTemp;
			}
			
			 optTemp = (float) coordinates.optDouble("fromY");

			if (optTemp != Double.NaN) {
				fromY = optTemp;
			}
			
			 optTemp = (float) coordinates.optDouble("toY");

			if (optTemp != Double.NaN) {
				toY = optTemp;
			}
			
			 optTemp = (float) coordinates.optDouble("pivotXValue");

			if (optTemp != Double.NaN) {
				pivotXValue = optTemp;
			}
			
			 optTemp = (float) coordinates.optDouble("pivotYValue");

			if (optTemp != Double.NaN) {
				pivotYValue = optTemp;
			}
		}
		
		ScaleAnimation animation = new ScaleAnimation(fromX, toX, fromY, toY, pivotXType, pivotXValue, pivotYType, pivotYValue);
		CommonAnimation.setCommonFields(animation, object);
		return animation;
	}
}
