package com.andrei.storytelling.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class NotifyUser {
	
	public static void showDialog(Context context, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message).setPositiveButton("Ok", null);
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	public static void showDialogAndClose(final Context context, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message).setPositiveButton("Ok", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				((Activity)context).finish();
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}
}
