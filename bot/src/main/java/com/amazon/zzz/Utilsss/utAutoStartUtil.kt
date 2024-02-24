package com.amazon.zzz.Utilsss

import android.app.ActivityManager
import android.content.Context

object utAutoStartUtil {
    fun isMyServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
        try {
            val manager =
                context.applicationContext?.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
                if (serviceClass.name == service.service.className) {
                    return true
                }
            }
        } catch (e: Exception) {
        }
        return false
    }

}
