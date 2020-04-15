package com.healthcare.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.healthcare.model.User;

public class DBManager {

	static final String DBDriver = "com.mysql.cj.jdbc.Driver";
	static final String DBUrl = "jdbc:mysql://localhost:3306/healthcare";
	static final String DBUser = "root";
	static final String DBPassword = "";

	static {
		try {
			Class.forName(DBDriver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static int registerUser(String fullName, String email, String password, String mobileNumber,
			String address) {

		int i = 0;
		int j = 0;

		try {

			Connection con = DriverManager.getConnection(DBUrl, DBUser, DBPassword);

			String insertSQL1 = "INSERT INTO login VALUES(?, ?, ?, ?)";
			String insertSQL2 = "INSERT INTO user VALUES(?, ?, ?, ?, ?)";
			String emailVarification = "SELECT * FROM login WHERE Login_Email = ?";
			String getUId = "SELECT Login_Id FROM login WHERE Login_Email = ?";

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

				PreparedStatement ps_getUId = con.prepareStatement(getUId);
				ps_getUId.setString(1, email);

				ResultSet rs2 = ps_getUId.executeQuery();

				while (rs2.next()) {

					if (i > 0) {

						PreparedStatement ps_insertUser = con.prepareStatement(insertSQL2);
						ps_insertUser.setInt(1, 0);
						ps_insertUser.setInt(2, rs2.getInt(1));
						ps_insertUser.setString(3, fullName);
						ps_insertUser.setString(4, mobileNumber);
						ps_insertUser.setString(5, address);
						j = ps_insertUser.executeUpdate();

						if (j < 0)
							i = 0;
					}

				}

			} else {
				i = 0;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
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

					User user = getUserDetailsByLoginId(String.valueOf(rs_verifyLogin.getInt(1)));
					h.put("status", "success");
					h.put("userId", user.getUserId());
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

	public static User getUserDetails(String userId) {
		User user = null;
		try {

			Connection con = DriverManager.getConnection(DBUrl, DBUser, DBPassword);

			String getSql = "SELECT u.UFullName, l.Login_Email, u.UMobile, u.UAddress FROM user u, login l WHERE u.User_Id = ? AND u.Login_id = l.Login_Id";

			PreparedStatement ps_getUserDetails = con.prepareStatement(getSql);
			ps_getUserDetails.setInt(1, Integer.parseInt(userId));

			ResultSet rs = ps_getUserDetails.executeQuery();

			while (rs.next()) {

				user = new User(userId, rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

	public static User getUserDetailsByLoginId(String loginId) {
		User user = null;
		try {

			Connection con = DriverManager.getConnection(DBUrl, DBUser, DBPassword);

			String getSql = "SELECT u.User_Id, u.UFullName, l.Login_Email, u.UMobile, u.UAddress FROM user u, login l WHERE u.Login_id = ? AND u.Login_id = l.Login_Id";

			PreparedStatement ps_getUserDetails = con.prepareStatement(getSql);
			ps_getUserDetails.setInt(1, Integer.parseInt(loginId));

			ResultSet rs = ps_getUserDetails.executeQuery();

			while (rs.next()) {

				user = new User(String.valueOf(rs.getInt(1)), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

	public static ArrayList<User> getAllUsers() {

		ArrayList<User> allUsers = new ArrayList<>();
		try {

			Connection con = DriverManager.getConnection(DBUrl, DBUser, DBPassword);

			String getSql = "SELECT u.User_Id, u.UFullName, l.Login_Email, u.UMobile, u.UAddress FROM user u, login l WHERE u.Login_id = l.Login_Id";

			PreparedStatement ps_getUserDetails = con.prepareStatement(getSql);

			ResultSet rs = ps_getUserDetails.executeQuery();

			while (rs.next()) {
				allUsers.add(new User(String.valueOf(rs.getInt(1)), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(4)));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return allUsers;

	}

	public static User deleteUser(String userId) {
		User user = null;
		try {
			user = getUserDetails(userId);
			int loginId = getLoginId(userId);
			Connection con = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
			String deleteFromUser = "DELETE FROM user WHERE User_Id = " + userId;

			if (loginId > 0) {
				String deleteFromLogin = "DELETE FROM login WHERE Login_Id = " + loginId;
				PreparedStatement ps_deleteFromUser = con.prepareStatement(deleteFromUser);
				if (ps_deleteFromUser.executeUpdate() > 0) {

					PreparedStatement ps_deleteFromLogin = con.prepareStatement(deleteFromLogin);
					if (ps_deleteFromLogin.executeUpdate() < 0) {
						user = null;
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	public static User updateUser(String userId, String username, String email, String mobileNumber, String address) {
		User user = null;
		try {

			int loginId = getLoginId(userId);
			Connection con = DriverManager.getConnection(DBUrl, DBUser, DBPassword);

			String updateUserTable = "UPDATE user SET UFullName = ?, UMobile = ?, UAddress = ? WHERE User_Id = ?";
			String updateLoginTable = "UPDATE login SET Login_Email = ? WHERE Login_Id = ?";

			PreparedStatement ps_updateUserTable = con.prepareStatement(updateUserTable);
			ps_updateUserTable.setString(1, username);
			ps_updateUserTable.setString(2, mobileNumber);
			ps_updateUserTable.setString(3, address);
			ps_updateUserTable.setInt(4, Integer.parseInt(userId));

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
