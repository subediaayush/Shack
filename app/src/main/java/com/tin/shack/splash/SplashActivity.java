package com.tin.shack.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tin.shack.R;
import com.tin.shack.login.LoginActivity;

/**
 * Created by aayushsubedi on 9/3/17.
 */

public class SplashActivity extends AppCompatActivity {
	
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
		super.onCreate(savedInstanceState, persistentState);
		
		setContentView(R.layout.activity_splash);
		
		FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
		if (user != null) {
			
		}
		
		Intent loginIntent = new Intent(this, LoginActivity.class);
		startActivity(loginIntent);
		
		finish();
	}
}
