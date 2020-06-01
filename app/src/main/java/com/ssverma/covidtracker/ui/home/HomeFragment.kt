package com.ssverma.covidtracker.ui.home

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.ssverma.covidtracker.R
import com.ssverma.covidtracker.databinding.FragmentHomeBinding
import com.ssverma.covidtracker.di.ApplicationGraph
import com.ssverma.covidtracker.ui.BaseInjectionFragment
import com.ssverma.covidtracker.ui.stats.SymptomAdapter
import com.ssverma.covidtracker.util.decoration.GridSpacingDecoration

class HomeFragment : BaseInjectionFragment<FragmentHomeBinding, HomeViewModel>() {

    override fun provideLayoutResId(): Int {
        return R.layout.fragment_home
    }

    override fun provideViewModelType(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun injectFragment(applicationGraph: ApplicationGraph) {
        applicationGraph.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpContents()
        setUpPreventions()
    }

    private fun setUpPreventions() {
        binding.rvPreventions.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvPreventions.addItemDecoration(GridSpacingDecoration(2))

        val preventionAdapter = PreventionAdapter()
        binding.rvPreventions.adapter = preventionAdapter

        preventionAdapter.submitList(viewModel.preventions)
    }

    private fun setUpContents() {
        val symptomAdapter = SymptomAdapter()
        binding.viewPagerSymptom.adapter = symptomAdapter

        TabLayoutMediator(binding.tabIndicator, binding.viewPagerSymptom) { tab, position ->
            //
        }.attach()

        symptomAdapter.submitList(viewModel.symptoms)

        binding.viewPagerSymptom.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                symptomAdapter.notifyItemChanged(position)
            }
        })

    }
}