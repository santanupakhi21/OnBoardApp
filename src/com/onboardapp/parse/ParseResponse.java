package com.onboardapp.parse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.onboardapp.model.VehicleData;
import com.onboardapp.result.Result;
import com.onboardapp.result.ResultConfirmVehicleData;
import com.onboardapp.result.ResultVehicleData;
import com.onboardapp.util.Constants;




public class ParseResponse {
	public ParseResponse(int type, String response) {
		super();
		this.type = type;
		Response = response;
	}
	int type;
	String Response;
	Result result=new Result();
	public Result parseData()
	{
		switch (type) {
		case Constants.LIST_VEHICLE:
			parseVehicle();
			break;
		case Constants.CONFIRM_VEHICLE:
			parseConfirmVehicle();
			break;
		case Constants.DOWNLOAD_ROUTE:
			parseDownloadRoute();
			break;
		default:
			break;
		}
		
		
		return result;
	}
	
	

	public void parseVehicle()
	{
		try {
			ResultVehicleData Rvehicle=new ResultVehicleData();
			JSONObject obj=new JSONObject(Response);
			JSONArray jArray=obj.getJSONArray("Vehicle");
			for(int i=0;i<jArray.length();i++)
			{
				VehicleData vehicle=new VehicleData();
				vehicle.setVehicleref(jArray.getJSONObject(i).optString("VehicleRef"));
				Rvehicle.ListVehicle.add(vehicle);
			}
			
			
			result=(Result)Rvehicle;
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void parseDownloadRoute()
	{
		try {
			
//			{"BusHubRouteRef":"557-1","Direction":"0",�JourneyCode�:�1045�,"RouteName":"557","LastRecorded":"2015-02-02T18:12:23",
//				"LastLocation":{"Latitude":"51.424982","Longitude":"-0.435072"}}

			ResultConfirmVehicleData RCvehicle=new ResultConfirmVehicleData();
			JSONObject obj=new JSONObject(Response);
			RCvehicle.cVehicleData.setBusHubRouteRef(obj.optString("BusHubRouteRef"));
			RCvehicle.cVehicleData.setJourneyCode(obj.optString("JourneyCode"));
			
			
			
			
			result=(Result)RCvehicle;
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void parseConfirmVehicle()
	{
		try {
			
//			{"BusHubRouteRef":"557-1","Direction":"0",�JourneyCode�:�1045�,"RouteName":"557","LastRecorded":"2015-02-02T18:12:23",
//				"LastLocation":{"Latitude":"51.424982","Longitude":"-0.435072"}}

			ResultConfirmVehicleData RCvehicle=new ResultConfirmVehicleData();
			JSONObject obj=new JSONObject(Response);
			RCvehicle.cVehicleData.setBusHubRouteRef(obj.optString("BusHubRouteRef"));
			RCvehicle.cVehicleData.setJourneyCode(obj.optString("JourneyCode"));
			
			
			
			
			result=(Result)RCvehicle;
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}