package com.healthCare.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.healthCare.model.DoctorUser;
import com.healthCare.model.Login;



public class DB {
	
	static final  String DBDriver = "com.mysql.cj.jdbc.Driver";
	static final  String DBUrl = "jdbc:mysql://localhost:3306/healthcare";
	static final  String DBUser = "root";
	static final  String DBPassword ="4212";
	
	static {
		try {
			Class.forName(DBDriver);
			
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
			
		}
	}
	
	public static int registerUser(String firstName, String lastName, String idnum, String gender, String address,  String workplace, String degree ,String mobileNumber,String email, String password  ) {
		int a = 0;
		int b= 0;
		
		try {
			Connection con = DriverManager.getConnection(DBUrl,DBUser,DBPassword);
			
			String insertSQL1 = "INSERT INTO login VALUES(?, ?, ?, ?)";
			String insertSQL2 = "INSERT INTO user VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			String emailVerification  = "SELECT * FROM login WHERE LOgin_email = ?";
			String getUId = "SELECT login_Id FROM login WHERE login_email = ?";
			
			PreparedStatement ps_emailVerification = con.prepareStatement(emailVerification);
			ps_emailVerification.setString(1, email);
			
			ResultSet rs1 = ps_emailVerification.executeQuery();
			if(rs1.first() == false) {
				PreparedStatement ps_insertLogin = con.prepareStatement(insertSQL2);
				ps_insertLogin.setInt(1, 0);
				ps_insertLogin.setInt(2, 2);
				ps_insertLogin.setString(3, email);
				ps_insertLogin.setString(4, password);
				a = ps_insertLogin.executeUpdate();
				
				PreparedStatement ps_getDId = con.prepareStatement(getUId);
				ps_getDId.setString(1, email);
				
				ResultSet rs2 = ps_getDId.executeQuery();
				
				while (rs2.next()) {
					PreparedStatement ps_insertUser = con.prepareStatement(insertSQL2);
					ps_insertUser.setInt(1, 0);
					ps_insertUser.setInt(2, rs2.getInt(1));
					ps_insertUser.setString(3, firstName);
					ps_insertUser.setString(4, lastName);
					ps_insertUser.setString(5, idnum);
					ps_insertUser.setString(6, gender);
					ps_insertUser.setString(7, address);
					ps_insertUser.setString(8, mobileNumber);
					ps_insertUser.setString(9, workplace);
					ps_insertUser.setString(10, degree);
					b = ps_insertUser.executeUpdate();
					
					if(b<0)
					{
						a = 0;
				}
			}
			
		} else {
			a =0;
		}
					
					
				} catch(SQLException e) {
					e.printStackTrace();
					
		}
				return a;
}

 
 public static int getLoginId(String Did) {
	 int loginId = 0;
	 try {
		 
		 Connection con = DriverManager.getConnection(DBUrl,DBUser,DBPassword);
		 String getLoginId = "SELECT Login_id FROM user WHERE Do_Id = "+Did;
		 PreparedStatement ps_getLoginId = con.prepareStatement(getLoginId);
		 ResultSet rs_getLoginId = ps_getLoginId.executeQuery();
		 
		 while(rs_getLoginId.next()) {
			 loginId = rs_getLoginId.getInt(1);
			 
		 }
	 }catch (Exception e) {
		 e.printStackTrace();
		// TODO: handle exception
	}
	 return loginId;
	 }

