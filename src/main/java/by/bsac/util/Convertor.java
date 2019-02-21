package by.bsac.util;

import java.math.BigInteger;

public class Convertor {

    public static String byteToHexForm(byte[] bytes) {

        //Convert bytes to number form
        BigInteger bi = new BigInteger(1, bytes);

        return bi.toString(16);
    }
}
