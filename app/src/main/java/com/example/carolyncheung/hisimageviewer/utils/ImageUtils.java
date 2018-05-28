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

    public static int[] BrightnessAdjustment(int[] bytes, int brightness) {
        for (int i = 0; i < bytes.length; i++) {
            int newColor = bytes[i] + brightness;
            bytes[i] = Color.argb(255, newColor, newColor, newColor);
        }
    }
}
