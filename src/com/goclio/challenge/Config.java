package com.goclio.challenge;

public class Config {
	public static final boolean DEBUG = true; // BuildConfig.DEBUG; beware of potential buildconfig bug?
	/**
	 * Unused, auth is not necessary for this example
	 */
	public static final String APP_KEY = "";
	public static final String APP_SECRET = "";
	public static final String OAUTH_URL_REQUEST_CODE = "";
	public static final String OAUTH_TOKEN_URL = "https://app.goclio.com/oauth/token";
	public static final String OAUTH_URL_APPROVAL = "https://app.goclio.com/oauth/approval";
	public static final String OAUTH_REDIRECT_URL = "http://yourapp.com/callback";
	public static final String PREF_KEY_TOKEN = "token";
	
	
	
}
