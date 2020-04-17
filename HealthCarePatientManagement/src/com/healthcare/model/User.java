package com.healthcare.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.JsonObject;

@XmlRootElement(name="user")
public class User {
	
	private int userId;
	private String firstName;
	private String lastName;
	private int age;
	private String gender;
	private String mobileNumber;
	private String address;
	
	public User() {
		
	}
	
	public User(int userId, String firstName, String lastName, int age, String gender, String mobileNumber,
			String address) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
		this.mobileNumber = mobileNumber;
		this.address = address;
	}
	
	public JsonObject createUserObjWithBasicDetails() {
		
		JsonObject user = new JsonObject();
		user.addProperty("userId", userId);
		user.addProperty("firstName", lastName);
		user.addProperty("age", age);
		user.addProperty("gender", gender);
		user.addProperty("mobileNumber", mobileNumber);
		user.addProperty("address", address);
		
		return user;
	}
	
	public JsonObject createUserObjWithLoginDetails(User iUser, int loginId, int loginRole, String email) {
		
		JsonObject user = new JsonObject();
		JsonObject login = new JsonObject();
		
		login.addProperty("loginId", loginId);
		login.addProperty("loginRole", loginRole);
		login.addProperty("email", email);
		
		user.addProperty("userId", iUser.getUserId());
		user.addProperty("firstName", iUser.getFirstName());
		user.addProperty("lastName", iUser.getLastName());
		user.addProperty("age", iUser.getAge());
		user.addProperty("gender", iUser.getGender());
		user.addProperty("mobileNumber", iUser.getMobileNumber());
		user.addProperty("address", iUser.getAddress());
		user.add("login", login);
		
		return user;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}
