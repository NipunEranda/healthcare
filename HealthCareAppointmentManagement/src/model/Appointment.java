package model;
import java.sql.*;
public class Appointment
{ //A common method to connect to the DB
	private Connection connect(){
		 Connection con = null;
		 try
		 {
			 Class.forName("com.mysql.jdbc.Driver");
		
			 //Provide the correct details: DBServer/DBName, user name, password
			 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/healthcare", "root", "");
		 }
	 	 catch (Exception e)
		 {
		 	e.printStackTrace();
	 	 }
		 return con;
	}

	public String insertAppointment(int id, String no, String date, String type, String desc, int docId, int hospId, int patientId)
	{
		 String output = "";
		 try
		 {
			 Connection con = connect();
			 if (con == null)
			 {
				 return "Error while connecting to the database for inserting."; 
			 }
			 // create a prepared statement
			 String query = " insert into appointments (`appId`,`appNo`,`appDate`,`appType`,`appDesc`,`appDoctor`,`appHospital`,`appPatient`)"
			     + " values (?, ?, ?, ?, ?, ?, ?, ?)";
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 // binding values
			 preparedStmt.setInt(1, id);
			 preparedStmt.setString(2, no);
			 preparedStmt.setString(3, date);
			 preparedStmt.setString(4, type);
			 preparedStmt.setString(5, desc);
			 preparedStmt.setString(6, getDoctorNameById(docId)); 
			 preparedStmt.setString(7, getHospitalNameById(hospId)); 
			 preparedStmt.setString(8, getPatientNameById(patientId)); 
			 //execute the statement
			 preparedStmt.execute();
			 con.close();
			 output = "Inserted successfully";
		}
		catch (Exception e)
		{
			output = "Error while inserting the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readAppointments()
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for reading."; 
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Appointment no</th><th>Appointment Date</th><th>Appointment Type</th><th>Appointment desc</th><th>Doctor</th><th>Hospital</th><th>Patient</th></tr>";
			String query = "select * from appointments";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next())
			{
				String appId = Integer.toString(rs.getInt("appId"));
				String appNo = rs.getString("appNo");
				String appDate = rs.getDate("appDate").toString();
				String appType = rs.getString("appType");
				String appDesc = rs.getString("appDesc");
				String appDoctor = rs.getString("appDoctor");
				String appHospital = rs.getString("appHospital");
				String appPatient =  rs.getString("appPatient");
				
				// Add into the html table
				output += "<tr><td>" + appNo + "</td>";
				output += "<td>" + appDate + "</td>";
				output += "<td>" + appType + "</td>";
				output += "<td>" + appDesc + "</td>";
				output += "<td>" + appDoctor + "</td>";
				output += "<td>" + appHospital + "</td>";
				output += "<td>" + appPatient + "</td>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		}
		catch (Exception e)
		{
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		return output;
	} 
	
	public String updateAppointment(int appId, String appDate, String appDesc, int appDocId, int appHospId)
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE appointments SET appDate=? , appDesc=?, appDoctor=?, appHospital=? WHERE appId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, appDate);
			preparedStmt.setString(2, appDesc);
			preparedStmt.setInt(3, appDocId);
			preparedStmt.setInt(4, appHospId);
			preparedStmt.setInt(5, appId);
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		}
		catch (Exception e)
		{
			output = "Error while updating the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteAppointment(int appId)
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for deleting."; 
			}
			// create a prepared statement
			String query = "delete from appointments where appId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, appId);
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
		}
		catch (Exception e)
		{
		output = "Error while deleting the item.";
		System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String getDoctorNameById(int id) {
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for reading."; 
			}
			String query = "select * from doctor where docId = "+id;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next())
			{
				output = rs.getString("docName");
				
			}
			con.close();
		}
		catch (Exception e)
		{
			output = "empty";
		}
		return output;
	}
	
	public String getPatientNameById(int id) {
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for reading."; 
			}
			String query = "select * from patient where patientId = "+id;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next())
			{
				output = rs.getString("patientName");
				
			}
			con.close();
		}
		catch (Exception e)
		{
			output = "empty";
		}
		return output;
	}
	
	public String getHospitalNameById(int id) {
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for reading."; 
			}
			String query = "select * from hospital where hospId = "+id;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next())
			{
				output = rs.getString("hospName");
				
			}
			con.close();
		}
		catch (Exception e)
		{
			output = "empty";
		}
		return output;
	}
} 