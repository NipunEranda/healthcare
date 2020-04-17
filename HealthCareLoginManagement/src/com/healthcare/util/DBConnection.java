package com.healthcare.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private static Connection connection;
	private static String DBDriver = "com.mysql.cj.jdbc.Driver";
	private static String DBUrl = "jdbc:mysql://localhost:3306/healthcare";
	private static String DBUser = "root";
	private static String DBPassword = "4212";

	private DBConnection() {

	}

	public static Connection connect() throws SQLException, ClassNotFoundException {
		Class.forName(DBDriver);
		Connection con = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
		return con;
	}

}