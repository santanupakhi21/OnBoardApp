package com.onboardapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;

import org.json.JSONObject;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.onboardapp.callback.OnNotifyGetResponse;
import com.onboardapp.imageload.ImageLoaderPlayed;
import com.onboardapp.model.AdvertModel;
import com.onboardapp.model.BusHubRouteModel;
import com.onboardapp.model.JourneyPatternModel;
import com.onboardapp.model.RouteData;
import com.onboardapp.model.ServiceModel;
import com.onboardapp.parse.DirectionsJSONParser;
import com.onboardapp.result.Result;
import com.onboardapp.result.ResultRoute;
import com.onboardapp.timer.OnNotifyViewTimerMessage;
import com.onboardapp.timer.ViewTimerTask;
import com.onboardapp.util.AppUtil;

public class ActivityView1 extends FragmentActivity implements OnNotifyGetResponse, OnNotifyViewTimerMessage{
	LinearLayout ll_list;
	LinearLayout ll_map;
	LinearLayout ll_advert;
	
	ArrayList<String>ListStop;
	LinearLayout ll_time;
	String busHubRouteRef="";
	ArrayList<JourneyPatternModel>ListJourney=new ArrayList<JourneyPatternModel>();
	ArrayList<AdvertModel>MCU_Advert=new ArrayList<AdvertModel>();
	ArrayList<AdvertModel>Banner_Advert=new ArrayList<AdvertModel>();
	static Timer timerVirtual;
	 static ViewTimerTask myTimerTaskView;
	String routeName="";
	String str_origin="";
	String strMcuAdvert="";
	TextView tvRouteName;
	TextView tvOrigin;
	TextView tvMcuAdvert;
	 GoogleMap map;
	 GoogleMap googleMap;
	 ImageView imgAdvert;
	 ImageLoaderPlayed imageLoaderLast;
	 

		LatLng LOWER_MANHATTAN;
		LatLng BROOKLYN_BRIDGE;
		
	String mode_id = "2";
		
		
		final String TAG = "PathGoogleMapActivity";
		
		int mMode=0;
		
		ArrayList<LatLng> markerPoints;
		Marker startMarker;
		Marker endMarkar;
//		ArrayList<Points> test = new ArrayList<Points>();
	 
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.view2);
//		intent.putExtra("BusHubRouteRef", rVehicle.cVehicleData.getBusHubRouteRef());
//		busHubRouteRef=getIntent().getStringExtra("BusHubRouteRef");
		
		busHubRouteRef="292933";
		
		initialize();
		getJourneyPattern();
		displayMap();

	}
	
	private void initialize()
	{
		ll_list=(LinearLayout)findViewById(R.id.ll_list);
		ll_map=(LinearLayout)findViewById(R.id.ll_map);
		ll_advert=(LinearLayout)findViewById(R.id.ll_advert);
		
		imgAdvert=(ImageView)findViewById(R.id.image_advert);
		tvRouteName=(TextView)findViewById(R.id.tv_route_name);
		tvOrigin=(TextView)findViewById(R.id.tv_origin);
		tvMcuAdvert=(TextView)findViewById(R.id.tv_mcu_advert);
		
		/*ListStop=new ArrayList<String>();
		for(int i=0;i<10;i++)
			ListStop.add("Stains");
		
		ll_list=(LinearLayout)findViewById(R.id.ll_list);
		for(int i=0;i<ListStop.size();i++)
		{
			
			LayoutInflater layoutInflater=LayoutInflater.from(ActivityView1.this);
			View row=layoutInflater.inflate(R.layout.inflate_view,null,false);
			TextView item = (TextView) row.findViewById(R.id.txt_name);
			item.setText(ListStop.get(i));
			ll_list.addView(row);
	
		
	
	}*/
		
		ll_time=(LinearLayout)findViewById(R.id.ll_time);
		ll_time.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(ActivityView1.this,ActivityView2.class));
				
			}
		});
		
