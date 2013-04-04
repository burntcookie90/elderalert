package com.mas.elderalert.account;

import static android.accounts.AccountManager.ACTION_AUTHENTICATOR_INTENT;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Service used by our application to utilize Android's Account Manager
 * @author vishnu
 *
 */
public class AuthenticationService extends Service {

	/**
	 * 
	 */
	public AuthenticationService() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
//	@Override
//	public IBinder onBind(Intent intent) {
//		// TODO Auto-generated method stub
//		return new AccountAuthenticator(this).getIBinder();
//	}

	private static AccountAuthenticator AUTHENTICATOR;

	public void onCreate(){
		Log.v("AuthenticationService","Auth Created");
	}
    public IBinder onBind(Intent intent) {
        return intent.getAction().equals(ACTION_AUTHENTICATOR_INTENT) ? getAuthenticator()
                .getIBinder() : null;
    }

    private AccountAuthenticator getAuthenticator() {
        if (AUTHENTICATOR == null)
            AUTHENTICATOR = new AccountAuthenticator(this);
        return AUTHENTICATOR;
    }
}
