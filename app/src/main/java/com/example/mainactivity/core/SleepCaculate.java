package com.example.mainactivity.core;

import com.example.mainactivity.model.TimeDoSleep;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.StringTokenizer;

public class SleepCaculate {
    Calendar calendar ;
    ArrayList<TimeDoSleep> time;
    public SleepCaculate() {
       calendar = new GregorianCalendar(Locale.getDefault());
    }
    public ArrayList<TimeDoSleep> getTimeDoSleep(){
        time = new ArrayList<>();
        calendar.setTime(new Date());
        int hour =calendar.get(Calendar.HOUR_OF_DAY);
        int minute =calendar.get(Calendar.MINUTE);
        for (int i = 3; i <7 ; i++) {
            time.add(famulaSleepTime(hour,minute,i));
        }
        return time;
    }
    private TimeDoSleep famulaSleepTime(int hour,int minute,int x){
        int[] ar =getNum((double) x*90/60);
        int rsHour=hour+ar[0];
        int rsMinute= (int) (minute+((ar[1]*0.1)*60)+14);
        return new TimeDoSleep(rsHour,rsMinute);
    }

    private int[] getNum(double a){
        String[] strings= new String[2];
        String value=String.valueOf( a);
        StringTokenizer stk = new StringTokenizer(value,".");
        if(value.contains(".")) {
            strings[0]=stk.nextToken();
            strings[1]=stk.nextToken();
        }else{
            strings[0]=stk.nextToken();
            strings[1]="0";
        }
        return  new int[]{Integer.parseInt(strings[0]),
                Integer.parseInt(strings[1])};
    }
}
