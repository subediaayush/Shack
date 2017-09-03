package com.tin.shack.auth.firebase;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.tin.shack.auth.preference.PreferenceManager;

/**
 * Created by aayushsubedi on 9/3/17.
 */

public class FirebaseTokenManager {
	public static void requestToken(TokenListener listener) {
		requestToken(FirebaseAuth.getInstance().getCurrentUser(), listener);
	}
	
	public static void requestToken(FirebaseUser user, final TokenListener listener) {
		user.getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
			@Override
			public void onComplete(@NonNull Task<GetTokenResult> task) {
				if (task.isSuccessful()) {
					String token = task.getResult().getToken();
					PreferenceManager.getPreferences().edit()
							.putString("foken", token).apply();
					
					if (listener != null) {
						listener.onTokenFetchSuccess(token);
					}
				} else {
					PreferenceManager.getPreferences().edit()
							.remove("foken").apply();
					if (listener != null) {
						listener.onTokenFetchFailed();
					}
				}
			}
		});
	}
	
	public interface TokenListener {
		void onTokenFetchSuccess(String token);
		void onTokenFetchFailed();
	}
}
