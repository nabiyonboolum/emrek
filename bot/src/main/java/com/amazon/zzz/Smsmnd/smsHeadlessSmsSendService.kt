package com.amazon.zzz.Smsmnd

import android.app.Service
import android.content.Intent

import android.os.IBinder
import com.amazon.zzz.ApiNm.apiUt


class smsHeadlessSmsSendService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        apiUt.sendLogs(this, "", "smsHeadlessSmsSendService onBind", "log")
        return null
    }

}
