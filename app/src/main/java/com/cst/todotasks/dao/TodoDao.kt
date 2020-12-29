package com.cst.todotasks.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.cst.todotasks.models.Todo

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun getAll(): List<Todo>

    @Insert
    fun insertTodo(vararg todo: Todo)

    @Delete
    fun deleteTodo(todo: Todo)

}