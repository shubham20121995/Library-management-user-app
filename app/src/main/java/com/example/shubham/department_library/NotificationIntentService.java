package com.example.shubham.department_library;

/**
 * Created by shubham on 12/20/2016.
 */
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import static android.content.Intent.getIntent;


/**
 * Created by klogi
 *
 *
 */
public class NotificationIntentService extends IntentService {


    private static final String ACTION_START = "ACTION_START";
    private static final String ACTION_DELETE = "ACTION_DELETE";

    public NotificationIntentService() {
        super(NotificationIntentService.class.getSimpleName());
    }

    public static Intent createIntentStartNotificationService(Context context) {
        Intent intent = new Intent(context, NotificationIntentService.class);
        intent.setAction(ACTION_START);

        return intent;
    }

    public static Intent createIntentDeleteNotification(Context context) {
        Intent intent = new Intent(context, NotificationIntentService.class);
        intent.setAction(ACTION_DELETE);
        return intent;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(getClass().getSimpleName(), "onHandleIntent, started handling a notification event");
        try {
            String action = intent.getAction();
            if (ACTION_START.equals(action)) {
                String name=intent.getStringExtra("name");
                int i=intent.getIntExtra("id",0);
                String return_date=intent.getStringExtra("return_date");
                Log.e("name",name);
                processStartNotification(name,i,return_date);
            }
        } finally {
            WakefulBroadcastReceiver.completeWakefulIntent(intent);
        }
    }

    private void processDeleteNotification(Intent intent) {
        // Log something?
    }

    private void processStartNotification(String name,int i,String return_date) {
        // Do something. For example, fetch fresh data from backend to create a rich notification?
Log.e("name4",name);
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Return book reminder")
                .setAutoCancel(true)
                .setColor(getResources().getColor(R.color.colorAccent))
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(return_date + " is the last day to return your book '"+name+"'. Ignore if already returned or re-issued"))
                .setContentText(return_date +" is the last day to return your book '"+name+"'. Ignore if already returned or re-issued")
                .setSmallIcon(R.drawable.all_books)
                ;

        Intent mainIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                i,
                mainIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setDeleteIntent(NotificationEventReceiver.getDeleteIntent(this));

        final NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(i, builder.build());


    }
}

