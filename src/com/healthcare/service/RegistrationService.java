package com.healthcare.service;

import java.util.HashMap;

import com.healthcare.model.User;

public interface RegistrationService {

	public HashMap<String, String> RegisterUser(String fullName, String email, String password,String mobileNumber, String address);
	public User getUserDetails(String userId);
}
