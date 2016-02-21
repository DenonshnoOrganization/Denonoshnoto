package com.example.arc.androidhomweorkonetaskone;

import java.util.Calendar;

public class Report {
	
	private String text;
	private User addedBy;
	private Calendar localDate;
	
	public Report(String text,User user) {
		this.text = text;
		addedBy = user;
		this.localDate = Calendar.getInstance();
		
	}

	public String getText() {
		return text;
	}

	public User getAddedBy() {
		return addedBy;
	}

	public Calendar getLocalDate() {
		return localDate;
	}
}
