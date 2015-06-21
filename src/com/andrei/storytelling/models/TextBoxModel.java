package com.andrei.storytelling.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.widget.RelativeLayout;

import com.andrei.storytelling.util.Tools;

/**
 * This Class contains common attributes for all textboxes. If a
 * parameter is missing, the attribute will have the default value
 * 
 * @author AncientMachine
 * 
 */
public class TextBoxModel {


	private final String backgroundColor;
	private final String textColor;
	private final int xPosition;
	private final int yPosition;
	private final int width;
	private final int height;
	private final int radius;
	private final int opacity;
	private final int textSize;
	private final int animationDuration;
	private final String font;
	
	public String getExternalFont() {
		return externalFont;
	}

	private final String externalFont;
	private final List<TextBoxItem> tbItems;

	public String getBackgroundColor() {
		return backgroundColor;
	}
	
	/**
	 * This method combine opacity with background color, so the color has the following format #rrggbb
	 * The color with the alpha channel has the following format #aarrggbb
	 * @return the background color in the following format #aarrggbb
	 */
	public String getBackgroundColorWithOpacity() {
		int alpha = (int)(((float)(100 - opacity)/100)*255);
		String alphaHex = Integer.toHexString(alpha);
		return "#" + alphaHex + backgroundColor.substring(1);
		
	}
	
	public RelativeLayout.LayoutParams getParams(float scaleFactor) {

		
		return Tools.getLayoutParams(width, height, xPosition, yPosition, scaleFactor);
	}
	public String getTextColor() {
		
		return textColor;
	}

	public int getxPosition() {
		return xPosition;
	}

