package com.healthcare.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {

	static final String DBDriver = "com.mysql.jdbc.Driver";
	static final String DBUrl = "jdbc:mysql://localhost:3306/test";
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

				while(rs2.next()) {

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
}
