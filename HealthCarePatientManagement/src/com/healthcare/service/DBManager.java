package com.healthcare.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonObject;
import com.healthcare.model.User;
import com.healthcare.util.DBConnection;

public class DBManager {

	public static int registerUser(String firstName, String lastName, String age, String gender, String address,
			String mobileNumber, String email, String password) {

		int i = 0;
		int j = 0;
		int z = 0;

		try {

			Connection con = DBConnection.connect();

			String insertSQL1 = "INSERT INTO login VALUES(?, ?, ?, ?)";
			String insertSQL2 = "INSERT INTO user VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
			String insertSQL3 = "INSERT INTO patient VALUES(?, NULL, NULL)";
			String emailVarification = "SELECT * FROM login WHERE Login_Email = ?";
			String getLoginId = "SELECT Login_Id FROM login WHERE Login_Email = ?";
			int loginId;

			PreparedStatement ps_emailVarification = con.prepareStatement(emailVarification);
			ps_emailVarification.setString(1, email);

			ResultSet rs1 = ps_emailVarification.executeQuery();

			if (rs1.first() == false) {

				PreparedStatement ps_insertLogin = con.prepareStatement(insertSQL1);
				ps_insertLogin.setInt(1, 0);
				ps_insertLogin.setInt(2, 2);
				ps_insertLogin.setString(3, email);
				ps_insertLogin.setString(4, password);
				i = ps_insertLogin.executeUpdate();

				PreparedStatement ps_getLoginId = con.prepareStatement(getLoginId);
				ps_getLoginId.setString(1, email);

				ResultSet rs2 = ps_getLoginId.executeQuery();

				while (rs2.next()) {

					if (i > 0) {
						loginId = rs2.getInt(1);
						PreparedStatement ps_insertUser = con.prepareStatement(insertSQL2);
						ps_insertUser.setInt(1, 0);
						ps_insertUser.setInt(2, loginId);
						ps_insertUser.setString(3, firstName);
						ps_insertUser.setString(4, lastName);
						ps_insertUser.setInt(5, Integer.parseInt(age));
						ps_insertUser.setString(6, gender);
						ps_insertUser.setString(7, address);
						ps_insertUser.setString(8, mobileNumber);
						j = ps_insertUser.executeUpdate();

						if (j < 0) {
							i = 0;
						} else {

							JsonObject user = getUserDetailsByLoginId(String.valueOf(loginId));
							PreparedStatement ps_insertPatient = con.prepareStatement(insertSQL3);
							ps_insertPatient.setInt(1, user.get("userId").getAsInt());
							z = ps_insertPatient.executeUpdate();

							if (z < 0) {
								i = 0;
								j = 0;
							}
						}
					}

				}

			} else {
				i = 0;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	public static int getLoginId(String userId) {
		int loginId = 0;
		try {

			Connection con = DBConnection.connect();
			
			String getLoginId = "SELECT loginId FROM user WHERE userId = " + userId;
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

	public static JsonObject getUserDetails(String userId) {
		JsonObject jsonUser = null;
		try {

			Connection con = DBConnection.connect();

			String getSql = "SELECT u.loginId, u.firstName, u.lastName, u.age, u.gender, u.mobileNumber, u.address, l.Login_Email, l.Login_Role FROM user u, login l WHERE u.userId = ? AND u.loginId = l.Login_Id";

			PreparedStatement ps_getUserDetails = con.prepareStatement(getSql);
			ps_getUserDetails.setInt(1, Integer.parseInt(userId));

			ResultSet rs = ps_getUserDetails.executeQuery();

			while (rs.next()) {
				User user = new User(Integer.parseInt(userId), rs.getString(2), rs.getString(3), rs.getInt(4),
						rs.getString(5), rs.getString(6), rs.getString(7));
				jsonUser = user.createUserObjWithLoginDetails(user, rs.getInt(1), rs.getInt(9), rs.getString(8));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return jsonUser;
	}

	public static JsonObject getUserDetailsByLoginId(String loginId) {
		JsonObject jsonUser = null;
		try {

			Connection con = DBConnection.connect();

			String getSql = "SELECT u.userId, u.firstName, u.lastName, u.age, u.gender, u.mobileNumber, u.address, l.Login_Email, l.Login_Role FROM user u, login l WHERE u.loginId = ? AND u.LoginId = l.Login_Id";

			PreparedStatement ps_getUserDetails = con.prepareStatement(getSql);
			ps_getUserDetails.setInt(1, Integer.parseInt(loginId));

			ResultSet rs = ps_getUserDetails.executeQuery();

			while (rs.next()) {
				User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5),
						rs.getString(6), rs.getString(7));
				jsonUser = user.createUserObjWithLoginDetails(user, Integer.parseInt(loginId), rs.getInt(9),
						rs.getString(8));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return jsonUser;
	}

	public static List<String> getAllUsers() {

		List<String> allUsers = new ArrayList<>();
		try {

			Connection con = DBConnection.connect();

			String getSql = "SELECT u.userId, u.loginId, u.firstName, u.lastName, u.age, u.gender, u.mobileNumber, u.address, l.Login_Email, l.Login_Role FROM user u, login l WHERE u.loginId = l.Login_Id";

			PreparedStatement ps_getUserDetails = con.prepareStatement(getSql);

			ResultSet rs = ps_getUserDetails.executeQuery();

			while (rs.next()) {
				User user = new User(rs.getInt(1), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6),
						rs.getString(7), rs.getString(8));
				JsonObject object = user.createUserObjWithLoginDetails(user, rs.getInt(2), rs.getInt(10),
						rs.getString(9));
				allUsers.add(object.toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return allUsers;

	}

	public static JsonObject deleteUser(String userId) {
		JsonObject user = null;
		try {
			user = getUserDetails(userId);
			int loginId = getLoginId(userId);
			Connection con = DBConnection.connect();
			
			String deleteFromUser = "DELETE FROM user WHERE userId = " + userId;

			if (loginId > 0) {
				String deleteFromLogin = "DELETE FROM login WHERE Login_Id = " + loginId;
				PreparedStatement ps_deleteFromUser = con.prepareStatement(deleteFromUser);
				if (ps_deleteFromUser.executeUpdate() > 0) {

					PreparedStatement ps_deleteFromLogin = con.prepareStatement(deleteFromLogin);
					if (ps_deleteFromLogin.executeUpdate() < 0) {
						user = null;
					} else {
						String deleteFromPatient = "DELETE FROM patient WHERE userId = " + userId;
						PreparedStatement ps_deleteFromPatient = con.prepareStatement(deleteFromPatient);
						if (ps_deleteFromPatient.executeUpdate() < 0) {
							user = null;
						}
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	public static JsonObject updateUser(String userId, String firstName, String lastName, String age, String gender,
			String address, String mobileNumber, String email) {
		JsonObject user = null;
		try {

			int loginId = getLoginId(userId);
			Connection con = DBConnection.connect();

			String updateUserTable = "UPDATE user SET firstName = ?, lastName = ?, age = ?, gender = ?, address = ?, mobileNumber = ? WHERE userId = ?";
			String updateLoginTable = "UPDATE login SET Login_Email = ? WHERE Login_Id = ?";

			PreparedStatement ps_updateUserTable = con.prepareStatement(updateUserTable);
			ps_updateUserTable.setString(1, firstName);
			ps_updateUserTable.setString(2, lastName);
			ps_updateUserTable.setInt(3, Integer.parseInt(age));
			ps_updateUserTable.setString(4, gender);
			ps_updateUserTable.setString(5, address);
			ps_updateUserTable.setString(6, mobileNumber);
			ps_updateUserTable.setInt(7, Integer.parseInt(userId));

			PreparedStatement ps_updateLoginTable = con.prepareStatement(updateLoginTable);
			ps_updateLoginTable.setString(1, email);
			ps_updateLoginTable.setInt(2, loginId);

			if (ps_updateUserTable.executeUpdate() > 0) {

				if (ps_updateLoginTable.executeUpdate() > 0) {
					user = getUserDetails(userId);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

	public static HashMap<String, String> recordPatientCondition(String userId, String patientCondition) {

		HashMap<String, String> h = new HashMap<>();

		try {

			Connection con = DBConnection.connect();

			String updateSQL = "UPDATE patient SET patientCondition = ? WHERE userId = ?";

			PreparedStatement ps_insertPatientCondition = con.prepareStatement(updateSQL);
			ps_insertPatientCondition.setString(1, patientCondition);
			ps_insertPatientCondition.setInt(2, Integer.parseInt(userId));

			if (ps_insertPatientCondition.executeUpdate() > 0) {
				h.put("status", "success");
			} else {
				h.put("status", "fail");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return h;
	}

	public static HashMap<String, String> assignToHospital(String userId, String hospitalId) {
		HashMap<String, String> h = new HashMap<>();

		try {
			Connection con = DBConnection.connect();
			
			String updateSQL = "UPDATE patient SET hospitalId = ? WHERE userId = ?";

			PreparedStatement ps_updatehospitalId = con.prepareStatement(updateSQL);
			ps_updatehospitalId.setInt(1, Integer.parseInt(hospitalId));
			ps_updatehospitalId.setInt(2, Integer.parseInt(userId));

			if (ps_updatehospitalId.executeUpdate() > 0) {
				h.put("status", "success");
			} else {
				h.put("status", "fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return h;
	}

}
