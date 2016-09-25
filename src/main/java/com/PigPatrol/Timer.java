package com.PigPatrol;

/**
 * Created by gdeshazer on 4/19/16.
 */
public class Timer {

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

    public void setStartTime() {

        if (_s == "millis") {
            startTime = System.currentTimeMillis();
        } else if (_s == "nano"){
            startTime = System.nanoTime();
        } else if (_s == "micro"){
            startTime = this.nanoToMicro(System.nanoTime());
        }

    }

    public void setTime(){
        if(_s == "millis"){
            _time = System.currentTimeMillis();
        } else if (_s == "nano") {
            _time = System.nanoTime();
        } else if (_s == "micro"){
            _time = this.nanoToMicro(System.nanoTime());
        }
    }

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

    public long getDeltaTimeFromStart() {

        if (_s == "millis"){
            _t = System.currentTimeMillis() - startTime;
        } else if (_s == "nano"){
            _t = System.nanoTime() - startTime;
        } else if (_s == "micro"){
            _t = this.nanoToMicro(System.nanoTime()) - startTime;
        }

        return _t;
    }


    public long nanoToMicro(long time){
        return (long) (((double) time) * 0.001);
    }

    private long nanoToMillis(long time){
        return (long) (((double)time) *  Math.pow(1,-6));
    }

    private long startTime;
    private long _time;
    private long _t;
    private String _s;

}