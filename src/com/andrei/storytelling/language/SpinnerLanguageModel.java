package com.andrei.storytelling.language;

import android.graphics.drawable.Drawable;

public class SpinnerLanguageModel {
	
	private String language;
	private Drawable flag;
	
	public SpinnerLanguageModel(String language, Drawable flag) {
		super();
		this.language = language;
		this.flag = flag;
	}

	public String getLanguage() {
		return language;
	}

	public Drawable getFlag() {
		return flag;
	}
		
}
