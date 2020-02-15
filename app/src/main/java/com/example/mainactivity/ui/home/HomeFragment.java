package com.example.mainactivity.ui.home;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainactivity.MainActivity;
import com.example.mainactivity.R;
import com.example.mainactivity.adapter.RecycleAdapter;
import com.example.mainactivity.model.TimeDoSleep;
import com.example.mainactivity.service.AlarmService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment {
    ArrayList<TimeDoSleep> mTimeDoSleep;
    private HomeViewModel homeViewModel;
    TextView tvOldTime, tvOldDay;
    Switch swAlarm;
    RecyclerView recyclerView;
    RecycleAdapter recycleAdapter;
    AlarmManager alarmManager;
    View view;
    @SuppressLint("WrongConstant")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        view = root.findViewById(R.id.timeOld);
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        setDataOldTime();
        recyclerView = root.findViewById(R.id.recycleView);
        tvOldTime = root.findViewById(R.id.tvOldTime);
        tvOldDay = root.findViewById(R.id.tvOldDay);
        swAlarm =root.findViewById(R.id.swOldAlarm);
        alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        homeViewModel.getmTimeDoSleep().observe(getActivity(), new Observer<ArrayList<TimeDoSleep>>() {
            @Override
            public void onChanged(ArrayList<TimeDoSleep> timeDoSleeps) {
                mTimeDoSleep = timeDoSleeps;
                recycleAdapter = new RecycleAdapter(HomeFragment.this.getContext(), mTimeDoSleep, alarmManager);
                recyclerView.setAdapter(recycleAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(HomeFragment.this.getContext()));
            }
        });
        swAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    ((MainActivity)getActivity()).deleteTimeOld();
                    setDataOldTime();
                }
            }
        });
        homeViewModel.getmOldTime().observe(getActivity(), new Observer<TimeDoSleep>() {
            @Override
            public void onChanged(TimeDoSleep timeDoSleep) {
                if (timeDoSleep.getHour() != -1) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, timeDoSleep.getHour());
                    calendar.set(Calendar.MINUTE, timeDoSleep.getMinute());
                    Date date = calendar.getTime();
                    SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy ");
                    SimpleDateFormat format2 = new SimpleDateFormat("HH : mm");
                    tvOldTime.setText(format2.format(date));
                    tvOldDay.setText(format1.format(date));
                    swAlarm.setChecked(true);
                } else {
                    view.setVisibility(View.GONE);
                }
            }
        });
        return root;
    }

    private void setDataOldTime() {
        TimeDoSleep timeDoSleep = ((MainActivity) getActivity()).getTimeOld();
        if (timeDoSleep == null) {
            homeViewModel.setTimeOld(timeDoSleep = new TimeDoSleep());
        } else {
            homeViewModel.setTimeOld(timeDoSleep);
        }
    }
}