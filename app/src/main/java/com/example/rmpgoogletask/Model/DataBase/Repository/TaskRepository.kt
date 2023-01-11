package com.example.rmpgoogletask.Model.DataBase.Repository

import com.example.rmpgoogletask.Model.DataBase.DB.MainDB
import com.example.rmpgoogletask.Model.Domain.Task
import com.example.rmpgoogletask.Model.Repository.ITaskRepository
import kotlinx.coroutines.flow.Flow

class TaskRepository(_db: MainDB) : ITaskRepository {
    private var db: MainDB = _db

    override suspend fun GetItemsBySubtaskFor(id: Int): List<Task> {
        return db.getTaskDao().GetItemsBySubtaskFor(id)
    }

    override suspend fun GetItemsByGroupId(id: Int): List<Task> {
        return db.getTaskDao().GetItemsByGroupId(id)
    }

    override suspend fun GetItemsByFavourite(): List<Task> {
        return db.getTaskDao().GetItemsByFavourite()
    }

    override suspend fun GetAllTasks(): List<Task> {
        return db.getTaskDao().GetAllTasks()
    }

    override suspend fun Create(item: Task) {
        db.getTaskDao().Create(item)
    }

    override suspend fun Update(item: Task) {
        db.getTaskDao().Update(item)
    }

    override suspend fun Delete(item: Task) {
        db.getTaskDao().Delete(item)
    }

    override suspend fun GetItem(id: Int): Task {
        return db.getTaskDao().GetItem(id)
    }

    override suspend fun DeleteAllTasksByGroup(id: Int) {
        return db.getTaskDao().DeleteAllTasksByGroup(id)
    }
}