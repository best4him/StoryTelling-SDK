package com.andrei.storytelling;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.RelativeLayout;

public class ScreenSettings {
	
	private static int windowWidth, windowHeight;
	private static int scaledWidth, scaledHeight;
	private static int paddingX, paddingY;
	private static float scaleFactor;
	
	
	public static int getWindowWidth() {
		return windowWidth;
	}


	public static int getWindowHeight() {
		return windowHeight;
	}


	public static int getScaledWidth() {
		return scaledWidth;
	}


	public static int getScaledHeight() {
		return scaledHeight;
	}


	public static int getPaddingX() {
		return paddingX;
	}


	public static int getPaddingY() {
		return paddingY;
	}


	public static float getScaleFactor() {
		return scaleFactor;
	}


	public static void setParameters(Context context, double width, double height) {

		
		
		// get display metrics object
		DisplayMetrics dm = new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);

		// get absolute
//		 windowWidth = Scale(dm.widthPixels,1/dpScaleFactor);
//		 windowHeight = Scale(dm.heightPixels,1/dpScaleFactor);
		//
		windowWidth = dm.widthPixels;
		windowHeight = dm.heightPixels;
		scaleFactor = (float) ((float) windowHeight / height);
	
		// compute our frame
		scaledWidth = (int) (width * scaleFactor);
		scaledHeight = windowHeight;

		// compute padding for our frame inside the total screen size
		paddingY = 0;
		paddingX = (windowWidth - scaledWidth) / 2;
	}
	
	public static RelativeLayout.LayoutParams getFrameParams () {
		
		RelativeLayout.LayoutParams frameParams = new RelativeLayout.LayoutParams(scaledWidth, scaledHeight);
		frameParams.leftMargin = paddingX;
		frameParams.topMargin = paddingY;
		
		return frameParams;
	}
}