//		if (AppUtil.internetOnline(ActivityView1.this)) {
//			String url=Constants.URL_DOWNLOAD_ROUTE;
//			new AsyncTaskForConnect(url, null, ActivityView1.this, Constants.DOWNLOAD_ROUTE, Constants.CONNECT_GET).execute();
//			}
		
		
		
	}
	
	private void getJourneyPattern()
	{
		for(RouteData rdata:AppUtil.rRoute.ListRoute)
		{
			for(ServiceModel sModel:rdata.ListService)
			{
				for(BusHubRouteModel bshModel:sModel.ListBusHubRouteModel)
				{
					for(String strBudHub:bshModel.ListBusHubRouteRef)
					{
						if(strBudHub.equals(busHubRouteRef))
						{
							ListJourney=bshModel.ListJourneyPattern;
							MCU_Advert=sModel.ListMPUAd;
							Banner_Advert=sModel.ListBannerAd;
							str_origin=sModel.getOrigin() + " to "+sModel.getDestination();
							routeName=sModel.getRouteName();
							
							break;
						}
					}
				}
			}
		}
		tvOrigin.setText(str_origin);
		tvRouteName.setText(routeName);
		tvMcuAdvert.setText(MCU_Advert.get(0).getDescription());
		try {
			imageLoaderLast = new ImageLoaderPlayed(
					ActivityView1.this);
			imageLoaderLast.DisplayImage("http:"+Banner_Advert.get(0).getImage(), imgAdvert);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		displayList();
		startTimer();

	}
	
	
	private void displayList()
	{
		for(int i=0;i<ListJourney.size();i++)
		{
			
			LayoutInflater layoutInflater=LayoutInflater.from(ActivityView1.this);
			View row=layoutInflater.inflate(R.layout.inflate_view,null,false);
			TextView item = (TextView) row.findViewById(R.id.txt_name);
			item.setText(ListJourney.get(i).getName());
			ll_list.addView(row);
	
		
	
	}
	}
	
	private void startTimer()
	{
		if(timerVirtual==null){
		timerVirtual = new Timer();
		myTimerTaskView = new ViewTimerTask(ActivityView1.this);
		timerVirtual.schedule(myTimerTaskView, 2000,30000);
		System.out.println("==Virtual timer started===");
		
		}
	}
	
	private void displayMap()
	{
		SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		googleMap = fm.getMap();
		
//		gps = new GPSTracker(ActivityView1.this);
        // check if GPS enabled       
        if(AppUtil.track.canGetLocation()){                  
            double latitude = AppUtil.track.getLatitude();
            double longitude = AppUtil.track.getLongitude();  
            		LatLng myloc=new LatLng(latitude, longitude);
    		googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myloc,
    		            10));
    		
    		  googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker)));
    		 
    		  addLines();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
        	AppUtil.track.showSettingsAlert();
        }               
		
       
        ll_map.setVisibility(View.VISIBLE);
		ll_advert.setVisibility(View.GONE);
	}
	
	public void displayAd()
	{
		
		int aNumber=0;
	 	System.out.println("Interval");
	 	 int min = 0;
	 	int max = Banner_Advert.size()-1;
	 	Random r = new Random();
	 	//...
	aNumber = min + r.nextInt(max-min+1); //+1 if high is inclusive
		
		try {
			imageLoaderLast = new ImageLoaderPlayed(
					ActivityView1.this);
			imageLoaderLast.DisplayImage("http:"+Banner_Advert.get(aNumber).getImage(), imgAdvert);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 int randNo=0;
	 	System.out.println("Interval");
	 	  min = 0;
	 	max = MCU_Advert.size()-1;
	 	r = new Random();
	 	//...
	 	randNo = min + r.nextInt(max-min+1); 
	 	tvMcuAdvert.setText(MCU_Advert.get(randNo).getDescription());
		
	}
	
	
	@Override
	public void setOnNotifyGetResponse(Result result, int action) {
		
		ResultRoute rRoute=(ResultRoute)result;
		System.out.println("==="+rRoute.ListRoute.size());
	}

	@Override
	public void setOnNotifyViewTimerMessage(final int value) {
		
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				if(value==1)
				{
					//list
					ll_map.setVisibility(View.GONE);
					ll_advert.setVisibility(View.GONE);
				}else if(value==2)
				{
					//list  map
					ll_map.setVisibility(View.VISIBLE);
					ll_advert.setVisibility(View.GONE);
//					 double latitude = AppUtil.Lattitude;
//			            double longitude = AppUtil.track.getLongitude();  
			            		LatLng myloc=new LatLng(Double.valueOf(AppUtil.Lattitude), Double.valueOf(AppUtil.Longitude));
			    		googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myloc,
			    		            10));
			    		
//			    		  googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(AppUtil.Lattitude), Double.valueOf(AppUtil.Longitude))).icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker)));
//			    		 googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf("52.000"), Double.valueOf("51.000"))).icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker)));
			    		  

				}else
				{
//					list advert
					ll_map.setVisibility(View.GONE);
					ll_advert.setVisibility(View.VISIBLE);
					
					displayAd();
					
					
				}
			}
		});
		
		startTimer();
	}
	
	
	
	
	private void addLines() {

		final ArrayList<LatLng>ListLatLng=new ArrayList<LatLng>();
		int i=0;
		int end=ListJourney.size()-1;
		 for(JourneyPatternModel jModel:ListJourney)
		 {
			 if (jModel.getLat().length()>2 && jModel.getLng().length()>2) {
				
				System.out.println("Lat :"
						+ Double.parseDouble(jModel.getLat()) + "   Lang : "
						+ Double.parseDouble(jModel.getLng()));
				LatLng latLng = new LatLng(Double.parseDouble(jModel.getLat()),
						Double.parseDouble(jModel.getLng()));
				if(i==0)
				{
					startMarker=googleMap.addMarker(new MarkerOptions().position(latLng).title("Start").icon(
							BitmapDescriptorFactory
									.fromResource(R.drawable.bus_pin)));
//					 startMarker.showInfoWindow();
				        
				}else if(i==end)
				{
					endMarkar=googleMap.addMarker(new MarkerOptions().position(latLng).title("End").icon(
							BitmapDescriptorFactory
									.fromResource(R.drawable.bus_pin)));
//					endMarkar.showInfoWindow();
				}else{
				googleMap.addMarker(new MarkerOptions().position(latLng).icon(
						BitmapDescriptorFactory
								.fromResource(R.drawable.bus_pin)));
				}
				ListLatLng.add(latLng);
				 i++;
			}
		 }
		 
		 runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				 drawPathDrive(ListLatLng);
				
			}
		});
		
		
		 ArrayList<LatLng>ListLatLngWalk2=new ArrayList<LatLng>();

		 
	}
	
	private void drawPathDrive(ArrayList<LatLng>ListLatLng)
	{
		mMode=Integer.parseInt("0");

		
			// Getting URL to the Google Directions API
		
		for(int i=0;i<(ListLatLng.size()-1);i++){
			LatLng lt1=ListLatLng.get(i);
			LatLng lt2=ListLatLng.get(i+1);
			LOWER_MANHATTAN =lt1;
			BROOKLYN_BRIDGE = lt2;
			String url = getDirectionsUrl(LOWER_MANHATTAN, BROOKLYN_BRIDGE);				
			
			DownloadTask downloadTask = new DownloadTask();
			downloadTask.execute(url);
	
		}
		
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ListLatLng.get(0),
				10));
	}
	
	
