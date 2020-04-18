package com.healthCare.model;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="duser")
public class DoctorUser {
	
	private int Did;
	private String idnum;
	private String firstName;
	private String lastName;
	private int age;
	private String gender;
	private String mobileNumber;
	private String address;
	private String workplace;
	private String degree;
	private Login login;
	
	public DoctorUser() {
		
	}
	
	public DoctorUser(int did, String idnum,String firstName, String lastName, int age, String gender, String mobileNumber,
			String address, String workplace, String degree, Login login) {
		super();
		Did = did;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
		this.mobileNumber = mobileNumber;
		this.address = address;
		this.workplace = workplace;
		this.degree = degree;
		this.login = login;
	}
	
	public String getIdnum() {
		return idnum;
	}

	public void setIdnum(String idnum) {
		this.idnum = idnum;
	}

	public int getDid() {
		return Did;
	}
	public void setDid(int did) {
		Did = did;
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
	public String getWorkplace() {
		return workplace;
	}
	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public Login getLogin() {
		return login;
	}
	public void setLogin(Login login) {
		this.login = login;
	}
	
}