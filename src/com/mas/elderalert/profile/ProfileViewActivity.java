package com.mas.elderalert.profile;

import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.util.Log;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.mas.elderalert.Constants;
import com.mas.elderalert.R;
import com.mas.elderalert.account.GetLoginAcct;


/**
 * @author vishnu
 *
 */
public class ProfileViewActivity extends SherlockPreferenceActivity {
	public static final String TAG = ProfileViewActivity.class.getSimpleName();
	String userEducation, userYearsOfExp, userEmailAddress, userLastName, userMiddleName, userFirstName;
	private String userJobPref;

	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.profile);
	}

	public void onStart(){
		super.onStart();
		
		// Populate the username of the currently logged in user in the Settings
		AccountManager accountManager = AccountManager.get(getApplicationContext());
		Account[] accounts = accountManager.getAccountsByType(Constants.ACCOUNT_TYPE);

		if (accounts.length > 0) {
			Account account = accounts[0];
			EditTextPreference usernamePreference = (EditTextPreference) getPreferenceScreen().findPreference("pref_profile_username");
			EditTextPreference firstNamePref = (EditTextPreference) getPreferenceScreen().findPreference("pref_f_name");
			EditTextPreference middleNamePref= (EditTextPreference) getPreferenceScreen().findPreference("pref_m_name");
			EditTextPreference lastNamePref= (EditTextPreference) getPreferenceScreen().findPreference("pref_l_name");
			EditTextPreference emailAddrPref= (EditTextPreference) getPreferenceScreen().findPreference("pref_email_addr");
			EditTextPreference yrsOfExpPref = (EditTextPreference) getPreferenceScreen().findPreference("pref_yrs_of_experience");

			ListPreference eduPref = (ListPreference)getPreferenceScreen().findPreference("pref_highest_ed");
			Preference jobPref = findPreference("pref_job_type");

			usernamePreference.setSummary(account.name);
			try {
				String responseJSON = new GetLoginAcct().execute(account.name).get();
				JSONArray o = new JSONArray(responseJSON);
				Log.v(TAG,o.toString(1));
				int responseLength = o.length();
				Log.v(TAG,""+responseLength);

				JSONObject responseObject = o.getJSONObject(0);
				Log.v(TAG,responseObject.toString(1));

				userFirstName = responseObject.getString(Constants.FIRST_NAME);
				firstNamePref.setSummary(userFirstName);
				firstNamePref.setText(userFirstName);

				userMiddleName = responseObject.getString(Constants.MIDDLE_NAME);
				middleNamePref.setSummary(userMiddleName);
				middleNamePref.setText(userMiddleName);

				userLastName = responseObject.getString(Constants.LAST_NAME);
				lastNamePref.setSummary(userLastName);
				lastNamePref.setText(userLastName);

				userEmailAddress = responseObject.getString(Constants.EMAIL_ADDRESS);
				emailAddrPref.setSummary(userEmailAddress);
				emailAddrPref.setText(userEmailAddress);
				
				Log.v(TAG,responseObject.getJSONObject(Constants.USER_ID).getString("$oid"));
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// )TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException e){
				e.printStackTrace();
				Toast.makeText(getApplicationContext(), "No Connection!",Toast.LENGTH_SHORT).show();
				this.finish();
			}
		}
	}
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.profile_menu,menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case R.id.save_profile:
			Toast.makeText(getApplicationContext(), "Saving Profile!",Toast.LENGTH_SHORT).show();
		}
		return false;
	}
	
	@Override
	public void onDestroy() {
	    super.onDestroy();  // Always call the superclass
	    
	    // Stop method tracing that the activity started during onCreate()
	    android.os.Debug.stopMethodTracing();
	}
}
