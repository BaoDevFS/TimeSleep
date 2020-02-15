package com.example.mainactivity;

import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mainactivity.broadcast.AlarmReceiver;

import java.io.IOException;

public class LookScreen extends AppCompatActivity {
    Button btclose;
    public final String TAG = this.getClass().getSimpleName();

    private PowerManager.WakeLock mWakelock;
    private MediaPlayer mMedia;
    private static int WAKELOCK_TIME = 60 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_screen);
        btclose = findViewById(R.id.btClearAlarm);
        btclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMedia.stop();
                closeNotification();
                finish();
            }
        });

        mMedia = new MediaPlayer();

        try {
            Uri toneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            if (toneUri != null) {
                mMedia.setDataSource(this, toneUri);
                mMedia.setAudioStreamType(AudioManager.STREAM_ALARM);
                mMedia.setLooping(true);
                mMedia.prepare();
                mMedia.start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Runnable releaseWakelock = new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

                if (mWakelock != null && mWakelock.isHeld()) {
                    mWakelock.release();
                }
            }
        };

        new Handler().postDelayed(releaseWakelock, WAKELOCK_TIME);
    }
    public void closeNotification(){
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(AlarmReceiver.NTFID);
    }
    @SuppressWarnings("deprecation")
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        PowerManager pm = (PowerManager) getApplication().getSystemService(Context.POWER_SERVICE);

        if (mWakelock == null) {
            mWakelock = pm.newWakeLock((PowerManager.FULL_WAKE_LOCK |
                    PowerManager.SCREEN_BRIGHT_WAKE_LOCK |
                    PowerManager.ACQUIRE_CAUSES_WAKEUP), TAG);
        }

        if (!mWakelock.isHeld()) {
            mWakelock.acquire();
            Log.i(TAG, "Wakelog acquired!");
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (mWakelock != null && mWakelock.isHeld()) {
            mWakelock.release();
        }
    }
}
