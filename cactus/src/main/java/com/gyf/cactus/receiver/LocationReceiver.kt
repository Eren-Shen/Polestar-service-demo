package com.gyf.cactus.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log

internal class LocationReceiver private constructor(val context: Context) : BroadcastReceiver() {

    companion object {
        internal fun newInstance(context: Context) = LocationReceiver(context)
    }

    /**
     * 待操作事件
     */
    private var mBlock: (() -> Unit)? = null

    private var mActionStop = "location_in_background"

    init {
        context.registerReceiver(this, IntentFilter(mActionStop))
    }

    /**
     * 注册
     *
     * @param block Function0<Unit>
     */
    internal fun register(block: () -> Unit) {
        mBlock = block
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.action?.also {
//            when (it) {
//                mActionStop -> {
//                    this.context.unregisterReceiver(this)
//                    mBlock?.let {
//                        it()
//                    }
//                }
//            }
            Log.e("York", "LocationReceiver onReceived")
            val action = intent.action
            if (action == mActionStop) {
                val locationResult = intent.getStringExtra("result")
                if (null != locationResult && locationResult.trim { it <= ' ' } != "") {
//                    tvResult.setText(locationResult)
                    Log.e("York", "LocationReceiver data = " + locationResult)
                }
            }
        }
    }
}