package com.ssverma.covidtracker.ui

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.ssverma.covidtracker.R
import com.ssverma.covidtracker.databinding.ActivityMainBinding
import com.ssverma.covidtracker.di.ApplicationGraph
import com.ssverma.covidtracker.ui.about.AboutFragment
import com.ssverma.covidtracker.ui.country.CountryFragment
import com.ssverma.covidtracker.ui.home.HomeFragment
import com.ssverma.covidtracker.ui.stats.StatsFragment
import kotlin.reflect.KClass


class MainActivity : BaseInjectionActivity<ActivityMainBinding, MainViewModel>() {

    companion object {
        fun launch(activity: Activity) {
            activity.startActivity(Intent(activity, MainActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpBottomNavigationView()
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

    private fun setUpBottomNavigationView() {
        setSupportActionBar(binding.toolbar)

        toggleToolbarIcon(false)
        updateToolbarTitle(R.string.app_name)
        switchTo(HomeFragment::class)
        binding.cvSearch.visibility = View.GONE

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    toggleToolbarIcon(false)
                    updateToolbarTitle(R.string.app_name)
                    binding.cvSearch.visibility = View.GONE

                    switchTo(HomeFragment::class)
                }
                R.id.navigation_statistics -> {
                    toggleToolbarIcon(true)
                    updateToolbarTitle(R.string.title_statistics)
                    binding.cvSearch.visibility = View.GONE

                    switchTo(StatsFragment::class)
                }
                R.id.navigation_country -> {
                    toggleToolbarIcon(true)
                    updateToolbarTitle(R.string.title_country)
                    binding.cvSearch.visibility = View.VISIBLE

                    switchTo(CountryFragment::class)
                }

                R.id.navigation_about -> {
                    toggleToolbarIcon(true)
                    updateToolbarTitle(R.string.title_about)
                    binding.cvSearch.visibility = View.GONE

                    switchTo(AboutFragment::class)
                }

                else -> false
            }
        }

    }

    private fun <T : Fragment> switchTo(clazz: KClass<T>): Boolean {
        val currentVisibleFragment: Fragment? =
            supportFragmentManager.primaryNavigationFragment

        val tag = clazz.simpleName

        if (currentVisibleFragment?.tag == tag) {
            return false
        }

        val fragmentTransaction: FragmentTransaction =
            supportFragmentManager.beginTransaction()

        if (currentVisibleFragment != null) {
            fragmentTransaction.hide(currentVisibleFragment)
        }

        val alreadyAddedFragment: Fragment? = supportFragmentManager.findFragmentByTag(tag)

        if (alreadyAddedFragment == null) {
            val destinationFragment = fragmentNewInstance(clazz)
            fragmentTransaction.add(R.id.fcv_home, destinationFragment, tag)
                .show(destinationFragment)
                .setPrimaryNavigationFragment(destinationFragment)
        } else {
            fragmentTransaction
                .setPrimaryNavigationFragment(alreadyAddedFragment)
                .show(alreadyAddedFragment)
        }

        fragmentTransaction.commit()

        return true
    }

    private fun <T : Fragment> fragmentNewInstance(clazz: KClass<T>): Fragment {
        return when (clazz) {
            HomeFragment::class -> HomeFragment()
            StatsFragment::class -> StatsFragment()
            CountryFragment::class -> CountryFragment()
            AboutFragment::class -> AboutFragment()
            else -> HomeFragment()
        }
    }

    private fun updateToolbarTitle(@StringRes titleId: Int) {
        supportActionBar?.setTitle(titleId)
    }

    private fun toggleToolbarIcon(shouldRemove: Boolean) {
        if (shouldRemove) {
            binding.toolbar.navigationIcon = null
            return
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            binding.toolbar.setNavigationIcon(R.drawable.ic_logo_navigation)
        } else {
            binding.toolbar.setNavigationIcon(R.drawable.toolbar_logo)
        }
    }

    private fun isHomeFragmentVisible(): Boolean {
        return supportFragmentManager.primaryNavigationFragment?.tag == HomeFragment::class.java.simpleName
    }

    override fun onBackPressed() {
        if (!isHomeFragmentVisible()) {
            switchTo(HomeFragment::class)
            binding.bottomNavigationView.selectedItemId = R.id.navigation_home
            return
        }
        super.onBackPressed()
    }

}
