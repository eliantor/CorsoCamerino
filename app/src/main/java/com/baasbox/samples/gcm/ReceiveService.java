package com.baasbox.samples.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * Created by eto on 13/04/14.
 */
public class ReceiveService extends IntentService {


    public ReceiveService() {
        super(ReceiveService.class.getName());
    }





    @Override
    protected void onHandleIntent(Intent intent) {
        GoogleCloudMessaging gcm =GoogleCloudMessaging.getInstance(this);
        Bundle extras = intent.getExtras();
        String message = gcm.getMessageType(intent);
        if (extras!=null){
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(message)){
                Log.d("LOG","RECEIVED");

            }

        }
        Receiver.completeWakefulIntent(intent);
    }
}
