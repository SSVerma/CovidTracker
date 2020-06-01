package com.ssverma.covidtracker.api

import okhttp3.Headers

abstract class ApiResponse(
    val httpCode: Int,
    val headers: Headers?
)

class ApiSuccess<T> private constructor(
    val data: T?,
    httpCode: Int,
    headers: Headers?
) : ApiResponse(httpCode = httpCode, headers = headers) {

    companion object {
        fun <T> build(data: T, httpCode: Int, headers: Headers? = null): ApiSuccess<T> {
            return ApiSuccess(
                data = data,
                httpCode = httpCode,
                headers = headers
            )
        }
    }
}

class ApiError private constructor(
    val errorMessage: String,
    val errorPayload: Any?,
    httpCode: Int,
    headers: Headers?,
    val onRetry: suspend () -> Unit
) : ApiResponse(httpCode = httpCode, headers = headers) {

    companion object {
        fun build(
            errorMessage: String,
            errorPayload: Any? = null,
            httpCode: Int,
            headers: Headers? = null,
            onRetry: suspend () -> Unit
        ): ApiError {
            return ApiError(
                errorMessage = errorMessage,
                errorPayload = errorPayload,
                httpCode = httpCode,
                headers = headers,
                onRetry = onRetry
            )
        }
    }
}