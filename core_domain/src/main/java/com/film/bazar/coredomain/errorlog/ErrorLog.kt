package com.film.bazar.coredomain.errorlog

data class ErrorLog(
    @JvmField val clientCode: String = "",
    @JvmField val userType: String = "",
    @JvmField val osVersion: String = "",
    @JvmField val appVersion: String = "",
    @JvmField val timeStamp:Long = 0L,
    @JvmField val network: String = "",
    @JvmField val batteryPercent: Int = 0,
    @JvmField val freeRam: String = "",
    @JvmField val totalRam: String = "",
    @JvmField val freeDiskInternal : String = "",
    @JvmField val freeDiskExternal : String = "",
    @JvmField val totalDiskInternal : String = "",
    @JvmField val serverLog : ServerLog?= null
)

data class ServerLog(
    @JvmField val deepLink : String,
    @JvmField val apiUrl :String,
    @JvmField val errorMessage : String,
    @JvmField val errorCode :Int = 200
)

data class AppErrorLog(
    @JvmField var sessionId : String,
    @JvmField var errorLogs : List<ErrorLog>
)