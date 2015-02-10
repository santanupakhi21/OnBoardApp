package com.onboardapp.model;

public class JourneyPatternModel {
	public String getAtcoCode() {
		return AtcoCode;
	}
	public void setAtcoCode(String atcoCode) {
		AtcoCode = atcoCode;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getLocality() {
		return Locality;
	}
	public void setLocality(String locality) {
		Locality = locality;
	}
	public String getBearing() {
		return Bearing;
	}
	public void setBearing(String bearing) {
		Bearing = bearing;
	}
	public String getIndicator() {
		return Indicator;
	}
	public void setIndicator(String indicator) {
		Indicator = indicator;
	}
	public String getLat() {
		return Lat;
	}
	public void setLat(String lat) {
		Lat = lat;
	}
	public String getLng() {
		return Lng;
	}
	public void setLng(String lng) {
		Lng = lng;
	}
	public String getTravelConnection() {
		return TravelConnection;
	}
	public void setTravelConnection(String travelConnection) {
		TravelConnection = travelConnection;
	}
	public String getStopAlerts() {
		return StopAlerts;
	}
	public void setStopAlerts(String stopAlerts) {
		StopAlerts = stopAlerts;
	}
	String AtcoCode="";
	String Name="";
	String Locality="";
	String Bearing="";
	String Indicator="";
	String Lat="";
	String Lng="";
	String TravelConnection="";
	String StopAlerts="";
	

}
