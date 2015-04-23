package com.andrei.storytelling.models;

import java.util.ArrayList;


public class NavigationModel {
	
	private ArrayList<ButtonModel> buttons = new ArrayList<>();
	
	public void addButton(ButtonModel button) {
		buttons.add(button);
	}

	public ArrayList<ButtonModel> getButtons() {
		return buttons;
	}
	
	
}
