package test;

import java.time.LocalDate;

public class Comment {
	
	String title;
	String description;
	LocalDate dateOfCreation;
	User addedBy;
	public Comment(String title, String description,User addedBy) {
		super();
		this.title = title;
		this.description = description;
		this.dateOfCreation = LocalDate.now();
		this.addedBy = addedBy;
	}
	
	
	
}
