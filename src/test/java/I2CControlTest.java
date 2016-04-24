import java.nio.ByteBuffer;

/**
 * Created by grantdeshazer on 4/19/16.
 */


public class I2CControlTest {
    public float getFloat(float in){
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

    public float[] getFloatArray(){
        byte[] b = new byte[] {63, -128, 0, 0, 64, 0, 0, 0};
        float[] returnFloat = new float[2];

        for(int i  = 0; i < 2; i++){
            ByteBuffer buffer = ByteBuffer.wrap(b, i*4,4);
            returnFloat[i] = buffer.getFloat();

        }

        return returnFloat;
    }

    public static void main(String[] args){
        float f = 1;
        I2CControlTest test = new I2CControlTest();

        System.out.println("float value:  " + f);
        f = test.getFloat(f);
        System.out.println("float value:  " + f);

        f = 2;

        System.out.println("float value:  " + f);
        f = test.getFloat(f);
        System.out.println("float value:  " + f);

        float[] fArray = new float[2];

        fArray = test.getFloatArray();

        System.out.println("float 1: " + fArray[0]);
        System.out.println("float 2: " + fArray[1]);
    }
}
