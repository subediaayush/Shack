package com.tin.shack.auth.shauth;

import com.google.firebase.auth.FirebaseUser;
import com.tin.shack.auth.FirebaseLoginCallback;
import com.tin.shack.auth.firebase.FirebaseTokenManager;
import com.tin.shack.auth.preference.PreferenceManager;
import com.tin.shack.http.HttpExecutor;
import com.tin.shack.http.HttpRequestType;
import com.tin.shack.http.Request;
import com.tin.shack.http.Response;
import com.tin.shack.http.ResponseCallback;

/**
 * Created by aayushsubedi on 8/8/17.
 */

public class Shauth {
	public static void requestToken(final FirebaseUser user, final FirebaseLoginCallback callback) {
		FirebaseTokenManager.requestToken(user, new FirebaseTokenManager.TokenListener() {
			@Override
			public void onTokenFetchSuccess(String token) {
				requestShackToken(user, token, callback);
			}
			
			@Override
			public void onTokenFetchFailed() {
				callback.onLoginFailure("Could not connect to login service");
			}
		});
	}
	
	private static void requestShackToken(final FirebaseUser user, String foken, final FirebaseLoginCallback callback) {
		Request request = new Request.RequestBuilder()
				.setType(HttpRequestType.GET)
				.addHeader("token", foken)
				.addDataPath("fuid", user.getUid())
				.build();
		
		HttpExecutor.getInstance().execute(request, new ResponseCallback() {
			@Override
			public void onResult(Response response) {
				if (response.isParsadi()) {
					String doken = response.getData().get("token");
					String uid = response.getData().get("uid");
					PreferenceManager.getPreferences().edit()
							.putString("doken", doken)
							.putString("uid", uid)
							.apply();
					if (callback != null) {
						callback.onLoginSuccess(user);
					}
				} else {
					if (callback != null) {
						callback.onLoginFailure(response.getMessage());
					}
				}
			}
		});
	}
}
