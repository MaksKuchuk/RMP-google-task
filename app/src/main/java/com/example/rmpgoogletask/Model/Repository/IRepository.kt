package com.example.rmpgoogletask.Model.Repository

import androidx.room.Dao
import kotlinx.coroutines.flow.Flow

@Dao
interface IRepository<T> {
    suspend fun Create(item: T)
    suspend fun Update(item: T)
    suspend fun Delete(item: T)
    suspend fun GetItem(id: Int): T
}