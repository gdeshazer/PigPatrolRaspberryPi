package com.PigPatrol;

/**
 * Created by grantdeshazer on 4/14/16.
 *
 * I2C controller using pi4j library.
 */

import java.io.*;
import java.util.logging.*;


public class Main {
    //using a logger for data storage...simple set up and relatively fast
    //easier than file IO
    //set up for logger
    private static final Logger  LOGGER = Logger.getLogger( Main.class.getName() );
    private static FileHandler fh = null;
    private static ConsoleHandler ch = new ConsoleHandler();
    private static DataLogFormater form = new DataLogFormater();

    //Logger congfiguration
    private static void init(){

        int fileSize = (int) (5 * Math.pow(10,6));
        int numberOfFiles = 10;

        try{
            //path here is specific to beaglebone
            //boolean param controls whether file is appended to
            //or is overwritten
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
            String input = "";

            float returnFloat;
            sampleTime.setStartTime();
            returnFloat = controller.getFloat();

//            for(float i : returnFloat){
//                input = input + Float.toString(i) + "\t";
//            }
            input = Float.toString(returnFloat) + "\t";
            input = input +  "\t" + Long.toString(timer.getDeltaTime());

            if(sampleTime.getDeltaTime() < delayTime) {
                try {
                    Thread.sleep(delayTime);  //like arduio delay (delay(10))
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            LOGGER.log(Level.INFO, input);  //Store collected data in log file

            counter++;
        }
    }

}



