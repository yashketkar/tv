package com.yashketkar.tv;

import com.google.android.gms.ads.*;

import android.app.ProgressDialog;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.WindowManager;
import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class MediaPlayerDemo extends Activity {

	private AdView adView;
	VideoView videoView;
	ProgressDialog pDialog;

	String httpLiveUrl;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (!LibsChecker.checkVitamioLibs(this))
			return;

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		Intent intent = getIntent();
		httpLiveUrl = intent.getStringExtra("EXTRA_URL");
		// Toast.makeText(getApplicationContext(), "Loading Live Stream...",
		// Toast.LENGTH_LONG).show();
		setContentView(R.layout.media_player);

		// Create a progressbar
		pDialog = new ProgressDialog(MediaPlayerDemo.this);
		// Set progressbar title
		pDialog.setTitle("Loading Live Stream");
		// Set progressbar message
		pDialog.setMessage("Buffering...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(false);
		// Show progressbar
		pDialog.show();

		// Create an ad.
		AdView adView = (AdView) this.findViewById(R.id.adView2);
		// Create an ad request. Check logcat output for the hashed device ID to
		// get test ads on a physical device.
		AdRequest adRequest = new AdRequest.Builder().build();
		// .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
		// .addTestDevice("169FC43CA819A64255B0C3CEE2A6722F").build();
		// Start loading the ad in the background.
		adView.loadAd(adRequest);

		videoView = (VideoView) findViewById(R.id.VideoView);
		videoView.setVideoURI(Uri.parse(httpLiveUrl));
		MediaController mediaController = new MediaController(this);
		videoView.setMediaController(mediaController);
		videoView.requestFocus();
		videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			// Close the progress bar and play the video
			public void onPrepared(MediaPlayer mp) {
				pDialog.dismiss();
				videoView.start();
			}
		});
		videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
			// Close the progress bar and play the video
			public boolean onError(MediaPlayer mp, int x, int y) {
				// if (y == MediaPlayer.MEDIA_ERROR_UNSUPPORTED) {
				pDialog.dismiss();
				// }
				return false;
			}
		});

		videoView.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {

				if (hasFocus) {
					hideUI();
				}
			}
		});

		hideUI();
	}

	@SuppressLint("NewApi")
	public void hideUI() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			getWindow().getDecorView().setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_LAYOUT_STABLE
							| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
							| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_FULLSCREEN
							| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

			getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(
					new View.OnSystemUiVisibilityChangeListener() {
						@Override
						public void onSystemUiVisibilityChange(int visibility) {
							if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
								// set immersive mode sticky
							}
						}
					});
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (adView != null) {
			adView.resume();
		}
	}

	@Override
	public void onPause() {
		if (adView != null) {
			adView.pause();
		}
		super.onPause();
	}

	/** Called before the activity is destroyed. */
	@Override
	public void onDestroy() {
		// Destroy the AdView.
		if (adView != null) {
			adView.destroy();
		}
		super.onDestroy();
	}

}