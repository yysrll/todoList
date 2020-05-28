package com.yusril.todo.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.yusril.todo.R
import com.yusril.todo.db.todo.Todo
import com.yusril.todo.ui.TodoViewModel
import kotlinx.android.synthetic.main.activity_add.*
import java.text.SimpleDateFormat
import java.util.*


class EditActivity : AppCompatActivity(), View.OnClickListener {

    private var buttonDate: Button? = null
    private var textViewDate: TextView? = null
    private var cal = Calendar.getInstance()
    private lateinit var todoViewModel: TodoViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        OnClickTime()

        todoViewModel = ViewModelProvider(this).get(TodoViewModel::class.java)

        //INTENT FOR BUTTON

        val btnMoveActivityData: Button = findViewById(R.id.button)
        btnMoveActivityData.setOnClickListener(this)

        val btnMoveActivity: Button = findViewById(R.id.button_cancel)
        btnMoveActivity.setOnClickListener(this)

        //TIME PICKER

        // get the references from layout file
        textViewDate = this.in_date
        buttonDate = this.btn_date

        textViewDate!!.text = "--/--/----"

        // create an OnDateSetListener
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }

        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        buttonDate!!.setOnClickListener {
            DatePickerDialog(this@EditActivity,
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }


    }





    //DATE PICKER

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textViewDate!!.text = sdf.format(cal.time)
    }

    //TIME PICKER

    private fun OnClickTime() {
        val textViewTime = findViewById<TextView>(R.id.in_time)
        val timePicker = findViewById<TimePicker>(R.id.timePicker)
        timePicker.setOnTimeChangedListener { _, hour, minute -> var hour = hour
            var am_pm = ""
            // AM_PM decider logic
            when {hour == 0 -> { hour += 12
                am_pm = "AM"
            }
                hour == 12 -> am_pm = "PM"
                hour > 12 -> { hour -= 12
                    am_pm = "PM"
                }
                else -> am_pm = "AM"
            }
            if (textViewTime != null) {
                val hour = if (hour < 10) "0" + hour else hour
                val min = if (minute < 10) "0" + minute else minute
                // display format of time
                val msg = "$hour:$min $am_pm"
                textViewTime.text = msg
                textViewTime.visibility = ViewGroup.VISIBLE
            }
        }
    }

    fun formatDate(date: Date, format: String): String{
        return date.toString(format)
    }

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }



    override fun onClick(v: View) {    val currentDate = getCurrentDateTime()

    val dateCreated = formatDate(currentDate, "dd/MM/yy HH:mm:ss")
    val dateUpdate = dateCreated
    val title = findViewById<EditText>(R.id.input_todo).text.toString()
    val description = findViewById<EditText>(R.id.input_desc).text.toString()
    val dueDate = findViewById<TextView>(R.id.due_date).text.toString()
    val dueTime = findViewById<TextView>(R.id.in_date).text.toString()


    val todo = Todo(
        title = title,
        desc = description,
        dateCreated = dateCreated,
        dateUpdated = dateUpdate,
        dueDate = dueDate,
        dueTime = dueTime,
        remindMe = true
    )


    //BUTTON


        when (v.id) {
            R.id.button_cancel -> {
                val moveIntent = Intent(this@EditActivity, MainActivity::class.java)
                startActivity(moveIntent)
            }

            R.id.button -> {
                val moveWithDataIntent = Intent(this@EditActivity, MainActivity::class.java)

                todoViewModel.updateTodo(todo)

                startActivity(moveWithDataIntent)
            }
        }
    }




//    override fun onClick(v: View) {
//        when (v.id) {
//            R.id.button_cancel -> {
//                val moveIntent = Intent(this@AddActivity, MainActivity::class.java)
//                startActivity(moveIntent)
//            }
//        }
//    }
}
