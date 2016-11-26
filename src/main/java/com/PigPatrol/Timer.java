package com.PigPatrol;

/**
 * Created by Grant DeShazer on 4/19/16.
 *
 * Timer class
 *
 *  Generated objects used to keep track of timing functions in main loop.
 *  Can be customized to perform timing functions in milliseconds, nanoseconds,
 *  or microseconds.  Default timing behavior is in milliseconds.
 */
public class Timer {

    public Timer(){
        _s = "millis";
        _t = 0;
        _time = 0;
        startTime = System.currentTimeMillis();
        System.err.println("Defaulting to millisecond timing");
    }

    public Timer(String s) {
        if (s == "millis") {
            _s = "millis";
            _t = 0;
            _time = 0;
            startTime = System.currentTimeMillis();
        } else if (s == "nano") {
            _s = "nano";
            _t = 0;
            _time = 0;
            startTime = System.nanoTime();
        } else if (s == "micro"){
            _s = "micro";
            _t = 0;
            _time = 0;
            startTime = System.nanoTime();

        } else {
            _s = "millis";
            _t = 0;
            _time = 0;
            startTime = System.currentTimeMillis();
            System.err.println("Incorrect timer selection");
        }
    }

    //Store starting time from which delta time is calculated from.
    public void setStartTime() {

        if (_s == "millis") {
            startTime = System.currentTimeMillis();
        } else if (_s == "nano"){
            startTime = System.nanoTime();
        } else if (_s == "micro"){
            startTime = this.nanoToMicro(System.nanoTime());
        }

    }

    //Return current time stamp in units specified by object.
    public long getCurrentTime() {

        if(_s == "millis"){
            return System.currentTimeMillis();
        } else if (_s == "nano") {
            return System.nanoTime();
        } else if (_s == "micro"){
            return this.nanoToMicro(System.nanoTime());
        }

        return 0;
    }

    //Returns elapsed time in units specified by object.
    public long getDeltaTFromStart() {

        if (_s == "millis"){
            _t = System.currentTimeMillis() - startTime;
        } else if (_s == "nano"){
            _t = System.nanoTime() - startTime;
        } else if (_s == "micro"){
            _t = this.nanoToMicro(System.nanoTime()) - startTime;
        }

        return _t;
    }

    //----------------------------------------//
    //----------- TIME CONVERSIONS -----------//
    //----------------------------------------//
    public long nanoToMicro(long time){
        return (long) (((double) time) * 0.001);
    }

    private long nanoToMillis(long time){
        return (long) (((double)time) *  Math.pow(10,-6));
    }

    //Object variables
    private long startTime;
    private long _time;
    private long _t;
    private String _s;

}