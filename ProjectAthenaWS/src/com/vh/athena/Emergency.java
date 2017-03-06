package com.vh.athena;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

//Path: http://localhost/<appln-folder-name>/emergency
@Path("/emergency")
public class Emergency {

	@GET
	@Path("/createemid")
	@Produces(MediaType.APPLICATION_JSON)
	public String createEM(@QueryParam("username") String uname,
			@QueryParam("em_times") int emCount,
			@QueryParam("address") String address,
			@QueryParam("country") String country,
			@QueryParam("latitude") String latitude,
			@QueryParam("longitude") String longitude,
			@QueryParam("locality") String locality,
			@QueryParam("timezone") String timezone) {
		String response = "";
		if (createEMID(uname, emCount, address, country, latitude, longitude,
				locality, timezone)) {
			response = Utility.constructJSON("EMID", true);
		} else {
			response = Utility.constructJSON("EMID", false, "Error Occurred");
		}
		return response;
	}

	private boolean createEMID(String uname, int emCount, String address,
			String country, String latitude, String longitude, String locality, String timezone) {
		System.out.println("Inside create EMID");
		boolean result = false;
		if (Utility.isNotNull(uname)) {
			try {
				result = DBConnection.createEMID(uname, emCount, address,
						country, latitude, longitude, locality, timezone);
				// System.out.println("Inside checkCredentials try "+result);
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
	@Path("/getemcount")
	@Produces(MediaType.APPLICATION_JSON)
	public String getEmergencyCount(@QueryParam("username") String uname) {
		System.out.println("Inside getEmergencyCount");
		String response = "";
		if (Utility.isNotNull(uname)) {
			try {
				response = Utility.constructJSON("EMID",
						String.valueOf(DBConnection.getEmergencyCount(uname)));
			} catch (Exception e) {
				response = Utility.constructJSON("EMID", false,
						"Error Occurred");
			}
		} else {
			response = Utility.constructJSON("EMID", false, "Error Occurred");
		}
		return response;
	}

	@GET
	@Path("/endem")
	@Produces(MediaType.APPLICATION_JSON)
	public String completeEM(@QueryParam("username") String uname,
			@QueryParam("track_em_id") int emID,
			@QueryParam("emStatus") String emStatus) {
		String response = "";
		if (endEM(uname, emID, emStatus)) {
			response = Utility.constructJSON("EMID", true);
		} else {
			response = Utility.constructJSON("EMID", false, "Error Occurred");
		}
		return response;
	}

	private boolean endEM(String uname, int emID, String emStatus) {
		System.out.println("Inside create EMID");
		boolean result = false;
		if (Utility.isNotNull(uname)) {
			try {
				result = DBConnection.endEM(uname, emID, emStatus);
				// System.out.println("Inside checkCredentials try "+result);
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
	@Path("/getemergency")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<EmergencyData> getEmergency(
			@QueryParam("username") String uname) {
		System.out.println("Inside getting emergency list");
		ArrayList<EmergencyData> result;
		if (Utility.isNotNull(uname)) {
			try {
				result = DBConnection.getEmergency(uname);
				Collections.reverse(result);
				long epocST;
				long epocET;
				for(int i = 0; i < result.size(); i++){
					epocST = result.get(i).getDtStartTime().getTime() + result.get(i).getTzOffset();
					result.get(i).setStartTime(String.valueOf(new Timestamp(epocST)));
					
					if(result.get(i).getDtEndTime() != null){
					epocET = result.get(i).getDtEndTime().getTime() + result.get(i).getTzOffset();
					result.get(i).setEndTime(String.valueOf(new Timestamp(epocET)));
					}else{
						result.get(i).setEndTime("Not Available");
					}
				}
			} catch (Exception e) {
				result = null;
			}
		} else {
			result = null;
		}

		return result;
	}

	/*
	@GET
	@Path("/getcountryem")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<EmergencyData> getCountryEmergency() {
		System.out.println("Inside getting country emergency list");
		ArrayList<EmergencyData> result;
			try {
				result = DBConnection.getEmergencyAll();
			} catch (Exception e) {
				result = null;
			}
		return result;
	}*/

	@GET
	@Path("/gettrack")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<TrackData> getTrackList(
			@QueryParam("username") String uname,
			@QueryParam("track_em_id") int emID) {
		System.out.println("Inside getting track list");
		ArrayList<TrackData> result;
			try {
				result = DBConnection.getTrack(uname, emID);
			} catch (Exception e) {
				result = null;
			}
		return result;
	}

	@GET
	@Path("/sendmail")
	@Produces(MediaType.APPLICATION_JSON)
	public String sendEmergency1(@QueryParam("username") String uname,
			@QueryParam("name") String name,
			@QueryParam("country") String country,
			@QueryParam("address") String address,
			@QueryParam("latitude") String latitude,
			@QueryParam("longitude") String longitude) {
		System.out.println("Sending emergency list");
		String response = "";

		try {
			ArrayList<ContactData> contactList = DBConnection.getContact(uname);
			EmergencySendService em = new EmergencySendService();
			String failedList = "";
			for (int i = 0; i < contactList.size(); i++) {
				if (!em.sendEmail(contactList.get(i).getEmail(), name, country,
						address, latitude, longitude)) {
					failedList += contactList.get(i).getEmail() + " ";
				}
				;
			}
			response = Utility.constructJSON("EmailStatus", failedList);

		} catch (Exception e) {
			response = Utility.constructJSON("EmailStatus", "Error");
		}

		return response;
	}

	@GET
	@Path("/sendsms")
	@Produces(MediaType.APPLICATION_JSON)
	public String sendEmergency2(@QueryParam("username") String uname,
			@QueryParam("name") String name,
			@QueryParam("country") String country,
			@QueryParam("address") String address,
			@QueryParam("latitude") String latitude,
			@QueryParam("longitude") String longitude) {
		System.out.println("Sending emergency list");
		String response = "";

		try {
			ArrayList<ContactData> contactList = DBConnection.getContact(uname);

			EmergencySendService em = new EmergencySendService();

			for (int i = 0; i < contactList.size(); i++) {
				em.sendSMS(name, country, address,
						"+" + String.valueOf(contactList.get(i).getPhone()),
						latitude, longitude);
			}

		} catch (Exception e) {
			response = Utility.constructJSON("EMID", false, "Error Occurred");
		}

		return response;
	}

	@GET
	@Path("/changestatus")
	@Produces(MediaType.APPLICATION_JSON)
	public String changeStatus(@QueryParam("username") String uname,
			@QueryParam("track_em_id") int emID,
			@QueryParam("emStatus") String emStatus) {
		String response = "";
		if (changeEMStatus(uname, emID, emStatus)) {
			response = Utility.constructJSON("EMID", true);
		} else {
			response = Utility.constructJSON("EMID", false, "Error Occurred");
		}
		return response;
	}

	private boolean changeEMStatus(String uname, int emID, String emStatus) {
		System.out.println("Inside create EMID");
		boolean result = false;
		if (Utility.isNotNull(uname)) {
			try {
				result = DBConnection.changeEMStatus(uname, emID, emStatus);
				// System.out.println("Inside checkCredentials try "+result);
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
