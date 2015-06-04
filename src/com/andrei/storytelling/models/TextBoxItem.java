package com.andrei.storytelling.models;

import org.json.JSONObject;

public class TextBoxItem  {
	
	private int xPosition;
	private int yPosition;
	private int duration;
	private String text;
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
		tbm.text = textBoxModel.optString("text");
		
		return tbm;
	}
}
