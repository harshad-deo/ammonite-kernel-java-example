package com.simianquant.ammonite.kernel.javaexample;

public class Newton {

    // return the square root of c, computed using Newton's method
    // shamelessly copied from http://introcs.cs.princeton.edu/java/21function/Newton.java.html
    public static double sqrt(double c) {
        if (c < 0) return Double.NaN;
        double EPSILON = 1E-15;
        double t = c;
        while (Math.abs(t - c/t) > EPSILON*t)
            t = (c/t + t) / 2.0;
        return t;
    }

}
