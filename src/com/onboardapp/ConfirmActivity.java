package com.onboardapp;

import com.onboardapp.asynctask.AsyncTaskForConnect;
import com.onboardapp.callback.OnNotifyGetResponse;
import com.onboardapp.result.Result;
import com.onboardapp.result.ResultConfirmVehicleData;
import com.onboardapp.util.AppUtil;
import com.onboardapp.util.Constants;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ConfirmActivity extends Activity implements OnNotifyGetResponse{
	LinearLayout ll_confirm;
	TextView tvVehicle;
	String route="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.confirm);
		route=getIntent().getStringExtra("route");
		System.out.println("===="+route);
//		AppUtil.route=route;
		initialize();
	}
	private void initialize()
	{
		ll_confirm=(LinearLayout)findViewById(R.id.ll_confirm);
		tvVehicle=(TextView)findViewById(R.id.tv_vehicle);
		
		tvVehicle.setText(route);
		ll_confirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AppUtil.route=route;
				storedata(AppUtil.prefVehicle, AppUtil.vehicle, route);
				startActivity(new Intent(ConfirmActivity.this,DownloadRoute.class));
				
			}
		});
		
//		if (AppUtil.internetOnline(ConfirmActivity.this)) {
//			String url=Constants.URL_CONFIRM_VEHICLE+route+"&latitude=52.000&longitude=51.000";
//			new AsyncTaskForConnect(url, null, ConfirmActivity.this, Constants.CONFIRM_VEHICLE, Constants.CONNECT_GET).execute();
//			}
			
	}
	@Override
	public void setOnNotifyGetResponse(Result result, int action) {
		ResultConfirmVehicleData rVehicle=(ResultConfirmVehicleData)result;
		tvVehicle.setText(rVehicle.cVehicleData.getBusHubRouteRef());
	}
	
	
	private void storedata(String prefUser, String name, String value) {

		SharedPreferences settings = getSharedPreferences(prefUser, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(name, value);
		editor.commit();

	}

	private String getdata(String prefUser, String name) {

		String value = "";
		SharedPreferences settings = getSharedPreferences(prefUser, 0);

		value = settings.getString(name, "");
		return value;

	}

	private void clearMemory(String prefUser)

	{

		SharedPreferences settings = getSharedPreferences(prefUser, 0);

		SharedPreferences.Editor editor = settings.edit();

		editor.clear();

		editor.commit();

	}
	
}
