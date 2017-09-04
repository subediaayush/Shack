package com.tin.shack.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by aayushsubedi on 8/26/17.
 */

public class NetworkUtils {
	public static boolean isNetworkAvailable(Context context){
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
}
