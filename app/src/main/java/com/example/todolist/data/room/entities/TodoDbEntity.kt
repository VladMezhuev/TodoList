package com.example.todolist.data.room.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todolist.data.entites.AddItem
import com.example.todolist.data.entites.TodoItem
import kotlinx.android.parcel.Parcelize

@Entity(
  tableName = "todoList"
)
@Parcelize
data class TodoDbEntity(
  @ColumnInfo(name="id") @PrimaryKey(autoGenerate = true) val id: Int,
  @ColumnInfo(name="title") val title: String,
  @ColumnInfo(name="text") val text: String? = null,
  @ColumnInfo(name="created_at") val createdAt: String,
  @ColumnInfo(name="priority") val priority: Int,
  @ColumnInfo(name="status") var status: Boolean = false
): Parcelable {
  fun toTodoItem(): TodoItem {
    return TodoItem(
      id = id,
      title = title,
      text = text,
      createdAt = createdAt,
      priority = priority,
      status = status,
    )
  }

  companion object {
    fun createTodoDbEntity(addItem: AddItem): TodoDbEntity {
      return TodoDbEntity(
        id = 0,
        title = addItem.title,
        text = addItem.text,
        createdAt = addItem.createdAt,
        priority = addItem.priority,
        status = addItem.status
      )
    }
  }
}
