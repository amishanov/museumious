package com.example.museumius.db;

import android.content.Context;
import android.content.res.Resources;

import com.example.museumius.Exhibit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class JsonHelper {

    public static ArrayList<Exhibit> importExhibitsFromJson(Context context) {
        ArrayList<Exhibit> exhibits = new ArrayList<>();
        Exhibit exhibit = null;
        String name, description, img;
        try {
            String response = getJson(context, "exhibits");
            if (response == null)
                return null;
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("exhibits");
            JSONObject obj;
            for (int i = 0; i < jsonArray.length(); i++) {
                obj = jsonArray.getJSONObject(i);
                name = obj.getString("name");
                description = obj.getString("description");
                img = obj.getString("img");
                exhibit = new Exhibit(name, description, img);
                exhibits.add(exhibit);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return exhibits;
    }



    public static ArrayList<String> importEventsFromJson(Context context) {
        ArrayList<String> events = new ArrayList<>();
        try {
            String response = getJson(context, "events");
            if (response == null)
                return null;
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("events");
            JSONObject obj;
            for (int i = 0; i < jsonArray.length(); i++) {
                obj = jsonArray.getJSONObject(i);
                events.add(obj.getString("event"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return events;
    }

    public static int getCount(Context context, String filename) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(getJson(context, filename));
            return jsonObject.getInt("count");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getVersion(Context context, String filename) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(getJson(context, filename));
            return jsonObject.getInt("version");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static String getJson(Context context, String filename) {
        String response = null;
        try {
            int r =  context.getResources().getIdentifier(filename, "raw", context.getPackageName());
            InputStream inputStream = context.getResources().openRawResource(r);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            response = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Resources.NotFoundException e) {
            return null;
        }
        return response;
    }
}
