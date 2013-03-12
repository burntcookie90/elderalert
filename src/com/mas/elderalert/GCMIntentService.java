package com.mas.elderalert;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class GCMIntentService extends com.google.android.gcm.GCMBaseIntentService{
	public static String senderIds= "772989319697";

	public GCMIntentService() {
		// TODO Auto-generated constructor stub
		super(senderIds);
	}

	@Override
	protected void onError(Context arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onMessage(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		String message = arg1.getExtras().getString("1");
		Log.v("Service","Message received");
		Toast.makeText(this,message, Toast.LENGTH_SHORT).show();

		
	}

	@Override
	protected void onRegistered(Context arg0, String arg1) {
		// TODO Auto-generated method stub
		Log.i(TAG, "Device registered: regId = " + arg1);
        Toast.makeText(arg0, "Your device registred with GCM",Toast.LENGTH_SHORT).show();
		
	}

	@Override
	protected void onUnregistered(Context arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

}
