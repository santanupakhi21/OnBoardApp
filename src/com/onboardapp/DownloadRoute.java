package com.onboardapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.onboardapp.asynctask.AsyncTaskForConnect;
import com.onboardapp.callback.OnNotifyGetResponse;
import com.onboardapp.result.Result;
import com.onboardapp.result.ResultConfirmVehicleData;
import com.onboardapp.result.ResultRoute;
import com.onboardapp.util.AppUtil;
import com.onboardapp.util.Constants;

public class DownloadRoute  extends Activity implements OnNotifyGetResponse{
	LinearLayout ll_confirm;
	TextView tvVehicle;
//	String route="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.download_route);
		
//		AppUtil.route=route;
		initialize();
	}
	private void initialize()
	{
		ll_confirm=(LinearLayout)findViewById(R.id.ll_confirm);
		tvVehicle=(TextView)findViewById(R.id.tv_vehicle);
		
//		tvVehicle.setText(route);
		
		ll_confirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				AppUtil.route=route;
				startActivity(new Intent(DownloadRoute.this,SignalActivity.class));
				
			}
		});
		
		ll_confirm.setVisibility(View.INVISIBLE);
		tvVehicle.setVisibility(View.INVISIBLE);
		
		if (AppUtil.internetOnline(DownloadRoute.this)) {
			String url=Constants.URL_DOWNLOAD_ROUTE;
			new AsyncTaskForConnect(url, null, DownloadRoute.this, Constants.DOWNLOAD_ROUTE, Constants.CONNECT_GET).execute();
			}
		
		
//		if (AppUtil.internetOnline(ConfirmActivity.this)) {
//			String url=Constants.URL_CONFIRM_VEHICLE+route+"&latitude=52.000&longitude=51.000";
//			new AsyncTaskForConnect(url, null, ConfirmActivity.this, Constants.CONFIRM_VEHICLE, Constants.CONNECT_GET).execute();
//			}
			
	}
	@Override
	public void setOnNotifyGetResponse(Result result, int action) {
		ResultRoute rRoute=(ResultRoute)result;
		AppUtil.rRoute=rRoute;
		System.out.println("==="+rRoute.ListRoute.size());
		
		ll_confirm.setVisibility(View.VISIBLE);
		tvVehicle.setVisibility(View.VISIBLE);
		String str="You have\n successfully \ndownloaded \n"+rRoute.ListRoute.size()+" routes. ";
		tvVehicle.setText(str);
	}
}
