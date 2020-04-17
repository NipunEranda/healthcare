package com.healthcare.model;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class Response {

	public static JsonObject getResponse(String getUrl) {
		
		Client client = new Client().create();
		WebResource webResource = client.resource(getUrl);
		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
		if(response.getStatus()!=200){
            throw new RuntimeException("HTTP Error: "+ response.getStatus());
        }
		String result = response.getEntity(String.class);
		JsonObject userObject = new JsonParser().parse(result).getAsJsonObject();
		return userObject;
	}
	
}
