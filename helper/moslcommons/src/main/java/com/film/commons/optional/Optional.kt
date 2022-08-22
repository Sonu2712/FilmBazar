package com.film.commons.optional

class Optional<out T> private constructor(private val value: T?) {

  fun isPresent() = value != null

  fun get(): T = value!!

  fun getOrNull(): T? = value

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Optional<*>

    if (value != other.value) return false

    return true
  }

  override fun hashCode(): Int {
    return value?.hashCode() ?: 0
  }

  companion object {
    internal val EMPTY: Optional<*> = Optional(null)

    @Suppress("UNCHECKED_CAST")
    fun <T> empty(): Optional<T> {
      return EMPTY as Optional<T>
    }

    fun <T> of(value: T): Optional<T> {
      return Optional(value)
    }

    fun <T> ofNullable(value: T?): Optional<T> {
      return if (value == null) empty() else of(value)
    }
  }
}