package com.ssverma.covidtracker.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ssverma.covidtracker.ui.MainViewModel
import com.ssverma.covidtracker.ui.country.CountryViewModel
import com.ssverma.covidtracker.ui.home.HomeViewModel
import com.ssverma.covidtracker.ui.stats.StatsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory)
            : ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StatsViewModel::class)
    abstract fun bindStatsViewModel(statsViewModel: StatsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CountryViewModel::class)
    abstract fun bindCountryViewModel(countryViewModel: CountryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

}