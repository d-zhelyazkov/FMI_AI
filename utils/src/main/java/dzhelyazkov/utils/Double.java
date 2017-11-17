package dzhelyazkov.utils;

public class Double {

    private static final double EPSILON = 5.96e-08;

    /**
     * https://www.ibm.com/developerworks/java/library/j-jtp0114/#N10255
     */
    public static int compare(double d1, double d2) {
        return (java.lang.Math.abs(d1 / d2 - 1) < EPSILON) ? 0 : java.lang.Double.compare(d1, d2);
    }
}
