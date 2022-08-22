package com.film.app.core.utils

import android.util.Base64
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.security.GeneralSecurityException
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.text.Charsets.UTF_8

object Encryptor {
  const val cipherTransformation = "AES/CBC/PKCS5Padding"
  const val aesEncryptionAlgorithm = "AES"

  @Throws(NoSuchAlgorithmException::class, NoSuchPaddingException::class, InvalidKeyException::class, InvalidAlgorithmParameterException::class, IllegalBlockSizeException::class, BadPaddingException::class)
  private fun decrypt(cipherText: ByteArray, key: ByteArray, initialVector: ByteArray): ByteArray {
    val cipher = Cipher.getInstance(cipherTransformation)
    val secretKeySpecy = SecretKeySpec(key, aesEncryptionAlgorithm)
    val ivParameterSpec = IvParameterSpec(initialVector)
    cipher.init(Cipher.DECRYPT_MODE, secretKeySpecy, ivParameterSpec)
    return cipher.doFinal(cipherText)
  }

  @Throws(NoSuchAlgorithmException::class, NoSuchPaddingException::class, InvalidKeyException::class, InvalidAlgorithmParameterException::class, IllegalBlockSizeException::class, BadPaddingException::class)
  private fun encrypt(plainText: ByteArray, key: ByteArray, initialVector: ByteArray): ByteArray {
    val cipher = Cipher.getInstance(cipherTransformation)
    val secretKeySpec = SecretKeySpec(key, aesEncryptionAlgorithm)
    val ivParameterSpec = IvParameterSpec(initialVector)
    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec)
    return cipher.doFinal(plainText)
  }

  @Throws(UnsupportedEncodingException::class)
  private fun getKeyBytes(key: String): ByteArray {
    val keyBytes = ByteArray(16)
    val parameterKeyBytes = key.toByteArray(UTF_8)
    System.arraycopy(parameterKeyBytes, 0, keyBytes, 0,
        Math.min(parameterKeyBytes.size, keyBytes.size))
    return keyBytes
  }

  @Throws(UnsupportedEncodingException::class, InvalidKeyException::class, NoSuchAlgorithmException::class, NoSuchPaddingException::class, InvalidAlgorithmParameterException::class, IllegalBlockSizeException::class, BadPaddingException::class)
  fun encrypt(plainText: String, key: String = "mosl"): String {
    val plainTextbytes = plainText.toByteArray(UTF_8)
    val keyBytes = getKeyBytes(key)
    return Base64.encodeToString(encrypt(plainTextbytes, keyBytes, keyBytes), Base64.DEFAULT)
  }

  @Throws(GeneralSecurityException::class, IOException::class)
  fun decrypt(encryptedText: String, key: String = "mosl"): String {
    val cipheredBytes = Base64.decode(encryptedText, Base64.DEFAULT)
    val keyBytes = getKeyBytes(key)
    return String(decrypt(cipheredBytes, keyBytes, keyBytes), UTF_8)
  }
}
