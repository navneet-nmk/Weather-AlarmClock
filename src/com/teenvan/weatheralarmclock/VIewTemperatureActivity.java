package com.teenvan.weatheralarmclock;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.teenvan.databasemodels.DBHelper;
import com.teenvan.databasemodels.DataSource;

public class VIewTemperatureActivity extends Activity {
	// Declaration of variables
	protected DataSource mdataSource;
	protected TextView mTempText;
	protected double temp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_temperature);
		mTempText = (TextView) findViewById(R.id.tempText);
		mdataSource = new DataSource(VIewTemperatureActivity.this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_temperature, menu);
		return true;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mdataSource.open();
		Cursor cursor = mdataSource.selecttemp();
		cursor.moveToFirst();
		int i = cursor.getColumnIndex(DBHelper.COLUMN_TEMPERATURE);
		temp = cursor.getDouble(i);
		String tempText = String.valueOf(temp);
		mTempText.setText(tempText);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mdataSource.close();
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
