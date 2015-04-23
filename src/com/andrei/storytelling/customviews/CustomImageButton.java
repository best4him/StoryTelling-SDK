package com.andrei.storytelling.customviews;
import android.content.Context;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.widget.ImageView;
public class CustomImageButton extends ImageView {
private int myWidth, myHeight;
	
	public CustomImageButton(Context context, int myWidth, int myHeight) {
		
		super(context);
		this.myWidth = myWidth;
		this.myHeight = myHeight;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		this.setMeasuredDimension(myWidth, myHeight);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
            	
                //overlay is black with transparency of 0x77 (119)
                this.getDrawable().setColorFilter(0x77000000,PorterDuff.Mode.SRC_ATOP);
                this.invalidate();
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                //clear the overlay            
                this.getDrawable().clearColorFilter();
                this.invalidate();
                callOnClick();
                break;
            }
        }

        return true;
    
	}
}
