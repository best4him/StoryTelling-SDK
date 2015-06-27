package com.andrei.storytelling.language;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrei.storytelling.R;
import com.andrei.storytelling.models.LanguageModel;
import com.andrei.storytelling.util.Tools;

public class SelectLanguageAdapter extends ArrayAdapter<LanguageModel> {
	
	private Context context;
	private int layoutResourceId;
	ArrayList<LanguageModel>  languages;
	
	public SelectLanguageAdapter(Context context, int resource, ArrayList<LanguageModel>  languages) {
		super(context, resource, languages);
		this.context = context;
		this.layoutResourceId = resource;
		this.languages = languages;
	}
	
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		return getCustomView(position, convertView, parent);
	}

	public View getCustomView(int position, View convertView, ViewGroup parent) {
		View row = convertView;	
		LanguageHolder holder = null;
		
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			 row = inflater.inflate(R.layout.spinner_row, parent, false);
			
			holder = new LanguageHolder();
			
			holder.flag = (ImageView)row.findViewById(R.id.language_spinner_icon);
			holder.name = (TextView)row.findViewById(R.id.language_spinner_country);
			
			row.setTag(holder);
			
		} else {
			holder = (LanguageHolder)row.getTag();
		}
		
		LanguageModel model = languages.get(position);

		holder.flag.setImageDrawable(Tools.getDrawable(context, model.getFlag()));
		holder.name.setText(model.getName());

		return row;

	}

	static class LanguageHolder {
		ImageView flag;
		TextView name;
	}
}
