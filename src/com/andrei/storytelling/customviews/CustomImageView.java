package com.andrei.storytelling.customviews;

import java.util.ArrayList;

import android.content.Context;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

import com.andrei.storytelling.models.Sprite;
import com.andrei.storytelling.music.FXPlayer;
import com.andrei.storytelling.util.Tools;
public class CustomImageView extends ImageView {
	
	private int myWidth, myHeight;
	private Sprite sprite;
	private Context context;
	private FXPlayer sounds;
	private Handler mAnimationHandler;
	public CustomImageView(Context context, int myWidth, int myHeight, Sprite sprite, FXPlayer sounds) {
		
		super(context);
		this.myWidth = myWidth;
		this.myHeight = myHeight;
		this.sprite = sprite;
		this.context = context;
		this.sounds = sounds;
       	if (sprite.getSounds() != null) {
    		
    		sounds.addSound(300, Tools.getSong(context, sprite.getSounds().getName()));
       	}
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

            break;
        }
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_CANCEL: {
        	
        	if (sprite.getSounds() != null) {
        		
        		int loop = sprite.getSounds().getLoop();
        		
        		if (loop != -1) {
        			
        			sounds.playSound(sprite.getSoundId(),loop);
        			
        		} else {
        			int streamID = sounds.playLoopedSound(sprite.getSoundId());
        		}
        	}
        	
        	int n = 0;
        	ArrayList<Animation> animations = sprite.getAnimations();
			if (animations != null && (n = animations.size())> 0) {
				AnimationSet animationSet = new AnimationSet(true);
				
        		for (Animation animation : animations) {
        			animationSet.addAnimation(animation);
				}
        		
        		this.startAnimation(animationSet);
        		
        	}
			if (sprite.getdAnimation() != null ) {
				this.post(new Starter() );
			}
			System.out.println("qqq" + sprite.getdAnimation().getTotalDuration());
			mAnimationHandler = new Handler();
			mAnimationHandler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					setBackground(sprite.getdAnimation());
				}
			}, sprite.getdAnimation().getTotalDuration() );
            break;
        }
    }
    return true;
	}
	
	class Starter implements Runnable {

		@Override
		public void run() {
			
			if (!sprite.getdAnimation().isRunning()) {
				sprite.getdAnimation().stop();
				sprite.getdAnimation().start();
			} else {
				sprite.getdAnimation().stop();
			}
			
		}
	}
}
