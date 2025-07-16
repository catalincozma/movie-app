package com.catalincozma.data.remote.interceptor

import com.catalincozma.data.NetworkConstants
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestWithApiKey = chain.request().newBuilder()
            .url(
                chain.request().url().newBuilder()
                    .addQueryParameter("api_key", NetworkConstants.API_KEY)
                    .build()
            )
            .build()
        return chain.proceed(requestWithApiKey)
    }
}
