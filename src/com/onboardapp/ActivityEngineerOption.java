package com.onboardapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;

public class ActivityEngineerOption  extends Activity{
	LinearLayout ll_dwnload,ll_update;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.saved_login);
		
		initialize();
	}
	private void initialize()
	{
		ll_dwnload=(LinearLayout)findViewById(R.id.ll_download);
		ll_update=(LinearLayout)findViewById(R.id.ll_update_vehicle);
		
		ll_dwnload.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				startActivity(new Intent(ActivityEngineerOption.this,DownloadRoute.class));
				
			}
		});
		
		ll_update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				startActivity(new Intent(ActivityEngineerOption.this,AsignCodeActivity.class));
				
			}
		});
		
			
	}
	
}