package com.tin.shack.auth.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import java.util.Map;
import java.util.Set;

/**
 * Created by aayushsubedi on 9/3/17.
 */

public class PreferenceManager implements SharedPreferences {
	private static PreferenceManager sPreferences;
	
	private final Context mContext;
	private SharedPreferences mPref;
	
	private PreferenceManager(Context context) {
		this.mContext = context;
		mPref = mContext.getSharedPreferences("Shack", Context.MODE_PRIVATE);
	}
	
	public static void init(Context context) {
		sPreferences = new PreferenceManager(context.getApplicationContext());
	}
	
	public static PreferenceManager getPreferences(){
		return sPreferences;
	}
	
	@Override
	public Map<String, ?> getAll() {
		return mPref.getAll();
	}
	
	@Nullable
	@Override
	public String getString(String key, @Nullable String defValue) {
		return mPref.getString(key, defValue);
	}
	
	@Nullable
	@Override
	public Set<String> getStringSet(String key, @Nullable Set<String> defValues) {
		return mPref.getStringSet(key, defValues);
	}
	
	@Override
	public int getInt(String key, int defValue) {
		return mPref.getInt(key, defValue);
	}
	
	@Override
	public long getLong(String key, long defValue) {
		return mPref.getLong(key, defValue);
	}
	
	@Override
	public float getFloat(String key, float defValue) {
		return mPref.getFloat(key, defValue);
	}
	
	@Override
	public boolean getBoolean(String key, boolean defValue) {
		return mPref.getBoolean(key, defValue);
	}
	
	@Override
	public boolean contains(String key) {
		return mPref.contains(key);
	}
	
	@Override
	public Editor edit() {
		return mPref.edit();
	}
	
	@Override
	public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
		mPref.registerOnSharedPreferenceChangeListener(listener);
	}
	
	@Override
	public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
		mPref.registerOnSharedPreferenceChangeListener(listener);
	}
}
