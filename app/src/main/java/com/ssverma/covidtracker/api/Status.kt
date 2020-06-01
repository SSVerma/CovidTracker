package com.ssverma.covidtracker.api

enum class Status {
    SUCCESS,
    LOADING,
    ERROR_LOCAL,
    ERROR_API,
    ERROR_CONNECTION
}

enum class DataSource {
    LOCAL,
    API
}