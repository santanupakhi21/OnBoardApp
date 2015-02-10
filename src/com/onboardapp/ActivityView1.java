package com.onboardapp;

import java.util.ArrayList;

import com.onboardapp.asynctask.AsyncTaskForConnect;
import com.onboardapp.callback.OnNotifyGetResponse;
import com.onboardapp.result.Result;
import com.onboardapp.result.ResultRoute;
import com.onboardapp.util.AppUtil;
import com.onboardapp.util.Constants;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivityView1 extends Activity implements OnNotifyGetResponse{
	LinearLayout ll_list;
	ArrayList<String>ListStop;
	LinearLayout ll_time;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.view1);

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
			
			LayoutInflater layoutInflater=LayoutInflater.from(ActivityView1.this);
			View row=layoutInflater.inflate(R.layout.inflate_view,null,false);
			TextView item = (TextView) row.findViewById(R.id.txt_name);
			item.setText(ListStop.get(i));
			ll_list.addView(row);
	
		
	
	}
		
		ll_time=(LinearLayout)findViewById(R.id.ll_time);
		ll_time.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(ActivityView1.this,ActivityView2.class));
				
			}
		});
		
		if (AppUtil.internetOnline(ActivityView1.this)) {
			String url=Constants.URL_DOWNLOAD_ROUTE;
			new AsyncTaskForConnect(url, null, ActivityView1.this, Constants.DOWNLOAD_ROUTE, Constants.CONNECT_GET).execute();
			}
		
	}

	@Override
	public void setOnNotifyGetResponse(Result result, int action) {
		
		ResultRoute rRoute=(ResultRoute)result;
		System.out.println("==="+rRoute.ListRoute.size());
	}
}