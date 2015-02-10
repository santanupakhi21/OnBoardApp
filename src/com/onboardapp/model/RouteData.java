package com.onboardapp.model;

import java.util.ArrayList;

public class RouteData {

	public String getRouteName() {
		return RouteName;
	}
	public void setRouteName(String routeName) {
		RouteName = routeName;
	}
	String RouteName="";
	public ArrayList<ServiceModel>ListService=new ArrayList<ServiceModel>();
	
}
