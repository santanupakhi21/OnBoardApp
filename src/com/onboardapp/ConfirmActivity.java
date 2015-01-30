package com.onboardapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;

public class ConfirmActivity extends Activity{
	LinearLayout ll_confirm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.confirm);
		initialize();
	}
	private void initialize()
	{
		ll_confirm=(LinearLayout)findViewById(R.id.ll_confirm);
		ll_confirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(ConfirmActivity.this,ActivityView1.class));
				
			}
		});
	}
}
