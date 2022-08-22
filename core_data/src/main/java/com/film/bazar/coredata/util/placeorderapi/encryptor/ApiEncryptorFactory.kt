package com.film.bazar.coredata.util.placeorderapi.encryptor

import com.film.bazar.coredata.util.placeorderapi.encryptor.ApiEncryptor.Companion.NO_ENCRYPTION_SCHEME
import com.film.bazar.coredata.util.placeorderapi.encryptor.ApiEncryptor.Companion.ProtectedByHeader
import okhttp3.Request
import okhttp3.Response

class ApiEncryptorFactory(val enabled: Boolean = true) {
    private val rsaEncryptor = RsaEncryptor()

    fun get(): ApiEncryptor {
        return if (enabled) {
            AesRsaEncryptor(rsaEncryptor)
        } else {
            NoEncryption
        }
    }
}

interface ApiEncryptor {
    fun encrypt(request: Request): Request
    fun decrypt(response: Response): Response

    companion object {
        const val ProtectedByHeader = "x-protected-by"
        const val AES_RSA_ENCRYPTION_SCHEME = "MOFSL-AES-RSA"
        const val NO_ENCRYPTION_SCHEME = "MOFSL-NONE"
    }
}

object NoEncryption : ApiEncryptor {
    override fun encrypt(request: Request): Request {
        return request.newBuilder()
            .header(ProtectedByHeader, NO_ENCRYPTION_SCHEME)
            .build()
    }

    override fun decrypt(response: Response): Response {
        return response
    }
}
