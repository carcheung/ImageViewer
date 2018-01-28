package com.example.carolyncheung.hisimageviewer.data;

/**
 * Created by carolyncheung on 1/21/18.
 */

public class HISHeader {
    short FileType;
    short HeaderSize;
    short HeaderVersion;
    long FileSize;
    short ImageHeaderSize;
    short ULX, ULY, BRX, BRY;
    short NumberOfFrames;
    short Corection;
    double IntegrationTime;
    short TypeOfNumbers;
    char x[] = new char[68];
}
