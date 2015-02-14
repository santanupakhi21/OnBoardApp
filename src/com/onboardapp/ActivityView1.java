package com.onboardapp;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.onboardapp.callback.OnNotifyGetResponse;
import com.onboardapp.imageload.ImageLoaderPlayed;
import com.onboardapp.model.AdvertModel;
import com.onboardapp.model.BusHubRouteModel;
import com.onboardapp.model.JourneyPatternModel;
import com.onboardapp.model.RouteData;
import com.onboardapp.model.ServiceModel;
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
		timerVirtual.schedule(myTimerTaskView, 2000,10000);
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
    		 

        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
        	AppUtil.track.showSettingsAlert();
        }               
		
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
			    		
			    		  googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(AppUtil.Lattitude), Double.valueOf(AppUtil.Longitude))).icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker)));
			    		 

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
}