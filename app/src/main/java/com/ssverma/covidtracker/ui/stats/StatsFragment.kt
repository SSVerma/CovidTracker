package com.ssverma.covidtracker.ui.stats

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssverma.covidtracker.R
import com.ssverma.covidtracker.api.Status
import com.ssverma.covidtracker.data.model.domain.asStatItems
import com.ssverma.covidtracker.databinding.FragmentStatsBinding
import com.ssverma.covidtracker.di.ApplicationGraph
import com.ssverma.covidtracker.extension.displaySnackBar
import com.ssverma.covidtracker.ui.BaseInjectionFragment
import com.ssverma.covidtracker.ui.home.StatAdapter
import com.ssverma.covidtracker.util.decoration.GridSpacingDecoration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StatsFragment : BaseInjectionFragment<FragmentStatsBinding, StatsViewModel>() {

    override fun provideLayoutResId(): Int {
        return R.layout.fragment_stats
    }

    override fun provideViewModelType(): Class<StatsViewModel> {
        return StatsViewModel::class.java
    }

    override fun injectFragment(applicationGraph: ApplicationGraph) {
        applicationGraph.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpContents()
        setUpMyCountrySection()
    }

    private fun setUpMyCountrySection() {
        binding.rvMyCountryStats.layoutManager =
            GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
        binding.rvMyCountryStats.addItemDecoration(GridSpacingDecoration(2, 8))

        val statAdapter = StatAdapter()
        binding.rvMyCountryStats.adapter = statAdapter

        viewModel.myCountryStats.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { stat ->
                        lifecycleScope.launch(Dispatchers.Default) {
                            val items = stat.asStatItems()
                            withContext(Dispatchers.Main) {
                                statAdapter.submitList(items)
                                toggleMyCountryLoadingIndicator(false)
                            }
                        }
                        binding.flagImageUrl = stat.countryInfo?.flag
                        binding.tvCountryName.text = stat.country
                        binding.tvMyCountryLabel.visibility = View.VISIBLE
                    }
                }

                Status.LOADING -> {
                    toggleMyCountryLoadingIndicator(true)
                }

                Status.ERROR_API -> {
                    toggleMyCountryLoadingIndicator(false)
                    binding.tvMyCountryLabel.visibility = View.GONE
                    activity?.displaySnackBar(it.errorMessage)
                }

                Status.ERROR_CONNECTION -> {
                    toggleMyCountryLoadingIndicator(false)
                    binding.tvMyCountryLabel.visibility = View.GONE
                    activity?.displaySnackBar(it.errorMessage)
                }

            }
        })
    }

    private fun toggleGlobalStatLoadingIndicator(shouldShow: Boolean) {
        if (shouldShow) {
            binding.shimmerGlobal.visibility = View.VISIBLE
            binding.shimmerGlobal.startShimmer()
        } else {
            binding.shimmerGlobal.stopShimmer()
            binding.shimmerGlobal.visibility = View.GONE
        }
    }


    private fun setUpContents() {
        binding.rvGlobalStats.layoutManager =
            GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
        binding.rvGlobalStats.addItemDecoration(GridSpacingDecoration(2, 8))

        val statAdapter = StatAdapter()
        binding.rvGlobalStats.adapter = statAdapter

        viewModel.globalStats.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    lifecycleScope.launch(Dispatchers.Default) {
                        val items = it.data?.asStatItems()
                        withContext(Dispatchers.Main) {
                            statAdapter.submitList(items)
                            toggleGlobalStatLoadingIndicator(false)
                            binding.tvGlobalLabel.visibility = View.VISIBLE
                        }
                    }
                }

                Status.LOADING -> {
                    toggleGlobalStatLoadingIndicator(true)
                }

                Status.ERROR_API -> {
                    toggleGlobalStatLoadingIndicator(false)
                    binding.tvGlobalLabel.visibility = View.GONE
                    activity?.displaySnackBar(it.errorMessage)
                }

                Status.ERROR_CONNECTION -> {
                    toggleGlobalStatLoadingIndicator(false)
                    binding.tvGlobalLabel.visibility = View.GONE
                    activity?.displaySnackBar(it.errorMessage)
                }
            }
        })
    }

    private fun toggleMyCountryLoadingIndicator(shouldShow: Boolean) {
        if (shouldShow) {
            binding.shimmerMyCountry.visibility = View.VISIBLE
            binding.shimmerMyCountry.startShimmer()
        } else {
            binding.shimmerMyCountry.stopShimmer()
            binding.shimmerMyCountry.visibility = View.GONE
        }
    }

}