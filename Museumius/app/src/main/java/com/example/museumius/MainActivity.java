package com.example.museumius;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.museumius.db.DbManager;
import com.example.museumius.db.DbThread;
import com.example.museumius.db.JsonHelper;
import com.example.museumius.fragments.collection.CollectionFragment;
import com.example.museumius.fragments.events.EventsFragment;
import com.example.museumius.fragments.InfoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ArrayList<Exhibit> exhibits;
    private ArrayList<String> events;
    private BottomNavigationView navigation;
    private DbManager dbManager;
    private DbThread dbThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbManager = new DbManager(this);
        dbThread = new DbThread(dbManager, this, exhibits);
        if (savedInstanceState == null) {
            events = JsonHelper.importEventsFromJson(this);
            if (events == null) {
                events = new ArrayList<>();
                events.add("Ой-ой, похоже, вам нужно подключится к интернету, " +
                        "чтобы увидеть предстоящие события!");
            }
            getSupportFragmentManager().
                    beginTransaction().add(R.id.host, EventsFragment.newInstance(events)).commit();
            dbThread.start();
        }
        else {
            exhibits = (ArrayList<Exhibit>) savedInstanceState.getSerializable("exhibits");
            events = (ArrayList<String>) savedInstanceState.getStringArrayList("events");
        }
        navigation = (BottomNavigationView) findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_events:
                        switchFragment(EventsFragment.newInstance(events));
                        return true;
                    case R.id.navigation_collection:
                        if (dbThread.isAlive()) {
                            try {
                                dbThread.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        if (exhibits == null)
                            exhibits = dbThread.getExhibits();
                        switchFragment(CollectionFragment.newInstance(exhibits));
                        return true;
                    case R.id.navigation_info:
                        switchFragment(InfoFragment.newInstance());
                        return true;
                }
                return false;
            }
        });
    }

    private void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fragment_fade_enter,
                R.anim.fragment_fade_exit);
        fragmentTransaction.replace(R.id.host, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("exhibits", exhibits);
        outState.putStringArrayList("events", events);
    }


    public void openSite(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://youtu.be/dQw4w9WgXcQ"));
        startActivity(intent);
    }

    //TODO Проверить, нужна ли (ВРОДЕ МОЖНО УБРАТЬ)
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        exhibits = (ArrayList<Exhibit>) savedInstanceState.getSerializable("exhibits");
    }

    public BottomNavigationView getNavigation() {
        return navigation;
    }

    public DbManager getDbManager() {
        return dbManager;
    }
}
