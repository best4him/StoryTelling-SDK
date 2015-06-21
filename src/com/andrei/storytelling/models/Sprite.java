package com.andrei.storytelling.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.drawable.AnimationDrawable;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.andrei.storytelling.models.animations.FrameAnimation;
import com.andrei.storytelling.models.animations.MyAlphaAnimation;
import com.andrei.storytelling.models.animations.MyRotateAnimation;
import com.andrei.storytelling.models.animations.MyScaleAnimation;
import com.andrei.storytelling.models.animations.MyTranslateAnimation;
import com.andrei.storytelling.util.Tools;


public class Sprite {

	private Image image;
	private String name;
	private int xPosition;
	private int yPosition;
	private SoundModel sound;
	private int soundId;
	private boolean draggable;
	private int trigerOffset;
	private FrameAnimation dAnimation;
	
	public int getTrigerOffset() {
		return trigerOffset;
	}

	public boolean isDraggable() {
		return draggable;
	}

	public FrameAnimation getdAnimation() {
		return dAnimation;
	}
	
	public RelativeLayout.LayoutParams getParams(float scaleFactor) {
		return Tools.getLayoutParams(image.getWidth(), image.getWidth(), xPosition, yPosition, scaleFactor);
	}
	public int getSoundId() {
		return soundId;
	}

	public void setSoundId(int soundId) {
		this.soundId = soundId;
	}

	private ArrayList<Animation> animations;



	public SoundModel getSound() {
		return sound;
	}

	public ArrayList<Animation> getAnimations() {
		return animations;
	}

	public Image getImage() {
		return image;
	}

	public String getName() {
		return name;
	}

	public int getxPosition() {
		return xPosition;
	}

	public int getyPosition() {
		return yPosition;
	}

	public SoundModel getSounds() {
		return sound;
	}

	

	private  Sprite() {}
	
	public static Sprite getSprite (JSONObject object) {
		
		if (object == null) {
			return null;
		}
		
		Sprite sprite = new Sprite();
		sprite.image = Image.getImage(object.optJSONObject("image"));
		sprite.xPosition = object.optInt("xPosition");
		sprite.yPosition = object.optInt("yPosition");
		sprite.name = object.optString("name");
		sprite.draggable = object.optBoolean("draggable");
		sprite.trigerOffset = object.optInt("triger_offset");
		sprite.animations = Sprite.getSpriteAnimation(object.optJSONArray("animations"));
		sprite.sound = SoundModel.getSoundModel(object.optJSONObject("sound"));
		sprite.dAnimation = FrameAnimation.getFrameAnimation(object.optJSONObject("frame_animation"));
		
		return sprite;
	}
	

	
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
	
	private  static ArrayList<Animation> getSpriteAnimation (JSONArray jsonArray) {
		
		ArrayList<Animation> animations = new ArrayList<>();
		if (jsonArray == null) return null;

		
		for (int i = 0, n = jsonArray.length(); i < n; i++) {
			Animation animation = getAnimation(jsonArray.optJSONObject(i));
			if (animation != null)
				animations.add(animation);
		}
		
		return animations;
	}
	
}
