/**
 * 
 */
package com.mas.elderalert.account;


import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mas.elderalert.AeSimpleSHA1;
import com.mas.elderalert.Constants;
import com.mas.elderalert.MainActivity;
import com.mas.elderalert.R;

/**
 * Activity that displays the UI for logging into the application. Also authenticates with the Account Manager
 * @author vishnu
 *
 */
public class LoginActivity extends AccountAuthenticatorActivity implements OnClickListener{
	private static final String TAG = "Roaming Recruiter " +LoginActivity.class.getSimpleName();                                                                                                                  
	public static final String PARAM_AUTHTOKEN_TYPE = Constants.ACCOUNT_TYPE;
	public static final String PARAM_CREATE = "create";
	public static final int REQ_CODE_CREATE = 1;
	public static final int REQ_CODE_UPDATE = 2;
	public static final String EXTRA_REQUEST_CODE = "req.code";
	public static final int RESP_CODE_SUCCESS = 0;
	public static final int RESP_CODE_ERROR = 1;
	public static final int RESP_CODE_CANCEL = 2;
	private boolean hasErrors;
	private EditText username;
	private EditText password;
	private JSONObject responseObject;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		hasErrors = false;
		setContentView(R.layout.activity_login);

		username = (EditText)findViewById(R.id.login_username);
		password = (EditText)findViewById(R.id.password);

		Button login = (Button) findViewById(R.id.sign_in_button);

		// TODO Just for now, this way we can skip logging for a bit

		login.setOnClickListener(this);

		Button createAccountButton = (Button)findViewById(R.id.register_button);
		createAccountButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(LoginActivity.this, CreateAccountActivity.class);
				startActivity(i);
				LoginActivity.this.finish();

			}
		});
	}
//	@Override
	public void onClick(View v) {
		String loginUsername = username.getText().toString();
		String loginPassword = null;

		try{
			loginPassword = AeSimpleSHA1.SHA1(Constants.SALT+ password.getText().toString());
		}catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  

		Log.v(TAG,"Logging in with "+ loginUsername);
		try {
			String responseJSON = new GetLoginAcct().execute(loginUsername).get();
			JSONArray o = new JSONArray(responseJSON);
			Log.v(TAG,o.toString(1));
			int responseLength = o.length();
			Log.v(TAG,""+responseLength);

			responseObject = o.getJSONObject(0);
			Log.v(TAG,responseObject.toString(1));

			if(responseObject.getString(Constants.USERNAME).equals(loginUsername)){
				if(responseObject.getString(Constants.PASSWORD).equals(loginPassword)){
					System.out.println("Login Successful!");
					Log.d(TAG,"Login Successful!");
					Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_SHORT).show();
					hasErrors = false;
				}
				else{
					System.out.println("Bad password");
					Log.d(TAG,"Bad Password");
					hasErrors = true;
					Toast.makeText(getApplicationContext(), "Bad Password!",Toast.LENGTH_SHORT).show();
				}
			}
			else{
				Log.d(TAG,"Login Unsuccessful");
				hasErrors = true;
				Toast.makeText(getApplicationContext(), "Bad Username!", Toast.LENGTH_SHORT).show();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String accountType = this.getIntent().getStringExtra(PARAM_AUTHTOKEN_TYPE);
		if (accountType == null) {
			accountType = Constants.ACCOUNT_TYPE;
		}

		AccountManager accMgr = AccountManager.get(this);

		if (hasErrors) {

			// handle errors

		} else {

			// This is the magic that adds the account to the Android Account Manager
			final Account account = new Account(loginUsername, accountType);

			try {
				accMgr.addAccountExplicitly(account, AeSimpleSHA1.SHA1(Constants.SALT+loginPassword), null);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Now we tell our caller, could be the Android Account Manager or even our own application
			// that the process was successful

			final Intent intent = new Intent();
			intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, loginUsername);
			intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, accountType);
			intent.putExtra(AccountManager.KEY_AUTHTOKEN, accountType);
			try {
				intent.putExtra(AccountManager.KEY_USERDATA,responseObject.getString(Constants.USER_ID));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.setAccountAuthenticatorResult(intent.getExtras());
			this.setResult(RESULT_OK, intent);

			Intent mapIntent = new Intent(LoginActivity.this, MainActivity.class);
			startActivity(mapIntent);

			this.finish();

		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();  // Always call the superclass

		// Stop method tracing that the activity started during onCreate()
		android.os.Debug.stopMethodTracing();
	}
}