package com.yusril.todo.db.todo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_db")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "desc")
    var desc: String? = null,

    @ColumnInfo(name = "date_created")
    var dateCreated: String,

    @ColumnInfo(name = "date_updated")
    var dateUpdated: String,

    @ColumnInfo(name = "due_date")
    var dueDate: String,

    @ColumnInfo(name = "due_time")
    var dueTime: String,

    @ColumnInfo(name = "remind_me")
    var remindMe: Boolean = true
)