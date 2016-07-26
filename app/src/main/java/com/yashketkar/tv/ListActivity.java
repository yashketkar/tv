package com.yashketkar.tv;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

//import com.winsontan520.wversionmanager.library.WVersionManager;

public class ListActivity extends AppCompatActivity {

    private ListView mlistview;
    public TVChannelCustomAdapter TVChannelAdapter;
    public ArrayList<TVC> tvc;
//    public final static String URL_VERSION = "http://www.livetvap.5gbfree.com/version.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mlistview = (ListView) findViewById(R.id.mlistview);

        tvc = AddTVC.addTVC();

        TVChannelAdapter = new TVChannelCustomAdapter(ListActivity.this,
                ListActivity.this, R.layout.list_item, tvc);
        TVChannelAdapter.setNotifyOnChange(true);
        mlistview.setAdapter(TVChannelAdapter);
        mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(ListActivity.this,
                        MediaPlayerActivity.class);
                String extraURL = tvc.get(position).path;
                intent.putExtra("EXTRA_URL", extraURL);
                startActivity(intent);
            }
        });

        checkVersion();
    }

    private void checkVersion() {
//		WVersionManager versionManager = new WVersionManager(this);
//		versionManager.setVersionContentUrl(URL_VERSION);
//		versionManager.checkVersion();
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
                String sAux = "http://www.yashketkar.com";
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(i, "Share"));
            } catch (Exception e) {
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}