 public static DoctorUser getUserDetails(String Did) {
	 DoctorUser user = null;
	 try {
		Connection con = DriverManager.getConnection(DBUrl,DBUser,DBPassword);
		String getSql = "SELECT u.loginId, u.loginId, u.firstName,u.lastName,u.idnum,u.gender, u.address, u.mobileNumber, u.workplace, u.degree, l.Login_Email, l.Login_Role FROM user u, login l WHERE u.Did = ? AND u.loginId = l.Login_Id";
		PreparedStatement ps_getUserDetails = con.prepareStatement(getSql);
		ps_getUserDetails.setInt(1, Integer.parseInt(Did));
		
		ResultSet rs = ps_getUserDetails.executeQuery();
		
		while(rs.next()) {
			
			Login login = new Login(rs.getInt(1), rs.getInt(9), rs.getString(8), null);
			user = new DoctorUser(Integer.parseInt(Did), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),rs.getString(8),rs.getString(9), login);
		}
	} catch ( Exception e) {
		e.printStackTrace();
		// TODO: handle exception
	}
	 return user;
}
 
 public static DoctorUser getUserDetailsByLoginId(String loginId) {
	 DoctorUser user = null;
	 try
	 {
		 Connection con = DriverManager.getConnection(DBUrl,DBUser,DBPassword);
		 String getSql = "SELECT u.userId, u.firstName, u.lastName, u.idnum, u.gender, u.address, u.mobileNumber, u.workplace, u.degree, l.Login_Email, l.Login_Role FROM user u, login l WHERE u.loginId = ? AND u.Login_id = l.Login_Id";
		 PreparedStatement ps_getUserDetails = con.prepareStatement(getSql);
		 ps_getUserDetails.setInt(1, Integer.parseInt(loginId));;
		 
		 ResultSet rs = ps_getUserDetails.executeQuery();
		 
		 while(rs.next()) {
			 Login login = new Login(Integer.parseInt(loginId), rs.getInt(9), rs.getString(8), null);
			 user = new DoctorUser(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),rs.getString(8),rs.getString(9), login);
			 
		 }
	 } catch (Exception e) {
			 e.printStackTrace();
			// TODO: handle exception
		}
		 return user;
	 }
	 
	 public static ArrayList<DoctorUser> getAllUser(){
		 ArrayList<DoctorUser>allUser = new ArrayList<>();
		 try {

				Connection con = DriverManager.getConnection(DBUrl, DBUser, DBPassword);

				String getSql = "SELECT u.userId, u.loginId, u.firstName, u.lastName, u.age, u.gender, u.address, u.mobileNumber,u.workplace, u.degree, l.Login_Email, l.Login_Role FROM user u, login l WHERE u.Login_id = l.Login_Id";

				PreparedStatement ps_getUserDetails = con.prepareStatement(getSql);

				ResultSet rs = ps_getUserDetails.executeQuery();

				while (rs.next()) {
					Login login = new Login(rs.getInt(2), rs.getInt(10), rs.getString(9), null);
					DoctorUser user = new DoctorUser(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),rs.getString(8),rs.getString(9), login);
					allUser.add(user);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return allUser;

		}
	 
	 public static DoctorUser deleteUser(String Did) {
			DoctorUser user = null;
			try {
				user = getUserDetails(Did);
				int loginId = getLoginId(Did);
				Connection con = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
				String deleteFromUser = "DELETE FROM user WHERE User_Id = " + Did;

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
		 
	 public static DoctorUser updateUser(String Did, String firstName, String lastName, String idnum, String gender, String address, String mobileNumber, String workplace, String degree, String email) {
			DoctorUser user = null;
			try {

				int loginId = getLoginId(Did);
				Connection con = DriverManager.getConnection(DBUrl, DBUser, DBPassword);

				String updateUserTable = "UPDATE user SET firstName = ?, lastName = ?, idnum = ?, gender = ?, address = ?, mobileNumber = ?, workplace = ?, degree = ? WHERE User_Id = ?";
				String updateLoginTable = "UPDATE login SET Login_Email = ? WHERE Login_Id = ?";

				PreparedStatement ps_updateUserTable = con.prepareStatement(updateUserTable);
				ps_updateUserTable.setString(1, firstName);
				ps_updateUserTable.setString(2, lastName);
				ps_updateUserTable.setString(3, idnum);
				ps_updateUserTable.setString(4, gender);
				ps_updateUserTable.setString(5, address);
				ps_updateUserTable.setString(6, mobileNumber);
				ps_updateUserTable.setString(7, workplace);
				ps_updateUserTable.setString(8, degree);
				ps_updateUserTable.setInt(9, Integer.parseInt(Did));

				PreparedStatement ps_updateLoginTable = con.prepareStatement(updateLoginTable);
				ps_updateLoginTable.setString(1, email);
				ps_updateLoginTable.setInt(2, loginId);

				if (ps_updateUserTable.executeUpdate() > 0) {

					if (ps_updateLoginTable.executeUpdate() > 0) {
						user = getUserDetails(Did);
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return user;
		}

	}
	 
 
