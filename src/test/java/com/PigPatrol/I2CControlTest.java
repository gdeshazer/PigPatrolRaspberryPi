package com.PigPatrol;

import java.nio.ByteBuffer;

import junit.framework.*;
import org.junit.Test;

/**
 * Created by grantdeshazer on 4/19/16.
 */


public class I2CControlTest extends TestCase{

    @Test
    public float testGetFloat(float in){
        byte[] b = new byte[4];

        b = ByteBuffer.allocate(4).putFloat(in).array();
        System.out.print("Byte array: ");
        for(byte bi : b){
            System.out.print(bi + ", ");
        }
        System.out.println("\n");

        ByteBuffer buffer = ByteBuffer.wrap(b);

        return buffer.getFloat();
    }

    @Test
    public float[] testGetFloatArray(){
        //hard coded byte array.  Testing conversion of bytes to float via bytebuffer
        byte[] b = new byte[] {63, -128, 0, 0, 63, -128, 0, 0};
        float[] returnFloat = new float[2];

        for(int i  = 0; i < 2; i++){
            ByteBuffer buffer = ByteBuffer.wrap(b, i*4,4);
            returnFloat[i] = buffer.getFloat();

        }

        return returnFloat;
    }

    @Test
    public static void testRunner(){
//        float f = 1;
//        I2CControlTest test = new I2CControlTest();
//
//        System.out.println("float value:  " + f);
//        f = test.testGetFloat(f);
//        System.out.println("float value:  " + f);
//
//
//        System.out.println("float value:  " + f);
//        f = test.testGetFloat(f);
//        System.out.println("float value:  " + f);
//
//        float[] fArray = new float[2];
//
//        fArray = test.getFloatArray();
//
//        System.out.println("float 1: " + fArray[0]);
//        System.out.println("float 2: " + fArray[1]);

        I2CControlTest test = new I2CControlTest();
        float f = 1;

        assertEquals(f, test.testGetFloat(f));

        float[] retF = test.testGetFloatArray();
        for(int i = 0; i < 2; i++){
            assertEquals(f, retF[i]);
        }
    }
}
