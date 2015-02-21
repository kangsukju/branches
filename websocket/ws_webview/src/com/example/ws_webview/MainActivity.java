package com.example.ws_webview;

import java.io.File;
import java.io.IOException;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;


public class MainActivity extends ActionBarActivity {

	static String TAG = "kinow";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        System.out.println(".........");
        
        try {
			File tf = File.createTempFile("kinow", null);
			Log.i(TAG, tf.getAbsolutePath());
			System.out.println("........."+tf.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        
        WebView wb = (WebView)findViewById(R.id.webView);
        File f = new File(Environment.getExternalStorageDirectory() + "client.html");
        Log.i(TAG, "local html file: " + f.getAbsolutePath());
        wb.loadUrl("file:///" + f.getAbsolutePath());
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
