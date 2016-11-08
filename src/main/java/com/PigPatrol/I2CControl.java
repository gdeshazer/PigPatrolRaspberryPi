package com.PigPatrol;

/**
 * Created by gdeshazer on 4/14/16.
 *
 * I2C Control class
 *
 *  Stores I2C devices address
 *  Collects data from specific device
 *
 *  ** Consider **
 *    May want to implement the actual short array collection
 *    in its own class and only have this class deal with i2c handling
 *
 */

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;

import java.nio.ByteBuffer;
import java.io.IOException;

public class I2CControl {
    private I2CBus m_bus;
    private int m_devAddr;
    private I2CDevice m_device;

    public I2CControl(){
        System.out.println("Generating I2C Bus");
        try {
            m_bus = I2CFactory.getInstance(I2CBus.BUS_1);
            m_devAddr = 0x10;   //default I2C address
            m_device = m_bus.getDevice(m_devAddr);


        } catch(IOException e){
            System.err.println("Something has gone wrong!  Could not get I2C set up.");
            e.printStackTrace();
        }
    }

    //Initilize device with specific I2C Address
    public I2CControl(int devAddr){
        System.out.println("Generating I2C Bus");
        try {
            m_bus = I2CFactory.getInstance(I2CBus.BUS_1);
            m_devAddr = devAddr;
            m_device = m_bus.getDevice(m_devAddr);


        } catch(IOException e){
            System.err.println("Something has gone wrong!  Could not get I2C set up.");
            e.printStackTrace();
        }
    }

    //reads a single float array
    //assumes that endianness is dealt with
    // on the connected I2C device
    public float getFloat(){
        byte[] input = new byte[4];

        try{
            m_device.read(input, 0, 4);

        } catch (IOException e){
            System.err.println("Failed to read a float");
            e.printStackTrace();
        }

//        for(int i : input){
//            System.out.println(Integer.toHexString(i) + ", ");
//        }
//
//        System.out.println("\n");


        ByteBuffer buffer = ByteBuffer.wrap(input);

        return buffer.getFloat();
    }

    //Returns a float array of values retreived from
    //I2C device.  Currently hardcoded for 4 floats or
    //8 Bytes of data.
    public float[] getFloatArray(){
        int numOfFloats = 4;
        byte[] b = new byte[4*numOfFloats];

        try{
            m_device.read(b,0,4*numOfFloats);

//            for(int i : b){
//                System.out.println(Integer.toHexString(i) + ", ");
//            }


        } catch (IOException e){
            System.err.println("Failed to read a float array.");
            e.printStackTrace();
        }

        float[] returnFloat = new float[numOfFloats];

        //coverts chunks of 4 bytes into a single float
        for(int i  = 0; i < numOfFloats; i++){
            ByteBuffer buffer = ByteBuffer.wrap(b, i*4,4);
            returnFloat[i] = buffer.getFloat();

        }

        return returnFloat;
    }

    public short[] getShortArray(){
        short numOfshorts= 4;
        byte[] b = new byte[2*numOfshorts];

        try{
            m_device.read(b,0,2*numOfshorts);
        } catch (IOException e) {
            System.err.println("Failed to read int array");
            e.printStackTrace();
        }

//        for(short i : b){
//            System.out.println(Short.toString(i));
//        }

        short[] returnShort = new short[numOfshorts];

        for (int i = 0; i < numOfshorts; i++){
            ByteBuffer buffer = ByteBuffer.wrap(b,i*2,2);
            returnShort[i] = buffer.getShort();
        }

        return returnShort;
    }

}
