package com.myst.notifyme;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import java.util.Calendar;

/**
 * Created by Myst on 1/29/2017.
 */

public class MyReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,15);
        calendar.set(Calendar.MINUTE,45);

        if(System.currentTimeMillis() >= calendar.getTimeInMillis()){
            AlarmManager am = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);

            PendingIntent i = PendingIntent.getBroadcast(context,22,intent,0);
            am.cancel(i);
        }
            context.startService(new Intent(context, NotifyMeService.class));
    }
}
