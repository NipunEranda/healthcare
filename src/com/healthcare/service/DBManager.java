package com.healthcare.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBManager {

	static final String DBDriver = "com.mysql.jdbc.Driver";
	static final String DBUrl = "jdbc:mysql://localhost:3306/test";
	static final String DBUser = "root";
	static final String DBPassword = "4212";
	
	static {
		try {
			Class.forName(DBDriver);
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public static int getUser(String username, String email, String password, String mobileNumber) {
		
		int i = 0;
		
		try {
			
			Connection con = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
			
			String insertSQL = "INSERT INTO user VALUES(?,?,?,?)";
			
			PreparedStatement ps = con.prepareStatement(insertSQL);
			ps.setString(1, username);
			ps.setString(2, email);
			ps.setString(3, password);
			ps.setString(4, mobileNumber);
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
}
