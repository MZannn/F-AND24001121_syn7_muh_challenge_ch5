package com.example.movieapplication.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MovieViewModelFactory() :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: MovieViewModelFactory? = null

        fun getInstance(context: Context): MovieViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: MovieViewModelFactory(

                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> MovieViewModel() as T
            else -> throw Throwable("Unknown ViewModel class: ${modelClass.name}")
        }
    }

}
