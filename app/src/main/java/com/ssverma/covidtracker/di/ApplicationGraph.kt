package com.ssverma.covidtracker.di

import android.app.Application
import com.ssverma.covidtracker.di.annotation.ApplicationScope
import com.ssverma.covidtracker.ui.MainActivity
import com.ssverma.covidtracker.ui.about.AboutFragment
import com.ssverma.covidtracker.ui.country.CountryFragment
import com.ssverma.covidtracker.ui.home.HomeFragment
import com.ssverma.covidtracker.ui.stats.StatsFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        NetworkModule::class,
        SharedPrefsModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationGraph {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationGraph
    }

    fun inject(homeFragment: HomeFragment)
    fun inject(statsFragment: StatsFragment)
    fun inject(countryFragment: CountryFragment)
    fun inject(mainActivity: MainActivity)
    fun inject(aboutFragment: AboutFragment)

}