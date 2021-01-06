# Polestar 车机 service 保活 demo

1. 申请忽略电池优化权限和开机自启动权限，以用于保活：


```
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
    if (!isIgnoringBatteryOptimizations()){
        requestIgnoreBatteryOptimizations()
    }
}

//申请自启动权限，目前只适配了小米，可以达到保活效果
showActivity(
    "com.miui.securitycenter",
    "com.miui.permcenter.autostart.AutoStartManagementActivity"
)
```

2. 监听广播用于自启动：

```
Intent.ACTION_SCREEN_OFF
Intent.ACTION_SCREEN_ON
Intent.ACTION_BOOT_COMPLETED
```

3. LocalService 后台长期运行，每隔十秒获取一次当前位置信息并打印log
