// Save as "mieterverwaltung.c"
#include <jni.h>        // JNI header provided by JDK
#include <stdio.h>      // C Standard IO Header
#include <time.h>       // For srand-init
#include <stdlib.h>     // For rand()
#include <stdbool.h>    // For boolean-type

bool randomInitHasBeenCalled = 0;

// Implementation of the native method berechneKautionsKosten()
JNIEXPORT float JNICALL Java_application_MainController_berechneKautionsKosten(JNIEnv *env, jobject thisObj) {
   return 100;
}


/*
 * Class:     BasisControl
 * Method:    holeStromzaehlerStandInKWh
 * Signature: ()D
 */
JNIEXPORT jdouble JNICALL Java_application_MainController_holeAktuellenStromverbrauchInKWh(JNIEnv *env, jobject thisObj){
    if(!randomInitHasBeenCalled){
         srand(time(NULL));
         randomInitHasBeenCalled = 1;
    }
    return ((double) rand() / (RAND_MAX)) * 10;
}

/*
 * Class:     BasisControl
 * Method:    holeWasserverbrauchInKubikM
 * Signature: ()D
 */
JNIEXPORT jdouble JNICALL Java_application_MainController_holeAktuellenWasserverbrauchInKubikM(JNIEnv *env, jobject thisObj){
    if(!randomInitHasBeenCalled){
         srand(time(NULL));
         randomInitHasBeenCalled = 1;
    }
    return ((double) rand() / (RAND_MAX)) * 10;
}

/*
 * Class:     BasisControl
 * Method:    berechneMieteinnahmenGesamtInEuro
 * Signature: ([ILjava/lang/String;)V
 * Once the result is determined, calling back the specified callback method and passing it the result of type double as a parameter
 */
JNIEXPORT void JNICALL Java_application_MainController_berechneMieteinnahmenGesamtInEuro(JNIEnv *env, jobject thisObj, jdoubleArray inJNIArray, jstring methodNameToCallBackTo){

   // Step 1: Convert the JNI String (jstring) into C-String (char*)
   const char *inCStr = (*env)->GetStringUTFChars(env, methodNameToCallBackTo, NULL);
   if (NULL == inCStr) return NULL;

   // Get a class reference for this object
   jclass thisClass = (*env)->GetObjectClass(env, thisObj);

   // Get a class reference for java.lang.Integer
   jclass classDouble = (*env)->FindClass(env, "java/lang/Double");
   // Use Integer.intValue() to retrieve the int
   jmethodID midDoubleValue = (*env)->GetMethodID(env, classDouble, "doubleValue", "()D");
   if (NULL == midDoubleValue) return NULL;

   // Step 1: Convert the incoming JNI jdoubleArray to C's jdouble[]
   jdouble *inCArray = (*env)->GetDoubleArrayElements(env, inJNIArray, NULL);
   if (NULL == inCArray) return NULL;
   jsize length = (*env)->GetArrayLength(env, inJNIArray);

   // Step 2: Perform its intended operations
   jdouble sum = 0;
   int i;
   for (i = 0; i < length; i++) {
      sum += inCArray[i];
   }
   (*env)->ReleaseDoubleArrayElements(env, inJNIArray, inCArray, 0); // release resources

   jmethodID methodToCallBack = (*env)->GetMethodID(env, thisClass, inCStr, "(D)V");
   if (NULL == methodToCallBack) return;

   (*env)->CallVoidMethod(env, thisObj, methodToCallBack, sum);
}

/*
 * Class:     application_MainController
 * Method:    erstelleMieterObjekt
 * Signature: (JLjava/lang/String;Ljava/lang/String;IIJ)Lmodel/Mieter;
 */
JNIEXPORT jobject JNICALL Java_application_MainController_erstelleMieterObjekt(JNIEnv *env, jobject thisObj,
                                                    jlong mieterID, jstring name, jstring vorname, jint alter, jint telefonnummer, jlong mietobjektID){
   // Get a class reference for model.Mieter
   jclass cls = (*env)->FindClass(env, "model/Mieter");

   // Get the Method ID of the constructor which takes an int
   jmethodID midInit = (*env)->GetMethodID(env, cls, "<init>", "(JLjava/lang/String;Ljava/lang/String;IIJ)V");
   if (NULL == midInit) return NULL;
   // Call back constructor to allocate a new instance, with all the right arguments
   jobject newObj = (*env)->NewObject(env, cls, midInit, mieterID, name, vorname, alter, telefonnummer, mietobjektID);

   return newObj;
}


/*
 * Class:     application_MainController
 * Method:    erstelleMietobjektObjekt
 * Signature: (JIDILjava/lang/String;Ljava/lang/String;)V
 * Once the object has been initialized, calling back the specified callback method and passing it the object of type model.Mieterobjekt
 */
JNIEXPORT void JNICALL Java_application_MainController_erstelleMietobjektObjekt(JNIEnv *env, jobject thisObj, jlong mietobjektID,
                                                        jint flaecheInQuadratmetern, jdouble monatsmieteInEuro, jint baujahr, jstring lage, jstring methodNameToCallBackTo){

    // Get a class reference for model.Mieter
   jclass cls = (*env)->FindClass(env, "model/Mietobjekt");

   // Get the Method ID of the constructor which takes an int
   jmethodID midInit = (*env)->GetMethodID(env, cls, "<init>", "(JIDILjava/lang/String;)V");
   if (NULL == midInit) return NULL;
   // Call back constructor to allocate a new instance, with all the right arguments
   jobject newObj = (*env)->NewObject(env, cls, midInit, mietobjektID, flaecheInQuadratmetern, monatsmieteInEuro, baujahr, lage);


   // Convert the JNI String (jstring) into C-String (char*), getting the name of the method to callback to
   const char *inCStr = (*env)->GetStringUTFChars(env, methodNameToCallBackTo, NULL);
   if (NULL == inCStr) return NULL;

   // Get a class reference for this object
   jclass thisClass = (*env)->GetObjectClass(env, thisObj);

   // Getting the method-ID of the method to call back to:
   jmethodID methodToCallBack = (*env)->GetMethodID(env, thisClass, inCStr, "(Lmodel/Mietobjekt;)V");
   if (NULL == methodToCallBack) return;

   // Calling back callback method with created Mietobjekt-Objekt:
   (*env)->CallVoidMethod(env, thisObj, methodToCallBack, newObj);
}
