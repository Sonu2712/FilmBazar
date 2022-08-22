package com.film.annotations

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

/**
 * This is supposed to mark a StringPreference, It contains the "userCode"
 * field received in the /authorize API.
 * It can be clientCode or userName based on the type of User Login.
 */
@Retention(RUNTIME)
@Qualifier
annotation class UserIdentity