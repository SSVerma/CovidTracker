package com.ssverma.covidtracker.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.ssverma.covidtracker.di.annotation.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class SharedPrefsModule {

    companion object {
        const val SHARED_PREFERENCES_NAME = "CovidTrackerPrefs"
    }

    @ApplicationScope
    @Provides
    internal fun provideSharedPrefs(application: Application): SharedPreferences {
        return application.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }
}