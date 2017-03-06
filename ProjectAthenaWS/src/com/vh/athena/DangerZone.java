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
@Path("/dangerzone")
public class DangerZone {

	@GET
	@Path("/adddz")
	@Produces(MediaType.APPLICATION_JSON)
	public String addDanger(@QueryParam("username") String uname,
			@QueryParam("title") String title,
			@QueryParam("addinfo") String addinfo,
			@QueryParam("latitude") String latitude,
			@QueryParam("longitude") String longitude) {
		String response = "";
		if (addDangerZone(uname, title, addinfo, latitude, longitude)) {
			response = Utility.constructJSON("EMID", true);
		} else {
			response = Utility.constructJSON("EMID", false, "Error Occurred");
		}
		return response;
	}

	private boolean addDangerZone(String uname, String title, String addinfo,
			String latitude, String longitude) {
		System.out.println("Inside create Danger Zone");
		boolean result = false;
		if (Utility.isNotNull(uname)) {
			try {
				result = DBConnection.addDZ(uname, title, addinfo, latitude,
						longitude);
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

	// HTTP Get Method
	@GET
	// Path: http://localhost/<appln-folder-name>/location/recordlocation
	@Path("/deletedz")
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON)
	// Query parameters are parameters:
	// http://localhost/<appln-folder-name>/location/dorecord?username=abc&password=xyz
	public String deleteRecord(@QueryParam("username") String uname,
			@QueryParam("latitude") String latitude,
			@QueryParam("longitude") String longitude) {
		String response = "";
		if (deleteDangerZone(uname, latitude, longitude)) {
			response = Utility.constructJSON("Delete DZ", true);
		} else {
			response = Utility.constructJSON("Delete DZ", false,
					"Error Occurred");
		}
		return response;
	}

	private boolean deleteDangerZone(String uname, String latitude,
			String longitude) {
		System.out.println("Inside adding contacts");
		boolean result = false;
		if (Utility.isNotNull(uname)) {
			try {
				result = DBConnection.deleteDZ(uname, latitude, longitude);
			} catch (Exception e) {
				result = false;
			}
		} else {
			result = false;
		}
		return result;
	}

	@GET
	@Path("/getdz")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<DangerZoneData> getDZ(@QueryParam("username") String uname) {
		System.out.println("Inside getting danger zone list");
		ArrayList<DangerZoneData> result;
		if (Utility.isNotNull(uname)) {
			try {
				result = DBConnection.getDZ(uname);
				Collections.reverse(result);
			} catch (Exception e) {
				result = null;
			}
		} else {
			result = null;
		}
		return result;
	}

	@GET
	@Path("/reportdz")
	@Produces(MediaType.APPLICATION_JSON)
	public String reportDZ(@QueryParam("username") String uname, @QueryParam("dz_id") String dz_id, @QueryParam("type") String type, @QueryParam("detail") String detail) {
		String response = "";
		if (reportDangerZone(uname, dz_id, type, detail)) {
			response = Utility.constructJSON("EMID", true);
		} else {
			response = Utility.constructJSON("EMID", false, "Report exist");
		}
		return response;
	}

	private boolean reportDangerZone(String uname, String dz_id, String type,
			String detail) {
		System.out.println("Inside create Danger Zone");
		boolean result = false;
		if (Utility.isNotNull(uname)) {
			try {
				result = DBConnection.reportDZ(uname, dz_id, type, detail);
			} catch (Exception e) {
				result = false;
			}
		} else {
			result = false;
		}

		return result;
	}

	@GET
	@Path("/getspecialz")
	@Produces(MediaType.APPLICATION_JSON) 
    public ArrayList<SpecialZoneData> getSZ(@QueryParam("latitude") String latitude, @QueryParam("longitude") String longitude, @QueryParam("distance") String distance){
        System.out.println("Inside getting special zone list");
        ArrayList<DangerZoneData> dangerList;
        ArrayList<EmergencyData> emergencyList;
        ArrayList<SpecialZoneData> specialList = new ArrayList<SpecialZoneData>();
        ArrayList<SpecialZoneData> selectedList = new ArrayList<SpecialZoneData>();
        if(Utility.isNotNull(latitude) && Utility.isNotNull(longitude)){
            try {
                double resultLat;
                double resultLng;
                dangerList = DBConnection.getDZAll();   
                emergencyList = DBConnection.getEmergencyAll();
                long epocST;

                for(int i = 0; i < dangerList.size(); i++){
                	DangerZoneData dz = dangerList.get(i);
                	specialList.add(new SpecialZoneData(dz.getDz_id(), dz.getTitle(), dz.getAddinfo(), "Danger", dz.getLatitude(),
                			dz.getLongitude(), dz.getDateTime()));
                }
                
                for(int i = 0; i < emergencyList.size(); i++){
                	EmergencyData ed = emergencyList.get(i);
					epocST = ed.getDtStartTime().getTime() + ed.getTzOffset();
                	specialList.add(new SpecialZoneData("null", "null", "null", "Emergency", ed.getLatitude(), ed.getLongitude(), String.valueOf(new Timestamp(epocST))));
                }
                
                System.out.println(specialList.size());
                
                for(int i = 0; i < specialList.size(); i++){
                	resultLat = Double.parseDouble(specialList.get(i).getLatitude());
                	resultLng = Double.parseDouble(specialList.get(i).getLongitude());
                	double distanceBetween = Haversine.calculateDistance(Double.parseDouble(latitude), Double.parseDouble(longitude), resultLat, resultLng);
                	if(distanceBetween <= Integer.parseInt(distance)){
                		selectedList.add(specialList.get(i));
                	}
                }
                
                System.out.println(specialList.size());
            } catch (Exception e) {
            	selectedList = null;
            }
        }else{
        	selectedList = null;
        }
        return selectedList;
    }
}
