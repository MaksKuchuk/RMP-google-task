package com.example.rmpgoogletask.Model.DataBase.Repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.rmpgoogletask.Model.DataBase.DB.MainDB
import com.example.rmpgoogletask.Model.Domain.Group
import com.example.rmpgoogletask.Model.Repository.IGroupRepository
import kotlinx.coroutines.flow.Flow

class GroupRepository(_db: MainDB) : IGroupRepository {
    private var db: MainDB = _db

    override suspend fun GetAllGroups(): List<Group> { return db.getGroupDao().GetAllGroups() }

    override suspend fun Create(item: Group) { db.getGroupDao().Create(item) }

    override suspend fun Update(item: Group) { db.getGroupDao().Update(item) }

    override suspend fun Delete(item: Group) { db.getGroupDao().Delete(item) }

    override suspend fun GetItem(id: Int): Group { return db.getGroupDao().GetItem(id) }
}