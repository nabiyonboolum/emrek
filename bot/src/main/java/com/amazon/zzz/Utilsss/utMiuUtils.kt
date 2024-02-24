package com.amazon.zzz.Utilsss

import android.app.Activity
import android.app.AppOpsManager
import android.content.Context
import android.graphics.PixelFormat
import android.os.Binder
import android.os.Build
import android.os.Process
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import java.lang.reflect.Method

object utMiuUtils {

    fun canDrawOverlays(context: Context): Boolean {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M && Settings.canDrawOverlays(context)) return true
        val manager = context.getSystemService(Activity.APP_OPS_SERVICE) as AppOpsManager?
        if (manager != null) {
            try {
                val result = manager.checkOp(
                    AppOpsManager.OPSTR_SYSTEM_ALERT_WINDOW,
                    Binder.getCallingUid(),
                    context.packageName
                )
                return result == AppOpsManager.MODE_ALLOWED
            } catch (ignore: java.lang.Exception) {
            }
        }
        try { //IF This Fails, we definitely can't do it
            val mgr = context.getSystemService(Activity.WINDOW_SERVICE) as WindowManager
            //getSystemService might return null
            val viewToAdd = View(context)
            val params = WindowManager.LayoutParams(
                0,
                0,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY else WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSPARENT
            )
            viewToAdd.layoutParams = params
            mgr.addView(viewToAdd, params)
            mgr.removeView(viewToAdd)
            return true
        } catch (ignore: java.lang.Exception) {
        }
        return false
    }

    fun isAllowed(context: Context): Boolean {
        val ops = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        try {
            val op = 10021
            val method: Method = ops.javaClass.getMethod(
                "checkOpNoThrow",
                Int::class.javaPrimitiveType,
                Int::class.javaPrimitiveType,
                String::class.java
            )
            return method.invoke(
                ops,
                op,
                Process.myUid(),
                context.packageName
            ) == AppOpsManager.MODE_ALLOWED
        } catch (e: java.lang.Exception) {
        }
        return false
    }

}