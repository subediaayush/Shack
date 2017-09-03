package com.tin.shack;

import android.app.Application;

import com.tin.shack.auth.preference.PreferenceManager;

/**
 * Created by aayushsubedi on 8/26/17.
 */

public class ShackApplication extends Application {
	
	private static Application sInstance;
	
	@Override
	public void onCreate() {
		super.onCreate();
		sInstance = this;
		
		PreferenceManager.init(this);
	}
	
	public static Application getApplicationInstance(){
		return sInstance;
	}
}
