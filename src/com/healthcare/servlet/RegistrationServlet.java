package com.healthcare.servlet;

import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.healthcare.model.User;
import com.healthcare.service.DBManager;
import com.healthcare.service.RegistrationService;
import com.healthcare.service.RegistrationServiceImp;

@Path("/user")
public class RegistrationServlet {

	RegistrationService registrationServiceObj = new RegistrationServiceImp();
	@GET
	@Path("/hello")
	@Produces(MediaType.TEXT_PLAIN)
	public String hello() {
		return "Hello";
	}
	
	@Path("/registerUser")
	@POST
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
	
	@Path("/getUserDetails")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String getUserDetails(@FormParam("User_Id")String User_Id) {
		
		User user = registrationServiceObj.getUserDetails(User_Id);
		
		if(user != null) {
			return user.getUsername();
		}else {
			return "fail";
		}
	}
	
}
