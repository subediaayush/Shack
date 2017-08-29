package com.tin.shack.auth.providers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.tin.shack.R;

import java.util.Collections;

/**
 * Created by aayushsubedi on 7/23/17.
 */

public class FacebookAuthActivity extends AuthActivity {
	
	LoginManager mLoginManager;
	CallbackManager mCallbackManager;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(R.style.Transparent);
		
		mCallbackManager = CallbackManager.Factory.create();
		mLoginManager = LoginManager.getInstance();
		mLoginManager.logInWithReadPermissions(this, Collections.singletonList("public_profile"));
		mLoginManager.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
			@Override
			public void onSuccess(LoginResult loginResult) {
				LogInDelegate.setLogInCredential(
						getProviderName(),
						getCredentials(loginResult)
				);
				finish();
			}
			
			@Override
			public void onCancel() {
				String message = "Login was cancelled";
				LogInDelegate.setLogInError(
						getProviderName(),
						message
				);
				finish();
			}
			
			@Override
			public void onError(FacebookException error) {
				String message = error.getMessage();
				LogInDelegate.setLogInError(
						getProviderName(),
						message
				);
				finish();
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		mCallbackManager.onActivityResult(requestCode, resultCode, data);
	}
	
	private AuthCredential getCredentials(LoginResult result) {
		AccessToken token = result.getAccessToken();
		return FacebookAuthProvider.getCredential(token.getToken());
	}
}
