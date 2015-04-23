package com.andrei.storytelling.util;

import java.io.UnsupportedEncodingException;

import org.apache.http.Header;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class RestClient extends AsyncHttpClient {
	private static String baseUrl;
	private static AsyncHttpClient client = new AsyncHttpClient();
	private static Context mContext;

	private static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {

		if (baseUrl != null)
			client.get(getAbsoluteUrl(url), params, responseHandler);

	}

	private static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {

		if (baseUrl != null)
			client.post(getAbsoluteUrl(url), params, responseHandler);
		else
			throw new NullPointerException("Set baseUrl");
	}

	private static String getAbsoluteUrl(String relativeUrl) {

		return baseUrl + relativeUrl;
	}

	public static void makeRequest(Context context, RequestType type, String url, RequestParams params,
			final RequestResponseHandler responseHandler) {
		mContext = context;
		
		if (!isOnline()) {
			responseHandler.onFaild("No internet connection! Please connect to the interent and try again");
			return;
		}
		switch (type) {

		case GET:
			client.get(getAbsoluteUrl(url), params, new AsyncHttpResponseHandler() {

				@Override
				public void onSuccess(int statusCode, Header[] headers, byte[] response) {
					try {
						String strResponse = new String(response, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, byte[] response, Throwable arg3) {

					responseHandler.onFaild(arg3.getLocalizedMessage().toString());
				}
			});
			break;

		case POST:
			client.post(getAbsoluteUrl(url), params, new AsyncHttpResponseHandler() {

				@Override
				public void onSuccess(int statusCode, Header[] headers, byte[] response) {

					try {
						String strResponse = new String(response, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, byte[] response, Throwable arg3) {
					responseHandler.onFaild(arg3.getLocalizedMessage().toString());
				}
			});
			break;

		default:
			break;
		}
		

	}
	
	//TODO
	public static void makeRequestTest (Context context, RequestType type, String url, RequestParams params,
			final RequestResponseHandler responseHandler) {
		mContext = context;
		if (isOnline()) {
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					responseHandler.onSuccess("Succes");
				}
			}, 2000);
		} else {
			responseHandler.onFaild("Please connect to the interent and try again!");
		}
	}

	public static void setBaseUrl(String url) {
		baseUrl = url;
	}

	public enum RequestType {
		GET, POST
	}

	public interface RequestResponseHandler {

		void onSuccess(String response);

		void onFaild(String message);
	}

	public static boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		return netInfo != null && netInfo.isConnectedOrConnecting();
	}
}
