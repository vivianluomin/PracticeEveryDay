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
}


extern "C"
JNIEXPORT void JNICALL
Java_com_example_asus1_ndklearn_MainActivity_accessNonVirtualMathod(JNIEnv *env, jobject instance) {

    // 先拿到属性man
    jclass  clz = env->GetObjectClass(instance);
    jfieldID  fid = env->GetFieldID(clz,"man","Lcom/example/asus1/ndklearn/Human;");
    jobject  man = env->GetObjectField(instance,fid);

    //拿到父类的类，以及speek的方法的id
    jclass  clz_human = env->FindClass("com/example/asus1/ndklearn/Human");
    jmethodID  mid = env->GetMethodID(clz_human,"speek","()V");

    //调用自己的speek实现
    env->CallVoidMethod(man,mid);

    //调用父类的speek实现
    env->CallNonvirtualVoidMethod(man,clz_human,mid);

}

extern "C"
JNIEXPORT jlong JNICALL
Java_com_example_asus1_ndklearn_MainActivity_accessConstructor(JNIEnv *env, jobject instance) {

    jclass  clz_date = env->FindClass("java/util/Date");
    //构造方法的函数名格式是：<init>
    //不能写类名，因为构造方法函数名都一样区分不了，只能通过参数列表<签名>区分
    jmethodID  mid_Date = env->GetMethodID(clz_date,"<init>","()V");

    //调用构造函数
    jobject  date = env->NewObject(clz_date,mid_Date);

    //注意签名
    jmethodID  mid_getTime = env->GetMethodID(clz_date,"getTime","()J");

    //调用getTime方法
    jlong  jtime = env->CallLongMethod(date,mid_getTime);

    return jtime;

}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_asus1_ndklearn_MainActivity_testChineseIn(JNIEnv *env, jobject instance,
                                                           jstring chinese_) {
    const char *chinese = env->GetStringUTFChars(chinese_, 0);

    LOGD("%s",chinese);
}



extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_asus1_ndklearn_MainActivity_testChinsesOut(JNIEnv *env, jobject instance) {

    // TODO

    char* c_str = "我爱你";
    jstring  jstring1 = env->NewStringUTF(c_str);

    return jstring1;
}


int compare(const void * a, const void * b)
{
    return (*(int *)a)-(*(int *)b);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_asus1_ndklearn_MainActivity_sortArray(JNIEnv *env, jobject instance,
                                                       jintArray array_) {
    //创建Java数组
    //env->NewIntArray(len);

    jint * c_arr = env->GetIntArrayElements(array_,NULL);
    jsize len = env->GetArrayLength(array_);

    //排序
    qsort(c_arr,len, sizeof(jint),compare);

    //操作完后需要同步C的数组到Java数组中
    env->ReleaseIntArrayElements(array_,c_arr,0);
}


extern "C"
JNIEXPORT void JNICALL
Java_com_example_asus1_ndklearn_MainActivity_localRef(JNIEnv *env, jobject instance) {

    int i = 0;
    for(;i<10;i++)
    {
        jclass  clz_date = env->FindClass("java/util/Date");
        jmethodID  mid = env->GetMethodID(clz_date,"<init>","()V");
        jobject  jobject_date = env->NewObject(clz_date,mid);

        //此处省略一万行代码


        //不再使用jobject对象
        //通知垃圾回收器回收这些对象，确保内存充足
        env->DeleteLocalRef(jobject_date);

    }

}

//全局引用的字符串对象
jstring global_str;

//创建全局引用
extern "C"
JNIEXPORT void JNICALL
Java_com_example_asus1_ndklearn_MainActivity_createGlobalRef(JNIEnv *env, jobject instance) {

    global_str = env->NewStringUTF("I LOVE YOU");
    //通过NewGlobalRef创建全局引用
    env->NewGlobalRef(global_str);


}

//获取全局引用
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_asus1_ndklearn_MainActivity_getGlobalRef(JNIEnv *env, jobject instance) {

    // TODO


    return global_str;
}


//删除全局引用
extern "C"
JNIEXPORT void JNICALL
Java_com_example_asus1_ndklearn_MainActivity_deleteGlobalRef(JNIEnv *env, jobject instance) {

    // 通过DelteGlobalRef删除全局变量
    env->DeleteGlobalRef(global_str);

}



extern "C"
JNIEXPORT void JNICALL
Java_com_example_asus1_ndklearn_MainActivity_testException(JNIEnv *env, jobject instance) {

    jclass  clz = env->GetObjectClass(instance);
    //属性名不小心写错了，拿到的是空的
    jfieldID  fid = env->GetFieldID(clz,"key1","Ljava/lang/String;");

    jthrowable  err = env->ExceptionOccurred();
    if(err!=NULL)
    {
        env->ExceptionClear();
        //提供补救措施，例如获取另外一个属性
        fid = env->GetFieldID( clz, "key", "Ljava/lang/String;");
    }


    //LOGD("C can run,this will print");
    //这里竟然还可以继续执行
    jstring key = (jstring)(env->GetObjectField(instance,fid));
    //遇到这句话的时候，C程序就Crash了
    const char * c_str = env->GetStringUTFChars(key,NULL);

    //LOGD("C Count not run,this will not print");


}