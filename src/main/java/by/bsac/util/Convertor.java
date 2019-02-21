package by.bsac.util;

import java.math.BigInteger;

public class Convertor {

    public static String byteToHexForm(byte[] bytes) {

        //Convert to signum representation
        BigInteger bi = new BigInteger(1, bytes);

        //Convert to hex value
        StringBuilder hex_text = new StringBuilder(bi.toString(16));

        // Add preceding 0s to make it 32 bit
        while (hex_text.length() < 32) {
            hex_text.insert(0, "0");
        }

        return hex_text.toString();
    }
}
