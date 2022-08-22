package com.film.bazar.coredata.util.placeorderapi.sign

import okhttp3.Headers
import okhttp3.HttpUrl
import okhttp3.Request
import okhttp3.RequestBody
import okio.Buffer
import okio.ByteString
import okio.ByteString.Companion.encode
import java.net.URLEncoder
import java.util.*

object AllHeaders {
    const val SigningKeyHeader = "X-Signature"
    const val DateHeader = "X-Mofsl-Date"
    const val RequestIdHeader = "Request-Id"
}

internal const val SIGNING_ALGORITHM = "MOFSL-HMAC-SHA256"
internal const val RequestScopeStr = "mofsl_request"
internal val RequestScope: ByteString = RequestScopeStr.encode()

internal fun Request.getSigningHeader(userId: String, appId: String): String {
    return "$SIGNING_ALGORITHM Credential=$userId/${credentialScope()}, " +
            "SignedHeaders=${headers.signed()}, " +
            "Signature=${signature(appId)}"
}

/**
 * Calculate the signature according to [this](https://docs.aws.amazon.com/general/latest/gr/sigv4-calculate-signature.html)
 */
internal fun Request.signature(appId: String): String {
    val firstInput = dateHeaderShort().encode()
    val firstKey = "MOFSL$appId".encode()

    val secondKey = firstInput.hmacSha256(firstKey)

    val thirdKey = RequestScope.hmacSha256(secondKey)

    val stringToSign = stringToSign()
    return stringToSign.hmacSha256(thirdKey).hex()
}

/**
 * Create a string to sign as described [here](https://docs.aws.amazon.com/general/latest/gr/sigv4-create-string-to-sign.html)
 */
internal fun Request.stringToSign(): ByteString {
    val canonicalRequest = canonicalRequest().encode()
    return """
    |$SIGNING_ALGORITHM
    |${dateHeader()}
    |${credentialScope()}
    |${canonicalRequest.sha256().hex()}
    """.trimMargin("|")
        .encode()
}

/**
 * Create a canonical request as described [here](https://docs.aws.amazon.com/general/latest/gr/sigv4-create-canonical-request.html)
 */
internal fun Request.canonicalRequest(): String {
    return """
    |${method}
    |${url.canonicalPath()}
    |${url.canonicalQueryString()}
    |${headers.canonical()}
    |
    |${headers.signed()}
    |${body.bodyDigest()}
    """.trimMargin("|")
}

private fun RequestBody?.bodyDigest(): String {
    this ?: return getEmptyPayloadContentHash()
    val sink = Buffer()
    this.writeTo(sink)
    return sink.sha256().hex()
}

private fun HttpUrl.canonicalPath(): String {
    return encodedPath.replace(Regex("/+"), "/")
}

private fun HttpUrl.canonicalQueryString(): String {
    return queryParameterNames.sorted()
        .takeIf { it.isNotEmpty() }
        ?.flatMap { name ->
            queryParameterValues(name)
                .filterNotNull()
                .sorted()
                .map { value ->
                    Pair(name.rfc3986Encode(), value.rfc3986Encode())
                }
        }
        ?.joinToString("&") { (name, value) ->
            "$name=$value"
        }
        ?: ""
}

private fun Headers.signed(): String {
    return names()
        .joinToString(";") {
            it.trim().toLowerCase(Locale.ENGLISH)
        }
}

private fun Headers.canonical(): String {
    return names()
        .joinToString("\n") {
            "${it.trim().toLowerCase(Locale.ENGLISH)}:${values(it).trimmedAndJoined()}"
        }
}

/**
 * Trims all the values and joins them with commas
 */
private fun List<String>.trimmedAndJoined(): String {
    return joinToString(",") { it.trimAll() }
}

/**
 * Trims the trailing and leading spaces and replaces multiple spaces for only one
 */
private fun String.trimAll(): String {
    return trim()
        .replace(Regex("\\s+"), " ")
}

/**
 * Encode the given string with RFC3986
 */
private fun String.rfc3986Encode(): String {
    return URLEncoder.encode(this, "utf8")
        .replace("+", "%20")
        .replace("*", "%2A")
        .replace("%7E", "~")
}

private fun Request.credentialScope(): String {
    return "${dateHeaderShort()}/$RequestScopeStr"
}

/**
 * Get the header with only date.
 *
 * @throws NoSuchFieldException When header is not found
 */
private fun Request.dateHeaderShort(): String {
    return dateHeader().substring(0..7)
}

/**
 * Get the header with date.
 *
 * @throws NoSuchFieldException When header is not found
 */
private fun Request.dateHeader(): String {
    return header(AllHeaders.DateHeader)
        ?: throw NoSuchFieldException("Request cannot be signed without having the $AllHeaders.DateHeader header")
}
