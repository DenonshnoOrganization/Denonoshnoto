package com.example.chav.mapsproject;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Scanner;


public class User{

	private long id;
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private HashSet<User> friends; 
	private HashSet<Spot> favouriteSpots;
	private HashSet<Spot> spotsAddedByUser;
	Scanner sc = new Scanner(System.in);

	
	public User(String username, String firstName, String lastName, String email, String password) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		friends = new HashSet<User>();
		favouriteSpots = new HashSet<Spot>();
		spotsAddedByUser = new HashSet<Spot>();
	}

	public User(long id, String username, String firstName, String lastName, String email, String password) {
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		friends = new HashSet<User>();
		favouriteSpots = new HashSet<Spot>();
		spotsAddedByUser = new HashSet<Spot>();
	}

	public String getUsername() {
		return username;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public HashSet<Spot> getSpotsAddedByUser() {
		return spotsAddedByUser;
	}

	public void setSpotsAddedByUser(HashSet<Spot> spotsAddedByUser) {
		this.spotsAddedByUser = spotsAddedByUser;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public HashSet<User> getFriends() {
		return friends;
	}

	public void setFriends(HashSet<User> friends) {
		this.friends = friends;
	}

	public HashSet<Spot> getFavouriteSpots() {
		return favouriteSpots;
	}

	public void setFavouriteSpots(HashSet<Spot> favouriteSpots) {
		this.favouriteSpots = favouriteSpots;
	}

	public void addSpotToFavourites(Spot spot) {
			favouriteSpots.add(spot);
	}



	
	public void addReport(String report, Spot spot) {
		spot.addReport(report,this);
	}
	
	public void rateTaggedSpot(Spot spot,double userRate) {
			spot.rate(userRate);
	}
	
	public void tagPlaceGPS() {
		//..GPS on
		Spot.SpotType spotType = null;
		//input spotType
		Spot gpsSpot = new Spot(sc.nextLine(), spotType);
		gpsSpot.addInfo(sc.nextLine(),sc.nextLine(),this);
		spotsAddedByUser.add(gpsSpot);
	}
	//instead of x,y we can use some kind of option tap() and gets its coordinates
	public void tagPlaceCoordingates(double x,double y) {
		Spot.SpotType spotType = null;
		//input spotType;
		Spot coordSpot = new Spot(sc.nextLine(),spotType);
		Calendar date = Calendar.getInstance();
		coordSpot.addInfo(sc.nextLine(),sc.nextLine(),this);
		spotsAddedByUser.add(coordSpot);
	}
	
	
	public void showHistory() {
		for(Spot spot : spotsAddedByUser) {
			System.out.println(spot.getName());
		}
	}
	public void addFriend(User userF) {
		friends.add(userF);
	}
	
	public Spot filterBy(Spot.SpotType filteredSPots) { 
		// should return filtered spots
		return null;
	}
	
	public boolean checkSignedUp(String username, String password) {
		return true;
	}

}
