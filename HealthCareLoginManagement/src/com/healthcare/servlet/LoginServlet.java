package com.healthcare.servlet;


import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.healthcare.service.LoginService;
import com.healthcare.service.LoginServiceImp;

@Path("/login")
public class LoginServlet {

	LoginService loginServiceObj = new LoginServiceImp();
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String login(@FormParam("email") String email, @FormParam("password") String password, @FormParam("roleId") String roleId) {
		
		String userId;
		
		HashMap<String, String> h = loginServiceObj.login(email, password, roleId);
		
		if(h.get("status").equalsIgnoreCase("success")) {
			userId = h.get("userId");
		}else {
			userId = null;
		}
		
		return userId;
	}
	
	@POST
	@Path("/getUserLoginId")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String getUserLoginId(@FormParam("UserId") String User_Id) {
		int loginId = loginServiceObj.getUserLoginId(User_Id);
		return String.valueOf(loginId);
	}
	
	@POST
	@Path("/resetPassword")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String resetPassword(@FormParam("UserId") String User_Id, @FormParam("currentPassword") String currentPassword, @FormParam("newPassword") String newPassword) {
		String status = loginServiceObj.resetPassword(User_Id, currentPassword, newPassword);
		return status;
	}
	
	@POST
	@Path("/verifyPassword")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String verifyPassword(@FormParam("UserId") String User_Id, @FormParam("currentPassword") String currentPassword) {
		String status = loginServiceObj.verifyPassword(User_Id, currentPassword);
		return status;
	}
	
	@POST
	@Path("/getRoleName")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String getRoleName(@FormParam("roleId") String role_Id) {
		String roleName = loginServiceObj.getRoleName(role_Id);
		return roleName;
	}
	
}
