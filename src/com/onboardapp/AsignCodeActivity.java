package com.onboardapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class AsignCodeActivity extends Activity{

	LinearLayout ll_assign;
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
		ll_assign=(LinearLayout)findViewById(R.id.ll_assign);
		ll_assign.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(AsignCodeActivity.this,ConfirmActivity.class));
				
			}
		});
	}
}