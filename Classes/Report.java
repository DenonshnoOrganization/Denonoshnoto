package test;

import java.time.LocalDate;

public class Report {
	
	String text;
	User addedBy;
	LocalDate localDate;
	
	public Report(String text,User user,LocalDate localDate) {
		this.text = text;
		addedBy = user;
		this.localDate = localDate;
		
	}

}
