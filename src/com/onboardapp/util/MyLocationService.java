package com.onboardapp.util;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

public class MyLocationService extends Service {
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
						toastHandler.post(toastRunnable);
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
}