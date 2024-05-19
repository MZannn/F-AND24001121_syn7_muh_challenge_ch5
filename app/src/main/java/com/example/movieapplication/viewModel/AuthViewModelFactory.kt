package com.example.movieapplication.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapplication.db.UserDatabase
import com.example.movieapplication.db.dao.UserDao

class AuthViewModelFactory private constructor(
    private val userDao: UserDao
) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: AuthViewModelFactory? = null

        fun getInstance(context: Context): AuthViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: AuthViewModelFactory(

                    UserDatabase.getInstance(context).userDao
                )
            }
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel(userDao) as T
            }

            else -> throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}