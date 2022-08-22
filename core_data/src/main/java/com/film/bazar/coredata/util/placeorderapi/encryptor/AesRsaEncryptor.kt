package com.film.bazar.coredata.util.placeorderapi.encryptor

import com.film.bazar.coredata.util.placeorderapi.encryptor.ApiEncryptor.Companion.AES_RSA_ENCRYPTION_SCHEME
import com.film.bazar.coredata.util.placeorderapi.encryptor.ApiEncryptor.Companion.ProtectedByHeader
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import java.security.SecureRandom

class AesRsaEncryptor(
    val rsaEncryptor: RsaEncryptor,
    val aesKey: ByteArray = generateAesKey()
) : ApiEncryptor {
    val encryptor = AesEncryptor(aesKey)

    override fun encrypt(request: Request): Request {
        val newBuilder = request.newBuilder()

        val body = request.body
        if (body != null) {
            val originalMediaType = body.contentType()
            val buffer = Buffer()
            body.writeTo(buffer)

            val encryptedBody = encryptor.encrypt(buffer.readByteArray())
                .toRequestBody(originalMediaType)

            newBuilder.method(request.method, encryptedBody)
        }

        val keyId = "001"
        return newBuilder
            .header(ProtectedByHeader,
                "$AES_RSA_ENCRYPTION_SCHEME KeyId=${keyId}, " +
                        "Key=${rsaEncryptor.encryptWithBase64(aesKey)}")
            .build()
    }

    override fun decrypt(response: Response): Response {
        val body = response.body
        if (body != null && response.hasSuccessBody()) {
            val decryptedBody = encryptor.decrypt(body.bytes())
                .toResponseBody(body.contentType())

            return response.newBuilder()
                .body(decryptedBody)
                .build()
        }

        return response
    }

    private fun Response.hasSuccessBody(): Boolean {
        return header("Api-Error", null) == null
    }

    companion object {

        internal fun generateAesKey(): ByteArray {
            return ByteArray(16).apply {
                SecureRandom().nextBytes(this)
            }
        }
    }
}