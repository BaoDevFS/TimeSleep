package com.example.mainactivity.model;

import java.io.Serializable;

public class TimeDoSleep implements Serializable {
    int hour=-1;
    int minute=-1;

    public TimeDoSleep(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }


    @Override
    public String toString() {
        return  hour + " : " + minute;
    }

    public TimeDoSleep() {
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
