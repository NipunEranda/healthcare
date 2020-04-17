package com.healthcare.servlet;

import java.util.HashMap;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.healthcare.service.PatientManagementService;
import com.healthcare.service.PatientManagementServiceImp;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Path("/patient")
public class PatientManagementServlet {

	PatientManagementService patientManagementServiceObj = new PatientManagementServiceImp();
	
	@POST
	@Path("/registerUser")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public Response registerUser(@FormParam("firstName") String firstName, @FormParam("lastName") String lastName, @FormParam("age") String age, @FormParam("gender") String gender,
			@FormParam("address") String address, @FormParam("mobileNumber") String mobileNumber, @FormParam("email") String email, @FormParam("password") String password){
		HashMap<String, String> h = patientManagementServiceObj.RegisterUser(firstName, lastName, age, gender, address, mobileNumber, email, password);
		return Response.ok(h.get("status").toString()).build();
	}
	
	@GET
	@Path("/userId/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserDetails(@PathParam("userId") String userId) {
		JsonObject user = patientManagementServiceObj.getUserDetails(userId);
		return Response.ok(user.toString()).build();
	}
	
	@GET
	@Path("/loginId/{loginId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserDetailsByLoginId(@PathParam("loginId") String loginId) {
		JsonObject user = patientManagementServiceObj.getUserDetailsByLoginId(loginId);
		return Response.ok(user.toString()).build();
	}

	@GET
	@Path("/getAllUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsers() {
		
		List<String> userList = patientManagementServiceObj.getAllUsers();
		
		JSONArray jsonArray = new JSONArray(userList);
		return Response.ok(jsonArray.toString()).build();
	}

	@DELETE
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUser(@PathParam("userId") String userId) {
		JsonObject user = patientManagementServiceObj.deleteUser(userId);
		return Response.ok(user.toString()).build();
	}
	
	@PUT
	@Path("/updateUser")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(@FormParam("userId") String userId,@FormParam("firstName") String firstName,@FormParam("lastName") String lastName,@FormParam("age") String age,@FormParam("gender") String gender,@FormParam("address") String address,@FormParam("mobileNumber") String mobileNumber,@FormParam("email") String email) {
		JsonObject user = patientManagementServiceObj.updateUser(userId, firstName, lastName, age, gender, address, mobileNumber,email);
		return Response.ok(user.toString()).build();
	}
	
	@GET
	@Path("/PatientCondition/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPatientCondition(@PathParam("userId") String userId) {
		JsonObject patient = patientManagementServiceObj.getPatientCondition(userId);
		return Response.ok(patient.toString()).build();
	}
	
	@PUT
	@Path("/recordPatientCondition")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public Response recordPatientCondition(@FormParam("userId") String userId, @FormParam("patientCondition") String patientCondition) {
		HashMap<String, String> h = patientManagementServiceObj.recordPatientCondition(userId, patientCondition);
		return Response.ok(h.get("status").toString()).build();
	}
	
	@PUT
	@Path("/assignToHospital")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public Response assignToHospital(@FormParam("userId") String userId, @FormParam("hospitalId") String hospitalId) {
		HashMap<String, String> h = patientManagementServiceObj.assignToHospital(userId, hospitalId);
		return Response.ok(h.get("status").toString()).build();
	}
	
}
