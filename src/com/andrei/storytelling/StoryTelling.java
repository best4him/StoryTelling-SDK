package com.andrei.storytelling;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.andrei.storytelling.BookParser.OnInitCompleted;
import com.andrei.storytelling.controllers.BookController;
import com.andrei.storytelling.pages.PagesActivity;

public abstract class StoryTelling extends FragmentActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// set fullscreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// control the music volume
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		setContentView(R.layout.main);

		ImageView img = new ImageView(this);
		InitSettings settings = initAppSettings();
		BookParser.init(this, settings, new OnInitCompleted() {

			@Override
			public void onInitCompleted(final Context context) {
				
				
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						if (BookController.getInstance().menu() != null) {
							
							FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
							ft.replace(R.id.main_container, new MenuFragment()).commit();
						} else {
							Intent menuIntent = new Intent(context, PagesActivity.class);
//							menuIntent.setComponent(new ComponentName("com.andrei.storytelling", "com.andrei.storytelling.PagesActivity"));
							((FragmentActivity)context).startActivity(menuIntent);
						}
					}
				}, 2000);
				
			}
		});

	}

	

	public abstract InitSettings initAppSettings();
}
