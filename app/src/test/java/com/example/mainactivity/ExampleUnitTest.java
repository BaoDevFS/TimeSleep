package com.example.mainactivity;

import com.example.mainactivity.core.SleepCaculate;
import com.example.mainactivity.model.TimeDoSleep;
import com.google.gson.Gson;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
        Calendar calendar = new GregorianCalendar(Locale.getDefault());
    @Test
    public void addition_isCorrect() throws InterruptedException {
            // and the current date and time
//            for (int i=0;i<100;i++) {
//                    Thread.sleep(900);
//                    calendar.setTime(new Date());
//                    System.out.println("HOUR_OF_DAY: " + calendar.get(Calendar.HOUR_OF_DAY));
//                    System.out.println("MINUTE: " + calendar.get(Calendar.MINUTE));
//                    System.out.println("SECOND: " + calendar.get(Calendar.SECOND));
//                    System.out.println("MILLISECOND: " + calendar.get(Calendar.MILLISECOND));
//                System.out.println("MILLISECOND: " + calendar.get(Calendar.DAY_OF_MONTH));
//            }
//        SleepCaculate sleepCaculate = new SleepCaculate();
//        System.out.println(sleepCaculate.getTimeDoSleep());
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY,30);
//        Date date = calendar.getTime();
//        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
//        String date1 = format1.format(date);
//        System.out.println(date1);
//        Calendar calendar =Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY,30);
//        calendar.set(Calendar.MINUTE,96);
//        System.out.println(calendar.get(Calendar.HOUR_OF_DAY));
//        System.out.println(calendar.get(Calendar.MINUTE));
//        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
//        System.out.println(7.5/100+"----"+(String.valueOf(new Double(3*90)/60).contains(".")));
//
    }
}