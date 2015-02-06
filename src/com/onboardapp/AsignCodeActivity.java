package com.onboardapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.onboardapp.asynctask.AsyncTaskForConnect;
import com.onboardapp.callback.OnNotifyGetResponse;
import com.onboardapp.model.VehicleData;
import com.onboardapp.result.Result;
import com.onboardapp.result.ResultVehicleData;
import com.onboardapp.util.AppUtil;
import com.onboardapp.util.Constants;

public class AsignCodeActivity extends Activity implements OnNotifyGetResponse{

	LinearLayout ll_assign;
	Spinner sp;
	EditText etCode;
	String route="";
	String[] str_arr;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.assign_code);

		initialize();

	}
	
	private void initialize()
	{
		
		
		etCode=(EditText)findViewById(R.id.et_code);
		ll_assign=(LinearLayout)findViewById(R.id.ll_assign);
		ll_assign.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				Intent intent=new Intent(AsignCodeActivity.this,ConfirmActivity.class);
				intent.putExtra("route", route);
				startActivity(intent);
				
			}
		});
		sp=(Spinner)findViewById(R.id.sp_choose);
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
				route=str_arr[arg2];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		if (AppUtil.internetOnline(AsignCodeActivity.this)) {
		new AsyncTaskForConnect(Constants.URL_LIST_VEHICLE, null, AsignCodeActivity.this, Constants.LIST_VEHICLE, Constants.CONNECT_GET).execute();
		}
		
		
	}

	@Override
	public void setOnNotifyGetResponse(Result result, int action) {
		ResultVehicleData rVehicle=(ResultVehicleData)result;
		str_arr=new String[rVehicle.ListVehicle.size()];
		int i=0;
		for(VehicleData data:rVehicle.ListVehicle)
		{
			System.out.println("==="+data.getVehicleref());
			str_arr[i]=data.getVehicleref();
			i++;
		}
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>  (this,R.layout.my_spinner, str_arr);
		sp.setAdapter(dataAdapter);
	}
}
