package edu.uwp.appfactory.racinezoo;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**

 * Created by dakota on 3/4/17.

 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        Prefs.init(this);
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build());
    }
}
