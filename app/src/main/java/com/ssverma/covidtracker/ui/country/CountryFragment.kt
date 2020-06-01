package com.ssverma.covidtracker.ui.country


import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssverma.covidtracker.R
import com.ssverma.covidtracker.api.Status
import com.ssverma.covidtracker.data.model.domain.toDomain
import com.ssverma.covidtracker.databinding.FragmentCountryBinding
import com.ssverma.covidtracker.di.ApplicationGraph
import com.ssverma.covidtracker.extension.displaySnackBar
import com.ssverma.covidtracker.ui.BaseInjectionFragment
import com.ssverma.covidtracker.ui.MainActivity
import com.ssverma.covidtracker.ui.MainViewModel
import kotlinx.coroutines.launch

class CountryFragment : BaseInjectionFragment<FragmentCountryBinding, CountryViewModel>() {
    override fun provideLayoutResId(): Int {
        return R.layout.fragment_country
    }

    override fun provideViewModelType(): Class<CountryViewModel> {
        return CountryViewModel::class.java
    }

    override fun injectFragment(applicationGraph: ApplicationGraph) {
        applicationGraph.inject(this)
    }

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(
            (activity as MainActivity),
            viewModelFactory
        ).get(MainViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpContents()
    }

    private fun setUpContents() {
        binding.rvCountries.layoutManager = LinearLayoutManager(requireContext())

        val countryStatAdapter = CountryStatAdapter()
        binding.rvCountries.adapter = countryStatAdapter

        mainViewModel.searchQuery.observe(this, Observer {
            countryStatAdapter.search(it)
        })

        viewModel.countriesStats.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    lifecycleScope.launch {
                        val countries = it.data?.toDomain()
                        countryStatAdapter.submitList(countries, true)
                        toggleMyCountryLoadingIndicator(false)
                    }
                }

                Status.LOADING -> {
                    toggleMyCountryLoadingIndicator(true)
                }

                Status.ERROR_API -> {
                    toggleMyCountryLoadingIndicator(false)
                    activity?.displaySnackBar(it.errorMessage)
                }

                Status.ERROR_CONNECTION -> {
                    toggleMyCountryLoadingIndicator(false)
                    activity?.displaySnackBar(it.errorMessage)
                }
            }
        })
    }

    private fun toggleMyCountryLoadingIndicator(shouldShow: Boolean) {
        if (shouldShow) {
            binding.shimmerCountries.visibility = View.VISIBLE
            binding.shimmerCountries.startShimmer()
            binding.rvCountries.visibility = View.GONE
        } else {
            binding.shimmerCountries.stopShimmer()
            binding.shimmerCountries.visibility = View.GONE
            binding.rvCountries.visibility = View.VISIBLE
        }
    }

}
