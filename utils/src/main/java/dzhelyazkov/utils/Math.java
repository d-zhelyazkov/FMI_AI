package dzhelyazkov.utils;

public class Math {
    public static int dir(double number) {
        return (number == 0) ? 0 : (int) (number / java.lang.Math.abs(number));
    }
}
