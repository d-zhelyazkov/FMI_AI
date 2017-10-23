package util;

/**
 * Created by dzhel on 8.10.2017 г..
 */
public class ArrayUtils {

    public static <T> void swap(T[] array, int i, int j) {
        T buf = array[i];
        array[i] = array[j];
        array[j] = buf;
    }

    public static void swap(char[] array, int i, int j) {
        char buf = array[i];
        array[i] = array[j];
        array[j] = buf;
    }
}
