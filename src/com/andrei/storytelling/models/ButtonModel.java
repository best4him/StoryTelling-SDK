package com.andrei.storytelling.models;

import org.json.JSONObject;

import com.andrei.storytelling.ScreenSettings;
import com.andrei.storytelling.util.Tools;

import android.widget.RelativeLayout;

public class ButtonModel {
	
	private String name;
	private int xPosition;
	private int yPosition;
	private Image image;
	private String text;
	private int action;
	private RelativeLayout.LayoutParams params;
	private static float scaleFactor;
	public RelativeLayout.LayoutParams getParams() {
		return params;
	}

	
	
	public void setName(String name) {
		this.name = name;
	}
	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}
	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setAction(int action) {
		this.action = action;
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
	public Image getImage() {
		return image;
	}
	public String getText() {
		return text;
	}
	public int getAction() {
		return action;
	}
	
	/**
	 * create an menu option. 
	 * @param menuAt
	 * @return  a menu option object or null if the json object is null
	 */
	public  static ButtonModel getButton(JSONObject menuAt) {
		
		if (menuAt == null) {
			return null;
		}
		
		ButtonModel mn = new ButtonModel();
		
		mn.name = menuAt.optString("name");
		mn.xPosition = menuAt.optInt("xPosition");
		mn.yPosition = menuAt.optInt("yPosition");
		mn.action = menuAt.optInt("action");
		mn.image = Image.getImage(menuAt.optJSONObject("image"));
		scaleFactor = ScreenSettings.getScaleFactor();
		mn.params = new RelativeLayout.LayoutParams(Tools.Scale(mn.image.getWidth(), scaleFactor), Tools.Scale(mn.image.getHeight(), scaleFactor));
		
		setPosition (mn.params, mn.xPosition, Direction.X );
		setPosition(mn.params, mn.yPosition, Direction.Y);
//		mn.params.leftMargin = Tools.Scale(mn.xPosition, scaleFactor);
//		mn.params.topMargin = Tools.Scale(mn.yPosition, scaleFactor);
		
		return mn;
	}
	
	public static void setPosition(RelativeLayout.LayoutParams params, int val, Direction dir) {
		
		switch(val) {
		
		case -1000:
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			break;
			
		case -2000:
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			break;
			
		case -3000:
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			break;
			
		case -4000:
			params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			break;
			
		default:
			val = Tools.Scale(val, scaleFactor);
			switch (dir) {
			case X:
				params.leftMargin = val;
				break;
			case Y:
				params.topMargin = val;
				break;
			}
			break;
		}
	}
	
	private enum Direction {
		X,Y;
	}
	
}
