package com.film.commons

class Result<out T> private constructor(
    @JvmField val data: T? = null,
    @JvmField val error: Throwable? = null
) {

  operator fun component1(): T? {
    return data
  }

  operator fun component2(): Throwable? {
    return error
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Result<*>

    if (data != other.data) return false
    if (error != other.error) return false

    return true
  }

  override fun hashCode(): Int {
    var result = data?.hashCode() ?: 0
    result = 31 * result + (error?.hashCode() ?: 0)
    return result
  }

  override fun toString(): String {
    return "Result(data=$data, error=$error)"
  }

  companion object {
    fun <T> empty(error: Throwable = IllegalArgumentException()): Result<T> {
      return Result(error = error)
    }

    fun <T> data(data: T): Result<T> {
      return Result(data = data)
    }
  }
}