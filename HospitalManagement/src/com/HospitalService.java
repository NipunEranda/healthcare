package com;

import model.Hospital;
//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;
@Path("/Hospital")
public class HospitalService
{
    Hospital hos= new Hospital();
    @GET
    @Path("/")
    @Produces(MediaType.TEXT_HTML)
    public String readHospital()
    {

        return hos.readHospital();
    }


    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String insertHospital(@FormParam("Hid") String Hid,
                                 @FormParam("Hname") String Hname,
                                 @FormParam("address") String address,
                                 @FormParam("telephone") String telephone)
    {
        String output = hos.insertHospital(Hid,Hname, address, telephone);

        return output;
    }


    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateHospital(String HData)
    {
        //Convert the input string to a JSON object
        JsonObject hos1 = new JsonParser().parse(HData).getAsJsonObject();
        //Read the values from the JSON object
        String HosCode = hos1.get("HosCode").getAsString();
        String Hid = hos1.get("Hid").getAsString();
        String Hname = hos1.get("Hname").getAsString();
        String address = hos1.get("address").getAsString();
        String telephone = hos1.get("telephone").getAsString();

        String output=hos.updateHospital(HosCode,Hid ,Hname ,address , telephone);

        return output;
    }

    @DELETE
    @Path("/")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteHospital(String HData)
    {
        //Convert the input string to an XML document
        Document doc = Jsoup.parse(HData, "", Parser.xmlParser());

        //Read the value from the element <HospitalID>
        String HosCode = doc.select("HosCode").text();
        String output = hos.deleteHospital(HosCode);
        return output;
    }
}
