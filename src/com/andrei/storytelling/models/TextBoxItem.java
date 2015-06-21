package com.andrei.storytelling.models;

import org.json.JSONObject;

import com.andrei.storytelling.language.LanguageController;

public class TextBoxItem  {
	
	private int xPosition;
	private int yPosition;
	private int duration;
	private String text;
	private String id;
	
	public static TextBoxItem noBox() {
		return null;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getxPosition() {
		return xPosition;
	}
	public int getyPosition() {
		return yPosition;
	}
	public int getDuration() {
		return duration;
	}
	

	public String getId() {
		return id;
	}
	/**
	 * This method returns a textBoxModel object from a Json Object 
	 * @param textBoxModel a json object that contains the textBox attributes like position or duration
	 * @return
	 */
	public static TextBoxItem getTextBoxItem(JSONObject textBoxModel) {
		
		TextBoxItem tbm = new TextBoxItem();
		tbm.xPosition = textBoxModel.optInt("xPosition");
		tbm.yPosition = textBoxModel.optInt("yPosition");
		tbm.duration = textBoxModel.optInt("duration");
		tbm.id = textBoxModel.optString("id");
		
		//get the first language, the default languages


		tbm.text = LanguageController.INSTANCE.getLanguages().get(0).getValues().get(tbm.id);
		return tbm;
	}
}
