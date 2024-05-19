package com.example.movieapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "username")
    val username: String,
    @ColumnInfo(name = "password")
    val password: String,
    @ColumnInfo(name = "fullname")
    val fullname: String? = null,
    @ColumnInfo(name = "birthdate")
    val birthdate: String? = null,

    @ColumnInfo(name = "address")
    val address: String? = null,
)
