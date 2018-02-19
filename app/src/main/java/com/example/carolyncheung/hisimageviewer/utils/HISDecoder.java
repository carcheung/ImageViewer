package com.example.carolyncheung.hisimageviewer.utils;

/**
 * Created by carolyncheung on 2/11/18.
 */

public class HISDecoder {
    static {
        System.loadLibrary("HISDecoder");
    }

    public static native int HISOpen(String path);

    public static native int getWidth();

    public static native int getHeight();

    public static native int getNumberOfFrames();

    public static native int[] getBytes();

    public static native void HISClose();

    public static String FILEPATH_EXTRA = "filepath";

}
