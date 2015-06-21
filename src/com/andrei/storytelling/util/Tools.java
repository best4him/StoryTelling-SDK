package com.andrei.storytelling.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import com.andrei.storytelling.language.LanguageController;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.RelativeLayout;

public class Tools {
	/**
	 * This method is used to get the Drawable file from resource directory of
	 * the application that's use this library
	 * 
	 * @param context
	 * @param resource_name
	 * @return the Drawable from resource file
	 */
	public static Drawable getDrawable(Context context, String resource_name) {
		
		try {
			
			//first try to find a drawable for a specific language
			
			
			String rsName = LanguageController.INSTANCE.getLanguages().get(0).getSt()+"_"+resource_name;
			
			int lgResId = context.getResources().getIdentifier(rsName, "drawable", context.getPackageName());
			
			Drawable drawable;
			if (lgResId != 0 && (drawable = context.getResources().getDrawable(lgResId)) != null ) {
				 
				return drawable;
			}else {
				
			
				//get default resource
				int resId = context.getResources().getIdentifier(resource_name, "drawable", context.getPackageName());
				if (resId != 0) {
					drawable = context.getResources().getDrawable(resId);
					return drawable;
				} 
			}
		} catch (Exception e) {
			Log.e("ll", "getDrawable - resource_name: " + resource_name);
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Get the full qualified name of the given resource name (add the package path);
	 * @param context
	 * @param resource_name
	 * @return
	 */
	public static String getString(Context context, String resource_name) {

		try {
			
			int resId = context.getResources().getIdentifier(resource_name, "string", context.getPackageName());

			if (resId != 0) {
				return context.getResources().getString(resId);
			}

		} catch (Exception e) {
			Log.e("ll", "getString - resource_name: " + resource_name);
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 
	 * Scale the integer with scale factor
	 * @param v the integer that will be scale
	 * @param scale the scale ration
	 * @return the new scaled integer
	 */
	public static int Scale(int v, float scale) {

		float s = (float) v * scale;
		int rs = 0;

		if (s - (int) s >= 0.5)
			rs = ((int) s) + 1;
		else
			rs = (int) s;

		return rs;
	}

	public static String loadJSONFromAsset(Context context, String fileName) {
		
		String json = null;
		
		try {

			InputStream is = context.getAssets().open(fileName);

			int size = is.available();

			byte[] buffer = new byte[size];

			is.read(buffer);

			is.close();

			json = new String(buffer, "UTF-8");

		} catch (IOException ex) {
			
			ex.printStackTrace();
			return null;
		
		}
		return json;

	}
	
	
	
	/**
	 * Return the identifier for a given resource from raw directory.
	 * 
	 * @param context
	 * @param resource_name
	 *            the resource name without extension
	 * @return
	 */
	public static int getSong(Context context, String resource_name) {

		try {
			
			String rsName = LanguageController.INSTANCE.getLanguages().get(0).getSt()+"_"+resource_name;	
			int lgResId = context.getResources().getIdentifier(rsName, "raw", context.getPackageName());
			
			if (lgResId != 0)
				return lgResId;
			
			int resId = context.getResources().getIdentifier(resource_name, "raw", context.getPackageName());
			return resId;

		} catch (Exception e) {

			Log.e("ll", "getString - resource_name: " + resource_name);
			e.printStackTrace();
		}

		return 0;
	}
	public static RelativeLayout.LayoutParams getLayoutParams(int width,int height,int xPosition, int yPosition, float scaleFactor) {
		RelativeLayout.LayoutParams boxParams = new RelativeLayout.LayoutParams(
				Tools.Scale(width, scaleFactor),
				Tools.Scale(height, scaleFactor));
		boxParams.leftMargin = Tools.Scale(xPosition,
				scaleFactor);
		boxParams.topMargin = Tools.Scale(yPosition,
				scaleFactor);
		
		return boxParams;
	}
}
