package com.example.carolyncheung.hisimageviewer.utils;

import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carolyn Cheung on 4/12/2018.
 */

public class ImageUtils {
    Integer ROI_LUT_MAX = 65535;
    Integer ROI_LUT_MIN = 0;

    // LUT table calculate via y = mx + b
    public static int[] LUTCalculation(int[] bytes, double bright, double dark) {
        // y = mx + b
        double slope = (double) 255 / (bright - dark);
        double b;
        if (bright > dark) {
            b = (slope * dark) * (double) (-1);
        } else {
            b = slope * bright;
        }

        double x = (int) ((2 - b) / slope);

        for (int i = 0; i < bytes.length; i++) {
            double y = (int) (slope * bytes[i]) + b;
            if (y != 0) {
                y = y - 2;
            }

            if (bytes[i] > bright) {
                y = 255;
            } else if(bytes[i] == bright) {
                bytes[i] = 254;
            } else if (bytes[i] <= dark) {
                y = 0;
            } else if (bytes[i] < x) {
                y = 2;
            }

            bytes[i] = Color.argb(255, (int) y, (int) y, (int) y);
        }

        return bytes;
    }

    // adjust brightness of the image, images are black and white so only need to modify
    // one channel instead of all
    public static int[] AdjustBrightness(int[] bytes, int brightness) {
        for (int i = 0; i < bytes.length; i++) {
            // grey scale, only need 1 color
            int r = Color.red(bytes[i]);
            r += brightness;
            if (r > 255) {
                r = 255;
            } else if (r < 0) {
                r = 0;
            }

            bytes[i] = Color.argb(255, r, r, r);
        }

        return bytes;
    }

    // adjust contrast of the image
    // Calculation: contrast = (( value + 100 ) / 100) ^ 2
    public static int[] AdjustContrast(int[] bytes, double val) {
        // calculate contrast val
        double contrast = Math.pow((100 + val) / 100, 2);
        for (int i = 0; i < bytes.length; i++) {
            int r = Color.red(bytes[i]);
            // contrast calculation
            r = (int) (((((r / 255.0) - 0.5) * contrast) + 0.5) * 255.0 );
            if (r > 255) {
                r = 255;
            } else if (r < 0) {
                r = 0;
            }

            bytes[i] = Color.argb(255, r, r, r);
        }

        return bytes;
    }
}
