package com.andrei.storytelling.language;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;

import com.andrei.storytelling.MenuFragment;
import com.andrei.storytelling.R;
import com.andrei.storytelling.models.LanguageModel;

public class LanguageDialog extends Dialog implements android.view.View.OnClickListener{
	
	private FragmentActivity parentActivity;
	private int languagePosition;
//	private CustomImageButton button;
	private ArrayList<LanguageModel> languages;
	private SelectLanguageAdapter adapter;
	
	public LanguageDialog(Context context) {
		super(context);
		this.parentActivity = (FragmentActivity)context;
		
	}



	private Button btnYes, btnNo;
	private Spinner languageSpinner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.select_language_dialogbox);
		btnYes = (Button) findViewById(R.id.btnYes);
		btnNo = (Button) findViewById(R.id.btnNo);
		languageSpinner = (Spinner)findViewById(R.id.language_spriner);
		
		languages = LanguageController.INSTANCE.getLanguage(parentActivity);
		
		adapter = new SelectLanguageAdapter(parentActivity, R.layout.spinner_row, languages);
		languageSpinner.setAdapter(adapter);
		
		languageSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				languagePosition = position;
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// do nothing
				
			}
		});
		
		btnYes.setOnClickListener(this);
		btnNo.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
		int id = v.getId();
		
		if (id == R.id.btnYes) {
			
//			button.setImageDrawable(languages.get(languagePosition).getFlag());
			ArrayList<LanguageModel> auxLanguages = new ArrayList<LanguageModel>(languages);
			LanguageModel aux = auxLanguages.get(languagePosition);
			auxLanguages.remove(languagePosition);
			auxLanguages.add(0, aux);
			
			languages.clear();			
			languages.addAll(auxLanguages);
			LanguageController.INSTANCE.setLanguages(); 
			adapter.notifyDataSetChanged();
			FragmentTransaction ft = parentActivity.getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.main_container, new MenuFragment()).commit();
			
			dismiss();
			
		} else if (id == R.id.btnNo) {
			
			dismiss();
			
		} else {
			
		}
		
	}
	

}
