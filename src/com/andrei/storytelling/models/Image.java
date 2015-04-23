package com.andrei.storytelling.models;

import org.json.JSONObject;

import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ImageView.ScaleType;


public class Image {

	private String name;
	private String path;
	private String URL;
	private int width;
	private int height;
	private ScaleType scaleType;
	RelativeLayout.LayoutParams image2Params;
	
	public String getName() {
		return name;
	}


	public String getPath() {
		return path;
	}


	public String getURL() {
		return URL;
	}


	public int getWidth() {
		return width;
	}


	public int getHeight() {
		return height;
	}


	public ScaleType getScaleType() {
		return scaleType;
	}


	public Image (String path) {
		this.path = path;
	}
	public static class Builder {
		
		// required parameter
		private final String path;
		
		private String name = "No name";
		private String URL = "";
		private int width = 0;
		private int height = 0;
		private ScaleType scaleType = null;
		
		public Builder(String path) {
			this.path = path;
		}

		public Builder setName(String name) {
			this.name = name;
			return this;
		}

		public Builder setURL(String uRL) {
			URL = uRL;
			return this;
		}

		public Builder setWidth(int width) {
			this.width = width;
			return this;
		}

		public Builder setHeight(int height) {
			this.height = height;
			return this;
		}

		public Builder setScaleType(ScaleType type) {
			this.scaleType = type;
			return this;
		}
		
		public Image build() {
			return new Image(this);
		}
		
	}
	
	private Image(Builder builder) {
		this.name = builder.name;
		this.URL = builder.URL;
		this.width = builder.width;
		this.height = builder.height;
		this.scaleType = builder.scaleType;
	}
	
	
	/**Create new image model from JSON object
	 * @param imageObject
	 * @return the image object or null if imageObject is null
	 */
	public static Image getImage(JSONObject imageObject) {
		
		if (imageObject == null) return null;
		Image image = new  Image(imageObject.optString("name"));
		image.width = getDim(imageObject.optString("width"));
		image.height = getDim(imageObject.optString("height"));
		image.scaleType = getScaleType(imageObject.optString("scale_type"));
		
		return image;
	}
	
	public static int getDim (String dim) {
		
		if (dim == null) return 0;
		int iDim = 0;
		
		switch (dim) {
		
		case "wrap_content":
			iDim =ViewGroup.LayoutParams.WRAP_CONTENT;
			break;
			
		case "match_parent":
			iDim = ViewGroup.LayoutParams.MATCH_PARENT;
			break;

		default:
			iDim = Integer.parseInt(dim);
			break;
		}
		return iDim;
	}
	
	public static ScaleType getScaleType (String scaleType) {
		
		switch (scaleType.toUpperCase()) {
		case "NONE":
			return null;
		case "MATRIX":
			return ScaleType.MATRIX;
		case "FIT_XY":
			return ScaleType.FIT_XY;
		case "FIT_START":
			return ScaleType.FIT_START;
		case "FIT_CENTER":
			return ScaleType.FIT_CENTER;
		case "FIT_END":
			return ScaleType.FIT_END;
		case "CENTER":
			return ScaleType.CENTER;
		case "CENTER_CROP":
			return ScaleType.CENTER_CROP;
		case "CENTER_INSIDE":
			return ScaleType.CENTER_INSIDE;
		default:
			return null;
		}
	}
	
}
