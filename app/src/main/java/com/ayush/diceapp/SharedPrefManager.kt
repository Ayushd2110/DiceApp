package com.ayush.diceapp

import android.content.Context
import android.content.SharedPreferences

//Manage Shared Pref from this class
object SharedPrefManager {
    private var isInit = false
    private var prefs: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    const val PREF_NAME = "Data_Save"
    /*
     * Constant Strings: username, password, cameraip, port, ptzip, ptzport,
     * ptzusername, ptzpassword values-> Srings: temperture,
     * voltageac,voltagedc,bligecount,. boolean:
     * bilgepump,bilgelevel,bilgeintrusion,smoke
     */
    /**
     * init AndroidSharedPreferences ,must be called before use
     * AndroidSharedPreferences
     *
     * @param context
     */
    fun init(context: Context) {
        if (isInit) return
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = prefs!!.edit()
        isInit = true
    }

    fun putString(key: String?, value: String?) {
        editor!!.putString(key, value)
        editor!!.commit()
    }

    fun putInt(key: String?, value: Int) {
        editor!!.putInt(key, value)
        editor!!.commit()
    }

    fun getString(key: String?, defValue: String?): String? {
        return prefs!!.getString(key, defValue)
    }

    fun getInt(key: String?, defValue: Int): Int {
        return prefs!!.getInt(key, defValue)
    }

    fun RemoveAllData() {
        editor!!.clear().commit()
    }
}