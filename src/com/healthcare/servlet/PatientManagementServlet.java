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

@Path("/user")
public class PatientManagementServlet {

	PatientManagementService registrationServiceObj = new PatientManagementServiceImp();
	
	@POST
	@Path("/registerUser")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String registerUser(@FormParam("username")String username, @FormParam("email")String email, @FormParam("password")String password, @FormParam("mobileNumber")String mobileNumber, @FormParam("address")String address){
		HashMap<String, String> h = registrationServiceObj.RegisterUser(username, email, password, mobileNumber, address);
		
		if(h.get("register").equalsIgnoreCase("success")) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	@POST
	@Path("/getUserDetails")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserDetails(@FormParam("User_Id") String User_Id) {
		User user = registrationServiceObj.getUserDetails(User_Id);
		return user;
	}
	
	@POST
	@Path("/getUserLoginId")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String getUserLoginId(@FormParam("User_Id") String User_Id) {
		int loginId = registrationServiceObj.getUserLoginId(User_Id);
		return String.valueOf(loginId);
	}
	
	@GET
	@Path("/getAllUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUsers() {
		
		List<User> userList = registrationServiceObj.getAllUsers();
		return userList;
	}
	
	@POST
	@Path("/deleteUser")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public User deleteUser(@FormParam("User_Id") String User_Id) {
		User user = registrationServiceObj.deleteUser(User_Id);
		return user;
	}
	
	@POST
	@Path("/updateUser")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public User updateUser(@FormParam("User_Id") String User_Id, @FormParam("username")String username, @FormParam("email")String email, @FormParam("mobileNumber")String mobileNumber, @FormParam("address")String address) {
		User user = registrationServiceObj.updateUser(User_Id, username, email, mobileNumber, address);
		return user;
	}
	
	@POST
	@Path("/resetPassword")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String resetPassword(@FormParam("User_Id") String User_Id, @FormParam("currentPassword") String currentPassword, @FormParam("newPassword") String newPassword) {
		String status = registrationServiceObj.resetPassword(User_Id, currentPassword, newPassword);
		return status;
	}
	
	@POST
	@Path("/verifyPassword")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String verifyPassword(@FormParam("User_Id") String User_Id, @FormParam("currentPassword") String currentPassword) {
		String status = registrationServiceObj.verifyPassword(User_Id, currentPassword);
		return status;
	}
	
}
