package com.myst.notifyme;

import android.app.IntentService;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Myst on 1/27/2017.
 */

public class NotifyMeService extends IntentService {

    private static final int NOTIFICATION_ID = 7126;
    private TimeTableAdapter timeTableAdapter;
    private String day;
    private String time;
    private String[] data;
    private String[] parsedData;
    private StringBuilder parsedSubject;
    private static int index;
    private Handler handler;
    NotificationCompat.Builder notification;

    public NotifyMeService() {
        super("NotifyMe");
    }


    @Override
    public void onCreate() {
        super.onCreate();

        handler = new Handler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Calendar calendar = Calendar.getInstance();
        timeTableAdapter = new TimeTableAdapter(this);
        Date date = calendar.getTime();
        day = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
        data = timeTableAdapter.getAllData(day);

        long time = System.currentTimeMillis();

        if(time == 28800000){

            for(int i=1;i<=8;i++){
                parsedData = data[i].split("[=,\n]+");
                if(!parsedData[3].equals("None")){
                    parsedSubject.append(parsedData[3]+"\n");
                }
            }

                     notification = new NotificationCompat.Builder(getApplicationContext())
                    .setContentTitle("Your Timetable Subjects Are ")
                    .setSmallIcon(R.drawable.notify)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(parsedSubject))
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setDefaults(NotificationCompat.DEFAULT_ALL);
        }

        else {

            if (time >= 30600000 && time < 32400000) {
                index = 1;  //time = 8:30 - 8:59
            }

            if (time >= 32400000 && time < 36000000) {
                index = 2;  //time = 9:00 - 9:59
            }

            if (time >= 36000000 && time < 39600000) {
                index = 3;  // time = 10:00 - 10:59
            }
            if (time >= 39600000 && time < 43200000) {
                index = 4;  // time = 11:00 - 11:59
            }
            if (time >= 43200000 && time < 46800000) {
                index = 5;  // time = 12:00 - 12:59
            }
            if (time >= 46800000 && time < 50400000) {
                index = 6;  // time = 13:00 - 13:59
            }
            if (time >= 50400000 && time < 54000000) {
                index = 7;  // time = 14:00 - 14:59
            }
            if (time >= 54000000 && time < 57600000) {
                index = 8;  // time = 15:00 - 15:59
            }

                     notification = new NotificationCompat.Builder(getApplicationContext())
                    .setContentTitle("Your Current TimeTable Is ")
                    .setSmallIcon(R.drawable.notify)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(data[0] + "\n" + data[index]))
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setDefaults(NotificationCompat.DEFAULT_ALL);

        }

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(NOTIFICATION_ID, notification.build());

    }


}
