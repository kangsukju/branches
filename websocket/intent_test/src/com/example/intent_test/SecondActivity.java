package com.example.intent_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second_activity_main);
		
		TextView tv = (TextView) findViewById(R.id.second_textView);
		
		Intent i = getIntent();
		if (i != null)
		{
			MyData obj;
			
			obj = (MyData) i.getSerializableExtra("MyData");
			String txt = ""; 
			txt += "string_data 1:" + i.getStringExtra("string_data_1") + "\n";
			txt += "string_data 2:" + i.getStringExtra("string_data_2") + "\n";
			txt += "kinow.ID: " + obj.id + "\n";
			txt += "kinow.NAME: " + obj.name;
			
			tv.setText(txt);
		}
		
		Button bt = (Button) findViewById(R.id.second_button);
        bt.setOnClickListener(myButtonClick);
	}
	
	Button.OnClickListener myButtonClick = new Button.OnClickListener()
	{
		public void onClick(View v)
		{
			finish();
			/*
			Intent i = new Intent(SecondActivity.this, Activity.class);
			startActivity(i);
			*/
		}
	};
}
