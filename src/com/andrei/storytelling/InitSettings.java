package com.andrei.storytelling;

import android.support.v4.app.Fragment;




public class InitSettings {
	
	private ApplicationTypeEnum type;
	private String fileName;
	private String name;
	private String baseUrl;
	private String jsonWebAddress;
	
	private Fragment mSplashScreen;
	
	public Fragment getmSplashScreen() {
		return mSplashScreen;
	}



	private static InitSettings webInstance;
	private static InitSettings aloneInstance;

	private InitSettings() {}


	/**
	 *  return an instance of InitSettings class for a web  application
	 * @param name the name of application, can be the title of the book
	 * @param baseUrl the base Url of server
	 * @param jsonWebAddress the web address where the user can get the book's json file
	 * @return an InitSettings web instance
	 * @throws IllegalArgumentException if the baseUrl is null
	 */
	public static InitSettings getWebSettingsInstance (String name, String baseUrl, String jsonWebAddress, Fragment splashScreen) {
		
		if ( baseUrl == null) {
			throw new IllegalArgumentException("baseUrl must not be null");
		}
		
		if (webInstance == null) {
			
			webInstance = new InitSettings();
			webInstance.type = ApplicationTypeEnum.ST_WEB;
			webInstance.name = name;
			webInstance.baseUrl = baseUrl;
			webInstance.jsonWebAddress = jsonWebAddress;
			webInstance.mSplashScreen = splashScreen;
		}
		
		return webInstance;
	}


	/**
	 * return an instance of InitSettings class for an alone application
	 * @param name the name of application, can be the title of the book
	 * @param fileName the json file name
	 * @return an InitSettings alone instance
	 * @throws IllegalArgumentException if the filneName is null
	 */
	public static InitSettings getAloneSettingsInstance(String name, String fileName,  Fragment splashScreen) {
		
		if ( fileName == null) {
			throw new IllegalArgumentException("fileName must not be null");
		}

		if (aloneInstance == null) {
			aloneInstance = new InitSettings();
			aloneInstance.type = ApplicationTypeEnum.ST_ALONE;
			aloneInstance.name = name;
			aloneInstance.fileName = fileName;
			aloneInstance.mSplashScreen = splashScreen;
		}
		
		return aloneInstance;
	}
	
	/**
	 * this method returns the applications details like, the type or the name. 
	 * Before you call this method you must initialize this class by calling 
	 * getWebSettingsInstance or getAloneSettingsInstance
	 * @return initSettings instance
	 * @throws NullPointerException if  getWebSettingsInstance or getAloneSettingsInstance were not called
	 */
	public static InitSettings  getInstance () {
		
		if (aloneInstance != null) {
			return aloneInstance;
		} else if (webInstance != null) {
			return webInstance;
		} else {
			throw new NullPointerException("First you should set an instance");
		}
	}
	
	
	public ApplicationTypeEnum getType() {
		return type;
	}



	public String getFileName() {
		return fileName;
	}



	public String getName() {
		return name;
	}



	public String getBaseUrl() {
		return baseUrl;
	}



	public String getJsonWebAddress() {
		return jsonWebAddress;
	}



	public static InitSettings getWebInstance() {
		return webInstance;
	}



	public static InitSettings getAloneInstance() {
		return aloneInstance;
	}
}
