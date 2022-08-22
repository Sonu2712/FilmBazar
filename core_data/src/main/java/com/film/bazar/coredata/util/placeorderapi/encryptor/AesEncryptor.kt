package com.film.bazar.coredata.util.placeorderapi.encryptor

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class AesEncryptor(private val key: ByteArray) {
  private val secretKeySpec = SecretKeySpec(key, aesEncryptionAlgorithm)
  private val ivSpec = IvParameterSpec(key)

  fun encrypt(plainBytes: ByteArray): ByteArray {
    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, IvParameterSpec(key))
    return cipher.doFinal(plainBytes)
  }

  fun encryptAndBase64Encode(plainInput: ByteArray): String {
    val encryptedBytes = encrypt(plainInput)
    return Base64.encodeToString(encryptedBytes, Base64.NO_WRAP)
  }

  fun decrypt(cipherBytes: ByteArray): ByteArray {
    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec)
    return cipher.doFinal(cipherBytes)
  }

  fun base64DecodeAndDecrypt(input: ByteArray): ByteArray {
    val cipheredBytes = Base64.decode(input, Base64.DEFAULT)
    return decrypt(cipheredBytes)
  }

  companion object {
    private const val AESCipherTransformation = "AES/CBC/PKCS5Padding"
    private const val aesEncryptionAlgorithm = "AES"
    private val cipher = Cipher.getInstance(AESCipherTransformation)
  }
}