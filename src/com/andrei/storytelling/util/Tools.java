package com.andrei.storytelling.util;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class Tools {
    /**
     * This method is used to get the Drawable file from resource directory of the application that's use this library
     * @param context
     * @param resource_name
     * @return the Drawable from resource file
     */
    public static Drawable getDrawable(Context context, String resource_name){
    	
        try{
            int resId = context.getResources().getIdentifier(resource_name, "drawable", context.getPackageName());
            if(resId != 0){
                return context.getResources().getDrawable(resId);
            }
        }catch(Exception e){
            Log.e("ll","getDrawable - resource_name: "+resource_name);
            e.printStackTrace();
        }

        return null;
    }
    
    public static String getString(Context context, String resource_name){
    	
        try{
            int resId = context.getResources().getIdentifier(resource_name, "string", context.getPackageName());
            if(resId != 0){
                return context.getResources().getString(resId);
            }
        }catch(Exception e){
            Log.e("ll","getString - resource_name: "+resource_name);
            e.printStackTrace();
        }

        return null;
    }
	public static int Scale(int v, float scale) {
		
		float s = (float)v * scale;
		int rs = 0;
		
		if (s - (int)s >= 0.5)
			rs= ((int)s)+1;
		else rs= (int)s;
		
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
}
