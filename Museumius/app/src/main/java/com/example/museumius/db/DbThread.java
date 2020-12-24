package com.example.museumius.db;

import android.content.Context;

import com.example.museumius.Exhibit;

import java.util.ArrayList;

public class DbThread extends Thread {
    ArrayList<Exhibit> exhibits;
    DbManager dbManager;
    Context context;

    public DbThread( DbManager dbManager, Context context, ArrayList<Exhibit> exhibits) {
        this.exhibits = exhibits;
        this.dbManager = dbManager;
        this.context = context;
    }
    @Override
    public void run() {
        dbManager.openDb();
        if (JsonHelper.getCount(context, "exhibits") != dbManager.getCount()) {
            exhibits = JsonHelper.importExhibitsFromJson(context);
            dbManager.upgradeDb(2);
            dbManager.insertToDb(exhibits);
        }
        else {
            exhibits = dbManager.getExhibitList();
            int a = dbManager.getCount();
        }
        dbManager.closeDb();
    }

    public ArrayList<Exhibit> getExhibits() {
        return exhibits;
    }
}
