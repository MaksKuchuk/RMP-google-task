package com.example.rmpgoogletask.Model.Domain

data class Task(val id: Int, val title: String, var isFavourite: Boolean, var groupId: Int)
