package com.teenvan.weatheralarmclock;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.teenvan.databasemodels.DataSource;

public class MainActivity extends Activity {

	protected DataSource mDataSource;
	protected Button mSetAlarm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mSetAlarm = (Button) findViewById(R.id.button1);
		mSetAlarm.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mDataSource.insert();
			}
		});
		mDataSource = new DataSource(MainActivity.this);

	}

	@Override
	protected void onResume() {
		// Open the above instantiated database
		super.onResume();
		mDataSource.open();
	}

	@Override
	protected void onPause() {
		// Close the above instantiated database
		super.onPause();
		mDataSource.close();
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
