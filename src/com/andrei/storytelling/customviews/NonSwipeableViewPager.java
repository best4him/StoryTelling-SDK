package com.andrei.storytelling.customviews;

import java.lang.reflect.Field;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Interpolator;

public class NonSwipeableViewPager extends ViewPager {
	
    public NonSwipeableViewPager(Context context) {
        super(context);
        postInitViewPager();
    }
    
	public NonSwipeableViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		postInitViewPager();
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
        // Never allow swiping to switch between pages
        return false;
	}

	
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
        // Never allow swiping to switch between pages
        return false;
	}
	
	private ScrollerCustomDuration mScroller = null;
	   /**
     * Override the Scroller instance with our own class so we can change the
     * duration
     */
    private void postInitViewPager() {
        try {
            Field scroller = ViewPager.class.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            Field interpolator = ViewPager.class.getDeclaredField("sInterpolator");
            interpolator.setAccessible(true);

            mScroller = new ScrollerCustomDuration(getContext(),
                    (Interpolator) interpolator.get(null));
            scroller.set(this, mScroller);
        } catch (Exception e) {
        }
    }
    
    /**
     * Set the factor by which the duration will change
     */
    public void setScrollDurationFactor(double scrollFactor) {
        mScroller.setScrollDurationFactor(scrollFactor);
    }
}
