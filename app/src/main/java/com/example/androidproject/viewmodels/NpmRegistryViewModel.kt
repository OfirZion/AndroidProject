package com.example.androidproject.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidproject.helpers.LoadingState
import com.example.androidproject.models.npm.NpmRegistryRoot
import com.example.androidproject.network.NpmRegistryClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NpmRegistryViewModel
@Inject constructor(private val npmRegistryClient: NpmRegistryClient) : ViewModel() {

    private val _registryLiveData = MutableLiveData<NpmRegistryRoot?>(null)
    private val _loadingState = MutableLiveData<LoadingState>(LoadingState.Loaded)
    private val _exceptions = MutableLiveData<Exception?>(null)

    val registryLiveData: LiveData<NpmRegistryRoot?> get() = _registryLiveData
    val loadingState: LiveData<LoadingState> get() = _loadingState
    val exceptions: LiveData<Exception?> get() = _exceptions

    private  var searchJob: Job? = null

    fun search(query: String) {
        if (query.isBlank()) return
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500) // wait 500 ms before executing a new request
            npmRegistryClient.searchNpmRegistry(
                query = query,
                loadingLiveData = _loadingState,
                liveData = _registryLiveData,
                exceptions = _exceptions
            )
        }
    }

}