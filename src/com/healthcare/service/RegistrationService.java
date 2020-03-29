package com.healthcare.service;

import java.util.HashMap;

public interface RegistrationService {

	public HashMap<String, String> RegisterUser(String fullName, String email, String password,String mobileNumber, String address);
	
}
