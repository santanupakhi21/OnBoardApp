package com.onboardapp.model;

public class ConfirmVehicledata {
	public String getBusHubRouteRef() {
		return BusHubRouteRef;
	}
	public void setBusHubRouteRef(String busHubRouteRef) {
		BusHubRouteRef = busHubRouteRef;
	}
	public String getJourneyCode() {
		return JourneyCode;
	}
	public void setJourneyCode(String journeyCode) {
		JourneyCode = journeyCode;
	}
	String BusHubRouteRef="";
	String JourneyCode="";
}
