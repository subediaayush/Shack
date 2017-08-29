package com.tin.shack.auth.providers;

import com.google.firebase.auth.AuthCredential;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aayushsubedi on 7/23/17.
 */

class LogInDelegate {
	
	private static Map<String, AuthProvider> sDelegateMap = new HashMap<>();
	
	public static void register(AuthProvider authProvider) {
		sDelegateMap.put(authProvider.getName(), authProvider);
	}
	
	public static void setLogInCredential(String name, AuthCredential credential) {
		AuthProvider provider = sDelegateMap.get(name);
		if (provider != null) {
			provider.setCredential(credential);
			provider.notifyLoginSuccess();
		}
	}
	
	public static void setLogInError(String name, String message) {
		AuthProvider provider = sDelegateMap.get(name);
		if (provider != null) {
			provider.setError(message);
			provider.notifyLoginFailure();
		}
	}
}
