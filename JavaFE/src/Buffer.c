#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "Buffer_Helper.h"
 
JNIEXPORT jstring JNICALL JNICALL Java_Buffer_00024Helper_prepareFloat(JNIEnv *env, jobject jo, jfloatArray arr){
   jstring result=NULL;
   size_t i;
   size_t j;
   char *msg=NULL;
   int msg_index=0;
   char buffer[9];
   unsigned char const *p;
   jsize len = (*env)->GetArrayLength(env, arr);
   long msg_size=4*len*sizeof(float)+1;
   msg=(char *)malloc(msg_size);
   msg[msg_size-1]=0x00;

   int ii;
   union {
	   char a;
	   unsigned char bytes[4];
   } thing;

   thing.a = '0';

   jfloat *body = (*env)->GetFloatArrayElements(env, arr, 0);
   for (j=0;j<len;j++) {    
     p = (unsigned char const *)&body[j];
     for (i = 0; i != sizeof(float); ++i) {
       sprintf(buffer,"%02X%02X%02X%02X",p[0],p[1],p[2],p[3]);
     }
     memcpy(msg+8*j,buffer,8);
   }
   (*env)->ReleaseFloatArrayElements(env, arr, body, 0);
   result = (*env)->NewStringUTF(env,msg);  
   free(msg);
   return result;
}

JNIEXPORT jstring JNICALL Java_Buffer_00024Helper_preparePtxSource
  (JNIEnv *env, jobject jo, jstring ptxSource, jlong size){

size_t i,j;
   char *msg=NULL;
   long msg_size=size*2+1;
   msg=(char *)malloc(msg_size);
   msg[msg_size-1]=0x00;


   jstring result=NULL;

const char *nativeString = (*env)->GetStringUTFChars(env, ptxSource, 0);

 for(i = 0; i<size; i++){
        sprintf(msg+i*2, "%02X", nativeString[i]);
    }


//printf("name printed as %%s is %s\n",msg);
/*printf("name printed as %%s is %s\n",nativeString);
printf("name printed as %%c 0 is %c\n",nativeString[0]);
printf("name printed as %%c 1 is %c\n",nativeString[1]);
printf("name printed as %%c 2 is %c\n",nativeString[2]);
printf("name printed as %%c 3 is %c\n",nativeString[3]);
printf("name printed as %%x 0 is %02X\n",nativeString[0] & 0xff);
printf("name printed as %%x 1 is %02X\n",nativeString[1] & 0xff);
printf("name printed as %%x 2is %02X\n",nativeString[2] & 0xff);
printf("name printed as %%x 3 is %02X\n",nativeString[3] & 0xff);*/

  /*  int i, len;

    printf("Intro word:");
    fgets(word, sizeof(word), stdin);
    len = strlen(word);
    if(word[len-1]=='\n')
        word[--len] = '\0';*/

    

(*env)->ReleaseStringUTFChars(env, ptxSource, nativeString);
result = (*env)->NewStringUTF(env,msg);
free(msg);

return result;	

}


JNIEXPORT jstring JNICALL Java_Buffer_00024Helper_prepareSingleByte
  (JNIEnv *env, jobject jo, jint i){
  char *msg=NULL;
   long msg_size=2+1;
   msg=(char *)malloc(msg_size);
   msg[msg_size-1]=0x00;
   jstring result=NULL;
        sprintf(msg, "%02X", i);
result = (*env)->NewStringUTF(env,msg);
free(msg);

return result;	

}

/*#include <jni.h>
#include <stdio.h>
#include "Buffer.h"
///usr/lib/jvm/java-1.7.0-openjdk-1.7.0.101.x86_64/bin/javah 
// Implementation of native method sayHello() of HelloJNI class
JNIEXPORT jbyteArray  JNICALL Java_Buffer_sayHello
  (JNIEnv *env, jobject ob, jfloat jf)  {
    
int ii;
union {
	float a;	
	unsigned char bytes[4];
} thing;
jstring result;
//char* buf2;
//char buf[] = {0, 0, 128, 63};
jbyteArray data = (*env)->NewByteArray(env,8);

thing.a = jf;
printf("Float passed : %f  in hex is: %08X \n", jf, *(unsigned int*)&jf);
for (ii = 0; ii< 4; ii++){
  //      buf2 += sprintf(buf2, "%02X", buf[ii]);
	//buf2 += sprintf(buf2, "%02X", thing.bytes[ii]);
        printf("byte %d in hex %02x in char %c in %d  \n", ii, data[ii]);
	printf("byte %d in hex %02x in int %d\n", ii, thing.bytes[ii], thing.bytes[ii]);}        

//(*env)->ReleaseStringUTFChars(env,string, thing.bytes);
result = (*env)->NewStringUTF(env,thing.bytes);
return data;
       }
*/
