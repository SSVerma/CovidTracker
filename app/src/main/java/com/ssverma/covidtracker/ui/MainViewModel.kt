package com.ssverma.covidtracker.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {

    private val _searchQuery = MutableLiveData<String?>()

    val searchQuery: LiveData<String?>
        get() = _searchQuery

    fun onSearchTextChanged(query: String?) {
        _searchQuery.value = query
    }

}