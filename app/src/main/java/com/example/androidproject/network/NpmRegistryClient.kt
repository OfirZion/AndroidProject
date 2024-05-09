package com.example.androidproject.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.androidproject.helpers.LoadingState
import com.example.androidproject.models.npm.NpmRegistryRoot

class NpmRegistryClient(private val httpClient: HttpClient) {


    suspend fun searchNpmRegistry(
        query: String,
        loadingLiveData: MutableLiveData<LoadingState>,
        liveData: MutableLiveData<NpmRegistryRoot?>,
        exceptions: MutableLiveData<Exception?>,
    ) {
        loadingLiveData.postValue(LoadingState.Loading)
        Log.d("searchNpmRegistry", "Exec..")

        val response = httpClient.get<NpmRegistryRoot>(
            String.format(
                "https://registry.npmjs.org/-/v1/search?text=%s",
                query
            )
        )
        Log.d("searchNpmRegistry", response.toString())

        when (response) {
            is Resource.Success -> {
                liveData.postValue(response.data)
            }
            is Resource.Failure -> {
                exceptions.postValue(response.exc)
            }
        }
        loadingLiveData.postValue(LoadingState.Loaded)
    }
}