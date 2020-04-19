// Save as "mieterverwaltung.c"
#include <jni.h>        // JNI header provided by JDK
#include <stdio.h>      // C Standard IO Header
#include <time.h>       // For srand-init
#include <stdlib.h>     // For rand()
#include <windows.h>    // For sleeping (on windows)

unsigned int numOfMillisecondsToSleepFor = 2000;


// Implementation of the native method berechneKautionsKosten()
JNIEXPORT float JNICALL Java_BasisControl_berechneKautionsKosten(JNIEnv *env, jobject thisObj) {
   return 100;
}


/*
 * Class:     BasisControl
 * Method:    holeStromzaehlerStandInKWh
 * Signature: ()D
 */
JNIEXPORT jdouble JNICALL Java_BasisControl_holeStromzaehlerStandInKWh(JNIEnv *env, jobject thisObj){
    srand(time(NULL));
    return ((double) rand() / (RAND_MAX)) * 10;
}

/*
 * Class:     BasisControl
 * Method:    holeWasserverbrauchInKubikM
 * Signature: ()D
 */
JNIEXPORT jdouble JNICALL Java_BasisControl_holeWasserverbrauchInKubikM(JNIEnv *env, jobject thisObj){
    srand(time(NULL));
    return ((double) rand() / (RAND_MAX)) * 10;
}

/*
 * Class:     BasisControl
 * Method:    berechneMieteinnahmenGesamtInEuro
 * Signature: ([ILjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_BasisControl_berechneMieteinnahmenGesamtInEuro(JNIEnv *env, jobject thisObj, jintArray inJNIArray, jstring methodNameToCallBackTo){

    // Sleeping, to simulate a function call that takes some time to respond
   Sleep(numOfMillisecondsToSleepFor);

   // Step 1: Convert the JNI String (jstring) into C-String (char*)
   const char *inCStr = (*env)->GetStringUTFChars(env, methodNameToCallBackTo, NULL);
   if (NULL == inCStr) return NULL;

   // Get a class reference for this object
   jclass thisClass = (*env)->GetObjectClass(env, thisObj);

   // Get a class reference for java.lang.Integer
   jclass classInteger = (*env)->FindClass(env, "java/lang/Integer");
   // Use Integer.intValue() to retrieve the int
   jmethodID midIntValue = (*env)->GetMethodID(env, classInteger, "intValue", "()I");
   if (NULL == midIntValue) return NULL;

   // Step 1: Convert the incoming JNI jintarray to C's jint[]
   jint *inCArray = (*env)->GetIntArrayElements(env, inJNIArray, NULL);
   if (NULL == inCArray) return NULL;
   jsize length = (*env)->GetArrayLength(env, inJNIArray);

   // Step 2: Perform its intended operations
   jdouble sum = 0;
   int i;
   for (i = 0; i < length; i++) {
      sum += inCArray[i];
   }
   (*env)->ReleaseIntArrayElements(env, inJNIArray, inCArray, 0); // release resources

   jmethodID methodToCallBack = (*env)->GetMethodID(env, thisClass, inCStr, "(D)V");
   if (NULL == methodToCallBack) return;

   (*env)->CallVoidMethod(env, thisObj, methodToCallBack, sum);
}
