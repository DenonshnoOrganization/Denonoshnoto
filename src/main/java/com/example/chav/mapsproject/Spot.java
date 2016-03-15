package com.example.chav.mapsproject;
import java.util.ArrayList;
import java.util.Calendar;

public  class Spot {
	
	public enum SpotType{ GAS_STATION, CONVENIENCE_STORE, ATM, PHARMACY, GROCERY_STORE,
							ANIMAL_CLINIC, FAST_FOOD,/*SUBTYPES FOR PIZZA KEBAP BURGERS , HOW TO IMPLENET
							WITH SMALL ICONS*/ RESTAURANT, CASINO, GYM, FREE_WI_FI }
	//public enum SpotSubType { PIZZA, KEBAP, BURGER, ATM, COFFEE,} //TODO additional functionality for secondary activity on a spot
	private long id;
	private String name;
	private double rate;
	private int numRates;
	private int img;
//	private Comment comment;//TODO
	private SpotType typeOfSpot;
	private double longitude;
	private double latitude;//TODO shared preferences to remmeber logedin user + last place


	Calendar dateOfCreation;
	//private ArrayList<SpotSubType> spotSubType;
	private ArrayList<Report> reports;
	
	
	//constructor with Name,Comment and Type
	public Spot(String name, Comment comment, SpotType typeOfSpot) {
		super();
		this.name = name;
		this.rate = 0;
		this.numRates = 0;
//		this.comment = comment;
		this.typeOfSpot = typeOfSpot;
		this.reports = new ArrayList<Report>();
		this.dateOfCreation = Calendar.getInstance();
	}

	//constructor only with Name and Type
	public Spot(String name, double latitude, double longitude) {
		super();
		this.name = name;
		this.rate = 0;
		this.numRates = 0;
		this.typeOfSpot = typeOfSpot;
		this.reports = new ArrayList<Report>();
		this.latitude = latitude;
		this.longitude = longitude;
		this.dateOfCreation = Calendar.getInstance();
	}

	public Spot(long id, String name, double rate,  double latitude, double longitude, Calendar dateOfCreation, int image) {
		this.id = id;
		this.name = name;
		this.rate = 5;//TODO optimize rate. For now it will be 5 only
		this.longitude = longitude;
		this.latitude = latitude;
		this.dateOfCreation = dateOfCreation;
		this.numRates = 5;// FAKE VALUE
		this.reports = new ArrayList<Report>();
		this.img = image;
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
	
//	public void addInfo(String title,String description,User user) {
//		this.comment = new Comment(title,description,user);
//
//	}
	
	public String getName() {
		return this.name;
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

//	public Comment getComment() {
//		return comment;
//	}

	public SpotType getTypeOfSpot() {
		return typeOfSpot;
	}

	public Calendar getDateOfCreation() {
		return dateOfCreation;
	}

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

	public int getImg() {
		return img;
	}

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
