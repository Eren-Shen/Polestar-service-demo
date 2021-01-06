package com.gyf.cactus.sample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.gyf.cactus.Cactus

/**
 * 测试Cactus广播接受
 * @author geyifeng
 * @date 2019-08-30 10:30
 */
class MainReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        intent.action?.apply {
            when (this) {
                Cactus.CACTUS_WORK -> {
                    Log.d(
                        App.TAG,
                        this + "--" + intent.getIntExtra(Cactus.CACTUS_TIMES, 0)
                    )
                }
                Cactus.CACTUS_STOP -> {
                    Log.d(App.TAG, this)
                }
                Cactus.CACTUS_BACKGROUND -> {
                    Log.d(App.TAG, this)
                }
                Cactus.CACTUS_FOREGROUND -> {
                    Log.d(App.TAG, this)
                }
                Intent.ACTION_BOOT_COMPLETED -> {
                    Log.d("York", "ACTION_BOOT_COMPLETED")
                }
            }
        }
    }
}