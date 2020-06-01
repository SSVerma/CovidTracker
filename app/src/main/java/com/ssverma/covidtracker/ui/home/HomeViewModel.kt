package com.ssverma.covidtracker.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ssverma.covidtracker.CovidTrackerApplication
import com.ssverma.covidtracker.R
import com.ssverma.covidtracker.data.model.domain.Prevention
import com.ssverma.covidtracker.data.model.domain.Symptom
import com.ssverma.covidtracker.data.repository.CovidRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    app: Application,
    private val covidRepository: CovidRepository
) : AndroidViewModel(app) {


    val symptoms = listOf(
        Symptom(
            title = getApplication<CovidTrackerApplication>().getString(R.string.most_common_symptom_label),
            description = getApplication<CovidTrackerApplication>().getString(R.string.most_common_symptoms),
            illustration = R.drawable.symptom_illustration
        ),
        Symptom(
            title = getApplication<CovidTrackerApplication>().getString(R.string.less_common_symptom_label),
            description = getApplication<CovidTrackerApplication>().getString(R.string.less_common_symptoms),
            illustration = R.drawable.symptom_illustration
        ),
        Symptom(
            title = getApplication<CovidTrackerApplication>().getString(R.string.serious_symptom_label),
            description = getApplication<CovidTrackerApplication>().getString(R.string.serious_symptoms),
            illustration = R.drawable.symptom_illustration
        )
    )

    val preventions = listOf(
        Prevention(
            title = R.string.prevention_stay_at_home,
            imageId = R.drawable.ic_stay_at_home
        ),
        Prevention(
            title = R.string.prevention_keep_safe_distance,
            imageId = R.drawable.ic_distance
        ),
        Prevention(
            title = R.string.prevention_wash_hands,
            imageId = R.drawable.ic_handwash
        ),
        Prevention(
            title = R.string.prevention_cover_cough,
            imageId = R.drawable.ic_cough
        ),
        Prevention(
            title = R.string.prevention_wear_mask,
            imageId = R.drawable.ic_mask
        ),
        Prevention(
            title = R.string.prevention_clean_disinfect,
            imageId = R.drawable.ic_clean
        )
    )

}