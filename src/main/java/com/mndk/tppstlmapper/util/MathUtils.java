package com.mndk.tppstlmapper.util;

public class MathUtils {
    public static double bound(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
    public static int bound(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}
