package com.ssverma.covidtracker.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ssverma.covidtracker.CovidTrackerApplication
import com.ssverma.covidtracker.di.ApplicationGraph
import com.ssverma.covidtracker.di.ViewModelFactory
import javax.inject.Inject

abstract class BaseInjectionFragment<B : ViewDataBinding, VM : ViewModel> : Fragment() {

    protected lateinit var binding: B
    protected lateinit var viewModel: VM

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onAttach(context: Context) {
        activity?.let {
            injectFragment((it.application as CovidTrackerApplication).applicationGraph)
        }
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(provideViewModelType())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, provideLayoutResId(), container, false)
        return binding.root
    }

    @LayoutRes
    abstract fun provideLayoutResId(): Int

    abstract fun provideViewModelType(): Class<VM>

    abstract fun injectFragment(applicationGraph: ApplicationGraph)

}