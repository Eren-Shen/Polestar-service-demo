package com.polestar.cn.service.sample

import android.annotation.SuppressLint
import com.gyf.cactus.sample.preference

/**
 * @author York
 * @date 2020-01-05
 */
@SuppressLint("StaticFieldLeak")
object Save {
    var timer by preference(0L)
    var lastTimer by preference(0L)
    var date by preference("0000-01-01 00:00:00")
    var endDate by preference("0000-01-01 00:00:00")
}