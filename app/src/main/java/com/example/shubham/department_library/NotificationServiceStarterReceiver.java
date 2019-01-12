package com.example.shubham.department_library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;

/**
 * Created by shubham on 12/20/2016.
 */

public final class NotificationServiceStarterReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            NotificationEventReceiver.cancelAlarm(context);
            Log.e("Started by","NotificationServiceStarterReceiver");
            Log.e("Action",String.valueOf(intent.getAction()));
            NotificationEventReceiver.setupAlarm2(context);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}