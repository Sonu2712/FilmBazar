package com.film.bazar.coredata.util.interceptors

import com.film.app.core.exception.ServiceFailureException
import com.film.bazar.appuser.repository.UserManager
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

// hello
class AuthorizationInterceptor @Inject constructor(
    private val userManager: UserManager/*,
    private val tokenManager: Lazy<TokenManager>*/
) : Interceptor {
    //private val tokenProvider: TokenManager by lazy { tokenManager.get() }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val builder = original.newBuilder()

        when {
            original.header(NO_AUTH) != null -> builder.removeHeader(NO_AUTH)
            userManager.isLoggedIn() -> builder.header(
                API_HEADER_AUTHORIZATION,
                String.format("Bearer %s", "tokenProvider.getAuthorizationToken().blockingGet()")
            )
        }

        val response = chain.proceed(builder.build())

        val code  = response.code
        if (code == 401) {
            if (response.headers.get(API_HEADER_Logout)?.equals("ReLoginRequired") == true) {
                //tokenProvider.logoutNeeded()
                throw ServiceFailureException("PasswordChanged","Your password has been changed, Please login again")
            } else {
                when (response.request.url.pathSegments.last()) {
                    /*Authorize -> tokenProvider.logoutNeeded()
                    Token -> tokenProvider.refreshTokenExpired()
                    Logout -> {
                    }
                    else -> tokenProvider.expired()*/
                }
            }
        }

        return response
    }

    companion object {
        const val NO_AUTH = "No-Auth"
        private const val API_HEADER_AUTHORIZATION = "Authorization"
        private const val API_HEADER_Logout = "WWW-Authenticate"
    }
}