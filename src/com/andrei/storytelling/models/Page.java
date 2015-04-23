package com.andrei.storytelling.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.andrei.storytelling.music.FXPlayer;
import com.andrei.storytelling.util.Tools;

public class Page {

	private String background;
	private List<Sprite> sprites;
	private String textSound;
	public static int  contor = 0;
	private Page( ) {}
	public static class Builder {
		
		// required parameter
		private final String background;
		
		// optional parameters
		private List<Sprite> sprites = null;
		private String textSound = null;
		
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
		
		public Page build () {
			return new Page(this);
		}
		
	}
	
	public Page (Builder builder ) {
		this.background = builder.background;
		this.sprites = builder.sprites;
		this.textSound = builder.textSound;
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
		
		Page page = new Page();
		page.background = object.optString("background");
		page.textSound = object.optString("sound");
		JSONArray spritesJson = object.optJSONArray("sprites");
		
		if (spritesJson != null) {
			List<Sprite> sprites = new ArrayList<>();
			for (int i = 0, n = spritesJson.length(); i < n; i++) {
				sprites.add(Sprite.getSprite(spritesJson.optJSONObject(i)));
			}
			page.sprites = sprites;
		} else {
			page.sprites = new ArrayList<>();
		}
		return page;
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
