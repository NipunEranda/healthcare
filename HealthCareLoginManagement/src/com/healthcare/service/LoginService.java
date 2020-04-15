package com.healthcare.service;

import java.util.HashMap;

public interface LoginService {

	public HashMap<String, String> login(String email, String password, String roleId);
	public int getUserLoginId(String userId);
	public String getRoleName(String roleId);
	public String resetPassword(String User_Id, String currentPassword, String newPassword);
	public String verifyPassword(String User_Id, String currentPassword);
}
