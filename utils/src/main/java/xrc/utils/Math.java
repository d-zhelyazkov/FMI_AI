package xrc.utils;

public class Math {
    public static int dir(double number) {
        return (number == 0) ? 0 : (int) (number / java.lang.Math.abs(number));
    }

    public static long factorial(int number) {
        return (number < 1) ? 1 : (number * factorial(number - 1));
    }
}
