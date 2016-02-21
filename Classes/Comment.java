package com.example.arc.androidhomweorkonetaskone;

import java.util.Calendar;

public class Comment {
	
	private String title;
	private String description;
	private Calendar dateOfCreation;
	private User addedBy; //+ neobhodimo li e sled kato go imame v bazi danni?

	public Comment(String title, String description,User addedBy) {
		super();
		this.title = title;
		this.description = description;
		this.dateOfCreation = Calendar.getInstance();
		this.addedBy = addedBy;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Calendar getDateOfCreation() {
		return dateOfCreation;
	}

	public User getAddedBy() {
		return addedBy;
	}
}
