#include <jni.h>
#include <string>
#include <android/log.h>


#define LOG_TAG "jnidemo"
// 定义debug信息
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
// 定义error信息
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)
// 定义info信息
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)


extern "C" JNIEXPORT jstring

JNICALL
Java_com_example_asus1_ndklearn_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}


extern "C"
JNIEXPORT void JNICALL
Java_com_example_asus1_ndklearn_MainActivity_accessFiled(JNIEnv *env, jobject instance) {

    // TODO
    //通过对象拿到Class
    jclass  clz = env->GetObjectClass(instance);
    //拿到对应属性的ID
    jfieldID fid = env->GetFieldID(clz,"str","Ljava/lang/String;");
    //通过属性ID拿到属性的值
    jstring  jstr = (jstring)(env->GetObjectField(instance,fid));

    //通过Java字符串拿到c字符串，第二个参数是一个出参，用来告诉我们GetStringUTFChars内部是否复制了一份字符串
    //如果没有复制，那么出参为isCopy，这时候就不能修改字符串的值了，因为Java中常量池中的字符串是不允许修改的（但是jstr可以指向另外一个字符串）
    const char * cstr = env->GetStringUTFChars(jstr,NULL);
    //在c层修改这个属性的值
    char  res[20] = "I LOVE YOU,";
    strcat(res,cstr);

    //重新生成Jasva的字符串，并且设置给对应的属性
    jstring  jstr_new = env->NewStringUTF(res);
    env->SetObjectField(instance,fid,jstr_new);

    //最后释放资源，通知垃圾回收器来回收
    env->ReleaseStringUTFChars(jstr,cstr);


}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_asus1_ndklearn_MainActivity_accessStaticFiled(JNIEnv *env, jobject instance) {

    // TODO
    jclass clz = env->GetObjectClass(instance);
    jfieldID  fid = env->GetStaticFieldID(clz,"NUM","I");
    jint  jInt = env->GetStaticIntField(clz,fid);
    jInt++;
    env->SetStaticIntField(clz,fid,jInt);

}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_asus1_ndklearn_MainActivity_accessMethd(JNIEnv *env, jobject instance) {

    // TODO
    jclass  clz = env->GetObjectClass(instance);
    jmethodID mid = env->GetMethodID(clz,"genRandomInt","(I)I");

    //调用该方法，最后一个是可变参数，就是调用该方法所传入的参数
    jint  jInt = env->CallIntMethod(instance,mid,100);
    //printf("output from C: %d",jInt);
   LOGD("output from C : %d",jInt);

}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_asus1_ndklearn_MainActivity_accessStaticMathod(JNIEnv *env, jobject instance) {

    // TODO
    jclass  clz = env->GetObjectClass(instance);

    jmethodID mid = env->GetStaticMethodID(clz,"getUUID","()Ljava/lang/String;");

    //调用java的静态方法，拿到返回值
    jstring  jstr = (jstring)(env->CallStaticObjectMethod(clz,mid,NULL));

    //把拿到的Java字符串转换为C的字符串
    const  char * cstr = env->GetStringUTFChars(jstr,NULL);

    //后续操作，产生以UUID为文件名的文件
    char fileName[100];
    sprintf(fileName,"G:\\%s.txt",cstr);
    FILE * f = fopen(fileName,"w");
    fputs(cstr,f);
    fclose(f);

    LOGD("output from C : File had saved", jstr);
}extern "C"
JNIEXPORT void JNICALL
Java_com_example_asus1_ndklearn_MainActivity_accessNonVirtualMathod(JNIEnv *env, jobject instance) {

    // TODO
    jclass  clz = env->GetObjectClass(instance);


}