package com.healthcare.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Set;

import com.healthcare.util.DBConnection;

public class Authentication {
	
	public static boolean isUserAllowed(final String username, final String password, final Set<String> rolesSet) {

        boolean isAllowed = false;
        String role = "" , user = "", pswd = "" ;

        try {

            Connection con = DBConnection.connect();

            if (con == null) {

            }

            // creating prepared statements
            String queryUser = " SELECT * FROM user where username = '" + username + "' and password = '" + password + "' ";
            String queryHospital = " SELECT * FROM hospital where hospitalUsername = '" + username + "' and hospitalPassword = '" + password + "' ";
            String queryDoctor = " SELECT * FROM doctor where doctorUsername = '" + username + "' and doctorPassword = '" + password + "' ";

            PreparedStatement preparedStmtUser = con.prepareStatement(queryUser);
            PreparedStatement preparedStmtHospital = con.prepareStatement(queryHospital);
            PreparedStatement preparedStmtDoctor = con.prepareStatement(queryDoctor);

            preparedStmtUser.execute();
            preparedStmtHospital.execute();
            preparedStmtDoctor.execute();

            //executing the statements
            ResultSet rsUser = preparedStmtUser.executeQuery(queryUser);
            ResultSet rsHospital = preparedStmtHospital.executeQuery(queryHospital);
            ResultSet rsDoctor = preparedStmtDoctor.executeQuery(queryDoctor);

            if(rsUser.next()) {

                user = rsUser.getString("username");
                pswd = rsUser.getString("password");
                role= rsUser.getString("role");

            }

            if(rsHospital.next()) {

                user = rsHospital.getString("hospitalUsername");
                pswd = rsHospital.getString("hospitalPassword");
                role= rsHospital.getString("role");

            }

            if(rsDoctor.next()) {

                user = rsDoctor.getString("doctorUsername");
                pswd = rsDoctor.getString("doctorPassword");
                role= rsDoctor.getString("role");

            }

            con.close();

        } catch (Exception e) {

            System.err.println(e.getMessage());

        }

        System.out.println(user +" "+ pswd);

        if(username.equals(user) && password.equals(pswd)) {

            if(rolesSet.contains(role)) {

                isAllowed = true;

            }

        }

        return isAllowed;

    }

}
