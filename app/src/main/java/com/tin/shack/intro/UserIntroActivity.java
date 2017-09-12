package com.tin.shack.intro;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.flurgle.camerakit.CameraListener;
import com.flurgle.camerakit.CameraView;
import com.tin.shack.R;
import com.tin.shack.behavior.SimpleBottomSheetCallback;

/**
 * Created by aayushsubedi on 9/4/17.
 */

public class UserIntroActivity extends AppCompatActivity {
	
	private static final String TAG = "UserIntroActivity";
	
	private CameraView mCameraView;
	private ViewSwitcher mIntroText;
	private View mCameraConfirmation;
	private ImageView mIntroSelfie;
	
	private BottomSheetBehavior mIntroBehavior;
	private BottomSheetBehavior mCameraBehavior;
	
	private BottomSheetBehavior.BottomSheetCallback mIntroCallback;
	private BottomSheetBehavior.BottomSheetCallback mCameraConfirmationCallback;
	private CameraListener mCameraCallback;
	
	private boolean pictureAccepted;
	private boolean startedTakingPicture;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_intro);
		
		mCameraView = (CameraView) findViewById(R.id.camera);
		mIntroText = (ViewSwitcher) findViewById(R.id.intro_text);
		mCameraConfirmation = findViewById(R.id.camera_confirmation);
		mIntroSelfie = (ImageView) findViewById(R.id.intro_selfie);
		
		mIntroBehavior = BottomSheetBehavior.from(mIntroText);
		mCameraBehavior = BottomSheetBehavior.from(mCameraConfirmation);
		
		mIntroBehavior.setSkipCollapsed(true);
		mCameraBehavior.setSkipCollapsed(true);
		
		mIntroBehavior.setHideable(true);
		mCameraBehavior.setHideable(true);
		
		
		mCameraBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
		mIntroBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
		
		setupCallback();
		
		mIntroBehavior.setBottomSheetCallback(mIntroCallback);
		mCameraBehavior.setBottomSheetCallback(mCameraConfirmationCallback);
		mCameraView.setCameraListener(mCameraCallback);
		
		if (savedInstanceState != null) {
			startedTakingPicture = savedInstanceState.getBoolean("started_taking_picture", false);
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putBoolean("started_taking_picture", startedTakingPicture);
		super.onSaveInstanceState(outState);
	}
	
	private void setupCallback() {
		mCameraCallback = new CameraListener() {
			@Override
			public void onPictureTaken(byte[] jpeg) {
				Bitmap bitmap = BitmapFactory.decodeByteArray(jpeg, 0, jpeg.length);
				mIntroSelfie.setImageBitmap(bitmap);
				mCameraBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
			}
		};
		
		mIntroCallback = new SimpleBottomSheetCallback() {
			@Override
			public void onStateChanged(@NonNull View bottomSheet, int newState) {
				if (newState == BottomSheetBehavior.STATE_HIDDEN) {
					Log.d(TAG, "Hiding intro bottom sheet");
					mCameraView.start();
					startedTakingPicture = true;
				}
			}
		};
		
		mCameraConfirmationCallback = new SimpleBottomSheetCallback() {
			@Override
			public void onStateChanged(@NonNull View bottomSheet, int newState) {
				if (newState == BottomSheetBehavior.STATE_HIDDEN) {
					Log.d(TAG, "Hiding camera bottom sheet");
					if (pictureAccepted) {
						mCameraView.stop();
						processUserPicture();
					} else {
						mCameraView.start();
					}
				} else if (newState == BottomSheetBehavior.STATE_EXPANDED) {
					mCameraView.stop();
				}
			}
		};
		
	}
	
	private void processUserPicture() {
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (startedTakingPicture) {
			mCameraView.start();
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mCameraView.stop();
	}
	
	public void startSelfieChallenge(View view) {
		mIntroBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
	}
	
	public void retakePicture(View view) {
		pictureAccepted = false;
		mCameraBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
	}
	
	public void acceptPicture(View view) {
		pictureAccepted = true;
		mCameraBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
	}
	
	public void takePicture(View view) {
		mCameraView.captureImage();
	}
}
