package by.bsac.util;

import by.bsac.exceptions.NotSameTypesException;

import java.lang.reflect.Array;

/**
 *
 */
public class CommonUtils {

    /**
     * Concatenate two objects arrays with same types.
     * @param arr1 - First source array.
     * @param arr2 - Second source array.
     * @param <T> - Arrays content type
     * @return - new array of class <T> which contains two arrays.
     * @throws NotSameTypesException - causes if content types of sources array are not the same.
     */
    public static <T> T[] concatenateTwoObjectsArrays(T[] arr1, T[] arr2) throws NotSameTypesException {

        //Check on sameness types
        if (arr1.getClass().isAssignableFrom(arr2.getClass())) throw new NotSameTypesException();
        if (arr2.getClass().isAssignableFrom(arr1.getClass())) throw new NotSameTypesException();

        //Create resulting array
        @SuppressWarnings("unchecked")
        T[] result = (T[]) Array.newInstance(arr1.getClass().getComponentType(), arr1.length + arr2.length);

        //Concatenate 2 arrays
        //Copy first array to resulting array
        System.arraycopy(arr1, 0, result, 0, arr1.length);

        //Copy second array to resulting array
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);

        //Return statement
        return result;
    }

    /**
     * Concatenate two primitive arrays with same type.
     * @param arr1 - First source array.
     * @param arr2 - Second source array.
     * @param <T> - Arrays content type
     * @return - new array of class <T> which contains two arrays.
     * @throws NotSameTypesException causes if content types of sources array are not the same.
     */
    public static <T> T concatenateTwoArrays(T arr1, T arr2) throws NotSameTypesException {

        //Check that the arguments are array
        if (!arr1.getClass().isArray() || !arr2.getClass().isArray()) throw new IllegalArgumentException("One of the arguments is not array");

        //Check on sameness primitive types
        Class<?> resCompType;
        Class<?> aCompType = arr1.getClass().getComponentType();
        Class<?> bCompType = arr2.getClass().getComponentType();

        if (aCompType.isAssignableFrom(bCompType)) {
            resCompType = aCompType;
        } else if (bCompType.isAssignableFrom(aCompType)) {
            resCompType = bCompType;
        } else {
            throw new NotSameTypesException();
        }

        //Get component type class
        Class<?> resulting_component_type = arr1.getClass().getComponentType();

        //Create resulting array
        @SuppressWarnings("unchecked")
        T result = (T) Array.newInstance(resulting_component_type, Array.getLength(arr1) + Array.getLength(arr2));

        //Concatenate 2 arrays
        //Copy first array to resulting array
        //noinspection SuspiciousSystemArraycopy
        System.arraycopy(arr1, 0, result, 0, Array.getLength(arr1));

        //Copy second array to resulting array
        //noinspection SuspiciousSystemArraycopy
        System.arraycopy(arr2, 0, result, Array.getLength(arr1), Array.getLength(arr2));

        return result;
    }

}
