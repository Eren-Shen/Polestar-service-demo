package com.gyf.cactus.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.gyf.cactus.entity.Constant
import com.gyf.cactus.entity.NotificationConfig
import com.gyf.cactus.ext.sMainHandler
import com.gyf.cactus.ext.setNotification

/**
 * 隐藏前台服务
 * @author geyifeng
 * @date 2019-08-30 14:27
 */
class HideForegroundService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.getParcelableExtra<NotificationConfig>(Constant.CACTUS_NOTIFICATION_CONFIG)
            ?.let {
                setNotification(it, false)
            }
        sMainHandler.postDelayed({
//            stopForeground(true)
//            stopSelf()
        }, 2000)
        return START_NOT_STICKY
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Log.e("York", "HideForegroundService onLowMemory ")

    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Log.e("York", "HideForegroundService onRebind ")

    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        Log.e("York", "HideForegroundService onTaskRemoved ")

    }

    override fun onCreate() {
        super.onCreate()
        Log.e("York", "HideForegroundService onCreate ")

    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Log.e("York", "HideForegroundService onTrimMemory ")

    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
        Log.e("York", "HideForegroundService onUnbind ")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("York", "HideForegroundService onDestroy")
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}