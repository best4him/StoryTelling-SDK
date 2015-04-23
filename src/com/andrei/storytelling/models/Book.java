package com.andrei.storytelling.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Book {

	private final double height;
	private final double width;
	private final String title;
	private final List<String> autohors;
	private final Date date;
	private final Image coverImage;
	private final List<Page> pages;
	private final Menu optionMenu;
	private final String description;
	private final NavigationModel nav;

	private static  int windowWidth, windowHeight;
	private static  int paddingX, paddingY;
	
	public static class Builder {

		private final double height;
		private final double width;

		private String title = "No Title";
		private String description = "No description";
		private List<String> autohors = new ArrayList<String>();
		private Image coverImage = new Image("placeholder_book");
		private Date date = new Date();
		private List<Page> pages = new ArrayList<Page>();
		private Menu optionMenu = null;
		private NavigationModel nav = null;
		public Builder(double width, double height) {
			this.width = width;
			this.height = height;
			
		}

		public Builder title(String title) {
			this.title = title;
			return this;
		}

		public Builder autohors(List<String> autohors) {
			this.autohors = autohors;
			return this;
		}
		public Builder navigation(NavigationModel nav) {
			this.nav = nav;
			return this;
		}
		public Builder date(Date date) {
			this.date = date;
			return this;
		}

		public Builder setCoverImage(Image coverImage) {
			this.coverImage = coverImage;
			return this;
		}

		public Builder setPages(List<Page> pages) {

			this.pages = pages;
			return this;
		}

		public Builder setOptionMenu(Menu optionMenu) {

			this.optionMenu = optionMenu;
			return this;
		}


		public Builder setDescription(String description) {

			this.description = description;
			return this;
		}

		public Book build() {
			return new Book(this);
		}

	}

	private Book(Builder builder) {

		width = builder.width;
		height = builder.height;
		title = builder.title;
		description = builder.description;
		autohors = builder.autohors;
		coverImage = builder.coverImage;
		date = builder.date;
		pages = builder.pages;
		optionMenu = builder.optionMenu;
		nav = builder.nav;
	}

	/**
	 * get the book's title
	 * 
	 * @return the title of the book
	 */
	public String getTitle() {

		return title;
	}

	public List<String> getAutohors() {
		return autohors;
	}

	public Date getDate() {
		return date;
	}

	public Image getCoverImage() {
		return coverImage;
	}

	public double getHeight() {
		return height;
	}

	public double getWidth() {
		return width;
	}

	public List<Page> getPages() {
		return pages;
	}

	public NavigationModel getNav() {
		return nav;
	}

	public Menu menu() {
		return optionMenu;
	}

	public String getDescription() {
		return description;
	}

	/**
	 * This method returns the number of current book pages, or zero if there is
	 * no page
	 * 
	 * @return an integer that represents the number of pages
	 */
	public int getNumberOfPage() {
		return pages != null ? pages.size() : 0;
	}

	/**
	 * return the pageNumber. Attention, it's start from zero
	 * 
	 * @param pageNumber
	 * @return
	 */
	public Page getPage(int pageNumber) {
		return pages.get(pageNumber);
	}



}
