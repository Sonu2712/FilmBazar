package com.film.bazar.coredata.util

import com.jakewharton.rxrelay3.PublishRelay
import io.reactivex.rxjava3.core.Observable
import okhttp3.Call
import okhttp3.EventListener
import java.util.concurrent.atomic.AtomicLong

class HttpEventListener(
    val callId: Long,
    val callStartNanos: Long,
    val apiStatsRelay: PublishRelay<Long>
) : EventListener() {

    /* override fun callEnd(call: Call?) {
         apiStatsRelay.accept(System.nanoTime() - callStartNanos)
     }*/
}

object HttpListenerFactory : EventListener.Factory {
    private val nextCallId = AtomicLong(1L)
    private val apiStatsRelay: PublishRelay<Long> = PublishRelay.create()

    fun getApiStatsObservable(): Observable<Long> {
        return apiStatsRelay
    }

    override fun create(call: Call): EventListener {
        val callId = nextCallId.getAndIncrement()
        return if (Math.random() < 0.50) {
            HttpEventListener(callId, System.nanoTime(), apiStatsRelay)
        } else {
            EventListener.NONE
        }
    }
}
