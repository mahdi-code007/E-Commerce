//package com.example.e_commerce.util
//
//import android.content.Context
//import android.content.SharedPreferences
//import com.example.e_commerce.util.Constants.Companion.EMPTY
//import com.example.e_commerce.util.Constants.Companion.IS_LOGIN
//import com.example.e_commerce.util.Constants.Companion.SHARED_PREFERENCES_NAME
//import com.example.e_commerce.util.Constants.Companion.TOKEN
//import com.google.gson.Gson
//
//
//class SharedPreferenceManger(context: Context) {
//
//
//    var con = context
//
//    companion object {
//        private val sharedPreferences by lazy {
//            var con: Context
//            fun getContext(context: Context) {
//                SharedPreferenceManger.c
//            }
//            SharedPreferences =
//                    con.contegetSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
//
//        }
//    }
//
//    private val editor by lazy {
//        var editor: SharedPreferences.Editor = sharedPreferences.edit()
//    }
//
//    private val gson = Gson()
//
//    var isLogin: Boolean
//        get() = getBooleanValue(IS_LOGIN)
//        set(value) = setValue(IS_LOGIN, value)
//
//
////    var language: String?
////        get() = getStringValue(LANGUAGE)
////        set(value) = setValue(LANGUAGE, value!!)
////
////    var deviceId: String?
////        get() = getStringValue(DEVICE_ID)
////        set(value) = setValue(DEVICE_ID, value!!)
//
//    var token: String?
//        get() = getUserToken()
//        set(value) = setUserToken(value!!)
//
////    var userType: String?
////        get() = getStringValue(USER_TYPE)
////        set(value) = setValue(USER_TYPE, value!!)
//
////    var user: UserData?
////        get() = getUserData()
////        set(value) = setUserData(value!!)
//
//    private fun setUserToken(value: String) {
//        editor.putString(TOKEN, value)
//        editor.apply()
//    }
//
////    private fun setUserToken(value: String) {
////        editor.putString(TOKEN, "$BEARER_TOKEN $value")
////        editor.apply()
////    }
//
//    private fun getUserToken(): String? {
//        return sharedPreferences.getString(TOKEN, EMPTY)
//    }
//
//    fun setValue(key: String, value: String) {
//        editor.putString(key, value)
//        editor.apply()
//    }
//
//    fun setValue(key: String, value: Int) {
//        editor.putInt(key, value)
//        editor.apply()
//    }
//
//    fun setValue(key: String, value: Float) {
//        editor.putFloat(key, value)
//        editor.apply()
//    }
//
//    fun setValue(key: String, value: Boolean) {
//        editor.putBoolean(key, value)
//        editor.apply()
//    }
//
//    fun getStringValue(key: String): String? {
//        return sharedPreferences.getString(key, EMPTY)
//    }
//
//    fun getIntegerValue(key: String): Int {
//        return sharedPreferences.getInt(key, 0)
//    }
//
//    fun getFloatValue(key: String): Float {
//        return sharedPreferences.getFloat(key, 0F)
//    }
//
//    fun getBooleanValue(key: String): Boolean {
//        return sharedPreferences.getBoolean(key, false)
//    }
//
////    private fun setUserData(value: UserData) {
////        val json = gson.toJson(value)
////        setValue(USER_DATA, json)
////    }
//
////    private fun getUserData(): UserData? {
////
////        val value = getStringValue(USER_DATA)
////
////        return if (value != EMPTY) {
////            gson.fromJson(value, UserData::class.java)
////        } else {
////            null
////        }
////    }
//
//    fun remove(key: String) {
//        editor.remove(key)
//        editor.apply()
//    }
//}