package com.yusril.todo

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import kotlinx.android.synthetic.main.activity_add.*
import java.text.SimpleDateFormat
import java.util.*

class AddActivity : AppCompatActivity(), View.OnClickListener {

    private var buttonDate: Button? = null
    private var textViewDate: TextView? = null
    private var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        OnClickTime()

        //INTENT FOR CANCEL BUTTON

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
            DatePickerDialog(this@AddActivity,
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
        val textViewDate = findViewById<TextView>(R.id.in_time)
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
            if (textViewDate != null) {
                val hour = if (hour < 10) "0" + hour else hour
                val min = if (minute < 10) "0" + minute else minute
                // display format of time
                val msg = "Time is: $hour : $min $am_pm"
                textViewDate.text = msg
                textViewDate.visibility = ViewGroup.VISIBLE
            }
        }
    }



//BUTTON CANCEL

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_cancel -> {
                val moveIntent = Intent(this@AddActivity, MainActivity::class.java)
                startActivity(moveIntent)
            }
        }
    }
}