	public int getyPosition() {
		return yPosition;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getRadius() {
		return radius;
	}

	public int getOpacity() {
		return opacity;
	}

	public int getTextSize() {
		return textSize;
	}

	public String getFont() {
		return font;
	}

	public List<TextBoxItem> getTbItems() {
		return tbItems;
	}
	public int getAnimationDuration() {
		return animationDuration;
	}

	public static TextBoxModel noBox() {
		TextBoxModel noBox = new TextBoxModel.Builder().width(0).height(0).build();

		return noBox;
	}

	private TextBoxModel(Builder builder) {

		backgroundColor = builder.mbackgroundColor;
		textColor = builder.mtextColor;
		xPosition = builder.xPosition;
		yPosition = builder.yPosition;
		width = builder.width;
		height = builder.height;
		radius = builder.radius;
		opacity = builder.opacity;
		textSize = builder.textSize;
		font = builder.mfont;
		externalFont = builder.mExternalFont;
		tbItems = builder.mtbItems;
		animationDuration = builder.mAnimationDuration;
	}

	public static class Builder {

		private String mbackgroundColor = "#76E8DE";
		private String mtextColor = "#000000";
		private int xPosition = 0;
		private int yPosition = 0;
		private int width = LayoutParams.WRAP_CONTENT; // -2
		private int height = LayoutParams.WRAP_CONTENT;
		private int radius = 0;
		private int opacity = 0;
		private int textSize = 0;
		private int mAnimationDuration = 300;
		private String mfont = null;
		private String mExternalFont = null;
		private List<TextBoxItem> mtbItems = new ArrayList<>();

		public Builder() {

		}

		/**
		 * if backgroundColor is an empty string, the method return the default value
		 * @param backgroundColor represent a string that contains the background color
		 * @return the builder object
		 * @throws NullPointerException if the backgroundColor is null 
		 */ 
		public Builder backGroundColor(String backgroundColor) {
			if (backgroundColor == null) throw new NullPointerException("The backgroundColor should not be null");
			if (!backgroundColor.equals("")) {
				mbackgroundColor = backgroundColor;
			}

			return this;
		}

		/**
		 * builder method
		 * @param items
		 * @return the builder object
		 * @throws NullPointerException if the items is null 
		 */
		public Builder tbItems(List<TextBoxItem> items) {
			if (items == null) throw new NullPointerException("The backgroundColor should not be null");
			
			mtbItems = items;
			return this;
		}
		/**
		 * if textColor is an empty string, the method return the default value
		 * @param val represent a string that contains the text color
		 * @return the builder object
		 * @throws NullPointerException if the bgColor is null 
		 */ 
		public Builder textColor(String textColor) {
			
			if (textColor == null) throw new NullPointerException("The textColor should not be null");
			if (!textColor.equals("")) {
				mtextColor = textColor;
			}
			return this;
		}

		public Builder xPosition(int val) {
			xPosition = val;
			return this;
		}

		public Builder yPosition(int val) {
			yPosition = val;
			return this;
		}

		public Builder width(int val) {
			if (val != 0) {
				width = val;
			}

			return this;
		}

		public Builder height(int val) {
			if (val != 0) {
				height = val;
			}

			return this;
		}
		/**
		 * This method
		 * @param animationDuration represent the duration of textview animation (how fast the textview
		 * show be made invisible or visible)
		 * @return the builder object
		 * @throws IllegalArgumentException if the argument is negative
		 */
		public Builder animationDuration(int animationDuration) {
			
			if (animationDuration < 0) throw new IllegalArgumentException("animationDuration must not be negativ");
			if (animationDuration != 0) {
				mAnimationDuration = animationDuration;
			}

			return this;
		}
		public Builder radius(int val) {
			radius = val;
			return this;
		}

		public Builder opacity(int val) {
			opacity = val;
			return this;
		}

		public Builder textSize(int val) {
			textSize = val;
			return this;
		}
		
		/**
		 * if font is an empty string, the method return the default value
		 * @param font represent a string that contains the text font
		 * @return the builder object
		 * @throws NullPointerException if the font is null 
		 */ 
		public Builder font(String font) {
			
			if (font == null) throw new NullPointerException("The font should not be null");
			if (!font.equals("")) {
				mfont = font;
			}
			
			return this;
		}
		
		/**
		 * if externalFont is an empty string, the method return the default value
		 * @param externalFont represent a string that contains the external Font name 
		 * @return the builder object
		 * @throws NullPointerException if the externalFont is null 
		 */ 
		public Builder externalFont(String externalFont) {
			
			if (externalFont == null) throw new NullPointerException("The externalFont should not be null");
			if (!externalFont.equals("")) {
				mExternalFont = externalFont;
			}
			
			return this;
		}
		public TextBoxModel build() {
			return new TextBoxModel(this);
		}

	}

	public static TextBoxModel getTextBoxModel(JSONObject tbJsonObject) {

		TextBoxModel.Builder builder = new TextBoxModel.Builder();

		JSONObject tbAttribJson = tbJsonObject.optJSONObject("text_box_attributs");
		JSONArray tbArrayJson = tbJsonObject.optJSONArray("text_box_values");

		List<TextBoxItem> tbItems = new ArrayList<>();
	
		builder = builder.xPosition(tbAttribJson.optInt("xPosition"));
		builder = builder.yPosition(tbAttribJson.optInt("yPosition"));
		builder = builder.radius(tbAttribJson.optInt("radius"));
		builder = builder.opacity(tbAttribJson.optInt("opacity"));
		builder = builder.width(tbAttribJson.optInt("width"));
		builder = builder.height(tbAttribJson.optInt("height"));
		builder = builder.textSize(tbAttribJson.optInt("text_size"));
		builder = builder.animationDuration(tbAttribJson.optInt("default_animation_duration"));
		builder = builder.backGroundColor(tbAttribJson.optString("background_color"));
		builder = builder.textColor(tbAttribJson.optString("text_color"));
		builder = builder.font(tbAttribJson.optString("font"));
		builder = builder.externalFont(tbAttribJson.optString("external_font"));
//		default_animation_duration
		
//		add textBox sequence
		List<TextBoxItem> boxes = new ArrayList<TextBoxItem>();
		for (int i = 0, n = tbArrayJson.length(); i < n; i++) {
			boxes.add(TextBoxItem.getTextBoxItem(tbArrayJson.optJSONObject(i)));
		}
		
		builder = builder.tbItems(boxes);

		return builder.build();
	}
}
