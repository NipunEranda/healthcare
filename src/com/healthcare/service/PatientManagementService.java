package com.healthcare.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.healthcare.model.User;

public interface PatientManagementService {

	public HashMap<String, String> RegisterUser(String fullName, String email, String password,String mobileNumber, String address);
	public HashMap<String, String> login(String email, String password, String roleId);
	public User getUserDetails(String userId);
	public int getUserLoginId(String userId);
	public String getRoleName(String roleId);
	public ArrayList<User> getAllUsers();
	public User deleteUser(String userId);
	public User updateUser(String userId, String username, String email, String mobileNumber, String address);
	public String resetPassword(String User_Id, String currentPassword, String newPassword);
	public String verifyPassword(String User_Id, String currentPassword);
}
