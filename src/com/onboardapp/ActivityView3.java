package com.onboardapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivityView3  extends Activity{
	LinearLayout ll_list;
	ArrayList<String>ListStop;
	LinearLayout ll_time;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.view3);

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
			
			LayoutInflater layoutInflater=LayoutInflater.from(ActivityView3.this);
			View row=layoutInflater.inflate(R.layout.inflate_view,null,false);
			TextView item = (TextView) row.findViewById(R.id.txt_name);
			item.setText(ListStop.get(i));
			ll_list.addView(row);
	
		
	
	}
		
		
	}
}