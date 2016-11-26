package com.PigPatrol;


import junit.framework.*;
import org.junit.Test;

/**
 * Created by Grant DeShazer on 9/6/16.
 *
 * Tester for Timing class.  This is designed to test
 * conversion accuracy and the busy wait loop timing.
 *
 * Functions were copied from Timer class.  Future updates
 * to this file should make an attempt to use the Timer class
 * itself so that changes made to Timer will still be tested here.
 *
 */
public class TimerTest extends TestCase {

    @Test
    public static void setStartTime() {

        if (_s == "millis") {
            startTime = System.currentTimeMillis();
        } else if (_s == "nano") {
            startTime = System.nanoTime();
        } else if (_s == "micro") {
            startTime = nanoToMicro(System.nanoTime());
        }

    }

    @Test
    public static long getCurrentTime() {

        if (_s == "millis") {
            return System.currentTimeMillis();
        } else if (_s == "nano") {
            return System.nanoTime();
        } else if (_s == "micro") {
            return nanoToMicro(System.nanoTime());
        }

        return 0;
    }

    @Test
    public static long getDeltaTimeFromStart() {

        if (_s == "millis") {
            _t = System.currentTimeMillis() - startTime;
        } else if (_s == "nano") {
            _t = System.nanoTime() - startTime;
        } else if (_s == "micro") {
            _t = nanoToMicro(System.nanoTime()) - startTime;
        }

        return _t;
    }

    @Test
    public static void busyWaitTest() {
        long t = 1000;
        long stime = 0;
        long time = 0;


//        stime = System.nanoTime();
//        while (time < t) {
//            time = System.nanoTime() - stime;
//        }

        setStartTime();
        while (time < t) {
            time = getDeltaTimeFromStart();
        }

        System.out.println("Loop time: " + time);

        long diff = time - t;
        if (diff < 0){
            diff = diff * (-1);
        }

        //usually runs but depends a lot on processor availability
        //Spikes in processor demand can slow this loop down enough to
        //fail the test.
        //assertTrue(diff <= 1000);
    }

    @Test
    public static long nanoToMicro(long time) {
        return (long) (((double) time) * 0.001);
    }


    @Test
    public static void testRunner() {
        _s = "nano";
        startTime = 0;
        _t = 0;

        long nano = 1000;
        long millis = nanoToMicro(nano);


        assertEquals(1, millis);


        for(int i = 0; i < 10; i++) {
            busyWaitTest();
        }


    }

    public static long startTime;
    public static long _t;
    public static String _s;

}
