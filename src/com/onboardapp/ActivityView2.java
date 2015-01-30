package com.onboardapp;

import java.util.ArrayList;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ActivityView2 extends FragmentActivity{
	LinearLayout ll_list;
	ArrayList<String>ListStop;
	 GoogleMap map;
	    GoogleMap googleMap;
	    GPSTracker gps; 
	    LinearLayout ll_time;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.view2);

		initialize();

	}
	
	private void initialize()
	{
		ListStop=new ArrayList<String>();
		for(int i=0;i<10;i++)
			ListStop.add("Stains");
		
		ll_list=(LinearLayout)findViewById(R.id.ll_list);
		for(int i=0;i<ListStop.size();i++)
		{
			
			LayoutInflater layoutInflater=LayoutInflater.from(ActivityView2.this);
			View row=layoutInflater.inflate(R.layout.inflate_view,null,false);
			TextView item = (TextView) row.findViewById(R.id.txt_name);
			item.setText(ListStop.get(i));
			ll_list.addView(row);
	
		
	
	}
		ll_time=(LinearLayout)findViewById(R.id.ll_time);
		ll_time.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(ActivityView2.this,ActivityView3.class));
				
			}
		});
		SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		googleMap = fm.getMap();
		
		gps = new GPSTracker(ActivityView2.this);
        // check if GPS enabled       
        if(gps.canGetLocation()){                  
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();  
            		LatLng myloc=new LatLng(latitude, longitude);
    		googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myloc,
    		            10));
    		
    		  googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker)));
    		 

        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }               
		
		
		
//		LatLng myloc=new LatLng(Double.parseDouble(Constants.LAT), Double.parseDouble(Constants.LNG));
//		googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myloc,
//		            10));
		
//		  googleMap.addMarker(new MarkerOptions().position(new LatLng(lati,longLat)).icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker)));
		 

		
		
	}
}