package com.teenvan.databasemodels;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	// SQL convention to write everything in all caps
	public static final String TABLE_TEMPERATURES = "TEMPERATURES";
	public static final String COLUMN_ID = "_ID";
	public static final String COLUMN_TEMPERATURE = "TEMPERATURE";

	private static final String DB_NAME = "temperatures.db";
	private static final int DB_VERSION = 1;
	private static final String DB_CREATE = "CREATE TABLE "
			+ TABLE_TEMPERATURES + "(" + COLUMN_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_TEMPERATURE
			+ " REAL)";

	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Creates the database
		// Execute method for writing raw sql
		db.execSQL(DB_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Updates the databse for new data

	}

}
