package com;

import model.Payments;
//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Payments")
public class PayService {

	Payments paymentObj = new Payments();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readPaymentDetails() {
		return paymentObj.readPaymentDetails();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertPaymentDetails(@FormParam("paymentId") String paymentId,
			@FormParam("paidAmount") String paidAmount, @FormParam("paymentDescription") String paymentDescription,
			@FormParam("Hid") String Hid, @FormParam("userId") String userId) {
		String output = paymentObj.insertPaymentDetails(paymentId, paidAmount, paymentDescription, Hid, userId);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePaymentDetails(String paymentData) {

		// Convert the input string to a JSON object
		JsonObject paymentObject = new JsonParser().parse(paymentData).getAsJsonObject();

		// Read the values from the JSON object
		String paymentId = paymentObject.get("paymentId").getAsString();
		String paidAmount = paymentObject.get("paidAmount").getAsString();
		String paymentDescription = paymentObject.get("paymentDescription").getAsString();
		String Hid = paymentObject.get("Hid").getAsString();
		String userId = paymentObject.get("userId").getAsString();

		String output = paymentObj.updatePaymentDetails(paymentId, paidAmount, paymentDescription, Hid, userId);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePaymentDetails(String paymentData) {

		// Convert the input string to an XML document
		Document doc = Jsoup.parse(paymentData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String paymentId = doc.select("paymentId").text();
		String output = paymentObj.deletePaymentDetails(paymentId);
		return output;
	}

}
