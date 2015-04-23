package com.andrei.storytelling.models;

import java.util.ArrayList;
import java.util.List;

public class Menu   {

	private String background;

	private List<ButtonModel> options;

	public Menu() {}

	public Menu(String background) {
		this.options = new ArrayList<ButtonModel>();
		this.background = background;
	}
	
	public void addOptionMenu (ButtonModel option) {
		
		if (options == null) this.options = new ArrayList<ButtonModel>();
		options.add(option);
	}

	public String getBackground() {
		return background;
	}

	public List<ButtonModel> getOptions() {
		return options;
	}
	
}
