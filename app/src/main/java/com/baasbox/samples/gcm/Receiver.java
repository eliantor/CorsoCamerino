package com.baasbox.samples.gcm;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by eto on 13/04/14.
 */
public class Receiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ComponentName forwardTo =
                new ComponentName(context.getPackageName(),
                                   ReceiveService.class.getName());
        startWakefulService(context,intent.setComponent(forwardTo));
    }
}
