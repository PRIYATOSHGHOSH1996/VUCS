#include <jni.h>
#include "../../../../../../AppData/Local/Android/Sdk/ndk-bundle/toolchains/llvm/prebuilt/windows-x86_64/sysroot/usr/include/jni.h"

#define SIZE 101
JNIEXPORT jstring JNICALL
Java_com_vucs_service_DataServiceGenerator_getBaseURL(JNIEnv *env, jobject instance) {

    /* char k1 = 'h';
     char k2 = 't';
     char k3 = 't';
     char k4 = 'p';
     char k5 = 's';
     char k6 = ':';
     char k7 = '/';
     char k8 = '/';
     char k9 = 'w';
     char k10 = 'w';
     char k11 = 'w';
     char k12 = '.';
     char k13 = 't';
     char k14 = 'r';
     char k15 = 'a';
     char k16 = 'c';
     char k17 = 'k';
     char k18 = 'b';
     char k19 = 'e';
     char k20 = 'e';
     char k21 = '.';
     char k22 = 'c';
     char k23 = 'o';
     char k24 = 'm';
     char k25 = '/';
     char k26 = 's';
     char k27 = 'u';
     char k28 = 'r';
     char k29 = 'v';
     char k30 = 'e';
     char k31 = 'y';
     char k32 = '_';
     char k33 = 'v';
     char k34 = '8';
     char k35 = '/';
     char k36 = 'c';
     char k37 = 'o';
     char k38 = 'n';
     char k39 = 'n';
     char k40 = 'e';
     char k41 = 'c';
     char k42 = 't';
     char k43 = '/';
 */
    char k1 = 'h';
    char k2 = 't';
    char k3 = 't';
    char k4 = 'p';
    char k5 = 's';
    char k6 = ':';
    char k7 = '/';
    char k8 = '/';
    char k9 = 's';
    char k10 = 'a';
    char k11 = 'r';
    char k12 = 'a';
    char k13 = 's';
    char k14 = 'i';
    char k15 = 'j';
    char k16 = '.';
    char k17 = 'c';
    char k18 = 'o';
    char k19 = 'm';
    char k20 = '/';
    char k21 = 'v';
    char k22 = 'u';
    char k23 = 'c';
    char k24 = 's';
    char k25 = '_';
    char k26 = 'u';
    char k27 = 'p';
    char k28 = '/';
    char k29 = 'p';
    char k30 = 'f';
    char k31 = '-';
    char k32 = 'a';
    char k33 = 'd';
    char k34 = 'm';
    char k35 = 'i';
    char k36 = 'n';
    char k37 = '/';
    char k38 = 'a';
    char k39 = 'p';
    char k40 = 'i';
    char k41 = '/';

    //char key [SIZE] = {k1,k2,k3,k4,k5,k6,k7,k8,k9,k10,k11,k12,k13,k14,k15,k16,k17,k18,k19,k20,k21,k22,k23,k24,k25,k26,k27,k28,k29,k30,k31,k32,k33,k34,k35,k36,k37,k38,k39,k40,k41,k42,k43,'\0'};
    char key [SIZE] = {k1,k2,k3,k4,k6,k7,k8,k9,k10,k11,k12,k13,k14,k15,k16,k17,k18,k19,k20,k21,k22,k23,k24,k25,k26,k27,k28,k29,k30,k31,k32,k33,k34,k35,k36,k37,k38,k39,k40,k41,'\0'};
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