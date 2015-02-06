package com.onboardapp.asynctask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.onboardapp.util.AppUtil;

public class ConnectionProcess {

public ConnectionProcess(Context context,String url,JSONObject postJson) {
		super();
		this.context = context;
		this.url=url;
		this.postJson=postJson;
		
	}



	String url="";
	
	Context context;
	JSONObject postJson;
	String statusLine="200";
	private String connectWS()
	{
		String response="";
		
		return response;
	}
	
	public String postData()
	{
		// Create a new HttpClient and Post Header
		 String responseText = null;
	   /* HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost(url);
*/
	    try {
	    	
//	    	System.out.println("postJson.toString() : "+postJson.toString());
	       
	    	DefaultHttpClient httpclient = new DefaultHttpClient();
	    	HttpPost httppostreq = new HttpPost(url);
	    	 
	    	StringEntity se = new StringEntity(postJson.toString());
	    	se.setContentType("application/json;charset=UTF-8");
	    	se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));

	    	se.setContentEncoding(new BasicHeader("api-key","f3590938-9ad9-4168-9646-d02b67a95103"));
	    	httppostreq.setEntity(se);
	    	HttpResponse httpresponse = httpclient.execute(httppostreq);
	    	statusLine=httpresponse.getStatusLine().toString();
	    	AppUtil.status=httpresponse.getStatusLine().toString();
	    	System.out.println("===Status==="+httpresponse.getStatusLine());
	    	
	    	responseText = EntityUtils.toString(httpresponse.getEntity());
	    	 /*httppost.setHeader("api-key", "f3590938-9ad9-4168-9646-d02b67a95103");
	    	 httppost.setHeader("Content-Type", "application/json");
	    	 httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        // Execute HTTP Post Request
//	        HttpResponse response = httpclient.execute(httppost);
	        
	        ResponseHandler<String> responseHandler = new BasicResponseHandler();
	        String responseBody = httpclient.execute(httppost, responseHandler);*/

	        Log.d("DEBUG", "RESPONSE 1 : " + responseText);
	       
	        try {
//	        responseText = EntityUtils.toString(response.getEntity());
	        System.out.println("Response 2 : "+responseText);
	        
//	        if(!statusLine.contains("200"))
//	        	responseText=statusLine;
	        return responseText;
	        
	        }catch (ParseException e) {
	        e.printStackTrace();
	        Log.i("Parse Exception", e + "");
	        }
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    }
		return responseText;
	}
	
	public String putData()
	{
		// Create a new HttpClient and Post Header
		 String responseText = null;
	   /* HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost(url);
*/
	    try {
	    	
	    	System.out.println("postJson.toString() : "+postJson.toString());
	       
	    	DefaultHttpClient httpclient = new DefaultHttpClient();
	    	HttpPut httppostreq = new HttpPut(url);
	    	 
	    	StringEntity se = new StringEntity(postJson.toString());
	    	se.setContentType("application/json;charset=UTF-8");
	    	se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));

	    	se.setContentEncoding(new BasicHeader("api-key","f3590938-9ad9-4168-9646-d02b67a95103"));
	    	httppostreq.setEntity(se);
	    	HttpResponse httpresponse = httpclient.execute(httppostreq);
//	    	responseText = EntityUtils.toString(httpresponse.getEntity());
	    	InputStream is;
	    	StringBuffer sb = null;
	    	if (httpresponse.getStatusLine().getStatusCode()  == 204) 
            {
               /* is = httpresponse.getEntity().getContent();
                int ch;
                sb = new StringBuffer();
                while ((ch = is.read()) != -1) {
                    sb.append((char) ch);
                }*/
                // Log sb . it prints the response you get.
	    		
	    		try {
					JSONObject jsonobj = new JSONObject();
					jsonobj.put("Status", "1");
					responseText=jsonobj.toString();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		
            }else
            {
            	try {
					JSONObject jsonobj = new JSONObject();
					jsonobj.put("Status", "0");
					responseText=jsonobj.toString();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		
            }
	    	
//	    	responseText=sb.toString();
	        Log.d("DEBUG", "Put RESPONSE 1 : " + responseText);
	       
	        try {
//	        responseText = EntityUtils.toString(response.getEntity());
	        System.out.println("put Response 2 : "+responseText);
	        
	        return responseText;
	        
	        }catch (ParseException e) {
	        e.printStackTrace();
	        Log.i("Parse Exception", e + "");
	        }
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    }
		return responseText;
	}
	 
	public String getData()
	{
		 String responseText = null;

		    BufferedReader in = null;
		    String data = "";

		    try {
		        HttpClient client = new DefaultHttpClient();
//		        client.getConnectionManager().getSchemeRegistry().register(getMockedScheme());

		        URI website = new URI(url); 
		        HttpGet request = new HttpGet();
		        request.setURI(website);
//		        request.addHeader("api-key", "f3590938-9ad9-4168-9646-d02b67a95103");
		        HttpResponse response = client.execute(request);
		        statusLine=response.getStatusLine().toString();
		        System.out.println("===Status==="+response.getStatusLine());
		        AppUtil.status=response.getStatusLine().toString();
//		        response.getStatusLine().getStatusCode();

		        if (!AppUtil.status.contains("No Content")) {
					in = new BufferedReader(new InputStreamReader(response
							.getEntity().getContent()));
					StringBuffer sb = new StringBuffer("");
					String l = "";
					String nl = System.getProperty("line.separator");
					while ((l = in.readLine()) != null) {
						sb.append(l + nl);
					}
					in.close();
					data = sb.toString();
				}
//				System.out.println("Get Response : "+data);
//		        if(!statusLine.contains("200"))
//		        	data=statusLine;
		        return data;
		    } catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
		        if (in != null) {
		            try {
		                in.close();
		                return data;
		            } catch (Exception e) {
		                Log.e("GetMethodEx", e.getMessage());
		            }
		        }
		    }
//		    if(!statusLine.contains("200"))
//	        	responseText=statusLine;
		return responseText;
	}
	
	
	
}
