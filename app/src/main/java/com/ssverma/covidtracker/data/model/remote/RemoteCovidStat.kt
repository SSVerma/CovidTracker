package com.ssverma.covidtracker.data.model.remote

import com.google.gson.annotations.SerializedName

class RemoteCovidStat(
    @SerializedName("updated")
    val updatedAt: Long,

    @SerializedName("cases")
    val cases: Long,

    @SerializedName("todayCases")
    val todayCases: Int,

    @SerializedName("deaths")
    val deaths: Int,

    @SerializedName("todayDeaths")
    val todayDeaths: Int,

    @SerializedName("recovered")
    val recovered: Int,

    @SerializedName("active")
    val active: Int,

    @SerializedName("critical")
    val critical: Int,

    @SerializedName("casesPerOneMillion")
    val casesPerMillion: Float,

    @SerializedName("deathsPerOneMillion")
    val deathsPerMillion: Float,

    @SerializedName("tests")
    val tests: Long,

    @SerializedName("testsPerOneMillion")
    val testsPerMillion: Float,

    @SerializedName("population")
    val population: Long,

    @SerializedName("activePerOneMillion")
    val activePerMillion: Float,

    @SerializedName("recoveredPerOneMillion")
    val recoveredPerMillion: Float,

    @SerializedName("criticalPerOneMillion")
    val criticalPerMillion: Float,

    @SerializedName("affectedCountries")
    val affectedCountries: Int,

    @SerializedName("country")
    val country: String?,

    @SerializedName("countryInfo")
    val countryInfo: RemoteCountryInfo?

)