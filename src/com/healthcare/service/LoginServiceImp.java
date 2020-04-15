package com.healthcare.service;

import java.util.HashMap;

public class LoginServiceImp implements LoginService{

	@Override
	public HashMap<String, String> login(String email, String password, String roleId) {
		HashMap<String, String> h = new HashMap<>();
		
		HashMap<String, String> result = DBManager.login(email, password, roleId);
		
		if(result.get("status").equalsIgnoreCase("success")) {
			h.put("status", "success");
			h.put("userId", result.get("userId"));
		}else {
			h.put("status", "fail");
			h.put("userId", null);
		}
		
		return h;
		
	}
	
	@Override
	public int getUserLoginId(String userId) {
		int loginId = DBManager.getLoginId(userId);
		return loginId;
	}
	
	@Override
	public String resetPassword(String User_Id, String currentPassword, String newPassword) {
		String status = DBManager.resetPassword(User_Id, currentPassword, newPassword);
		return status;
	}
	
	@Override
	public String verifyPassword(String User_Id, String currentPassword) {
		String status = DBManager.verifyPassword(User_Id, currentPassword);
		return status;
	}
	
	@Override
	public String getRoleName(String roleId) {
		String roleName = DBManager.getRoleName(roleId);
		return roleName;
	}
	
}
