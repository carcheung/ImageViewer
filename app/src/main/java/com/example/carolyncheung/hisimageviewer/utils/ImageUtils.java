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

 /*   0           0
    1           257
    2           514
    3           771
    4           1028
    5           1285
    ..
    254         65278
    255         65535

    multiply by 257
    0 to < 257 = 0
    257 to < 514 = 1
    514 to < 771 = 2
    ..
    65278 to < 65535 = 254
    65535 = 255




    about ~13 colors for range between 4393 - 4382 = 11
        -makes sense, black and white is 2 colors
        - anything less than up to 4393 is black
        - anything greater than 4393 is white
        - leaves us with 11 shades of grey
        - how determine 11 shades of grey?


        1 / 11 = 0.09 -> 0.09 * 255 = 22.95, round to nearest even number, 22
        2 / 11 = 0.18 -> 0.18 * 255 = 45.9, round to nearest even, 46

        1 / 11 = 23, BETWEEN 22 AND 24 omfg its just 2 numbers alternating forever

        etc
        for the last number, subtract 255 - 1, voila!
        255, 254, 230, 206, 184, 160, 138, 114, 92, 68, 46, 22, 0
        22 + 24 + 22 + 24 + 22 + 24 ...


    about 88 colors for ranges between 9805 - 9719 = 86
    - black is 1, white is 1

    1/86 * 255 = 2.965.. round down = 2
    2/86 * 255 = 5.93.. = 4
    3/86 * 255 = 8.89.. = 8
    4/86 * 255 = 11.86.. = 10

    2, 4, 8, 10, 14, 16, 20, 22, 26, 28, 32 ... 250, 254, 255


    round down and add number to get closest there ? for # 2

*/
    public static int[] LUTCalculation(int[] bytes, double bright, double dark) {
        // find range
        List<Integer> GREY_VALUES = new ArrayList<Integer>();
        List<Double> X_INTEGERS = new ArrayList<Double>();
        BST bst = new BST();

        // y = mx + b
        double slope = (double) 255 / (bright - dark);
        double b;
        if (bright > dark) {
            b = (slope * dark) * (double) (-1);
        } else {
            b = slope * bright;
        }

        Log.d("SLOPE", Double.toString(slope));
        Log.d("B", Double.toString(b));

        double x = (int) ((0 - b) / slope);
        X_INTEGERS.add(x);
        GREY_VALUES.add(0);
        bst.insert((int) x, 0);

        for (int i = 1; i < 256; i++) {
            x = (int) ((i - b) / slope);
            if (!X_INTEGERS.contains(x) && (i % 2 == 0)) {
                X_INTEGERS.add(x);
                GREY_VALUES.add(i - 2);
                bst.insert((int)x, i - 2);
            }
        }

        if (!GREY_VALUES.contains(254)) {
            if (bright > dark) {
                X_INTEGERS.add(bright);
                bst.insert((int) bright, 254);
            } else {
                X_INTEGERS.add(dark);
                bst.insert((int) dark, 254);
            }
            GREY_VALUES.add(254);
        }

        // adjust the colors
        for (int i = 0; i < bytes.length; i++) {
            int p = bst.searchNearest(bytes[i]);

            bytes[i] = Color.argb(255, p, p, p);
        }
        return bytes;
    }

    // find min and max of ROI
    public void FindROILUTMaxMin(int [] bytes) {
        ROI_LUT_MAX = 0;
        ROI_LUT_MIN = 65535;

        for (int i = 0; i < bytes.length; i++) {
            if (ROI_LUT_MAX < bytes[i]) {
                ROI_LUT_MAX = bytes[i];
            }
            if (ROI_LUT_MIN > bytes[i]) {
                ROI_LUT_MIN = bytes[i];
            }
        }
    }

    public void setLUTMax(int max) {
        ROI_LUT_MAX = max;
    }

    public void setLUTMin(int min) {
        ROI_LUT_MIN = min;
    }

    // round to the nearest even number
    static public int RoundToEven(double d) {
        if (d % 2 != 0) {
            d = d - 1;
        }
        return (int) d;
        // return (int) Math.round(d / 2) * 2;
    }

}
