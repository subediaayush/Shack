package com.tin.shack.auth.firebase;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tin.shack.auth.FirebaseLoginCallback;
import com.tin.shack.auth.ProviderLoginCallback;
import com.tin.shack.auth.providers.AuthProvider;
import com.tin.shack.auth.shauth.Shauth;

/**
 * Created by aayushsubedi on 7/23/17.
 */

public class FirebaseLoginService implements ProviderLoginCallback {
	
	private Activity mParent;
	private boolean isSilentRun;
	
	private FirebaseLoginCallback mCallback;
	private AuthProvider mProvider;
	
	protected FirebaseLoginService(){}
	
	public void setCallingActivity(Activity callingActivity) {
		this.mParent = callingActivity;
	}
	
	public void setSilentMode(boolean silentMode) {
		isSilentRun = silentMode;
	}
	
	public void setLoginCallback(FirebaseLoginCallback firebaseLoginCallback) {
		mCallback = firebaseLoginCallback;
	}
	
	public void initiateLogin() {
		if (mParent == null || mProvider == null) throw new UnsupportedOperationException(
				"Could not initiate login. Please check if parent activity and login provider" +
						"both are valid."
		);
		
		mProvider.requestLogin(mParent, this);
	}
	
	public void setLoginProvider(AuthProvider loginProvider) {
		mProvider = loginProvider;
	}
	
	@Override
	public void onProviderLoginSuccess(AuthCredential credential) {
		FirebaseAuth.getInstance().signInWithCredential(credential)
				.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(@NonNull Task<AuthResult> task) {
				if (task.isSuccessful()) {
					if (mCallback != null) {
						FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
						Shauth.requestToken(mParent, user, mCallback);
					}
				} else {
					onProviderLoginFailure(task.getException().getMessage());
				}
			}
		});
	}
	
	@Override
	public void onProviderLoginFailure(String errorMessage) {
		if (mCallback != null) mCallback.onLoginFailure(errorMessage);
	}
	
	public static class Builder {
		FirebaseLoginService mLoginService;
		
		public Builder(Activity activity) {
			mLoginService = new FirebaseLoginService();
			mLoginService.setCallingActivity(activity);
		}
		
		public Builder setSilentMode(boolean silentMode) {
			mLoginService.setSilentMode(silentMode);
			return this;
		}
		
		public Builder setLoginCallback(FirebaseLoginCallback callback) {
			mLoginService.setLoginCallback(callback);
			return this;
		}
		
		public Builder withProvider(AuthProvider provider) {
			mLoginService.setLoginProvider(provider);
			return this;
		}
		
		public void initiate() {
			mLoginService.initiateLogin();
		}
	}
}
