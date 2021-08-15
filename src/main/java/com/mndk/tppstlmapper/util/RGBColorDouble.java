package com.mndk.tppstlmapper.util;

import lombok.ToString;

@ToString
public class RGBColorDouble {

    public final double red, green, blue;

    public RGBColorDouble(double red, double green, double blue) {
        this.red = MathUtils.bound(red, 0, 255);
        this.green = MathUtils.bound(green, 0, 255);
        this.blue = MathUtils.bound(blue, 0, 255);
    }

    public RGBColorDouble(int rgb) {
        this.red = (rgb & 0xff0000) >> 16;
        this.green = (rgb & 0x00ff00) >> 8;
        this.blue = rgb & 0x0000ff;
    }

    public int toRGBColor() {
        return (redInt() << 16) + (greenInt() << 8) + blueInt();
    }

    public int redInt() {
        return (int) Math.round(red) & 0xff;
    }
    public int greenInt() {
        return (int) Math.round(green) & 0xff;
    }
    public int blueInt() {
        return (int) Math.round(blue) & 0xff;
    }

    public double getSRGBDiffSq(RGBColorDouble other) {
        double r = (red - other.red) * 0.30, g = (green - other.green) * 0.59, b = (blue - other.blue) * 0.11;
        return r * r + g * g + b * b;
    }
}
