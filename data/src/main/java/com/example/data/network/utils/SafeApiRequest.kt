package com.example.data.network.utils

import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest {
    suspend fun <T : Any> safeApiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke()
        if (response.isSuccessful)
            return response.body()!!
        else {
            val responseErr = response.errorBody()?.string()
            val message = StringBuilder()
            responseErr.let {
                try {
                    message.append(it?.let { it1 -> JSONObject(it1).getString("error") })
                } catch (e: JSONException) {
                    ApiException(e.message.toString())
                }
            }
            Log.d("TAG", "safeApiRequest,${message.toString()}")
            throw ApiException(message.toString())
        }
    }
}