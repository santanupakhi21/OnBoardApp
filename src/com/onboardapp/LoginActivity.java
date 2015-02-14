package com.onboardapp;

import com.onboardapp.gps.BluetoothGpsActivity;
import com.onboardapp.util.AppUtil;
import com.onboardapp.util.MyLocationService;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

public class LoginActivity extends Activity{

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		AppUtil.track.getLocation();
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onDestroy() {
		Intent i = new Intent(LoginActivity.this, MyLocationService.class);
		
		LoginActivity.this.stopService(i);

		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	LinearLayout ll_login;
	boolean hasGps;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.login);
		AppUtil.context=LoginActivity.this;
		AppUtil.track=new GPSTracker(AppUtil.context);
		initialize();

	}
	
	private void initialize()
	{
		ll_login=(LinearLayout)findViewById(R.id.ll_login);
		ll_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(LoginActivity.this,AsignCodeActivity.class));
				
			}
		});
		
		PackageManager pm = this.getPackageManager();
//		 startService(new Intent(this, MyLocationService.class));

		hasGps = pm.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
		
		if(hasGps)
		{
			Toast.makeText(LoginActivity.this, "GPS is embedded in the device no need for external GPS", Toast.LENGTH_SHORT).show();
			
			AppUtil.track.getLocation();
			System.out.println("==="+AppUtil.Lattitude);
//			startActivity(new Intent(LoginActivity.this,BluetoothGpsActivity.class));
			 startService(new Intent(this, MyLocationService.class));
			
//			 startService(new Intent(this, MyLocationService.class));
		}else
		{
			Toast.makeText(LoginActivity.this, "GPS is not embedded in the device, trying to start external GPS", Toast.LENGTH_SHORT).show();
			startActivity(new Intent(LoginActivity.this,BluetoothGpsActivity.class));
			 startService(new Intent(this, MyLocationService.class));
		}
		
	}
	
	private void turnGPSOn(){
	    String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

	    if(!provider.contains("gps")){ //if gps is disabled
	        final Intent poke = new Intent();
	        poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider"); 
	        poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
	        poke.setData(Uri.parse("3")); 
	        sendBroadcast(poke);
	    }
	}

	
}
