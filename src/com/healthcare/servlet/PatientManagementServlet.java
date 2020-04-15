package com.healthcare.servlet;

import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.healthcare.model.User;
import com.healthcare.service.PatientManagementService;
import com.healthcare.service.PatientManagementServiceImp;

@Path("/patient")
public class PatientManagementServlet {

	PatientManagementService patientManagementServiceObj = new PatientManagementServiceImp();
	
	@POST
	@Path("/registerUser")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String registerUser(@FormParam("username")String username, @FormParam("email")String email, @FormParam("password")String password, @FormParam("mobileNumber")String mobileNumber, @FormParam("address")String address){
		HashMap<String, String> h = patientManagementServiceObj.RegisterUser(username, email, password, mobileNumber, address);
		
		if(h.get("register").equalsIgnoreCase("success")) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String login(@FormParam("email") String email, @FormParam("password") String password, @FormParam("roleId") String roleId) {
		
		String userId;
		
		HashMap<String, String> h = patientManagementServiceObj.login(email, password, roleId);
		
		if(h.get("status").equalsIgnoreCase("success")) {
			userId = h.get("userId");
		}else {
			userId = null;
		}
		
		return userId;
	}

	@POST
	@Path("/getUserDetails")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserDetails(@FormParam("User_Id") String User_Id) {
		User user = patientManagementServiceObj.getUserDetails(User_Id);
		return user;
	}
	
	@POST
	@Path("/getUserLoginId")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String getUserLoginId(@FormParam("User_Id") String User_Id) {
		int loginId = patientManagementServiceObj.getUserLoginId(User_Id);
		return String.valueOf(loginId);
	}
	
	@GET
	@Path("/getAllUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUsers() {
		
		List<User> userList = patientManagementServiceObj.getAllUsers();
		return userList;
	}
	
	@POST
	@Path("/deleteUser")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public User deleteUser(@FormParam("User_Id") String User_Id) {
		User user = patientManagementServiceObj.deleteUser(User_Id);
		return user;
	}
	
	@POST
	@Path("/updateUser")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public User updateUser(@FormParam("User_Id") String User_Id, @FormParam("username")String username, @FormParam("email")String email, @FormParam("mobileNumber")String mobileNumber, @FormParam("address")String address) {
		User user = patientManagementServiceObj.updateUser(User_Id, username, email, mobileNumber, address);
		return user;
	}
	
	@POST
	@Path("/resetPassword")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String resetPassword(@FormParam("User_Id") String User_Id, @FormParam("currentPassword") String currentPassword, @FormParam("newPassword") String newPassword) {
		String status = patientManagementServiceObj.resetPassword(User_Id, currentPassword, newPassword);
		return status;
	}
	
	@POST
	@Path("/verifyPassword")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String verifyPassword(@FormParam("User_Id") String User_Id, @FormParam("currentPassword") String currentPassword) {
		String status = patientManagementServiceObj.verifyPassword(User_Id, currentPassword);
		return status;
	}
	
	@POST
	@Path("/getRoleName")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String getRoleName(@FormParam("role_Id") String role_Id) {
		String roleName = patientManagementServiceObj.getRoleName(role_Id);
		return roleName;
	}
	
}
