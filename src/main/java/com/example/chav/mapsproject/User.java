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
	private HashSet<Long> friends;
	private HashSet<Spot> favouriteSpots;
	private HashSet<Spot> spotsAddedByUser;

	
	public User(String username, String firstName, String lastName, String email, String password) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.friends = new HashSet<>();
		this.favouriteSpots = new HashSet<Spot>();
		this.spotsAddedByUser = new HashSet<Spot>();
	}

	public User(long id, String username, String firstName, String lastName, String email, String password) {
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.friends = new HashSet<>();
		this.favouriteSpots = new HashSet<>();
		this.spotsAddedByUser = new HashSet<>();
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

	public HashSet<Long> getFriends() {
		return friends;
	}

	public void setFriends(HashSet<Long> friends) {
		this.friends = friends;
	}


}
