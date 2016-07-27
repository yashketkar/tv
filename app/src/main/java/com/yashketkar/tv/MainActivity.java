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

public class MainActivity extends AppCompatActivity {

    private ListView tvclistview;
    public TVCAdapter tvcAdapter;
    public ArrayList<TVC> tvc;
//    public final static String URL_VERSION = "http://www.livetvap.5gbfree.com/version.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvclistview = (ListView) findViewById(R.id.tvcListView);
        tvc = AddTVC.addTVC();
        tvcAdapter = new TVCAdapter(MainActivity.this, R.layout.list_item, tvc);
        tvcAdapter.setNotifyOnChange(true);
        tvclistview.setAdapter(tvcAdapter);
        tvclistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(MainActivity.this,
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
        // Handle toolbar item clicks here. The toolbar will
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