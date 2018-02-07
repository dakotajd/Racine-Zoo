package edu.uwp.appfactory.racinezoo;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Date;

/**
 * Created by hanh on 5/9/17.
 */

public class Prefs {

    private static SharedPreferences sharedPreferences;
    public static final String PREF_EXPLORE_MODE = "EXPLORE_MODE";

    public static void init(Application application){
        if(sharedPreferences==null){
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application);
        }
    }

    public static void setSeenAnimal(String animal){
        sharedPreferences.edit().putLong(animal,new Date().getTime()).apply();
    }

    public static long getAnimalLastSeenTime(String animal){
        return sharedPreferences.getLong(animal,0);
    }

    public static boolean isExploreMode(){
        return sharedPreferences.getBoolean(PREF_EXPLORE_MODE,false);
    }

    public static void setPrefExploreMode(boolean isExploreMode){
        sharedPreferences.edit().putBoolean(PREF_EXPLORE_MODE,isExploreMode).apply();
    }

    public static void setSeenLocation(String location) {
        sharedPreferences.edit().putLong(location, new Date().getTime()).apply();
    }

    public static long getLocationLastSeenTime(String location) {
        return sharedPreferences.getLong(location, 0);
    }
}
