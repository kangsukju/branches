package com.example.intent_test;

import java.io.Serializable;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button bt = (Button) findViewById(R.id.btn_launch_to_2nd_activity);
        bt.setOnClickListener(myButtonClick);
    }
    
    Button.OnClickListener myButtonClick = new Button.OnClickListener()
    {
		public void onClick(View v) {
			Intent i = new Intent(MainActivity.this, SecondActivity.class);
			
			MyData obj = new MyData(101, "kinow");
			i.putExtra("string_data_1", "첫번째 스트링데이터");
			i.putExtra("string_data_2", "두번째 스트링데이터");
			i.putExtra("MyData", obj);
			
			startActivity(i);
		}
    	
    };

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


class MyData implements Serializable
{
	int id;
	String name;
	
	MyData(int _id, String _name) {
		id = _id;
		name = _name;
	}
}