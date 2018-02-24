//
// Created by Carolyn Cheung on 2/11/18.
//

//
// Created by Carolyn Cheung on 2/11/18.
//

#include <jni.h>
#include <stdio.h>
#include <android/log.h>
#include <stdlib.h>
#include <iostream>
#include <cstring>
#include <string>

#include <errno.h>

using namespace std;

typedef struct {
    uint16_t FileType;
    uint16_t HeaderSize;
    uint16_t HeaderVersion;
    uint32_t FileSize;
    uint16_t ULX;
    uint16_t ULY;
    uint16_t BRX;
    uint16_t BRY;
    uint16_t NumberOfFrames;
    uint16_t Correction;
    double IntegrationTime;     // not a wholesome number
    uint16_t TypeOfNumbers;
    uint8_t x[68];
} HISHEADER;

FILE * fp;
HISHEADER * h = NULL;
unsigned short width = 0;
unsigned short height = 0;
unsigned short frames = 0;
unsigned short typeOfNumber = 0;
int bytesPerPixel = 0;

uint16_t * data;

extern "C"
JNIEXPORT jint
JNICALL
Java_com_example_carolyncheung_hisimageviewer_utils_HISDecoder_getWidth(JNIEnv *env) {
    return width;
}

extern "C"
JNIEXPORT jint
JNICALL
Java_com_example_carolyncheung_hisimageviewer_utils_HISDecoder_getHeight(JNIEnv *env) {
    return height;
}

extern "C"
JNIEXPORT jint
JNICALL
Java_com_example_carolyncheung_hisimageviewer_utils_HISDecoder_getNumberOfFrames(JNIEnv *env) {
    return frames;
}

// translate bytes array to jintArray
extern "C"
JNIEXPORT jintArray
JNICALL
Java_com_example_carolyncheung_hisimageviewer_utils_HISDecoder_getBytes(JNIEnv *env) {
//uint16_t *
    int length = width * height * frames;
    jintArray bytesArr = (env)->NewIntArray(length);
    if (!bytesArr) {
        __android_log_print(ANDROID_LOG_INFO, "getBytes", "Array not created!");
    } else {
        // put data into bytesArr
        jint * bytes = env->GetIntArrayElements(bytesArr, NULL);
        if (bytes != NULL) {
            memcpy(bytes, data, length * sizeof(uint16_t));
            __android_log_print(ANDROID_LOG_INFO, "bytes[0]", "%d", bytes[0]);
            env->ReleaseIntArrayElements(bytesArr, bytes, 0);
        }
    }

    int * a = env->GetIntArrayElements(bytesArr, false);
    for (int i = 0; i < 10; i++) {
        __android_log_print(ANDROID_LOG_INFO, "getBytes", "%d: %d", i, a[i]);
    }

    return bytesArr;
}
extern "C"
JNIEXPORT jint
JNICALL
Java_com_example_carolyncheung_hisimageviewer_utils_HISDecoder_HISOpen(
        JNIEnv *env, jobject, jstring path) {
    const char *strPath = NULL;
    errno = 0;
    if (data) {
        free(data);
    }
    if (fp) {
        fclose(fp);
    }

    strPath = env->GetStringUTFChars(path,NULL);
    fp = fopen(strPath, "rb");
    if (fp == NULL) {
        __android_log_print(ANDROID_LOG_INFO, "HISOpen", "Could not open image %s, errono: %d", strPath, errno);
        return -1;
    }
    // fill in the header
    h = (HISHEADER *)malloc(sizeof(HISHEADER));
    fread(h, sizeof(HISHEADER), 1, fp);

    uint16_t pixel;

    // get bytes per pixel
    if (h->TypeOfNumbers == 2) {
        // float32
        bytesPerPixel = 8;
    } else if (h->TypeOfNumbers == 4) {
        // uint16
        bytesPerPixel = 2;
    } else if (h->TypeOfNumbers == 32) {
        // uint32
        bytesPerPixel = 4;
    } else if (h->TypeOfNumbers == 12) {
        // int16
        bytesPerPixel = 2;
    } else if (h->TypeOfNumbers == 40) {
        // int32
        bytesPerPixel = 4;
    } else {
        __android_log_print(ANDROID_LOG_INFO, "HISOpen", "Unknown TypeOfNumbers!");
        return -1;
    }

    width = h->BRX;
    height = h->BRY;
    frames = h->NumberOfFrames;

    data = (uint16_t*) malloc(frames * width * height * bytesPerPixel);
    int index = 0;
    // skip the header (100 bytes)
    fseek(fp, (100) + (0), SEEK_SET);

    while (!feof(fp)) {
        fread(&pixel, bytesPerPixel, 1, fp);

        data[index] = pixel;
// TODO:  remove debug completely
//        if (index < 20) {
//            cout << index << ": " << pixel << endl;
//            __android_log_print(ANDROID_LOG_DEBUG, "HISOpen", "%d: %d", index, pixel);
//        }
        index++;
    }
    return 0;
}

extern "C"
JNIEXPORT void
JNICALL
Java_com_example_carolyncheung_hisimageviewer_utils_HISDecoder_HISClose(JNIEnv *env, jobject) {
    if (data) {
        free(data);
    }
    if (fp) {
        fclose(fp);
    }
}
