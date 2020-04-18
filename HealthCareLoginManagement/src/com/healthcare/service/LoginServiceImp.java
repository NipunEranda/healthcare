package com.healthcare.service;

import java.util.HashMap;

import com.google.gson.JsonObject;

public class LoginServiceImp implements LoginService {

	@Override
	public JsonObject login(String email, String password) {
		JsonObject result = DBManager.login(email, password);
		return result;
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

	@Override
	public JsonObject getAutization(String header) {
		JsonObject obj = DBManager.getAutization(header);
		return obj;
	}

}