private String getDirectionsUrl(LatLng origin,LatLng dest){
		
		// Origin of route
		String str_origin = "origin="+origin.latitude+","+origin.longitude;
		
		// Destination of route
		String str_dest = "destination="+dest.latitude+","+dest.longitude;	
					
		// Sensor enabled
		String sensor = "sensor=false";			
		
		// Travelling Mode
		String mode = "mode=walking";	
		
		if(mMode==0){
			mode="mode=driving";
			
		}
		 if(mMode==1){
			mode = "mode=bicycling";
		}
		 if(mMode==2){
			mode = "mode=walking";
		}
		
				
		// Building the parameters to the web service
		String parameters = str_origin+"&"+str_dest+"&"+sensor+"&"+mode;
					
		// Output format
		String output = "json";
		
		// Building the url to the web service
		String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
		
		
		return url;
	}
	
	
private class DownloadTask extends AsyncTask<String, Void, String>{			
		
		// Downloading data in non-ui thread
		@Override
		protected String doInBackground(String... url) {
				
			// For storing data from web service
			String data = "";
					
			try{
				// Fetching the data from web service
				data = downloadUrl(url[0]);
			}catch(Exception e){
				Log.d("Background Task",e.toString());
			}
			return data;		
		}
		
		// Executes in UI thread, after the execution of
		// doInBackground()
		@Override
		protected void onPostExecute(String result) {			
			super.onPostExecute(result);			
			
			ParserTask parserTask = new ParserTask();
			
			// Invokes the thread for parsing the JSON data
			parserTask.execute(result);
				
		}	
	}
	
