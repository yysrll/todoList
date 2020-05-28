package com.yusril.todo.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.yusril.todo.db.todo.Todo
import com.yusril.todo.db.todo.TodoRepository

class TodoViewModel(application: Application): AndroidViewModel(application) {
    private var todoRepository = TodoRepository(application)
    private var todos: LiveData<List<Todo>>? = todoRepository.getTodos()


    fun getTodos(): LiveData<List<Todo>>? {
        return todos
    }

    fun insertTodo(todo: Todo) {
        todoRepository.insert(todo)
    }

    fun deleteTodo(todo: Todo) {
        todoRepository.delete(todo)
    }

    fun updateTodo(todo: Todo) {
        todoRepository.update(todo)
    }
}