package com.baasbox.samples;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

/**
 * Created by eto on 13/04/14.
 */
public class GcmNotification {

    public static void showNotification(Context context){
        NotificationCompat.Builder b = new NotificationCompat.Builder(context);
        b.setContentTitle("New Message")
                .setContentText("A message has arrived")
                .setSmallIcon(R.drawable.ic_launcher);

        Notification notificati = b.build();

        NotificationManager m = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        m.notify(1,notificati);
    }
}
