package com.andrei.storytelling.controllers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.andrei.storytelling.BookParser;
import com.andrei.storytelling.ScreenSettings;
import com.andrei.storytelling.language.LanguageController;
import com.andrei.storytelling.models.Book;
import com.andrei.storytelling.models.Image;
import com.andrei.storytelling.models.Menu;
import com.andrei.storytelling.models.ButtonModel;
import com.andrei.storytelling.models.NavigationModel;
import com.andrei.storytelling.models.Page;

public class BookController {

	private static JSONObject jsonBook;
	private static Book.Builder bookBuilder;
	private static Book myBook;
	OnModelLoaded modelListener;
	
	private BookController() {
	};


	/**
	 * get a book model. Assure that the BookParser.init(Context context, String
	 * fileName) is already called
	 * 
	 * @return a book model

	 */
	public static Book getInstance() {

		if (myBook == null) {
			throw new NullPointerException("No book instance!");
		}
		
		return myBook;

	}

	/**
	 * Create a book model from a json file
	 * 
	 * @param context
	 * @param fileName
	 *            the name of json file
	 * @return
	 */
	public  static void createBook(Context context, OnModelLoaded listener) {

		jsonBook = BookParser.getInstance();
		try {
			
			JSONObject headerJson = jsonBook.getJSONObject("header");
			JSONObject menuJson = jsonBook.getJSONObject("body").optJSONObject("menu");
			JSONArray navigationJson = jsonBook.getJSONObject("body").optJSONArray("navigation");
			JSONArray pagesJson = jsonBook.getJSONObject("body").optJSONArray("pages");
			
			// set languages
			JSONArray languagesJson = jsonBook.optJSONArray("languages");
			
			//this must be  first object created, because, rest of the application will use
			//the a specific languages text
			LanguageController.INSTANCE.parseLanguages(languagesJson,context);
			
			double width = headerJson.getDouble("default_width");
			double height = headerJson.getDouble("default_heigt");
			bookBuilder = new Book.Builder(width, height);
			
			ScreenSettings.setParameters(context, width, height);
			
			bookBuilder.setCoverImage(new Image(headerJson.optString("cover_image")));
			
			//add menu to model
			if (menuJson != null) {
				
				Menu mMenu = new Menu(menuJson.optString("menu_background"));
				JSONArray menuOptionsJson = menuJson.optJSONArray("options");
				for (int i = 0,n = menuOptionsJson.length(); i < n; i++) {
					
					JSONObject menuAt = menuOptionsJson.getJSONObject(i);
						ButtonModel mn = ButtonModel.getButton(menuAt);
						mMenu.addOptionMenu(mn);
				}
				
				bookBuilder.setOptionMenu(mMenu);
			}
			
			// add navigation button
			NavigationModel navModel = new NavigationModel();
			
			for (int i =0, n = navigationJson.length(); i < n; i++) {
				
				ButtonModel btmodel = ButtonModel.getButton(navigationJson.getJSONObject(i));
				navModel.addButton(btmodel);
			}
			
//			navModel.setNext(navigationJson.optString("next_button"));
//			navModel.setPrevious(navigationJson.optString("previous_button"));
			bookBuilder.navigation(navModel);
			
			//add pages to model
			if (pagesJson != null) {
				List <Page> pages = new ArrayList<Page>();
				
				for (int i=0, n = pagesJson.length(); i < n; i++) {
					
					Page page = Page.getPage(pagesJson.optJSONObject(i));
					pages.add(page);
				}
				
				bookBuilder.setPages(pages);
			}
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
			
		myBook = bookBuilder.build();
		
		listener.OnModelLoadedComplete();
	}


	

	public interface OnModelLoaded {
		void OnModelLoadedComplete();
	}

}
