package com.example.chav.mapsproject;
import java.util.ArrayList;
import java.util.Calendar;

public  class Spot {
	
	public enum SpotType{ GAS_STATION, CONVENIENCE_STORE, ATM, PHARMACY, GROCERY_STORE,
							ANIMAL_CLINIC, FAST_FOOD,/*SUBTYPES FOR PIZZA KEBAP BURGERS , HOW TO IMPLENET
							WITH SMALL ICONS*/ RESTAURANT, CASINO, GYM, FREE_WI_FI }
	//public enum SpotSubType { PIZZA, KEBAP, BURGER, ATM, COFFEE,}
	private int id;
	private String name; 
	private double rate;
	private int numRates;
	private Comment comment;
	private SpotType typeOfSpot;


	Calendar dateOfCreation;
	//private ArrayList<SpotSubType> spotSubType;
	private ArrayList<Report> reports;
	
	
	//constructor with Name,Comment and Type
	public Spot(String name, Comment comment, SpotType typeOfSpot) {
		super();
		this.name = name;
		rate = 0;
		numRates = 0;
		this.comment = comment;
		this.typeOfSpot = typeOfSpot;
		reports = new ArrayList<Report>();
		dateOfCreation = Calendar.getInstance();
	}

	//constructor only with Name and Type
	public Spot(String name, SpotType typeOfSpot) {
		super();
		this.name = name;
		rate = 0;
		numRates = 0;
		this.typeOfSpot = typeOfSpot;
		reports = new ArrayList<Report>();
		dateOfCreation = Calendar.getInstance();
	}


	public void rate(double userRate) {
		if(userRate < 1 || userRate > 5) {
			//error
		}
		else {
			numRates++;
			rate = (rate + userRate)/numRates;
		}
	}
	
	public void addInfo(String title,String description,User user) {
		this.comment = new Comment(title,description,user);
		
	}
	
	public String getName() {
		return this.name;
	}
	
	public void showInfo() { 
		//System.out.println(this.name + this.typeOfSpot + this.rate + this.comment);
	}

	public void addReport(String report,User user) {
		reports.add(new Report(report,user));
	}

	public double getRate() {
		return rate;
	}

	public int getNumRates() {
		return numRates;
	}

	public Comment getComment() {
		return comment;
	}

	public SpotType getTypeOfSpot() {
		return typeOfSpot;
	}

	public Calendar getDateOfCreation() {
		return dateOfCreation;
	}
}
