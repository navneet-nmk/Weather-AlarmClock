package com.teenvan.databasemodels;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DataSource {

	private SQLiteDatabase mDatabase;
	private DBHelper mHelper;
	private Context mContext;

	public DataSource(Context context) {
		mContext = context;
		// instatntiate the DBHelper object
		mHelper = new DBHelper(mContext);
	}

	// Commands to be completed

	// open
	public void open() throws SQLException {
		// get a databse if it exists (for inserting new data) or create one
		mDatabase = mHelper.getWritableDatabase();
	}

	// close
	public void close() {
		mDatabase.close();
	}

	// insert
	public void insert(double temp, double appTemp) {
		// Start transaction

		ContentValues values = new ContentValues();
		values.put(DBHelper.COLUMN_TEMPERATURE, temp);

		mDatabase.insert(DBHelper.TABLE_TEMPERATURES, null, values);

		// end transaction

	}

	// delete

	// select
	public Cursor selecttemp() {
		Cursor cursor = mDatabase.query(DBHelper.TABLE_TEMPERATURES,
				new String[] {DBHelper.COLUMN_TEMPERATURE}, null, null, null,
				null, null);
		return cursor;
	}
}
