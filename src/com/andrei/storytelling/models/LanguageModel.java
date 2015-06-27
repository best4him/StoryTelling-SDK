package com.andrei.storytelling.models;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.andrei.storytelling.util.Tools;

/**
 * This class manipulate the json objects of the languages
 * It is a Model class
 * @author AncientMachine
 *
 */
public class LanguageModel {
	
	private String name;
	private String st;
	private String flag;
	private HashMap<String, String> otherLanguages;
	private HashMap<String, String> values;
	
	public String getName() {
		return name;
	}
	public String getSt() {
		return st;
	}
	public String getFlag() {

		return flag;
	}
	public HashMap<String, String> getValues() {
		return values;
	}
	private LanguageModel() {};
	
	/**
	 * static method factory for LanguageModel
	 * @param jsonObject
	 * @return an instance of LanguageModel class
	 */
	public static LanguageModel getLanguageModel(JSONObject jsonObject, Context context) {
		
		LanguageModel lg = new LanguageModel();
		
		lg.name = jsonObject.optString("name");
		lg.st = jsonObject.optString("st");
		lg.flag = jsonObject.optString("flag");
		JSONArray valuesJSONArray = jsonObject.optJSONArray("values");
		JSONArray otherLanguageJsonArray = jsonObject.optJSONArray("other_names");
		
		lg.values = new HashMap<>();
		lg.otherLanguages = new HashMap<>();
		
		
		
		String key;
		String value;
		
		// add text fileds 
		for (int i = 0, n = valuesJSONArray.length(); i < n; i++) {
			key = valuesJSONArray.optJSONObject(i).optString("id");
			value = valuesJSONArray.optJSONObject(i).optString("text");		
			lg.values.put(key, value);
		}
		
		// add the name of the language in other languages 
		for (int i = 0, n = otherLanguageJsonArray.length(); i < n; i++) {
			key = otherLanguageJsonArray.optJSONObject(i).optString("st");
			value = otherLanguageJsonArray.optJSONObject(i).optString("text");		
			lg.otherLanguages.put(key, value);
		}
		
		return lg;
	}
}
