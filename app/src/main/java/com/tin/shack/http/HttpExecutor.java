package com.tin.shack.http;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.tin.shack.ShackApplication;

import java.io.File;

import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/**
 * Created by aayushsubedi on 8/8/17.
 */

public class HttpExecutor {
	
	private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/json");
	
	private static final float MAX_AVAILABLE_SPACE_USE_FRACTION = 0.9f;
	private static final float MAX_TOTAL_SPACE_USE_FRACTION = 0.25f;
	
	private static final String BIG_CACHE_PATH = "download-cache";
	private static final int MIN_DISK_CACHE_SIZE = 5 * 1024 * 1024;       // 5MB
	private static final int MAX_DISK_CACHE_SIZE = 20 * 1024 * 1024;      // 20MB
	
	private static HttpExecutor sInstance;
	
	private Gson gson;
	
	private final String host = "www.shack.com";
	private final String[] pathSegments = new String[]{
			"api",
			"v1"
	};
	
	private OkHttpClient client;
	private okhttp3.Request.Builder requestBuilder;
	private HttpAsyncTask executor;
	
	private HttpExecutor(){
		client = new OkHttpClient.Builder()
				.cache(createCache())
				.addNetworkInterceptor(new Interceptors.ResponseCacheInterceptor())
				.addInterceptor(new Interceptors.OfflineResponseCacheInterceptor())
				.addInterceptor(new Interceptors.CurlLoggingInterceptor())
				.build();
		gson = new Gson();
	}
	
	public static HttpExecutor getInstance() {
		if (sInstance == null) sInstance = new HttpExecutor();
		return sInstance;
	}
	
	public void execute(final Request request, final ResponseCallback callback) {
		requestBuilder = new okhttp3.Request.Builder();
		HttpUrl.Builder urlBuilder = new HttpUrl.Builder().scheme("http")
				.host(host);
		
		for (String path : pathSegments) urlBuilder.addPathSegment(path);
		for (String path : request.getPath()) urlBuilder.addPathSegment(path);
		
		if (!TextUtils.isEmpty(request.getEndpoint())) urlBuilder.addPathSegment(request.getEndpoint());

		
		for (String key : request.getHeaders().keySet()) {
			requestBuilder.addHeader(key, request.getHeaders().get(key));
		}
		
		if (request.getType() == HttpRequestType.POST) {
			String data = gson.toJson(request.getData());
			requestBuilder.post(RequestBody.create(MEDIA_TYPE_MARKDOWN, data));
		} else {
			for (String key : request.getData().keySet()) {
				urlBuilder.addQueryParameter(key, request.getData().get(key));
			}
		}
		
		HttpUrl url = urlBuilder.build();
		requestBuilder.url(url);
		
		request.setDispatchedTime(System.currentTimeMillis());
		executor = new HttpAsyncTask(client, requestBuilder.build(), new HttpExecutorCallback() {
			@Override
			public void onResult(boolean success, String result) {
				if (callback != null) {
					com.tin.shack.http.Response response;
					if (success) {
						response = gson.fromJson(
								result,
								com.tin.shack.http.Response.class
						);
					} else {
						response = new Response();
						response.setCode(4004);
						response.setMessage("Could not connect to " + host);
					}
					
					response.setReceivedTime(System.currentTimeMillis());
					response.setRequest(request);
					
					callback.onResult(response);
				}
			}
		});
		
		executor.execute();
	}
	
	public Cache createCache(){
		return new Cache(new File(ShackApplication
				.getApplicationInstance().getCacheDir(),
				"apiResponses"), 5 * 1024 * 1024);
	}
	
}
