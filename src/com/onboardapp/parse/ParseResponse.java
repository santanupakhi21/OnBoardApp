package com.onboardapp.parse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.onboardapp.model.AdvertModel;
import com.onboardapp.model.BusHubRouteModel;
import com.onboardapp.model.JourneyPatternModel;
import com.onboardapp.model.RouteData;
import com.onboardapp.model.ServiceModel;
import com.onboardapp.model.VehicleData;
import com.onboardapp.result.Result;
import com.onboardapp.result.ResultConfirmVehicleData;
import com.onboardapp.result.ResultRoute;
import com.onboardapp.result.ResultVehicleData;
import com.onboardapp.util.AppUtil;
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
			
			
			ResultRoute rRoute=new ResultRoute();
			JSONObject obj=new JSONObject(Response);
			JSONArray aRoute=obj.getJSONArray("Routes");
			for(int i=0;i<aRoute.length();i++)
			{
				RouteData rData=new RouteData();
				rData.setRouteName(aRoute.getJSONObject(i).optString("RouteName"));
				
				
				///Service
				JSONArray aService=aRoute.getJSONObject(i).getJSONArray("Services");
				for(int j=0;j<aService.length();j++)
				{
					ServiceModel sModel=new ServiceModel();
					JSONObject joService=aService.getJSONObject(j);
					sModel.setServiceId(joService.optString("ServiceId"));
					sModel.setDescription(joService.optString("Description"));
					sModel.setDestination(joService.optString("Destination"));
					sModel.setOrigin(joService.optString("Origin"));
					sModel.setRouteName(joService.optString("RouteName"));
					JSONArray jaDes=joService.getJSONArray("DescriptionList");
					for(int a=0;a<jaDes.length();a++)
					{
						sModel.ListDescription.add(jaDes.getString(a));
					}
					
					JSONArray jaBanner=joService.getJSONObject("BannerAdverts").getJSONArray("Adverts");
					for(int a=0;a<jaBanner.length();a++)
					{
						JSONObject Jsobj=jaBanner.getJSONObject(a);
						AdvertModel aModel=new AdvertModel();
						aModel.setAdId(Jsobj.optString("Id"));
						aModel.setAdType(Jsobj.optString("AdType"));
						aModel.setDescription(Jsobj.optString("Description"));
						aModel.setTitle(Jsobj.optString("Title"));
						aModel.setImage(Jsobj.optString("Image"));
						sModel.ListBannerAd.add(aModel);
					}
					
					
					JSONArray jaMPU=joService.getJSONObject("MPUAdverts").getJSONArray("Adverts");
					for(int a=0;a<jaMPU.length();a++)
					{
						JSONObject Jsobj=jaMPU.getJSONObject(a);
						AdvertModel aModel=new AdvertModel();
						aModel.setAdId(Jsobj.optString("Id"));
						aModel.setAdType(Jsobj.optString("AdType"));
						aModel.setDescription(Jsobj.optString("Description"));
						aModel.setTitle(Jsobj.optString("Title"));
						aModel.setImage(Jsobj.optString("Image"));
						sModel.ListMPUAd.add(aModel);
					}
					
					//BusHub
					JSONArray aBusHub=joService.getJSONArray("BusHubRoutes");
					for(int k=0;k<aBusHub.length();k++)
					{
						JSONObject jObushub=aBusHub.getJSONObject(k);
						BusHubRouteModel bhRouteModel=new BusHubRouteModel();
						
						JSONArray jaBhRefs=jObushub.getJSONArray("BusHubRouteRefs");
						for(int a=0;a<jaBhRefs.length();a++)
						{
							bhRouteModel.ListBusHubRouteRef.add(jaBhRefs.getString(a));
						}
						
						JSONArray jaJourney=jObushub.getJSONArray("JourneyPatterns");
						for(int l=0;l<jaJourney.length();l++)
						{
							JourneyPatternModel jModel=new JourneyPatternModel();
							JSONObject jobj=jaJourney.getJSONObject(l);
							jModel.setName(jobj.optString("Name"));
							jModel.setAtcoCode(jobj.optString("AtcoCode"));
							jModel.setBearing(jobj.optString("Bearing"));
							jModel.setIndicator(jobj.optString("Indicator"));
							jModel.setLat(jobj.getJSONObject("Location").optString("Lattitude"));
							jModel.setLng(jobj.getJSONObject("Location").optString("Longitude"));
							jModel.setLocality(jobj.optString("Locality"));
							jModel.setStopAlerts(jobj.optString("StopAlerts"));
							jModel.setTravelConnection(jobj.optString("TravelConnections"));
							
							bhRouteModel.ListJourneyPattern.add(jModel);
							
						}
						sModel.ListBusHubRouteModel.add(bhRouteModel);
						
					}
					
					rData.ListService.add(sModel);
					
				}
				
				
				
				
				
				
				//
				rRoute.ListRoute.add(rData);
			}
			
			
			

			
			
			result=(Result)rRoute;
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void parseConfirmVehicle()
	{
		try {
			
//			{"BusHubRouteRef":"557-1","Direction":"0",”JourneyCode”:”1045”,"RouteName":"557","LastRecorded":"2015-02-02T18:12:23",
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
