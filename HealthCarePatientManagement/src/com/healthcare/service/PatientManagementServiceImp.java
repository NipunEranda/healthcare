package com.healthcare.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.healthcare.model.User;


public class PatientManagementServiceImp implements PatientManagementService {

	@Override
	public HashMap<String, String> RegisterUser(String firstName, String lastName, String age, String gender,
			String address, String mobileNumber, String email, String password) {
		HashMap<String, String> h = new HashMap<>();

		int i = DBManager.registerUser(firstName, lastName, age, gender, address, mobileNumber,email, password);
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
	public User updateUser(String userId, String firstName, String lastName, String age, String gender, String address,
			String mobileNumber, String email) {
		User user = DBManager.updateUser(userId, firstName, lastName, age, gender, address, mobileNumber, email);
		return user;
	}

	@Override
	public HashMap<String, String> recordPatientCondition(String userId, String patientCondition) {
		HashMap<String, String> h = DBManager.recordPatientCondition(userId, patientCondition);
		return h;
	}

	@Override
	public HashMap<String, String> assignToHospital(String userId, String hospitalId) {
		HashMap<String, String> h = DBManager.assignToHospital(userId, hospitalId);
		return h;
	}


}
