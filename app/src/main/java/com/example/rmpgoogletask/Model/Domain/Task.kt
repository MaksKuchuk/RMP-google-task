package com.example.rmpgoogletask.Model.Domain

import java.time.LocalDateTime
import java.util.Date

data class Task(val id: Int, val title: String, val description: String,
                val date: LocalDateTime, var isFavourite: Boolean, val isSubtaskFor: Int,
                var groupId: Int)
