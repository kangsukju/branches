package com.tw;

import java.net.InetAddress;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.appcompat.R.id;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity implements DiscoveryFindRoomHandler {

	private static boolean isSearchRoom = false;
	private DiscoveryFindRoomHandler thisDiscoveryFindRoomHandler = this;
	private Thread discoveryService = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_active);
        
        Button btn_new = (Button) findViewById(R.id.btn_new_rom);
        EditText edit_txt_join = (EditText) findViewById(R.id.edit_txt_join);
        Button btn_join = (Button) findViewById(R.id.btn_join);
        Button btn_search_room = (Button) findViewById(R.id.btn_search_room);
        
        /*
        edit_txt_join.setOnTouchListener(new OnTouchListener() {			
        	@Override
			public boolean onTouch(View v, MotionEvent event) {				
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					EditText txt = (EditText) v;
					txt.setText("");
					return true;
				}
				return false;
			}
		});
		*/
        
        btn_new.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, joinRoomActivity.class);
				startActivity(intent);
			}
		});
        
        btn_join.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, joinRoomActivity.class);				
				EditText url = (EditText) findViewById(R.id.edit_txt_join);
				intent.putExtra("server", url.getText().toString());
				startActivity(intent);
			}
		});
        
        btn_search_room.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i(NM.TAG, "run discovery\n");
		        try {
		        	if (discoveryService != null) {
		        		discoveryService.interrupt();
		        		Log.i(NM.TAG, "stop discoveryService");
		        	}
		        	discoveryService = new DiscoveryManager(
	        				DiscoveryManager.DISCOVERY_DISCOVER, 
	        				thisDiscoveryFindRoomHandler);
		        	discoveryService.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }
    
	@Override
	public void findRoom(DiscoveryMessage message) {
		Log.i(NM.TAG, "find room: "+message);
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
