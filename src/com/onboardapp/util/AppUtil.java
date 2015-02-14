package com.onboardapp.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import com.onboardapp.GPSTracker;
import com.onboardapp.result.ResultRoute;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;

public class AppUtil {

	public static String Lattitude="";
	public static String Longitude="";
	public static Context context;
	public static GPSTracker track;
	public static String route="";
	public static String filepath=Environment.getExternalStorageDirectory()+"/data.txt";
	
	public static String status="404";
	
	public static ResultRoute rRoute=new ResultRoute();
	
	public static int SignalStrength=100;
	
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
	
	
	public static void writeToFile(String data, String path) {
	    
		
//		writeLog(data);
		File f=new File(path);
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
		FileWriter fw = new FileWriter(path, false);
		fw.write(data);
		fw.close();
		} catch (IOException e) {
		e.printStackTrace();
		}
	    File file = new File(path);
		MediaScannerConnection.scanFile(context, new String[]{file.getPath()}, null, null);
		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
	
	
	}
	
	public static String readFromFile(String path)
	{
		try {
            File myFile = new File(path);
//            File myFile = new File(targetDirector+"/"+fileName);
            FileInputStream fIn = new FileInputStream(myFile);
            BufferedReader myReader = new BufferedReader(
                    new InputStreamReader(fIn));
            String aDataRow = "";
            String aBuffer = "";
            while ((aDataRow = myReader.readLine()) != null) {
                aBuffer += aDataRow + "\n";
            }
//            tv_log.setText(aBuffer);
            myReader.close();
            return aBuffer;
           
        } catch (Exception e) {
//            Toast.makeText(getBaseContext(), e.getMessage(),
//                    Toast.LENGTH_SHORT).show();
        }
		return "";
	}

	
}
