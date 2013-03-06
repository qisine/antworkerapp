package ch.antworker.app.accounts;

import android.content.Intent;
import android.accounts.AbstractAccountAuthenticator;  
import android.accounts.Account;  
import android.accounts.AccountManager;  
import android.accounts.AccountAuthenticatorResponse;  
import android.accounts.NetworkErrorException;  
import android.content.Context;  
import android.os.Bundle; 
import android.text.TextUtils;

import ch.antworker.app.Utils;

public class AccountAuthenticator extends AbstractAccountAuthenticator {
  private static final String TAG = "AccountAuthenticator";
  private final Context mContext;

  public AccountAuthenticator(Context context) {
    super(context);
    mContext = context;
  }

  @Override
  public Bundle addAccount(final AccountAuthenticatorResponse response, final String accountType,
    final String authTokenType, final String[] requiredFeatures, final Bundle options)
    throws NetworkErrorException {
    final Bundle b = new Bundle();
    final Intent i = new Intent(mContext, LoginActivity.class);
    i.putExtra(Utils.ACCOUNT_TYPE, authTokenType);
    i.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
    b.putParcelable(AccountManager.KEY_INTENT, i);
    return b;
  }

  @Override
  public Bundle confirmCredentials(final AccountAuthenticatorResponse response,
    final Account account, final Bundle options) {
    return null;
  }

  @Override
  public Bundle editProperties(final AccountAuthenticatorResponse response, final String accountType) {
    return null;
  }

  @Override
  public Bundle getAuthToken(final AccountAuthenticatorResponse response, final Account account,
    final String authTokenType, final Bundle options) throws NetworkErrorException {
    if (!authTokenType.equals(Utils.ACCOUNT_TYPE)) {
      final Bundle result = new Bundle();
      result.putString(AccountManager.KEY_ERROR_MESSAGE, "invalid authTokenType");
      return result;
    }

    final AccountManager am = AccountManager.get(mContext);
    final String password = am.getPassword(account);
    if (password != null) {
      final String authToken = Utils.authenticate(account.name, password);
      if (!TextUtils.isEmpty(authToken)) {
        final Bundle result = new Bundle();
        result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
        result.putString(AccountManager.KEY_ACCOUNT_TYPE, Utils.ACCOUNT_TYPE);
        result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
        return result;
      }
    }

    final Intent i = new Intent(mContext, LoginActivity.class);
    i.putExtra(LoginActivity.PARAM_USERNAME, account.name);
    i.putExtra(LoginActivity.PARAM_AUTHTOKEN_TYPE, authTokenType);
    i.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
    final Bundle bundle = new Bundle();
    bundle.putParcelable(AccountManager.KEY_INTENT, i);
    return bundle;
  }

  @Override
  public String getAuthTokenLabel(final String authTokenType) {
    return null;
  }

  @Override
  public Bundle hasFeatures(final AccountAuthenticatorResponse response, final Account account,
    final String[] features) throws NetworkErrorException {
    final Bundle result = new Bundle();
    result.putBoolean(AccountManager.KEY_BOOLEAN_RESULT, false);
    return result;
  }

  @Override
  public Bundle updateCredentials(final AccountAuthenticatorResponse response, final Account account,
    final String authTokenType, final Bundle options) {
        final Intent i = new Intent(mContext, LoginActivity.class);
        i.putExtra(LoginActivity.PARAM_AUTHTOKEN_TYPE, authTokenType);
        i.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        if (!TextUtils.isEmpty(account.name)) i.putExtra(Utils.PARAM_USERNAME, account.name);

        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, i);
        return bundle;
  }
}
