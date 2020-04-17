package com.healthcare.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonObject;
import com.healthcare.model.User;

public interface PatientManagementService {

	public HashMap<String, String> RegisterUser(String firstName, String lastName, String age, String gender, String address, String mobileNumber,String email, String password);
	public JsonObject getUserDetails(String userId);
	public JsonObject getUserDetailsByLoginId(String loginId);
	public List<String> getAllUsers();
	public JsonObject deleteUser(String userId);
	public JsonObject updateUser(String userId, String firstName, String lastName, String age, String gender, String address, String mobileNumber,String email);
	public HashMap<String, String> recordPatientCondition(String userId, String patientCondition);
	public HashMap<String, String> assignToHospital(String userId, String hospitalId);
}
