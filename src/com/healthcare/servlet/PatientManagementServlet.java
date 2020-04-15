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
	@Path("/getUserDetails")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserDetails(@FormParam("User_Id") String User_Id) {
		User user = patientManagementServiceObj.getUserDetails(User_Id);
		return user;
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
	
}
