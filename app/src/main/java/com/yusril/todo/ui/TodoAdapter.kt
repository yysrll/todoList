package com.yusril.todo.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yusril.todo.R
import com.yusril.todo.db.todo.Todo
import com.yusril.todo.util.Commons
import kotlinx.android.synthetic.main.item_row_todo.view.*
import java.text.SimpleDateFormat
import java.util.*

class TodoAdapter(private val context: Context?, private val listener: (Todo, Int) -> Unit) :
    RecyclerView.Adapter<TodoViewHolder>() {

    private var todos = listOf<Todo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row_todo,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = todos.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        if (context != null) {
            val todoHolder = holder
            val sortedList = todos.sortedWith(
                if(MainActivity.sortCreate)
                    compareBy({it.dateCreated}, {it.dateUpdated})
                else{
                    compareBy({it.dueDate}, {it.dueTime})
                })
            holder.bindItem(context, todos[position], listener)
        }
    }

    fun setTodos(todos: List<Todo>) {
        this.todos = todos
        notifyDataSetChanged()
    }
}

class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindItem(context: Context, todo: Todo, listener: (Todo, Int) -> Unit) {
//        val parsedDateCreated = SimpleDateFormat("dd/MM/yy", Locale.US).parse(todo.dateCreated) as Date
//        val dateCreated = Commons.formatDate(parsedDateCreated, "dd MMM yyyy")
//
//        val parsedDateUpdated = SimpleDateFormat("dd/MM/yy", Locale.US).parse(todo.dateCreated) as Date
//        val dateUpdated = Commons.formatDate(parsedDateUpdated, "dd MMM yyyy")
//
//        val date = if (todo.dateUpdated != todo.dateCreated) "Updated at $dateUpdated" else "Created at $dateCreated"
//
//        val parsedDate = SimpleDateFormat("dd/MM/yy", Locale.US).parse(todo.dueDate) as Date
//        val dueDate = Commons.formatDate(parsedDate, "dd MMM yyyy")
//
//        val dueDateTime = "Due ${dueDate}, ${todo.dueTime}"

        itemView.tv_title.text = todo.title
        itemView.tv_note.text = todo.desc
        itemView.tv_due_date.text = todo.dueTime
        itemView.tv_date_created_updated.text = todo.dateCreated


        itemView.setOnClickListener {
            listener(todo, layoutPosition)
        }
    }

}