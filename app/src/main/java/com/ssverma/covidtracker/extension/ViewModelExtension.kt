package com.ssverma.covidtracker.extension

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

fun ViewModel.onRetry(retry: (suspend () -> Unit)?) {
    retry?.let {
        viewModelScope.launch { retry.invoke() }
    }
}