package com.example.mainactivity.adapter;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainactivity.MainActivity;
import com.example.mainactivity.R;
import com.example.mainactivity.model.TimeDoSleep;
import com.example.mainactivity.service.AlarmService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    Context context;
    ArrayList<TimeDoSleep> mTime;
    Calendar calendar;
    Date date;
    AlarmManager alarmManager;
    public static int REQUESTCODE = 123;

    public static String ACTION = "WAKEUP";

    public RecycleAdapter(Context context, ArrayList<TimeDoSleep> mTime, AlarmManager alarmManager) {
        this.context = context;
        this.mTime = mTime;
        this.alarmManager = alarmManager;
        Log.e("@ALARM", "" + mTime.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.rowlayout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final TimeDoSleep timeDoSleep = mTime.get(position);
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, timeDoSleep.getHour());
        calendar.set(Calendar.MINUTE, timeDoSleep.getMinute());
        date = calendar.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy ");
        SimpleDateFormat format2 = new SimpleDateFormat("HH : mm");
        holder.tvTime.setText(format2.format(date));
        holder.tvDay.setText(format1.format(date));
        holder.swAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Intent intent = new Intent(context,AlarmService.class);
                    intent.putExtra("timeDoSleep",timeDoSleep);
                    if(((MainActivity)context).setTimeOld(timeDoSleep)){
                    if (Build.VERSION.SDK_INT >= 26) {
                        Log.e("@ALARM", ">=26");
                        context.startForegroundService(intent);
                    } else {
                        Log.e("@ALARM", "<26");
                       context.startService(intent);
                    }
                    }
                } else {
                    AlarmService alarmService = new AlarmService();
                    alarmService.alarmOff(context);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTime.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime, tvDay;
        //        ImageButton btAlarm;
        Switch swAlarm;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvDay = itemView.findViewById(R.id.tvDay);
            swAlarm = itemView.findViewById(R.id.swAlarm);
        }
    }
}
