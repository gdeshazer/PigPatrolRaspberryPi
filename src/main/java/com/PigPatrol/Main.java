package com.PigPatrol;

/**
 * Created by gdeshazer on 4/14/16.
 *
 * I2C controller using pi4j library.
 *
 * Currently collects 2 floats from connected I2C device and stores the returned values in a flat file.
 *
 * Data stored via the java built in logger, which is fairly easy to implement as it avoids file i/o.
 *
 */

import com.pi4j.io.gpio.*;

import java.io.*;
import java.util.logging.*;


public class Main {

    //set up for logger
    private static final Logger  LOGGER = Logger.getLogger( Main.class.getName() );
    private static FileHandler fh = null;
    private static ConsoleHandler ch = new ConsoleHandler();
    private static DataLogFormater form = new DataLogFormater();

    private static GpioController gpio;
    private static GpioPinDigitalInput button;

    //Logger configuration
    private static void init(){

        int fileSize = (int) (5 * Math.pow(10,6));
        int numberOfFiles = 10;

        try{
            //Path specified here is specific to Raspberry Pi
            //boolean flag sets whether files will be overwritten or appended to.  True means files will be
            //appended.
            fh = new FileHandler("/home/pi/Desktop/PigPatrolStuff/data/data%g.log",fileSize,numberOfFiles, true);
        }catch (IOException e){
            System.err.println("Failed to open file");
            e.printStackTrace();
        }

        LOGGER.setUseParentHandlers(false);
        LOGGER.addHandler(ch);
        LOGGER.addHandler(fh);
        fh.setFormatter(form);
        fh.setLevel(Level.INFO);
        ch.setFormatter(new SimpleFormatter());
        ch.setLevel(Level.OFF);  //disables console output

    }

    //pin initialization
    private static void initPin(){
        System.out.println("Initilizing pins");
        gpio= GpioFactory.getInstance();
        button = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00); //GPIO 0 starts at pin 11 on the break out
                                                                  //labeled as GPIO 17

    }

    public static void main(String[] args) throws IOException {
        initPin();
        init();

        Timer timer = new Timer("micro");
        Timer sampleTime = new Timer("micro");

        int counter = 0;
        int delayTime = 25;

        I2CControl controller = new I2CControl();


        boolean state = false;
        String previous = "LOW";
        Timer switchDebounce = new Timer("mills");
        Timer switchDelay = new Timer("millis");
        PinState p = button.getState();

        while(true) {
            switchDelay.setStartTime();
            if (switchDelay.getDeltaTimeFromStart() > 100) {
                p = button.getState();
                switchDelay.setStartTime();
            }

            //button polling to toggle data collection and storage
            if (p.toString() == "HIGH" && previous == "LOW" && switchDebounce.getDeltaTimeFromStart() > 10){

                if(state == false){
                    timer.setStartTime();
                    state = true;
                    System.out.println("Starting");
                } else {
                    state = false;
                    System.out.println("Finished");
                }

                switchDebounce.setStartTime();
            }

            if(p.toString() == "LOW"){
                previous = "LOW";
            } else {
                previous = "HIGH";
            }


            //Data collection and storage loop
            //pull out into funciton?
            if (state) {
                sampleTime.setStartTime();

                String input = "";
                float[] returnFloat = new float[2];

                returnFloat = controller.getFloatArray();

                for (float i : returnFloat) {
                    input = input + Float.toString(i) + "\t";
                }

                input = input + Long.toString(timer.getDeltaTimeFromStart());

                //Sample collection control.  Will not allow for data to be requested on I2C more than a certain
                //number of times per second.
                if (sampleTime.getDeltaTimeFromStart() < delayTime) {
                    try {
                        Thread.sleep(delayTime);  //like Arduio delay (delay(10))
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


                LOGGER.log(Level.INFO, input);  //Store collected data in log file

                counter++;
            } else {
//                System.out.println("Waiting for Button Input");
            }
        }
    }

}



