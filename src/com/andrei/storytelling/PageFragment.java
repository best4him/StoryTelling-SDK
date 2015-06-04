package com.andrei.storytelling;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andrei.storytelling.controllers.BookController;
import com.andrei.storytelling.customviews.CustomImageView;
import com.andrei.storytelling.models.Page;
import com.andrei.storytelling.models.Sprite;
import com.andrei.storytelling.models.TextBoxItem;
import com.andrei.storytelling.models.TextBoxModel;
import com.andrei.storytelling.music.FXPlayer;
import com.andrei.storytelling.music.MusicPlayer;
import com.andrei.storytelling.util.Tools;

public class PageFragment extends Fragment implements FragmentLifecycle {

	private int position;
	private FXPlayer soundPool;
	private Page currentPage;
	private MusicPlayer mPlayer;
	private OnButtonPressListener mCallback;
	private TextView mTextView;
	private TextBoxModel myTextBox;
	private List<TextBoxItem> tbItems;
	private  Handler textBoxHandler = new Handler();
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		RelativeLayout contentPanel = new RelativeLayout(getActivity());

		position = Integer.parseInt((getArguments().getString("position")));
		currentPage = BookController.getInstance().getPages().get(position);
		System.out.println("ttt" + "onReusmeFragment: " + position);
		// add sound
//		if (currentPage.getTextSound() != null
//				&& currentPage.getTextSound().length() > 0) {
//			mPlayer = MusicPlayer.create(getActivity(),
//					currentPage.getTextSound());
//			if (position == 0)
//				mPlayer.play();
//
//		}

		RelativeLayout frame = getFrame(position);
		// RelativeLayout frame = new RelativeLayout(getActivity());
		contentPanel.addView(frame, ScreenSettings.getFrameParams());

		return contentPanel;

	}

	@Override
	public void onResume() {
		super.onResume();

	}

	public static PageFragment newInstance(int pos) {
		
		PageFragment pageFragment = new PageFragment();
		Bundle b = new Bundle();
		b.putString("position", Integer.toString(pos));
		pageFragment.setArguments(b);
		return pageFragment;
	}

	private RelativeLayout getFrame(int position) {

		RelativeLayout frame = new RelativeLayout(getActivity());
		float scaleFactor = ScreenSettings.getScaleFactor();

		frame.setBackground(Tools.getDrawable(getActivity(),
				currentPage.getBackground()));
		soundPool = currentPage.getFxPlayer(getActivity());


		for (Sprite sprite : currentPage.getSprites()) {

			CustomImageView image = new CustomImageView(getActivity(),
					Tools.Scale(sprite.getImage().getWidth(), scaleFactor),
					Tools.Scale(sprite.getImage().getHeight(), scaleFactor),
					sprite, soundPool);
			
			image.setImageDrawable(Tools.getDrawable(getActivity(), sprite
					.getImage().getPath()));

			if (sprite.getImage().getScaleType() != null) {

				image.setScaleType(sprite.getImage().getScaleType());

			}
			
			if (sprite.getdAnimation() != null ) {
				image.setBackground(sprite.getdAnimation());
			}
			
			frame.addView(image, sprite.getParams(scaleFactor));
		}
		
		myTextBox = currentPage.getTextBoxes();
		if ( myTextBox != null) {
			mTextView = new TextView(getActivity());
			
			GradientDrawable shape = new GradientDrawable();
			shape.mutate();
			System.out.println("qqq" + myTextBox.getTextSize());
			shape.setCornerRadius(myTextBox.getRadius());
			shape.setColor(Color.parseColor(myTextBox.getBackgroundColorWithOpacity()));
			mTextView.setBackground(shape);
			
//			mTextView.setBackgroundColor();
			mTextView.setTextColor(Color.parseColor(myTextBox.getTextColor()));
			mTextView.setTextSize(myTextBox.getTextSize());
			mTextView.setGravity(Gravity.CENTER_HORIZONTAL);
			
			if (myTextBox.getExternalFont()!= null && !myTextBox.getExternalFont().equals("")) {
				Typeface type = Typeface.createFromAsset(getActivity().getAssets(), myTextBox.getExternalFont());
				mTextView.setTypeface(type);
			}
			tbItems = new ArrayList<>(myTextBox.getTbItems());
			textBoxHandler.post(runnable);
			
			frame.addView(mTextView, myTextBox.getParams(scaleFactor));
		}
		
		return frame;
	}
	
	
	private Runnable runnable = new Runnable() {
		
		@Override
		public void run() {
			
			if (tbItems.size() > 0) {
				if (tbItems.get(0).getText().equals("")){
					mTextView.animate().alpha(0.0f).setDuration(myTextBox.getAnimationDuration());
					mTextView.setVisibility(View.GONE);
				} else {
					mTextView.setVisibility(View.VISIBLE);
					mTextView.animate().alpha(1.0f).setDuration(myTextBox.getAnimationDuration());
					mTextView.setText(tbItems.get(0).getText());
				}
				
				textBoxHandler.postDelayed(this, tbItems.get(0).getDuration());
				tbItems.remove(0);
			} else {
				mTextView.animate().alpha(0.0f).setDuration(myTextBox.getAnimationDuration());
//				mTextView.setVisibility(View.GONE);
			}
		}
	};
	@Override
	public void onPause() {
		
		super.onPause();
		if (mPlayer != null)
			mPlayer.pause();
//		releaseResources();
	}
@Override
public void onDestroy() {
	super.onDestroy();
	releaseResources();
}
	@Override
	public void onPauseFragment() {

		onPause();
		
	}

	private void releaseResources() {

		if (soundPool != null) {
			soundPool.release();
		}

		if (mPlayer != null) {
			mPlayer.release();
		}
	}

	@Override
	public void onResumeFragment() {
		if (mPlayer != null) {
			
			mPlayer.play();
		} else {
			System.out.println("ttt pe else");
		}
	}

	public interface OnButtonPressListener {

		public void nextButtonPressed();
		public void previousButtonPressed();

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception

		// try {
		// mCallback = (OnButtonPressListener) activity;
		// } catch (ClassCastException e) {
		// throw new ClassCastException(activity.toString()
		// + " must implement OnButtonPressListener");
		// }

	}
}
