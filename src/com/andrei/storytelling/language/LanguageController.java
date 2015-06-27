package com.andrei.storytelling.language;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Context;

import com.andrei.storytelling.controllers.BookController;
import com.andrei.storytelling.models.LanguageModel;
import com.andrei.storytelling.models.Page;
import com.andrei.storytelling.models.Sprite;
import com.andrei.storytelling.models.TextBoxItem;
import com.andrei.storytelling.models.TextBoxModel;
import com.andrei.storytelling.util.Tools;

 /**
  * This class is a controller, for working well, first, it should be called the parseLanguages(object) method
  * for setting the languages
 * @author AncientMachine
 *
 */
public enum LanguageController {
	 INSTANCE;
	 
	ArrayList<LanguageModel> languages;
	ArrayList<SpinnerLanguageModel> lgModel = new ArrayList<>();
	public ArrayList<LanguageModel> getLanguages() {
		return languages;
	}


	private LanguageController () {
		
	}
	
	
	public void parseLanguages(JSONArray languagesJson, Context context) {

		languages = new ArrayList<>();
		if (languagesJson != null) {
			for (int i = 0, n = languagesJson.length(); i < n; i++) {
				languages.add(LanguageModel.getLanguageModel(languagesJson.optJSONObject(i), context));
			}
		}
	
	}
	
	public ArrayList<LanguageModel> getLanguage(Context context) {
		
				
		return languages;
		
	}
	
	public void setLanguages() {
		
		TextBoxModel myTextBox;
		for (Page page :BookController.getInstance().getPages()) {
			myTextBox = page.getTextBoxes();
			if (myTextBox != null) {
				for (TextBoxItem tbi : myTextBox.getTbItems()) {
					tbi.setText(languages.get(0).getValues().get(tbi.getId()));
				}
			}
			
		}
	}
}
