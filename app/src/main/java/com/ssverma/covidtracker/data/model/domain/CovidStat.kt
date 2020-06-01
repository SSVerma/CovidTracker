package com.ssverma.covidtracker.data.model.domain

import androidx.recyclerview.widget.DiffUtil
import com.ssverma.covidtracker.data.model.remote.RemoteCovidStat
import com.ssverma.covidtracker.util.DateHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class CovidStat(
    val updatedAt: String,
    val cases: Long,
    val displayCases: String,
    val todayCases: Int,
    val displayTodayCases: String,
    val deaths: Int,
    val displayDeaths: String,
    val todayDeaths: Int,
    val displayTodayDeaths: String,
    val recovered: Int,
    val displayRecovered: String,
    val active: Int,
    val displayActive: String,
    val critical: Int,
    val displayCritical: String,
    val tests: Long,
    val displayTests: String,
    val population: Long,
    val displayPopulation: String,
    val countryName: String?,
    val countryIso3: String,
    val countryFlagUrl: String
) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CovidStat>() {
            override fun areItemsTheSame(oldItem: CovidStat, newItem: CovidStat): Boolean {
                return oldItem.cases == newItem.cases && oldItem.countryName == newItem.countryName
            }

            override fun areContentsTheSame(oldItem: CovidStat, newItem: CovidStat): Boolean {
                return oldItem == newItem
            }
        }
    }
}


fun RemoteCovidStat.toDomain(): CovidStat {
    return CovidStat(
        updatedAt = DateHelper.dateObjToApiDateStr(DateHelper.millisToDate(updatedAt)),
        cases = cases,
        displayCases = String.format("%,d", cases),
        todayCases = todayCases,
        displayTodayCases = String.format("%,d", todayCases),
        deaths = deaths,
        displayDeaths = String.format("%,d", deaths),
        todayDeaths = todayDeaths,
        displayTodayDeaths = String.format("%,d", todayDeaths),
        recovered = recovered,
        displayRecovered = String.format("%,d", recovered),
        active = active,
        displayActive = String.format("%,d", active),
        critical = critical,
        displayCritical = String.format("%,d", critical),
        tests = tests,
        displayTests = String.format("%,d", tests),
        population = population,
        displayPopulation = String.format("%,d", population),
        countryName = country ?: "",
        countryIso3 = countryInfo?.iso3 ?: "",
        countryFlagUrl = countryInfo?.flag ?: ""
    )
}

suspend fun List<RemoteCovidStat>.toDomain(): List<CovidStat> {
    return withContext(Dispatchers.Default) {
        map {
            it.toDomain()
        }
    }
}