package com.healthcare.servlet;

import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.healthcare.model.User;
import com.healthcare.service.PatientManagementService;
import com.healthcare.service.PatientManagementServiceImp;
import com.sun.jersey.spi.container.ParamQualifier;

@Path("/patient")
public class PatientManagementServlet {

	PatientManagementService patientManagementServiceObj = new PatientManagementServiceImp();
	
	@POST
	@Path("/registerUser")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String registerUser(@FormParam("firstName") String firstName, @FormParam("lastName") String lastName, @FormParam("age") String age, @FormParam("gender") String gender,
			@FormParam("address") String address, @FormParam("mobileNumber") String mobileNumber, @FormParam("email") String email, @FormParam("password") String password){
		HashMap<String, String> h = patientManagementServiceObj.RegisterUser(firstName, lastName, age, gender, address, mobileNumber, email, password);
		
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
	public User getUserDetails(@FormParam("userId") String userId) {
		User user = patientManagementServiceObj.getUserDetails(userId);
		return user;
	}
	
	@GET
	@Path("/getAllUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUsers() {
		
		List<User> userList = patientManagementServiceObj.getAllUsers();
		return userList;
	}
	
	@DELETE
	@Path("/deleteUser")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public User deleteUser(@FormParam("userId") String userId) {
		User user = patientManagementServiceObj.deleteUser(userId);
		return user;
	}
	
	@PUT
	@Path("/updateUser")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public User updateUser(@FormParam("userId") String userId,@FormParam("firstName") String firstName,@FormParam("lastName") String lastName,@FormParam("age") String age,@FormParam("gender") String gender,@FormParam("address") String address,@FormParam("mobileNumber") String mobileNumber,@FormParam("email") String email) {
		User user = patientManagementServiceObj.updateUser(userId, firstName, lastName, age, gender, address, mobileNumber,email);
		return user;
	}
	
}
