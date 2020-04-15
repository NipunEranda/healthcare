package com.healthcare.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.healthcare.model.User;


public class PatientManagementServiceImp implements PatientManagementService {

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

	@Override
	public User getUserDetails(String userId) {
		User user = DBManager.getUserDetails(userId);
		return user;
	}

	@Override
	public ArrayList<User> getAllUsers() {
		ArrayList<User> allUsers;
		allUsers = DBManager.getAllUsers();
		return allUsers;
	}

	@Override
	public User deleteUser(String userId) {
		User user = DBManager.deleteUser(userId);
		return user;
	}

	@Override
	public User updateUser(String userId, String username, String email, String mobileNumber, String address) {
		User user = DBManager.updateUser(userId, username, email, mobileNumber, address);
		return user;
	}

}
