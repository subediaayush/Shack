package com.tin.shack.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tin.shack.R;
import com.tin.shack.auth.shauth.Shauth;
import com.tin.shack.context.UserContextActivity;
import com.tin.shack.http.FetchCallback;
import com.tin.shack.intro.UserIntroActivity;
import com.tin.shack.login.LoginActivity;
import com.tin.shack.user.User;

/**
 * Created by aayushsubedi on 9/3/17.
 */

public class SplashActivity extends AppCompatActivity {
	
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
		super.onCreate(savedInstanceState, persistentState);
		
		setContentView(R.layout.activity_splash);
		
		String token = Shauth.getInstance().getShackToken(this);
		String uid = Shauth.getInstance().getShackUserId(this);
		if (TextUtils.isEmpty(token) || TextUtils.isEmpty(token)) {
			handleUserAbsence();
		} else {
			handleLoggedInUser(token, uid);
		}
	}
	
	private void handleLoggedInUser(String token, String uid) {
		Shauth.getInstance().requestShackUser(this, token, uid, new FetchCallback<User>() {
			@Override
			public void onDataFetched(User user) {
				String profilePicture = user.getProfilePicture();
				if (TextUtils.isEmpty(profilePicture)) {
					handleNewUser(user);
				} else {
					showUserContext(user);
				}
			}
			
			@Override
			public void onDataFetchFailed(String message) {
				Toast.makeText(SplashActivity.this, message, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void showUserContext(User user) {
		Intent userContextIntent = new Intent(this, UserContextActivity.class);
		userContextIntent.putExtra("user", user);
		startActivity(userContextIntent);
		finish();
	}
	
	private void handleNewUser(User user) {
		Intent userIntroIntent = new Intent(this, UserIntroActivity.class);
		userIntroIntent.putExtra("user", user);
		startActivity(userIntroIntent);
		finish();
	}
	
	private void handleUserAbsence() {
		FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
		if (fUser == null) {
			promptUserLogIn();
		} else {
			Shauth.getInstance().requestShackToken(this, fUser, new FetchCallback<String>() {
				@Override
				public void onDataFetched(String token) {
					String uid = Shauth.getInstance().getShackUserId(SplashActivity.this);
					handleLoggedInUser(token, uid);
				}
				
				@Override
				public void onDataFetchFailed(String message) {
					Toast.makeText(SplashActivity.this, message, Toast.LENGTH_SHORT).show();
				}
			});
		}
	}
	
	private void promptUserLogIn() {
		Intent loginIntent = new Intent(this, LoginActivity.class);
		startActivity(loginIntent);
		finish();
	}
}
