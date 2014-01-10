package com.goclio.challenge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LoginActivity extends Activity {
	private String accessToken;
	private WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		webView = (WebView) findViewById(R.id.webView);
		accessToken = getPreferences(Context.MODE_PRIVATE)
				.getString(Config.PREF_KEY_TOKEN, null);
		
		webView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if(url.equalsIgnoreCase(Config.OAUTH_TOKEN_URL)) {
					
				}
				else if (url.contains(Config.OAUTH_URL_APPROVAL)) {

					// extract OAuth2 access_token appended in url
					int start = url.indexOf("code=");
					if (start != -1) {
						int end = url.indexOf("&state=");
						accessToken = url.substring(start + 5, end);

						// store in default SharedPreferences
						Editor e = getPreferences(
								Context.MODE_PRIVATE).edit();
						e.putString(Config.PREF_KEY_TOKEN, accessToken);
						e.commit();

						// spawn worker thread to do api calls to get list of
						// contacts to display
						// new MyWebservicesAsyncTask().execute(accessToken);
					}
					System.out.println(accessToken);
					new RequestTokenAsyncTask().execute(accessToken);
					// don't go to redirectUri
					return true;
				}

				// load the webpage from url: login and grant access
				return super.shouldOverrideUrlLoading(view, url); // return
																	// false;
			}
		});
		webView.loadUrl(Config.OAUTH_URL_REQUEST_CODE);
	}
	
	private class RequestTokenAsyncTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... strings) {
//			postData(strings[0]);
			postWebView(strings[0]);
			return null;
		}
    
	}
	
	public void postData(String authorizationCode) {
	    // Create a new HttpClient and Post Header
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost(Config.OAUTH_TOKEN_URL);
	    httppost.setHeader("Content-Type",
        "application/x-www-form-urlencoded");
	    /*
	    client_id:     application key from above
	    client_secret: application secret from above
	    grant_type:    "authorization_code""
	    code:          Authorization code from the redirect above
	    redirect_uri:  Redirect URI used in the authorization request above
	    */
	    try {
	        // Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
	        nameValuePairs.add(new BasicNameValuePair("client_id", Config.APP_KEY));
	        nameValuePairs.add(new BasicNameValuePair("client_secret", Config.APP_SECRET));
	        nameValuePairs.add(new BasicNameValuePair("grant_type", "authorization_code"));
	        nameValuePairs.add(new BasicNameValuePair("code", authorizationCode));
	        nameValuePairs.add(new BasicNameValuePair("redirect_uri", Config.OAUTH_REDIRECT_URL));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httppost);
	        BufferedReader r = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

	        StringBuilder total = new StringBuilder();

	        String line = null;

	        while ((line = r.readLine()) != null) {
	           total.append(line);
	        }
	        System.out.println(total);
	        
	        
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    }
	} 
	
	public void postWebView(String code) {
		
	    String postData = "client_id=" + Config.APP_KEY + "&client_secret=" + Config.APP_SECRET + "&grant_type=authorization_code&code=" + code + "&redirect_uri=" + Config.OAUTH_REDIRECT_URL;  
	    
	    webView.postUrl(Config.OAUTH_TOKEN_URL, EncodingUtils.getBytes(postData, "base64"));
	}
}
