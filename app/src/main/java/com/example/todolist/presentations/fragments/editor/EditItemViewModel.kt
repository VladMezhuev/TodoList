package com.example.todolist.presentations.fragments.editor

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.AppDatabase
import com.example.todolist.data.entites.AddItem
import com.example.todolist.data.entites.TodoItem
import com.example.todolist.data.room.entities.TodoDbEntity
import com.example.todolist.presentations.ADD_TODO_RESULT_OK
import com.example.todolist.presentations.EDIT_TODO_RESULT_OK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EditItemViewModel @Inject constructor(
  private val database: AppDatabase
): ViewModel() {

  private val addEditTaskEventChannel = Channel<AddEditTaskEvent>()
  val addEditTaskEvent = addEditTaskEventChannel.receiveAsFlow()

  fun addTodoItem(title: String, text: String?, createdAt: String, priority: Int) {
    viewModelScope.launch {
      withContext(Dispatchers.IO) {
        if (title.isNotEmpty()) {
          val item = AddItem(title = title, text = text, createdAt = createdAt, priority = priority)
          val entity = TodoDbEntity.createTodoDbEntity(addItem = item)
          database.getTodoListDao().addTodoItem(entity)
          addEditTaskEventChannel.send(AddEditTaskEvent.NavigateBackWithResult(ADD_TODO_RESULT_OK))
        } else {
          showInvalidInputMessage("Title cannot be empty")
          return@withContext
        }
      }
    }
  }

  fun editTodoItem(item: TodoDbEntity, title: String, text: String?, priority: Int) {
    viewModelScope.launch {
      withContext(Dispatchers.IO) {
        if (title.isNotEmpty()) {
          database.getTodoListDao().updateItem(
            item.copy(title = title, text = text, priority = priority)
          )
          addEditTaskEventChannel.send(AddEditTaskEvent.NavigateBackWithResult(EDIT_TODO_RESULT_OK))
        } else {
          showInvalidInputMessage("Title cannot be empty")
          return@withContext
        }
      }
    }
  }

  fun deleteTodoItem(item: TodoDbEntity) {
    viewModelScope.launch {
      withContext(Dispatchers.IO) {
        database.getTodoListDao().deleteTodoItem(item)
      }
    }
  }

  private fun showInvalidInputMessage(text: String) {
    viewModelScope.launch {
      addEditTaskEventChannel.send(AddEditTaskEvent.ShowInvalidInputMessage(text))
    }
  }

  sealed class AddEditTaskEvent {
    data class ShowInvalidInputMessage(val message: String): AddEditTaskEvent()
    data class NavigateBackWithResult(val result: Int) : AddEditTaskEvent()
  }

}