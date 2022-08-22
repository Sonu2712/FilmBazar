package com.film.bazar.coredata.util.placeorderapi.sign

import okio.Buffer

private val EMPTY_HASH = createEmptyPayloadContentHash()

/**
 * The hash returns the following value: a3ab6abebdd0883a2f98d79b1384a841a50f867fca48b701e9f475f51b06a641
 *
 * @return hash of the empty payload
 */
fun getEmptyPayloadContentHash(): String {
    return EMPTY_HASH
}

private fun createEmptyPayloadContentHash(): String {
    return Buffer().writeUtf8("zero-body").sha256().hex()
}