package com.myanmaritc.news.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myanmaritc.news.api.NewsApiClient
import com.myanmaritc.news.model.News
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private var result: MutableLiveData<News> = MutableLiveData()

    private var errorStatus: MutableLiveData<Boolean> = MutableLiveData()

    private var errorMessage: MutableLiveData<String> = MutableLiveData()

    private var loading: MutableLiveData<Boolean> = MutableLiveData()

    private var searchResult: MutableLiveData<News> = MutableLiveData()



    fun getResult(): LiveData<News> = result

    fun getErrorStatus(): LiveData<Boolean> = errorStatus

    fun getErrorMessage(): LiveData<String> = errorMessage

    fun getLoading(): LiveData<Boolean> = loading
    

    fun loadResult() {

        var apiClient = NewsApiClient()

        var apiCall = apiClient.getTopHeadLines("us", "Technology")

        apiCall.enqueue(object : Callback<News> {
            override fun onFailure(call: Call<News>, t: Throwable) {
                loading.value = false
                errorStatus.value = true
                errorMessage.value = t.toString()
            }

            override fun onResponse(call: Call<News>, response: Response<News>) {
                loading.value = false
                errorStatus.value = false
                result.value = response.body()
            }

        })
    }

}