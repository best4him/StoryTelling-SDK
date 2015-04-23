package com.andrei.storytelling.models.animations;

import org.json.JSONObject;

import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

public class MyRotateAnimation extends RotateAnimation {

	private MyRotateAnimation(float fromDegrees, float toDegrees) {
		super(fromDegrees, toDegrees);

	}

	public static RotateAnimation getRotateAnimation(JSONObject object) {

		int pivotXType = Animation.ABSOLUTE;
		int pivotYType = Animation.ABSOLUTE;

		float fromDegrees = 0.0f;
		float toDegrees = 0.0f;

		float pivotXValue = 0.0f;
		float pivotYValue = 0.0f;



		if (object == null) {
			return null;
		}
		JSONObject coordinates = object.optJSONObject("coordinates");

		if (coordinates != null) {

			float optTemp = (float) coordinates.optDouble("fromDegrees");

			if (optTemp != Double.NaN) {
				fromDegrees = optTemp;
			}
			
			optTemp = (float) coordinates.optDouble("toDegrees");

			if (optTemp != Double.NaN) {
				toDegrees = optTemp;
			}
			
			optTemp = (float) coordinates.optDouble("pivotXValue");

			if (optTemp != Double.NaN) {
				pivotXValue = optTemp;
			}
			
			optTemp = (float) coordinates.optDouble("pivotYValue");

			if (optTemp != Double.NaN) {
				pivotYValue = optTemp;
			}
			
			pivotXType = coordinates.optInt("pivotXType");
			pivotYType = coordinates.optInt("pivotYType");
			System.out.println("ppp: " + pivotXType + "--" + pivotYType);
			
			
		}

		RotateAnimation animation = new RotateAnimation(fromDegrees, toDegrees, pivotXType, pivotXValue, pivotYType, pivotYValue);
		CommonAnimation.setCommonFields(animation, object);
		
		return animation;
	}
}
