package com.healthcare.model;

public class User {
	
	private int userId;
	private String username;
	private String email;
	private String password;
	private String mobileNumber;
	private String address;
	
	public User(int userId, String username, String email, String password, String mobileNumber, String address) {
		super();
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.password = password;
		this.mobileNumber = mobileNumber;
		this.address = address;
	}
	
	public User(int userId, String username, String email, String mobileNumber, String address) {
		super();
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.password = password;
		this.mobileNumber = mobileNumber;
		this.address = address;
	}

	public int getUserId() {
		return userId;
	}
	public String getUsername() {
		return username;
	}
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}

	public String getAddress() {
		return address;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	
}
