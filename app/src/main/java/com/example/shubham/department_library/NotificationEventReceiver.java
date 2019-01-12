package com.example.shubham.department_library;

/**
 * Created by shubham on 12/20/2016.
 */

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



/**
 * Created by klogi
 *
 * WakefulBroadcastReceiver used to receive intents fired from the AlarmManager for showing notifications
 * and from the notification itself if it is deleted.
 */
public class NotificationEventReceiver extends WakefulBroadcastReceiver {

    private static final String ACTION_START_NOTIFICATION_SERVICE = "ACTION_START_NOTIFICATION_SERVICE";
    private static final String ACTION_DELETE_NOTIFICATION = "ACTION_DELETE_NOTIFICATION";
    Context context;



    public static void setupAlarm(Context context) throws ParseException {
        Log.e("context1",String.valueOf(context));

        DatabaseHandler db=new DatabaseHandler(context);
        Cursor c=db.viewIssue();
        if(c.getCount()>0) {

            if (c.moveToFirst())
                do {

                    String return_date = c.getString(c.getColumnIndex("return_date"));
                    String notification = c.getString(c.getColumnIndex("notification"));
                    if(notification.equals("0")) {
                        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                        Log.e("Notification",notification);
                        String book_id = c.getString(c.getColumnIndex("book_id"));
                        int book_id1=Integer.valueOf(book_id);
                        PendingIntent alarmIntent = getStartPendingIntent(context, c.getString(c.getColumnIndex("book_name")),book_id1,return_date);

                        alarmManager.set(AlarmManager.RTC_WAKEUP,
                                getTriggerAt(return_date), alarmIntent);
                    }


                } while (c.moveToNext());

        }


    }

    public static void setupAlarm2(Context context) throws ParseException {
        Log.e("Boot","Boot");

        DatabaseHandler db=new DatabaseHandler(context);
        Cursor c=db.viewIssue();
        if(c.getCount()>0) {

            if (c.moveToFirst())
                do {

                    String return_date = c.getString(c.getColumnIndex("return_date"));
                    String notification = c.getString(c.getColumnIndex("notification"));

                        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                        Log.e("Notification",notification);
                        String book_id = c.getString(c.getColumnIndex("book_id"));
                        int book_id1=Integer.valueOf(book_id);
                        PendingIntent alarmIntent = getStartPendingIntent(context,c.getString(c.getColumnIndex("book_name")),book_id1,return_date);

                        alarmManager.set(AlarmManager.RTC_WAKEUP,
                                getTriggerAt(return_date), alarmIntent);



                } while (c.moveToNext());

        }


    }
    public static void cancelAlarm(Context context) {


        DatabaseHandler db=new DatabaseHandler(context);
        Cursor c=db.viewIssue();
        if(c.getCount()>0) {

            if (c.moveToFirst())
                do {
                    String return_date = c.getString(c.getColumnIndex("return_date"));

                        String book_id = c.getString(c.getColumnIndex("book_id"));
                        int book_id1=Integer.valueOf(book_id);
                        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                        PendingIntent alarmIntent = getDeletependingIntent(context,c.getString(c.getColumnIndex("book_name")),book_id1,return_date);
                        alarmManager.cancel(alarmIntent);
                } while (c.moveToNext());

        }
        Log.e("cancel","Alarms cancled");


    }

    private static long getTriggerAt(String now) throws ParseException {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date d = dateFormat.parse(now);
        Log.e("Date",String.valueOf(d));
        calendar.setTime(d);
        calendar.set(Calendar.HOUR, 11);
        calendar.set(Calendar.MINUTE, 46);// 1481010780000
        calendar.set(Calendar.SECOND, 0);//1478855630000
        calendar.set(Calendar.AM_PM, Calendar.AM);
        d.setTime(calendar.getTimeInMillis());






        Log.e("Date",String.valueOf(d));
        Log.e("Hello",String.valueOf(calendar.getTimeInMillis()));
        //calendar.add(Calendar.HOUR, NOTIFICATIONS_INTERVAL_IN_HOURS);
        return calendar.getTimeInMillis();

    }

    private static PendingIntent getStartPendingIntent(Context context,String name,int i,String return_date) {
        Intent intent = new Intent(context, NotificationEventReceiver.class);
        intent.setAction(ACTION_START_NOTIFICATION_SERVICE);
        Log.e("name2",name);
        intent.putExtra("name",name);
        intent.putExtra("id",i);
        intent.putExtra("return_date",return_date);
        return PendingIntent.getBroadcast(context, i, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static PendingIntent getDeletependingIntent(Context context,String name,int i,String return_date) {
        Intent intent = new Intent(context, NotificationEventReceiver.class);
        intent.setAction(ACTION_START_NOTIFICATION_SERVICE);
        intent.putExtra("name",name);
        intent.putExtra("id",i);
        intent.putExtra("return_date",return_date);
        return PendingIntent.getBroadcast(context, i, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static PendingIntent getDeleteIntent(Context context) {
        Intent intent = new Intent(context, NotificationEventReceiver.class);
        intent.setAction(ACTION_DELETE_NOTIFICATION);

        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Intent serviceIntent = null;
        String n=intent.getStringExtra("name");
        int i=intent.getIntExtra("id",0);

        Log.e("name","Hello");
        if (ACTION_START_NOTIFICATION_SERVICE.equals(action)) {
            Log.i(getClass().getSimpleName(), "onReceive from alarm, starting notification service");
            serviceIntent = NotificationIntentService.createIntentStartNotificationService(context);
        } else if (ACTION_DELETE_NOTIFICATION.equals(action)) {
            Log.i(getClass().getSimpleName(), "onReceive delete notification action, starting notification service to handle delete");
            serviceIntent = NotificationIntentService.createIntentDeleteNotification(context);
        }

        if (serviceIntent != null) {
            // Start the service, keeping the device awake while it is launching.
            String name=intent.getStringExtra("name");
            String return_date=intent.getStringExtra("return_date");
            serviceIntent.putExtra("name",name);
            serviceIntent.putExtra("id",i);

            serviceIntent.putExtra("return_date",return_date);
            startWakefulService(context, serviceIntent);
        }
    }
}
