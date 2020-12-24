package com.example.museumius.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DbHelper extends SQLiteOpenHelper implements BaseColumns {

    public static final String DB_NAME = "exhibit.db";
    public static final int VERSION = 1;
    public static final String TABLE_NAME = "exhibit";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_IMG = "image";

    public static final String TABLE_REQUESTS_NAME = "requests";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_PRICE = "price";

    // Если надо создать несколько, то изменить это
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
            "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME +
            " TEXT," +
            COLUMN_DESCRIPTION + " TEXT," + COLUMN_IMG + " TEXT)";

    private static final String SQL_CREATE_REQUESTS = "CREATE TABLE IF NOT EXISTS " +
            TABLE_REQUESTS_NAME +
            "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME +
            " TEXT," +
            COLUMN_PHONE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    private static final String SQL_DELETE_REQUESTS =
            "DROP TABLE IF EXISTS " + TABLE_REQUESTS_NAME;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_REQUESTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_REQUESTS);
        onCreate(db);
    }
}
