package com.example.todolist.presentations.fragments.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.AppDatabase
import com.example.todolist.data.entites.AddItem
import com.example.todolist.data.entites.TodoItem
import com.example.todolist.data.room.entities.TodoDbEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EditItemViewModel @Inject constructor(
  private val database: AppDatabase
): ViewModel() {

//  private val _todoItem = MutableStateFlow<List<TodoDbEntity>>(emptyList())

  fun addTodoItem(addItem: AddItem) {
    viewModelScope.launch {
      withContext(Dispatchers.IO) {
        val entity = TodoDbEntity.createTodoDbEntity(addItem = addItem)
        database.getTodoListDao().addTodoItem(entity)
      }
    }
  }

}