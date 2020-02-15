package com.example.mainactivity.service;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import com.example.mainactivity.MainActivity;
import com.example.mainactivity.R;
import com.example.mainactivity.adapter.RecycleAdapter;
import com.example.mainactivity.broadcast.AlarmReceiver;
import com.example.mainactivity.model.TimeDoSleep;
import java.util.Calendar;
public class AlarmService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TimeDoSleep timeDoSleep = (TimeDoSleep) intent.getSerializableExtra("timeDoSleep");
        Log.e("@ALARM", timeDoSleep.toString());
        alarmOn(getBaseContext(), timeDoSleep);
        startForeground();
        return START_STICKY;
    }

    private void startForeground() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);
        startForeground(123, new NotificationCompat.Builder(this,
                AlarmReceiver.CHANELID) // don't forget create a notification channel first
                .setSmallIcon(R.drawable.bell)
                .setContentTitle("Alarm")
                .setContentText("Service is running background")
                .setContentIntent(pendingIntent)
                .setAutoCancel(false)
                .build());
    }

    public void alarmOn(Context context, TimeDoSleep timeDoSleep) {
        Log.e("@ALARM", "ON");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar localcalendar = Calendar.getInstance();
        localcalendar.setTimeInMillis(System.currentTimeMillis());
        localcalendar.set(Calendar.HOUR_OF_DAY, timeDoSleep.getHour());
        localcalendar.set(Calendar.MINUTE, timeDoSleep.getMinute());
        Intent intent1 = new Intent(context, AlarmReceiver.class);
        intent1.setAction(RecycleAdapter.ACTION);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context, RecycleAdapter.REQUESTCODE, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= 23) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, localcalendar.getTimeInMillis(), pendingIntent1);
        } else if (Build.VERSION.SDK_INT >= 19) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, localcalendar.getTimeInMillis(), pendingIntent1);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, localcalendar.getTimeInMillis(), pendingIntent1);
        }
        //test
//        if (Build.VERSION.SDK_INT >= 23) {
//            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 6 * 60 * 1000, pendingIntent1);
//        } else if (Build.VERSION.SDK_INT >= 19) {
//            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 6 * 60 * 1000, pendingIntent1);
//        } else {
//            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 6 * 60 * 1000, pendingIntent1);
//        }
    }

    public void alarmOff(Context context) {
        Log.e("@ALARM", "OFF");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent1 = new Intent(context, AlarmService.class);
        intent1.setAction(RecycleAdapter.ACTION);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context, RecycleAdapter.REQUESTCODE, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent1);
    }

    public void closeNotification() {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(AlarmReceiver.NTFID);
    }

    @Override
    public void onDestroy() {
        closeNotification();
        super.onDestroy();
    }
}
