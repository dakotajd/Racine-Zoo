package edu.uwp.appfactory.racinezoo.Util;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import edu.uwp.appfactory.racinezoo.R;

/**
 * Created by dakota on 4/23/17.
 */

public class BeaconUtil {

    public interface BluetoothDialogListener{
        void setEnable(boolean enable);

    }


    private static BluetoothAdapter sBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    //private static final int REQUEST_ENABLE_BT = 50;
    private static final int REQUEST_ENABLE_LOCATION = 51;

    private static boolean isBluetoothSupported() {
        return sBluetoothAdapter != null;
    }

    public static boolean promptForBluetooth(Context activityContext, BluetoothDialogListener listener) {
        if (isBluetoothSupported()) {
            if (!sBluetoothAdapter.isEnabled()) {
                /*if (!sBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    activityContext.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                }*/
                showBluetoothAlertDialog(activityContext, listener);
            }
        } else {
            Toast.makeText(activityContext, "This feature requires a bluetooth enabled device to function properly.", Toast.LENGTH_LONG).show();
        }

        return isBluetoothSupported()&& sBluetoothAdapter.isEnabled();
    }

    public static void promptForLocationPermissions(Activity activityContext) {
        if (ContextCompat.checkSelfPermission(activityContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activityContext,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_ENABLE_LOCATION);
        }
    }

    private static void showBluetoothAlertDialog(Context context, final BluetoothDialogListener listener) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(context.getResources().getText(R.string.enable_bt));
        dialog.setMessage(context.getResources().getText(R.string.enable_bt_message));
        dialog.setPositiveButton(context.getResources().getText(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sBluetoothAdapter.enable();
                listener.setEnable(true);
            }
        });

        dialog.setNegativeButton(context.getResources().getText(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                listener.setEnable(false);
            }
        });
        dialog.setIcon(R.drawable.ic_bluetooth);
        dialog.show();
    }
}
