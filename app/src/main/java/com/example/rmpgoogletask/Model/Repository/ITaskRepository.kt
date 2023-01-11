package com.example.rmpgoogletask.Model.Repository

import androidx.room.Dao
import com.example.rmpgoogletask.Model.Domain.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface ITaskRepository : IRepository<Task> {
    suspend fun GetItemsBySubtaskFor(id: Int): List<Task>
    suspend fun GetItemsByGroupId(id: Int): List<Task>
    suspend fun GetItemsByFavourite(): List<Task>
    suspend fun GetAllTasks(): List<Task>
    suspend fun DeleteAllTasksByGroup(id: Int)
}