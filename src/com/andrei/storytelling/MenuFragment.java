package com.andrei.storytelling;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

import com.andrei.storytelling.controllers.BookController;
import com.andrei.storytelling.customviews.CustomImageButton;
import com.andrei.storytelling.models.Menu;
import com.andrei.storytelling.models.ButtonModel;
import com.andrei.storytelling.util.Tools;

public class MenuFragment  extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		Menu menu = BookController.getInstance().menu();
		
		RelativeLayout contentPanel = new RelativeLayout(getActivity());
		
			
		RelativeLayout frame = new RelativeLayout(getActivity());

		contentPanel.addView(frame,new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	
		if (menu.getBackground() != null) {
			frame.setBackground(Tools.getDrawable(getActivity(), menu.getBackground() ));
		}
		
//		int length = menu.getOptions().size();
		float scaleFactor = ScreenSettings.getScaleFactor();
		for (ButtonModel option :  menu.getOptions()) {
			
			final CustomImageButton image = new CustomImageButton(getActivity(), Tools.Scale(option.getImage().getWidth(), scaleFactor), Tools.Scale(option.getImage().getHeight(), scaleFactor));
			image.setImageDrawable(Tools.getDrawable(getActivity(), option.getImage().getPath()));
			
			
			final Intent intent = new Intent(getActivity(), PagesActivity.class);
				intent.putExtra("action", option.getAction());
			image.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					startActivity(intent);
				}
			});
			frame.addView(image, option.getParams());
		}
		
		return contentPanel;
	}
}
