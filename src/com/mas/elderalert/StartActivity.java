/**
 * 
 */
package com.mas.elderalert;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.google.android.gcm.GCMRegistrar;
import com.mas.elderalert.account.LoginActivity;
/**
 * Activity that launches the application.
 * Checks if user is already logged in on the device, if so shows the main activity
 * Otherwise loads the login screen
 * @author vishnu
 *
 */
public class StartActivity extends SherlockActivity {

	private AccountManager accountManager;
	public static final String TAG = StartActivity.class.getSimpleName();

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onStart(){
		super.onStart();
		
		accountManager = AccountManager.get(getApplicationContext());
        Account[] accounts = accountManager.getAccountsByType(Constants.ACCOUNT_TYPE);
        
        //Checks if an account exists. If it doesn't, prompt the user for login.
        if(accounts.length==0){
        	Log.v(TAG, "No accounts found on device");
        	Toast.makeText(getApplicationContext(),"No accounts found on device!",Toast.LENGTH_SHORT).show();
        	Intent i = new Intent(StartActivity.this,LoginActivity.class);
        	startActivity(i);
        	this.finish();
        }
        else{
        	Account account = accounts[0];
        	Toast.makeText(getApplicationContext(),"Account found on device!",Toast.LENGTH_SHORT).show();
        	Intent intent = new Intent(StartActivity.this,MainActivity.class);
        	intent.putExtra("account", account);
        	startActivity(intent);
        	this.finish(); 		//remove this decision making activity from the stack.
        }
	}
	
	@Override
	public void onDestroy() {
	    super.onDestroy();  // Always call the superclass
	    
	    // Stop method tracing that the activity started during onCreate()
	    android.os.Debug.stopMethodTracing();
	}
}
