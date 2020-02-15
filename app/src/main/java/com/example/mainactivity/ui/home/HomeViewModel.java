package com.example.mainactivity.ui.home;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.mainactivity.core.SleepCaculate;
import com.example.mainactivity.model.TimeDoSleep;
import java.util.ArrayList;
public class HomeViewModel extends ViewModel {
    SleepCaculate sleepCaculate;
    private MutableLiveData<String> mText;
    private MutableLiveData<ArrayList<TimeDoSleep>> mTimeDoSleep;
    private  MutableLiveData<TimeDoSleep> mOldTime;
    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mTimeDoSleep = new MutableLiveData<>();
        mOldTime = new MutableLiveData<>();
        sleepCaculate = new SleepCaculate();
        mTimeDoSleep.setValue(sleepCaculate.getTimeDoSleep());
    }
    public void setTimeOld(TimeDoSleep timeDoSleep){
        mOldTime.setValue(timeDoSleep);
    }
    public MutableLiveData<TimeDoSleep> getmOldTime() {
        return mOldTime;
    }
    public void setmOldTime(MutableLiveData<TimeDoSleep> mOldTime) {
        this.mOldTime = mOldTime;
    }

    public LiveData<ArrayList<TimeDoSleep>> getmTimeDoSleep() {
        return mTimeDoSleep;
    }

    public void setmTimeDoSleep(MutableLiveData<ArrayList<TimeDoSleep>> mTimeDoSleep) {
        this.mTimeDoSleep = mTimeDoSleep;
    }

    public LiveData<String> getText() {
        return mText;
    }
}