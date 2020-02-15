package com.example.mainactivity.broadcast;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.mainactivity.LookScreen;
import com.example.mainactivity.R;
import com.example.mainactivity.adapter.RecycleAdapter;
import com.example.mainactivity.flag.Flag;

public class AlarmReceiver extends BroadcastReceiver {
    public static String CHANELID = "ABC";
    public static int NTFID = 123456;
    Uri alarmUri;

    public AlarmReceiver() {
        alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("@ALARM", "GOOOO");
        if (intent.getAction().equals(RecycleAdapter.ACTION)) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(Flag.SP_KEY, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(Flag.SP_TIMEDOSLEEP);
            editor.apply();

            Intent goToLock = new Intent(context, LookScreen.class);
            PendingIntent close = PendingIntent.getActivity(context, 0, goToLock, 0);

            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(context, AlarmReceiver.CHANELID)
                            .setContentTitle("Báo Thức")
                            .setContentText("Nhấn để tắt")
                            .setSmallIcon(R.drawable.ntficon)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setAutoCancel(true)
                            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE))
                            .setContentIntent(close);

            NotificationManagerCompat nmc = NotificationManagerCompat.from(context);
            nmc.notify(AlarmReceiver.NTFID, builder.build());

            Intent intent1 = new Intent(context, LookScreen.class);
            if (Build.VERSION.SDK_INT >= 26) {
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent1);
        }
    }
}
