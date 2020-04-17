package com.healthcare.servlet;


import java.util.HashMap;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.healthcare.service.LoginService;
import com.healthcare.service.LoginServiceImp;

@Path("/login")
public class LoginServlet {

	LoginService loginServiceObj = new LoginServiceImp();
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@FormParam("email") String email, @FormParam("password") String password, @FormParam("roleId") String roleId) {
		
		String userId;
		
		HashMap<String, String> h = loginServiceObj.login(email, password, roleId);
		
		if(h.get("status").equalsIgnoreCase("success")) {
			userId = h.get("userId");
		}else {
			userId = null;
		}
		return Response.ok(userId).build();
	}
	
	@GET
	@Path("/getUserLoginId/{UserId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserLoginId(@PathParam("UserId") String User_Id) {
		int loginId = loginServiceObj.getUserLoginId(User_Id);
		return Response.ok(String.valueOf(loginId)).build();
	}
	
	@POST
	@Path("/resetPassword")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response resetPassword(@FormParam("UserId") String User_Id, @FormParam("currentPassword") String currentPassword, @FormParam("newPassword") String newPassword) {
		String status = loginServiceObj.resetPassword(User_Id, currentPassword, newPassword);
		return Response.ok(status).build();
	}
	
	@POST
	@Path("/verifyPassword")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response verifyPassword(@FormParam("UserId") String User_Id, @FormParam("currentPassword") String currentPassword) {
		String status = loginServiceObj.verifyPassword(User_Id, currentPassword);
		return Response.ok(status).build();
	}
	
	@GET
	@Path("/getRoleName/{roleId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRoleName(@PathParam("roleId") String roleId) {
		String roleName = loginServiceObj.getRoleName(roleId);
		return Response.ok(roleName).build();
	}
	
}
