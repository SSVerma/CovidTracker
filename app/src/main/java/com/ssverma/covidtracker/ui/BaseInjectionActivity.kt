package com.ssverma.covidtracker.ui

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ssverma.covidtracker.CovidTrackerApplication
import com.ssverma.covidtracker.di.ApplicationGraph
import com.ssverma.covidtracker.di.ViewModelFactory
import javax.inject.Inject

abstract class BaseInjectionActivity<B : ViewDataBinding, VM : ViewModel> : AppCompatActivity() {

    protected lateinit var binding: B
    protected lateinit var viewModel: VM

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        injectActivity((application as CovidTrackerApplication).applicationGraph)
        super.onCreate(savedInstanceState)
        bindContentView()
        viewModel = ViewModelProvider(this, viewModelFactory).get(provideViewModelType())
    }

    abstract fun injectActivity(applicationGraph: ApplicationGraph)

    @LayoutRes
    abstract fun provideLayoutResId(): Int

    abstract fun provideViewModelType(): Class<VM>

    private fun bindContentView() {
        binding = DataBindingUtil.setContentView(this, provideLayoutResId())
    }
}
