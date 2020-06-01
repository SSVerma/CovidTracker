package com.ssverma.covidtracker.data.model.remote

import com.google.gson.annotations.SerializedName

class RemoteCountryInfo(
    @SerializedName("flag")
    val flag: String?,

    @SerializedName("iso3")
    val iso3: String?
)