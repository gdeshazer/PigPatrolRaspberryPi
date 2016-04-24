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

import java.io.*;
import java.util.logging.*;


public class Main {

    //set up for logger
    private static final Logger  LOGGER = Logger.getLogger( Main.class.getName() );
    private static FileHandler fh = null;
    private static ConsoleHandler ch = new ConsoleHandler();
    private static DataLogFormater form = new DataLogFormater();

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


    public static void main(String[] args) throws IOException {
        init();
        Timer timer = new Timer();
        Timer sampleTime = new Timer();

        int counter = 0;
        int delayTime = 50;

        I2CControl controller = new I2CControl();

        timer.setStartTime();
        while (counter != 100) {
            sampleTime.setStartTime();

            String input = "";
            float[] returnFloat = new float[2];

            returnFloat = controller.getFloatArray();

            for(float i : returnFloat){
                input = input + Float.toString(i) + "\t";
            }

            input = input + Long.toString(timer.getDeltaTime());

            //Sample collection control.  Will not allow for data to be requested on I2C more than a certain
            //number of times per second.
            if(sampleTime.getDeltaTime() < delayTime) {
                try {
                    Thread.sleep(delayTime);  //like Arduio delay (delay(10))
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            LOGGER.log(Level.INFO, input);  //Store collected data in log file

            counter++;
        }
    }

}


