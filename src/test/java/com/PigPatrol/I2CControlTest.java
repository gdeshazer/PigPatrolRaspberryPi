package com.PigPatrol;

import java.nio.ByteBuffer;

import junit.framework.*;
import org.junit.Test;

/**
 * Created by Grant DeShazer on 4/19/16.
 *
 * Junit tests for implemented code.  Since
 * it is difficult to test the actual I2C connection
 * the testing performed here is looking at the conversion
 * between byte arrays and and floats.
 *
 * Since the short conversion works on the same principle,
 * the short array collection was not tested.
 *
 * Maven packaging will automatically run these tests in the package
 * target.  Console output will display how tests performed.  More
 * information on test results can be found in target/surefire-reports.
 *      All tests are run by maven surefire plugin as part of the build
 *      process.  Tests that fail to run will prevent the build process
 *      and must, consequently, be dealt with immediately.  For
 *      information regarding surefire plugin, consult
 *      https://maven.apache.org/plugins/maven-shade-plugin/
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

    //Hard coded byte array to represent a float value of 1.  Note
    //that these values are specific to java.
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

    //Code responsible for actually running the above tests.
    @Test
    public static void testRunner(){
        I2CControlTest test = new I2CControlTest();
        float f = 1;

        assertEquals(f, test.testGetFloat(f));

        float[] retF = test.testGetFloatArray();
        for(int i = 0; i < 2; i++){
            assertEquals(f, retF[i]);
        }
    }
}
