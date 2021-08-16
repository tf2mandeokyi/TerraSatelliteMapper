package com.mndk.tppstlmapper.util;

public class MathUtils {

    public static double bound(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    public static int bound(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    public static double cubicInterpolation(double y0, double y1, double y2, double y3, double t) {
        if(Double.isNaN(y0)) y0 = y1;
        if(Double.isNaN(y3)) y3 = y2; // TODO figure out whether this is appropriate or not
        double a0 = y3 - y2 - y0 + y1, a1 = y0 - y1 - a0, a2 = y2 - y0;
        return a0 * Math.pow(t, 3) + a1 * Math.pow(t, 2) + a2 * t + y1;
    }
}
