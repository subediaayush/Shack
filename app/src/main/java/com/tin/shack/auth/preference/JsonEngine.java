package com.tin.shack.auth.preference;

import com.google.gson.Gson;

/**
 * Created by aayushsubedi on 9/4/17.
 */

public class JsonEngine {
	private static JsonEngine sInstance;
	private final Gson gson;
	
	private JsonEngine(){
		this.gson = new Gson();
	}
	
	public static JsonEngine getInstance(){
		if (sInstance == null) sInstance = new JsonEngine();
		return sInstance;
	}
	
	public <E> E getData(String json, Class<E> clazz){
		return gson.fromJson(json, clazz);
	}
	
	public String getJson(Object data) {
		return gson.toJson(data);
	}
}
