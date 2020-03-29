package com.healthcare.model;

import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.healthcare.service.DBManager;

@Path("/user")
public class User {

	@Path("/register")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public HashMap<String, String> getUserRegister(@FormParam("username")String username, @FormParam("email")String email, @FormParam("password")String password, @FormParam("mobileNumber")String mobileNumber){
		
		HashMap<String, String> h = new HashMap<>();
		
		int i = DBManager.getUser(username, email, password, mobileNumber);
		
		if(i > 0) {
			h.put("register", "success");
		}else {
			h.put("register", "failed");
		}
		
		return h;
	}
	
	
}
