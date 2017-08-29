package com.tin.shack;

import android.app.Application;

/**
 * Created by aayushsubedi on 8/26/17.
 */

public class ShackApplication extends Application {
	
	private static Application sInstance;
	
	@Override
	public void onCreate() {
		super.onCreate();
		sInstance = this;
	}
	
	public static Application getApplicationInstance(){
		return sInstance;
	}
}
