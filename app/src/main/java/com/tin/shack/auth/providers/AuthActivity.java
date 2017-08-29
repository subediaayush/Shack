package com.tin.shack.auth.providers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by aayushsubedi on 7/23/17.
 */

abstract class AuthActivity extends AppCompatActivity {
	public final static String PROVIDER_NAME = "provider_name";
	
	protected String mProviderName;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mProviderName = getIntent().getStringExtra(PROVIDER_NAME);
	}
	
	
	String getProviderName(){
		return mProviderName;
	}
}
