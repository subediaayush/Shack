package com.tin.shack.auth;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by aayushsubedi on 7/23/17.
 */
public interface FirebaseLoginCallback {
	void onLoginSuccess(FirebaseUser user);
	
	void onLoginFailure(String errorMessage);
}
