package com.andrei.storytelling.pages;

import com.andrei.storytelling.R;
import com.andrei.storytelling.StoryTelling;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class ReplyDialog extends Dialog implements OnClickListener {
	
	private FragmentActivity parentActivity;
	private ImageView replay, home;
	
	public ReplyDialog(Context context) {
		super(context);
		this.parentActivity = (FragmentActivity)context;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//prevent for closing on back press
		this.setCancelable(false);
		// prevent closing on touch outside
		this.setCanceledOnTouchOutside(false);
		setContentView(R.layout.reply_dialog);
		
		replay = (ImageView)findViewById(R.id.reply_dialog_replay);
		home = (ImageView)findViewById(R.id.reply_dialog_home);
		replay.setOnClickListener(this);
		home.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		
		if (id == R.id.reply_dialog_home) {
			this.dismiss();
			parentActivity.onBackPressed();
		} else if (id == R.id.reply_dialog_replay) {
			this.dismiss();
			final Intent intent = new Intent(parentActivity, PagesActivity.class);
			
			parentActivity.startActivity(intent);
			parentActivity.finish();
			
		} else {
			
		}
		
	}
}
