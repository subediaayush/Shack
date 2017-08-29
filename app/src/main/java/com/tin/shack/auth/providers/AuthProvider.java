package com.tin.shack.auth.providers;

import android.app.Activity;

import com.google.firebase.auth.AuthCredential;
import com.tin.shack.auth.ProviderLoginCallback;

/**
 * Created by aayushsubedi on 7/23/17.
 */

public abstract class AuthProvider {
	
	protected AuthCredential mCredential;
	
	public void setCredential(AuthCredential credential) {
		mCredential = credential;
	}
	
	public AuthCredential getAuthCredentials() {
		return mCredential;
	}
	
	public abstract String getName();
	
	public abstract void requestLogin(Activity parent, ProviderLoginCallback callback);
	
	public abstract void setError(String message);
	
	public abstract void notifyLoginSuccess();
	public abstract void notifyLoginFailure();
}
