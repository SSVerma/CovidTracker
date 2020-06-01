package com.ssverma.covidtracker.data.repository

import androidx.lifecycle.LiveData
import com.ssverma.covidtracker.api.NetworkBoundResource
import com.ssverma.covidtracker.api.NovelCovidApiEndPoints
import com.ssverma.covidtracker.data.model.remote.RemoteCovidStat
import com.ssverma.covidtracker.api.Resource
import retrofit2.Response
import javax.inject.Inject

class CovidRepository @Inject constructor(
    private val apiEndPoints: NovelCovidApiEndPoints
) {

    suspend fun fetchGlobalStats(): LiveData<Resource<RemoteCovidStat>> {
        return object : NetworkBoundResource<RemoteCovidStat>() {
            override suspend fun createCall(): Response<RemoteCovidStat> {
                return apiEndPoints.getGlobalStat()
            }
        }.asLiveData()
    }

    suspend fun fetchCountryStats(countryIso2: String): LiveData<Resource<RemoteCovidStat>> {
        return object : NetworkBoundResource<RemoteCovidStat>() {
            override suspend fun createCall(): Response<RemoteCovidStat> {
                return apiEndPoints.getCountryStat(countryIso2)
            }
        }.asLiveData()
    }

    suspend fun fetchAllCountriesStats(): LiveData<Resource<List<RemoteCovidStat>?>> {
        return object : NetworkBoundResource<List<RemoteCovidStat>?>() {
            override suspend fun createCall(): Response<List<RemoteCovidStat>?> {
                return apiEndPoints.getAllCountriesStats()
            }
        }.asLiveData()
    }
}