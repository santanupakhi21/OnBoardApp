package com.onboardapp.parse;

import java.util.ArrayList;


import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

public class DirectionsJSONParser {
	
	
	private static final String COLUMN_DISTANCE_TEXT = "distance_text";
	private static final String COLUMN_DISTANCE_VALUE = "distance_value";
	private static final String COLUMN_DURATION_TEXT = "duration_text";
	private static final String COLUMN_DURATION_VALUE = "duration_value";
	private static final String COLUMN_START_LOCATION = "start_location";
	private static final String COLUMN_END_LOCATION = "end_location";
	private static final String COLUMN_POLYLINE = "polyline";
	private static final String TABLE_STEPS = "steps";
	Context context;
	
	
	
	
	public DirectionsJSONParser(Context mContext){
		context = mContext;
		
		
	}
	

	
	/** Receives a JSONObject and returns a list of lists containing latitude and longitude */
	public List<List<HashMap<String,String>>> parse(JSONObject jObject){
		
		List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String,String>>>() ;
		JSONArray jRoutes = null;
		JSONArray jLegs = null;
		JSONArray jSteps = null;	
		
//		Points test = new Points();
		
		ArrayList<Points> test = new ArrayList<Points>();
		ArrayList<Points> _points = new ArrayList<Points>();
	//	db=new MySQLiteHelper(DirectionsJSONParser.this);
		try {			
			
			jRoutes = jObject.getJSONArray("routes");
			
			/** Traversing all routes */
			for(int i=0;i<jRoutes.length();i++){			
				jLegs = ( (JSONObject)jRoutes.get(i)).getJSONArray("legs");
				List path = new ArrayList<HashMap<String, String>>();
				
				/** Traversing all legs */
				for(int j=0;j<jLegs.length();j++){
					jSteps = ( (JSONObject)jLegs.get(j)).getJSONArray("steps");
					
					/** Traversing all steps */
					for(int k=0;k<jSteps.length();k++){
						
						Points obj_text=new Points();
						
						
						String polyline = "";
						String distance="";
						String distance_text="";
						Integer distance_value = null;
						int distance_value1;
						
						String duration="";
						String duration_text="";
						Integer duration_value = null;
						int duration_value1;
						
						String end_location="";
						String start_location="";
						
						polyline = (String)((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
						List<LatLng> list = decodePoly(polyline);
						
						/** Traversing all points */
						for(int l=0;l<list.size();l++){
							HashMap<String, String> hm = new HashMap<String, String>();
							hm.put("lat", Double.toString(((LatLng)list.get(l)).latitude) );
							hm.put("lng", Double.toString(((LatLng)list.get(l)).longitude) );
							path.add(hm);						
						}		
						
						
						try {
							distance_text = (String) ((JSONObject) ((JSONObject) jSteps
									.get(k)).get("distance")).get("text");
							
							distance_value=(Integer) ((JSONObject) ((JSONObject) jSteps
									.get(k)).get("distance")).get("value");
							
							//distance_value1=Integer.parseInt(distance_value);
							
							
							duration_text=(String) ((JSONObject) ((JSONObject) jSteps
									.get(k)).get("duration")).get("text");
							
							duration_value=(Integer) ((JSONObject) ((JSONObject) jSteps
									.get(k)).get("duration")).get("value");
							//duration_value1=Integer.parseInt(duration_value);
							
					
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							
							e.printStackTrace();
						}
						

						obj_text.setDistance_text(distance_text);
						obj_text.setDistance_value(distance_value);
						obj_text.setDuration_text(duration_text);
						obj_text.setDuration_value(duration_value);
						
					
						
						test.add(obj_text) ;
				
						
					
										
					}
					routes.add(path);
				}
			}
			
		} catch (JSONException e) {			
			e.printStackTrace();
			
		}catch (Exception e){
			
			e.printStackTrace();
			System.out.print(e.getMessage());
		}
		 
		
		return routes;
	}	
	
	
	/**
	 * Method to decode polyline points 
	 * Courtesy : jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java 
	 * */
    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }


}
