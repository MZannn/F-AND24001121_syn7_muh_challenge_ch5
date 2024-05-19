package com.example.movieapplication.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movieapplication.db.dao.UserDao
import com.example.movieapplication.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract val userDao: UserDao

    companion object {
        private const val DATABASE_NAME = "NOTES_DATABASE"

        private var INSTANCES: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase {
            var instance = INSTANCES
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    DATABASE_NAME
                ).fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                INSTANCES = instance
            }
            return instance
        }

    }
}