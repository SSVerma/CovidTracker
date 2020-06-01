package com.ssverma.covidtracker.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.ssverma.covidtracker.data.BaseResponse
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class NetworkBoundResource<T> {

    private val resultData = MediatorLiveData<Resource<T>>()

    fun asLiveData(): LiveData<Resource<T>> = resultData

    private val apiData = liveData {
        emit(Resource.loading())

        val remoteResource = makeApiRequest()
        emit(remoteResource)
    }

    init {
        resultData.addSource(apiData) {
            resultData.value = it
        }
    }

    private suspend fun makeApiRequest(): Resource<T> {
        try {

            val apiResponse = createCall()

            if (!apiResponse.isSuccessful) {
                return Resource.errorApi(
                    ApiError.build(
                        errorMessage = prepareErrorMessage(apiResponse),
                        httpCode = apiResponse.code(),
                        headers = apiResponse.headers(),
                        onRetry = {
                            onApiRequestRetry()
                        }
                    )
                )
            }

            if (apiResponse.body() == null) {
                return Resource.errorApi(
                    ApiError.build(
                        errorMessage = "No content available, Please try again later",
                        httpCode = apiResponse.code(),
                        headers = apiResponse.headers(),
                        onRetry = {
                            onApiRequestRetry()
                        }
                    )
                )
            }

            return Resource.success(
                ApiSuccess.build(
                    data = apiResponse.body()!!,
                    httpCode = apiResponse.code(),
                    headers = apiResponse.headers()
                )
            )

        } catch (e: Exception) {
            e.printStackTrace()
            return buildResourceError(e)
        }
    }

    private suspend fun onApiRequestRetry() {
        resultData.value = Resource.loading()
        val remoteResource = makeApiRequest()
        resultData.value = remoteResource
    }

    private fun buildResourceError(e: Exception): Resource<T> {
        when (e) {
            is HttpException -> {
                return Resource.errorApi(
                    ApiError.build(
                        errorMessage = prepareErrorMessage(e.response()),
                        httpCode = e.code(),
                        headers = e.response()?.headers(),
                        onRetry = {
                            onApiRequestRetry()
                        }
                    )
                )
            }
            is ConnectException, is SocketException, is SocketTimeoutException, is UnknownHostException -> {
                return Resource.errorConnection(
                    ConnectionError.buildError(
                        errorMessage = "Connection error",
                        onRetry = {
                            onApiRequestRetry()
                        }
                    )
                )
            }
        }

        return Resource.errorApi(
            ApiError.build(
                errorMessage = "Unknown error",
                httpCode = -1,
                headers = null,
                onRetry = {
                    onApiRequestRetry()
                }
            )
        )
    }

    private fun prepareErrorMessage(response: Response<*>?): String {
        val unknownError = "Unknown error"

        response?.errorBody()?.string()?.let {

            return try {
                val baseResponse = Gson().fromJson(it, BaseResponse::class.java)
                baseResponse.message
            } catch (e: Exception) {
                unknownError
            }

        } ?: return unknownError

    }

    abstract suspend fun createCall(): Response<T>
}