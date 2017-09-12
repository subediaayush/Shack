package com.tin.shack.auth.shauth;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;
import com.tin.shack.auth.firebase.FirebaseTokenManager;
import com.tin.shack.auth.preference.JsonEngine;
import com.tin.shack.auth.preference.PreferenceManager;
import com.tin.shack.http.FetchCallback;
import com.tin.shack.http.HttpExecutor;
import com.tin.shack.http.HttpRequestType;
import com.tin.shack.http.Request;
import com.tin.shack.http.Response;
import com.tin.shack.http.ResponseCallback;
import com.tin.shack.http.response.body.LoginTask;
import com.tin.shack.user.User;

/**
 * Created by aayushsubedi on 8/8/17.
 */

public class Shauth {
	
	public static Shauth getInstance() {
		return new Shauth();
	}
	
	public void requestShackToken(final Context context, final FirebaseUser user, final FetchCallback<String> callback) {
		FirebaseTokenManager.requestFirebaseToken(user, new FirebaseTokenManager.TokenListener() {
			@Override
			public void onTokenFetchSuccess(String token) {
				requestShackToken(context, user, token, callback);
			}
			
			@Override
			public void onTokenFetchFailed() {
				callback.onDataFetchFailed("Could not connect to login service");
			}
		});
	}
	
	private void requestShackToken(Context context, final FirebaseUser user, String foken, final FetchCallback<String> callback) {
		Request request = new Request.RequestBuilder()
				.setType(HttpRequestType.GET)
				.setEndpoint("token")
				.addHeader("token", foken)
				.addDataPath("fuid", user.getUid())
				.build();
		
		HttpExecutor.getInstance(context).execute(request, new ResponseCallback() {
			@Override
			public void onResult(Response response) {
				if (response.isParsadi()) {
					LoginTask loginTask = JsonEngine.getInstance().getData(response.getData(), LoginTask.class);
					PreferenceManager.getPreferences().edit()
							.putString("doken", loginTask.token)
							.putString("uid", loginTask.uid)
							.apply();
					if (callback != null) {
						callback.onDataFetched(loginTask.token);
					}
				} else {
					if (callback != null) {
						callback.onDataFetchFailed(response.getMessage());
					}
				}
			}
		});
	}
	
	public void requestShackUser(Context context, String token, String uid, final FetchCallback<User> callback) {
		Request request = new Request.RequestBuilder()
				.setType(HttpRequestType.GET)
				.setEndpoint("user")
				.addHeader("token", token)
				.addDataPath("uid", uid)
				.build();
		
		HttpExecutor.getInstance(context).execute(request, new ResponseCallback() {
			@Override
			public void onResult(Response response) {
				if (response.isParsadi()) {
					User user = JsonEngine.getInstance().getData(
							response.getData(),
							User.class
					);

					if (callback != null) {
						callback.onDataFetched(user);
					}
				} else {
					if (callback != null) {
						callback.onDataFetchFailed(response.getMessage());
					}
				}
			}
		});
	}
	
	public String getShackToken(Context context) {
		return PreferenceManager.getPreferences().getString("doken", null);
	}
	
	public String getShackUserId(Context context) {
		return PreferenceManager.getPreferences().getString("uid", null);
	}
}
