package com.andrei.storytelling;

import java.util.HashMap;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.andrei.storytelling.controllers.BookController;
import com.andrei.storytelling.customviews.AlphaPageTransformer;
import com.andrei.storytelling.customviews.CustomImageButton;
import com.andrei.storytelling.customviews.NonSwipeableViewPager;
import com.andrei.storytelling.models.Book;
import com.andrei.storytelling.models.ButtonModel;
import com.andrei.storytelling.models.NavigationModel;
import com.andrei.storytelling.models.Page;
import com.andrei.storytelling.music.MusicPlayer;
import com.andrei.storytelling.util.Tools;

public class PagesActivity extends FragmentActivity   {

	protected Book myBook;
	private NonSwipeableViewPager mPager;
	private RelativeLayout container;
	private HashMap<Integer, PageFragment> pageFragments = new HashMap<>();
	private Page currentPage;
	private MusicPlayer mPlayer;
	private final int numberOfFragments = BookController.getInstance()
			.getNumberOfPage();
	private MyPagerAdapter myPagerAdapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// set fullscreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.page_activity_layout);
		container = (RelativeLayout) findViewById(R.id.page_activity_container);
		
		container.setBackgroundColor(Color.BLACK);
		
		//get extra
		int optionButton;
		
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			
			if (extras == null) {
				optionButton = -1;
			} else {
			
				optionButton = extras.getInt("action");
			}
		} else {
			
			optionButton = Integer.parseInt((String)savedInstanceState.getSerializable("action"));
		}
		
		
		mPager = (NonSwipeableViewPager) findViewById(R.id.viewPager);
		mPager.setPageTransformer(true, new AlphaPageTransformer());
		mPager.setPageMargin(0);
		mPager.setScrollDurationFactor(15);
		mPager.setOffscreenPageLimit(0);
		myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
		mPager.setAdapter(myPagerAdapter);
		
		
		playBackgroundSound(0);
		RelativeLayout frame = new RelativeLayout(this);
		NavigationModel nav = BookController.getInstance().getNav();
		float scaleFactor = ScreenSettings.getScaleFactor();
		
		
		
		if (nav != null && optionButton != 3) {
			
			for (final ButtonModel option : nav.getButtons()) {

				final CustomImageButton image = new CustomImageButton(
						this, Tools.Scale(
								option.getImage().getWidth(), scaleFactor),
						Tools.Scale(option.getImage().getHeight(), scaleFactor));
				image.setImageDrawable(Tools.getDrawable(this, option
						.getImage().getPath()));

				image.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						switch (option.getAction()) {
						
						// previous button
						case 10:
							previousButtonPressed();
							break;
							
						//next button
						case 20:
							nextButtonPressed();
							break;
						}
					}
				});
				frame.addView(image, option.getParams());
			}
			container.addView(frame, ScreenSettings.getFrameParams());
		}
		
		
		mPager.setOnPageChangeListener(new OnPageChangeListener() {
			int currentPosition = 0;

			@Override
			public void onPageSelected(int position) {
				
				playBackgroundSound(position);

				FragmentLifecycle fragmentToShow = (FragmentLifecycle) myPagerAdapter
						.getItem(position);
				fragmentToShow.onResumeFragment();

				FragmentLifecycle fragmentToHide = (FragmentLifecycle) myPagerAdapter
						.getItem(currentPosition);
				fragmentToHide.onPauseFragment();

				currentPosition = position;
			}



			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {

			}

			@Override
			public void onPageScrollStateChanged(int position) {

			}
		});
	}
	@Override
	protected void onPause() {
		super.onPause();
		if (mPlayer != null) {
			mPlayer.release();
		}
	}
	private class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public android.support.v4.app.Fragment getItem(int pos) {
			
			if (pageFragments.containsKey(Integer.valueOf(pos))) {

				return pageFragments.get(Integer.valueOf(pos));
				
			} else {
//				
				PageFragment viewPagerFragment = PageFragment.newInstance(pos);

				pageFragments.put(Integer.valueOf(pos), viewPagerFragment);
				
				return viewPagerFragment;
			}
		
			// switch(pos) {
			//
			// case 0: return
			// FirstFragment.newInstance("FirstFragment, Instance 1");
			// case 1: return
			// SecondFragment.newInstance("SecondFragment, Instance 1");
			// case 2: return
			// ThirdFragment.newInstance("ThirdFragment, Instance 1");
			// case 3: return
			// ThirdFragment.newInstance("ThirdFragment, Instance 2");
			// case 4: return
			// ThirdFragment.newInstance("ThirdFragment, Instance 3");
			// default: return
			// ThirdFragment.newInstance("ThirdFragment, Default");
			// }
		}

		@Override
		public int getCount() {

			return numberOfFragments;
		}
	}

	private void playBackgroundSound(int position) {
		if (mPlayer != null) {
			mPlayer.release();
		}
		currentPage = BookController.getInstance().getPages().get(position);
		if (currentPage.getTextSound() != null
				&& currentPage.getTextSound().length() > 0) {
			mPlayer = MusicPlayer.create(PagesActivity.this,
					currentPage.getTextSound());
			
				mPlayer.play();

		}
	}
	public void nextButtonPressed() {
		mPager.setCurrentItem(getItem(+1), true);
	}

	
	public void previousButtonPressed() {
		mPager.setCurrentItem(getItem(-1), true);
		
	}
	
    private int getItem(int i) {
        return mPager.getCurrentItem() + i;
 }
}
