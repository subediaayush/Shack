package com.tin.shack.auth;

import com.google.firebase.auth.AuthCredential;

/**
 * Created by aayushsubedi on 7/23/17.
 */
public interface ProviderLoginCallback {
	void onProviderLoginSuccess(AuthCredential credential);
	
	void onProviderLoginFailure(String errorMessage);
}
