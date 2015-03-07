package com.andrei.storytelling.customviews;

import android.content.Context;
import android.widget.ImageView;

public class CustomImageView extends ImageView {
	
	private int myWidth, myHeight;
	
	public CustomImageView(Context context, int myWidth, int myHeight) {
		
		super(context);
		this.myWidth = myWidth;
		this.myHeight = myHeight;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		this.setMeasuredDimension(myWidth, myHeight);
	}

}
