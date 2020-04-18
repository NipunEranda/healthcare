package com.healthCare.Service;

import java.util.ArrayList;
import java.util.HashMap;

import com.healthCare.model.DoctorUser;
import com.healthCare.model.Login;


public class DoctorManagementServiceImp implements DoctorManageService {

	@Override
	public HashMap<String, String> RegisterUser(String firstName, String lastName, String idnum, String gender,
			String address, String mobileNumber, String workplace, String degree, String email, String password) {
		HashMap<String, String> h = new HashMap<>();
		
		int i = DB.registerUser(firstName, lastName, idnum, gender, address, mobileNumber, workplace, degree, email, password);
		if (i > 0) {
			h.put("register", "successfully");
		} else {
			h.put("register", "failed");
		}

		return h;
	}
		
	

	@Override
	public DoctorUser getUserDetails(String userId) {
		DoctorUser user = DB.getUserDetails(userId);
		return user;
	}

	@Override
	public ArrayList<DoctorUser> getAllUsers() {
		ArrayList<DoctorUser>allUsers;
		allUsers  = DB.getAllUser();
		return allUsers;
	}

	@Override
	public DoctorUser deleteUser(String userId) {
		DoctorUser user = DB.deleteUser(userId);
		return user;
	}

	@Override
	public DoctorUser updateUser(String Did, String firstName, String lastName, String idnum, String gender,
			String address, String mobileNumber, String workplace, String degree, String email) {
		DoctorUser user = DB.updateUser(Did, firstName, lastName, idnum, gender, address, mobileNumber, workplace, degree, email);
		return user;
	}
	

}
