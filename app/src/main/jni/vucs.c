#include <jni.h>
#include "../../../../../../AppData/Local/Android/Sdk/ndk-bundle/toolchains/llvm/prebuilt/windows-x86_64/sysroot/usr/include/jni.h"

#define SIZE 101
JNIEXPORT jstring JNICALL
Java_com_vucs_service_DataServiceGenerator_getBaseURL(JNIEnv *env, jobject instance) {

    /*char k1 = 'h';
    char k2 = 't';
    char k3 = 't';
    char k4 = 'p';
    char k5 = 's';
    char k6 = ':';
    char k7 = '/';
    char k8 = '/';
    char k9 = '1';
    char k10 = '9';
    char k11 = '2';
    char k12 = '.';
    char k13 = '1';
    char k14 = '6';
    char k15 = '8';
    char k16 = '.';
    char k17 = '0';
    char k18 = '.';
    char k19 = '1';
    char k20 = '0';
    char k21 = '/';
    char k22 = 'v';
    char k23 = 'u';
    char k24 = 'c';
    char k25 = 's';
    char k26 = '_';
    char k27 = 'u';
    char k28 = 'p';
    char k29 = '/';
    char k30 = 'p';
    char k31 = 'f';
    char k32 = '-';
    char k33 = 'a';
    char k34 = 'd';
    char k35 = 'm';
    char k36 = 'i';
    char k37 = 'n';
    char k38 = '/';
    char k39 = 'a';
    char k40 = 'p';
    char k41 = 'i';
    char k42 = '/';*/


   char k1 = 'h';
    char k2 = 't';
    char k3 = 't';
    char k4 = 'p';
    char k5 = 's';
    char k6 = ':';
    char k7 = '/';
    char k8 = '/';
    char k9 = 'v';
    char k10 = 'u';
    char k11 = 'c';
    char k12 = 's';
    char k13 = '.';
    char k14 = 'i';
    char k15 = 'n';
    char k16 = '/';
    char k17 = 'p';
    char k18 = 'f';
    char k19 = '-';
    char k20 = 'a';
    char k21 = 'd';
    char k22 = 'm';
    char k23 = 'i';
    char k24 = 'n';
    char k25 = '/';
    char k26 = 'a';
    char k27 = 'p';
    char k28 = 'i';
    char k29 = '/';


    //char key [SIZE] = {k1,k2,k3,k4,k6,k7,k8,k9,k10,k11,k12,k13,k14,k15,k16,k17,k18,k19,k20,k21,k22,k23,k24,k25,k26,k27,k28,k29,k30,k31,k32,k33,k34,k35,k36,k37,k38,k39,k40,k41,k42,'\0'};
    char key [SIZE] = {k1,k2,k3,k4,k6,k7,k8,k9,k10,k11,k12,k13,k14,k15,k16,k17,k18,k19,k20,k21,k22,k23,k24,k25,k26,k27,k28,k29,'\0'};
    return (*env)->NewStringUTF(env, key);
}
JNIEXPORT jstring JNICALL
Java_com_vucs_api_ApiCredential_getUsername(JNIEnv *env, jobject instance) {
    char a = 'V';
    char b = 'U';
    char c = 'c';
    char d = 's';
    char e = 'K';
    char f = '9';
    char g = '5';
    char key [SIZE] = {a,b,c,d,e,f,g,'\0'};
    return (*env)->NewStringUTF(env, key);
}
JNIEXPORT jstring JNICALL
Java_com_vucs_api_ApiCredential_getPassword(JNIEnv *env, jobject instance) {
    char a = 'V';
    char b = 'U';
    char c = 'c';
    char d = 's';
    char e = 'P';
    char f = 'r';
    char g = 'i';
    char h = '9';
    char i = '6';
    char key [SIZE] = {a,b,c,d,e,f,g,h,i,'\0'};
    return (*env)->NewStringUTF(env, key);
}