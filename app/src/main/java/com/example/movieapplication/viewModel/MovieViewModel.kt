package com.example.movieapplication.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapplication.api.ApiClient
import com.example.movieapplication.model.response.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel : ViewModel() {
    private var _movieResponse = MutableLiveData<MovieResponse?>()

    val movieResponse: MutableLiveData<MovieResponse?> get() = _movieResponse
    fun getMovieNowPlaying() {
        ApiClient.instance.getMovieNowPlaying().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val data = response.body()
                _movieResponse.postValue(data)
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                _movieResponse.postValue(null)
            }

        })
    }

}