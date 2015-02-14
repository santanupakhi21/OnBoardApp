package com.onboardapp.timer;

import java.util.Random;
import java.util.TimerTask;

import android.app.Activity;

public class ViewTimerTask extends TimerTask {
	OnNotifyViewTimerMessage onNotifyViewTimerMessage;
	int value=1;
	public ViewTimerTask(Activity context) {
		super();
		onNotifyViewTimerMessage= (OnNotifyViewTimerMessage)context;
	}
	
	  @Override
	  public void run() {
		  
		  int aNumber=0;
		 	System.out.println("Interval");
		 	
//		 	final int random = Random.nextInt(61) + 20; // [0, 60] + 20 => [20, 80]
//		

		 	 int min = 0;
		 	int max = 4;
		 	Random r = new Random();
		 	//...
		aNumber = min + r.nextInt(max-min+1); //+1 if high is inclusive
		 	
		 	System.out.println("=================Random Number for time : "+aNumber);
		 	
		  value=aNumber;
		  onNotifyViewTimerMessage.setOnNotifyViewTimerMessage(value);
	  }
	 }