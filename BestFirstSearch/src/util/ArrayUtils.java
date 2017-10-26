package util;

/**
 * Created by dzhel on 26.10.2017.
 */
public class ArrayUtils {
    public static int[][] clone2DArray(int[][] array) {
        int[][] clone = new int[array.length][array[0].length];
        for (int i = 0; i < array.length; i++) {
            System.arraycopy(array[i], 0, clone[i], 0, array[i].length);
        }

        return clone;
    }

    public static <T> void swapIn2DArray(T[][] array, Point el1, Point el2) {
        T buf = array[(int) el1.getY()][(int) el1.getX()];
        array[(int) el1.getY()][(int) el1.getX()] = array[(int) el2.getY()][(int) el2.getX()];
        array[(int) el2.getY()][(int) el2.getX()] = buf;
    }

    public static void swapIn2DArray(int[][] array, Point el1, Point el2) {
        int buf = array[(int) el1.getY()][(int) el1.getX()];
        array[(int) el1.getY()][(int) el1.getX()] = array[(int) el2.getY()][(int) el2.getX()];
        array[(int) el2.getY()][(int) el2.getX()] = buf;
    }
}
