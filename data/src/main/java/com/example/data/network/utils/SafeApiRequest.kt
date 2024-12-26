package com.example.data.network.utils

import retrofit2.Response

abstract class SafeApiRequest {
    suspend fun <T : Any> safeApiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke()
        if (response.isSuccessful)
            return response.body()!!
        else {
            val error = response.errorBody()?.string() ?: "Unknown error"
            throw ApiException(error)
        }
    }
}