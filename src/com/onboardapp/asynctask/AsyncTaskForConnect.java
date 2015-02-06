package com.onboardapp.asynctask;
import org.json.JSONObject;

import com.onboardapp.callback.OnNotifyGetResponse;
import com.onboardapp.parse.ParseResponse;
import com.onboardapp.result.Result;
import com.onboardapp.util.AppUtil;
import com.onboardapp.util.Constants;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class AsyncTaskForConnect  extends AsyncTask<Void, Void, Void> {
	 
	  ProgressDialog dialog;
	Context contex;
	  String url;
	  JSONObject json;
	  int action_type;
	  OnNotifyGetResponse onNotifyGetResponse;
	  Result resultReturn=new Result();
	  int connectionType;
	  public AsyncTaskForConnect(String url,JSONObject json,Context contex,int action_type,int connectionType) {
		  this.url=url;
		  this.contex=contex;
		  this.action_type=action_type;
		  this.json=json;
		  this.connectionType=connectionType;
	  }

	  @Override
	  protected void onPreExecute() {
	   super.onPreExecute();

	   dialog = new ProgressDialog(contex);
	   dialog.setMessage("Loading..");
	   dialog.setTitle("");
	   dialog.setCancelable(false);
	   dialog.show();
	  }

	  @Override
	  protected void onPostExecute(Void result) {
	   super.onPostExecute(result);
	   /*SignUpResult resultReturn = new SignUpResult();
	  
	   resultReturn.setStatus(Constants.statusOK);
	   resultReturn.sign_up_data=new SignUpData();
	   resultReturn.sign_up_data.seteMail("jh");*/
	   dialog.dismiss();
	   onNotifyGetResponse = (OnNotifyGetResponse) contex;

	   onNotifyGetResponse.setOnNotifyGetResponse(resultReturn,action_type);
	   
	  
	  
	  }

	  @Override
	  protected Void doInBackground(Void... params) {
//		  String url="http://dev.bushubapi.risedm.com/api/Settings/AppRegistration";
		  System.out.println("URL : "+url);
//		  resultReturn=new Result();
			ConnectionProcess cProcess=new ConnectionProcess(contex, url, json);
			String response;
			if(connectionType==Constants.CONNECT_GET)
				response=cProcess.getData();
			else if(connectionType==Constants.CONNECT_POST)
				response=cProcess.postData();
			else
				response=cProcess.putData();
			System.out.println("Response : "+response);
			if(AppUtil.status.contains("200")){
			
			ParseResponse parse=new ParseResponse(action_type, response);
			resultReturn =parse.parseData();
			resultReturn.setStatus(Constants.statusOK);
			}else{
				resultReturn.setStatus(Constants.statusERROR);
			}
//			System.out.println("parse done");
	   return null;
	  }

	 }


	

