package com.example.museumius.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.museumius.Exhibit;

import java.util.ArrayList;

public class DbManager {
    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public DbManager(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    public void openDb() {
        db = dbHelper.getWritableDatabase();
    }

    public void closeDb() {
        dbHelper.close();
    }

    public void insertToDb(ArrayList<Exhibit> exhibits) {
        ContentValues contentValues;
        for (Exhibit exhibit: exhibits) {
            contentValues = new ContentValues();
            contentValues.put(DbHelper.COLUMN_NAME, exhibit.getName());
            contentValues.put(DbHelper.COLUMN_DESCRIPTION, exhibit.getDescription());
            contentValues.put(DbHelper.COLUMN_IMG, exhibit.getImg());
            db.insert(DbHelper.TABLE_NAME, null, contentValues);
        }
    }

    public void insertToDbRequest(String name, String phone) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.COLUMN_NAME, name);
        contentValues.put(DbHelper.COLUMN_PHONE, phone);
    }

    public void upgradeDb(int version) {
        dbHelper.onUpgrade(db, dbHelper.VERSION, version);
    }
    public ArrayList<Exhibit> getExhibitList() {
        ArrayList<Exhibit> exhibits = new ArrayList<>();
        Exhibit exhibit;
        String name, description, img;
        Cursor cursor = db.query(DbHelper.TABLE_NAME, null, null,
                null, null, null, null);
        while (cursor.moveToNext()) {
            name = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_NAME));
            description = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_DESCRIPTION));
            img = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_IMG));
            exhibit = new Exhibit(name, description, img);
            exhibits.add(exhibit);
        }
        cursor.close();
        return exhibits;
    }

    public int getCount() {
        Cursor cursor = db.query(DbHelper.TABLE_NAME, null, null,
                null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

}
