package com.ssverma.covidtracker.ui.country

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.ssverma.covidtracker.data.repository.CovidRepository
import javax.inject.Inject

class CountryViewModel @Inject constructor(
    private val covidRepository: CovidRepository
) : ViewModel() {

    val countriesStats = liveData { emitSource(covidRepository.fetchAllCountriesStats()) }
}