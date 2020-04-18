package model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.DriverManager;
 import java.sql.*;

    public class Hospital { //A common method to connect to the DB
        public Connection connect() throws Exception {

            Connection con=null;
            try {
                Class.forName("com.mysql.jdbc.Driver");

                //Provide the correct details: DBServer/DBName, username, password
                con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "ravindu", "ravindu");
            } catch (Exception e) {
                e.printStackTrace();
            }

            return con;
        }


        public String readHospital() {
            StringBuilder output;
            try {
                Connection con=connect();
                if (con == null) {
                    return "Error while connecting to the database for reading.";
                }
                // Prepare the html table to be displayed
                output=new StringBuilder("<table border=\"1\"><tr><th>Hospital ID</th>"
                        + "<th>Hospital Name</th><th>Hospital Address</th>"
                        +"<th>Hospital Tel</th>"
                        + "<th>Update</th><th>Remove</th></tr>");
                String query="select * from hospital";
                Statement stmt=con.createStatement();
                ResultSet rs=stmt.executeQuery(query);
                // iterate through the rows in the result set
                while (rs.next()) {
                    String Hid=Integer.toString(rs.getInt("Hid"));
                    String Hname=rs.getString("Hname");
                    String address=rs.getString("address");
                    String telephone=rs.getString("telephone");
                    // Add into the html table
                    output.append("<tr><td>").append(Hid).append("</td>");
                    output.append("<td>").append(Hname).append("</td>");
                    output.append("<td>").append(address).append("</td>");
                    output.append("<td>").append(telephone).append("</td>");
                    // buttons
                    output.append("<td><input name=\"btnUpdate\" " + " type=\"button\" value=\"Update\"></td>" +
                            "<td><form method=\"post\" action=\"index.jsp\">" +
                            "<input name=\"btnRemove\" " +
                            " type=\"submit\" value=\"Remove\"  class=\"btn btndanger\">" +
                            "<input name=\"Hid\" type=\"hidden\" " +
                            " value=\"").append(Hid).append("\">").append("</form></td></tr>");
                }
                con.close();
                // Complete the html table
                output.append("</table>");
            } catch (Exception e) {
                output=new StringBuilder("Error while reading the Hospital.");
                System.err.println(e.getMessage());
            }
            return output.toString();
        }


        public String insertHospital( String ID,String name, String address, String telephone)
        {
            String output = "";
            try
            {
                Connection con = connect();
                if (con == null)
                {return "Error while connecting to the database for inserting."; }
                // create a prepared statement
                String query=" insert into hospital ('HosCode',`Hid`,`Hname`,`address`,`telephone`)"
                        + " values (?, ?, ?, ?, ?)";
                PreparedStatement preparedStmt = con.prepareStatement(query);
                // binding values
                preparedStmt.setInt(1, 0);
                preparedStmt.setString(2, ID);
                preparedStmt.setString(3, name);
                preparedStmt.setString(4, address);
                preparedStmt.setString(5, telephone);

                // execute the statement
                preparedStmt.execute();
                con.close();
                output = "Inserted successfully";
            }
            catch (Exception e)
            {
                output = "Error while inserting the Hospital.";
                System.err.println(e.getMessage());
            }
            return output;
        }



        public String updateHospital(String code,String ID,String name, String address, String telephone) {
            String output="";
            try {
                Connection con=connect();
                if (con == null) {
                    return "Error while connecting to the database for updating.";
                }
                String query="UPDATE hospital SET Hid=?,Hname=?,address=? ,telephone=?  where HosCode=?";

                PreparedStatement preparedStmt = con.prepareStatement(query);
                // binding values
                preparedStmt.setString(1, ID);
                preparedStmt.setString(2, name);
                preparedStmt.setString(3, address);
                preparedStmt.setString(4, telephone);
                preparedStmt.setInt(5, Integer.parseInt(code));

                // execute the statement
                preparedStmt.execute();
                con.close();
                output = "Updated successfully";
            }
            catch (Exception e)
            {
                output = "Error while updating the Hospital.";
                System.err.println(e.getMessage());
            }
            return output;

        }

        public String deleteHospital(String HosCode) {
            String output;
            try {
                Connection con=connect();
                if (con == null) {
                    return "Error while connecting to the database for deleting.";
                }
                // create a prepared statement
                String query="delete from hospital where HosCode=?";
                PreparedStatement preparedStmt=con.prepareStatement(query);
                // binding values
                preparedStmt.setInt(1, Integer.parseInt(HosCode));
                // execute the statement
                preparedStmt.execute();
                con.close();
                output="Deleted successfully";
            } catch (Exception e) {
                output="Error while deleting the Hospital.";
                System.err.println(e.getMessage());
            }
            return output;
        }






}
