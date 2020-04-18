package com;
import model.Appointment;

import java.sql.Date;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;
@Path("/Appointments")
public class AppointmentService
{
	Appointment appointmentObj = new Appointment();
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readAppointments()
	{
		return appointmentObj.readAppointments();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertAppointment(String data)
	{
		JsonObject itemObject = new JsonParser().parse(data).getAsJsonObject();
		//Read the values from the JSON object
		int appId = itemObject.get("appId").getAsInt();
		String appNo = itemObject.get("appNo").getAsString();
		String appDate = itemObject.get("appDate").getAsString();
		String appType = itemObject.get("appType").getAsString();
		String appDesc = itemObject.get("appDesc").getAsString();
		int appDocId = itemObject.get("appDocId").getAsInt(); 
		int appHospId = itemObject.get("appHospId").getAsInt(); 
		int appPatientId = itemObject.get("appPatientId").getAsInt(); 
		 
		String output = appointmentObj.insertAppointment(appId,appNo, appDate, appType, appDesc, appDocId, appHospId,appPatientId);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateAppointment(String data)
	{
		JsonObject itemObject = new JsonParser().parse(data).getAsJsonObject();
		//Read the values from the JSON object
		int appId = itemObject.get("appId").getAsInt();
	    String appDate = itemObject.get("appDate").getAsString();
		String appDesc = itemObject.get("appDesc").getAsString();
		int appDocId = itemObject.get("appDocId").getAsInt(); 
		int appHospId = itemObject.get("appHospId").getAsInt(); 
		 
		String output = appointmentObj.updateAppointment(appId, appDate, appDesc,  appDocId, appHospId);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteAppointment(String appData)
	{
		//Convert the input string to an XML document
		Document doc = Jsoup.parse(appData, "", Parser.xmlParser());
	
		//Read the value from the element <itemID>
		int appId = Integer.parseInt(doc.select("appId").text());
		String output = appointmentObj.deleteAppointment(appId);
		return output;
	}
} 

