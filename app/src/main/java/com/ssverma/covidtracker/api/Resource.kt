package com.ssverma.covidtracker.api

class Resource<T> private constructor(
    val status: Status,
    val data: T?,
    val dataSource: DataSource? = null,
    val errorMessage: String?,
    val apiSuccess: ApiSuccess<T>? = null,
    val apiError: ApiError? = null,
    val localError: LocalError? = null,
    val connectionError: ConnectionError? = null,
    val retry: (suspend () -> Unit)? = null
) {

    companion object {
        fun <T> success(data: T?, dataSource: DataSource): Resource<T> {
            return Resource(
                status = Status.SUCCESS,
                data = data,
                dataSource = dataSource,
                errorMessage = null
            )
        }

        fun <T> success(response: ApiSuccess<T>): Resource<T> {
            return Resource(
                status = Status.SUCCESS,
                data = response.data,
                dataSource = DataSource.API,
                errorMessage = null,
                apiSuccess = response
            )
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(
                status = Status.LOADING,
                data = data,
                errorMessage = null
            )
        }

        fun <T> errorLocal(error: LocalError): Resource<T> {
            return Resource(
                status = Status.ERROR_LOCAL,
                data = null,
                errorMessage = error.errorMessage,
                localError = error
            )
        }

        fun <T> errorApi(error: ApiError): Resource<T> {
            return Resource(
                status = Status.ERROR_API,
                data = null,
                errorMessage = error.errorMessage,
                apiError = error,
                retry = error.onRetry
            )
        }

        fun <T> errorConnection(error: ConnectionError): Resource<T> {
            return Resource(
                status = Status.ERROR_CONNECTION,
                data = null,
                errorMessage = error.errorMessage,
                connectionError = error,
                retry = error.onRetry
            )
        }

    }

}

class LocalError private constructor(
    val errorMessage: String
) {
    companion object {
        fun buildError(errorMessage: String): LocalError {
            return LocalError(errorMessage = errorMessage)
        }
    }
}

class ConnectionError private constructor(
    val errorMessage: String,
    val onRetry: suspend () -> Unit
) {
    companion object {
        fun buildError(errorMessage: String, onRetry: suspend (() -> Unit)): ConnectionError {
            return ConnectionError(
                errorMessage = errorMessage,
                onRetry = onRetry
            )
        }
    }
}

fun <F, T> Resource<F>.mapTo(converter: (F?) -> T?): Resource<T?> {
    return when (status) {
        Status.SUCCESS -> Resource.success(converter(data), dataSource!!)
        Status.LOADING -> Resource.loading(converter(data))
        Status.ERROR_LOCAL -> Resource.errorLocal(localError!!)
        Status.ERROR_API -> Resource.errorApi(apiError!!)
        Status.ERROR_CONNECTION -> Resource.errorConnection(connectionError!!)
    }
}