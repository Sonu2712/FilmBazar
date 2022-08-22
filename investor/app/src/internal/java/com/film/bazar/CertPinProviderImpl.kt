package com.film.bazar

import com.film.bazar.coredata.util.CertPinProvider
import okhttp3.CertificatePinner

object CertPinProviderImpl : CertPinProvider {
    override fun getPinner(): CertificatePinner? {
        return null
    }
}
