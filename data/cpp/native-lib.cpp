#include <jni.h>
#include <string>

std::string getBaseUrl() {
    return "https://pokeapi.co/api/v2";
}

std::string getProfileImageUrl() {
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork";
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_onedev_pokedex_data_core_NativeLib_getBaseUrl(JNIEnv *env, jobject /* this */) {
    return env->NewStringUTF(getBaseUrl().c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_onedev_pokedex_data_core_NativeLib_getProfileImageUrl(JNIEnv *env, jobject /* this */) {
    return env->NewStringUTF(getProfileImageUrl().c_str());
}
