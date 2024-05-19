package com.example.movieapplication.viewModel

import androidx.lifecycle.ViewModel
import com.example.movieapplication.db.dao.UserDao
import com.example.movieapplication.model.User

class AuthViewModel(private val userDao: UserDao) : ViewModel() {
    fun login(email: String, password: String): User? {
        return userDao.login(email, password)
    }

    fun register(user: User) {
        return userDao.insert(user)
    }

    fun getAllUsers() = userDao.getAllUsers()

    fun getUserById(id: Int) = userDao.getUserById(id)

    fun updateUser(
        username: String,
        fullname: String,
        birthdate: String,
        address: String,
        id: Int
    ) = userDao.updateUser(
        username = username,
        fullname = fullname,
        birthdate = birthdate,
        address = address,
        id
    )
}