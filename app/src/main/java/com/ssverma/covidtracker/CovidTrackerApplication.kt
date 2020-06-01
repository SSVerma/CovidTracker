package com.ssverma.covidtracker

import android.app.Application
import com.ssverma.covidtracker.di.ApplicationGraph
import com.ssverma.covidtracker.di.DaggerApplicationGraph

class CovidTrackerApplication : Application() {
    val applicationGraph: ApplicationGraph by lazy {
        DaggerApplicationGraph.builder()
            .application(this)
            .build()
    }
}