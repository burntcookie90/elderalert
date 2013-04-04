/**
 * 
 */
package com.mas.elderalert.gcm;

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
				Log.v(TAG,account.name);
			}
			
//			URL url = new URL("https://api.mongolab.com/api/1/databases/elderalert/collections/users-relatives?apiKey=507989d2e4b0e9396b4a4a90");
//			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//
//			conn.setRequestMethod("POST");
//			conn.setDoOutput(true);
//			conn.setDoInput(true);
//			conn.setUseCaches(false);
//			conn.setAllowUserInteraction(false);
//			conn.setRequestProperty("Content-Type","application/json");
//
//			OutputStream out = conn.getOutputStream();
//
//			out.write(params[0].getBytes());
//			out.flush();
//
//			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
//				throw new RuntimeException("Failed : HTTP error code : "
//						+ conn.getResponseCode());
//			}
//
//			BufferedReader br = new BufferedReader(new InputStreamReader(
//					(conn.getInputStream())));
//
//			String output;
//			String response="";
//			System.out.println("Output from Server .... \n");
//			while ((output = br.readLine()) != null) {
//				response += output;
//			}
//
//			conn.disconnect();
//			return response;
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
