package by.bsac.util;

import java.lang.reflect.Array;

/**
 *
 */
public class CommonUtils {

    public static <T> T[] concatenateTwoArrays(T[] arr1, T[] arr2) {

        //Create resulting array
        T[] result = (T[]) Array.newInstance(arr1.getClass(), arr1.length + arr2.length);

        //Concatenate 2 arrays
        //Copy first array to resulting array
        System.arraycopy(arr1, 0, result, 0, arr1.length);

        //Copy second array to resulting array
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);

        //Return statement
        return result;
    }

}
