package com.film.commons.rx

import io.reactivex.rxjava3.core.Scheduler

data class Dispatchers(
    @JvmField val io: Scheduler,
    @JvmField val computation: Scheduler,
    @JvmField val main: Scheduler,
    @JvmField val db: Scheduler
)