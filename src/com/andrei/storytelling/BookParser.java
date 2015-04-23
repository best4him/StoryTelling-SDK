package com.andrei.storytelling;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.andrei.storytelling.controllers.BookController;
import com.andrei.storytelling.controllers.BookController.OnModelLoaded;
import com.andrei.storytelling.models.Book;
import com.andrei.storytelling.util.NotifyUser;
import com.andrei.storytelling.util.RestClient;
import com.andrei.storytelling.util.RestClient.RequestResponseHandler;
import com.andrei.storytelling.util.RestClient.RequestType;
import com.andrei.storytelling.util.Tools;

public class BookParser {

	private static JSONObject jsonBook;
	private Book.Builder bookBuilder;
	private ProgressDialog progressDialog;
	private static Context mContext;
	private static OnInitCompleted mCallback;
	private BookParser() {
	}

	public static Context getmContext() {
		return mContext;
	}

	/**
	 * get the json file with the name fileName from asset
	 * 
	 * @param context
	 * @param fileName
	 *            the name of json file
	 * @return the json object
	 */
	public static void init(Context context, InitSettings settings, OnInitCompleted listener) {
		
		mCallback = listener;
		mContext = context;
		
		switch (settings.getType()) {
		
		case ST_ALONE:
			
			FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
			ft.add(R.id.main_container, settings.getmSplashScreen(), "splashScreen").commit();
			
			if (jsonBook == null) {

				try {

					jsonBook = new JSONObject(Tools.loadJSONFromAsset(context, settings.getFileName()));
					BookController.createBook(mContext, new OnModelLoaded() {
						
						@Override
						public void OnModelLoadedComplete() {
//							Toast.makeText(mContext, "gata", Toast.LENGTH_LONG).show();
							mCallback.onInitCompleted(mContext);
						}
					});
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			break;
			
		case ST_WEB:
			
			FragmentTransaction ftWeb = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
			ftWeb.add(R.id.main_container, settings.getmSplashScreen(), "splashScreen").commit();
			RestClient.setBaseUrl(settings.getBaseUrl());
			
			
			
			RestClient.makeRequestTest(mContext, RequestType.POST, settings.getJsonWebAddress(), null, new RequestResponseHandler() {
				
				@Override
				public void onSuccess(String response) {
					try {
						jsonBook = new JSONObject(response);
						BookController.createBook(mContext, new OnModelLoaded() {
							
							@Override
							public void OnModelLoadedComplete() {
								mCallback.onInitCompleted(mContext);
							}
						});
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				
				@Override
				public void onFaild(String message) {
					
					NotifyUser.showDialogAndClose(mContext, message);
					Log.d("BookParser.init(...)", "onFaild: Donwloading faild");
				}
			});
			
			break;
			
		default:
			break;
		}
	}

	/**
	 * get the jsonObject if it was created, otherwise throws
	 * NullPointerException. Use this method after you call at least once the
	 * init (Context context, String fileName) method;
	 * 
	 * @return the json object
	 * @throws JSONException
	 *             if the parse fails or doesn't yield a JSONObject
	 */
	public static JSONObject getInstance() {

		if (jsonBook == null) {

			throw new NullPointerException("First you should call getInstance(Context context, String fileName)");

		} else {
			return jsonBook;
		}
	}
	
	private class LoadViewTask extends AsyncTask<Void, Integer, Void> 
	{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			 progressDialog = ProgressDialog.show(mContext,"Loading...", "Preparing application, please wait...", false, false);
			 progressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			return null;
		}
	}
	
	public interface OnInitCompleted {
		void onInitCompleted(Context context);
	}
	
}
