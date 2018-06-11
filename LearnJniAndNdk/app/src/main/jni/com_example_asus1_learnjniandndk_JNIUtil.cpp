#include "com_example_asus1_learnjniandndk_JNIUtil.h"
JNIEXPORT jstring JNICALL Java_com_example_asus1_learnjniandndk_JNIUtil_getWorld
        (JNIEnv *env, jclass) {
    // new 一个字符串，返回Hello World
    return env -> NewStringUTF("Hello World ZZZZZ");
}
