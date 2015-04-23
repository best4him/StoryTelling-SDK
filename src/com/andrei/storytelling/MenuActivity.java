package com.andrei.storytelling;

import com.andrei.storytelling.controllers.BookController;
import com.andrei.storytelling.models.Menu;
import com.andrei.storytelling.util.Tools;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.RelativeLayout;

public class MenuActivity  extends FragmentActivity{

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		Menu menu = BookController.getInstance().menu();
		
		RelativeLayout contentPanel = new RelativeLayout(this);
		
			
		RelativeLayout frame = new RelativeLayout(this);
		contentPanel.addView(frame, ScreenSettings.getFrameParams());
		
		if (menu.getBackground() != null) {
			frame.setBackground(Tools.getDrawable(this, menu.getBackground() ));
		}
		
		setContentView(contentPanel);
	}
}
