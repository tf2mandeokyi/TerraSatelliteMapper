package com.mndk.tppstlmapper.util;

public class CIELabColor {



    public final double l, a, b;



    public CIELabColor(double l, double a, double b) {
        this.l = MathUtils.bound(l, 0, 1);
        this.a = MathUtils.bound(a, 0, 1);
        this.b = MathUtils.bound(b, 0, 1);
    }



    /**
     * Reference to <a href="https://gist.github.com/manojpandey/f5ece715132c572c80421febebaf66ae">here</a>
     *
     * @param color The RGB color
     */
    public CIELabColor(RGBColorDouble color) {
        double red100 = color.red * 100 / 255.,
                green100 = color.green * 100 / 255.,
                blue100 = color.blue * 100 / 255.;
        double x = red100 * 0.4124 + green100 * 0.3576 + blue100 * 0.1805,
                y = red100 * 0.2126 + green100 * 0.7152 + blue100 * 0.0722,
                z = red100 * 0.0193 + green100 * 0.1192 + blue100 * 0.9505;

        x /= 95.047;
        y /= 100;
        z /= 108.883;

        x = x > 0.008856 ? Math.cbrt(x) : 7.787 * x + (16 / 116.);
        y = y > 0.008856 ? Math.cbrt(y) : 7.787 * y + (16 / 116.);
        z = z > 0.008856 ? Math.cbrt(z) : 7.787 * z + (16 / 116.);

        this.l = (116 * y) - 16;
        this.a = 500 * (x - y);
        this.b = 200 * (y - z);
    }



    public double getCIE76DiffSq(CIELabColor other) {
        double dl = l - other.l, da = a - other.a, db = b - other.b;
        return dl * dl + da * da + db * db;
    }



    public double getCIE94DiffSq(CIELabColor other) {
        double dL = l - other.l, da = a - other.a, db = b - other.b;
        double C1 = Math.sqrt(a * a + b * b), C2 = Math.sqrt(other.a * other.a + other.b * other.b);
        double dC = C1 - C2;
        double dH = Math.sqrt(da * da + db * db - dC * dC);
        double SL = 1, SC = 1 + 0.045 * C1, SH = 1 + 0.015 * C1;
        double dL1 = dL / SL, dC1 = dC / SC, dH1 = dH / SH;
        return dL1 * dL1 + dC1 * dC1 + dH1 * dH1;
    }



    public double getCIE2000DiffSq(CIELabColor other) {
        double l1 = l, l2 = other.l, a1 = a, a2 = other.a, b1 = b, b2 = other.b;

        double deltaLPrime = l1 - l2;
        double c1 = Math.sqrt(a1 * a1 + b1 * b1), c2 = Math.sqrt(a2 * a2 + b2 * b2);
        double dashL = (l1 + l2) / 2, dashC = (c1 + c2) / 2, dashC7 = Math.pow(dashC, 7);
        double v1 = Math.sqrt(dashC7 / (dashC7 + Math.pow(25, 7)));
        double v = Math.sqrt(1 - v1);
        double a1Prime = a1 + (a1 / 2) * v;
        double a2Prime = a2 + (a2 / 2) * v;
        double c1Prime = Math.sqrt(a1Prime * a1Prime + b1 * b1);
        double c2Prime = Math.sqrt(a2Prime * a2Prime + b2 * b2);
        double deltaCPrime = c2Prime - c1Prime;
        double dashCPrime = (c1Prime + c2Prime) / 2;
        double h1Prime = Math.toDegrees(Math.atan2(b1, a1Prime)) % 360;
        double h2Prime = Math.toDegrees(Math.atan2(b2, a2Prime)) % 360;
        double absDeltaHPrime = Math.abs(h1Prime - h2Prime), deltaHPrime, dashBigHPrime;
        if (absDeltaHPrime <= 180) {
            deltaHPrime = h2Prime - h1Prime;
            dashBigHPrime = (h1Prime + h2Prime) / 2;
        } else {
            if (h2Prime <= h1Prime) deltaHPrime = h2Prime - h1Prime + 360;
            else deltaHPrime = h2Prime - h1Prime - 360;

            if (h1Prime + h2Prime < 360) dashBigHPrime = (h1Prime + h2Prime + 360) / 2;
            else dashBigHPrime = (h1Prime + h2Prime - 360) / 2;
        }
        double deltaBigHPrime = 2 * Math.sqrt(c1Prime * c2Prime) * Math.sin(Math.toRadians(deltaHPrime / 2));
        double bigT = 1
                - 0.17 * Math.cos(Math.toRadians(dashBigHPrime - 30))
                + 0.24 * Math.cos(Math.toRadians(2 * dashBigHPrime))
                + 0.32 * Math.cos(Math.toRadians(3 * dashBigHPrime + 6))
                - 0.20 * Math.cos(Math.toRadians(4 * dashBigHPrime - 63));
        double SL = 1 + (0.015 * (dashL - 50) * (dashL - 50)) / Math.sqrt(20 + (dashL - 50) * (dashL - 50));
        double SC = 1 + 0.045 * dashCPrime;
        double SH = 1 + 0.015 * dashCPrime * bigT;
        double v2 = Math.floor((dashBigHPrime - 275) / 25);
        double RT = -2 * v1 * Math.sin(Math.toRadians(60 * Math.exp(-v2 * v2)));

        double dL1 = deltaLPrime / SL, dC1 = deltaCPrime / SC, dH1 = deltaBigHPrime / SH;
        return dL1 * dL1 + dC1 * dC1 + dH1 * dH1 + RT * deltaCPrime * deltaBigHPrime / (SC * SH);
    }


}
