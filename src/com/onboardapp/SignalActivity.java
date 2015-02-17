package com.onboardapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.onboardapp.asynctask.AsyncTaskForConnect;
import com.onboardapp.callback.OnNotifyGetResponse;
import com.onboardapp.result.Result;
import com.onboardapp.result.ResultConfirmVehicleData;
import com.onboardapp.result.ResultRoute;
import com.onboardapp.util.AppUtil;
import com.onboardapp.util.Constants;

public class SignalActivity  extends Activity implements OnNotifyGetResponse{
	
	ImageView imgRed,imgYellow,imgGreen;
	TextView tvSignal;
	int strength=0;
	
	int SIGNAL=0;// 0=poor, 1=average, 2=Good
	int connectAttempt=0;
	int maxConnectAttempt=3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.signal);
		AppUtil.track.getLocation();
		initialize();

	}
	
	private void initialize()
	{
		imgRed=(ImageView)findViewById(R.id.img_red);
		imgYellow=(ImageView)findViewById(R.id.img_yellow);
		imgGreen=(ImageView)findViewById(R.id.img_green);
		tvSignal=(TextView)findViewById(R.id.tv_signal);
		
		if (AppUtil.Lattitude.length()<2) {
			if (AppUtil.Satellites <= 10) {
				//poor
				SIGNAL=0;
				displaySignal(SIGNAL);
				
			} else if (AppUtil.Satellites  > 10 && AppUtil.Satellites  < 30) {
				//average
				SIGNAL=1;
				displaySignal(SIGNAL);
				
			} else {
				//Good
				SIGNAL=2;
				displaySignal(SIGNAL);
			}
		}else
		{
			//good
//			SIGNAL=2;
			if (AppUtil.Satellites <= 10) {
				//poor
				SIGNAL=0;
				displaySignal(SIGNAL);
				
			} else if (AppUtil.Satellites  > 10 && AppUtil.Satellites  < 30) {
				//average
				SIGNAL=1;
				displaySignal(SIGNAL);
				
			} else {
				//Good
				SIGNAL=2;
				displaySignal(SIGNAL);
			}
			displaySignal(SIGNAL);
			if (AppUtil.internetOnline(SignalActivity.this)) {
			String url=Constants.URL_CONFIRM_VEHICLE+AppUtil.route+"&latitude=52.000&longitude=51.000";
			new AsyncTaskForConnect(url, null, SignalActivity.this, Constants.CONFIRM_VEHICLE, Constants.CONNECT_GET).execute();
			}
		}
		
		
//		if (AppUtil.internetOnline(SignalActivity.this)) {
//			String url=Constants.URL_DOWNLOAD_ROUTE;
//			new AsyncTaskForConnect(url, null, SignalActivity.this, Constants.DOWNLOAD_ROUTE, Constants.CONNECT_GET).execute();
//			}
		
	}

	private void displaySignal(int check)
	{
		if(check==0)
		{
			imgRed.setImageResource(R.drawable.circle_red_on);
			imgYellow.setImageResource(R.drawable.circle_yellow_off);
			imgGreen.setImageResource(R.drawable.circle_green_off);
			tvSignal.setText("Poor");
			
		}else if(check==1)
		{
			imgRed.setImageResource(R.drawable.circle_red_off);
			imgYellow.setImageResource(R.drawable.circle_yellow_on);
			imgGreen.setImageResource(R.drawable.circle_green_off);
			tvSignal.setText("Average");
		}else if(check==2)
		{
			imgRed.setImageResource(R.drawable.circle_red_off);
			imgYellow.setImageResource(R.drawable.circle_yellow_off);
			imgGreen.setImageResource(R.drawable.circle_green_on);
			tvSignal.setText("Good");
		}
	}
	
	
	
	@Override
	public void setOnNotifyGetResponse(Result result, int action) {
		if(result.getStatus().equals(Constants.statusERROR))
		{
			
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			connectAttempt++;
//			runOnUiThread(new Runnable() {
//				
//				@Override
//				public void run() {
//					Toast.makeText(SignalActivity.this, "Trying to reconnect...", Toast.LENGTH_SHORT).show();
//				}
//			});
			
			
			if (connectAttempt<=maxConnectAttempt) {
				if (AppUtil.internetOnline(SignalActivity.this)) {
					String url = Constants.URL_CONFIRM_VEHICLE + AppUtil.route
							+ "&latitude=52.000&longitude=51.000";
					new AsyncTaskForConnect(url, null, SignalActivity.this,
							Constants.CONFIRM_VEHICLE, Constants.CONNECT_GET)
							.execute();
				}
			}else
			{
				runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					Toast.makeText(SignalActivity.this, "No BusHubRef found.Please select another route.", Toast.LENGTH_SHORT).show();
				}
			});
			}

		}else{
			ResultConfirmVehicleData rVehicle=(ResultConfirmVehicleData)result;
			System.out.println("===="+rVehicle.cVehicleData.getBusHubRouteRef());
			Intent intent=new Intent(SignalActivity.this,ActivityView1.class);
			intent.putExtra("BusHubRouteRef", rVehicle.cVehicleData.getBusHubRouteRef());
			startActivity(intent);
		}
	}
}