package com.healthcare.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.healthcare.model.Login;
import com.healthcare.model.User;

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

	public static int registerUser(String firstName, String lastName, String age, String gender, String address, String mobileNumber,String email, String password) {

		int i = 0;
		int j = 0;

		try {

			Connection con = DriverManager.getConnection(DBUrl, DBUser, DBPassword);

			String insertSQL1 = "INSERT INTO login VALUES(?, ?, ?, ?)";
			String insertSQL2 = "INSERT INTO user VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
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
						ps_insertUser.setString(3, firstName);
						ps_insertUser.setString(4, lastName);
						ps_insertUser.setInt(5, Integer.parseInt(age));
						ps_insertUser.setString(6, gender);
						ps_insertUser.setString(7, address);
						ps_insertUser.setString(8, mobileNumber);
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

	public static User getUserDetails(String userId) {
		User user = null;
		try {

			Connection con = DriverManager.getConnection(DBUrl, DBUser, DBPassword);

			String getSql = "SELECT u.loginId, u.firstName, u.lastName, u.age, u.gender, u.address, u.mobileNumber, l.Login_Email, l.Login_Role FROM user u, login l WHERE u.userId = ? AND u.loginId = l.Login_Id";

			PreparedStatement ps_getUserDetails = con.prepareStatement(getSql);
			ps_getUserDetails.setInt(1, Integer.parseInt(userId));

			ResultSet rs = ps_getUserDetails.executeQuery();

			while (rs.next()) {

				Login login = new Login(rs.getInt(1), rs.getInt(9), rs.getString(8), null);
				user = new User(Integer.parseInt(userId), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getString(7), login);
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

			String getSql = "SELECT u.userId, u.firstName, u.lastName, u.age, u.gender, u.address, u.mobileNumber, l.Login_Email, l.Login_Role FROM user u, login l WHERE u.loginId = ? AND u.Login_id = l.Login_Id";

			PreparedStatement ps_getUserDetails = con.prepareStatement(getSql);
			ps_getUserDetails.setInt(1, Integer.parseInt(loginId));

			ResultSet rs = ps_getUserDetails.executeQuery();

			while (rs.next()) {

				Login login = new Login(Integer.parseInt(loginId), rs.getInt(9), rs.getString(8), null);
				user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getString(7), login);
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

			String getSql = "SELECT u.userId, u.loginId, u.firstName, u.lastName, u.age, u.gender, u.address, u.mobileNumber, l.Login_Email, l.Login_Role FROM user u, login l WHERE u.Login_id = l.Login_Id";

			PreparedStatement ps_getUserDetails = con.prepareStatement(getSql);

			ResultSet rs = ps_getUserDetails.executeQuery();

			while (rs.next()) {
				Login login = new Login(rs.getInt(2), rs.getInt(10), rs.getString(9), null);
				User user = new User(rs.getInt(1), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getString(7), rs.getString(8), login);
				allUsers.add(user);
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

	public static User updateUser(String userId, String firstName, String lastName, String age, String gender, String address, String mobileNumber,String email) {
		User user = null;
		try {

			int loginId = getLoginId(userId);
			Connection con = DriverManager.getConnection(DBUrl, DBUser, DBPassword);

			String updateUserTable = "UPDATE user SET firstName = ?, lastName = ?, age = ?, gender = ?, address = ?, mobileNumber = ? WHERE User_Id = ?";
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

}
