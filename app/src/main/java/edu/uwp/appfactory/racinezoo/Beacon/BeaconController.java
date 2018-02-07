package edu.uwp.appfactory.racinezoo.Beacon;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import edu.uwp.appfactory.racinezoo.Util.Config;

/**
 * Created by hanh on 4/22/17.
 */

public class BeaconController {
    private BeaconManager beaconManager;

    private int currentId = -1;

    private Handler handler;

    public void setHandler(Handler handler){
        this.handler = handler;
    }
    public BeaconController(Context context){
        beaconManager = BeaconManager.getInstanceForApplication(context);
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(Config.BEACON_LAYOUT));


    }

    private static final String TAG = "Beacon";
    private static final String BEACON_UUID = "8AEFB031-6C32-486F-825B-E26FA193487D";


    public void connectToService(){
        Log.d(TAG, "connect service");

        try {
            beaconManager.setForegroundBetweenScanPeriod(200);
            beaconManager.updateScanPeriods();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        RangeNotifier rangeNotifier = new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(final Collection<Beacon> beacons, Region region) {
                if(beacons.size()>0){
                    Beacon beacon = Collections.min(beacons, new Comparator<Beacon>() {
                        @Override
                        public int compare(Beacon b1, Beacon b2) {
                            if(b1.getRssi()==b2.getRssi()){

                                return 0;
                            }
                            else {
                                return b1.getRssi() < b2.getRssi()? 1: -1;
                            }
                        }
                    });


                    if(beacon.getId3().toInt() != currentId){
                        currentId = beacon.getId3().toInt();
                        setBeaconId(currentId);
                    }
                    Log.d(TAG, "Closest beacon as id   " + beacon.getId3().toInt());


                }
            }
        };

        try {
            beaconManager.startRangingBeaconsInRegion(new Region(BEACON_UUID, null, null, null));
            beaconManager.addRangeNotifier(rangeNotifier);
            Log.d(TAG, "Enter beacon in region.");
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        //        beaconManager.addMonitorNotifier(new MonitorNotifier() {
//            @Override
//            public void didEnterRegion(Region region) {
//                Log.d(TAG, "Enter beacon in region.");
//                try {
//                    beaconManager.startRangingBeaconsInRegion(region);
//                    beaconManager.addRangeNotifier(rangeNotifier);
//                    Log.d(TAG, "Enter beacon in region.");
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void didExitRegion(Region region) {
//
//            }
//
//            @Override
//            public void didDetermineStateForRegion(int i, Region region) {
//
//            }
//        });
//
//        try {
//            beaconManager.startMonitoringBeaconsInRegion(new Region(BEACON_UUID, null, null, null));
//        } catch (RemoteException e) {
//        }
    }

    private void setBeaconId(int id){
        if(handler!=null){
            Message message = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putInt("beaconId",id);
            message.setData(bundle);
            handler.sendMessage(message);
        }

    }



    public void bindConsumer(BeaconConsumer consumer){
        beaconManager.bind(consumer);
    }

    public void unbindConsumer(BeaconConsumer consumer){
        beaconManager.unbind(consumer);
    }



}
