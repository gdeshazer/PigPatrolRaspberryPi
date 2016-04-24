package com.PigPatrol;

/**
 * Created by grantdeshazer on 4/14/16.
 */

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;

import java.nio.ByteBuffer;
import java.io.IOException;

public class I2CControl {
    private I2CBus m_bus;
    private int m_devAddr;
    private I2CDevice m_arduino;

    public I2CControl(){
        System.out.println("Generating I2C Bus");
        try {
            m_bus = I2CFactory.getInstance(I2CBus.BUS_1);
            m_devAddr = 0x10;
            m_arduino = m_bus.getDevice(m_devAddr);


        } catch(IOException e){
            System.err.println("Something has gone wrong!");
            e.printStackTrace();
        }
    }

    public I2CControl(int devAddr){
        System.out.println("Generating I2C Bus");
        try {
            m_bus = I2CFactory.getInstance(I2CBus.BUS_1);
            m_devAddr = devAddr;
            m_arduino = m_bus.getDevice(m_devAddr);


        } catch(IOException e){
            System.err.println("Something has gone wrong!");
            e.printStackTrace();
        }
    }

    public float getFloat(){
        byte[] input = new byte[4];

        try{
            m_arduino.read(input, 0, 4);

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

    public float[] getFloatArray(){
        byte[] b = new byte[8];
        int numOfFloats = 2;

        try{
            //read number of floats to receive
//            m_arduino.read(b, 0, 1);
//            numOfFloats = b[0];
//
//            //read floats
//            b = new byte[numOfFloats*4];
            m_arduino.read(b,0,8);

            for(int i : b){
                System.out.println(Integer.toHexString(i) + ", ");
            }


        } catch (IOException e){
            System.err.println("Failed to read a float array.");
            e.printStackTrace();
        }

        float[] returnFloat = new float[numOfFloats];

        int offset = 0;
        for(int i  = 0; i < 2; i++){
            ByteBuffer buffer = ByteBuffer.wrap(b, i*4,4);
            returnFloat[i] = buffer.getFloat();

        }

        return returnFloat;
    }
}
