package com.example.data.network.di

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class LoginInterceptor(val isValid: Boolean) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        Log.d(
            "LoginInterceptor",
            "Sending request to ${originalRequest.url} with headers ${originalRequest.headers}"
        )
        val modifiedRequest: Request

        if (isValid) {
            modifiedRequest = originalRequest.newBuilder()
                .addHeader(
                    "Authorization",
                    "Bearer YOUR_TOKEN_HERE"
                ) // Replace with your token logic
                .build()
        } else {
            modifiedRequest = originalRequest.newBuilder()
                .build()
        }
        val response = chain.proceed(modifiedRequest)
        Log.d(
            "LoginInterceptor",
            "Received response from ${response.request.url} with status code ${response.code}"
        )
        return response
    }
}