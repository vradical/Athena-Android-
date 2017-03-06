package com.vh.athena;

import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class EmergencyData {

	private String startTime;
	private String endTime;
    private int numOfTrack;
    private int emID;
    private String address;
    private String country;
    private String status;
    private String latitude;
    private String longitude;
    private String locality;
    private int tzOffset;
	private Timestamp dtStartTime;
	private Timestamp dtEndTime;
    
	public int getNumOfTrack() {
		return numOfTrack;
	}
	public void setNumOfTrack(int i) {
		this.numOfTrack = i;
	}
	public int getEmID() {
		return emID;
	}
	public void setEmID(int emID) {
		this.emID = emID;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	public int getTzOffset() {
		return tzOffset;
	}
	public void setTzOffset(int tzOffset) {
		this.tzOffset = tzOffset;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Timestamp getDtStartTime() {
		return dtStartTime;
	}
	public void setDtStartTime(Timestamp dtStartTime) {
		this.dtStartTime = dtStartTime;
	}
	public Timestamp getDtEndTime() {
		return dtEndTime;
	}
	public void setDtEndTime(Timestamp dtEndTime) {
		this.dtEndTime = dtEndTime;
	}

	
}
