package com.cst.todotasks.dao

import androidx.room.*
import com.cst.todotasks.models.Todo

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun getAll(): List<Todo>

    @Insert
    fun insertTodo(vararg todo: Todo)

    @Delete
    fun deleteTodo(todo: Todo)

    @Update
    fun updateTodo(vararg todo: Todo)

    @Query("UPDATE todo SET isChecked = (:isChecked) WHERE id = (:id)")
    fun updateChecked(id: Int, isChecked: Boolean)

}