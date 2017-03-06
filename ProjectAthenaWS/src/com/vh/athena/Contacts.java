package com.vh.athena;

import java.util.ArrayList;
import java.util.Collections;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

//Path: http://localhost/<appln-folder-name>/contacts
@Path("/contacts")
public class Contacts {

	// HTTP Get Method
	@GET
	// Path: http://localhost/<appln-folder-name>/location/recordlocation
	@Path("/addcontact")
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON)
	// Query parameters are parameters:
	// http://localhost/<appln-folder-name>/location/dorecord?username=abc&password=xyz
	public String doRecord(@QueryParam("username") String uname,
			@QueryParam("name") String contact_name,
			@QueryParam("email") String email,
			@QueryParam("country") String country, @QueryParam("phone") String phone) {
		String response = "";
		if (addContacts(uname, contact_name, email, country, phone)) {
			response = Utility.constructJSON("Add Contacts", true);
		} else {
			response = Utility.constructJSON("Add Contacts", false,
					"Error Occurred");
		}
		return response;
	}

	private boolean addContacts(String uname, String contact_name,
			String email, String country, String phone) {
		System.out.println("Inside adding contacts");
		boolean result = false;
		if (Utility.isNotNull(uname)) {
			try {
				result = DBConnection.addContact(uname, contact_name, email,
						country, phone);
				// System.out.println("Inside storing location  try "+result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// System.out.println("Inside checkCredentials catch");
				result = false;
			}
		} else {
			// System.out.println("Inside checkCredentials else");
			result = false;
		}

		return result;
	}

	@GET
	@Path("/getcontact")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<ContactData> getContactList(
			@QueryParam("username") String uname) {
		System.out.println("Inside getting contacts");
		ArrayList<ContactData> result;
		if (Utility.isNotNull(uname)) {
			try {
				result = DBConnection.getContact(uname);
				Collections.reverse(result);

			} catch (Exception e) {
				result = null;
			}
		} else {
			result = null;
		}

		return result;
	}

	// HTTP Get Method
	@GET
	// Path: http://localhost/<appln-folder-name>/location/recordlocation
	@Path("/deletecontact")
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON)
	// Query parameters are parameters:
	// http://localhost/<appln-folder-name>/location/dorecord?username=abc&password=xyz
	public String deleteRecord(@QueryParam("username") String uname,
			@QueryParam("email") String email, @QueryParam("phone") String phone) {
		String response = "";
		if (deleteContact(uname, email, phone)) {
			response = Utility.constructJSON("Delete Contacts", true);
		} else {
			response = Utility.constructJSON("Delete Contacts", false,
					"Error Occurred");
		}
		return response;
	}

	private boolean deleteContact(String uname, String email, String phone) {
		System.out.println("Inside adding contacts");
		boolean result = false;
		if (Utility.isNotNull(uname)) {
			try {
				result = DBConnection.deleteContact(uname, email, phone);
				// System.out.println("Inside storing location  try "+result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// System.out.println("Inside checkCredentials catch");
				result = false;
			}
		} else {
			// System.out.println("Inside checkCredentials else");
			result = false;
		}

		return result;
	}
}
