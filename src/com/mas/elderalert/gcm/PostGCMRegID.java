/**
 * 
 */
package com.mas.elderalert.gcm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.mas.elderalert.Constants;

/**
 * @author vishnu
 *
 */
public class PostGCMRegID extends AsyncTask<String, Void, String> {
	private Context callingContext;	
	public String TAG = PostGCMRegID.class.getSimpleName();

	public PostGCMRegID(Context context) {
		super();
		callingContext = context;
	}

	private AccountManager accountManager;
	private String id;
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try {
			accountManager = AccountManager.get(callingContext);
			Account[] accounts = accountManager.getAccountsByType(Constants.ACCOUNT_TYPE);
			//Checks if an account exists. If it doesn't, prompt the user for login.
			if(accounts.length==0){
				Log.v(TAG, "No accounts found on device");
			}
			else{
				Account account = accounts[0];
				id  = accountManager.getUserData(account, AccountManager.KEY_USERDATA);
				Log.v(TAG,account.name + " id " + id);
			}
			JSONObject _id = new JSONObject();
			_id.put("$oid", id);
			
			JSONObject updateJSON = new JSONObject();
//			updateJSON.put("_id", _id);
			
			JSONObject setJSON = new JSONObject();
			setJSON.put(Constants.GCM_ID,params[0]);
			updateJSON.put("$set",setJSON);
			System.out.println(updateJSON.toString());
			
			String requestUrl = "https://api.mongolab.com/api/1/databases/elderalert/collections/users-relatives?";
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("id",id));
			nameValuePairs.add(new BasicNameValuePair("apiKey", "507989d2e4b0e9396b4a4a90"));
			String paramString = URLEncodedUtils.format(nameValuePairs,"UTF-8");
			requestUrl+=paramString;
			Log.v(TAG,requestUrl);
			URL req = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection)req.openConnection();

			conn.setRequestMethod("PUT");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setAllowUserInteraction(false);
			conn.setRequestProperty("Content-Type","application/json");

			OutputStream out = conn.getOutputStream();

			out.write(updateJSON.toString().getBytes());
			out.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			String response="";
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				response += output;
			}

			conn.disconnect();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
