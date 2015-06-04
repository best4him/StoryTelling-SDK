package com.andrei.storytelling.customviews;

import android.R;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;
public class AlphaPageTransformer implements ViewPager.PageTransformer{

	@Override
	public void transformPage(View view, float position) {
		
		
        view.setTranslationX(view.getWidth() * -position);

     if(position <= -1.0F || position >= 1.0F) {
         view.setAlpha(0.0F);
     } else if( position == 0.0F ) {
         view.setAlpha(1.0F);
     } else { 
         // position is between -1.0F & 0.0F OR 0.0F & 1.0F
         view.setAlpha(1.0F - Math.abs(position));
     }
		
//		if (position < 0.5 && position >0) {
//			view.setAlpha(0.5f);
//		} else if (position > 0.5) {
//			view.setAlpha(0.0f);
//		}
//		if (position == 0) {
//			view.setAlpha(1);
//			
//			
////			view.setBackgroundColor(view.getResources().getColor(R.color.transparent));
//		} else {
////			view.setAlpha(0);
//			view.setBackgroundColor(Color.BLACK);
////			view.setBackgroundColor(view.getResources().getColor(R.color.darker_gray));
//		}
//		if (position < -0.5) {
//			view.setAlpha(0.0f);
//		} else if(position <0) {
//			view.setAlpha(0.5f);
//		}
	}
	

}
