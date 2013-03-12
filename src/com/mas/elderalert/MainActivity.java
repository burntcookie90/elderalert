package com.mas.elderalert;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.google.android.gcm.GCMRegistrar;

public class MainActivity extends SherlockActivity {
	public final String TAG = MainActivity.class.getSimpleName();
	ListView list;
	LazyAdapter adapter;
	
	  static final String KEY_SONG = "song"; // parent node
	    static final String KEY_ID = "id";
	    static final String KEY_TITLE = "title";
	    static final String KEY_ARTIST = "artist";
	    static final String KEY_DURATION = "duration";
	    static final String KEY_THUMB_URL = "thumb_url";
	    
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
		
		ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put(KEY_ID, "1");
        map.put(KEY_TITLE,"Mother" );
        map.put(KEY_ARTIST,"Status Normal" );
        
		map.put(KEY_ID, "2");
        map.put(KEY_TITLE,"Uncle" );
        map.put(KEY_ARTIST,"Status Normal" );
//        map.put(KEY_DURATION, parser.getValue(e, KEY_DURATION));
//        map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));
        
        songsList.add(map);
        
        list = (ListView) findViewById(R.id.list);
        adapter=new LazyAdapter(this, songsList);
        list.setAdapter(adapter);
        
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
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(this)
		        .setSmallIcon(R.drawable.ic_launcher)
		        .setContentTitle("Elder Alert")
		        .setContentText("ElderAlert is running");
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
		mNotificationManager.notify(1, mBuilder.build());

	}

}
