package com.teenvan.weatheralarmclock;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.teenvan.databasemodels.DataSource;

public class MainActivity extends Activity {

	protected DataSource mDataSource;
	protected Button mSetAlarm;
	protected double temperature;
	protected double appTemp;
	protected String URL = "https://api.forecast.io/forecast/1596465b38efd4d44a379555b84090d3/21.0000,78.0000";
	protected Button tempButton;
	protected TextView mTemp, mappTemp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getActionBar().hide();
		mTemp = (TextView) findViewById(R.id.textView1);
		mappTemp = (TextView) findViewById(R.id.textView2);
		mSetAlarm = (Button) findViewById(R.id.button1);
		tempButton = (Button) findViewById(R.id.tempButton);
		tempButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						VIewTemperatureActivity.class);
				startActivity(intent);
			}
		});
		mSetAlarm.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				new GetTemperatures()
						.execute("https://api.forecast.io/forecast/1596465b38efd4d44a379555b84090d3/21.0000,78.0000");
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

	public class GetTemperatures extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... uri) {
			// TODO Auto-generated method stub
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response;
			String responseString = null;
			try {
				// make a HTTP request
				response = httpclient.execute(new HttpGet(uri[0]));
				StatusLine statusLine = response.getStatusLine();
				if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
					// request successful - read the response and close the
					// connection
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					response.getEntity().writeTo(out);
					out.close();
					responseString = out.toString();
				} else {
					// request failed - close the connection
					response.getEntity().getContent().close();
					throw new IOException(statusLine.getReasonPhrase());
				}
			} catch (Exception e) {
				Log.d("Test", e.getMessage());
			}
			return responseString;

		}

		@Override
		protected void onPostExecute(String response) {
			super.onPostExecute(response);
			if (response != null) {
				try {
					JSONObject jsonResponse = new JSONObject(response);
					JSONObject currentTemp = jsonResponse
							.getJSONObject("currently");
					temperature = currentTemp.getDouble("temperature");
					appTemp = currentTemp.getDouble("apparentTemperature");
					String temp = String.valueOf(temperature);
					String appTempe = String.valueOf(appTemp);
					mTemp.setText(temp);
					mappTemp.setText(appTempe);
					mDataSource.insert(temperature, appTemp);
				} catch (JSONException e) {
					Log.d("JSON", e.getMessage());
				}

			} else {
				Toast.makeText(MainActivity.this,
						"Oops ! Error! Please try again", Toast.LENGTH_LONG)
						.show();

			}
		}
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
