package com.tin.shack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.tin.shack.auth.FirebaseLoginCallback;
import com.tin.shack.auth.firebase.FirebaseLogin;
import com.tin.shack.auth.firebase.FirebaseLoginService;
import com.tin.shack.auth.providers.FacebookAuthProvider;
import com.tin.shack.http.HttpExecutor;
import com.tin.shack.http.HttpRequestType;
import com.tin.shack.http.Request;
import com.tin.shack.http.Response;
import com.tin.shack.http.ResponseCallback;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements FirebaseLoginCallback {
	
	private static final String TAG = "LoginActivity";
	private View mFacebookButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		mFacebookButton = findViewById(R.id.facebook);
		mFacebookButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new FirebaseLoginService.Builder(LoginActivity.this)
						.setSilentMode(false)
						.setLoginCallback(LoginActivity.this)
						.withProvider(FacebookAuthProvider.getProvider())
						.initiate();
			}
		});
		mFacebookButton.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				sendRequest();
				return true;
			}
		});
		
		FirebaseLogin.initialise(this);
	}
	
	private void sendRequest() {
		HttpExecutor executor = HttpExecutor.getInstance();
		HashMap<String, String> data = new HashMap<String, String>() {{
			put("hello", "world");
		}};
		
		Request request = new Request.RequestBuilder().setType(HttpRequestType.GET)
				.setData(data).build();
		
		Log.d(TAG, "Sending http request " + new Gson().toJson(request));
		executor.execute(request, new ResponseCallback() {
			@Override
			public void onResult(Response response) {
				Log.d(TAG, new Gson().toJson(response));
			}
		});
	}
	
	@Override
	public void onLoginSuccess(FirebaseUser user) {
		
		Log.d(TAG, "Login successful");
	}
	
	@Override
	public void onLoginFailure(String errorMessage) {
		Log.d(TAG, "Login failed: " + errorMessage);
	}
}
