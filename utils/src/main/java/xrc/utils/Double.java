package xrc.utils;

public class Double {

    private static final double EPSILON = 5.96e-08;

    /**
     * <a href="https://www.ibm.com/developerworks/java/library/j-jtp0114/#N10255"a>
     * Guidelines for comparing floating point numbers</a>
     */
    public static boolean equal(double d1, double d2) {
        if (d2 == 0) {
            d1 += 1;
            d2 += 1;
        }
        return (java.lang.Math.abs(d1 / d2 - 1) < EPSILON);
    }

    public static int compare(double d1, double d2) {
        return equal(d1, d2) ? 0 : java.lang.Double.compare(d1, d2);
    }

}
