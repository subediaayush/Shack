package com.tin.shack.auth.firebase;

import android.content.Context;

/**
 * Created by aayushsubedi on 7/23/17.
 */

public class FirebaseLogin {
	
	private static FirebaseLogin sInstance;
	
	public static void initialise(Context context) {
		if (sInstance != null) throw new UnsupportedOperationException(
				"Firebase login already initialised"
		);
		
		sInstance = new FirebaseLogin();
	}
	
	public static FirebaseLogin getInstance() {
		if (sInstance == null) throw new UnsupportedOperationException(
				"Firebase login not initialised"
		);
		
		return sInstance;
	}
	
}
