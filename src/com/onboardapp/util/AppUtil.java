package com.onboardapp.util;

import com.onboardapp.result.ResultRoute;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class AppUtil {

	public static String Lattitude="";
	public static String Longitude="";
	public static Context context;
	
	public static String route="";
	
	public static String status="404";
	
	public static ResultRoute rRoute=new ResultRoute();
	
	
	public static boolean internetOnline(Context c) {

		
	    ConnectivityManager cm = (ConnectivityManager) c
	      .getSystemService(Context.CONNECTIVITY_SERVICE);

	    NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

	    // test for connection for WIFI
	    if (info != null && info.isAvailable() && info.isConnected()) {
	     return true;
	    }

	    info = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	    // test for connection for Mobile
	    if (info != null && info.isAvailable() && info.isConnected()) {
	     return true;
	    }
	    info = cm.getNetworkInfo(ConnectivityManager.TYPE_BLUETOOTH);
	    // test for connection for Mobile
	    if (info != null && info.isAvailable() && info.isConnected()) {
	     return true;
	    }
	    return false;
	   }
	
}
