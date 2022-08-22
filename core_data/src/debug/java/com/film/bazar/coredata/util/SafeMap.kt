package com.film.bazar.coredata.util

inline fun <T, R : Any> Iterable<T>.safeMap(transform: (T) -> R?): List<R> {
    return mapNotNullTo(ArrayList(), transform)
}

inline fun <T, R : Any, C : MutableCollection<in R>> Iterable<T>.safeMapTo(
    destination: C,
    transform: (T) -> R?
): C {
    return mapNotNullTo(destination, transform)
}
