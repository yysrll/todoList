package com.yusril.todo.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.yusril.todo.R
import com.yusril.todo.db.todo.Todo
import kotlinx.android.synthetic.main.item_row_todo.view.*
import java.util.*


class TodoAdapter(private val context: Context?, private val listener: (Todo, Int) -> Unit) :
    RecyclerView.Adapter<TodoViewHolder>(),Filterable {

    private var todos = listOf<Todo>()
    private var todoFilteredList = listOf<Todo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row_todo,
                parent,
                false
            )
        )
    }




    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val keywords = constraint.toString()
                if (keywords.isEmpty())
                    todoFilteredList = todos
                else{
                    val filteredList = ArrayList<Todo>()
                    for (todo in todos) {
                        if (todo.toString().toLowerCase(Locale.ROOT).contains(keywords.toLowerCase(Locale.ROOT)))
                            filteredList.add(todo)
                    }
                    todoFilteredList = filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = todoFilteredList
                return  filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                todoFilteredList = results?.values as List<Todo>
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int = if (todoFilteredList.isEmpty()) 1 else todoFilteredList.size


    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        if (context != null) {
            val todoHolder = holder
            val sortedList = todos.sortedWith(
                if(MainActivity.sortCreate)
                    compareBy({it.dateCreated}, {it.dateUpdated})
                else{
                    compareBy({it.dueDate}, {it.dueTime})
                })
            todoHolder.bindItem(context, sortedList[position], listener)
        }
    }

    fun setTodos(todos: List<Todo>) {
        this.todos = todos
        todoFilteredList = todos
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