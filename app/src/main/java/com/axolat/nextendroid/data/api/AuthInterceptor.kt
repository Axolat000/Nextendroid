package com.axolat.nextendroid.data.api

import com.axolat.nextendroid.data.repository.SessionManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val sessionManager: SessionManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = sessionManager.getToken()

        val newRequest = if (!token.isNullOrEmpty()) {
            originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
                .header("User-Agent", "Nextendroid/1.0 (Android)")
                .build()
        } else {
            originalRequest.newBuilder()
                .header("User-Agent", "Nextendroid/1.0 (Android)")
                .build()
        }

        return chain.proceed(newRequest)
    }
}
