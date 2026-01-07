package com.unluckyprayers.associumhub.data.local.pref

import android.content.Context

class SharedPrefManager (private val context: Context) {

    companion object {
        private const val SHARED_PREF_NAME = "ahub_shared_pref"
    }

    fun saveString(key: String, value: String?) {
        val sharedPref = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getStringData(key: String): String? {
        val sharedPref = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString(key, null)
    }
    
    fun deleteString(key: String) {
        val sharedPref = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.remove(key)
        editor.apply()
    }
}
