package com.cst.todotasks.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cst.todotasks.models.Todo

@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun getTodoDao(): TodoDao
}