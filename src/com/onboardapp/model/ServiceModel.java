package com.onboardapp.model;

import java.util.ArrayList;

public class ServiceModel {
	public String getServiceId() {
		return ServiceId;
	}
	public void setServiceId(String serviceId) {
		ServiceId = serviceId;
	}
	public String getRouteName() {
		return RouteName;
	}
	public void setRouteName(String routeName) {
		RouteName = routeName;
	}
	public String getOrigin() {
		return Origin;
	}
	public void setOrigin(String origin) {
		Origin = origin;
	}
	public String getDestination() {
		return Destination;
	}
	public void setDestination(String destination) {
		Destination = destination;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	String ServiceId="";
	String RouteName="";
	String Origin="";
	String Destination="";
	String Description="";
	public ArrayList<String>ListDescription=new ArrayList<String>();
	public ArrayList<BusHubRouteModel>ListBusHubRouteModel=new ArrayList<BusHubRouteModel>();
	public ArrayList<AdvertModel>ListBannerAd=new ArrayList<AdvertModel>();
	public ArrayList<AdvertModel>ListMPUAd=new ArrayList<AdvertModel>();
}
