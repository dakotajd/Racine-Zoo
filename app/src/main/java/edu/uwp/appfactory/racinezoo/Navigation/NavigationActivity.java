package edu.uwp.appfactory.racinezoo.Navigation;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.os.RemoteException;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import org.altbeacon.beacon.BeaconConsumer;
import java.util.List;

import edu.uwp.appfactory.racinezoo.Beacon.BeaconController;
import edu.uwp.appfactory.racinezoo.DetailScreen.DetailActivity;
import edu.uwp.appfactory.racinezoo.InfoScreen.InfoFragment;
import edu.uwp.appfactory.racinezoo.Model.Animal;
import edu.uwp.appfactory.racinezoo.Model.DetailObject;
import edu.uwp.appfactory.racinezoo.Model.ZooLocation;
import edu.uwp.appfactory.racinezoo.Prefs;
import edu.uwp.appfactory.racinezoo.Util.Config;
import edu.uwp.appfactory.racinezoo.Util.BeaconUtil;
import edu.uwp.appfactory.racinezoo.Util.DateUtils;
import edu.uwp.appfactory.racinezoo.Util.GsonUtil;
import io.realm.Realm;
import io.realm.RealmObject;


public class NavigationActivity extends AppCompatActivity implements BeaconConsumer, InfoFragment.BeaconControllerContract {

    private ViewPager viewPager;
    private BeaconController beaconController;
    private Context context;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(android.os.Message message) {
            if (Prefs.isExploreMode()) {
                if (viewPager.getCurrentItem() == 0) {

                    Bundle bundle = message.getData();
                    int id = bundle.getInt("beaconId");
                    Log.d("BEACONTEST", "Found beacon id = " + id);
                    Realm realm = Realm.getDefaultInstance();
                    DetailObject animal = realm.where(Animal.class).equalTo("id", id).findFirst();
                    DetailObject location = realm.where(ZooLocation.class).equalTo("id", id).findFirst();

                    if (animal != null) {

                        long lastSeenTime = Prefs.getAnimalLastSeenTime(animal.getName());

                        Prefs.setSeenAnimal(animal.getName());
                        if (DateUtils.checkAnimalTime(lastSeenTime)) {
                            Intent intent = new Intent(context, DetailActivity.class);
                            intent.putExtra("name", animal.getName());
                            intent.putExtra("type", Config.ANIMAL);
                            startActivity(intent);
                        }
                    } else if (location != null) {
                        long lastSeenTime = Prefs.getLocationLastSeenTime(location.getName());
                        Prefs.setSeenLocation(location.getName());
                        if (DateUtils.checkAnimalTime(lastSeenTime)) {
                            Intent intent = new Intent(context, DetailActivity.class);
                            intent.putExtra("name", location.getName());
                            intent.putExtra("type", Config.LOCATION);
                            startActivity(intent);
                        }
                    }

                    realm.close();
                    return true;


                }
            }
                return false;
            }

    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(edu.uwp.appfactory.racinezoo.R.layout.activity_navigation);

        BeaconUtil.promptForLocationPermissions(this);

        setupBottomNavigation();
        getSupportActionBar().setElevation(0);



        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("FIRST_RUN", false)) {
            copyToDB(GsonUtil.populateRealmDB(this));
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("FIRST_RUN", true);
            editor.apply();
        }

        if(Prefs.isExploreMode()){
            beaconController = new BeaconController(this);
        }


        context = this;
    }


    @Override
    protected void onResume() {
        if(beaconController!=null) {
            beaconController.bindConsumer(this);
            beaconController.setHandler(handler);
        }
        Log.d("Lifecycle", "onResume");
        super.onResume();
    }

    private void copyToDB(List<RealmObject> objects) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        for (RealmObject object : objects) {
            realm.copyToRealm(object);
        }
        realm.commitTransaction();
        realm.close();
    }

    public void setupBottomNavigation() {

        viewPager = (ViewPager) findViewById(edu.uwp.appfactory.racinezoo.R.id.view_pager);
        viewPager.setAdapter(new NavigationPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(4);


        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(edu.uwp.appfactory.racinezoo.R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case edu.uwp.appfactory.racinezoo.R.id.action_animal:
                                viewPager.setCurrentItem(0);
                                break;

                            case edu.uwp.appfactory.racinezoo.R.id.action_map:
                                viewPager.setCurrentItem(1);
                                break;

                            case edu.uwp.appfactory.racinezoo.R.id.action_event:
                                viewPager.setCurrentItem(2);
                                break;
                            case edu.uwp.appfactory.racinezoo.R.id.action_about:
                                viewPager.setCurrentItem(3);
                                break;

                        }
                        return true;
                    }
                });
    }

    

    @Override
    protected void onStop() {
        super.onStop();
        if(beaconController!=null){
            beaconController.unbindConsumer(this);
            beaconController.setHandler(null);
        }



    }


    @Override
    public void onBeaconServiceConnect() {
        beaconController.connectToService();
    }

    @Override
    public void initBeaconController() {
        beaconController = new BeaconController(this);
        beaconController.bindConsumer(this);
        beaconController.setHandler(handler);
        Log.d("Beacon", "BeaconController init after toggle explore mode on");
    }

    @Override
    public void deinitBeaconController() {
        if (beaconController != null) {
            beaconController.unbindConsumer(this);
            beaconController.setHandler(null);
            beaconController = null;
            Log.d("Beacon", "BeaconController deinit after toggle explore mode off");
        }
    }
}
