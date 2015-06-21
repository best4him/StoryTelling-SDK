package com.andrei.storytelling.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.andrei.storytelling.music.FXPlayer;
import com.andrei.storytelling.util.Tools;

public class Page {

	private final String background;
	private final List<Sprite> sprites;
	private final TextBoxModel textBoxes;
	
	public TextBoxModel getTextBoxes() {
		return textBoxes;
	}
	
	private final String textSound;
	private final int duration;
	
	public int getDuration() {
		return duration;
	}

	public static int  contor = 0;

	public static class Builder {
		
		// required parameter
		private final String background;
		
		// optional parameters
		private List<Sprite> sprites = null;
		private String textSound = null;
		private TextBoxModel mTextBoxes;
		private int mDuration;
		public Builder (String background) {
			this.background = background;	
		}
		
		public Builder sprites (List<Sprite> sprites) {
			this.sprites = sprites;
			return this;
		}
		
		public Builder textSound (String textSound) {
			this.textSound = textSound;
			return this;
		}
		public Builder duration (int duration) {
			this.mDuration = duration;
			return this;
		}
		public Builder textBox (TextBoxModel textBoxes) {
			this.mTextBoxes = textBoxes;
			return this;
		}
		
		public Page build () {
			return new Page(this);
		}
		
	}
	
	public Page (Builder builder ) {
		this.background = builder.background;
		this.sprites = builder.sprites;
		this.textSound = builder.textSound;
		this.textBoxes = builder.mTextBoxes;
		this.duration = builder.mDuration;
	}
	
	public String getBackground() {
		return background;
	}

	public List<Sprite> getSprites() {
		return sprites;
	}
	
	public String getTextSound() {
		return textSound;
	}
	public static Page getPage(JSONObject object ) {
		
		if (object == null) return null;
		
		Page.Builder builder = new Page.Builder(object.optString("background"));

		builder = builder.textSound(object.optString("sound"));
		builder = builder.duration(object.optInt("duration"));
		JSONArray spritesJson = object.optJSONArray("sprites");
		
		
		// text Box json
		JSONObject textBoxesJson = object.optJSONObject("text_box");
		
		if (spritesJson != null) {
			List<Sprite> sprites = new ArrayList<>();
			for (int i = 0, n = spritesJson.length(); i < n; i++) {
				sprites.add(Sprite.getSprite(spritesJson.optJSONObject(i)));
			}
			builder = builder.sprites(sprites);
//			page.sprites = sprites;
		} else {
			List<Sprite> sprites = new ArrayList<>();
			builder = builder.sprites(sprites);		
		}
		
		if (textBoxesJson != null) {	
			builder = builder.textBox(TextBoxModel.getTextBoxModel(textBoxesJson));	
					
		} else {
//			page.textBoxes =TextBoxModel.noBox();
		}
		return builder.build();
	}
	public FXPlayer getFxPlayer (Context context) {
		FXPlayer player = new FXPlayer(context);
		int contor = 1;
		for (Sprite sprite :  this.getSprites()) {
			if (sprite.getSounds() != null) {
				sprite.setSoundId(contor);
				player.addSound(contor++, Tools.getSong(context, sprite.getSounds().getName()));
			}
	}
		return player;
	}
}
