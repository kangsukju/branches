package com.example.webview;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

	private final Handler handler = new Handler();
	private WebView wb;
	final Context ctx = this;
	
	private class webBridge {
		@JavascriptInterface
		public void setMessage(final String arg) { // must be final
			handler.post(new Runnable() {
				public void run() {
					TextView tv = (TextView) findViewById(R.id.textView1);
					tv.setText(arg);
				}
			});
		}
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        wb = (WebView)findViewById(R.id.webView1);
        wb.getSettings().setJavaScriptEnabled(true);
        wb.addJavascriptInterface(new webBridge(), "android");
        //wb.loadUrl("http://www.google.com");
        wb.loadUrl("file:///android_asset/test.html");        
        
        
        // webview에서 alert 사용하기
        wb.setWebChromeClient(new WebChromeClient() {
        	public boolean onJsAlert(WebView view, String url, String message, final android.webkit.JsResult result)
            {
        		new AlertDialog.Builder(ctx)
                .setTitle("AlertDialog")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok,
                        new AlertDialog.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                result.confirm();
                            }
                        })
                .setCancelable(false)
                .create()
                .show();
        		return true;
            }
        });
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
