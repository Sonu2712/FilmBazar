package com.film.bazar.coredata.util

import okhttp3.CertificatePinner

interface CertPinProvider {
    fun getPinner(): CertificatePinner?
}
