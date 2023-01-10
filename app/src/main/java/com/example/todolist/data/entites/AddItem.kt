package com.example.todolist.data.entites

data class AddItem(
  val title: String,
  var text: String? = null,
  val createdAt: String,
  val priority: Int,
  val status: Boolean = false,
)
