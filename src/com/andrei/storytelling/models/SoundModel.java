package com.andrei.storytelling.models;

import org.json.JSONObject;

public class SoundModel {
	
	private String name;
	private int loop = 0;

	public String getName() {
		return name;
	}
	public int getLoop() {
		return loop;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	private SoundModel (){};
	
	public static SoundModel getSoundModel(JSONObject object) {
		
		if (object == null) return null;
		
		SoundModel model = new SoundModel();
		model.name = object.optString("name");
		model.loop = Page.contor;

		
		return model;
	}
}
