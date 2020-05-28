package com.yusril.todo.db.todo

import androidx.lifecycle.LiveData
import androidx.room.*
import com.yusril.todo.db.todo.Todo

@Dao
interface TodoDao {
    @Query("Select * from todo_db")
    fun getTodos(): LiveData<List<Todo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTodo(note: Todo)

    @Delete
    suspend fun deleteTodo(note: Todo)

    @Update
    suspend fun updateTodo(note: Todo)
}