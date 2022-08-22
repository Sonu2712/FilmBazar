package com.film.bazar.appuser.meta

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Retention(RUNTIME)
@Qualifier
internal annotation class UserPreference
