package com.tin.shack.auth.providers;

import android.app.Activity;
import android.content.Intent;

import com.tin.shack.auth.ProviderLoginCallback;

import static com.tin.shack.auth.providers.AuthActivity.PROVIDER_NAME;

/**
 * Created by aayushsubedi on 7/23/17.
 */

public class FacebookAuthProvider extends AuthProvider {
	
	private String mErrorMessage = "";
	
	ProviderLoginCallback mCallback;
	
	@Override
	public String getName() {
		return "facebook";
	}
	
	@Override
	public void requestLogin(Activity parent, ProviderLoginCallback callback) {
		LogInDelegate.register(this);
		mCallback = callback;
		Intent loginIntent = new Intent(parent, FacebookAuthActivity.class);
		loginIntent.putExtra(PROVIDER_NAME, getName());
		
		parent.startActivity(loginIntent);
		mErrorMessage = "";
	}
	
	@Override
	public void setError(String message) {
		mErrorMessage = message;
	}
	
	@Override
	public void notifyLoginSuccess() {
		if (mCallback != null) mCallback.onProviderLoginSuccess(mCredential);
	}
	@Override
	public void notifyLoginFailure() {
		if (mCallback != null) mCallback.onProviderLoginFailure(mErrorMessage);
	}
	
	public static AuthProvider getProvider() {
		return new FacebookAuthProvider();
	}
}
