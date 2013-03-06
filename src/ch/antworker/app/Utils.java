package ch.antworker.app;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public final class Utils {
  public static final String TAG = "Utils";

  //App Constants
  public static final String ACCOUNT_TYPE = "ch.antworker";
  public static final String BASE_URL = "http://antworker.ch";

  //Util Constants
  public static final String PARAM_USERNAME = "first_param";
  public static final String PARAM_PASSWORD = "second_param";
  public static final String AUTH_URL = BASE_URL + "/auth";
  public static final int HTTP_REQUEST_TIMEOUT_MS = 500 * 1000;

  //adapted from Android SyncAdapter sample
  public static String authenticate(String username, String password) {
    final HttpResponse resp;
    final ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
    params.add(new BasicNameValuePair(PARAM_USERNAME, username));
    params.add(new BasicNameValuePair(PARAM_PASSWORD, password));

    final HttpEntity entity;
    try {
      entity = new UrlEncodedFormEntity(params);
    }
    catch (UnsupportedEncodingException e) {
      Log.e(TAG, "something went terribly wrong!", e);
      return null;
    }

    final HttpPost post = new HttpPost(AUTH_URL);
    post.addHeader(entity.getContentType());
    post.setEntity(entity);
    try {
        resp = getHttpClient().execute(post);
        String authToken = null;
        if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            InputStream istream = (resp.getEntity() != null) ? resp.getEntity().getContent() : null;
            if (istream != null) {
                BufferedReader ireader = new BufferedReader(new InputStreamReader(istream));
                authToken = ireader.readLine().trim();
            }
        }
        if ((authToken != null) && (authToken.length() > 0)) {
            Log.i(TAG, "success");
            return authToken;
        } else {
            Log.i(TAG, "failed to acquire token");
            return null;
        }
    } catch (final IOException e) {
        Log.e(TAG, "error", e);
        return null;
    } finally {
        Log.i(TAG, "getAuthtoken completing");
    }
  }

  public static HttpClient getHttpClient() {
    HttpClient httpClient = new DefaultHttpClient();
    final HttpParams params = httpClient.getParams();
    HttpConnectionParams.setConnectionTimeout(params, HTTP_REQUEST_TIMEOUT_MS);
    HttpConnectionParams.setSoTimeout(params, HTTP_REQUEST_TIMEOUT_MS);
    ConnManagerParams.setTimeout(params, HTTP_REQUEST_TIMEOUT_MS);
    return httpClient;
  }
}
