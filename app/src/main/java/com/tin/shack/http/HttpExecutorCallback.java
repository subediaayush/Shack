package com.tin.shack.http;

/**
 * Created by aayushsubedi on 8/26/17.
 */

interface HttpExecutorCallback {
	void onResult(boolean success, String result);
}
