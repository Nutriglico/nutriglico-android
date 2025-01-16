package com.fiap.startupone.nutriglico.commons.network

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import java.nio.charset.StandardCharsets
import kotlin.system.measureTimeMillis

class LoggingInterceptor : Interceptor {
    private val gson = GsonBuilder().setPrettyPrinting().create()
    private val jsonParser = JsonParser()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // Log request details
        val requestBodyString = requestBodyToString(request)
        val requestLog = mapOf(
            "Method" to request.method,
            "URL" to request.url.toString(),
            "Headers" to request.headers.toString(),
            "Body" to requestBodyString,
            "Content-Length" to request.body?.contentLength().toString()
        )
        Log.d("API Request", gson.toJson(requestLog))

        var responseTime: Long = 0
        val response = chain.proceed(request).also {
            responseTime = measureTimeMillis {
                chain.proceed(request)
            }
        }

        // Log response details
        val responseBody = response.body
        val responseBodyString = responseBody?.string() ?: "null"
        val responseLog = mutableMapOf(
            "Code" to response.code,
            "Message" to response.message,
            "Headers" to response.headers.toString(),
            "Content-Length" to responseBody?.contentLength().toString(),
            "Response-Time" to "$responseTime ms"
        )

        // Try to parse the response body as JSON
        try {
            val jsonElement = jsonParser.parse(responseBodyString)
            responseLog["Body"] = if (jsonElement.isJsonObject) {
                jsonElement.asJsonObject.toString()
            } else {
                responseBodyString
            }
        } catch (e: JsonSyntaxException) {
            responseLog["Body"] = responseBodyString
        }

        Log.d("API Response", gson.toJson(responseLog))

        // Create a new response with the logged body
        return response.newBuilder()
            .body(responseBodyString.toResponseBody(responseBody?.contentType()))
            .build()
    }

    private fun requestBodyToString(request: okhttp3.Request): String {
        return try {
            val buffer = Buffer()
            request.body?.writeTo(buffer)
            buffer.readString(StandardCharsets.UTF_8)
        } catch (e: Exception) {
            "Could not log request body"
        }
    }
}