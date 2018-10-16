//
// Created by asus1 on 2018/10/16.
//

#include "com_example_asus1_ffmpeglearn_NDKTest.h"
#include <jni.h>

JNIEXPORT jstring JNICALL Java_com_example_asus1_ffmpeglearn_NDKTest_getString
        (JNIEnv * env, jclass jclass1){

    char * st = "hello";

    return (*env)->NewStringUTF(env,st);

}

