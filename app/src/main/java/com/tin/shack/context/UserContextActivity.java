package com.tin.shack.context;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tin.shack.R;
import com.tin.shack.user.User;

/**
 * Created by aayushsubedi on 9/4/17.
 */

public class UserContextActivity extends AppCompatActivity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activty_user_context);
		
		User user = getIntent().getParcelableExtra("user");
		if (user == null) {
			// Should never happen though
			finish();
			return;
		}
		
		
	}
}
