package com.andrei.storytelling.pages;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ClipData;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andrei.storytelling.FragmentLifecycle;
import com.andrei.storytelling.ScreenSettings;
import com.andrei.storytelling.controllers.BookController;
import com.andrei.storytelling.customviews.CustomImageView;
import com.andrei.storytelling.models.Page;
import com.andrei.storytelling.models.Sprite;
import com.andrei.storytelling.models.TextBoxItem;
import com.andrei.storytelling.models.TextBoxModel;
import com.andrei.storytelling.music.FXPlayer;
import com.andrei.storytelling.music.MusicPlayer;
import com.andrei.storytelling.util.ReadingType;
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
	private ArrayList<CustomImageView> images = new ArrayList<>(); 
	RelativeLayout frame;
	private  Handler textBoxHandler = new Handler();
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		RelativeLayout contentPanel = new RelativeLayout(getActivity());
//		RelativeLayout contentPanel =(RelativeLayout) inflater.inflate(R.layout.text, container, false);

		position = Integer.parseInt((getArguments().getString("position")));

		currentPage = BookController.getInstance().getPages().get(position);
	

		 frame = getFrame(position);
//		 RelativeLayout frame = new RelativeLayout(getActivity());
		contentPanel.addView(frame, ScreenSettings.getFrameParams());
//		((RelativeLayout)contentPanel.findViewById(R.id.content)).addView(frame, ScreenSettings.getFrameParams());
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
			images.add(image);
			 
			frame.addView(image, sprite.getParams(scaleFactor));
			image.setImageDrawable(Tools.getDrawable(getActivity(), sprite
					.getImage().getPath()));

			if (sprite.getImage().getScaleType() != null) {

				image.setScaleType(sprite.getImage().getScaleType());

			}
			
			if (sprite.getdAnimation() != null ) {
				image.setBackground(sprite.getdAnimation());
			}
			
			if (sprite.isDraggable()) {
				image.setOnTouchListener(new MyTouchListener());
				frame.setOnDragListener(new MyDragListener());
			}
			
			
		}
		
		myTextBox = currentPage.getTextBoxes();
	
		if ( myTextBox != null) {
			
			mTextView = new TextView(getActivity());
			mTextView.setVisibility(View.INVISIBLE);
			
			GradientDrawable shape = new GradientDrawable();
			
			shape.mutate();
			shape.setCornerRadius(myTextBox.getRadius());
			shape.setColor(Color.parseColor(myTextBox.getBackgroundColorWithOpacity()));
			mTextView.setBackground(shape);
			
//			mTextView.setBackgroundColor();
			mTextView.setTextColor(Color.parseColor(myTextBox.getTextColor()));
			mTextView.setTextSize(myTextBox.getTextSize());
			mTextView.setGravity(Gravity.CENTER_HORIZONTAL);
			
			//add external font
			if (myTextBox.getExternalFont()!= null && !myTextBox.getExternalFont().equals("")) {
				Typeface type = Typeface.createFromAsset(getActivity().getAssets(), myTextBox.getExternalFont());
				mTextView.setTypeface(type);
			}

			
			frame.addView(mTextView, myTextBox.getParams(scaleFactor));
		}
		if (position == 0) {
			onResumeFragment();
		}
		return frame;
	}
	
	
	private Runnable runnable = new Runnable() {
		
		@Override
		public void run() {
			
			if (tbItems.size() > 0 ) {
				
				if (tbItems.get(0).getText() == null || tbItems.get(0).getText().equals("")){
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
		if (soundPool != null) {
			soundPool.autoPause();
		}
		if (textBoxHandler != null)
			textBoxHandler.removeCallbacksAndMessages(null);
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
		} 
		if (ReadingType.userChoice == 3 ) {
			for (CustomImageView image : images) {
				image.startAnim();
			}
		}
		
	
		if (myTextBox!= null) {
			tbItems = new ArrayList<>(myTextBox.getTbItems());	
			textBoxHandler = new Handler();
			textBoxHandler.post(runnable);
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
	
	private final class MyTouchListener implements OnTouchListener {
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
				ClipData data = ClipData.newPlainText("", "");
				DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
				view.startDrag(data, shadowBuilder, view, 0);
				view.setVisibility(View.INVISIBLE);
				return true;
			} else {
				return false;
			}
		}
	}

	class MyDragListener implements OnDragListener {
//		Drawable enterShape = getResources().getDrawable(R.drawable.shape_droptarget);
//		Drawable normalShape = getResources().getDrawable(R.drawable.shape);

		@Override
		public boolean onDrag(View v, DragEvent event) {
			int x = 0;
			int y = 0;
			int action = event.getAction();

			switch (event.getAction()) {
			case DragEvent.ACTION_DRAG_STARTED:
				// do nothing
				break;
			case DragEvent.ACTION_DRAG_ENTERED:
//				v.setBackgroundDrawable(enterShape);
				break;
			case DragEvent.ACTION_DRAG_EXITED:
//				v.setBackgroundDrawable(normalShape);

				break;
			case DragEvent.ACTION_DROP:
				// Dropped, reassign View to ViewGroup
				x = (int) event.getX();
				y = (int) event.getY();
				RelativeLayout container = (RelativeLayout) v;
				int containerwidth = container.getWidth();
				int containerHeight = container.getHeight();
		

				View view = (View) event.getLocalState();
				int height = view.getHeight();
				int width = view.getWidth();
				if ((x < 0 || x > containerwidth) && (y < 0 || y > containerHeight)) {
					view.setVisibility(View.VISIBLE);
					break;
				}
				float marginY = y + height / 2;
				float marginX = x + width / 2;
				ViewGroup owner = (ViewGroup) view.getParent();
				owner.removeView(view);

				if (x - (width / 2) > 0 && marginX <= containerwidth) {
					view.setX(x - (width / 2));
				} else if (marginX > containerwidth) {
					view.setX(containerwidth - width);
				} else {
					view.setX(0);
				}

				if (y - (height / 2) > 0 && marginY <= containerHeight) {
					view.setY(y - (height / 2));
					float f = y - (height / 2);

					// System.out.println("rrr" + f + "--" +
					// container.getHeight() + "--" + s );
				} else if (marginY > containerHeight) {

					view.setY(containerHeight - height);
				} else {
					view.setY(0);
				}

				container.addView(view);
				view.setVisibility(View.VISIBLE);
				break;
			case DragEvent.ACTION_DRAG_ENDED:

//				v.setBackgroundDrawable(normalShape);
			default:
				break;
			}
			return true;
		}
	}
	
}
