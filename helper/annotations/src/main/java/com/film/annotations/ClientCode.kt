package com.film.annotations

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

/**
 * This is supposed to mark a StringPreference, It contains "MGUEST" for Guest and Not Logged In users,
 * else the userCode field received from authorize API is stored
 */
@Retention(RUNTIME)
@Qualifier
annotation class ClientCode
