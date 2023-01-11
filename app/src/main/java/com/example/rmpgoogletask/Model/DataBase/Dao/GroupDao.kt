package com.example.rmpgoogletask.Model.DataBase.Dao

import androidx.room.*
import com.example.rmpgoogletask.Model.Domain.Group
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupDao {
    @Query("SELECT * FROM Groups")
    fun GetAllGroups(): List<Group>

    @Insert
    fun Create(item: Group)

    @Update
    fun Update(item: Group)

    @Delete
    fun Delete(item: Group)

    @Query("SELECT * FROM Groups WHERE id == :id LIMIT 1")
    fun GetItem(id: Int): Group
}