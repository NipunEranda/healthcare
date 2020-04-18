package com.healthCare.Servlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;

import com.healthCare.Service.DoctorManageService;
import com.healthCare.Service.DoctorManagementServiceImp;
import com.healthCare.model.*;

@Path("/doctor")
public class DoctorManagementServlet {

	DoctorManageService DoctorServiceObj = new DoctorManagementServiceImp();
	
	@POST
	@Path("/registerUser")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String registerUser(@FormParam("firstName") String firstName, @FormParam("lastName") String lastName,
			@FormParam("idnum") String idnum, @FormParam("gender") String gender, @FormParam("address") String address,
			@FormParam("mobileNumber") String mobileNumber, @FormParam("workplace") String workplace,
			@FormParam("degree") String degree, @FormParam("email") String email,
			@FormParam("password") String password) {
		HashMap<String, String> h = DoctorServiceObj.RegisterUser(firstName, lastName, idnum, gender, address,
				mobileNumber, workplace, degree, email, password);

		if (h.get("register").equalsIgnoreCase("successfully")) {
			return "successfully";
		} else {
			return "fail";
		}
	}

	@POST
	@Path("/getUserDetails")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public DoctorUser getUserDetails(@FormParam("userId") String Did) {
		DoctorUser user = DoctorServiceObj.getUserDetails(Did);
		return user;
	}

	@GET
	@Path("/getAllUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DoctorUser> getAllUsers() {

		List<DoctorUser> userList = DoctorServiceObj.getAllUsers();
		return userList;
	}

	@DELETE
	@Path("/deleteUser")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public DoctorUser deleteUser(@FormParam("userId") String Did) {
		DoctorUser user = DoctorServiceObj.deleteUser(Did);
		return user;
	}

	@PUT
	@Path("/updateUser")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public DoctorUser updateUser(@FormParam("userId") String Did, @FormParam("firstName") String firstName,
			@FormParam("lastName") String lastName, @FormParam("idnum") String idnum,
			@FormParam("gender") String gender, @FormParam("address") String address,
			@FormParam("mobileNumber") String mobileNumber, @FormParam("workplace") String workplace,
			@FormParam("degree") String degree, @FormParam("email") String email) {
		DoctorUser user = DoctorServiceObj.updateUser(Did, firstName, lastName, idnum, gender, address, mobileNumber,
				workplace, degree, email);
		return user;
	}
}
