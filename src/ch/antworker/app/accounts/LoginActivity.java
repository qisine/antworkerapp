package ch.antworker.app.accounts;

import ch.antworker.app.R;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

import ch.antworker.app.Utils;

public class LoginActivity extends AccountAuthenticatorActivity {
  private static final String TAG = "AccountAuthenticatorActivity";

  public static final String PARAM_CONFIRM_CREDENTIALS = "confirmCredentials";
  public static final String PARAM_USERNAME = "firstparam";
  public static final String PARAM_PASSWORD = "secondparam";
  public static final String PARAM_AUTHTOKEN_TYPE = "authtokenType";

  private AccountManager mAccountManager;

  private UserLoginTask mAuthTask = null;

  private ProgressDialog mProgressDialog = null;

  private Boolean mConfirmCredentials = false;

  private final Handler mHandler = new Handler();

  private TextView mMessage;

  private String mPassword;

  private EditText mPasswordEdit;

  private boolean mRequestNewAccount = false;

  private String mUsername;

  private EditText mUsernameEdit;

  public void onCreate(Bundle bundle) {
    super.onCreate(bundle);

    mAccountManager = AccountManager.get(this);
    final Intent intent = getIntent();
    mUsername = intent.getStringExtra(PARAM_USERNAME);
    mRequestNewAccount = mUsername == null;
    mConfirmCredentials = intent.getBooleanExtra(PARAM_CONFIRM_CREDENTIALS, false);
    
    requestWindowFeature(Window.FEATURE_LEFT_ICON);
    setContentView(R.layout.login_activity);
    getWindow().setFeatureDrawableResource(
            Window.FEATURE_LEFT_ICON, android.R.drawable.ic_dialog_alert);
    mMessage = (TextView) findViewById(R.id.message);
    mUsernameEdit = (EditText) findViewById(R.id.username_edit);
    mPasswordEdit = (EditText) findViewById(R.id.password_edit);
    if (!TextUtils.isEmpty(mUsername)) mUsernameEdit.setText(mUsername);

    CheckBox cb = (CheckBox) findViewById(R.id.show_password);
    cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int type = android.text.InputType.TYPE_CLASS_TEXT;
        if (isChecked)
          type |= android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
        else
          type |= android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;
        int selection = mPasswordEdit.getSelectionStart();
        mPasswordEdit.setInputType(type);
        if (selection > 0) mPasswordEdit.setSelection(selection);
      }
    });
    mMessage.setText(getMessage());
  }

  @Override
  protected Dialog onCreateDialog(int id, Bundle bundle) {
    final ProgressDialog dialog = new ProgressDialog(this);
    dialog.setMessage(getText(R.string.ui_activity_authenticating));
    dialog.setIndeterminate(true);
    dialog.setCancelable(true);
    dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
      public void onCancel(DialogInterface dialog) {
        if (mAuthTask != null) {
          mAuthTask.cancel(true);
        }
      }
    });
    
    mProgressDialog = dialog;
    return dialog;
  }

  public void handleLogin(View view) {
    if (mRequestNewAccount) {
      mUsername = mUsernameEdit.getText().toString();
    }

    mPassword = mPasswordEdit.getText().toString();
    if (TextUtils.isEmpty(mUsername) || TextUtils.isEmpty(mPassword)) {
      mMessage.setText(getMessage());
    } else {
      showDialog(0);
      mAuthTask = new UserLoginTask();
      mAuthTask.execute();
    }
  }

  private void finishConfirmCredentials(boolean result) {
    final Account account = new Account(mUsername, Utils.ACCOUNT_TYPE);
    mAccountManager.setPassword(account, mPassword);
    final Intent intent = new Intent();
    intent.putExtra(AccountManager.KEY_BOOLEAN_RESULT, result);
    setAccountAuthenticatorResult(intent.getExtras());
    setResult(RESULT_OK, intent);
    finish();
  }

  private void finishLogin(String authToken) {
    final Intent intent = new Intent();
    intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, mUsername);
    intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, Utils.ACCOUNT_TYPE);
    setAccountAuthenticatorResult(intent.getExtras());
    setResult(RESULT_OK, intent);
    finish();
  }

  public void onAuthenticationResult(String authToken) {
    boolean success = ((authToken != null) && (authToken.length() > 0));

    mAuthTask = null;

    hideProgress();

    if (success) {
      if (!mConfirmCredentials) {
        finishLogin(authToken);
      } else {
        finishConfirmCredentials(success);
      }
    } else {
      if (mRequestNewAccount) {
        mMessage.setText(getText(R.string.login_activity_loginfail_text_both));
      } else {
        mMessage.setText(getText(R.string.login_activity_loginfail_text_pwonly));
      }
    }
  }

  public void onAuthenticationCancel() {
    mAuthTask = null;
    hideProgress();
  }

  private CharSequence getMessage() {
    if (TextUtils.isEmpty(mUsername)) {
      final CharSequence msg = getText(R.string.login_activity_newaccount_text);
      return msg;
    }
    if (TextUtils.isEmpty(mPassword)) {
      return getText(R.string.login_activity_loginfail_text_pwmissing);
    }
    return null;
  }

  private void hideProgress() {
    if (mProgressDialog != null) {
      mProgressDialog.dismiss();
      mProgressDialog = null;
    }
  }

  public class UserLoginTask extends AsyncTask<Void, Void, String> {

    @Override
    protected String doInBackground(Void... params) {
      try {
        return Utils.authenticate(mUsername, mPassword);
      } catch (Exception ex) {
        Log.e(TAG, "UserLoginTask.doInBackground: failed to authenticate");
        Log.i(TAG, ex.toString());
        return null;
      }
    }

    @Override
    protected void onPostExecute(final String authToken) {
      onAuthenticationResult(authToken);
    }

    @Override
    protected void onCancelled() {
      onAuthenticationCancel();
    }
  }
}
