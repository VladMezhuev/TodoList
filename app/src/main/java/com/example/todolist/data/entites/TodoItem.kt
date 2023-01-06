package com.example.todolist.data.entites

data class TodoItem(
  val id: Int,
  val title: String,
  val createdAt: String,
  val priority: Int,
  val status: Boolean,
)
