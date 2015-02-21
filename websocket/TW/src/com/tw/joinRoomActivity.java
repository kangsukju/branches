package com.tw;


import java.net.InetAddress;
import java.net.InetSocketAddress;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebSettings.PluginState;

@SuppressLint("SetJavaScriptEnabled") 
public class joinRoomActivity extends Activity {

	public static final int JOIN_TYPE_SERVER_AND_CLIENT = 1;
	public static final int JOIN_TYPE_ONLY_CLIENT = 2;
	
	private Activity thisActivity = this; 
	private int join_mode;
	private String server_address = null;
	private WebView joinWebView = null;
	private final Handler handler = new Handler();
	final Context context = this;
	private static final String local_html = "file:///android_asset/index.html";
	
    // call javascript to native
    private class javascriptNativeBridge {
    	@JavascriptInterface
    	// java script debug function
    	public void debug(final String msg) { // must be final
    		handler.post(new Runnable() {
				@Override
				public void run() {
					Log.i(NM.TAG, msg);
				}
			});
    	}
    	@JavascriptInterface
    	public final String getServerHost() {
    		String server;
    		if (join_mode == JOIN_TYPE_ONLY_CLIENT) {
    			server = "ws://"+server_address+":"+WSServer.WS_PORT;
    		} else {
    			try {
					server = "ws://"+Utils.getMyWifiAddress(thisActivity)+":"+WSServer.WS_PORT;
				} catch (Exception e) {
					server = "";
					e.printStackTrace();
				}
    		}
    		return server;
    	}
    }
    
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.join_room_activity);
    	
    	// determine join mode
    	Intent intent = getIntent();
    	join_mode = JOIN_TYPE_SERVER_AND_CLIENT;
    	if (intent == null) {
    		join_mode = JOIN_TYPE_ONLY_CLIENT;
    	} else {
    		server_address = intent.getStringExtra("server");
    		if (server_address != null) {
    			join_mode = JOIN_TYPE_ONLY_CLIENT;	
    		}
    	}
    	
    	if (join_mode == JOIN_TYPE_SERVER_AND_CLIENT) {
    		// start websocket server.
    		try {
				ServerStart();
			} catch (Exception e) {
				Log.e(NM.TAG, "failed server start: "+e.getMessage());
				e.printStackTrace();
			}
    		
    		// offer message for find client owner room.
    		try {
    			InetAddress offer_address = Utils.getMyWifiInetAddress(this);
    			Runnable discoveryRun = new DiscoveryManager(DiscoveryManager.DISCOVERY_OFFER, offer_address);
    			Thread discoveryService = new Thread(discoveryRun);
	        	discoveryService.start();
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    	
    	Log.i(NM.TAG, "join mode is "+ ((join_mode == JOIN_TYPE_SERVER_AND_CLIENT) ? "server+client" : "only client"));
    	
    	joinWebView = (WebView)findViewById(R.id.wb_join);
    	joinWebView.getSettings().setJavaScriptEnabled(true);
    	joinWebView.addJavascriptInterface(new javascriptNativeBridge(), "tw_native");
    	enableWebViewAlertFunction(joinWebView); // enable alter function
    	joinWebView.loadUrl("file:///android_asset/index.html");
    }
    
    private void enableWebViewAlertFunction(WebView webView) {
    	webView.setWebChromeClient(new WebChromeClient(){
    		public boolean onJsAlert(WebView view, String url, String message, final android.webkit.JsResult result) {
    			new AlertDialog.Builder(context)
    			.setTitle("AlertDialog")
    			.setMessage(message)
    			.setPositiveButton(android.R.string.ok, 
    					new OnClickListener() 
    					{
    						public void onClick(DialogInterface dialog, int which) {
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
    
    private void ServerStart() throws Exception {
    	InetSocketAddress address = new InetSocketAddress(Utils.getMyWifiAddress(this), WSServer.WS_PORT);
		Log.i(NM.TAG, "my ipaddress is "+address.getAddress());
		WSServer server = new WSServer(address);
		String [] params = null;
		server.execute(params);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
