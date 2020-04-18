package model;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Payments {

	// A common method to connect to the DB
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthcare",
					"root", "4212");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertPaymentDetails(String paymentId, String paidAmount, String paymentDescription, String Hid,
			String userId) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into payment_details(`paymentId`,`paidAmount`,`paymentDescription`,`Hid`,`userId`)"
					+ " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, paymentId);
			preparedStmt.setString(2, paidAmount);
			preparedStmt.setString(3, paymentDescription);
			preparedStmt.setString(4, Hid);
			preparedStmt.setString(5, userId);

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readPaymentDetails() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Payment ID</th><th>Paid Amount</th><th>Payment Description</th><th>Hospital ID</th><th>User ID</th><th>Update</th><th>Remove</th></tr>";
			String query = "select * from payment_details";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {

				String paymentId = rs.getString("paymentId");
				String paidAmount = Integer.toString(rs.getInt("paidAmount"));
				String paymentDescription = rs.getString("paymentDescription");
				String Hid = rs.getString("Hid");
				String userId = rs.getString("userId");

				// Add into the html table
				output += "<tr><td>" + paymentId + "</td>";
				output += "<td>" + paidAmount + "</td>";
				output += "<td>" + paymentDescription + "</td>";
				output += "<td>" + Hid + "</td>";
				output += "<td>" + userId + "</td>";
				// buttons
				output += "<td><input name=\"btnUpdate\" type=\"button\"value=\"Update\" class=\"btn btn-secondary\"></td>"
						+ "<td><form method=\"post\" action=\"Payments.jsp\">"
						+ "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\"class=\"btn btn-danger\">"
						+ "<input name=\"paymentId\" type=\"hidden\" value=\"" + paymentId + "\">"
						+ "</form></td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updatePaymentDetails(String paymentId, String paidAmount, String paymentDescription, String Hid,
			String userId) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE payment_details SET paidAmount=?,paymentDescription=?,Hid=?,userId=? WHERE paymentId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(paidAmount));
			preparedStmt.setString(2, paymentDescription);
			preparedStmt.setInt(3, Integer.parseInt(Hid));
			preparedStmt.setInt(4, Integer.parseInt(userId));
			preparedStmt.setString(5, paymentId);

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deletePaymentDetails(String paymentId) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from payment_details where paymentId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, paymentId);
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}

}
