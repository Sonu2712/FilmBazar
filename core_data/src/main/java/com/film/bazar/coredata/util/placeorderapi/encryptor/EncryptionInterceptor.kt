package com.film.bazar.coredata.util.placeorderapi.encryptor

import com.film.bazar.coredata.util.placeorderapi.encryptor.ApiEncryptorFactory
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import javax.inject.Inject

class EncryptionInterceptor @Inject constructor(): Interceptor {
  private val encryptorFactory: ApiEncryptorFactory = ApiEncryptorFactory()

  override fun intercept(chain: Chain): Response {
    val encryptor = encryptorFactory.get()

    val request = chain.request()

    val updatedRequest = encryptor.encrypt(request)

    val response = chain.proceed(updatedRequest)

    return encryptor.decrypt(response)
  }
}