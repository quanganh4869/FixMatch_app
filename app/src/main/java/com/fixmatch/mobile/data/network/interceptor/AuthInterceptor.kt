package com.fixmatch.mobile.data.network.interceptor

import com.fixmatch.mobile.data.settings.TokenManager
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenManager: TokenManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // Skip auth headers for public endpoints if needed, but let's assume all need it for now 
        // or we check a custom header @Headers("No-Authentication: true")
        val noAuth = request.header("No-Authentication") != null
        if (noAuth) {
            return chain.proceed(request)
        }

        val token = runBlocking {
            tokenManager.accessToken.firstOrNull()
        }

        if (token != null) {
            val authenticatedRequest = request.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
            
            val response = chain.proceed(authenticatedRequest)

            // Handle token refresh logic here (e.g. if response.code == 401)
            // For MVP, we pass it down and let the repository/ViewModel handle session expiry.
            return response
        }

        return chain.proceed(request)
    }
}
