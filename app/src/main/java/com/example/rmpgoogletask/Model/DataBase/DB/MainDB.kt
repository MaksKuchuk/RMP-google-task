package com.example.rmpgoogletask.Model.DataBase.DB

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rmpgoogletask.Model.DataBase.Dao.GroupDao
import com.example.rmpgoogletask.Model.DataBase.Dao.TaskDao
import com.example.rmpgoogletask.Model.Domain.Group
import com.example.rmpgoogletask.Model.Domain.Task

@Database (entities = [Task::class, Group::class], version = 1)
abstract class MainDB : RoomDatabase() {
    abstract fun getTaskDao(): TaskDao
    abstract fun getGroupDao(): GroupDao
    companion object {
        fun getDb(context: Context): MainDB {
            return Room.databaseBuilder(
                context.applicationContext,
                MainDB::class.java,
                "DB.db"
            ).build()
        }
    }
}