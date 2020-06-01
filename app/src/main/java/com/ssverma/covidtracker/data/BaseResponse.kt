package com.ssverma.covidtracker.data

import com.google.gson.annotations.SerializedName

class BaseResponse<T>(

    @SerializedName("status")
    val status: String,

    @SerializedName("message")
    val message: String,

    @SerializedName("payload")
    val payload: T?
)