package fr.the4pe18.Robby;

import java.lang.reflect.Array;
import java.util.Arrays;

public class LangUtils {
    public static <T> T[] removeFromArray(T[] array, int index) {
        if (array == null || index < 0 || index >= array.length) return array;
        T[] newArray = (T[]) Array.newInstance(array.getClass().getComponentType(), array.length - 1);
        for (int i = 0, k = 0; i < array.length; i++) {
            if (i == index) continue;
            newArray[k++] = array[i];
        }
        return (T[]) newArray;
    }
}
