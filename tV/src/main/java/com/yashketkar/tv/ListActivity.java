package com.yashketkar.tv;

import java.util.ArrayList;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.winsontan520.wversionmanager.library.WVersionManager;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

public class ListActivity extends ActionBarActivity {
	private AdView adView;
	private InterstitialAd interstitial;
	private ListView mlistview;
	public TVChannelCustomAdapter TVChannelAdapter;
	public ArrayList<TVC> tvc;
	public final static String URL_VERSION = "http://www.livetvap.5gbfree.com/version.txt";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		AdView adView = (AdView) this.findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		// .addTestDevice("169FC43CA819A64255B0C3CEE2A6722F")
		adView.loadAd(adRequest);

		interstitial = new InterstitialAd(this);
		interstitial.setAdUnitId("ca-app-pub-2265268935639337/5198257009");
		AdRequest adRequest2 = new AdRequest.Builder().build();
		interstitial.loadAd(adRequest2);

		mlistview = (ListView) findViewById(R.id.mlistview);

		tvc = AddTVC.addTVC();

		TVChannelAdapter = new TVChannelCustomAdapter(ListActivity.this,
				ListActivity.this, R.layout.row, tvc);
		TVChannelAdapter.setNotifyOnChange(true);
		mlistview.setAdapter(TVChannelAdapter);
		mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				displayInterstitial();

				Intent intent = new Intent(ListActivity.this,
						MediaPlayerDemo.class);
				String extraURL = tvc.get(position).path;
				intent.putExtra("EXTRA_URL", extraURL);
				startActivity(intent);

			}
		});

		checkVersion();
	}

	private void checkVersion() {
		WVersionManager versionManager = new WVersionManager(this);
		versionManager.setVersionContentUrl(URL_VERSION);
		versionManager.checkVersion();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_share) {

			try {
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("text/plain");
				i.putExtra(Intent.EXTRA_SUBJECT,
						"Watch Free Live TV on your Android Device");
				String sAux = "http://www.getlivetvapp.co.vu/download.html";
				i.putExtra(Intent.EXTRA_TEXT, sAux);
				startActivity(Intent.createChooser(i, "Share"));
			} catch (Exception e) {
			}

			return true;
		}
		return super.onOptionsItemSelected(item);
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

	public void displayInterstitial() {
		if (interstitial.isLoaded()) {
			interstitial.show();
		}
	}
}