package com.healthcare.service;

import java.util.HashMap;


public class RegistrationServiceImp implements RegistrationService {

	@Override
	public HashMap<String, String> RegisterUser(String fullName, String email, String password,String mobileNumber, String address) {
		HashMap<String, String> h = new HashMap<>();

		int i = DBManager.registerUser(fullName, email, password, mobileNumber, address);
		if (i > 0) {
			h.put("register", "success");
		} else {
			h.put("register", "failed");
		}

		return h;
	}


}
