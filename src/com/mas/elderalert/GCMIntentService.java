package com.mas.elderalert;

import com.mas.elderalert.R;
import com.mas.elderalert.R.drawable;
import com.mas.elderalert.gcm.PostGCMRegID;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.LocalBroadcastManager;
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
		String name = arg1.getExtras().getString("1");
		String status = arg1.getExtras().getString("2");
		Log.v("GCMService","Message received");

		NotificationCompat.Builder mBuilder =
				new NotificationCompat.Builder(this)
		.setSmallIcon(R.drawable.ic_launcher)
		.setContentTitle("ElderAlert")
		.setContentText("ALERT!: " + name + "'s status is " + status);
		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(this, MainActivity.class);

		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(MainActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
				stackBuilder.getPendingIntent(
						0,
						PendingIntent.FLAG_UPDATE_CURRENT
						);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager =
				(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(2, mBuilder.build());

		sendMessage(name, status);


	}

	private void sendMessage(String inputName,String inputStatus) {
		Log.d("sender", "Broadcasting message");
		Intent intent = new Intent("custom-event-name");
		// You can also include some extra data.
		intent.putExtra("name",inputName);
		intent.putExtra("status",inputStatus);
		LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	}

	@Override
	protected void onRegistered(Context arg0, String arg1) {
		// TODO Auto-generated method stub
		Log.v(TAG, "Device registered: regId = " + arg1);
		Toast.makeText(arg0, "Your device registred with GCM",Toast.LENGTH_SHORT).show();
		
		new PostGCMRegID(arg0).execute(arg1);

	}

	@Override
	protected void onUnregistered(Context arg0, String arg1) {
		// TODO Auto-generated method stub
		Log.v(TAG, "Device unregistered");

	}

}
