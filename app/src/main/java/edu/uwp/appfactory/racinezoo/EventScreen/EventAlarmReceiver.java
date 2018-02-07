package edu.uwp.appfactory.racinezoo.EventScreen;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import edu.uwp.appfactory.racinezoo.R;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by dakota on 3/19/17.
 */

public class EventAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);
        // build notification
        // the addAction re-use the same intent to keep the example short
        Notification n  = new Notification.Builder(context)
                .setContentTitle(intent.getStringExtra("EVENT_NAME"))
                .setContentText(intent.getStringExtra("NOTIF_INFO"))
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentIntent(pIntent)
                .setAutoCancel(true).build();


        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);

    }
}
