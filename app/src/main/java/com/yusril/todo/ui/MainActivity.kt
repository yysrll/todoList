package com.yusril.todo.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yusril.todo.R
import com.yusril.todo.db.todo.Todo
import com.yusril.todo.util.Commons
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var todoViewModel: TodoViewModel
    private lateinit var todoAdapter: TodoAdapter


    private val todoList: ArrayList<Todo> = ArrayList<Todo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnMoveActivity: Button = findViewById(R.id.btn_add)
        btnMoveActivity.setOnClickListener(this)

        val layoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = layoutManager

        todoAdapter = TodoAdapter(this) { todo, i ->
            showAlertMenu(todo)
        }

        recyclerview.adapter = todoAdapter

        todoViewModel = ViewModelProvider(this).get(TodoViewModel::class.java)
        todoViewModel.getTodos()?.observe(this, Observer {
            todoAdapter.setTodos(it)
        })

    }



    private fun showAlertMenu(todo: Todo) {
        val items = arrayOf("Edit", "Delete")

        val builder =
            AlertDialog.Builder(this)
        builder.setItems(items) { dialog, which ->
            // the user clicked on colors[which]
            when (which) {
                0 -> {
                    val editIntent = Intent(this@MainActivity, EditActivity::class.java)
                    startActivity(editIntent)
                }
                1 -> {
                    todoViewModel.deleteTodo(todo)
                }
            }
        }
        builder.show()
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_add -> {
                val moveIntent = Intent(this@MainActivity, AddActivity::class.java)
                startActivity(moveIntent)
            }
        }
    }
}


