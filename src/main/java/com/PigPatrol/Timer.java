package com.PigPatrol;

/**
 * Created by gdeshazer on 4/19/16.
 */
public class Timer {
    public Timer(){
        _t = 0;
        startTime = System.currentTimeMillis();
        time = 0;
    }

    public void setStartTime(){
        startTime = System.currentTimeMillis();
    }

    public void setTime(){
        time = System.currentTimeMillis();
    }

    public long getTime(){
        return time;
    }

    public long getCurrentTime(){
        return System.currentTimeMillis();
    }

    public long getDeltaTimeFromStart(){
        _t = System.currentTimeMillis() - startTime;
        return _t;
    }

    private long startTime;
    private long _t;
    private long time;

}