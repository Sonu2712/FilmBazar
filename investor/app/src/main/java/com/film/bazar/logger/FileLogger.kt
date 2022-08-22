package com.film.bazar.logger

import android.content.Context
import android.os.Process
import android.util.Log
import com.film.app.core.utils.AppDateFormats
import com.film.bazar.logger.FileZipper.zipDirectory
import io.reactivex.rxjava3.core.Observable
import okio.buffer
import okio.sink
import timber.log.Timber
import java.io.File
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FileLogger @Inject constructor(val context: Context) {

    private val entries: Deque<InvestorLog> = ArrayDeque()

    init {
        Observable.interval(10, TimeUnit.SECONDS)
            .filter { entries.isNotEmpty() }
            .doOnNext {
                saveLogs()
            }
            .subscribe()
    }

    fun saveLogs() {
        writeLogs()
        entries.clear()
    }

    fun tree(): Timber.Tree {
        return object : Timber.DebugTree() {

            override fun createStackElementTag(element: StackTraceElement): String? {
                return element.lineNumber.toString() + ":" + super.createStackElementTag(element)
            }

            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                if (isLoggable(tag, priority)) {
                    entries.addLast(
                        InvestorLog(
                            dateTime = LOG_DATE_FORMAT.format(Date()),
                            level = priority,
                            tag = tag ?: "",
                            message = message
                        )
                    )
                }
            }

            override fun isLoggable(tag: String?, priority: Int): Boolean {
                return priority != Log.VERBOSE && priority != Log.DEBUG // Only log WARN, INFO, ERROR, WTF
            }
        }
    }

    private fun writeLogs() {
        val root = context.filesDir

        val logDir = File(root, "logs") // Logs Root Folder
        if (!logDir.exists()) logDir.mkdir()

        val dayDirName = AppDateFormats.df_Date5.format(Calendar.getInstance().time)
        val dayWise = File(logDir, dayDirName) // Day Wise Folder
        if (!dayWise.exists()) dayWise.mkdir()

        val output = File(dayWise, "${Process.myPid()}.txt")

        try {
            val sink = output.sink(true).buffer()
            for (entry in entries) {
                sink.writeUtf8(entry.prettyPrint()).writeByte('\n'.toInt())
            }
            sink.close()
        } catch (e: Exception) {
            Timber.e("Error Writing Logs to File $e")
        }
    }

    fun cleanUp() {
        val folder = context.filesDir
        if (folder != null) {
            val rootFiles = folder.listFiles() ?: emptyArray()
            for (file in rootFiles) {
                if (file.name.contains("Logs", true)) {
                    if (file.isDirectory) {
                        // For date wise logs
                        val logFiles = file.listFiles() ?: emptyArray()
                        for (log in logFiles) {
                            deleteFiles(log)
                        }
                    } else {
                        // For .zip file
                        deleteFiles(file)
                    }
                }
            }
        }
    }

    private fun deleteFiles(file: File) {
        val difference = Date().time - file.lastModified()
        val days = 14
        if (difference > days * 24 * 60 * 60 * 1000) {
            file.deleteRecursively()
        }
    }

    fun getLogs(logDate: String): String {
        val root = context.filesDir
        val outputFilePath = "${root.path}/logs.zip"
        if (logDate == "All") {
            zipDirectory(sourceFile = "${root.path}/logs", outputFile = outputFilePath)
        } else {
            zipDirectory(sourceFile = "${root.path}/logs/$logDate", outputFile = outputFilePath)
        }
        return outputFilePath
    }

    private fun InvestorLog.prettyPrint(): String {
        return "$dateTime $tag ${message.replace("\\n".toRegex(), "\n")}"
    }

    private val time2 = "yyyy-MM-dd HH:mm:ss.SSS"
    private val LOG_DATE_FORMAT: DateFormat = SimpleDateFormat(time2, Locale.ENGLISH)

    companion object {
        const val LOG_DATE = "LogDate"
    }
}
