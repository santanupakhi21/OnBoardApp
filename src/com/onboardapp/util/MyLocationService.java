package com.onboardapp.util;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.onboardapp.GPSTracker;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

public class MyLocationService extends Service implements
ConnectionCallbacks, OnConnectionFailedListener, LocationListener{
	Context context;
	Handler toastHandler = new Handler();
	Runnable toastRunnable = new Runnable() {public void run() {
		
		if(AppUtil.Lattitude.length()>0)
			Toast.makeText(MyLocationService.this,"===Location==Lat : "+AppUtil.Lattitude+"  Long : "+AppUtil.Longitude, Toast.LENGTH_SHORT).show();
		}};
	
	public MyLocationService(/*Context context*/) {
//		this.context=context;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
    public void onCreate() {
//        Toast.makeText(this, "The new Service was Created", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onStart(Intent intent, int startId) {
    	// For time consuming an long tasks you can launch a new thread here...
        Toast.makeText(this, " Service Started", Toast.LENGTH_LONG).show();
        checkLocation();
    }

    private void checkLocation()
    {
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true)
				{
					try {
						Thread.sleep(5000);
//						GPSTracker track=new GPSTracker(AppUtil.context);
						AppUtil.track.getLocation();
						System.out.println("==="+AppUtil.Lattitude);
//						track.getLocation();
//						toastHandler.post(toastRunnable);
//						System.out.println("====Location===");
//						Toast.makeText(MyLocationService.this, "Location", Toast.LENGTH_SHORT);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}
		}).start();
    }
    
    @Override
    public void onDestroy() {
//        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();

    }

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		 /*LocationRequest mLocationRequest = new LocationRequest();
		    mLocationRequest.setInterval(10000);
		    mLocationRequest.setFastestInterval(5000);
		    mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		    LocationServices.FusedLocationApi.requestLocationUpdates(
		            mGoogleApiClient, mLocationRequest, this);*/
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub
		
	}
	
	protected void startLocationUpdates() {
	  
	}
}