private String downloadUrl(String strUrl) throws IOException{
    String data = "";
    InputStream iStream = null;
    HttpURLConnection urlConnection = null;
    try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url 
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url 
            urlConnection.connect();

            // Reading data from url 
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                    sb.append(line);
            }
            
            data = sb.toString();

            br.close();

    }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
    }finally{
            iStream.close();
            urlConnection.disconnect();
    }
    return data;
 }

	 private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{
	    	
	    	// Parsing the data in non-ui thread    	
			@Override
			protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
				
				JSONObject jObject;	
				List<List<HashMap<String, String>>> routes = null;			           
	            
	            try{
	            	jObject = new JSONObject(jsonData[0]);
	            	DirectionsJSONParser parser = new DirectionsJSONParser(ActivityView1.this);
	            	
	            	// Starts parsing data
	           	routes = parser.parse(jObject);    
	            }catch(Exception e){
	            	e.printStackTrace();
	            }
	            return routes;
			}
			
			// Executes in UI thread, after the parsing process
			@Override
			protected void onPostExecute(List<List<HashMap<String, String>>> result) {
				ArrayList<LatLng> points = null;
				PolylineOptions lineOptions = new PolylineOptions();

				MarkerOptions markerOptions = new MarkerOptions();
				
				// Traversing through all the routes
				try {
					for(int i=0;i<result.size();i++){
						points = new ArrayList<LatLng>();
						lineOptions = new PolylineOptions();
						
						// Fetching i-th route
						List<HashMap<String, String>> path = result.get(i);
						
						// Fetching all the points in i-th route
						for(int j=0;j<path.size();j++){
							HashMap<String,String> point = path.get(j);					
							
							
							
							double lat=Double.parseDouble(point.get("lat"));
							double lng=Double.parseDouble(point.get("lng"));
							
							LatLng position = new LatLng(lat, lng);	
							
							points.add(position);
							//test=db.getAllPoints();
							
							
						}
						
						// Adding all the points in the route to LineOptions
						lineOptions.addAll(points);
						lineOptions.width(7);
					//	test=db.getAllPoints();
						 
						
						// Changing the color polyline according to the mode
						if(mMode==2)
							lineOptions.color(Color.RED);
						 if(mMode==1)
							lineOptions.color(Color.GREEN);
						 if(mMode==0){
//							lineOptions.color(Color.BLUE);
							lineOptions.color(Color.RED);		
						 }
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				googleMap.addPolyline(lineOptions);
				
				if(result.size()<1){
					Toast.makeText(getBaseContext(), "No Points", Toast.LENGTH_SHORT).show();
					return;
				}
			}
	
		
	private String getMapsApiDirectionsUrl() {
		String waypoints = "waypoints=optimize:true|"
				+ LOWER_MANHATTAN.latitude + "," + LOWER_MANHATTAN.longitude
				+ "|" + "|" + BROOKLYN_BRIDGE.latitude + ","
				+ BROOKLYN_BRIDGE.longitude;

		

		String sensor = "sensor=false";
		String params = waypoints + "&" + sensor;
		String output = "json";
		String url = "https://maps.googleapis.com/maps/api/directions/"
				+ output + "?" + params;
		return url;
	}

	 }
	
	
	
	
	
}