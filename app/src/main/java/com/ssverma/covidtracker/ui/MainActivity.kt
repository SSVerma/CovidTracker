package com.ssverma.covidtracker.ui

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ssverma.covidtracker.R
import com.ssverma.covidtracker.databinding.ActivityMainBinding
import com.ssverma.covidtracker.di.ApplicationGraph
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseInjectionActivity<ActivityMainBinding, MainViewModel>() {

    companion object {
        fun launch(activity: Activity) {
            activity.startActivity(Intent(activity, MainActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpToolbarWithNavController()
        setUpSearchView()
    }

    override fun injectActivity(applicationGraph: ApplicationGraph) {
        applicationGraph.inject(this)
    }

    override fun provideLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun provideViewModelType(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    private fun setUpSearchView() {
        binding.cvSearch.setOnClickListener {
            binding.searchView.isIconified = false
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.onSearchTextChanged(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.onSearchTextChanged(newText)
                return true
            }

        })
    }

    private fun setUpToolbarWithNavController() {
        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_statistics,
                R.id.navigation_country,
                R.id.navigation_about
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.navigation_home) {
                supportActionBar?.title = getString(R.string.app_name)
                updateToolbarIcon()
            } else {
                toolbar.navigationIcon = null
            }

            if (destination.id == R.id.navigation_country) {
                binding.cvSearch.visibility = View.VISIBLE
            } else {
                binding.cvSearch.visibility = View.GONE
            }

        }

    }

    private fun updateToolbarIcon() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            toolbar.setNavigationIcon(R.drawable.ic_logo_navigation)
        } else {
            toolbar.setNavigationIcon(R.drawable.toolbar_logo)
        }
    }
}
