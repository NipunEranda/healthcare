package com.healthcare.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.healthcare.model.User;

public interface RegistrationService {

	public HashMap<String, String> RegisterUser(String fullName, String email, String password,String mobileNumber, String address);
	public User getUserDetails(String userId);
	public int getUserLoginId(String userId);
	public ArrayList<User> getAllUsers();
	public User deleteUser(String userId);
	public User updateUser(String userId, String username, String email, String mobileNumber, String address);
	public String resetPassword(String User_Id, String currentPassword, String newPassword);
}
