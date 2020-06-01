package com.ssverma.covidtracker.ui.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.ssverma.covidtracker.data.repository.CovidRepository
import com.ssverma.covidtracker.util.CoreUtil
import javax.inject.Inject

class StatsViewModel @Inject constructor(
    private val covidRepository: CovidRepository
) : ViewModel() {

    val globalStats = liveData { emitSource(covidRepository.fetchGlobalStats()) }

    val myCountryStats =
        liveData { emitSource(covidRepository.fetchCountryStats(CoreUtil.getCountryIso2())) }
}