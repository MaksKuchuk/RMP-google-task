package com.example.rmpgoogletask.Model.Repository

import androidx.room.Dao
import com.example.rmpgoogletask.Model.Domain.Group
import kotlinx.coroutines.flow.Flow

@Dao
interface IGroupRepository : IRepository<Group> {
    suspend fun GetAllGroups(): List<Group>
}