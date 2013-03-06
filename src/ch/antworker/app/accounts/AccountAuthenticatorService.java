package ch.antworker.app.accounts;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AccountAuthenticatorService extends Service {
  private static final String TAG = "AccountAuthenticator";
  private static AccountAuthenticator ACCOUNT_AUTHENTICATOR_INSTANCE;

  public IBinder onBind(Intent intent) {
    IBinder ret = null;
    if(intent.getAction().equals(android.accounts.AccountManager.ACTION_AUTHENTICATOR_INTENT))
      ret = getAuthenticator().getIBinder();
    return ret; 
  }

  private AccountAuthenticator getAuthenticator() {
    if(ACCOUNT_AUTHENTICATOR_INSTANCE == null) 
      ACCOUNT_AUTHENTICATOR_INSTANCE = new AccountAuthenticator(this);
    return ACCOUNT_AUTHENTICATOR_INSTANCE;
  }
}
