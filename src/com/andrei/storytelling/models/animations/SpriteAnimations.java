package com.andrei.storytelling.models.animations;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.view.animation.Animation;

public class SpriteAnimations {
	private ArrayList<Animation> animations = new ArrayList<>();
	
	private SpriteAnimations () {};
	
	private static Animation getAnimation(JSONObject object) {
		
		if (object == null) return null;
		
		String type = object.optString("type");
		
		switch (type) {
		case "translate_animation":
			return MyTranslateAnimation.getTranslateAnimation(object);
			
		case "rotate_animation":
			return MyRotateAnimation.getRotateAnimation(object);
			
		case "scale_animation":
			return MyScaleAnimation.getScaleAnimation(object);
			
		case "alpha_animation":
			return MyAlphaAnimation.getAlphaAnimation(object);
		default:
			return null;
		}
	}
	
	public static SpriteAnimations getSpriteAnimation (JSONArray jsonArray) {
		if (jsonArray == null) return null;
		SpriteAnimations spriteAnimation = new SpriteAnimations();
		
		for (int i = 0, n = jsonArray.length(); i < n; i++) {
			Animation animation = getAnimation(jsonArray.optJSONObject(i));
			if (animation != null)
				spriteAnimation.animations.add(animation);
		}
		
		return spriteAnimation;
	}
	
	public enum AnimationType {
		SCALE_ANIMATION,
		ROTATE_ANIMATION,
		TRANSLATE_ANIMATION,
		ALPHA_ANIMATION
	}
	
		
}
