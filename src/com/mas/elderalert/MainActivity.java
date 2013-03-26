package com.mas.elderalert;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.google.android.gcm.GCMRegistrar;

public class MainActivity extends SherlockActivity {
	public final String TAG = MainActivity.class.getSimpleName();
	ListView list;
	LazyAdapter adapter;
	private HashMap<String, String> map;
	private HashMap<String, String> map2;
	private ArrayList<HashMap<String, String>> relativeList;

	static final String KEY_ID = "id";
	static final String KEY_NAME = "name";
	static final String KEY_STATUS = "status";
	static final String KEY_COLOR = "color";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		final String regId = GCMRegistrar.getRegistrationId(this);
		if (regId.equals("")) {
			GCMRegistrar.register(this, "772989319697");
		} else {
			Log.v(TAG, "Already registered "+regId);
		}

		relativeList = new ArrayList<HashMap<String, String>>();

		map = new HashMap<String, String>();
		map2 = new HashMap<String, String>();

		map.put(KEY_ID, "1");
		map.put(KEY_NAME,"Mother");
		map.put(KEY_STATUS,"Status Normal");
		map.put(KEY_COLOR, "reg");

		map2.put(KEY_ID, "2");
		map2.put(KEY_NAME,"Uncle" );
		map2.put(KEY_STATUS,"Status Normal");
		map2.put(KEY_COLOR,"reg");
		
		//        map.put(KEY_DURATION, parser.getValue(e, KEY_DURATION));
		//        map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));

		relativeList.add(map);
		relativeList.add(map2);

		list = (ListView) findViewById(R.id.list);
		adapter=new LazyAdapter(this, relativeList);
		list.setAdapter(adapter);

		LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
				new IntentFilter("custom-event-name"));

	}

	private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// Get extra data included in the Intent
			String name = intent.getStringExtra("name");
			String status = intent.getStringExtra("status");
			
			for(int i = 0;i<relativeList.size();i++){
				if(relativeList.get(i).get("name").equals(name)){
					Log.v(TAG,"FOUND RELATIVE");
					relativeList.get(i).put("status", "Status: " + status);
					if(status.equals("Abnormal"))
						relativeList.get(i).put("color","elevate");
					else
						relativeList.get(i).put("color","reg");
					adapter.notifyDataSetChanged();
				}
			}
			Log.d("receiver", "Got message: " + name);
		}
	};
	
	@Override
	protected void onDestroy() {
	  // Unregister since the activity is about to be closed.
	  LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
	  super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		//		NotificationCompat.Builder mBuilder =
		//				new NotificationCompat.Builder(this)
		//		.setSmallIcon(R.drawable.ic_launcher)
		//		.setContentTitle("Elder Alert")
		//		.setContentText("ElderAlert is running");
		//		// Creates an explicit intent for an Activity in your app
		//		Intent resultIntent = new Intent(this, MainActivity.class);
		//
		//		// The stack builder object will contain an artificial back stack for the
		//		// started Activity.
		//		// This ensures that navigating backward from the Activity leads out of
		//		// your application to the Home screen.
		//		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		//		// Adds the back stack for the Intent (but not the Intent itself)
		//		stackBuilder.addParentStack(MainActivity.class);
		//		// Adds the Intent that starts the Activity to the top of the stack
		//		stackBuilder.addNextIntent(resultIntent);
		//		PendingIntent resultPendingIntent =
		//				stackBuilder.getPendingIntent(
		//						0,
		//						PendingIntent.FLAG_UPDATE_CURRENT
		//						);
		//		mBuilder.setContentIntent(resultPendingIntent);
		//		NotificationManager mNotificationManager =
		//				(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		//		// mId allows you to update the notification later on.
		//		mNotificationManager.notify(1, mBuilder.build());

	}

}
