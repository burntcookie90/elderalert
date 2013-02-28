package com.mas.elderalert;

import android.content.Context;
import android.content.Intent;

import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends com.google.android.gcm.GCMBaseIntentService{
	static String senderIds= "772989319697";

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
		
	}

	@Override
	protected void onRegistered(Context arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onUnregistered(Context arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

}
