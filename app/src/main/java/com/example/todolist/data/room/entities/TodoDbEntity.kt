package com.example.todolist.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.example.todolist.data.entites.TodoItem

data class TodoDbEntity(
  @ColumnInfo(name="id") @PrimaryKey(autoGenerate = true) val id: Int,
  @ColumnInfo(name="title") val title: String,
  @ColumnInfo(name="created_at") val createdAt: String,
  @ColumnInfo(name="priority") val priority: Int,
  @ColumnInfo(name="status") val status: Boolean
) {
  fun toTodoItem(): TodoItem {
    return TodoItem(
      id = id,
      title = title,
      createdAt = createdAt,
      priority = priority,
      status = status,
    )
  }

  companion object {
    fun createTodoDbEntity(todoItem: TodoItem): TodoDbEntity {
      return TodoDbEntity(
        id = 0,
        title = todoItem.title,
        createdAt = todoItem.createdAt,
        priority = todoItem.priority,
        status = todoItem.status
      )
    }
  }
}
