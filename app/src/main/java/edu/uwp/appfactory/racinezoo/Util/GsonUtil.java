package edu.uwp.appfactory.racinezoo.Util;

import android.content.Context;
import android.support.v4.content.res.TypedArrayUtils;
import android.util.Log;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import edu.uwp.appfactory.racinezoo.Model.Animal;
import edu.uwp.appfactory.racinezoo.Model.Event;
import edu.uwp.appfactory.racinezoo.Model.Marker;
import edu.uwp.appfactory.racinezoo.Model.RealmString;
import edu.uwp.appfactory.racinezoo.Model.ZooLocation;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by hanh on 3/28/17.
 */

public class GsonUtil {

    public static Gson getGson(){

        Type tokenString = new TypeToken<RealmList<RealmString>>(){}.getType();
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .registerTypeAdapter(tokenString, new TypeAdapter<RealmList<RealmString>>() {

                    @Override
                    public void write(JsonWriter out, RealmList<RealmString> value) throws IOException {
                        // Ignore
                    }

                    @Override
                    public RealmList<RealmString> read(JsonReader in) throws IOException {
                        RealmList<RealmString> list = new RealmList<RealmString>();
                        in.beginArray();
                        while (in.hasNext()) {
                            list.add(new RealmString(in.nextString()));
                        }
                        in.endArray();
                        return list;
                    }
                })
                .setDateFormat("MM/dd/yyyy h:mma z")
                .create();

        return gson;

    }

    public static List<RealmObject> populateRealmDB(Context context) {

        ArrayList<RealmObject> all = new ArrayList<>();
        Gson gson = GsonUtil.getGson();
        String json;

        try {
            InputStream is = context.getAssets().open("zoo-events.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");


            JSONObject ja = new JSONObject(json);
            JSONArray jo = ja.getJSONArray("Event");
            List<Event> events = gson.fromJson(jo.toString(), new TypeToken<List<Event>>(){}.getType());
            all.addAll(events);

            is = context.getAssets().open("zoo-animals.json");
            size = is.available();
            buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            ja = new JSONObject(json);
            jo = ja.getJSONArray("Animals");
            List<Animal> animals = gson.fromJson(jo.toString(), new TypeToken<List<Animal>>(){}.getType());
            all.addAll(animals);


            is = context.getAssets().open("zoo-coordinates.json");
            size = is.available();
            buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            ja = new JSONObject(json);
            jo = ja.getJSONArray("customAnnotations");
            List<ZooLocation> locations = gson.fromJson(jo.toString(), new TypeToken<List<ZooLocation>>(){}.getType());
            all.addAll(locations);

            /*is = context.getAssets().open("zoo-coordinates.json");
            json = new String(buffer,"UTF-8");

            ja = new JSONObject(json);
            jo = ja.getJSONArray("customAnnotations");
            List<ZooLocation> locations = gson.fromJson(jo.toString(), new TypeToken<List<ZooLocation>>(){}.getType());
            all.addAll(locations);*/

            is = context.getAssets().open("zoo-markers.json");

            size = is.available();
            buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");

            ja = new JSONObject(json);
            jo = ja.getJSONArray("locationDescriptions");
            List<Marker> markers = gson.fromJson(jo.toString(), new TypeToken<List<Marker>>(){}.getType());

            all.addAll(markers);
        } catch (IOException | JSONException ex) {
            ex.printStackTrace();
            Log.e("JSON", "Error reading animal JSON");
        }

        return all;

    }
}
