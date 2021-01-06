package com.gyf.cactus

import android.app.Notification
import android.app.Service
import android.content.*
import android.location.LocationManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Environment
import android.text.TextUtils
import com.amap.api.location.AMapLocation
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * 辅助工具类
 *
 * @author hongming.wang
 * @创建时间： 2015年11月24日 上午11:46:50
 * @项目名称： AMapLocationDemo2.x
 * @文件名称: Utils.java
 * @类型名称: Utils
 */
object Utils {
    /**
     * 开始定位
     */
    val MSG_LOCATION_START = 0

    /**
     * 定位完成
     */
    val MSG_LOCATION_FINISH = 1

    /**
     * 停止定位
     */
    val MSG_LOCATION_STOP = 2
    val KEY_URL = "URL"
    val URL_H5LOCATION = "file:///android_asset/location.html"

    /**
     * 根据定位结果返回定位信息的字符串
     *
     * @param location
     * @return
     */
    @Synchronized
    fun getLocationStr(location: AMapLocation?): String? {
        if (null == location) {
            return null
        }
        val sb = StringBuffer()
        //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
        if (location.errorCode == 0) {
            sb.append("定位成功" + "\n")
            sb.append("定位类型: " + location.locationType + "\n")
            sb.append("经    度    : " + location.longitude + "\n")
            sb.append("纬    度    : " + location.latitude + "\n")
            sb.append("精    度    : " + location.accuracy + "米" + "\n")
            sb.append("提供者    : " + location.provider + "\n")
            sb.append("海    拔    : " + location.altitude + "米" + "\n")
            sb.append("速    度    : " + location.speed + "米/秒" + "\n")
            sb.append("角    度    : " + location.bearing + "\n")
            if (location.provider.equals(
                    LocationManager.GPS_PROVIDER, ignoreCase = true
                )
            ) {
                // 以下信息只有提供者是GPS时才会有
                // 获取当前提供定位服务的卫星个数
                sb.append(
                    "星    数    : "
                            + location.satellites + "\n"
                )
            }

            //逆地理信息
            sb.append("国    家    : " + location.country + "\n")
            sb.append("省            : " + location.province + "\n")
            sb.append("市            : " + location.city + "\n")
            sb.append("城市编码 : " + location.cityCode + "\n")
            sb.append("区            : " + location.district + "\n")
            sb.append("区域 码   : " + location.adCode + "\n")
            sb.append("地    址    : " + location.address + "\n")
            sb.append("兴趣点    : " + location.poiName + "\n")
            //定位完成的时间
            sb.append("定位时间: " + formatUTC(location.time, "yyyy-MM-dd HH:mm:ss") + "\n")
        } else {
            //定位失败
            sb.append("定位失败" + "\n")
            sb.append("错误码:" + location.errorCode + "\n")
            sb.append("错误信息:" + location.errorInfo + "\n")
            sb.append("错误描述:" + location.locationDetail + "\n")
        }
        //定位之后的回调时间
        sb.append("回调时间: " + formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "\n")
        return sb.toString()
    }

    private var sdf: SimpleDateFormat? = null

    @Synchronized
    fun formatUTC(l: Long, strPattern: String?): String {
        var strPattern = strPattern
        if (TextUtils.isEmpty(strPattern)) {
            strPattern = "yyyy-MM-dd HH:mm:ss"
        }
        if (sdf == null) {
            try {
                sdf = SimpleDateFormat(strPattern, Locale.CHINA)
            } catch (e: Throwable) {
            }
        } else {
            sdf!!.applyPattern(strPattern)
        }
        return if (sdf == null) "NULL" else sdf!!.format(l)
    }

    fun getExplicitIntent(context: Context, implicitIntent: Intent?): Intent? {
        if (context.applicationInfo.targetSdkVersion < Build.VERSION_CODES.LOLLIPOP) {
            return implicitIntent
        }

        // Retrieve all services that can match the given intent
        val pm = context.packageManager
        val resolveInfo = pm.queryIntentServices(implicitIntent, 0)
        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size != 1) {
            return null
        }
        // Get component info and create ComponentName
        val serviceInfo = resolveInfo[0]
        val packageName = serviceInfo.serviceInfo.packageName
        val className = serviceInfo.serviceInfo.name
        val component = ComponentName(packageName, className)
        // Create a new intent. Use the old one for extras and such reuse
        val explicitIntent = Intent(implicitIntent)
        // Set the component to be explicit
        explicitIntent.component = component
        return explicitIntent
    }

    /**
     * 　　* 保存文件 　　* @param toSaveString 　　* @param filePath
     */
    fun saveFile(toSaveString: String, fileName: String, append: Boolean) {
        try {
            val sdCardRoot = Environment.getExternalStorageDirectory()
                .absolutePath
            val saveFile = File("$sdCardRoot/$fileName")
            if (!saveFile.exists()) {
                val dir = File(saveFile.parent)
                dir.mkdirs()
                saveFile.createNewFile()
            }
            val outStream = FileOutputStream(saveFile, append)
            outStream.write(toSaveString.toByteArray())
            outStream.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun buildNotification(context: Context?): Notification {
        val builder = Notification.Builder(context)
        builder.setContentText("service")
        return builder.notification
    }

    fun startWifi(context: Context) {
        val wm = context
            .getSystemService(Context.WIFI_SERVICE) as WifiManager
        wm.isWifiEnabled = true
        wm.reconnect()
    }

    fun isWifiEnabled(context: Context): Boolean {
        val wm = context
            .getSystemService(Context.WIFI_SERVICE) as WifiManager
        return wm.isWifiEnabled
    }

    fun getManufacture(context: Context?): String {
        return Build.MANUFACTURER
    }

    private val CLOSE_BRODECAST_INTENT_ACTION_NAME = "com.amap.locationservicedemo.CloseService"
    val closeBrodecastIntent: Intent
        get() = Intent(CLOSE_BRODECAST_INTENT_ACTION_NAME)
    val closeServiceFilter: IntentFilter
        get() = IntentFilter(CLOSE_BRODECAST_INTENT_ACTION_NAME)

    class CloseServiceReceiver(var mService: Service?) : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (mService == null) {
                return
            }
            mService!!.onDestroy()
        }
    }
}
