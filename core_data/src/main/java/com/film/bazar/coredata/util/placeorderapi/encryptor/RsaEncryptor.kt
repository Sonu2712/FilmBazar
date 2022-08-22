package com.film.bazar.coredata.util.placeorderapi.encryptor

import android.util.Base64
import java.security.KeyFactory
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

class RsaEncryptor(
    publicKey: String = PUBLIC_KEY
) {
  private val cipher: Cipher

  init {
    val keyFactory = KeyFactory.getInstance("RSA")
    val publicKeyBytes = Base64.decode(publicKey, Base64.DEFAULT)
    val publicKey = keyFactory.generatePublic(X509EncodedKeySpec(publicKeyBytes))

    cipher = Cipher.getInstance(TRANSFORMATION_ASYMMETRIC)
    cipher.init(Cipher.ENCRYPT_MODE, publicKey)
  }

  fun encrypt(input: String, base64: Boolean = false): String {
    val encryptedBytes = if (base64) {
      encrypt(Base64.decode(input, Base64.DEFAULT))
    } else {
      encrypt(input.toByteArray())
    }
    return Base64.encodeToString(encryptedBytes, Base64.NO_WRAP)
  }

  fun encryptWithBase64(input: ByteArray): String {
    return Base64.encodeToString(encrypt(input), Base64.NO_WRAP)
  }

  fun encrypt(input: ByteArray): ByteArray {
    return cipher.doFinal(input)
  }

  companion object {
    const val PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwI/AxyuubOzZslNJwm/uDurDTuZGUvrN7jUYZurlkzIGGqvKUuw8yhtBirMbEH722VJ30/ZMFeZuWzSliYLD4ZOfza6oSXZT+A1mMI2aQW94R554QNKp+hBNNjCd4vbs+3Gtzod41fNcpIMwGECOYN9gPdv8hrXyRcEpsrooa8sU+rq2ZPvTsEXrJlWXbv2FaDwYroyV4UrF1LVcFIu7TeUw2okbhvNskU7TzK8UQlCawLLzPQgvJJ3XXVFn8mqm5QfCbxuwPCf4ck3JbEslpgBL2nhGgNmd5k+aPgQKeTrttsDKI0MJrwFef999j8vH4/BK5PImKArFtpIiFyqUYQIDAQAB"
    const val TRANSFORMATION_ASYMMETRIC = "RSA/ECB/PKCS1Padding"
  }
}