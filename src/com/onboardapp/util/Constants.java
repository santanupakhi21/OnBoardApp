package com.onboardapp.util;

public class Constants {
	public static String statusOK="OK"; 
	public static String statusERROR="ERROR"; 
	
	public static final int LIST_VEHICLE=1;
	public static final int CONFIRM_VEHICLE=2;
	public static final int DOWNLOAD_ROUTE=3;
	
	public static final int CONNECT_GET=1;
	public static final int CONNECT_POST=2;
	public static final int CONNECT_PUT=3;
	
	public static  String LAT="51.459493";
	public static  String LNG="-0.494017";
	
	
	public static final String BASE_URL="http://dev.abellio.risedm.com";
	
//	public static final String URL_SERACH="http://dev.bushubapi.risedm.com/api/journeyplanner/townsuggestions?searchTerm=whiteley&guid=9202a029-e089-4e9b-ac9b-5010a60d460d";
	public static final String URL_LIST_VEHICLE=BASE_URL+"/api/onboard/vehicles";
	
	public static  String URL_CONFIRM_VEHICLE=BASE_URL+"/api/onboard/VehicleRoute?vehicleRef=";
	public static final String URL_DOWNLOAD_ROUTE=BASE_URL+"/api/onboard/CompanyServices";

	
}
