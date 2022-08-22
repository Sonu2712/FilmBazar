package com.film.debugview.core

enum class NetworkErrorCode constructor(
    @JvmField val code: Int
) {
  HTTP_403(403),
  HTTP_404(404),
  HTTP_500(500),
  HTTP_501(501),
  HTTP_503(503),
  HTTP_504(504);

  override fun toString(): String {
    return "HTTP $code"
  }
}
