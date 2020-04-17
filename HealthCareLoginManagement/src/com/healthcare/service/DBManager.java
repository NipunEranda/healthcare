package com.healthcare.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.healthcare.model.Response;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class DBManager {

	static final String DBDriver = "com.mysql.cj.jdbc.Driver";
	static final String DBUrl = "jdbc:mysql://localhost:3306/healthcare";
	static final String DBUser = "root";
	static final String DBPassword = "4212";

	static {
		try {
			Class.forName(DBDriver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static HashMap<String, String> login(String email, String password, String roleId) {
		HashMap<String, String> h = new HashMap<>();

		try {
			Connection con = DriverManager.getConnection(DBUrl, DBUser, DBPassword);

			String verifyLogin = "SELECT * FROM login WHERE Login_Email = ? and Login_Password = ?";
			PreparedStatement ps_verifyLogin = con.prepareStatement(verifyLogin);
			ps_verifyLogin.setString(1, email);
			ps_verifyLogin.setString(2, password);

			ResultSet rs_verifyLogin = ps_verifyLogin.executeQuery();

			while (rs_verifyLogin.next()) {

				if (rs_verifyLogin.getInt(2) == Integer.parseInt(roleId)) {

					String getUrl = "http://localhost:8080/HealthCarePatientManagement/webapi/patient/loginId/" + String.valueOf(8);
					JsonObject object = Response.getResponse(getUrl);
					
					h.put("status", "success");
					h.put("userId", object.get("userId").toString());
				}else {
					h.put("status", "fail");
					h.put("userId", null);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return h;
	}

	public static int getLoginId(String userId) {
		int loginId = 0;
		try {

			Connection con = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
			String getLoginId = "SELECT Login_id FROM user WHERE User_Id = " + userId;
			PreparedStatement ps_getLoginId = con.prepareStatement(getLoginId);
			ResultSet rs_getLoginId = ps_getLoginId.executeQuery();

			while (rs_getLoginId.next()) {
				loginId = rs_getLoginId.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginId;
	}

	public static String getRoleName(String roleId) {

		String roleName = null;

		try {

			Connection con = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
			String getroleName = "SELECT roleName FROM role WHERE role_id = " + roleId;
			PreparedStatement ps_getroleName = con.prepareStatement(getroleName);
			ResultSet rs_getroleName = ps_getroleName.executeQuery();

			while (rs_getroleName.next()) {
				roleName = rs_getroleName.getString(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return roleName;
	}

	public static String verifyPassword(String UserId, String currentPassword) {
		String status = null;

		try {
			Connection con = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
			int loginId = getLoginId(UserId);
			String getLoginId = "SELECT * FROM login WHERE Login_Id = ? AND Login_Password = ?";

			PreparedStatement ps_verifyPassword = con.prepareStatement(getLoginId);
			ps_verifyPassword.setInt(1, loginId);
			ps_verifyPassword.setString(2, currentPassword);

			ResultSet rs_verifyPassword = ps_verifyPassword.executeQuery();

			if (rs_verifyPassword.first()) {
				status = "success";
			} else {
				status = "fail";
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return status;
	}

	public static String resetPassword(String UserId, String currentPassword, String newPassword) {

		String status = null;

		try {
			Connection con = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
			int loginId = getLoginId(UserId);
			String passwordVerification = verifyPassword(UserId, currentPassword);

			if (passwordVerification.equalsIgnoreCase("success")) {
				String changePassword = "UPDATE login SET Login_Password = " + newPassword;

				PreparedStatement ps_changePassword = con.prepareStatement(changePassword);
				if (ps_changePassword.executeUpdate() > 0) {
					status = "success";
				} else {
					status = "fail";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

}
