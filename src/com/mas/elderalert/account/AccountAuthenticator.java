/**
 * 
 */
package com.mas.elderalert.account;

import static android.accounts.AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE;
import static android.accounts.AccountManager.KEY_ACCOUNT_NAME;
import static android.accounts.AccountManager.KEY_ACCOUNT_TYPE;
import static android.accounts.AccountManager.KEY_AUTHTOKEN;
import static android.accounts.AccountManager.KEY_BOOLEAN_RESULT;
import static android.accounts.AccountManager.KEY_INTENT;
import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.mas.elderalert.Constants;

/**
 * @author vishnu
 *
 */
public class AccountAuthenticator extends AbstractAccountAuthenticator {

	private Context mContext;
	/**
	 * @param context
	 */
	public AccountAuthenticator(Context context) {
		super(context);
		mContext = context;
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.accounts.AbstractAccountAuthenticator#addAccount(android.accounts.AccountAuthenticatorResponse, java.lang.String, java.lang.String, java.lang.String[], android.os.Bundle)
	 */
	@Override
	public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options)
			throws NetworkErrorException {

		final Bundle result;
		final Intent intent;

		intent = new Intent(this.mContext, LoginActivity.class);
		intent.putExtra(Constants.AUTH_TOKEN_TYPE, authTokenType);
		intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);

		result = new Bundle();
		result.putParcelable(AccountManager.KEY_INTENT, intent);

		return result;
	}

	/* (non-Javadoc)
	 * @see android.accounts.AbstractAccountAuthenticator#confirmCredentials(android.accounts.AccountAuthenticatorResponse, android.accounts.Account, android.os.Bundle)
	 */
	@Override
	public Bundle confirmCredentials(AccountAuthenticatorResponse response,
			Account account, Bundle options) throws NetworkErrorException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see android.accounts.AbstractAccountAuthenticator#editProperties(android.accounts.AccountAuthenticatorResponse, java.lang.String)
	 */
	@Override
	public Bundle editProperties(AccountAuthenticatorResponse response,
			String accountType) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see android.accounts.AbstractAccountAuthenticator#getAuthToken(android.accounts.AccountAuthenticatorResponse, android.accounts.Account, java.lang.String, android.os.Bundle)
	 */
	@Override
	public Bundle getAuthToken(AccountAuthenticatorResponse response,
			Account account, String authTokenType, Bundle options)
					throws NetworkErrorException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see android.accounts.AbstractAccountAuthenticator#getAuthTokenLabel(java.lang.String)
	 */
	@Override
	public String getAuthTokenLabel(String authTokenType) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see android.accounts.AbstractAccountAuthenticator#hasFeatures(android.accounts.AccountAuthenticatorResponse, android.accounts.Account, java.lang.String[])
	 */
	@Override
	public Bundle hasFeatures(AccountAuthenticatorResponse response,
			Account account, String[] features) throws NetworkErrorException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see android.accounts.AbstractAccountAuthenticator#updateCredentials(android.accounts.AccountAuthenticatorResponse, android.accounts.Account, java.lang.String, android.os.Bundle)
	 */
	@Override
	public Bundle updateCredentials(AccountAuthenticatorResponse response,
			Account account, String authTokenType, Bundle options)
					throws NetworkErrorException {
		// TODO Auto-generated method stub
		final Intent intent = new Intent(mContext, LoginActivity.class);
		intent.putExtra(Constants.AUTH_TOKEN_TYPE, authTokenType);
		intent.putExtra(KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
		if (!TextUtils.isEmpty(account.name))
			intent.putExtra(Constants.USERNAME, account.name);
		final Bundle bundle = new Bundle();
		bundle.putParcelable(KEY_INTENT, intent);
		return bundle;
	}

}
