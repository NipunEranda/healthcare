package com.healthcare.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.healthcare.model.User;

public interface PatientManagementService {

	public HashMap<String, String> RegisterUser(String firstName, String lastName, String age, String gender, String address, String mobileNumber,String email, String password);
	public User getUserDetails(String userId);
	public ArrayList<User> getAllUsers();
	public User deleteUser(String userId);
	public User updateUser(String userId, String firstName, String lastName, String age, String gender, String address, String mobileNumber,String email);
	public HashMap<String, String> recordPatientCondition(String userId, String patientCondition);
	public HashMap<String, String> assignToHospital(String userId, String hospitalId);
}
