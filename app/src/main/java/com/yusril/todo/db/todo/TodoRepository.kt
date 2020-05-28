package com.yusril.todo.db.todo

import android.app.Application
import androidx.lifecycle.LiveData
import com.yusril.todo.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TodoRepository(application: Application){
    private val todoDao: TodoDao?
    private var todos: LiveData<List<Todo>>? = null

    init{
        val db = AppDatabase.getInstance(application.applicationContext)
        todoDao = db?.todoDao()
        todos = todoDao?.getTodos()
    }

    fun getTodos(): LiveData<List<Todo>>?{
        return todos
    }

    fun insert(todo: Todo) = runBlocking {
        this.launch(Dispatchers.IO){
            todoDao?.insertTodo(todo)
        }
    }

    fun update(todo: Todo) = runBlocking {
        this.launch(Dispatchers.IO){
            todoDao?.updateTodo(todo)
        }
    }

    fun delete(todo: Todo) = runBlocking {
        this.launch(Dispatchers.IO){
            todoDao?.deleteTodo(todo)
        }
    }

}