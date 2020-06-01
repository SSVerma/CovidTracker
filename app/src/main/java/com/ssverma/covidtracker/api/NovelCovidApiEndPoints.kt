package com.ssverma.covidtracker.api

import com.ssverma.covidtracker.data.model.remote.RemoteCovidStat
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NovelCovidApiEndPoints {

    @GET("all")
    suspend fun getGlobalStat(): Response<RemoteCovidStat>

    @GET("countries/{countryIso2}")
    suspend fun getCountryStat(
        @Path("countryIso2") countryIso2: String
    ): Response<RemoteCovidStat>

    @GET("countries/")
    suspend fun getAllCountriesStats(): Response<List<RemoteCovidStat>?>

}