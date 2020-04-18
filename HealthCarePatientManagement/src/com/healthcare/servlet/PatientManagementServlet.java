package com.healthcare.servlet;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;

import com.google.gson.JsonObject;
import com.healthcare.service.PatientManagementService;
import com.healthcare.service.PatientManagementServiceImp;

@Path("/patient")
@DeclareRoles({ "admin", "patient", "doctor" })
public class PatientManagementServlet {

	PatientManagementService patientManagementServiceObj = new PatientManagementServiceImp();

	@POST
	@Path("/registerUser")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public Response registerUser(@FormParam("firstName") String firstName, @FormParam("lastName") String lastName,
			@FormParam("age") String age, @FormParam("gender") String gender, @FormParam("address") String address,
			@FormParam("mobileNumber") String mobileNumber, @FormParam("email") String email,
			@FormParam("password") String password, @HeaderParam("authString") String authString) {

		if (AccessFilter.checkAccess(new String[] { "patient", "admin" }, authString)) {
			HashMap<String, String> h = patientManagementServiceObj.RegisterUser(firstName, lastName, age, gender,
					address, mobileNumber, email, password);
			return Response.ok(h.get("status").toString()).build();
		} else {
			return com.healthcare.model.Response.sendErrorResponse();
		}

	}

	@GET
	@Path("/userId/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserDetails(@PathParam("userId") String userId, @HeaderParam("authString") String authString) {

		if (AccessFilter.checkAccess(new String[] { "patient", "admin" }, authString)) {
			return Response.ok(patientManagementServiceObj.getUserDetails(userId).toString()).build();
		} else {
			return com.healthcare.model.Response.sendErrorResponse();
		}
	}

	@GET
	@Path("/loginId/{loginId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserDetailsByLoginId(@PathParam("loginId") String loginId,
			@HeaderParam("authString") String authString) {

		if (AccessFilter.checkAccess(new String[] { "admin", "patient", "doctor" }, authString)) {
			JsonObject user = patientManagementServiceObj.getUserDetailsByLoginId(loginId);
			return Response.ok(user.toString()).build();
		} else {
			return com.healthcare.model.Response.sendErrorResponse();
		}

	}

	@GET
	@Path("/getAllUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsers(@HeaderParam("authString") String authString) {

		if (AccessFilter.checkAccess(new String[] { "admin" }, authString)) {
			List<String> userList = patientManagementServiceObj.getAllUsers();
			JSONArray jsonArray = new JSONArray(userList);
			return Response.ok(jsonArray.toString()).build();
		} else {
			return com.healthcare.model.Response.sendErrorResponse();
		}
	}

	@DELETE
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUser(@PathParam("userId") String userId, @HeaderParam("authString") String authString) {

		if (AccessFilter.checkAccess(new String[] { "admin", "patient" }, authString)) {
			JsonObject user = patientManagementServiceObj.deleteUser(userId);
			return Response.ok(user.toString()).build();
		} else {
			return com.healthcare.model.Response.sendErrorResponse();
		}
	}

	@PUT
	@Path("/updateUser")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(@FormParam("userId") String userId, @FormParam("firstName") String firstName,
			@FormParam("lastName") String lastName, @FormParam("age") String age, @FormParam("gender") String gender,
			@FormParam("address") String address, @FormParam("mobileNumber") String mobileNumber,
			@FormParam("email") String email, @HeaderParam("authString") String authString) {

		if (AccessFilter.checkAccess(new String[] { "admin", "patient" }, authString)) {
			JsonObject user = patientManagementServiceObj.updateUser(userId, firstName, lastName, age, gender, address,
					mobileNumber, email);
			return Response.ok(user.toString()).build();
		} else {
			return com.healthcare.model.Response.sendErrorResponse();
		}

	}

	@GET
	@Path("/PatientCondition/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPatientCondition(@PathParam("userId") String userId,
			@HeaderParam("authString") String authString) {

		if (AccessFilter.checkAccess(new String[] { "admin", "patient", "doctor" }, authString)) {
			JsonObject patient = patientManagementServiceObj.getPatientCondition(userId);
			return Response.ok(patient.toString()).build();
		} else {
			return com.healthcare.model.Response.sendErrorResponse();
		}

	}

	@PUT
	@Path("/recordPatientCondition")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public Response recordPatientCondition(@FormParam("userId") String userId,
			@FormParam("patientCondition") String patientCondition, @HeaderParam("authString") String authString) {

		if (AccessFilter.checkAccess(new String[] { "admin", "doctor" }, authString)) {
			HashMap<String, String> h = patientManagementServiceObj.recordPatientCondition(userId, patientCondition);
			return Response.ok(h.get("status").toString()).build();
		} else {
			return com.healthcare.model.Response.sendErrorResponse();
		}

	}

	@PUT
	@Path("/assignToHospital")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public Response assignToHospital(@FormParam("userId") String userId, @FormParam("hospitalId") String hospitalId,
			@HeaderParam("authString") String authString) {

		if (AccessFilter.checkAccess(new String[] { "admin", "doctor" }, authString)) {
			HashMap<String, String> h = patientManagementServiceObj.assignToHospital(userId, hospitalId);
			return Response.ok(h.get("status").toString()).build();
		} else {
			return com.healthcare.model.Response.sendErrorResponse();
		}

	}

}
