package com.vh.athena;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
//Path: http://localhost/<appln-folder-name>/location
@Path("/location")
public class Location {

	// HTTP Get Method
	@GET
	// Path: http://localhost/<appln-folder-name>/location/recordlocation
	@Path("/dorecord")
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON) 
	// Query parameters are parameters: http://localhost/<appln-folder-name>/location/dorecord?username=abc&password=xyz
	public String doRecord(@QueryParam("username") String uname, @QueryParam("longitude") double longitude, 
			@QueryParam("latitude") double latitude, @QueryParam("address") String address, @QueryParam("track_type") String track_type, 
			@QueryParam("track_em_id") String track_em_id, @QueryParam("country") String country, @QueryParam("locality") String locality){
		String response = "";
		if(storeLocation(uname, longitude, latitude, address, track_type, track_em_id, country, locality)){
			response = Utility.constructJSON("Record Location",true);
		}else{
			response = Utility.constructJSON("Record Location", false, "Error Occurred");    
		}
		return response;    
	}
	
    private boolean storeLocation(String uname, double longitude, double latitude, String address, String track_type, String track_em_id, String country, String locality){
        System.out.println("Inside storing location");
        boolean result = false;
        if(Utility.isNotNull(uname)){
            try {
                result = DBConnection.storeLocation(uname, longitude, latitude, address, track_type, track_em_id, country, locality);
                //System.out.println("Inside storing location  try "+result);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                //System.out.println("Inside checkCredentials catch");
                result = false;
            }
        }else{
            //System.out.println("Inside checkCredentials else");
            result = false;
        }
 
        return result;
    }
}
