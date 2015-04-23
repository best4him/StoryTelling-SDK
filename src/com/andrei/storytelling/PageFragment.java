package com.andrei.storytelling;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.andrei.storytelling.controllers.BookController;
import com.andrei.storytelling.customviews.CustomImageView;
import com.andrei.storytelling.models.Page;
import com.andrei.storytelling.models.Sprite;
import com.andrei.storytelling.music.FXPlayer;
import com.andrei.storytelling.music.MusicPlayer;
import com.andrei.storytelling.util.Tools;

public class PageFragment extends Fragment implements FragmentLifecycle {

	private int position;
	private FXPlayer soundPool;
	private Page currentPage;
	private MusicPlayer mPlayer;
	private OnButtonPressListener mCallback;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		RelativeLayout contentPanel = new RelativeLayout(getActivity());

		position = Integer.parseInt((getArguments().getString("position")));
		currentPage = BookController.getInstance().getPages().get(position);

		// add sound
		if (currentPage.getTextSound() != null
				&& currentPage.getTextSound().length() > 1) {
			mPlayer = MusicPlayer.create(getActivity(),
					currentPage.getTextSound());

		}

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
			RelativeLayout.LayoutParams image2Params = new RelativeLayout.LayoutParams(
					Tools.Scale(sprite.getImage().getWidth(), scaleFactor),
					Tools.Scale(sprite.getImage().getHeight(), scaleFactor));
			image2Params.leftMargin = Tools.Scale(sprite.getxPosition(),
					scaleFactor);
			image2Params.topMargin = Tools.Scale(sprite.getyPosition(),
					scaleFactor);

			if (sprite.getImage().getScaleType() != null) {

				image.setScaleType(sprite.getImage().getScaleType());

			}
			
			if (sprite.getdAnimation() != null ) {
				image.setBackground(sprite.getdAnimation());
			}
			
			frame.addView(image, image2Params);
		}
		return frame;
	}

	@Override
	public void onPause() {
		super.onPause();
		releaseResources();
	}

	@Override
	public void onPauseFragment() {

		onPause();
		releaseResources();
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
